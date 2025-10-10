<template>
  <div class="relative">
    <!-- Nút thông báo -->
    <a-badge :count="unreadNotifications.length">
      <a-dropdown
        placement="bottomRight"
        :trigger="['click']"
        :visible="showNotifications"
        @visibleChange="toggleNotifications"
      >
        <template #overlay>
          <div class="w-72 bg-white border border-gray-200 rounded-lg shadow-lg overflow-hidden">
            <!-- Header Thông báo -->
            <div class="p-3 border-b flex items-center justify-between">
              <span class="font-semibold text-gray-700">Thông báo</span>
              <div class="flex items-center space-x-2">
                <span class="text-sm text-gray-600">Chỉ hiển thị chưa đọc</span>
                <a-switch v-model:checked="showOnlyUnread" />
              </div>
            </div>

            <!-- Danh sách thông báo -->
            <div class="py-2 text-gray-700 text-sm max-h-60 overflow-y-auto">
              <a-list
                v-if="filteredNotifications.length > 0"
                :data-source="filteredNotifications"
              >
                <template #renderItem="{ item }">
                  <a-list-item
                    class="px-4 py-3 border-b hover:bg-gray-100 transition"
                    @click="handleNotificationClick(item)"
                  >
                    <div>
                      <p class="font-medium">{{ item.content }}</p>
                      <p class="text-gray-500 text-xs">
                        {{ getDateFormat(item.createdDate, true) }}
                      </p>
                    </div>
                  </a-list-item>
                </template>
              </a-list>
              <p v-else class="text-center text-gray-500 py-3">
                Không có thông báo nào
              </p>
            </div>
          </div>
        </template>

        <a-button
          type="text"
          class="notification-button flex items-center justify-center"
          @click.prevent="toggleNotifications"
        >
          <BellOutlined class="notification-icon" />
        </a-button>
      </a-dropdown>
    </a-badge>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { BellOutlined } from '@ant-design/icons-vue'
import { ROLES } from '@/constants/roles'
import { computed, ref } from 'vue'

// Giả lập thông tin user
const userLogin = {
  userId: 1,
  roleScreen: ROLES.MEMBER
}

const router = useRouter()
const showNotifications = ref(false)
const showOnlyUnread = ref(false)

// Mock data thông báo
const notificationsMember = ref([
  { id: 1, content: 'Bạn có nhiệm vụ mới', createdDate: new Date(), status: 0, url: '/tasks/1' },
  { id: 2, content: 'Hệ thống sẽ bảo trì lúc 22h', createdDate: new Date(), status: 1, url: '/news/22h' },
  { id: 3, content: 'Bạn được phân công vào dự án A', createdDate: new Date(), status: 0, url: '/projects/a' }
])

// computed
const unreadNotifications = computed(() =>
  notificationsMember.value.filter((noti) => noti.status === 0)
)
const filteredNotifications = computed(() =>
  showOnlyUnread.value ? unreadNotifications.value : notificationsMember.value
)

// click thông báo
const handleNotificationClick = (notification: any) => {
  if (notification.status === 0) {
    // đánh dấu đã đọc (mock)
    notification.status = 1
  }

  if (userLogin.roleScreen == ROLES.ADMIN) {
    router.push('/manage' + notification.url)
  } else if (userLogin.roleScreen == ROLES.SUPER_ADMIN) {
    router.push('/manage' + notification.url)
  } else if (userLogin.roleScreen == ROLES.MEMBER) {
    router.push('/member' + notification.url)
  }
}

const toggleNotifications = (visible?: boolean) => {
  showNotifications.value = visible !== undefined ? visible : !showNotifications.value
}

// format ngày đơn giản
const getDateFormat = (date: Date, short = false) => {
  const d = new Date(date)
  return short ? d.toLocaleString() : d.toLocaleDateString()
}
</script>

<style scoped>
.notification-button {
  width: 42px;
  height: 42px;
  border-radius: 50%;
  background-color: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s;
}
.notification-button:hover {
  background-color: #e0e0e0;
}
.notification-icon {
  font-size: 20px;
  color: #ffffff;
}
</style>
