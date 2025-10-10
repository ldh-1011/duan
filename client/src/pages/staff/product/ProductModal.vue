<template>
  <a-modal
      v-model:open="visible"
      :title="title"
      @cancel="closeModal"
      style="top: 20px"
      width="650px"
  >
    <template #footer>
      <a-popconfirm
          title="Bạn có chắc chắn muốn lưu thay đổi?"
          @confirm="handleSubmit"
          ok-text="Đồng ý"
          cancel-text="Huỷ"
      >
        <a-button type="primary">Lưu</a-button>
      </a-popconfirm>
      <a-button @click="closeModal">Đóng</a-button>
    </template>

    <a-form :model="form" ref="formRef" layout="vertical">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <a-form-item label="Mã sản phẩm" name="productCode" :rules="rules.productCode">
          <a-input v-model:value="form.productCode" placeholder="VD: P001" />
        </a-form-item>

        <a-form-item label="Tên sản phẩm" name="productName" :rules="rules.productName">
          <a-input v-model:value="form.productName" placeholder="VD: iPhone 15 Pro Max" />
        </a-form-item>

        <a-form-item label="Danh mục (Category)" name="categoryCode" :rules="rules.categoryCode">
          <a-select
              v-model:value="form.categoryCode"
              placeholder="Chọn danh mục"
              :options="categoryOptions"
          />
        </a-form-item>

        <a-form-item label="ISO" name="isoName" :rules="rules.isoName">
          <a-select
              v-model:value="form.isoName"
              placeholder="Chọn ISO"
              :options="isoOptions"
          />
        </a-form-item>

        <a-form-item label="Processor" name="processorName" :rules="rules.processorName">
          <a-select
              v-model:value="form.processorName"
              placeholder="Chọn Processor"
              :options="processorOptions"
          />
        </a-form-item>

        <a-form-item label="Resolution" name="resolutionName" :rules="rules.resolutionName">
          <a-select
              v-model:value="form.resolutionName"
              placeholder="Chọn Resolution"
              :options="resolutionOptions"
          />
        </a-form-item>

        <a-form-item label="Sensor" name="sensorName" :rules="rules.sensorName">
          <a-select
              v-model:value="form.sensorName"
              placeholder="Chọn Sensor"
              :options="sensorOptions"
          />
        </a-form-item>
      </div>

      <a-form-item label="Mô tả" name="description">
        <a-textarea v-model:value="form.description" placeholder="Nhập mô tả sản phẩm" rows="3" />
      </a-form-item>

      <a-form-item v-if="errorMessage" validate-status="error" :help="errorMessage" />
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, watch, onMounted } from 'vue'
import { toast } from 'vue3-toastify'
import {
  addProduct,
  updateProduct,
  getProductById,
  getAllCategory,
  getAllIso,
  getAllProcessor,
  getAllResolution,
  getAllSensor
} from '@/services/api/staff/product.api'

// ✅ Props
const props = defineProps<{ open: boolean; productId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

// ✅ State
const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  productCode: '',
  productName: '',
  description: '',
  categoryCode: '',
  isoName: '',
  processorName: '',
  resolutionName: '',
  sensorName: ''
})

const errorMessage = ref('')

// ✅ Validation rules
const rules = {
  productCode: [{ required: true, message: 'Vui lòng nhập mã sản phẩm' }],
  productName: [{ required: true, message: 'Vui lòng nhập tên sản phẩm' }],
  categoryCode: [{ required: true, message: 'Vui lòng chọn danh mục' }],
  isoName: [{ required: true, message: 'Vui lòng chọn ISO' }],
  processorName: [{ required: true, message: 'Vui lòng chọn Processor' }],
  resolutionName: [{ required: true, message: 'Vui lòng chọn Resolution' }],
  sensorName: [{ required: true, message: 'Vui lòng chọn Sensor' }]
}

// ✅ Dropdown options
const categoryOptions = ref([])
const isoOptions = ref([])
const processorOptions = ref([])
const resolutionOptions = ref([])
const sensorOptions = ref([])

// ✅ Reset form
const resetForm = () => {
  form.value = {
    productCode: '',
    productName: '',
    description: '',
    categoryCode: '',
    isoName: '',
    processorName: '',
    resolutionName: '',
    sensorName: ''
  }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

// ✅ Close modal
const closeModal = () => emit('close')

// ✅ Fetch dropdown data
const fetchDropdowns = async () => {
  try {
    const [catRes, isoRes, proRes, resoRes, senRes] = await Promise.all([
      getAllCategory(),
      getAllIso(),
      getAllProcessor(),
      getAllResolution(),
      getAllSensor()
    ])

    categoryOptions.value =
        catRes.data.data?.map((c: string) => ({
          label: c,
          value: c
        })) || []

    isoOptions.value =
        isoRes.data.data?.map((i: string) => ({
          label: i,
          value: i
        })) || []

    processorOptions.value =
        proRes.data.data?.map((p: string) => ({
          label: p,
          value: p
        })) || []

    resolutionOptions.value =
        resoRes.data.data?.map((r: string) => ({
          label: r,
          value: r
        })) || []

    sensorOptions.value =
        senRes.data.data?.map((s: string) => ({
          label: s,
          value: s
        })) || []
  } catch {
    toast.error('Không thể tải dữ liệu dropdown')
  }
}
const fetchProductDetail = async (id: string) => {
  try {
    const res = await getProductById(id)
    if (res.data.success && res.data.data) {
      const p = res.data.data
      // Map đúng field với form
      form.value = {
        productCode: p.productCode || '',
        productName: p.productName || '',
        description: p.description || '',
        categoryCode: p.categoryCode || '',
        isoName: p.iso || '',
        processorName: p.processor || '',
        resolutionName: p.resolution || '',
        sensorName: p.sensor || ''
      }
    }
  } catch {
    toast.error('Không thể lấy thông tin sản phẩm')
  }
}

// ✅ Handle submit (add/update)
const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    const payload = { ...form.value }

    const res = props.productId
        ? await updateProduct(props.productId, payload)
        : await addProduct(payload)

    if (res.data.success) {
      toast.success(props.productId ? 'Cập nhật sản phẩm thành công' : 'Thêm sản phẩm thành công')
      emit('success')
      resetForm()
      closeModal()
    } else {
      throw new Error(res.data.message)
    }
  } catch (err: any) {
    const msg =
        err?.response?.data?.message ||
        JSON.stringify(err?.response?.data) ||
        err?.message ||
        'Lỗi hệ thống'
    errorMessage.value = msg
    toast.error(msg)
  }
}

onMounted(fetchDropdowns)

watch(
    () => props.productId,
    async (newId) => {
      if (newId) {
        await fetchDropdowns()
        await fetchProductDetail(newId)
      } else {
        resetForm()
      }
    },
    { immediate: true }
)
</script>
