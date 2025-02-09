
// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import { type User } from '@/type/User'
// 定义并暴露一个store
export const useUser = defineStore('count',{
  // 动作
  actions:{},
  // 状态
  state(){
    return {
      username : 'Anonymous',                //当前用户名
    }
  },
  // 计算
  getters:{}
})