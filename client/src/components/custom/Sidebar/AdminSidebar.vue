<script setup lang="ts">
import { ref, computed } from 'vue'
import { useSidebarStore } from '@/stores/sidebar'
import { useAuthStore } from '@/stores/auth'
import { useRoute, useRouter } from 'vue-router'
import { ROUTES_CONSTANTS } from '@/constants/path'
import { DoubleLeftOutlined } from '@ant-design/icons-vue'

const sidebarStore = useSidebarStore()
const authStore = useAuthStore()
const route = useRoute()
const router = useRouter()

// state
const isCollapsed = ref(false)
const isMobileMenuOpen = ref(false)

// ✅ mở sẵn “Quản lý hàng hóa” và “Thuộc tính”
const openParentLevel1 = ref<string>('Quản lý hàng hóa')
const openParentLevel2 = ref<string>('Thuộc tính')

const toggleMenu = (menuItem: any, level = 1) => {
  if (menuItem.children) {
    if (level === 1) {
      openParentLevel1.value =
          openParentLevel1.value === menuItem.label ? null : menuItem.label
      openParentLevel2.value = null
    } else if (level === 2) {
      openParentLevel2.value =
          openParentLevel2.value === menuItem.label ? null : menuItem.label
    }
    return
  }

  if (menuItem.routeName) {
    router.push({ name: menuItem.routeName }).catch(() => {})
    isMobileMenuOpen.value = false
  }
}

// ✅ Danh sách menu
const menuItems = computed(() => [
  {
    label: 'Quản lý hàng hóa',
    icon: 'fa-solid fa-boxes-stacked',
    children: [
      {
        label: 'Danh mục',
        routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.CATEGORIES.name,
        icon: 'fa-solid fa-list'
      },
      {
        label: 'Sản phẩm',
        routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PRODUCT.name,
        icon: 'fa-solid fa-mobile-screen'
      },
      {
        label: 'Chi tiết sản phẩm',
        routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PRODUCT_DETAIL.name,
        icon: 'fa-solid fa-circle-info'
      },
      {
        label: 'Thuộc tính',
        icon: 'fa-solid fa-clipboard-list',
        children: [
          {
            label: 'Màu sắc',
            icon: 'fa-solid fa-palette',
            routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.COLOR.name
          },
          {
            label: 'Độ phân giải',
            icon: 'fa-solid fa-image',
            routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.RESOLUTION.name
          },
          {
            label: 'Chip xử lý',
            icon: 'fa-solid fa-microchip',
            routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.PROCESSOR.name
          },
          {
            label: 'Phiên bản',
            icon: 'fa-solid fa-layer-group',
            routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.VERSION.name
          },
          {
            label: 'Cảm biến',
            icon: 'fa-solid fa-bullseye',
            routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.SENSOR.name
          },
          {
            label: 'ISO',
            icon: 'fa-solid fa-camera',
            routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.ISO.name
          },
          {
            label: 'Dung lượng lưu trữ',
            icon: 'fa-solid fa-database',
            routeName: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.STORAGE_CAPACITY.name
          }
        ]
      }
    ]
  }
])
</script>

<template>
  <!-- Nút mở menu Mobile -->
  <button
      v-if="!isMobileMenuOpen"
      @click="isMobileMenuOpen = true"
      class="fixed top-4 left-4 z-50 flex items-center justify-center w-10 h-10
      rounded-full bg-gray-800 hover:bg-gray-700 text-white
      shadow-lg transition transform hover:scale-105 active:scale-95 lg:hidden"
  >
    <svg class="w-6 h-6" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
      <path stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 12h16M4 18h16" />
    </svg>
  </button>

  <!-- Overlay cho mobile -->
  <div
      v-if="isMobileMenuOpen"
      @click="isMobileMenuOpen = false"
      class="fixed inset-0 bg-black/40 backdrop-blur-sm z-30 lg:hidden"
  ></div>

  <!-- SIDEBAR -->
  <aside
      class="fixed inset-y-0 left-0 z-40 flex flex-col bg-white shadow-xl border-r
      transition-all duration-300 ease-in-out lg:relative"
      :class="{
      'hidden lg:flex': !isMobileMenuOpen && isCollapsed,
      'translate-x-0 w-[80%] max-w-xs': isMobileMenuOpen,
      'w-20': isCollapsed && !isMobileMenuOpen,
      'w-64': !isCollapsed && !isMobileMenuOpen
    }"
  >
    <!-- Logo & Toggle -->
    <div class="flex items-center justify-between px-4 py-4 border-b">
      <img
          src="@/assets/images/anh1.png"
          alt="Logo"
          class="transition-all duration-300"
          :class="{ 'opacity-0 w-0': isCollapsed }"
          style="width: 160px; height: auto"
      />

      <button
          v-if="!isMobileMenuOpen"
          @click="isCollapsed = !isCollapsed"
          class="hidden lg:flex items-center justify-center w-9 h-9
          rounded-full hover:bg-gray-200 transition"
      >
        <DoubleLeftOutlined
            :style="{ transform: isCollapsed ? 'rotate(180deg)' : 'rotate(0deg)', transition: '0.3s' }"
        />
      </button>

      <button
          v-if="isMobileMenuOpen"
          @click="isMobileMenuOpen = false"
          class="lg:hidden flex items-center justify-center w-8 h-8"
      >
        <svg class="w-6 h-6 text-gray-700" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
          <path stroke-linecap="round" stroke-linejoin="round" d="M15 19l-7-7 7-7" />
        </svg>
      </button>
    </div>

    <!-- Menu -->
    <div class="flex-1 overflow-y-auto px-3 py-4">
      <nav>
        <ul class="space-y-1">
          <li v-for="(menuItem, index) in menuItems" :key="index">
            <template v-if="menuItem.children">
              <!-- menu cấp 1 -->
              <div
                  class="flex items-center gap-3 px-3 py-2 font-medium text-gray-700 cursor-pointer hover:bg-gray-100 rounded-md"
                  :class="{ 'justify-center': isCollapsed }"
                  @click="toggleMenu(menuItem, 1)"
              >
                <i :class="[menuItem.icon]" class="w-5 h-5"></i>
                <span v-if="!isCollapsed">{{ menuItem.label }}</span>
              </div>

              <!-- cấp 2 -->
              <ul v-if="!isCollapsed && openParentLevel1 === menuItem.label" class="ml-6 space-y-1">
                <li v-for="(child, cIdx) in menuItem.children" :key="cIdx">
                  <template v-if="child.children">
                    <!-- có cấp 3 -->
                    <div
                        class="flex items-center gap-2 px-3 py-1.5 cursor-pointer rounded-md hover:bg-gray-100"
                        @click="toggleMenu(child, 2)"
                    >
                      <i :class="[child.icon]" class="w-4 h-4"></i>
                      <span>{{ child.label }}</span>
                    </div>

                    <ul v-if="openParentLevel2 === child.label" class="ml-5 space-y-1">
                      <li v-for="(grand, gIdx) in child.children" :key="gIdx">
                        <router-link
                            :to="{ name: grand.routeName }"
                            class="flex items-center gap-2 px-3 py-1.5 rounded-md text-sm"
                            :class="{
                            'bg-blue-50 text-blue-600': route.name === grand.routeName,
                            'text-gray-600 hover:bg-gray-100 hover:text-blue-600': route.name !== grand.routeName
                          }"
                        >
                          <i :class="[grand.icon]" class="w-4 h-4"></i>
                          <span>{{ grand.label }}</span>
                        </router-link>
                      </li>
                    </ul>
                  </template>

                  <template v-else>
                    <!-- cấp 2 bình thường -->
                    <router-link
                        :to="{ name: child.routeName }"
                        class="flex items-center gap-2 px-3 py-1.5 rounded-md text-sm"
                        :class="{
                        'bg-blue-50 text-blue-600': route.name === child.routeName,
                        'text-gray-600 hover:bg-gray-100 hover:text-blue-600': route.name !== child.routeName
                      }"
                    >
                      <i :class="[child.icon]" class="w-4 h-4"></i>
                      <span>{{ child.label }}</span>
                    </router-link>
                  </template>
                </li>
              </ul>
            </template>

            <template v-else>
              <!-- menu không có children -->
              <router-link
                  :to="{ name: menuItem.routeName }"
                  class="flex items-center gap-3 px-3 py-2 rounded-lg font-medium cursor-pointer"
                  :class="{
                  'bg-blue-50 text-blue-600': route.name === menuItem.routeName,
                  'text-gray-600 hover:bg-gray-100 hover:text-blue-600': route.name !== menuItem.routeName
                }"
              >
                <i :class="[menuItem.icon]" class="w-5 h-5"></i>
                <span v-if="!isCollapsed" class="text-sm truncate">{{ menuItem.label }}</span>
              </router-link>
            </template>
          </li>
        </ul>
      </nav>
    </div>
  </aside>
</template>

<style scoped>
aside {
  transition: transform 0.3s ease-in-out;
}
</style>
