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
      <a-form-item label="Tên ISO" name="isoName" :rules="rules.isoName">
        <a-input
          v-model:value="form.isoName"
          placeholder="VD: ISO 9001, ISO 14001..."
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
import { addIso, updateIso, getIsoById } from '@/services/api/staff/iso.api'

const props = defineProps<{ open: boolean; isoId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  isoName: ''
})

const rules = {
  isoName: [{ required: true, message: 'Vui lòng nhập tên ISO' }]
}

const errorMessage = ref('')

const resetForm = () => {
  form.value = { isoName: '' }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

const closeModal = () => emit('close')

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    const payload = {
      isoName: form.value.isoName.trim()
    }

    const res = props.isoId
      ? await updateIso(props.isoId, payload)
      : await addIso(payload)

    if (res.data.success) {
      toast.success(props.isoId ? 'Cập nhật ISO thành công' : 'Thêm ISO thành công')
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

const fetchIsoDetail = async (id: string) => {
  try {
    const res = await getIsoById(id)
    if (res.data.success && res.data.data) {
      form.value = {
        isoName: res.data.data.isoName
      }
    }
  } catch {
    toast.error('Không thể lấy thông tin ISO')
  }
}

watch(
  [() => props.isoId, () => visible.value],
  async ([id, open]) => {
    if (!open) return
    if (id) await fetchIsoDetail(id)
    else resetForm()
  },
  { immediate: true }
)
</script>
