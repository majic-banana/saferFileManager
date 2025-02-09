<template>
    
    <div class="container">
      <div class="header">
        <img src="@/assets/imgs/logo.png" alt="Logo" class="logo">
        <span class="title">个人网盘系统</span>
      </div>
  
      <div class="content">
        <div class="image-container">
          <img src="@/assets/imgs/bg.png" alt="Background" class="bg-image">
        </div>
        <div class="form-container">
          <div class="form-box">
            <div class="form-title">欢 迎 注 册</div>
            <form @submit.prevent="register">
              <div class="form-item">
                <input type="text" v-model="form.username" placeholder="请输入账号" required>
              </div>
              <div class="form-item">
                <input type="email" v-model="form.email" placeholder="请输入邮箱" required>
              </div>
              <div class="form-item">
                <input type="password" v-model="form.password" placeholder="请输入密码" required>
              </div>
              <div class="form-item">
                <input type="password" v-model="form.confirmPassword" placeholder="确认密码" required>
              </div>
              
              <div class="form-item">
                <button type="submit" class="register-button">注 册</button>
              </div>
              <div class="login-link">
                已有账号？请 <a href="/login">登录</a>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router';
  
  const form = ref({
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  
  const router = useRouter();
  
  const register = async () => {
    if (form.value.password !== form.value.confirmPassword) {
      alert('两次输入的密码不一致！');
      return;
    }
  
    try {
      const response = await axios.post('/register', null, {
        params: {
          username: form.value.username,
          email: form.value.email,
          password: form.value.password,
        }
      });
      
      const res = response.data;
  
      if (res.code === '200') {
        alert('注册成功！请登录。');
        router.push('/login');
      } else {
        alert(res.msg); // 提示错误信息
      }
    } catch (error) {
      console.error('注册请求失败:', error);
      alert('注册失败，请重试。');
    }
  };
  </script>
  
  <style scoped>
  /* 这里的样式保持不变 */
  .container {
    height: 100vh;
    display: flex;
    flex-direction: column;
  }
  
  .header {
    height: 60px;
    display: flex;
    align-items: center;
    padding-left: 20px;
    position: fixed;
    top: 0;
    background-color: white;
    width: 100%;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  }
  
  .logo {
    width: 50px;
  }
  
  .title {
    color: #409EFF;
    font-size: 24px;
    font-weight: bold;
    margin-left: 5px;
  }
  
  .content {
    display: flex;
    flex: 1;
  }
  
  .image-container {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .bg-image {
    max-width: 100%;
    height: auto; /* 保持比例 */
  }
  
  .form-container {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .form-box {
    width: 350px;
    padding: 50px 30px;
    box-shadow: 0 0 10px rgba(0, 0, 0, .2);
    background-color: white;
    border-radius: 5px;
    position: relative;
  }
  
  .form-title {
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 30px;
    color: #333;
  }
  
  .form-item {
    margin-bottom: 20px;
  }
  
  input[type="text"],
  input[type="email"],
  input[type="password"],
  select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
  }
  
  select {
    cursor: pointer;
  }
  
  .register-button {
    width: 100%;
    padding: 10px;
    background-color: #2a60c9;
    border: none;
    border-radius: 4px;
    color: white;
    font-size: 16px;
    cursor: pointer;
  }
  
  .register-button:hover {
    background-color: #1c4a8f;
  }
  
  .login-link {
    text-align: right;
    margin-top: 10px;
  }
  
  .login-link a {
    color: #2a60c9;
  }
  </style>
  