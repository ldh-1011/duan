<script setup lang="ts">
import { ROLES } from '@/constants/roles'
import { useAuthStore } from '@/stores/auth'
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { Dropdown, Menu, Avatar, Space } from 'ant-design-vue'
import { UserOutlined, LogoutOutlined, IdcardOutlined } from '@ant-design/icons-vue'
import ModalProfile from './ModalProfile.vue'
import { getUserInformation } from '@/utils/jwt'

const router = useRouter()
const isModalVisible = ref(false)
const userAuthStore = useAuthStore()

// Thông tin người dùng sau khi decode JWT
const userInfor = ref({
  email: '',
  role: ''
})

onMounted(() => {
  const token = userAuthStore.token
  if (token) {
    const decoded = getUserInformation(token)
    userInfor.value.email = decoded.email
    userInfor.value.role = decoded.role
  }
})

const handleLogout = () => {
  userAuthStore.logout()
  router.push({ name: 'login' })
}

const showProfileModal = () => {
  isModalVisible.value = true
}

// Hiển thị role rõ ràng hơn
const showRoleUser = computed(() => {
  switch (userInfor.value.role) {
    case ROLES.ADMIN:
      return 'Quản trị viên'
    case ROLES.STAFF:
      return 'Nhân viên'
    case ROLES.CUSTOMER:
      return 'Khách hàng'
    default:
      return 'Không xác định'
  }
})
</script>

<template>
  <Dropdown>
    <template #overlay>
      <Menu>
        <Menu.Item>
          <UserOutlined /> Vai trò: {{ showRoleUser }}
        </Menu.Item>

        <Menu.Item @click="showProfileModal">
          <IdcardOutlined /> Thông tin cá nhân
        </Menu.Item>

        <Menu.Item @click="handleLogout">
          <LogoutOutlined /> Đăng xuất
        </Menu.Item>
      </Menu>
    </template>

    <a class="flex items-center gap-3 cursor-pointer">
      <Avatar :size="40">{{ userInfor.email.charAt(0).toUpperCase() }}</Avatar>
      <Space direction="vertical" size="small" class="hidden lg:block">
        <span class="text-sm font-medium">{{ userInfor.email }}</span>
        <span class="text-xs text-gray-500">{{ showRoleUser }}</span>
      </Space>
    </a>
  </Dropdown>

  <!-- Modal hiển thị thông tin cá nhân -->
  <ModalProfile v-model:visible="isModalVisible" :userInfor="userInfor" />
</template>

<style scoped>
a {
  display: flex;
  align-items: center;
}
</style>
