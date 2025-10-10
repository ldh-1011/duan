<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'
import ButtonDefault from '@/components/custom/Button/ButtonDefault.vue'

const props = defineProps<{ open: boolean; title: string }>()
const emit = defineEmits(['close', 'submit'])

const handleCancel = () => {
  emit('close')
}

const handleOk = () => {
  emit('submit')
}
</script>

<template>
  <a-modal :open="props.open" :title="props.title" @ok="handleOk" @cancel="handleCancel" footer="">
    <slot></slot>
    <div class="flex justify-end gap-1 mt-3">
      <ButtonDefault
        label="Xác nhận"
        customClasses="bg-black text-white !px-3 !py-2"
        @click="handleOk"
      />
      <ButtonDefault
        label="Huỷ"
        customClasses="bg-black text-white !px-3 !py-2"
        @click="handleCancel"
      />
    </div>
  </a-modal>
</template>
