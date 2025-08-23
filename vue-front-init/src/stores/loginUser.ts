import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getLoginUser } from '@/api/userController.ts'

export const useLoginUserStore = defineStore('loginUser', () => {
  // 从localStorage获取用户信息或使用默认值
  const storedUser = localStorage.getItem('loginUser')
  const defaultUser = {
    userName: '未登录',
  }

  // 默认值
  const loginUser = ref<API.LoginUserVO>(
    storedUser ? JSON.parse(storedUser) : defaultUser
  )

  // 获取登录用户信息
  async function fetchLoginUser() {
    const res = await getLoginUser()
    if (res.data.code === 0 && res.data.data) {
      loginUser.value = res.data.data
      // 保存到localStorage
      localStorage.setItem('loginUser', JSON.stringify(res.data.data))
    }
  }

  // 更新登录用户信息
  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser
    // 保存到localStorage
    localStorage.setItem('loginUser', JSON.stringify(newLoginUser))
  }

  // 清除登录信息
  function clearLoginUser() {
    loginUser.value = defaultUser
    localStorage.removeItem('loginUser')
  }

  return { loginUser, setLoginUser, fetchLoginUser, clearLoginUser }
})
