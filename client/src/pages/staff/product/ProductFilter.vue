<template>
  <DivCustom label="Bộ lọc sản phẩm">
    <template #icon>
      <FilterOutlined />
    </template>

    <div class="w-full grid grid-cols-1 md:grid-cols-[1fr_1fr_auto] gap-4 items-center">
      <!-- Ô nhập liệu tìm kiếm -->
      <a-input
          v-model:value="localKeyword"
          placeholder="Nhập mã hoặc tên sản phẩm..."
          allow-clear
          class="w-full"
      />

      <!-- Chọn trạng thái -->
      <a-select
          v-model:value="localStatus"
          placeholder="Chọn trạng thái"
          allow-clear
          class="w-full"
      >
        <a-select-option
            v-for="item in statusOptions"
            :key="item.value"
            :value="item.value"
        >
          {{ item.label }}
        </a-select-option>
      </a-select>

      <a-tooltip title="Làm mới bộ lọc">
        <a-button
            @click="resetFilters"
            class="p-3 flex items-center justify-center min-w-[100px]"
            style="
            background-color: #e6f4ff !important;
            color: #183153 !important;
            border: none !important;
            font-weight: 500 !important;
          "
        >
          <ReloadOutlined />
          <span class="ml-2">Làm mới</span>
        </a-button>
      </a-tooltip>
    </div>
  </DivCustom>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import DivCustom from '@/components/custom/Div/DivCustom.vue'
import { FilterOutlined, ReloadOutlined } from '@ant-design/icons-vue'

const props = defineProps<{ keyword: string; status: string }>()
const emit = defineEmits<{
  (e: 'update:keyword', value: string): void
  (e: 'update:status', value: string): void
}>()

const localKeyword = ref(props.keyword || '')
const localStatus = ref<string | null>(props.status || null)

const statusOptions = [
  { label: 'Đang bán', value: '0' },
  { label: 'Ngừng kinh doanh', value: '1' }
]

watch(localKeyword, (val) => emit('update:keyword', val))
watch(localStatus, (val) => emit('update:status', val ?? ''))

const resetFilters = () => {
  localKeyword.value = ''
  localStatus.value = ''
  emit('update:keyword', '')
  emit('update:status', '')
}
</script>
