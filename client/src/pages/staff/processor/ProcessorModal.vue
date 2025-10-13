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
        label="Tên processor"
        name="processorName"
        :rules="rules.processorName"
      >
        <a-input
          v-model:value="form.processorName"
          placeholder="VD: Digic 4..."
          class="w-full"
        />
      </a-form-item>

      <a-form-item
        v-if="errorMessage"
        validate-status="error"
        :help="errorMessage"
      />
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, watch } from 'vue'
import { toast } from 'vue3-toastify'
import {
  addProcessor,
  updateProcessor,
  getProcessorId
} from '@/services/api/staff/processor.api.ts'

const props = defineProps<{ open: boolean; processorId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const visible = ref(props.open)
watch(() => props.open, (v) => (visible.value = v))

const formRef = ref()
const form = ref({
  processorName: ''
})

const rules = {
  processorName: [{ required: true, message: 'Vui lòng nhập tên processor' }]
}

const errorMessage = ref('')

const resetForm = () => {
  form.value = { processorName: '' }
  formRef.value?.resetFields()
  errorMessage.value = ''
}

const closeModal = () => emit('close')

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    const payload = {
      processorName: form.value.processorName.trim()
    }

    const res = props.processorId
      ? await updateProcessor(props.processorId, payload)
      : await addProcessor(payload)

    if (res.data.success) {
      toast.success(
        props.processorId
          ? 'Cập nhật processor thành công'
          : 'Thêm processor thành công'
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

const fetchProcessorDetail = async (id: string) => {
  try {
    const res = await getProcessorId(id)
    if (res.data.success && res.data.data) {
      form.value = {
        processorName: res.data.data.processor
      }
    }
  } catch {
    toast.error('Không thể lấy thông tin processor')
  }
}

watch(
  [() => props.processorId, () => visible.value],
  async ([id, open]) => {
    if (!open) return
    if (id) await fetchProcessorDetail(id)
    else resetForm()
  },
  { immediate: true }
)
</script>
