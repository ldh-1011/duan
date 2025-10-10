<template>
  <div
      class="min-h-screen bg-cover bg-center flex justify-center items-center px-4 sm:px-6 lg:px-8 relative"
      :style="{ backgroundImage: `url(/images/bg-simple.jpg)` }"
  >
    <!-- Overlay -->
    <div class="absolute inset-0 bg-black/40 backdrop-blur-sm"></div>

    <div
        class="relative bg-white/90 backdrop-blur-lg shadow-2xl rounded-3xl p-8 w-full max-w-md flex flex-col items-center gap-8"
    >
      <!-- Logo -->
      <div class="flex flex-col sm:flex-row justify-center items-center gap-6">
        <img src="/src/assets/images/anh1.png" alt="Logo FPT" class="w-28 sm:w-36" />
        <img src="/src/assets/images/anh2.png" alt="Logo UDPM" class="w-28 sm:w-36" />
      </div>

      <!-- Title -->
      <h1 class="text-2xl sm:text-3xl font-bold text-selectedLogin tracking-wide text-center">
        ĐĂNG NHẬP HỆ THỐNG CONFESSION
      </h1>

      <!-- Login Form -->
      <form
          @submit.prevent="handleLogin"
          class="w-full flex flex-col gap-4 max-w-sm"
      >
        <div>
          <label for="email" class="block text-sm font-medium text-gray-700 mb-1">Email</label>
          <input
              v-model="form.email"
              id="email"
              type="email"
              required
              placeholder="Nhập email..."
              class="w-full rounded-xl border px-4 py-3 focus:ring-2 focus:ring-orange-500 outline-none"
          />
        </div>

        <div>
          <label for="password" class="block text-sm font-medium text-gray-700 mb-1">Mật khẩu</label>
          <input
              v-model="form.password"
              id="password"
              type="password"
              required
              placeholder="Nhập mật khẩu..."
              class="w-full rounded-xl border px-4 py-3 focus:ring-2 focus:ring-orange-500 outline-none"
          />
        </div>

        <button
            type="submit"
            :disabled="loading"
            class="w-full bg-orange-500 text-white font-semibold py-3 rounded-xl hover:bg-orange-600 active:scale-95 transition disabled:opacity-70"
        >
          <span v-if="!loading">Đăng nhập</span>
          <span v-else>Đang xử lý...</span>
        </button>
      </form>

      <!-- Footer -->
      <div
          class="mt-6 text-center flex flex-col sm:flex-row items-center justify-center gap-2 text-gray-700 text-sm"
      >
        <span>Powered by</span>
        <span class="font-bold text-lg text-selectedLogin"></span>
        <img src="/src/assets/images/anh3.png" alt="logo" class="h-8" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/services/api/staff/login.api.ts'
import { ROUTES_CONSTANTS } from '@/constants/path'
import { localStorageAction } from '@/utils/storage'
import {
  ACCESS_TOKEN_STORAGE_KEY,
  USER_INFO_STORAGE_KEY
} from '@/constants/storagekey'

const router = useRouter()
const loading = ref(false)

const form = ref({
  email: '',
  password: ''
})

const handleLogin = async () => {
  try {
    loading.value = true
    const res = await login({
      email: form.value.email.trim(),
      password: form.value.password
    })

    const data = res.data
    if (data && data.token) {
      // ✅ Lưu token và thông tin user
      localStorageAction.set(ACCESS_TOKEN_STORAGE_KEY, data.token)
      localStorageAction.set(USER_INFO_STORAGE_KEY, {
        email: data.email,
        role: data.role
      })

      // ✅ Điều hướng dựa theo vai trò
      if (data.role === 'ADMIN') {
        router.push({ name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.CATEGORIES.name })
      } else if (data.role === 'STAFF') {
        router.push({ name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.CATEGORIES.name })
      } else {
        router.push('/')
      }
    } else {
      alert('Sai tài khoản hoặc mật khẩu!')
    }
  } catch (err: any) {
    console.error(err)
    alert('Đăng nhập thất bại. Vui lòng kiểm tra lại!')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.text-selectedLogin {
  color: #ff6600;
}
</style>
