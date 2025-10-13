<template>
  <DivCustom label="Danh sách sản phẩm" customClasses="mt-5">
    <template #icon>
      <IdcardOutlined />
    </template>

    <template #extra>
      <a-tooltip title="Thêm mới sản phẩm">
        <a-button
            type="primary"
            @click="openAddModal"
            class="flex items-center justify-center px-4"
        >
          Thêm Sản Phẩm
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
          :scroll="{ x: 1000 }"
          @change="handlePageChange"
          row-key="id"
      >
        <template #bodyCell="{ column, record, index }">
          <!-- STT -->
          <template v-if="column.key === 'orderNumber'">
            {{ (page - 1) * size + index + 1 }}
          </template>

          <!-- Trạng thái -->
          <template v-if="column.key === 'status'">
            <a-tag
                :color="record.status === 0 ? 'green' : 'red'"
                class="px-3 py-1 rounded-full font-medium"
            >
              {{ record.status === 0 ? 'Đang bán' : 'Dừng kinh doanh' }}
            </a-tag>

            <!-- Nút đổi trạng thái -->
            <a-tooltip title="Đổi trạng thái">
              <a-button
                  size="small"
                  class="ml-2"
                  @click="changeStatus(record.id)"
                  style="background-color: #fef9c3; color: #92400e; border: none;"
              >
                <ReloadOutlined />
              </a-button>
            </a-tooltip>
          </template>

          <!-- Hành động -->
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
                    title="Bạn có chắc muốn xóa sản phẩm này?"
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
  <ProductModal
      v-if="modalOpen"
      :open="modalOpen"
      :product-id="selectedProductId"
      :title="modalTitle"
      @close="closeModal"
      @success="handleSuccess"
  />
</template>

<script setup lang="ts">
import DivCustom from '@/components/custom/Div/DivCustom.vue'
import ProductModal from './ProductModal.vue'
import { IdcardOutlined, FormOutlined, DeleteFilled, ReloadOutlined } from '@ant-design/icons-vue'
import { ref, onMounted } from 'vue'
import { getAllProducts, deleteProduct, changeProductStatus } from '@/services/api/staff/product.api.ts'
import { toast } from 'vue3-toastify'
import { watch } from 'vue'

const props = defineProps<{
  keyword: string
  status: string
}>()

const loading = ref(false)
const page = ref(1)
const size = ref(10)
const total = ref(0)
const data = ref<any[]>([])

const modalOpen = ref(false)
const selectedProductId = ref<string | null>(null)
const modalTitle = ref('Thêm Sản Phẩm')

const columns = [
  { title: 'STT', key: 'orderNumber', align: 'center', width: 70 },
  { title: 'Mã sản phẩm', key: 'productCode', dataIndex: 'productCode', align: 'center' },
  { title: 'Tên sản phẩm', key: 'productName', dataIndex: 'productName', align: 'center' },
  { title: 'ISO', key: 'iso', dataIndex: 'iso', align: 'center' },
  { title: 'Vi xử lý', key: 'processor', dataIndex: 'processor', align: 'center' },
  { title: 'Độ phân giải', key: 'resolution', dataIndex: 'resolution', align: 'center' },
  { title: 'Cảm biến', key: 'sensor', dataIndex: 'sensor', align: 'center' },
  { title: 'Mô tả', key: 'description', dataIndex: 'description', align: 'center' },
  { title: 'Trạng thái', key: 'status', align: 'center' },
  { title: 'Hành động', key: 'operation', align: 'center', width: 120 }
]

const fetchProducts = async () => {
  loading.value = true
  try {
    const res = await getAllProducts(
      page.value,
      size.value,
      props.keyword,
      props.status
    )
    if (res.data.success) {
      data.value = res.data.data?.content || res.data.data
      total.value = res.data.data?.totalElements || res.data.data.length
    } else {
      toast.error(res.data.message || 'Không thể tải danh sách sản phẩm')
    }
  } catch (err) {
    toast.error('Lỗi khi tải danh sách sản phẩm!')
    console.error(err)
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: string) => {
  try {
    const res = await deleteProduct(id)
    if (res.data.success) {
      toast.success('Xóa sản phẩm thành công!')
      await fetchProducts()
    } else {
      toast.error(res.data.message || 'Xóa thất bại!')
    }
  } catch (err) {
    toast.error('Lỗi khi xóa sản phẩm!')
    console.error(err)
  }
}

const changeStatus = async (id: string) => {
  try {
    const res = await changeProductStatus(id)
    if (res.data.success) {
      toast.success('Thay đổi trạng thái thành công!')
      await fetchProducts()
    } else {
      toast.error(res.data.message || 'Đổi trạng thái thất bại!')
    }
  } catch (err) {
    toast.error('Lỗi khi đổi trạng thái!')
    console.error(err)
  }
}

const handlePageChange = (pagination: any) => {
  page.value = pagination.current
  size.value = pagination.pageSize
  fetchProducts()
}

const openAddModal = () => {
  selectedProductId.value = null
  modalTitle.value = 'Thêm Sản Phẩm'
  modalOpen.value = true
}

const openEditModal = (id: string) => {
  selectedProductId.value = id
  modalTitle.value = 'Cập Nhật Sản Phẩm'
  modalOpen.value = true
}

const closeModal = () => {
  modalOpen.value = false
}

const handleSuccess = async () => {
  closeModal()
  await fetchProducts()
}

watch(
  () => [props.keyword, props.status],
  () => {
    page.value = 1
    fetchProducts()
  }
)
onMounted(fetchProducts)
</script>
