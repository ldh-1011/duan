import request from '@/services/request'
import { AxiosResponse } from 'axios'
import { DefaultResponse } from '@/types/api.common'

const PREFIX_API_STAFF_PROCESSOR = '/staff/processor'

export interface ProcessorResponse {
  id: string
  processor: string
}

export interface CreateUpdateProcessorRequest {
  processorName: string
}

export const getAllProcessor = async (): Promise<AxiosResponse<DefaultResponse<ProcessorResponse[]>>> => {
  return request.get(`${PREFIX_API_STAFF_PROCESSOR}/all`)
}

export const getProcessorId = async (id: string): Promise<AxiosResponse<DefaultResponse<ProcessorResponse>>> => {
  return request.get(`${PREFIX_API_STAFF_PROCESSOR}/${id}`)
}

export const addProcessor = async (
  payload: CreateUpdateProcessorRequest
): Promise<AxiosResponse<DefaultResponse<ProcessorResponse>>> => {
  return request.post(`${PREFIX_API_STAFF_PROCESSOR}/add`, payload)
}

export const updateProcessor = async (
  id: string,
  payload: CreateUpdateProcessorRequest
): Promise<AxiosResponse<DefaultResponse<ProcessorResponse>>> => {
  return request.put(`${PREFIX_API_STAFF_PROCESSOR}/update/${id}`, payload)
}

export const deleteProcessor = async (
  id: string
): Promise<AxiosResponse<DefaultResponse<string>>> => {
  return request.delete(`${PREFIX_API_STAFF_PROCESSOR}/delete/${id}`)
}
