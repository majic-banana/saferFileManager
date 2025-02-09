<template>
    <div class="modal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <h2 class="title">{{ isEdit ? '编辑用户' : '添加用户' }}</h2>
        <form @submit.prevent="submitUser">
          <div class="user-info">
            <label>用户:</label>
            <input class="user-info-input" type="text" v-model="user.name" required />
          </div>
          <div>
            <label>角色:</label>
            <input class="user-info-input" type="text" v-model="user.role" required />
          </div>
          <div>
            <label>邮箱:</label>
            <input class="user-info-input" type="email" v-model="user.email" />
          </div>
          <div>
            <label>密码:</label>
            <input class="user-info-input" type="password" v-model="user.password" required />
          </div>
          <button class="submit-button" type="submit">{{ isEdit ? '更新' : '添加' }}</button>
        </form>
        
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, defineProps, defineEmits } from 'vue';
  import { type User } from '@/type/User';
    import axios from 'axios';
  
  // 接收编辑状态的 props
  const props = defineProps<{
    isEdit: boolean;
  }>();
  
  // 定义 emit 事件
  const emit = defineEmits<{
    (e: 'submit', code: string): void; // 定义 submit 事件及其参数
    (e: 'close'): void; // 定义 close 事件
  }>();
  
  // 定义用户数据
  const user = ref<User>({
    id: 0,
    name: '',
    email: '',
    role: '',
    password: '',
  });
  
  // 提交用户信息并触发事件
  const submitUser = async () => {

    const response = await ApiCall(user.value);
    
    // Emit the response code to the parent component
    emit('submit', response.data.code); // 触发 submit 事件并传递状态码
    closeModal();
  };
  
  // 模拟 API 调用函数
  const ApiCall = async (user: User) => {
    let temp = 'addUser';
    if(props.isEdit){
        temp = 'updateUser'
    }
    
    return axios.post(`/admin/${temp}`,user)
    
  };
  
  // 关闭模态框
  const closeModal = () => {
    emit('close'); // 触发 close 事件
  };
  </script>
 
 














  <style scoped>
  .modal {
    position: fixed;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
  }

  
  .modal-content {
    position: relative;
    margin: 15% auto;
    padding: 20px;
    width: 80%;
    max-width: 500px;
    background-color: white;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }

  .title {
    margin:3px
  }
  
  .close {
    position: absolute;
    right: 15px;
    top: 10px;
    cursor: pointer;
  }

  .user-info-input{
    margin-left: 5%;
    width: 80%;
  }

  .submit-button {
    margin-top: 3%;
    margin-left: 85%;
    padding: 5px ;
    margin-right: 3%;
    border: none;
    border-radius: 5px;
    background-color: #28a745;
    color: white;
    cursor: pointer;
  }

  </style>
  