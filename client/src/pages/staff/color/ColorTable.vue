<template>
  <DivCustom label="Danh sách màu" customClasses="mt-5">
    <template #icon>
      <IdcardOutlined />
    </template>

    <template #extra>
      <a-tooltip title="Thêm mới màu">
        <a-button
          type="primary"
          @click="openAddModal"
          class="flex items-center justify-center px-4"
        >
          Thêm Màu
        </a-button>
      </a-tooltip>
    </template>

    <div class="min-h-[360px]">
      <a-table
        :columns="columns"
        :data-source="data"
        bordered
        :loading="loading"
        :pagination="{
          current: page,
          pageSize: size,
          total: total,
          showSizeChanger: true,
          pageSizeOptions: ['10', '20', '30']
        }"
        :scroll="{ x: 500 }"
        @change="handlePageChange"
        row-key="id"
      >
        <template #bodyCell="{ column, record, index }">
          <template v-if="column.key === 'orderNumber'">
            {{ (page - 1) * size + index + 1 }}
          </template>

          <template v-if="column.key === 'operation'">
            <div class="flex items-center gap-2 justify-center">
              <a-tooltip title="Sửa">
                <a-button
                  type="default"
                  class="flex items-center justify-center w-8 h-8"
                  style="background-color: #e0f2fe; color: #0284c7; border: none;"
                  @click="openEditModal(record.id)"
                >
                  <FormOutlined />
                </a-button>
              </a-tooltip>

              <a-tooltip title="Xóa">
                <a-popconfirm
                  title="Bạn có chắc muốn xóa màu này?"
                  ok-text="Xóa"
                  cancel-text="Hủy"
                  @confirm="() => handleDelete(record.id)"
                >
                  <a-button
                    danger
                    class="flex items-center justify-center w-8 h-8"
                    style="background-color: #fee2e2; color: #dc2626; border: none;"
                  >
                    <DeleteFilled />
                  </a-button>
                </a-popconfirm>
              </a-tooltip>
            </div>
          </template>
        </template>
      </a-table>
    </div>
  </DivCustom>

  <!-- Modal Thêm/Sửa -->
  <ColorModal
    v-if="modalOpen"
    :open="modalOpen"
    :color-id="selectedColorId"
    :title="modalTitle"
    @close="closeModal"
    @success="handleSuccess"
  />
</template>

<script setup lang="ts">
import DivCustom from '@/components/custom/Div/DivCustom.vue'
import { IdcardOutlined, FormOutlined, DeleteFilled } from '@ant-design/icons-vue'
import { ref, onMounted } from 'vue'
import { getAllColor, deleteColor } from '@/services/api/staff/color.api.ts'
import { toast } from 'vue3-toastify'
import ColorModal from './ColorModal.vue'

const loading = ref(false)
const page = ref(1)
const size = ref(10)
const total = ref(0)
const data = ref<any[]>([])

const modalOpen = ref(false)
const selectedColorId = ref<string | null>(null)
const modalTitle = ref('Thêm màu')

const columns = [
  { title: 'STT', key: 'orderNumber', align: 'center', width: 70 },
  { title: 'Tên màu', key: 'colorName', dataIndex: 'colorName', align: 'center' },
  { title: 'Hành động', key: 'operation', align: 'center', width: 120 }
]

const fetchColors = async () => {
  loading.value = true
  try {
    const res = await getAllColor()
    if (res.data.success) {
      data.value = res.data.data?.content || res.data.data
      total.value = res.data.data?.totalElements || res.data.data.length
    } else {
      toast.error(res.data.message || 'Không thể tải danh sách màu')
    }
  } catch (err) {
    toast.error('Lỗi khi tải danh sách màu!')
    console.error(err)
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: string) => {
  try {
    const res = await deleteColor(id)
    if (res.data.success) {
      toast.success('Xóa màu thành công!')
      await fetchColors()
    } else {
      toast.error(res.data.message || 'Xóa thất bại!')
    }
  } catch (err) {
    toast.error('Lỗi khi xóa màu!')
    console.error(err)
  }
}

const handlePageChange = (pagination: any) => {
  page.value = pagination.current
  size.value = pagination.pageSize
  fetchColors()
}

const openAddModal = () => {
  selectedColorId.value = null
  modalTitle.value = 'Thêm màu'
  modalOpen.value = true
}

const openEditModal = (id: string) => {
  selectedColorId.value = id
  modalTitle.value = 'Cập nhật màu'
  modalOpen.value = true
}

const closeModal = () => {
  modalOpen.value = false
}

const handleSuccess = async () => {
  console.log('✅ handleSuccess gọi lại')
  closeModal()
  await fetchColors()
}

onMounted(fetchColors)
</script>
