<template>
  <div>
    <h1>默认位置天气数据</h1>
    <div v-if="weather">
      <p>地点：{{ weather.location }}</p>
      <p>温度：{{ weather.temperature }}°C</p>
      <p>体感温度：{{ weather.feelsLike }}°C</p>
      <p>天气：{{ weather.weatherDescription }}</p>
      <p>风力：{{ weather.windScale }} 级</p>
      <p>风向角度：{{ weather.wind360 }}°</p>
      <p>风速：{{ weather.windSpeed }} km/h</p>
      <p>风向：{{ weather.windDir }}</p>
      <p>云量：{{ weather.cloud }}</p>
      <p>露点：{{ weather.dewPoint }}°C</p>
      <p>湿度：{{ weather.humidity }}%</p>
      <p>气压：{{ weather.pressure }} hPa</p>
      <p>能见度：{{ weather.visibility }} km</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      weather: null,  // 用于存储获取到的天气数据
    };
  },
  mounted() {
    this.fetchWeatherData();  // 页面加载时获取默认位置的天气数据
  },
  methods: {
    async fetchWeatherData() {
      try {
        const response = await fetch('http://localhost:8080/weather/now');  // 请求后端获取默认位置的天气数据
        if (response.ok) {
          const data = await response.json();
          this.weather = data;  // 将获取到的天气数据保存到weather变量中
        } else {
          console.error('无法获取天气数据');
        }
      } catch (error) {
        console.error('请求失败:', error);
      }
    }
  }
};
</script>
