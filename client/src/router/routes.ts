import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import { ROUTES_CONSTANTS } from '@/constants/path';
import { useAuthStore } from '@/stores/auth';
import { ROLES } from '@/constants/roles';

export const routes: RouteRecordRaw[] = [
    // ✅ Trang login
    {
        path: '/',
        redirect: ROUTES_CONSTANTS.LOGIN.path,
    },
    {
        path: ROUTES_CONSTANTS.LOGIN.path,
        name: ROUTES_CONSTANTS.LOGIN.name,
        component: () => import('@/pages/login/Login.vue'),
    },

    // ✅ Layout nhân viên / admin
    {
        path: ROUTES_CONSTANTS.STAFF_HOME.path,
        name: ROUTES_CONSTANTS.STAFF_HOME.name,
        component: () => import('@/layout/Staff.vue'),
        meta: { requiresAuth: true, roles: [ROLES.STAFF, ROLES.ADMIN] },
        children: [
            {
                path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.path, // goods
                name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.name,
                component: () => import('@/pages/staff/goods/Goods.vue'),
                children: [
                    {
                        path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.CATEGORIES.path, // category
                        name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.CATEGORIES.name,
                        component: () => import('@/pages/staff/category/Category.vue'),
                    },
                    {
                        path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PRODUCT.path, // product
                        name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PRODUCT.name,
                        component: () => import('@/pages/staff/product/Product.vue'),
                    },
                    {
                        path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PRODUCT_DETAIL.path, // product detail
                        name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PRODUCT_DETAIL.name,
                        component: () => import('@/pages/staff/productdetail/ProductDetail.vue'),
                    },
                    {
                        path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.path, // attribute
                        name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.name,
                        component: () => import('@/pages/staff/attribute/Attribute.vue'),
                        children: [
                            {
                                path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.COLOR.path,
                                name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.COLOR.name,
                                component: () => import('@/pages/staff/color/Color.vue'),
                            },
                            {
                                path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.ISO.path,
                                name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.ISO.name,
                                component: () => import('@/pages/staff/iso/Iso.vue'),
                            },
                            {
                                path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.PROCESSOR.path,
                                name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.PROCESSOR.name,
                                component: () => import('@/pages/staff/processor/Processor.vue'),
                            },
                            {
                                path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.RESOLUTION.path,
                                name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.RESOLUTION.name,
                                component: () => import('@/pages/staff/resolution/Resolution.vue'),
                            },
                            {
                                path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.SENSOR.path,
                                name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.SENSOR.name,
                                component: () => import('@/pages/staff/sensor/Sensor.vue'),
                            },
                            {
                                path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.STORAGE_CAPACITY.path,
                                name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.STORAGE_CAPACITY.name,
                                component: () => import('@/pages/staff/storagecapacity/StorageCapacity.vue'),
                            },
                            {
                                path: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.VERSION.path,
                                name: ROUTES_CONSTANTS.STAFF_HOME.children.GOODS.children.PROPERTIES.children.VERSION.name,
                                component: () => import('@/pages/staff/version/Version.vue'),
                            },
                        ],
                    },
                ],
            },
        ],
    },

    // ✅ Trang lỗi
    {
        path: ROUTES_CONSTANTS.UNAUTHORIZED.path,
        name: ROUTES_CONSTANTS.UNAUTHORIZED.name,
        component: () => import('@/pages/401/Unauthorized.vue'),
    },
    {
        path: ROUTES_CONSTANTS.FORBIDDEN.path,
        name: ROUTES_CONSTANTS.FORBIDDEN.name,
        component: () => import('@/pages/403/Forbidden.vue'),
    },
    {
        path: ROUTES_CONSTANTS.NOT_FOUND.path,
        name: ROUTES_CONSTANTS.NOT_FOUND.name,
        component: () => import('@/pages/404/NotFound.vue'),
    },
];

export const router = createRouter({
    history: createWebHistory(),
    routes,
});

// ✅ Middleware kiểm tra đăng nhập & quyền
router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    const isLogin = !!authStore.user;
    const userRole = authStore.user?.role;

    if (to.meta.requiresAuth && !isLogin) {
        return next({ path: ROUTES_CONSTANTS.LOGIN.path });
    }

    if (to.meta.roles && !to.meta.roles.includes(userRole)) {
        return next({ path: ROUTES_CONSTANTS.FORBIDDEN.path });
    }

    next();
});
