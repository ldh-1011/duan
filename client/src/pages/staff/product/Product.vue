<template>
  <BreadcrumbDefault label="Sản phẩm">
    <ProductFilter
        v-model:keyword="keyword"
        v-model:status="status"
    />
    <ProductTable
        :keyword="keyword"
        :status="status"
    />
  </BreadcrumbDefault>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import BreadcrumbDefault from '@/components/custom/Div/BreadcrumbDefault.vue'
import ProductFilter from '@/pages/staff/product/ProductFilter.vue'
import ProductTable from '@/pages/staff/product/ProductTable.vue'

const keyword = ref('')
const status = ref('')
</script>
