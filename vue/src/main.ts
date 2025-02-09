

import { createApp} from 'vue'
import App from './App.vue'
import axios from 'axios';
import router from './router'

const app = createApp(App)

import { createPinia } from 'pinia'
const pinia = createPinia()


// 配置 Axios
axios.defaults.baseURL = 'http://localhost:9090'

axios.defaults.withCredentials = true   //存储和发送后端发来的cookie
// 将 Axios 添加到 Vue 原型上，以便全局使用
app.config.globalProperties.$axios = axios;

app.use(router)
app.use(pinia)

app.mount('#app')

