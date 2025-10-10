import { jwtDecode } from 'jwt-decode'

export interface DecodedToken {
    sub: string // email
    auth: string // role
    exp: number // thời gian hết hạn
    iat: number // thời gian tạo token
}

export interface UserInformation {
    email: string
    role: string
}

export const getUserInformation = (token: string): UserInformation => {
    const decoded = jwtDecode<DecodedToken>(token)
    return {
        email: decoded.sub || '',
        role: decoded.auth || ''
    }
}

export const getRolesUser = (token: string): string[] => {
    const decoded = jwtDecode<DecodedToken>(token)
    return decoded.auth ? [decoded.auth] : []
}

export const getExpireTime = (token: string): number => {
    const decoded = jwtDecode<DecodedToken>(token)
    return decoded.exp
}
