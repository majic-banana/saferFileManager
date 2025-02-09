import {createRouter,createWebHistory} from 'vue-router'



const router = createRouter({
    history:createWebHistory(),
    routes:[
        {
            path:'/',
            name:'home',
            component: () => import('@/views/Manager.vue'),
            children: [
                {
                    name:'upload_n',
                    path:'upload',
                    component: () => import('@/views/UpLoad.vue'),
                },
                {
                    name:'Share',
                    path:'share',
                    component: () => import('@/views/Share.vue'),
                },
                {
                    name:'admin-userManger',
                    path:'userManager',
                    component: () => import('@/views/UserManager.vue'),
                    
                },

                


            ],


        },
        {
            path:'/login',
            name:'Login',
            component:()=>import('@/views/Login.vue'),

        },
        {
            path:'/register',
            name:'register',
            component: () =>import('@/views/Register.vue'),
        },
        
    ]
});

export default router