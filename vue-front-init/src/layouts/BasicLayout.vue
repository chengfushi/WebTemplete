<template>
  <a-layout class="basic-layout">
    <!-- 顶部导航栏 -->
    <GlobalHeader />

    <!-- 主要内容区域 -->
    <a-layout-content class="main-content">
      <router-view />
    </a-layout-content>

    <!-- 底部版权信息 -->
    <GlobalFooter />
  </a-layout>
</template>

<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
</script>

<style scoped>
.basic-layout {
  background: none;
  min-height: 100vh; /* 确保布局至少占满整个视口高度 */
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1; /* 占据剩余空间 */
  padding: 24px;
  background: white;
}
</style>
