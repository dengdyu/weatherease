<template>
  <el-container style="height: 100vh;">
    <!-- 左侧 -->
    <el-aside :width="isCollapse ? '60px' : '180px'" style="background-color: #2f3542; overflow: hidden; ">
      <!-- logo -->
      <div class="logo" style="padding: 10px; color: #fff; font-size: 18px; display: flex; align-items: center;">
        <img style="width: 40px; height: 40px" src="@/assets/image/logo.png" alt="">
        <span v-if="!isCollapse" style="font-size: 13px">气象数据可视化平台</span>
      </div>
      <!-- 菜单 -->
      <el-menu
          router
          default-active="home"
          background-color="#2f3542"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
          class="el-menu-vertical-demo"
          :collapse="isCollapse"
          :collapse-transition="false"
          >
        <el-menu-item index="/home">
          <el-icon><House /></el-icon>
          <span >首页</span>
        </el-menu-item>
        <el-menu-item index="/current">
          <el-icon><Sunrise /></el-icon>
          <span>实时气象数据</span>
        </el-menu-item>
        <el-menu-item index="historical">
          <el-icon><Sunset /></el-icon>
          <span>历史气象数据</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧主页面 -->
    <el-container>
      <!-- 头部 -->
      <el-header style="background-color: #fff; padding: 0 20px; display: flex; justify-content: space-between; align-items: center; height: 60px;">
        <div>
          <el-button  @click="toggleCollapse" v-if = "!isCollapse" type="primary" :icon="Fold" size="large" />
          <el-button  @click="toggleCollapse" v-if = "isCollapse" type="primary" :icon="Expand" size="large" />
        </div>
        <div>
          <el-avatar size="small" src="https://www.example.com/avatar.jpg"></el-avatar>
          <span style="margin-left: 10px;">用户名</span>
        </div>
      </el-header>
      <!-- 主内容区域 -->
      <div>
        <RouterView/>
      </div>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import {Expand, Fold, House, Sunrise, Sunset} from "@element-plus/icons-vue";
// 切换菜单折叠状态
const isCollapse = ref(true)
const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

</script>

<style scoped>
.el-menu-vertical-demo {
  padding-top: 20px;
  height: calc(100% - 70px);
  border-color: #2f3542;
  width: 200px;
  min-height: 400px;
}


.el-avatar {
  vertical-align: middle;
}

.el-row {
  align-items: center;
}
</style>
