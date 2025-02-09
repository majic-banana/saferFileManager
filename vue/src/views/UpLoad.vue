<template>
    <div class="upload-container">
      <h2>文件上传</h2>
      <form @submit.prevent="uploadFile">
        <div class="form-item">
          <label for="uploadPath">上传路径：</label>
          <input type="text" id="uploadPath" v-model="uploadPath" placeholder="请输入上传路径(以用户名为根路径/)" required />
        </div>
        <div class="form-item">
          <label for="fileInput">选择文件：</label>
          <input type="file" id="fileInput" @change="onFileChange" required />
        </div>
        <div class="form-item">
          <button type="submit" class="upload-button">上传文件</button>
        </div>
      </form>
      <div v-if="uploading" class="loading">上传中，请稍候...</div>
      <div v-if="uploadResponse" class="response">{{ uploadResponse }}</div>
    </div>
  </template>
  
<script setup>
    import { ref } from 'vue';
    import axios from 'axios';
    
    import { useRouter } from 'vue-router';

    const router = useRouter();
    
    const uploadPath = ref('');
    const selectedFile = ref(null);
    const uploading = ref(false);
    const uploadResponse = ref('');
    
    const onFileChange = (event) => {
        selectedFile.value = event.target.files[0];
    };
    
    const uploadFile = async () => {
        if (!uploadPath.value || !selectedFile.value) {
        alert('请填写上传路径并选择文件');
        return;
        }
    
        const formData = new FormData();
        formData.append('file', selectedFile.value);
        formData.append('path', uploadPath.value);
    
        uploading.value = true;
        uploadResponse.value = '';
    
        try {
        const response = await axios.post('/file/upload', formData, {
            headers: {
            'Content-Type': 'multipart/form-data',
            },
        });

        
        if(response.data.code == '401'){
            router.replace('/login');
            return
        }


        uploadResponse.value = response.data.msg
        
        
    
        
        } catch (error) {
        uploadResponse.value = '文件上传失败：' + (error.response?.data.message || error.message);
        } finally {
        uploading.value = false;
        }
    };
  </script>
  

  <style scoped>
  
  
  h2 {
    text-align: center;
    color: #333;
  }
  
  .form-item {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    margin-bottom: 5px;
  }
  
  input[type="text"],
  input[type="file"] {
    width: 90%; /* 宽度为100% */
    
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .upload-button {
    width: 9%;
    padding: 10px;
    background-color: #2a60c9;
    border: none;
    border-radius: 4px;
    color: white;
    font-size: 16px;
    cursor: pointer;
  }
  
  .upload-button:hover {
    background-color: #1c4a8f;
  }
  
  .loading {
    margin-top: 10px;
    text-align: center;
    color: #666;
  }
  
  .response {
    margin-top: 10px;
    text-align: center;
    color: #2a60c9;
  }
  </style>
  