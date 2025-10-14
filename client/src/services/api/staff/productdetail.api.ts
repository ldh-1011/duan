import request from '@/services/request'
import type { AxiosResponse } from 'axios'
import type { DefaultResponse } from '@/types/api.common'

export interface ProductDetailResponse {
  id: string
  price: string
  img: string
  colorName: string
  versionName: string
  storageCapacityName: string
  status: string
}

export interface CreateUpdateProductDetailRequest {
  productName: string
  price: number
  colorName: string
  versionName: string
  storageCapacityName: string
}

export interface ProductDetailRequest {
  page?: number
  size?: number
  minPrice?: number
  maxPrice?: number
  colorName?: string
  versionName?: string
  storageCapacityName?: string
  status?: number
  productName?: string
  productCode?: string
}

const PREFIX_API_STAFF_PRODUCT_DETAIL = '/staff/product_detail'

export const getAllProductDetails = (
  params: ProductDetailRequest
): Promise<AxiosResponse<DefaultResponse<ProductDetailResponse[]>>> => {
  return request.get(`${PREFIX_API_STAFF_PRODUCT_DETAIL}/all`, { params })
}

export const addProductDetail = (
  payload: CreateUpdateProductDetailRequest
): Promise<AxiosResponse<DefaultResponse<null>>> => {
  return request.post(`${PREFIX_API_STAFF_PRODUCT_DETAIL}/add`, payload)
}

export const updateProductDetail = (
  id: string,
  payload: CreateUpdateProductDetailRequest
): Promise<AxiosResponse<DefaultResponse<null>>> => {
  return request.put(`${PREFIX_API_STAFF_PRODUCT_DETAIL}/update/${id}`, payload)
}

export const deleteProductDetail = (
  id: string
): Promise<AxiosResponse<DefaultResponse<null>>> => {
  return request.delete(`${PREFIX_API_STAFF_PRODUCT_DETAIL}/delete/${id}`)
}

export const changeProductDetailStatus = (
  id: string
): Promise<AxiosResponse<DefaultResponse<null>>> => {
  return request.put(`${PREFIX_API_STAFF_PRODUCT_DETAIL}/change_status/${id}`)
}
