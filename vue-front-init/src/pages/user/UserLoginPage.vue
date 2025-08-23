<template>
  <div class="login-page">
    <!-- 背景装饰 -->
    <div class="bg-decoration"></div>
    <div class="bg-circles">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="circle circle-4"></div>
    </div>

    <!-- 主容器 -->
    <div class="login-container">
      <div class="brand-section">
        <div class="logo-container">
          <div class="logo-icon">S</div>
        </div>
        <h2 class="brand-name">SpringBoot Init</h2>
        <p class="brand-slogan">快速、安全的应用程序框架</p>
      </div>

      <div class="login-box" :class="{ 'slide-register': isRegister }">
        <!-- 标题区域 -->
        <div class="title-container">
          <h1 class="main-title">{{ isRegister ? '创建账号' : '欢迎回来' }}</h1>
          <p class="sub-title">{{ isRegister ? '填写信息以创建您的账号' : '请输入您的账号继续' }}</p>
          <div class="toggle-buttons">
            <button
              class="toggle-btn"
              :class="{ active: !isRegister }"
              @click="toggleForm(false)"
            >
              登录
            </button>
            <button
              class="toggle-btn"
              :class="{ active: isRegister }"
              @click="toggleForm(true)"
            >
              注册
            </button>
          </div>
        </div>

        <!-- 表单滑动容器 -->
        <div class="form-slider" :style="{ transform: isRegister ? 'translateX(-100%)' : 'translateX(0)' }">
          <!-- 登录表单 -->
          <a-form
            :model="loginForm"
            :rules="loginRules"
            ref="loginFormRef"
            class="form-panel login-form"
          >
            <a-form-item name="userAccount">
              <a-input
                v-model:value="loginForm.userAccount"
                placeholder="请输入账号"
                prefix-icon="user"
              />
            </a-form-item>

            <a-form-item name="userPassword">
              <a-input-password
                v-model:value="loginForm.userPassword"
                placeholder="请输入密码"
                prefix-icon="lock"
              />
            </a-form-item>

            <a-form-item class="form-actions">
              <a-button
                type="primary"
                block
                @click="handleLogin"
                :loading="loading"
              >
                登录
              </a-button>
            </a-form-item>
          </a-form>

          <!-- 注册表单 -->
          <a-form
            :model="registerForm"
            :rules="registerRules"
            ref="registerFormRef"
            class="form-panel register-form"
          >
            <a-form-item name="userAccount">
              <a-input
                v-model:value="registerForm.userAccount"
                placeholder="请输入账号"
                prefix-icon="user"
              />
            </a-form-item>

            <a-form-item name="userPassword">
              <a-input-password
                v-model:value="registerForm.userPassword"
                placeholder="请输入密码"
                prefix-icon="lock"
              />
            </a-form-item>

            <a-form-item name="checkPassword">
              <a-input-password
                v-model:value="registerForm.checkPassword"
                placeholder="请确认密码"
                prefix-icon="lock"
              />
            </a-form-item>

            <a-form-item class="form-actions">
              <a-button
                type="primary"
                block
                @click="handleRegister"
                :loading="loading"
              >
                注册
              </a-button>
            </a-form-item>
          </a-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue';
import { message } from 'ant-design-vue';
import { userLogin, userRegister, userLogout } from '@/api/userController';
import { useLoginUserStore } from '@/stores/loginUser';
import { useRouter } from 'vue-router';

// 路由
const router = useRouter();
const loginUserStore = useLoginUserStore()

// 表单引用
const loginFormRef = ref(null);
const registerFormRef = ref(null);

// 状态管理
const isRegister = ref(false);
const loading = ref(false);

// 登录表单数据
const loginForm = reactive({
  userAccount: '',
  userPassword: ''
});

// 注册表单数据
const registerForm = reactive({
  userAccount: '',
  userPassword: '',
  checkPassword: ''
});

// 登录表单验证规则
const loginRules = {
  userAccount: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 4, message: '账号长度不能少于4位', trigger: 'blur' }
  ],
  userPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, message: '密码长度不能少于8位', trigger: 'blur' }
  ]
};

// 注册表单验证规则
const registerRules = {
  userAccount: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 4, message: '账号长度不能少于4位', trigger: 'blur' }
  ],
  userPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, message: '密码长度不能少于8位', trigger: 'blur' }
  ],
  checkPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (_, value) => {
        if (!value) {
          return Promise.reject('请确认密码');
        }
        if (value !== registerForm.userPassword) {
          return Promise.reject('两次输入的密码不一致');
        }
        return Promise.resolve();
      },
      trigger: 'blur'
    }
  ]
};

// 切换登录/注册表单
const toggleForm = (register) => {
  isRegister.value = register;
  // 重置表单状态
  if (register) {
    loginFormRef.value?.resetFields();
  } else {
    registerFormRef.value?.resetFields();
  }
};

// 处理登录
const handleLogin = async () => {
  try {
    loading.value = true;
    await loginFormRef.value.validate();

    // 调用登录API
    const res = await userLogin(loginForm);
    if (res.data.code === 0 && res.data.data) {
      await loginUserStore.fetchLoginUser()
      message.success('登录成功');
      // 登录成功后跳转到首页
      router.push('/');
    } else {
      message.error(res.data.message || '登录失败，请检查账号密码');
    }
  } catch (error) {
    console.log('登录验证失败:', error);
    message.error('登录失败，请检查账号密码');
  } finally {
    loading.value = false;
  }
};

// 处理注册
const handleRegister = async () => {
  try {
    loading.value = true;
    await registerFormRef.value.validate();

    // 调用注册API
    const res = await userRegister(registerForm);
    if (res.data.code === 0 && res.data.data) {
      message.success('注册成功');
      // 注册成功后切换到登录表单
      toggleForm(false);
      // 将注册的用户名自动填入登录表单
      loginForm.userAccount = registerForm.userAccount;
    } else {
      message.error(res.data.message || '注册失败，请稍后重试');
    }
  } catch (error) {
    console.log('注册验证失败:', error);
    message.error('注册失败，请稍后重试');
  } finally {
    loading.value = false;
  }
};

// 监听表单切换，自动聚焦第一个输入框
watch(isRegister, (newVal) => {
  setTimeout(() => {
    const firstInput = document.querySelector(newVal
      ? '.register-form .ant-input'
      : '.login-form .ant-input');
    firstInput?.focus();
  }, 300);
});
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #f6f9fc 0%, #edf2f7 100%);
  position: relative;
  overflow: hidden;
}

/* 背景装饰 */
.bg-decoration {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url("data:image/svg+xml,%3Csvg width='100' height='100' viewBox='0 0 100 100' xmlns='http://www.w3.org/2000/svg SVG namespace SVG namespace  '%3E%3Cpath d='M11 18c3.866 0 7-3.134 7-7s-3.134-7-7-7-7 3.134-7 7 3.134 7 7 7zm48 25c3.866 0 7-3.134 7-7s-3.134-7-7-7-7 3.134-7 7 3.134 7 7 7zm-43-7c1.657 0 3-1.343 3-3s-1.343-3-3-3-3 1.343-3 3 1.343 3 3 3zm63 31c1.657 0 3-1.343 3-3s-1.343-3-3-3-3 1.343-3 3 1.343 3 3 3zM34 90c1.657 0 3-1.343 3-3s-1.343-3-3-3-3 1.343-3 3 1.343 3 3 3zm56-76c1.657 0 3-1.343 3-3s-1.343-3-3-3-3 1.343-3 3 1.343 3 3 3zM12 86c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm28-65c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm23-11c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm-6 60c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm29 22c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zM32 63c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm57-13c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm-9-21c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2zM60 91c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2zM35 41c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2zM12 60c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2z' fill='%2393c5fd' fill-opacity='0.1' fill-rule='evenodd'/%3E%3C/svg%3E");
  z-index: 0;
  opacity: 0.6;
}

.bg-circles .circle {
  position: absolute;
  border-radius: 50%;
  filter: blur(60px);
}

.circle-1 {
  width: 300px;
  height: 300px;
  background: rgba(125, 211, 252, 0.15);
  top: -100px;
  left: -150px;
}

.circle-2 {
  width: 400px;
  height: 400px;
  background: rgba(251, 191, 36, 0.1);
  bottom: -200px;
  right: -100px;
}

.circle-3 {
  width: 200px;
  height: 200px;
  background: rgba(167, 139, 250, 0.1);
  top: 30%;
  right: 20%;
}

.circle-4 {
  width: 150px;
  height: 150px;
  background: rgba(74, 222, 128, 0.1);
  bottom: 20%;
  left: 10%;
}

.login-container {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 1000px;
  padding: 20px;
  display: flex;
  gap: 40px;
}

.brand-section {
  flex: 0 0 40%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding: 40px 20px;
}

.logo-container {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #0ea5e9 0%, #3b82f6 100%);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  box-shadow: 0 10px 20px rgba(59, 130, 246, 0.3);
}

.logo-icon {
  color: white;
  font-size: 32px;
  font-weight: 700;
}

.brand-name {
  font-size: 28px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 10px;
}

.brand-slogan {
  font-size: 16px;
  color: #64748b;
  margin: 0;
}

.login-box {
  flex: 0 0 50%;
  background-color: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-radius: 24px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid rgba(255, 255, 255, 0.8);
}

.title-container {
  padding: 40px 40px 20px;
  text-align: center;
}

.main-title {
  margin: 0 0 10px;
  color: #0f172a;
  font-size: 28px;
  font-weight: 700;
}

.sub-title {
  color: #64748b;
  font-size: 16px;
  margin: 0 0 25px;
}

.toggle-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-bottom: 15px;
}

.toggle-btn {
  padding: 8px 24px;
  background: transparent;
  border: none;
  border-radius: 30px;
  color: #64748b;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.toggle-btn.active {
  background: linear-gradient(135deg, #0ea5e9 0%, #3b82f6 100%);
  color: white;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.toggle-btn:hover:not(.active) {
  color: #1e293b;
  background-color: #f1f5f9;
}

.form-slider {
  display: flex;
  transition: transform 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}

.form-panel {
  width: 100%;
  padding: 0 40px 40px;
  flex-shrink: 0;
}

.form-actions {
  margin-top: 25px;
}

/* 拟态滑动效果相关样式 */
.slide-register .form-slider {
  transform: translateX(-100%);
}

/* 动画效果 */
.ant-input, .ant-input-password {
  transition: all 0.3s ease;
  border-radius: 12px;
  padding: 12px;
  border: 1px solid #e2e8f0;
  background-color: #f8fafc;
}

.ant-input:hover, .ant-input-password:hover {
  border-color: #93c5fd;
  background-color: #fff;
}

.ant-input:focus, .ant-input-password:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
  background-color: #fff;
}

.ant-btn {
  transition: all 0.3s ease;
  height: 48px;
  border-radius: 12px;
  font-weight: 600;
  font-size: 16px;
}

.ant-btn-primary {
  background: linear-gradient(135deg, #0ea5e9 0%, #3b82f6 100%);
  border: none;
  box-shadow: 0 8px 16px rgba(59, 130, 246, 0.2);
}

.ant-btn-primary:hover {
  background: linear-gradient(135deg, #0284c7 0%, #2563eb 100%);
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(59, 130, 246, 0.3);
}

/* 响应式调整 */
@media (max-width: 992px) {
  .login-container {
    flex-direction: column;
    max-width: 500px;
    gap: 20px;
  }

  .brand-section {
    align-items: center;
    text-align: center;
    padding: 20px 0;
  }
}

@media (max-width: 576px) {
  .login-box {
    border-radius: 20px;
  }

  .title-container {
    padding: 30px 20px 15px;
  }

  .form-panel {
    padding: 0 20px 30px;
  }

  .main-title {
    font-size: 24px;
  }

  .brand-name {
    font-size: 24px;
  }
}
</style>
