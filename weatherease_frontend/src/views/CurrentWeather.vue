<template>
  <div>
    <h1>默认位置天气数据</h1>
    <div v-if="weather.length">
      <!-- 温度体感温度图表 -->
      <div style="border: 1px solid black; height: 250px; width:400px">
        <TempChart
            :temperature="temperatures"
            :feelsLike="feelsLikes"
            :times="times"
            :location="location"
        />
        <div>{{feel}}</div>
      </div>
      <!-- 风力图表 -->
      <div style="border: 1px solid black; height: 300px; width:400px">
        <WindChart
            :windSpeed="windSpeed"
            :windDirection="windDirection"
            :windAngle="windAngle"
            :windScale="windScale"
        />
      </div>
      <!-- 湿度露点图表 -->
      <div style="border: 1px solid black; height: 250px; width:400px">
        <HumidityChart
            :humidities="humidities"
            :dewPoint="dewPoint"
            :times="times"
        />
      </div>
      <!-- 天气描述 -->
      <div style="border: 1px solid black; height: 300px; width:400px">
        <WeatherDescription
            :description="description"
            :cloud="cloud"
            :visibility="visibility"
        />
      </div>
      <!-- 降水柱状图 -->
      <div style="border: 1px solid black; height: 300px; width:400px">
        <PrecipitationChart
            :precipitation="precip"
            :times="times"
        />
      </div>
      <!-- 气压变化折线图 -->
      <div style="border: 1px solid black; height: 300px; width:400px">
        <PressureChart
            :pressure="pressure"
            :times="times"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue';
import TempChart from '@/components/TempChart.vue';
import WindChart from '@/components/WindChart.vue';
import HumidityChart from "@/components/HumidityChart.vue";
import WeatherDescription from "@/components/WeatherDescription.vue";
import PrecipitationChart from "@/components/PrecipitationChart.vue";
import PressureChart from "@/components/PressureChart.vue";

const temperatures = ref([]);  // 温度数据
const feelsLikes = ref([]);    // 体感温度数据
const times = ref([]);        // 时间数据
const location = ref('');
const windSpeed = ref(0);
const windDirection = ref('');
const windAngle = ref(0);
const windScale = ref(0);
const humidities = ref([]);
const dewPoint = ref([]);
const description = ref('');
const cloud = ref(0);
const visibility = ref(0);
const precip = ref([]);
const pressure = ref([]);
const weather = ref([]);      //完整天气数据

// 在组件挂载时获取天气数据
onMounted(() => {
  fetchWeatherData();
});

// 格式化时间为 M-d HH:mm 格式
const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  const month = date.getMonth() + 1; // 月份是从 0 开始的，所以要加 1
  const day = date.getDate();
  const hours = String(date.getHours()).padStart(2, '0'); // 保证小时是 2 位数
  const minutes = String(date.getMinutes()).padStart(2, '0'); // 保证分钟是 2 位数
  return `${month}-${day} ${hours}:${minutes}`;
};

// 获取天气数据的函数
const fetchWeatherData = async () => {
  try {
    const response = await fetch('http://localhost:8080/weather/now');  //请求后端获取默认位置的天气数据
    if (response.ok) {
      const data = await response.json();
      // 提取温度和体感温度
      temperatures.value = data.map(item => item.temperature).reverse();
      feelsLikes.value = data.map(item => item.feelsLike).reverse();
      times.value = data.map(item => formatDate(item.obsTime)).reverse();
      location.value = data[0].location;
      windSpeed.value = data[0].windSpeed;
      windDirection.value = data[0].windDir;
      windAngle.value = data[0].wind360;
      windScale.value = data[0].windScale;
      humidities.value = data.map(item => item.humidity).reverse();
      dewPoint.value = data[0].dewPoint;
      description.value = data[0].weatherDescription;
      cloud.value = data[0].cloud;
      visibility.value = data[0].visibility;
      precip.value = data.map(item => item.precip).reverse();
      pressure.value = data.map(item => item.pressure).reverse();
      weather.value = data;  // 将获取到的天气数据保存到weather变量中
      console.log(description.value);
    } else {
      console.error('无法获取天气数据');
    }
  } catch (error) {
    console.error('请求失败:', error);
  }
};

const feel = computed(() => {
  if (temperatures.value[0] > feelsLikes.value[0]) {
    return '比实际冷一些';
  } else if (temperatures.value[0] < feelsLikes.value[0]) {
    return '比实际热一些';
  } else {
    return '舒适';
  }
});

</script>
