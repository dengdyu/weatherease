<template>
  <div>
    <div>
      <div ref="humidityChart" style="width: 380px; height: 200px; ">
      </div>
      <div style="font-size: 16px; display: flex; align-items: center;">
        <p>露点: {{ dewPoint }} °C</p>
        <div>{{humifeel}}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue';
import * as echarts from 'echarts';

const humidityChart = ref(null);

const props = defineProps({
  humidities: Array,
  dewPoint: Number,
  times: Array,
});

onMounted(() => {
  const myChart = echarts.init(humidityChart.value);
  const option = {
    title: {
      text: '湿度',
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
      name: '湿度 (°C)',
    },
    series: [
      {
        name: '湿度',
        data: props.humidities,//温度
        type: 'line',
        smooth: true,
        itemStyle: {
          color: '#ff7f50',
        },
      },
    ],
  };
  myChart.setOption(option);
});

const humifeel = computed(() => {
  if (props.humidities <= 10) {
    return '特别干燥';
  } else if (props.humidities > 10 && props.humidities <= 20) {
    return '较干燥';
  } else if (props.humidities > 20 && props.humidities <= 30) {
    return '干燥';
  } else if (props.humidities > 30 && props.humidities <= 40) {
    return '舒适干燥';
  } else if (props.humidities > 40 && props.humidities <= 50) {
    return '轻微潮湿';
  } else if (props.humidities > 50 && props.humidities <= 60) {
    return '偏潮湿';
  } else if (props.humidities > 60 && props.humidities <= 70) {
    return '潮湿';
  } else if (props.humidities > 70 && props.humidities <= 80) {
    return '较湿润';
  } else {
    return '特别湿润';
  }
});
</script>

<style scoped>

</style>