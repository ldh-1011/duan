export const ROUTES_CONSTANTS = {
    LOGIN: { path: '/login', name: 'login' },

    STAFF_HOME: {
        path: '/staff',
        name: 'staff',
        children: {
            GOODS: {
                path: 'goods',
                name: 'staff-goods',
                children: {
                    CATEGORIES: { path: 'category', name: 'category' },
                    PRODUCT: { path: 'product', name: 'product' },
                    PRODUCT_DETAIL: { path: 'productdetail', name: 'productdetail' },
                    PROPERTIES: {
                        path: 'attribute',
                        name: 'attribute',
                        children: {
                            COLOR: { path: 'color', name: 'color' },
                            ISO: { path: 'iso', name: 'iso' },
                            PROCESSOR: { path: 'processor', name: 'processor' },
                            RESOLUTION: { path: 'resolution', name: 'resolution' },
                            SENSOR: { path: 'sensor', name: 'sensor' },
                            STORAGE_CAPACITY: { path: 'storage-capacity', name: 'storage-capacity' },
                            VERSION: { path: 'version', name: 'version' }
                        }
                    }
                }
            }
        }
    },

    FORBIDDEN: { path: '/error/403', name: 'forbidden' },
    UNAUTHORIZED: { path: '/error/401', name: 'unauthorized' },
    NOT_FOUND: { path: '/:pathMatch(.*)*', name: 'not-found' },
    REDIRECT: { path: '/redirect', name: 'redirect' }
}
