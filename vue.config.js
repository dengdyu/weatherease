// 跨域配置
module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端服务地址
        changeOrigin: true,
        pathRewrite: { '^/api': '' } // 重写路径
      }
    }
  }
};