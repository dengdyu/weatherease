<template>
  <div>
    <div ref="precipitationChart" style="height: 300px;"></div>
  </div>
</template>

<script setup>
import {ref, onMounted, watch} from 'vue';
import * as echarts from 'echarts';

const precipitationChart = ref(null);

const props = defineProps({
  precipitation: Array,
  times: Array,
});

onMounted(() => {
  initChart();

  //监听数据变化，重新更新图表
  watch([() => props.precipitation, () => props.times], () => {
    updateChart();
  }, { immediate: true });
});

let chartInstance = null;

function initChart() {
  if (precipitationChart.value) {
    chartInstance = echarts.init(precipitationChart.value);
    chartInstance.setOption(getChartOptions());
  }
}

function updateChart() {
  if (chartInstance) {
    chartInstance.setOption(getChartOptions());
  }
}

function getChartOptions() {
  return {
    title: {
      text: '降水量',
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
      },
    },
    xAxis: {
      type: 'category',
      data: props.times,
      axisLabel: {
        rotate: 45,
      }
    },
    yAxis: {
      type: 'value',
      name: '降水量 (mm)',
    },
    series: [
      {
        name: '降水量',
        type: 'bar',
        data: [3 ,1 ,4 ,10 ,2],
        //props.precipitation,
        itemStyle: {
          color: '#4fc3f7',
        },
      },
    ],
  };
}
</script>

<style scoped>

</style>