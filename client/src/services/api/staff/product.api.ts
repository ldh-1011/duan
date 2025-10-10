import request from '@/services/request'
import type { AxiosResponse } from 'axios'
import type { DefaultResponse } from '@/types/api.common'

// =====================
// 📦 TYPE DEFINITIONS
// =====================
export interface ProductResponse {
    id: string
    productCode: string
    productName: string
    iso: string
    processor: string
    resolution: string
    sensor: string
    description: string
    categoryCode : string
    status: number
}

export interface ProductRequest {
    keyword?: string
    status?: string
    page?: number
    size?: number
}

export interface CreateUpdateProductRequest {
    productCode: string
    productName: string
    isoId: string
    processorId: string
    resolutionId: string
    sensorId: string
    description: string
    status?: number
}

const PREFIX_API_STAFF_PRODUCT = '/staff/product'

export const getAllProducts = (
    page: number,
    size: number,
    keyword?: string,
    status?: string
): Promise<AxiosResponse<DefaultResponse<ProductResponse[]>>> => {
    return request.get(`${PREFIX_API_STAFF_PRODUCT}/all`, {
        params: { page, size, keyword, status }
    })
}

export const addProduct = (
    payload: CreateUpdateProductRequest
): Promise<AxiosResponse<DefaultResponse<null>>> => {
    return request.post(`${PREFIX_API_STAFF_PRODUCT}/add`, payload)
}

export const updateProduct = (
    id: string,
    payload: CreateUpdateProductRequest
): Promise<AxiosResponse<DefaultResponse<null>>> => {
    return request.put(`${PREFIX_API_STAFF_PRODUCT}/update/${id}`, payload)
}

export const deleteProduct = (
    id: string
): Promise<AxiosResponse<DefaultResponse<null>>> => {
    return request.delete(`${PREFIX_API_STAFF_PRODUCT}/delete/${id}`)
}

export const changeProductStatus = (
    id: string
): Promise<AxiosResponse<DefaultResponse<null>>> => {
    return request.put(`${PREFIX_API_STAFF_PRODUCT}/change_status/${id}`)
}

export const getAllCategory = (): Promise<AxiosResponse<DefaultResponse<any[]>>> => {
    return request.get(`${PREFIX_API_STAFF_PRODUCT}/all-category`)
}

export const getAllIso = (): Promise<AxiosResponse<DefaultResponse<any[]>>> => {
    return request.get(`${PREFIX_API_STAFF_PRODUCT}/all-iso`)
}

export const getAllProcessor = (): Promise<AxiosResponse<DefaultResponse<any[]>>> => {
    return request.get(`${PREFIX_API_STAFF_PRODUCT}/all-processor`)
}

export const getAllResolution = (): Promise<AxiosResponse<DefaultResponse<any[]>>> => {
    return request.get(`${PREFIX_API_STAFF_PRODUCT}/all-resolution`)
}

export const getAllSensor = (): Promise<AxiosResponse<DefaultResponse<any[]>>> => {
    return request.get(`${PREFIX_API_STAFF_PRODUCT}/all-sensor`)
}

export const getProductById = (
    id: string
): Promise<AxiosResponse<DefaultResponse<ProductResponse>>> => {
    return request.get(`${PREFIX_API_STAFF_PRODUCT}/${id}`)
}
