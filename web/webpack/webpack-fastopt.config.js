const { merge } = require('webpack-merge');
const path = require("path");
const HtmlWebpackPlugin = require('html-webpack-plugin');
const generatedConfig = require('./scalajs.webpack.config');

generatedConfig.module.rules = [];

module.exports = merge(generatedConfig, {
  devtool: "cheap-module-eval-source-map",
  resolve: {
    alias: {
      "resources": './resources',
      "static": './static',
    }
  },
  module: {
    rules: [
      {
        test: /\-fastopt.js$/,
        use: './fastopt-loader.js',
      },
      {
        test: /\.svg$/,
        use: [
          {
            loader: 'file-loader',
            query: {
              name: 'static/media/[name].[hash:8].[ext]'
            }
          }
        ]
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader', 'postcss-loader']
      },
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: "./public/index.html",
      inject: false,
    })
  ],
  devServer: {
    historyApiFallback: true,
    proxy: {
      '/graphql': 'http://localhost:8080',
      '/company/ticker/static/*': {
        target: 'http://localhost:8000',
        pathRewrite: { '^/company/ticker/static': '/static' },
        changeOrigin: true
      },
      '/company/ticker/tw-elements/*': {
        target: 'http://localhost:8000',
        pathRewrite: { '^/company/ticker/tw-elements': '/tw-elements' },
        changeOrigin: true
      },
      '/company/ticker/@popperjs/*': {
        target: 'http://localhost:8000',
        pathRewrite: { '^/company/ticker/@popperjs': '/@popperjs' },
        changeOrigin: true
      },
      '/tw-elements/*': {
        target: 'http://localhost:8000',
        pathRewrite: { '^/tw-elements': '/node_modules/tw-elements' },
        changeOrigin: true
      },
      '/@popperjs/*': {
        target: 'http://localhost:8000',
        pathRewrite: { '^/@popperjs': '/node_modules/@popperjs' },
        changeOrigin: true
      },
    }
  },
})