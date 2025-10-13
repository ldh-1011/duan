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
      <a-form-item label="Tên cảm biến" name="sensorName" :rules="rules.sensorName">
        <a-input
          v-model:value="form.sensorName"
          placeholder="VD: Cảm biến nhiệt độ, ánh sáng..."
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
import { addSensor, updateSensor, findSensorById } from '@/services/api/staff/sensor.api.ts'

const props = defineProps<{ open: boolean; sensorId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  sensorName: ''
})

const rules = {
  sensorName: [{ required: true, message: 'Vui lòng nhập tên cảm biến' }]
}

const errorMessage = ref('')

const resetForm = () => {
  form.value = { sensorName: '' }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

const closeModal = () => emit('close')

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    const payload = {
      sensorName: form.value.sensorName.trim()
    }

    const res = props.sensorId
      ? await updateSensor(props.sensorId, payload)
      : await addSensor(payload)

    if (res.data.success) {
      toast.success(props.sensorId ? 'Cập nhật cảm biến thành công' : 'Thêm cảm biến thành công')
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

const fetchSensorDetail = async (id: string) => {
  try {
    const res = await findSensorById(id)
    if (res.data.success && res.data.data) {
      form.value = {
        sensorName: res.data.data.sensorName
      }
    }
  } catch {
    toast.error('Không thể lấy thông tin cảm biến')
  }
}

watch(
  [() => props.sensorId, () => visible.value],
  async ([id, open]) => {
    if (!open) return
    if (id) await fetchSensorDetail(id)
    else resetForm()
  },
  { immediate: true }
)
</script>
