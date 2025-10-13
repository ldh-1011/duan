<template>
  <DivCustom label="Danh sách cảm biến" customClasses="mt-5">
    <template #icon>
      <IdcardOutlined />
    </template>

    <template #extra>
      <a-tooltip title="Thêm mới cảm biến">
        <a-button
          type="primary"
          @click="openAddModal"
          class="flex items-center justify-center px-4"
        >
          Thêm cảm biến
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
                  title="Bạn có chắc muốn xóa cảm biến này?"
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
  <SensorModal
    v-if="modalOpen"
    :open="modalOpen"
    :sensor-id="selectedSensorId"
    :title="modalTitle"
    @close="closeModal"
    @success="handleSuccess"
  />
</template>

<script setup lang="ts">
import DivCustom from '@/components/custom/Div/DivCustom.vue'
import { IdcardOutlined, FormOutlined, DeleteFilled } from '@ant-design/icons-vue'
import { ref, onMounted } from 'vue'
import { getAllSensors, deleteSensor } from '@/services/api/staff/sensor.api.ts'
import { toast } from 'vue3-toastify'
import SensorModal from './SensorModal.vue'

const loading = ref(false)
const page = ref(1)
const size = ref(10)
const total = ref(0)
const data = ref<any[]>([])

const modalOpen = ref(false)
const selectedSensorId = ref<string | null>(null)
const modalTitle = ref('Thêm cảm biến')

const columns = [
  { title: 'STT', key: 'orderNumber', align: 'center', width: 70 },
  { title: 'Tên cảm biến', key: 'sensorName', dataIndex: 'sensorName', align: 'center' },
  { title: 'Hành động', key: 'operation', align: 'center', width: 120 }
]

const fetchSensors = async () => {
  loading.value = true
  try {
    const res = await getAllSensors()
    if (res.data.success) {
      data.value = res.data.data?.content || res.data.data
      total.value = res.data.data?.totalElements || res.data.data.length
    } else {
      toast.error(res.data.message || 'Không thể tải danh sách cảm biến')
    }
  } catch (err) {
    toast.error('Lỗi khi tải danh sách cảm biến!')
    console.error(err)
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: string) => {
  try {
    const res = await deleteSensor(id)
    if (res.data.success) {
      toast.success('Xóa cảm biến thành công!')
      await fetchSensors()
    } else {
      toast.error(res.data.message || 'Xóa thất bại!')
    }
  } catch (err) {
    toast.error('Lỗi khi xóa cảm biến!')
    console.error(err)
  }
}

const handlePageChange = (pagination: any) => {
  page.value = pagination.current
  size.value = pagination.pageSize
  fetchSensors()
}

const openAddModal = () => {
  selectedSensorId.value = null
  modalTitle.value = 'Thêm cảm biến'
  modalOpen.value = true
}

const openEditModal = (id: string) => {
  selectedSensorId.value = id
  modalTitle.value = 'Cập nhật cảm biến'
  modalOpen.value = true
}

const closeModal = () => {
  modalOpen.value = false
}

const handleSuccess = async () => {
  closeModal()
  await fetchSensors()
}

onMounted(fetchSensors)
</script>
