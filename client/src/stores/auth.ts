import {
  ACCESS_TOKEN_STORAGE_KEY,
  REFRESH_TOKEN_STORAGE_KEY,
  USER_INFO_STORAGE_KEY
} from '@/constants/storagekey'
import type { UserInformation } from '@/types/auth.type'
import { localStorageAction } from '@/utils/storage'
import { defineStore } from 'pinia'
import { getExpireTime, getUserInformation } from '@/utils/token.helper'
import { computed, ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const user = ref<UserInformation | null>(
      localStorageAction.get(USER_INFO_STORAGE_KEY, null)
  )

  const accessToken = ref<string | null>(
      localStorage.getItem(ACCESS_TOKEN_STORAGE_KEY) || null
  )
  const refreshToken = ref<string | null>(
      localStorage.getItem(REFRESH_TOKEN_STORAGE_KEY) || null
  )

  const isAuthenticated = computed(() => {
    if (!accessToken.value) return false
    try {
      const expire = getExpireTime(accessToken.value)
      return Date.now() < expire * 1000
    } catch {
      return false
    }
  })

  const login = (tokenData: { email: string; role: string; token: string }) => {
    const userData = getUserInformation(tokenData.token)

    user.value = userData
    accessToken.value = tokenData.token
    refreshToken.value = null

    localStorageAction.set(USER_INFO_STORAGE_KEY, userData)
    localStorage.setItem(ACCESS_TOKEN_STORAGE_KEY, tokenData.token)

    console.log('✅ User Login:', userData)
  }

  const logout = () => {
    user.value = null
    accessToken.value = null
    refreshToken.value = null
    localStorageAction.remove(USER_INFO_STORAGE_KEY)
    localStorage.removeItem(ACCESS_TOKEN_STORAGE_KEY)
    localStorage.removeItem(REFRESH_TOKEN_STORAGE_KEY)
  }

  return { user, isAuthenticated, login, logout, accessToken, refreshToken }
})
