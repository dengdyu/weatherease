<template>
  <div class="weather-description">
    <!-- 天气描述 -->
    <div class="weather-text">
      <h2>{{ weatherDescription }}</h2>
    </div>

    <!-- 天气图标 -->
    <div class="weather-icon">
      <img :src="weatherIcon" alt="weather-icon" style="width: 50px; height: 50px;" />
    </div>

    <!-- 云量和可见度 -->
    <div class="weather-details">
      <p><strong>云量:</strong> {{ cloudiness }} %</p>
      <p><strong>能见度:</strong> {{ visibility }} km</p>
    </div>
  </div>
</template>

<script setup>
  import { ref, onMounted, computed } from 'vue';


  const props = defineProps({
    description: String,
    cloud: Number,
    visibility: Number,
  });

  const weatherIcon = computed(() => {
    //根据天气描述返回对应的图标路径
    switch (props.description) {
      case '晴':
        return new URL('@/assets/icons/晴.png', import.meta.url).href;
      case '阴':
        return new URL('@/assets/icons/阴.png', import.meta.url).href;
      case '多云':
        return new URL('@/assets/icons/多云.png', import.meta.url).href;
      case '小雨':
        return new URL('@/assets/icons/小雨.png', import.meta.url).href;
      case '中雨':
        return new URL('@/assets/icons/中雨.png', import.meta.url).href;
      case '大雨':
        return new URL('@/assets/icons/大雨.png', import.meta.url).href;
      case '暴雨':
        return new URL('@/assets/icons/暴雨.png', import.meta.url).href;
      case '大暴雨':
        return new URL('@/assets/icons/大暴雨.png', import.meta.url).href;
      case '阵雨':
        return new URL('@/assets/icons/阵雨.png', import.meta.url).href;
      case '雷阵雨':
        return new URL('@/assets/icons/雷阵雨.png', import.meta.url).href;
      case '雾':
        return new URL('@/assets/icons/雾.png', import.meta.url).href;
      default:
        console.log('无数据的天气描述:', props.description);
        return new URL('@/assets/icons/无数据.png', import.meta.url).href;
    }
  });

  const weatherDescription = ref(props.description);
  const cloudiness = ref(props.cloud);
  const visibility = ref(props.visibility);

  onMounted(() => {
    weatherDescription.value = props.description;
    cloudiness.value = props.cloud;
    visibility.value = props.visibility;
  });

  console.log('天气描述:', props.description);
</script>

<style scoped>

</style>