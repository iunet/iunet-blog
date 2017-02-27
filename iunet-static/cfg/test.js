'use strict';

let path = require('path');
let srcPath = path.join(__dirname, '/../resource/');

let baseConfig = require('./base');

let BowerWebpackPlugin = require('bower-webpack-plugin');

module.exports = {
  devtool: 'eval',
  module: {
    preLoaders: [
      {
        test: /\.js$/,
        loader: 'isparta-instrumenter-loader',
        include: [
          path.join(__dirname, '/../resource')
        ]
      }
    ],
    loaders: [
      {
        test: /\.(png|jpg|gif|woff|woff2|css|sass|scss|less|styl)$/,
        loader: 'null-loader'
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        include: [].concat(
          baseConfig.additionalPaths,
          [
            path.join(__dirname, '/../resource'),
            path.join(__dirname, '/../test')
          ]
        )
      }
    ]
  },
  resolve: {
    extensions: [ '', '.js' ],
    alias: {
      config: srcPath + 'config/' + process.env.WEBPACK_ENV,
    }
  },
  plugins: [
    new BowerWebpackPlugin({
      searchResolveModulesDirectories: false
    })
  ]
};
