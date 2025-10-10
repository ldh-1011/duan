import { ROLES } from './roles'

// BASE URL (đọc từ .env)
export const { VITE_BASE_URL_SERVER, VITE_BASE_URL_CLIENT } = import.meta.env

// DOMAIN
export const DOMAIN_BACKEND = VITE_BASE_URL_SERVER as string
export const DOMAIN_FRONTEND = VITE_BASE_URL_CLIENT as string

// FRONTEND redirect
export const URL_FRONTEND = `${DOMAIN_FRONTEND}/redirect`

// Role query param (khi redirect)
export const SCREEN_ROLE_ADMIN = `&screen=${ROLES.ADMIN}`
export const SCREEN_ROLE_STAFF = `&screen=${ROLES.STAFF}`
export const SCREEN_ROLE_CUSTOMER = `&screen=${ROLES.CUSTOMER}`

// ===================== API PREFIX =====================
export const API_VERSION_PREFIX = '/api/v1'
export const API_URL = DOMAIN_BACKEND + API_VERSION_PREFIX

// Auth
export const API_AUTH_PREFIX = `${API_VERSION_PREFIX}/auth`

// Common
export const API_COMMON = `${API_VERSION_PREFIX}/common`

// Staff
export const API_STAFF_PREFIX = `${API_VERSION_PREFIX}/staff`
export const API_STAFF_PRODUCT = `${API_STAFF_PREFIX}/product`
export const API_STAFF_CATEGORY = `${API_STAFF_PREFIX}/category`
export const API_STAFF_PROCESSOR = `${API_STAFF_PREFIX}/processor`
export const API_STAFF_ISO = `${API_STAFF_PREFIX}/iso`
export const API_STAFF_SENSOR = `${API_STAFF_PREFIX}/sensor`
export const API_STAFF_RESOLUTION = `${API_STAFF_PREFIX}/resolution`
export const API_STAFF_COLOR = `${API_STAFF_PREFIX}/color`
export const API_STAFF_VERSION = `${API_STAFF_PREFIX}/version`
export const API_STAFF_STORAGE_CAPACITY = `${API_STAFF_PREFIX}/storage_capacity`
export const API_STAFF_PRODUCT_DETAIL = `${API_STAFF_PREFIX}/product_detail`
export const API_STAFF_IMG = `${API_STAFF_PREFIX}/img`
export const API_STAFF_SERIAL = `${API_STAFF_PREFIX}/serial`

// Customer
export const API_CUSTOMER_PREFIX = `${API_VERSION_PREFIX}/customer`

// ===================== AUTH ENDPOINTS =====================
export const AUTH_API = {
    LOGIN: `${API_AUTH_PREFIX}/login`,
    REGISTER: `${API_AUTH_PREFIX}/register`,
    LOGOUT: `${API_AUTH_PREFIX}/logout`,
    REFRESH: `${API_AUTH_PREFIX}/refresh-token`
}
