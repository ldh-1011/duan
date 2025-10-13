import request from '@/services/request'
import { DefaultResponse } from '@/types/api.common'
import { AxiosResponse } from 'axios'

const PREFIX_API_STAFF_ISO = '/staff/iso'

export interface IsoResponse {
  id: string
  isoName: string
  createdDate: string
  updatedDate: string
}

export interface CreateUpdateIsoRequest {
  isoName: string
}

export const getAllIso = async (): Promise<AxiosResponse<DefaultResponse<IsoResponse[]>>> => {
  return request.get(`${PREFIX_API_STAFF_ISO}/all`)
}

export const addIso = async (data: CreateUpdateIsoRequest): Promise<AxiosResponse<DefaultResponse<IsoResponse>>> => {
  return request.post(`${PREFIX_API_STAFF_ISO}/add`, data)
}

export const updateIso = async (id: string, data: CreateUpdateIsoRequest): Promise<AxiosResponse<DefaultResponse<IsoResponse>>> => {
  return request.put(`${PREFIX_API_STAFF_ISO}/update/${id}`, data)
}

export const deleteIso = async (id: string): Promise<AxiosResponse<DefaultResponse<null>>> => {
  return request.delete(`${PREFIX_API_STAFF_ISO}/delete/${id}`)
}

export const getIsoById = async (id: string): Promise<AxiosResponse<DefaultResponse<IsoResponse>>> => {
  return request.get(`${PREFIX_API_STAFF_ISO}/${id}`)
}
