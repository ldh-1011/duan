// src/utils/storage.ts

export const localStorageAction = {
  get: (key: string, defaultValue: any = null) => {
    const value = localStorage.getItem(key)

    if (!value || value === 'undefined' || value === 'null') {
      return defaultValue
    }

    // Với token (ACCESS_TOKEN, REFRESH_TOKEN) mình sẽ để ở auth store xử lý riêng
    try {
      return JSON.parse(value)
    } catch {
      // Nếu parse lỗi thì trả raw string
      return value
    }
  },
  set: (key: string, value: any) => {
    if (value === undefined || value === null) {
      localStorage.removeItem(key)
      return
    }

    // Nếu là string thuần (token) thì lưu trực tiếp
    if (typeof value === 'string') {
      localStorage.setItem(key, value)
    } else {
      localStorage.setItem(key, JSON.stringify(value))
    }
  },
  remove: (key: string) => localStorage.removeItem(key),
  clear: () => localStorage.clear()
}

export const sessionStorageAction = {
  get: (key: string, defaultValue: string = '') => {
    const value = sessionStorage.getItem(key)
    return value ? value : defaultValue
  },
  set: (key: string, value: any) => sessionStorage.setItem(key, value),
  remove: (key: string) => sessionStorage.removeItem(key),
  clear: () => sessionStorage.clear()
}

export const cookieStorageAction = {
  get(key: string): string {
    const cookieArr = document.cookie.split('; ')
    for (let i = 0; i < cookieArr.length; i++) {
      const kv = cookieArr[i].split('=')
      if (kv[0] === key) {
        return kv[1]
      }
    }
    return ''
  },
  set: (key: string, value: any, expire: number | null = 60 * 60 * 0.5) => {
    document.cookie = `${key}=${value}; Max-Age=${expire}`
  },
  remove: (key: string) => {
    document.cookie = `${key}=; Max-Age=-1`
  },
  clear: () => {
    const keys = document.cookie.match(/[^ =;]+(?==)/g)
    if (keys) {
      for (let i = keys.length; i--; ) {
        document.cookie = `${keys[i]}=;expire=${new Date(0).toUTCString()}`
      }
    }
  }
}
