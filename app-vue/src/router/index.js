import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/Home.vue';
import Despesa from "../components/view/despesa/Despesa.vue";
import Fornecedor from "../components/view/fornecedor/Fornecedor.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {path: '/',name: 'home',component: Home},
        {path: '/despesas',name: 'despesa',component: Despesa},
        {path: '/contas',name: 'conta',component: Despesa},
        {path: '/fornecedores',name: 'fornecedor',component: Fornecedor},
        // ,
        // {
        //     path: '/about',
        //     name: 'about',
        //     // route level code-splitting
        //     // this generates a separate chunk (About.[hash].js) for this route
        //     // which is lazy-loaded when the route is visited.
        //     component: () => import('../views/AboutView.vue')
        // }
    ]
})

export default router