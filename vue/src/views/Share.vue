<template>
    <h2 class="title">下载分享</h2>
    
    <div class="share-container">
      <input
        type="text"
        v-model="inputValue"
        placeholder="请输入分享hash"
        class="share-input"
      />
      <button @click="handleShare(inputValue)" class="share-button">下载</button>
    </div>
  </template>
  
<script setup lang="ts">
  import { ref } from 'vue';
  import axios from 'axios';

  
  // 定义输入框的状态
  const inputValue = ref('');

  // 点击按钮时调用的函数
  const handleShare = (value) => {
    

    downloadShare(value);
  };
  

  const downloadShare = (hashName: string) => {

  const a = document.createElement('a');
  a.href = `${axios.defaults.baseURL}/share/download?hashName=${encodeURIComponent(hashName)}`; // 设置下载链接
  a.target = '_blank'; // 在新标签页中打开


  document.body.appendChild(a);
  a.click();
  

  document.body.removeChild(a);
  };
  </script>


  
  <style scoped>
  .title {
    align-items: center;
    padding: 0;
    color:#0056b3
    
  }
  .share-container {
    display: flex; /* 使用 flexbox 布局 */
    align-items: center; /* 垂直居中 */
    justify-content: space-between; /* 水平分散对齐 */
    width: 100%; /* 宽度占满父容器 */
    height: 80%; /* 高度占满父容器 */
  }
  
  .share-input {
    flex: 1; /* 输入框占据剩余空间 */
    padding: 10px; /* 内边距 */
    border: 1px solid #ccc; /* 边框 */
    border-radius: 5px; /* 圆角 */
    margin-right: 10px; /* 右侧间距 */
    box-sizing: border-box; /* 包括内边距和边框在内的总宽高 */
  }

  
  
  .share-button {
    padding: 10px 20px; /* 内边距 */
    border: none; /* 无边框 */
    border-radius: 5px; /* 圆角 */
    background-color: #007bff; /* 按钮背景颜色 */
    color: #fff; /* 字体颜色 */
    cursor: pointer; /* 鼠标悬停时显示手型 */
  }
  
  .share-button:hover {
    background-color: #0056b3; /* 悬停时改变背景颜色 */
  }
  </style>
  