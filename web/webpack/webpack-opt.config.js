const path = require("path");
const { merge } = require('webpack-merge');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const generatedConfig = require("./scalajs.webpack.config.js");
const webpack = require("webpack");

module.exports = merge(generatedConfig, {
  mode: "production",
  module: {
    rules: [
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader', 'postcss-loader']
      },
      // "file" loader for svg
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
      }
    ]
  },
  resolve: {
    alias: {
      "resources": './resources',
      "static": './static',
      "scalajs": path.resolve(__dirname, "./scalajs-entry.js")
    },
  },
  devtool: "source-map",
  output: {
    publicPath: "/",
    path: path.resolve(__dirname, "../../../../../server/src/main/resources/build")
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: JSON.stringify('production')
      }
    }),
    new CopyWebpackPlugin({
      patterns: [
        {
          from: './public',
          globOptions: {
            ignore: [
              "**/index.html"
            ]
          }
        },
        {
          from: './node_modules/tw-elements/dist/js/index.min.js',
          to: path.resolve(__dirname, "../../../../../server/src/main/resources/build/tw-elements/dist/js/index.min.js"),
        },
        {
          from: './node_modules/@popperjs/core/dist/umd/popper.min.js',
          to: path.resolve(__dirname, "../../../../../server/src/main/resources/build/@popperjs/core/dist/umd/popper.min.js"),
        }
      ]
    }),
    new HtmlWebpackPlugin({
      template: './public/index.html',
    })
  ],
})
