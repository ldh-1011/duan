<script setup lang="ts">
const props = defineProps({
  customClasses: String,
  customClassesLabel: String,
  label: String
})
</script>

<template>
  <div
    class="col-span-12 shadow rounded-md border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark xl:col-span-8"
    :class="props.customClasses"
  >
    <!-- 🔹 Thanh tiêu đề -->
    <div
      v-if="props.label"
      class="bg-selectedHeader text-gray-700 dark:bg-strokedark dark:text-white px-5 py-3 rounded-t-md text-base font-semibold border-b border-stroke dark:border-strokedark"
      :class="props.customClassesLabel"
    >
      <div class="flex items-center gap-2 leading-none">
        <!-- ✅ Icon slot -->
        <div class="text-lg flex items-center">
          <slot name="icon" />
        </div>
        <span class="text-base font-semibold mt-0.5">
          {{ props.label }}
        </span>
      </div>
    </div>

    <!-- 🔳 Nội dung chính -->
    <div class="px-10 pt-6 pb-10 sm:px-7.5">
      <!-- 👉 Slot extra content -->
      <div class="flex justify-end mb-3">
        <slot name="extra" />
      </div>

      <!-- 📄 Main content -->
      <slot />
    </div>
  </div>
</template>
