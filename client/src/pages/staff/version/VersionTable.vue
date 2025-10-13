<template>
  <DivCustom label="Danh sách phiên bản" customClasses="mt-5">
    <template #icon>
      <IdcardOutlined />
    </template>

    <template #extra>
      <a-tooltip title="Thêm mới phiên bản">
        <a-button
          type="primary"
          @click="openAddModal"
          class="flex items-center justify-center px-4"
        >
          Thêm phiên bản
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
                  title="Bạn có chắc muốn xóa phiên bản này?"
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
  <VersionModal
    v-if="modalOpen"
    :open="modalOpen"
    :version-id="selectedVersionId"
    :title="modalTitle"
    @close="closeModal"
    @success="handleSuccess"
  />
</template>

<script setup lang="ts">
import DivCustom from '@/components/custom/Div/DivCustom.vue'
import { IdcardOutlined, FormOutlined, DeleteFilled } from '@ant-design/icons-vue'
import { ref, onMounted } from 'vue'
import { getAllVersions, deleteVersion } from '@/services/api/staff/version.api'
import { toast } from 'vue3-toastify'
import VersionModal from './VersionModal.vue'

const loading = ref(false)
const page = ref(1)
const size = ref(10)
const total = ref(0)
const data = ref<any[]>([])

const modalOpen = ref(false)
const selectedVersionId = ref<string | null>(null)
const modalTitle = ref('Thêm phiên bản')

const columns = [
  { title: 'STT', key: 'orderNumber', align: 'center', width: 70 },
  { title: 'Tên phiên bản', key: 'versionName', dataIndex: 'versionName', align: 'center' },
  { title: 'Hành động', key: 'operation', align: 'center', width: 120 }
]

const fetchVersions = async () => {
  loading.value = true
  try {
    const res = await getAllVersions()
    if (res.data.success) {
      data.value = res.data.data?.content || res.data.data
      total.value = res.data.data?.totalElements || res.data.data.length
    } else {
      toast.error(res.data.message || 'Không thể tải danh sách phiên bản')
    }
  } catch (err) {
    toast.error('Lỗi khi tải danh sách phiên bản!')
    console.error(err)
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: string) => {
  try {
    const res = await deleteVersion(id)
    if (res.data.success) {
      toast.success('Xóa phiên bản thành công!')
      await fetchVersions()
    } else {
      toast.error(res.data.message || 'Xóa thất bại!')
    }
  } catch (err) {
    toast.error('Lỗi khi xóa phiên bản!')
    console.error(err)
  }
}

const handlePageChange = (pagination: any) => {
  page.value = pagination.current
  size.value = pagination.pageSize
  fetchVersions()
}

const openAddModal = () => {
  selectedVersionId.value = null
  modalTitle.value = 'Thêm phiên bản'
  modalOpen.value = true
}

const openEditModal = (id: string) => {
  selectedVersionId.value = id
  modalTitle.value = 'Cập nhật phiên bản'
  modalOpen.value = true
}

const closeModal = () => {
  modalOpen.value = false
}

const handleSuccess = async () => {
  console.log('✅ handleSuccess gọi lại')
  closeModal()
  await fetchVersions()
}

onMounted(fetchVersions)
</script>
