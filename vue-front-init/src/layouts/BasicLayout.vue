<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh; display: flex; flex-direction: column">
      <a-layout-header class="header">
        <GlobalHeader />
      </a-layout-header>

      <a-layout-content class="content" style="flex: 1">
        <router-view />
      </a-layout-content>

      <a-layout-footer class="footer">
        <GlobalFooter/>
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue';
</script>

<style scoped>
#basicLayout .footer {
  background: #1e1e1e;
  color: #d9d9d9;
  padding: 40px 20px 15px;
}

/* 其余原有区域保持不动 */
.content {
  background: linear-gradient(to right, #fefefe, #fff);
  padding: 20px;
}

.header {
  padding-inline: 20px;
  margin-bottom: 16px;
  color: unset;
  background: white;
}
</style>
