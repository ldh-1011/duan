import type { AxiosResponse } from 'axios'
import request from '@/services/request'

export interface LoginRequest {
    email: string
    password: string
}

export interface LoginResponse {
    email: string
    role: string
    token: string
}

export const login = async (
    data: LoginRequest
): Promise<AxiosResponse<LoginResponse>> => {
    return await request.post(`/auth/login`, data)
}
