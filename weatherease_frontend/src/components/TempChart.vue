<template>
  <div>
    <div>当前城市：{{ props.location }}</div>
    <div ref="temperatureChart" style="height: 200px; width:380px"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

const temperatureChart = ref(null);

const props = defineProps({
  temperature: Array,
  feelsLike: Array,
  times: Array,
  location: String,
});
console.log('温度:', props.temperature);
console.log('位置:', props.location);

onMounted(() => {
  const myChart = echarts.init(temperatureChart.value);
  const option = {
    title: {
      text: '温度和体感温度',
    },
    tooltip: {
      trigger: 'axis',
    },
    xAxis: {
      type: 'category',
      data: props.times,
      name: '时间',
      axisLabel: {
        rotate: 45,  //让时间标签倾斜
      }
    },
    yAxis: {
      type: 'value',
      name: '温度 (°C)',
    },
    series: [
      {
        name: '温度',
        data: props.temperature,//温度
        type: 'line',
        smooth: true,
        itemStyle: {
          color: '#ff7f50',
        },
      },
      {
        name: '体感温度',
        data: props.feelsLike,  //体感温度
        type: 'line',
        smooth: true,
        itemStyle: {
          color: '#3b9ae1',
        },
      },
    ],
  };
  myChart.setOption(option);
});
</script>

<style scoped>
h3 {
  text-align: center;
  margin-bottom: 10px;
  font-size: 18px;
}

div {
  width: 100%;
}
</style>