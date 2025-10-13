import request from '@/services/request'
import {
  DefaultResponse
} from '@/types/api.common'
import { AxiosResponse } from 'axios'

const PREFIX_API_STAFF_RESOLUTION = '/staff/resolution'

export interface ResolutionResponse {
  id: string
  resolutionName: string
}

export interface CreateUpdateResolutionRequest {
  resolutionName: string
}

export const getAllResolution = (): Promise<AxiosResponse<DefaultResponse<ResolutionResponse[]>>> =>{
  return request.get(`${PREFIX_API_STAFF_RESOLUTION}/all`);
}

export const addResolution = (
  data: CreateUpdateResolutionRequest
): Promise<AxiosResponse<DefaultResponse<ResolutionResponse>>> => {
  return request.post(`${PREFIX_API_STAFF_RESOLUTION}/add`, data)
}

export const updateResolution = (
  id: string,
  data: CreateUpdateResolutionRequest
): Promise<AxiosResponse<DefaultResponse<ResolutionResponse>>> => {
  return request.put(`${PREFIX_API_STAFF_RESOLUTION}/update/${id}`, data)
}

export const deleteResolution = (
  id: string
): Promise<AxiosResponse<DefaultResponse<any>>> => {
  return request.delete(`${PREFIX_API_STAFF_RESOLUTION}/delete/${id}`)
}

export const getResolutionId = (
  id: string
): Promise<AxiosResponse<DefaultResponse<ResolutionResponse>>> => {
  return request.get(`${PREFIX_API_STAFF_RESOLUTION}/${id}`)
}