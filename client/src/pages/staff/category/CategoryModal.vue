<template>
  <a-modal
      v-model:open="visible"
      :title="title"
      @cancel="closeModal"
      style="top: 20px"
      width="500px"
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
        <a-form-item label="Mã Category" name="categoryCode" :rules="rules.categoryCode">
          <a-input v-model:value="form.categoryCode" placeholder="VD: CAT001" />
        </a-form-item>

        <a-form-item label="Tên Category" name="categoryName" :rules="rules.categoryName">
          <a-input v-model:value="form.categoryName" placeholder="VD: Tên danh mục" />
        </a-form-item>
      </div>

      <a-form-item v-if="errorMessage" validate-status="error" :help="errorMessage" />
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, watch } from 'vue'
import { toast } from 'vue3-toastify'
import { addCategory, updateCategory, getCategoryById } from '@/services/api/staff/Category.api'

const props = defineProps<{ open: boolean; categoryId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  categoryCode: '',
  categoryName: ''
})

const rules = {
  categoryCode: [{ required: true, message: 'Vui lòng nhập mã danh mục' }],
  categoryName: [{ required: true, message: 'Vui lòng nhập tên danh mục' }]
}

const errorMessage = ref('')

const resetForm = () => {
  form.value = { categoryCode: '', categoryName: '' }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

const closeModal = () => emit('close')

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    const payload = {
      categoryCode: form.value.categoryCode.trim(),
      categoryName: form.value.categoryName.trim()
    }

    const res = props.categoryId
        ? await updateCategory(props.categoryId, payload)
        : await addCategory(payload)

    if (res.data.success) {
      toast.success(props.categoryId ? 'Cập nhật danh mục thành công' : 'Thêm danh mục thành công')
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

const fetchCategoryDetail = async (id: string) => {
  try {
    const res = await getCategoryById(id)
    if (res.data.success && res.data.data) {
      form.value = {
        categoryCode: res.data.data.categoryCode,
        categoryName: res.data.data.categoryName
      }
    }
  } catch {
    toast.error('Không thể lấy thông tin danh mục')
  }
}

watch(
    [() => props.categoryId, () => visible.value],
    async ([id, open]) => {
      if (!open) return
      if (id) await fetchCategoryDetail(id)
      else resetForm()
    },
    { immediate: true }
)
</script>
