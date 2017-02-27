'use strict';
let path = require('path');
let defaultSettings = require('./defaults');

let additionalPaths = [];

module.exports = {
  additionalPaths: additionalPaths,
  port: defaultSettings.port,
  debug: true,
  devtool: 'eval',
  output: {
    path: path.join(__dirname, '/../src/main/webapp'),
    filename: 'app.js',
    publicPath: defaultSettings.publicPath
  },
  devServer: {
    contentBase: './resource/',
    historyApiFallback: true,
    hot: true,
    port: defaultSettings.port,
    publicPath: defaultSettings.publicPath,
    noInfo: false
  },
  resolve: {
    extensions: ['', '.js'],
    alias: {
      plugin: `${defaultSettings.srcPath}/plugin/`,
      models: `${defaultSettings.srcPath}/models/`,
      images: `${defaultSettings.srcPath}/images/`,
      styles: `${defaultSettings.srcPath}/styles/`,
      config: `${defaultSettings.srcPath}/config/` + process.env.WEBPACK_ENV,
    }
  },
  module: {}
};
