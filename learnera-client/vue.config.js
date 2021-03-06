// vue.config.js
module.exports = {
  // proxy all webpack dev-server requests starting with /api
  // to our Spring Boot backend (localhost:8088) using http-proxy-middleware
  // see https://cli.vuejs.org/config/#devserver-proxy
  // devServer: process.env.NODE_ENV !== 'production' ? {
  //   proxy: {
  // 	  '/api': {
  //       target: 'http://localhost:8088',
  //       ws: true,
  //       changeOrigin: true
  // 	  }
  //   }
  // } : null,
  // Change build paths to make them Maven compatible
  // see https://cli.vuejs.org/config/
  outputDir: 'target/dist',
  baseUrl: process.env.NODE_ENV === 'production'
    ? '/'
    : '/',
  assetsDir: 'static',
  configureWebpack: {
    devtool: 'source-map'
  }
}
