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
      <a-form-item
        label="Tên dung lượng lưu trữ"
        name="storageCapacityName"
        :rules="rules.storageCapacityName"
      >
        <a-input
          v-model:value="form.storageCapacityName"
          placeholder="VD: 256GB, 512GB, 1TB..."
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
import {
  addStorageCapacity,
  updateStorageCapacity,
  getStorageCapacityById
} from '@/services/api/staff/storagecapacity.api.ts'

const props = defineProps<{ open: boolean; storageCapacityId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  storageCapacityName: ''
})

const rules = {
  storageCapacityName: [{ required: true, message: 'Vui lòng nhập tên dung lượng' }]
}

const errorMessage = ref('')

const resetForm = () => {
  form.value = { storageCapacityName: '' }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

const closeModal = () => emit('close')

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    const payload = {
      storageCapacityName: form.value.storageCapacityName.trim()
    }

    const res = props.storageCapacityId
      ? await updateStorageCapacity(props.storageCapacityId, payload)
      : await addStorageCapacity(payload)

    if (res.data.success) {
      toast.success(
        props.storageCapacityId
          ? 'Cập nhật dung lượng thành công'
          : 'Thêm dung lượng thành công'
      )
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

const fetchStorageCapacityDetail = async (id: string) => {
  try {
    const res = await getStorageCapacityById(id)
    if (res.data.success && res.data.data) {
      form.value = {
        storageCapacityName: res.data.data.storageCapacityName
      }
    }
  } catch {
    toast.error('Không thể lấy thông tin dung lượng')
  }
}

watch(
  [() => props.storageCapacityId, () => visible.value],
  async ([id, open]) => {
    if (!open) return
    if (id) await fetchStorageCapacityDetail(id)
    else resetForm()
  },
  { immediate: true }
)
</script>
