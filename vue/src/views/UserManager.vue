<template>
    <div class="user-management">
      <div class="header">
        <input
          type="text"
          v-model="searchQuery"
          placeholder="toDo:search"
          class="search-input"
        />
        <button @click="openAddUserModal" class="add-user-button">添加用户</button>
      </div>
  
      <div class="user-list">
        <!-- 属性栏 -->
        <div class="user-attributes">
          <span class="attribute">用户名</span>
          <span class="attribute">角色</span>
          <span class="attribute">邮箱</span>
          <span class="attribute">操作</span>
        </div>
        <div class="user-items" v-for="user in userData" :key="user.id">
          <div class="user-info">
            <span class="attribute">{{ user.name }}</span>
            <span class="attribute">{{ user.role }}</span>
            <span class="attribute">{{ user.email || '无' }}</span>
          </div>
          <div class="user-actions">
            <span class = "attribute-action">
                <button @click="openEditUserModal(user)">更改</button>
                <button @click="deleteUser(user)">删除</button>
            </span>
            
          </div>
        </div>
      </div>
  
      <UserInput
        v-if="isModalVisible"
        :isEdit="isEditMode"
        @close="closeModal"
        @submit="handleUserSubmit"
      ></UserInput>
    </div>
  </template>
  
  
<script setup lang="ts">
  import { onBeforeMount, ref} from 'vue';
  import UserInput from '@/components/UserInput.vue'; // 引入用户模式组件

  import { type User } from '@/type/User';
  import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

  const route = useRoute()
  // 接收路由传参（后端返回的数据结构）
  let userData =  ref<User[]>()

  // 定义响应式状态
  const searchQuery = ref('');
  const isModalVisible = ref(false);
  const currentUser = ref<User>({
    id: 0,
    name: "loading",
    email:"loading",
    role: 'loading',
    password: 'loading',
  });
  const isEditMode = ref(false);
  const router = useRouter()


  onBeforeMount(async () => {
    const response = await axios.get("/admin/showAllUsers")
  
    if(response.data.code == '401'){
        router.replace("/login");
    }
    else if(response.data.code == '200'){


    userData.value = response.data.data;

    }
})
  
  
  // 打开添加用户模态框
  const openAddUserModal = () => {
    currentUser.value = { id: 0, name: '', role: '', password: '', email: '' }; // 重置用户对象
    isEditMode.value = false; // 设置为添加模式
    isModalVisible.value = true; // 显示模态框
  };
  
  // 打开编辑用户模态框
  const openEditUserModal = (user) => {
    currentUser.value = user; // 复制用户数据用于编辑
    isEditMode.value = true; // 设置为编辑模式
    isModalVisible.value = true; // 显示模态框
  };
  
  // 处理用户提交
  const handleUserSubmit = (code: string) => {
    if(code == '200'){
        alert("操作成功")
        window.location.reload()
        router.replace("/userManager")
    }
    else{
        alert("操作失败")
    }

    closeModal(); // 关闭模态框
  };
  
  // 关闭模态框
  const closeModal = () => {
    isModalVisible.value = false;
  };
  
  // 删除用户
  const deleteUser = async (user) => {
    const response = await axios.post("/admin/deleteUser",user);

    alert(response.data.msg)
    window.location.reload()
    router.replace("/userManager")
  };
  </script>
  






  <style scoped>
  .user-management {
    width: 100%;
    height: 100%;
    padding: 20px;
    box-sizing: border-box;
  }
  
  .header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
  }
  
  .search-input {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    flex: 1;
    margin-right: 10px;
  }
  
  .add-user-button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: white;
    cursor: pointer;
  }
  
  .user-list {
    margin-top: 20px;
  }
  
  .user-attributes {
    display: flex;
    font-weight: bold; /* 加粗属性栏 */
    border-bottom: 2px solid #007bff; /* 属性栏下方的蓝色边框 */
    padding-bottom: 10px; /* 属性栏与内容之间的间距 */
  }

  .user-items {
    display: flex;

  }
  
  .attribute {
    flex: 1; /* 每个属性栏宽度均匀分配 */
    text-align: center; /* 中间对齐 */
    margin-bottom: 5px;
  }
  .attribute-action {
    
    flex: 1; /* 每个属性栏宽度均匀分配 */
    text-align: center; /* 中间对齐 */
    
    margin-bottom: 5px;
  }
  
  
  
  .user-info {
    display: flex;
    
    width: 75%;
  }
  
  
  
  .user-actions{
    display: flex;
    
    
    width: 25%;

  }
  

  
  .user-actions button {
    padding: 5px ;
    margin-right: 3%;
    border: none;
    border-radius: 5px;
    background-color: #28a745;
    color: white;
    cursor: pointer;
  }
  </style>
  
  