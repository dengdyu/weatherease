import { createRouter, createWebHistory } from 'vue-router'
import UserLogin from "@/view/UserLogin.vue";
import UserRegister from "@/view/UserRegister.vue";
import HomePage from '@/view/HomePage.vue'
import HistoryWeather from '@/view/HistoryWeather.vue'
import CurrentWeather from "@/view/CurrentWeather.vue";
import NotFound from '@/view/404NotFound.vue'
import Manager from "@/view/PageManager.vue";

const routes = [
    { path: '/login', component: UserLogin },
    { path: '/register', component: UserRegister },
    { path: '/history', component: HistoryWeather },
    { path: '/notFound', component: NotFound },
    { path: '/:pathMatch(.*)*', redirect: '/notFound' },
    { path: '/', component: Manager,
        children: [
            { path: 'home', component: HomePage },
            { path: 'current', component: CurrentWeather },
        ]
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
