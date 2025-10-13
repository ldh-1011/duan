import request from '@/services/request'
import {
  DefaultResponse
} from '@/types/api.common'
import { AxiosResponse } from 'axios'

const PREFIX_API_STAFF_COLOR = '/staff/color'

export interface ColorResponse {
  id: string
  colorName: string
}

export interface CreateUpdateColorRequest {
  colorName: string
}

export const getAllColor = (): Promise<AxiosResponse<DefaultResponse<ColorResponse[]>>> =>{
  return request.get(`${PREFIX_API_STAFF_COLOR}/all`);
}

export const addColor = (
  data: CreateUpdateColorRequest
): Promise<AxiosResponse<DefaultResponse<ColorResponse>>> => {
  return request.post(`${PREFIX_API_STAFF_COLOR}/add`, data)
}

export const updateColor = (
  id: string,
  data: CreateUpdateColorRequest
): Promise<AxiosResponse<DefaultResponse<ColorResponse>>> => {
  return request.put(`${PREFIX_API_STAFF_COLOR}/update/${id}`, data)
}

export const deleteColor = (
  id: string
): Promise<AxiosResponse<DefaultResponse<any>>> => {
  return request.delete(`${PREFIX_API_STAFF_COLOR}/delete/${id}`)
}

export const getColorById = (
  id: string
): Promise<AxiosResponse<DefaultResponse<ColorResponse>>> => {
  return request.get(`${PREFIX_API_STAFF_COLOR}/${id}`)
}
