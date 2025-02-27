<template>
  <div>
    <div>风力与风向</div>
    <div style="display: flex; align-items: center;">
      <div ref="windChart" style="width: 250px; height: 250px; position: relative; ">
      </div>
      <div style="margin-left: 20px; font-size: 16px;">
        <p>风速: {{ windSpeed }} km/h</p>
        <p>风力: {{ windScale }} 级</p>
        <p>风向: {{ windDirection }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import * as echarts from 'echarts';

//接收来自父组件的数据
const props = defineProps({
  windSpeed: Number,  //风速
  windDirection: String,  //风向文字
  windAngle: Number,  //风向角度
  windScale: Number  //风力
});

//获取风速和风向数据
const windSpeed = ref(props.windSpeed);
const windScale = ref(props.windScale);
const windDirection = ref(props.windDirection);
const windAngle = ref(props.windAngle);

const windChart = ref(null);

//初始化图表
onMounted(() => {
  initWindChart();
});

// 每当风向或风速数据更新时重新渲染
watch([windSpeed, windScale, windDirection, windAngle], () => {
  updateWindChart();
});

const initWindChart = () => {
  const chart = echarts.init(windChart.value);

  const option = {
    series: [
      {
        type: 'gauge',
        center: ['50%', '50%'],
        radius: '100%',
        min: 0,
        max: 360,
        splitNumber: 8,
        startAngle: 90,  //起始角度调整为90度
        endAngle: 450,   //结束角度调整为450度
        pointer: {
          length: '50%',
          width: 6
        },
        axisLine: {
          lineStyle: {
            width: 8
          }
        },
        axisTick: {
          distance: 10,
          length: 10,
          lineStyle: {
            color: '#eee'
          }
        },
        splitLine: {
          length: 20,
          lineStyle: {
            color: '#eee'
          }
        },
        axisLabel: {
          color: '#333',
          fontSize: 12,
          formatter: (value) => {
            const directions = ['北', '东北', '东', '东南', '南', '西南', '西', '西北'];
            return directions[Math.round(value / 45) % 8];
          }
        },
        detail: {
          show: true,
          offsetCenter: [0, '30%'],
          formatter: '{value}°',
          fontSize: 20
        },
        data: [{ value: windAngle.value}]
      }
    ]
  };

  chart.setOption(option);
};

const updateWindChart = () => {
  const chart = echarts.getInstanceByDom(windChart.value);
  chart.setOption({
    series: [{
      data: [{ value: windAngle.value, name: '风向' }]
    }]
  });
};
</script>


<style scoped>

</style>