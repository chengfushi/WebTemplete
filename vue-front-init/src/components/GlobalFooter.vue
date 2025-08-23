<template>
  <a-layout-footer class="footer">
    <div class="footer-container">
      <div class="footer-section">
        <h4>Web开发模板</h4>
        <p>专业的Web开发模板，为您提供智能化的解决方案。</p>

        <div class="social-links">
          <a href="https://github.com/chengfushi" target="_blank">
            <github-outlined />
          </a>
          <a href="https://chengfushi.blog.csdn.net/" target="_blank">
            <book-outlined />
          </a>
        </div>
      </div>

      <!-- 产品服务 -->
      <div class="footer-section">
        <h4>产品服务</h4>
        <p>前后端分离</p>
        <p>网页开发模板</p>
        <p>API接口</p>
      </div>

      <!-- 帮助与支持 -->
      <div class="footer-section">
        <h4>帮助与支持</h4>
        <p><a href="mailto:303623518@qq.com">303623518@qq.com</a></p>
      </div>

      <!-- 贡献者 -->
      <div class="footer-section">
        <h4>贡献者</h4>
        <p>
          <a href="https://github.com/chengfushi" target="_blank">Chengfu</a>
        </p>
        <p>
          <a href="https://github.com/camellialoveit" target="_blank">Yike</a>
        </p>
      </div>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
import { GithubOutlined, BookOutlined } from '@ant-design/icons-vue'
</script>

<style scoped>
.footer {
  background: #1e1e1e;
  color: #d9d9d9;
  padding: 40px 20px 15px;
  border-top: 1px solid #333;
}

.footer-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  max-width: 1200px;
  margin: 0 auto;
  gap: 32px;
}

.footer-section {
  flex: 1 1 260px;
  text-align: left;
}

.footer-section h4 {
  color: #ffffff;
  font-size: 18px;
  margin-bottom: 12px;
  letter-spacing: 0.5px;
}

.footer-section p {
  font-size: 14px;
  line-height: 1.8;
  margin: 0 0 6px;
}

.footer-section a {
  color: #a3a3a3;
  text-decoration: none;
  transition: color 0.2s;
}

.footer-section a:hover {
  color: #ffffff;
  text-decoration: underline;
}

/* 社交图标 */
.social-links {
  margin-top: 10px;
}

.social-links a {
  margin-right: 12px;
  color: #a3a3a3;
  font-size: 20px;
  transition:
    color 0.2s,
    transform 0.2s;
}

.social-links a:hover {
  color: #ffffff;
  transform: scale(1.15);
}
</style>
