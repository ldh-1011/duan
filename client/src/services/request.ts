import axios from 'axios'
import { API_URL } from '@/constants/url'
import { ROUTES_CONSTANTS } from '@/constants/path'
import {
  ACCESS_TOKEN_STORAGE_KEY,
  USER_INFO_STORAGE_KEY
} from '@/constants/storagekey'
import { localStorageAction } from '@/utils/storage'

// ⚙️ Khởi tạo axios instance
const request = axios.create({
  baseURL: API_URL,
  timeout: 10000 // 10s timeout
})

// 🟢 Thêm Authorization header nếu có token
request.interceptors.request.use((config) => {
  const token = localStorageAction.get(ACCESS_TOKEN_STORAGE_KEY)
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

// 🔴 Xử lý lỗi toàn cục (401, 403,...)
request.interceptors.response.use(
    (response) => response,
    async (error) => {
      const originalRequest = error.config
      const { response } = error

      if (!response) {
        console.error('🚨 Lỗi mạng hoặc server không phản hồi')
        return Promise.reject(error)
      }

      if (
          response.status === 401 &&
          !originalRequest._retry &&
          window.location.pathname !== ROUTES_CONSTANTS.LOGIN.path
      ) {
        localStorageAction.remove(ACCESS_TOKEN_STORAGE_KEY)
        localStorageAction.remove(USER_INFO_STORAGE_KEY)
        window.location.href = ROUTES_CONSTANTS.LOGIN.path
        return Promise.reject(error)
      }

      if (
          response.status === 403 &&
          window.location.pathname !== ROUTES_CONSTANTS.LOGIN.path
      ) {
        console.error('🚫 Lỗi 403 – Không đủ quyền:', response.data)
        window.location.href = ROUTES_CONSTANTS.FORBIDDEN.path
        return Promise.reject(error)
      }

      return Promise.reject(error)
    }
)

export default request
