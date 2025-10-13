import request from '@/services/request'
import { DefaultResponse } from '@/types/api.common'
import { AxiosResponse } from 'axios'

const PREFIX_API_STAFF_SENSOR = '/staff/sensor'

export interface SensorResponse {
  id: string
  sensorName: string
}

export interface CreateUpdateSensorRequest {
  sensorName: string
}


export const getAllSensors = (): Promise<AxiosResponse<DefaultResponse<SensorResponse[]>>> => {
  return request.get(`${PREFIX_API_STAFF_SENSOR}/all`)
}

export const addSensor = (
  data: CreateUpdateSensorRequest
): Promise<AxiosResponse<DefaultResponse<SensorResponse>>> => {
  return request.post(`${PREFIX_API_STAFF_SENSOR}/add`, data)
}

export const updateSensor = (
  id: string,
  data: CreateUpdateSensorRequest
): Promise<AxiosResponse<DefaultResponse<SensorResponse>>> => {
  return request.put(`${PREFIX_API_STAFF_SENSOR}/update/${id}`, data)
}

export const deleteSensor = (
  id: string
): Promise<AxiosResponse<DefaultResponse<null>>> => {
  return request.delete(`${PREFIX_API_STAFF_SENSOR}/delete/${id}`)
}

export const findSensorById = (
  id: string
): Promise<AxiosResponse<DefaultResponse<SensorResponse>>> => {
  return request.get(`${PREFIX_API_STAFF_SENSOR}/${id}`)
}
