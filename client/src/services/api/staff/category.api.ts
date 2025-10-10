import request from '@/services/request'
import {
    DefaultResponse
} from '@/types/api.common'
import { AxiosResponse } from 'axios'

export interface CategoryResponse {
    id: string
    categoryCode: string
    categoryName: string
}

export interface CreateUpdateCategoryRequest {
    categoryCode: string
    categoryName: string
}

export const getAllCategories = (): Promise<AxiosResponse<DefaultResponse<CategoryResponse[]>>> => {
    return request.get(`/staff/category/all`)
}

export const addCategory = (
    data: CreateUpdateCategoryRequest
): Promise<AxiosResponse<DefaultResponse<CategoryResponse>>> => {
    return request.post(`staff/category/add`, data)
}

export const updateCategory = (
    id: string,
    data: CreateUpdateCategoryRequest
): Promise<AxiosResponse<DefaultResponse<CategoryResponse>>> => {
    return request.put(`staff/category/update/${id}`, data)
}

export const deleteCategory = (
    id: string
): Promise<AxiosResponse<DefaultResponse<any>>> => {
    return request.delete(`/staff/category/delete/${id}`)
}

export const getCategoryById = (
    id: string
): Promise<AxiosResponse<DefaultResponse<CategoryResponse>>> => {
    return request.get(`staff/category/${id}`)
}

