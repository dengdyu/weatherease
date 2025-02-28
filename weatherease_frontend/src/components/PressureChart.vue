<template>
  <div>
    <div>
      <div ref="pressureChart" style="width: 380px; height: 200px; "/>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue';
import * as echarts from 'echarts';

const pressureChart = ref(null);

const props = defineProps({
  pressure: Array,
  times: Array,
});

onMounted(() => {
  const myChart = echarts.init(pressureChart.value);
  const option = {
    title: {
      text: '大气压强',
    },
    tooltip: {
      trigger: 'axis',
    },
    xAxis: {
      type: 'category',
      data: props.times,
      name: '时间',
      axisLabel: {
        rotate: 45,
      }
    },
    yAxis: {
      type: 'value',
      name: '气压 (hPa)',
    },
    series: [
      {
        name: '气压',
        data: props.pressure,
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
</script>

<style scoped>

</style>