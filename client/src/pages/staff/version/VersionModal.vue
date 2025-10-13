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
      <a-form-item label="Tên phiên bản" name="versionName" :rules="rules.versionName">
        <a-input
          v-model:value="form.versionName"
          placeholder="VD: iPhone 15 Pro, Galaxy S24, ..."
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
import { addVersion, updateVersion, getVersionById } from '@/services/api/staff/version.api'

const props = defineProps<{ open: boolean; versionId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  versionName: ''
})

const rules = {
  versionName: [{ required: true, message: 'Vui lòng nhập tên phiên bản' }]
}

const errorMessage = ref('')

const resetForm = () => {
  form.value = { versionName: '' }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

const closeModal = () => emit('close')

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    const payload = {
      versionName: form.value.versionName.trim()
    }

    const res = props.versionId
      ? await updateVersion(props.versionId, payload)
      : await addVersion(payload)

    if (res.data.success) {
      toast.success(props.versionId ? 'Cập nhật phiên bản thành công' : 'Thêm phiên bản thành công')
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

const fetchVersionDetail = async (id: string) => {
  try {
    const res = await getVersionById(id)
    if (res.data.success && res.data.data) {
      form.value = {
        versionName: res.data.data.versionName
      }
    }
  } catch {
    toast.error('Không thể lấy thông tin phiên bản')
  }
}

watch(
  [() => props.versionId, () => visible.value],
  async ([id, open]) => {
    if (!open) return
    if (id) await fetchVersionDetail(id)
    else resetForm()
  },
  { immediate: true }
)
</script>
