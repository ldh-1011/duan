import request from '@/services/request'
import { DefaultResponse } from '@/types/api.common'
import { AxiosResponse } from 'axios'

const PREFIX_API_STAFF_VERSION = '/staff/version'

export interface VersionResponse {
  id: string
  versionName: string
}

export interface CreateUpdateVersionRequest {
  versionName: string
}

export const getAllVersions = (): Promise<AxiosResponse<DefaultResponse<VersionResponse[]>>> => {
  return request.get(`${PREFIX_API_STAFF_VERSION}/all`)
}

export const addVersion = (data: CreateUpdateVersionRequest): Promise<AxiosResponse<DefaultResponse<VersionResponse>>> => {
  return request.post(`${PREFIX_API_STAFF_VERSION}/add`, data)
}

export const updateVersion = (
  id: string,
  data: CreateUpdateVersionRequest
): Promise<AxiosResponse<DefaultResponse<VersionResponse>>> => {
  return request.put(`${PREFIX_API_STAFF_VERSION}/update/${id}`, data)
}

export const deleteVersion = (id: string): Promise<AxiosResponse<DefaultResponse<any>>> => {
  return request.delete(`${PREFIX_API_STAFF_VERSION}/delete/${id}`)
}

export const getVersionById = (id: string): Promise<AxiosResponse<DefaultResponse<VersionResponse>>> => {
  return request.get(`${PREFIX_API_STAFF_VERSION}/${id}`)
}
