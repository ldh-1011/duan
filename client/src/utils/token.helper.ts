import { jwtDecode } from 'jwt-decode'
import type { DecodedToken, UserInformation } from '@/types/auth.type'

// Giải mã JWT để lấy thông tin user cơ bản
export const getUserInformation = (token: string): UserInformation => {
    const decoded = jwtDecode<DecodedToken>(token)
    return {
        email: decoded.sub || '',
        role: decoded.auth || ''
    }
}

// Lấy role từ token
export const getRolesUser = (token: string): string[] => {
    const decoded = jwtDecode<DecodedToken>(token)
    return [decoded.auth]
}

// Lấy thời gian hết hạn token (exp)
export const getExpireTime = (token: string): number => {
    const decoded = jwtDecode<DecodedToken>(token)
    return decoded.exp
}
