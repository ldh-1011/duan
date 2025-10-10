<script setup lang="ts">
import HeaderArea from '@/components/ui/header/HeaderArea.vue'
import AdminSidebar from '@/components/custom/Sidebar/AdminSidebar.vue'
</script>

<template>
  <!-- Wrapper -->
  <div class="flex h-screen bg-gray-50 dark:bg-gray-900 text-gray-800 dark:text-gray-200">
    <!-- Sidebar -->
    <AdminSidebar />

    <!-- Content Area -->
    <div class="flex flex-1 flex-col overflow-hidden">
      <!-- Header -->
      <HeaderArea />

      <!-- Main Content -->
      <main class="flex-1 overflow-y-auto p-4 md:p-6 bg-gray-100 dark:bg-gray-800">
        <div class="max-w-7xl mx-auto space-y-6">
          <router-view />
        </div>
      </main>
    </div>
  </div>
</template>
