import { createRouter, createWebHistory } from 'vue-router'
import UserLogin from "@/views/UserLogin.vue";
import UserRegister from "@/views/UserRegister.vue";
import HomePage from '@/views/HomePage.vue'
import HistoryWeather from '@/views/HistoryWeather.vue'
import CurrentWeather from "@/views/CurrentWeather.vue";
import NotFound from '@/views/404NotFound.vue'
import Manager from "@/views/PageManager.vue";

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
