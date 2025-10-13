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
      <a-form-item label="Tên độ phân giải" name="resolutionName" :rules="rules.resolutionName">
        <a-input
          v-model:value="form.resolutionName"
          placeholder="VD: Full HD (1920x1080), 4K (3840x2160)..."
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
import { addResolution, updateResolution, getResolutionId } from '@/services/api/staff/resolution.api.ts'

const props = defineProps<{ open: boolean; resolutionId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  resolutionName: ''
})

const rules = {
  resolutionName: [{ required: true, message: 'Vui lòng nhập tên độ phân giải' }]
}

const errorMessage = ref('')

const resetForm = () => {
  form.value = { resolutionName: '' }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

const closeModal = () => emit('close')

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    const payload = { resolutionName: form.value.resolutionName.trim() }

    const res = props.resolutionId
      ? await updateResolution(props.resolutionId, payload)
      : await addResolution(payload)

    if (res.data.success) {
      toast.success(props.resolutionId ? 'Cập nhật độ phân giải thành công' : 'Thêm độ phân giải thành công')
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

const fetchResolutionDetail = async (id: string) => {
  try {
    const res = await getResolutionId(id)
    if (res.data.success && res.data.data) {
      form.value = { resolutionName: res.data.data.resolutionName }
    }
  } catch {
    toast.error('Không thể lấy thông tin độ phân giải')
  }
}

watch(
  [() => props.resolutionId, () => visible.value],
  async ([id, open]) => {
    if (!open) return
    if (id) await fetchResolutionDetail(id)
    else resetForm()
  },
  { immediate: true }
)
</script>
