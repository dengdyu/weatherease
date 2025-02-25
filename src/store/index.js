import { createStore } from 'vuex'
import axios from "axios";

export default createStore({
    //state用于存储应用的状态，包含全局变量
    state: {
        currentWeather: null,
        historicalData: [],
        userInfo: null,
    },
    //mutations是同步方法，用来修改state中的数据
    mutations: {
        SET_CURRENT_WEATHER(state, data) {
            state.currentWeather = data
        },
        SET_HISTORICAL_DATA(state, data) {
            state.historicalData = data
        },
        SET_USER_INFO(state, data) {
            state.userInfo = data
        },
    },
    //actions是异步方法，用来处理数据获取和API调用
    actions: {
        fetchCurrentWeather({ commit }) {
            axios.get('/api/weather/current').then(response => {
                   commit('SET_CURRENT_WEATHER', response.data)
                 })
        },
    },
    modules: {},
})
