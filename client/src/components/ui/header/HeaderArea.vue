<script setup lang="ts">
import { useSidebarStore } from '@/stores/sidebar.ts'
import { computed, defineProps } from 'vue'
import DropdownUser from './DropdownUser.vue'
import Notification from './Notification.vue'

const { toggleSidebar } = useSidebarStore()
const sidebarStore = useSidebarStore()

const props = defineProps({
  isNoSidebarPage: Boolean
})

const headerStyle = computed(() => {
  return props.isNoSidebarPage
    ? {
      backgroundImage:
        "url('https://tse1.mm.bing.net/th?id=OIP.vz3cycLecmyDwRm64tRxUgHaHa&pid=Api&P=0&h=180')"
    }
    : { backgroundColor: 'white' }
})
</script>

<template>
  <header
    :class="[
      'sticky top-0 z-20 flex w-full drop-shadow-1 ',
      props.isNoSidebarPage ? 'bg-[#00112f]' : 'bg-white dark:bg-boxdark dark:drop-shadow-none'
    ]"
    :style="headerStyle"
  >
    <div class="flex flex-grow items-center justify-between py-2 px-4 shadow-2 md:px-4 2xl:px-8">
      <div class="flex items-center gap-2 sm:gap-4 lg:hidden">
        <!-- Hamburger Toggle BTN -->
        <button
          class="z-99999 block rounded-sm border border-stroke bg-white p-1 shadow-sm dark:border-strokedark dark:bg-boxdark lg:hidden"
          @click="toggleSidebar"
        >
          <span class="relative block h-4.5 w-4.5 cursor-pointer">
            <span class="block absolute right-0 h-full w-full">
              <span
                class="relative top-0 left-0 my-0.5 block h-0.5 w-0 rounded-sm bg-black delay-[0] duration-200 ease-in-out dark:bg-white"
                :class="{ '!w-full delay-300': !sidebarStore.isSidebarOpen }"
              ></span>
              <span
                class="relative top-0 left-0 my-0.5 block h-0.5 w-0 rounded-sm bg-black delay-150 duration-200 ease-in-out dark:bg-white"
                :class="{ '!w-full delay-400': !sidebarStore.isSidebarOpen }"
              ></span>
              <span
                class="relative top-0 left-0 my-0.5 block h-0.5 w-0 rounded-sm bg-black delay-200 duration-200 ease-in-out dark:bg-white"
                :class="{ '!w-full delay-500': !sidebarStore.isSidebarOpen }"
              ></span>
            </span>
            <span class="block absolute right-0 h-full w-full rotate-45">
              <span
                class="absolute left-2 top-0 block h-full w-0.5 rounded-sm bg-black delay-300 duration-200 ease-in-out dark:bg-white"
                :class="{ '!h-0 delay-[0]': !sidebarStore.isSidebarOpen }"
              ></span>
              <span
                class="delay-400 absolute left-0 top-2 block h-0.5 w-full rounded-sm bg-black duration-200 ease-in-out dark:bg-white"
                :class="{ '!h-0 delay-200': !sidebarStore.isSidebarOpen }"
              ></span>
            </span>
          </span>
        </button>
      </div>
      <div class="hidden sm:block"></div>

      <div class="flex items-center gap-2 2xsm:gap-4">
        <ul class="flex items-center gap-1 2xsm:gap-3">
          <!-- Notification Menu Area -->
        </ul>

        <Notification />

        <!-- User Area -->
        <DropdownUser :isNoSidebarPage="props.isNoSidebarPage" />
        <!-- User Area -->
      </div>
    </div>
  </header>
</template>