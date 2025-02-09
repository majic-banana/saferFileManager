<template>
  <div>
    <div
      class="file-item"
      @mouseover="hover = true"
      @mouseleave="hover = false"
      @contextmenu.prevent="showContextMenu($event, data)"
    >
      <div class="file-info" @click="toggle(data)">
        <span>{{ data.name }}</span>
        <span v-if="data.type === 'folder'">{{ isOpen ? '(-)' : '(+)' }}</span>
      </div>
      
      <!-- 右键菜单 -->
      <div v-if="contextMenuVisible && contextMenuFile === data" 
           class="context-menu" 
           :style="{ top: contextMenuY + 'px', left: contextMenuX + 'px' }">
        <button @click.stop="handleDelete(data.path, data.type)">删除</button>
        <button @click.stop="handleShare(data.path, data.type)">分享</button>
        <button @click.stop="handleDownload(data.path, data.type)">下载</button>
      </div>

      <div v-if="data.type === 'folder' && isOpen">
        <RecursiveDisplay
          v-for="child in data.children"
          :key="child.id"
          :data="child"
        />
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onBeforeMount,onMounted, type Ref } from 'vue';
import { type fileNode } from '@/type/fileNode';
import axios from 'axios';


const props = defineProps<{data:fileNode}>();

const isOpen = ref(false);
const hover = ref(false);
const contextMenuVisible = ref(false);
const contextMenuX = ref(0);
const contextMenuY = ref(0);
const contextMenuFile = ref<fileNode | null>(null);









const toggle = (item: fileNode) => {
  if (item.type === 'folder') {
    isOpen.value = !isOpen.value;
  }
};

const showContextMenu = (event: MouseEvent, item: fileNode) => {
  contextMenuX.value = event.clientX;
  contextMenuY.value = event.clientY + 15; // 下移15px，避免与右键点击重叠
  contextMenuFile.value = item; // 确保上下文菜单指向当前项
  contextMenuVisible.value = true;
};

const handleDelete = async (path: string, type: string) => {
  
  contextMenuVisible.value = false; // 隐藏菜单

  if(type == 'folder'){
    alert("不支持文件夹下载");
    return
  }

  const response = await axios.get('/file/delete', {
      params: {
        path: path
      },
      // withCredentials: true,
    });

    alert(response.data.msg);
    window.location.reload();

};



const handleShare = async (path: string, type: string) => {
  if (type === 'folder') {
    alert('暂不支持分享文件夹');
    return;
  }

  try {
    const response = await axios.get('/share/addShare', {
      params: {
        path: path
      },
    });

    if(response.data.code == '200'){
        alert(`分享成功：\n ${response.data.data} ` )
    }else{
        alert(response.data.msg)
    }


    
  } catch (error) {
    console.error('分享文件时发生错误:', error);
    alert('分享文件时发生错误，请稍后再试。');
  }
};

const handleDownload = (path: string, type: string) => {
  
  contextMenuVisible.value = false; // 隐藏菜单

  if(type == 'folder'){
    alert("不支持文件夹下载");
    return
  }

  // 创建一个临时的链接元素
  const a = document.createElement('a');
  a.href = `${axios.defaults.baseURL}/file/download?path=${encodeURIComponent(path)}`; // 设置下载链接
  a.target = '_blank'; // 在新标签页中打开

  // 模拟点击下载
  document.body.appendChild(a);
  a.click();
  
  // 清理元素
  document.body.removeChild(a);
};

// 处理点击空白区域以关闭上下文菜单
document.addEventListener('click', () => {
  contextMenuVisible.value = false;
});
</script>

<style scoped>
.file-item {
  padding-left: 20px;
  border: #ccc;
  cursor: pointer;
  transition: background-color 0.2s;
}

.file-item:hover {
  background-color: white;
  border: 1px solid #aaa; /* 鼠标悬浮时添加边框 */
}

.context-menu {
  position: absolute;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.context-menu button {
  border: none; /* 去掉按钮边框 */
  background-color: white; /* 按钮背景 */
  cursor: pointer;
  padding: 5px 10px;
  margin: 2px 0; /* 按钮间隔 */
  transition: background-color 0.2s;
}

.context-menu button:hover {
  background-color: #f0f0f0; /* 鼠标悬浮时的背景 */
}
</style>
