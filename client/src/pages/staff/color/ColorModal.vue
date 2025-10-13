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
      <a-form-item label="Tên màu" name="colorName" :rules="rules.colorName">
        <a-input
          v-model:value="form.colorName"
          placeholder="VD: Đỏ, Xanh, Đen..."
          class="w-full"
        />
      </a-form-item>

      <a-form-item v-if="errorMessage" validate-status="error" :help="errorMessage" />
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, watch } from 'vue'
import { toast } from 'vue3-toastify'
import { addColor, updateColor, getColorById } from '@/services/api/staff/color.api.ts'

const props = defineProps<{ open: boolean; colorId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  colorName: ''
})

const rules = {
  colorName: [{ required: true, message: 'Vui lòng nhập tên màu' }]
}

const errorMessage = ref('')

const resetForm = () => {
  form.value = { colorName: '' }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

const closeModal = () => emit('close')

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    const payload = {
      colorName: form.value.colorName.trim()
    }

    const res = props.colorId
      ? await updateColor(props.colorId, payload)
      : await addColor(payload)

    if (res.data.success) {
      toast.success(props.colorId ? 'Cập nhật màu thành công' : 'Thêm màu thành công')
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

const fetchColorDetail = async (id: string) => {
  try {
    const res = await getColorById(id)
    if (res.data.success && res.data.data) {
      form.value = {
        colorName: res.data.data.colorName
      }
    }
  } catch {
    toast.error('Không thể lấy thông tin màu')
  }
}

watch(
  [() => props.colorId, () => visible.value],
  async ([id, open]) => {
    if (!open) return
    if (id) await fetchColorDetail(id)
    else resetForm()
  },
  { immediate: true }
)
</script>
