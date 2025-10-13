import request from '@/services/request'
import { AxiosResponse } from 'axios'
import { DefaultResponse } from '@/types/api.common'

const PREFIX_API_STAFF_STORAGE_CAPACITY = '/staff/storage_capacity'

export interface StorageCapacityResponse {
  id: string
  storageCapacityName: string
}

export interface CreateUpdateStorageCapacityRequest {
  storageCapacityName: string
}

export const getAllStorageCapacity = (): Promise<
  AxiosResponse<DefaultResponse<StorageCapacityResponse[]>>
> => {
  return request.get(`${PREFIX_API_STAFF_STORAGE_CAPACITY}/all`)
}

export const getStorageCapacityById = (
  id: string
): Promise<AxiosResponse<DefaultResponse<StorageCapacityResponse>>> => {
  return request.get(`${PREFIX_API_STAFF_STORAGE_CAPACITY}/${id}`)
}

export const addStorageCapacity = (
  data: CreateUpdateStorageCapacityRequest
): Promise<AxiosResponse<DefaultResponse<StorageCapacityResponse>>> => {
  return request.post(`${PREFIX_API_STAFF_STORAGE_CAPACITY}/add`, data)
}

export const updateStorageCapacity = (
  id: string,
  data: CreateUpdateStorageCapacityRequest
): Promise<AxiosResponse<DefaultResponse<StorageCapacityResponse>>> => {
  return request.put(`${PREFIX_API_STAFF_STORAGE_CAPACITY}/update/${id}`, data)
}

export const deleteStorageCapacity = (
  id: string
): Promise<AxiosResponse<DefaultResponse<boolean>>> => {
  return request.delete(`${PREFIX_API_STAFF_STORAGE_CAPACITY}/delete/${id}`)
}
