<template>
    <!-- header -->
    <div class="header">
      <div class="header-left">
        <span class="logo"></span>
        <h2 class="title">网盘管理系统:2210301402</h2>
      </div>
      <div class="header-right">
        <div class="user-info" @mouseenter="showMenu = true" @mouseleave="showMenu = false">
          <img class="user-avatar" :src="userAvatar" alt="User Avatar" />
          <span class="username">{{ username }}</span>
          <div v-if="showMenu" class="dropdown-menu">
            <div class="dropdown-item" @click="changePswd">修改密码</div>
            <div class="dropdown-item" @click="logout">退出登录</div>
          </div>
        </div>
      </div>
    </div>
  

    <div class="layout">
      <!-- navigator -->
      <div class="navigator">
        <div class="title-div">
          <div class="selection" @click="goFileTree">
            目录结构
          </div>
          <div class="selection" @click="goShare">
            分享下载
          </div>
          <div class="selection" @click="goUpload">
            上传文件
          </div>
          <div class="selection" @click="goUserManager">
            用户管理
          </div>

        </div>
      </div>
  
      <!-- display -->
      <div class="display">
        <div class="fileTree-template" v-if="isShowFileTree">
          <h2 class="directory-title">文件目录</h2>
          <RecursiveDisplay :data="fileTree"></RecursiveDisplay>
        </div>
        
        <div class="other-template" v-if="!isShowFileTree">
          <RouterView></RouterView>
        </div>

        
        
      </div>


    </div>
  </template>
  




<script setup lang="ts" name="FileManager">

import { onMounted, ref } from 'vue';


import axios from 'axios';

import {type fileNode } from '@/type/fileNode';
import {useRouter } from 'vue-router';

import RecursiveDisplay from '@/components/RecursiveDisplay.vue'
import { useUser } from '@/store/User';








// -------header start------------
//data source
const userAvatar = ref('https://via.placeholder.com/40'); // 默认头像链接
const username = ref(useUser().username)
const showMenu = ref(false); // 控制下拉菜单的显示



// methods
const logout = () => {
  router.replace("/login")
};



const changePswd = () => {
  // 处理修改密码的逻辑:toDo
  console.log("Changing password...");
};

// --------------header end----------------



// navigator-showFileTree
let isShowFileTree = ref(false)
const router = useRouter();
let fileTree = ref<fileNode>({
        id: 0,
        name: 'loading',
        path: 'loading',
        type: 'folder',
        size: 0,
        owner: null,
        children: []
    });
async function goFileTree(){
    
    try {
        const response = await axios.get('/file/selectAll', {
        withCredentials: true, // 确保 Cookies 被发送
        });

        if (response.data.code === '200') {
        fileTree.value = response.data.data; // 更新 ref 的值
        } else if (response.data.code === '401') {
            router.push('/login');
            return   //必须要有，不然无法结束无法访问login
        } else {
            alert(response.data.msg);
        }
    } catch (error) {
        alert("获取数据失败");
    }


    isShowFileTree.value = true;

    return 
}
function goShare(){
  isShowFileTree.value = false;
  router.replace({path:'/share'});
}

function goUpload(){
  isShowFileTree.value = false
  router.replace({
    path: '/upload'
  })
}




async function goUserManager(){
  isShowFileTree.value = false;
  router.push({path: '/userManager'});
  }
  

  



</script>




















<style lang="scss" scoped>
  // ---------header css----------------
  .header {
    display: flex; /* 使用 flex 布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
    background-color: #29537d; /* 蓝色背景 */
    padding: 0 20px; /* 左右内边距 */
    height: 60px; /* 高度 */
    color: white; /* 字体颜色 */
  }
  
  .header-left {
    display: flex; /* 左侧内容使用 flex 布局 */
    align-items: center; /* 垂直居中 */
  }

  .logo {
    background-image: url('@/assets/imgs/logo.png'); /* 替换为您的图标路径 */
    background-size: contain; /* 确保图标适应 */
    background-repeat: no-repeat; /* 不重复 */
    width: 40px; /* 图标宽度 */
    height: 40px; /* 图标高度 */
    margin-right: 10px; /* 图标与标题间距 */
  }

  

  .header-right {
    display: flex; /* 右侧内容使用 flex 布局 */
    align-items: center; /* 垂直居中 */
  }

  .user-info {
    display: flex; /* 用户信息使用 flex 布局 */
    flex-direction: column; /* 垂直排列 */
    align-items: center; /* 水平居中 */
    position: relative; /* 为下拉菜单提供定位 */
  }

  /* 用户头像 */
  .user-avatar {
    width: 35px; /* 用户头像宽度 */
    height: 35px; /* 用户头像高度 */
    border-radius: 50%; /* 圆形头像 */
    margin-top: 10px;
    margin-bottom: 0px; /* 头像与用户名之间的间距 */
  }

  //------------header css end----------------

  
  .layout {
    display: flex; /* 使用 flex 布局 */
  }

  .navigator {
    padding: 0;
    border: 1px solid #ccc;
    border-radius: 5px;
    
    width: 15%; /* 固定宽度 */
    height: 100vh; /* 高度等于视口高度 */
    box-sizing: border-box; /* 包含边框和内边距在内的总高度 */
    background-color: #406994;
  }

  .title-div {
    margin: 0; /* 去掉上下外边距 */
    padding-top: 0px; /* 减少上填充 */
    font-size: 20px; /* 根据需要设置字体大小 */
    text-align: left;
    
  }

  .title {
    margin: 0; /* 去掉左右内边距 */
    padding-bottom: 0px;
    color: white;
  }

  .display {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-bottom: 20px;
    width: 85%; /* 固定宽度 */
    height: 100vh; /* 高度等于视口高度 */
    box-sizing: border-box; /* 包含边框和内边距在内的总高度 */
  }
  .directory-title {
    align-items: center;
    padding: 0;
    color:#0056b3
    
    
  }
  .fileTree-template {
    width: 100%;
    height: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    background-color: #fff;
    box-sizing: border-box;
    margin-bottom: 1%;
    overflow: auto; /* 或者使用 overflow: hidden; */
}
  .other-template {
    width: 100%; /* 宽度占满父容器 */
    height: 100%; /* 高度占满父容器 */
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    background-color: #fff; /* 背景颜色 */
    box-sizing: border-box; /* 包括内边距和边框在内的总宽高 */
  }






  // Dropdown menu styles
.dropdown-menu {
  position: absolute; /* 绝对定位 */
  top: 100%; /* 在用户信息下方显示 */
  right: 0; /* 右对齐 */
  background-color: white; /* 背景颜色为白色 */
  border: 1px solid #ccc; /* 边框颜色 */
  border-radius: 5px; /* 圆角 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2); /* 阴影效果 */
  z-index: 1000; /* 确保菜单在其他元素上方 */
  width: 150px; /* 固定宽度 */
}

.dropdown-item {
  padding: 10px 15px; /* 内边距 */
  cursor: pointer; /* 鼠标悬停时显示为可点击 */
  color: #333; /* 字体颜色 */
  transition: background-color 0.2s ease; /* 背景颜色过渡效果 */
}

/* 鼠标悬停时更改背景颜色 */
.dropdown-item:hover {
  background-color: #f5f5f5; /* 悬停时背景颜色 */
}

.selection {
    padding: 5px; /* 内边距 */
    cursor: pointer; /* 鼠标悬停时显示为可点击 */
    color: white; /* 字体颜色 */
    background-color: #406994;
    font-size: 18px; /* 字体大小 */
    transition: background-color 0.2s ease; /* 背景颜色过渡效果 */
    
    /* 添加上下边界样式 */
    border-top: 1px solid rgba(0, 0, 0, 0.1); /* 上边界 */
    border-bottom: 1px solid rgba(0, 0, 0, 0.1); /* 下边界 */
}

/* 鼠标悬停时更改背景颜色 */
.selection:hover {
    background-color: #2b4a6e; /* 悬停时背景颜色 */
}



</style>
