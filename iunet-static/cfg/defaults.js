'use strict';

const path = require('path');
const srcPath = path.join(__dirname, '/../resource');
const dfltPort = 8888;

function getDefaultModules() {
  return {
    preLoaders: [
      {
        test: /\.js$/,
        include: srcPath,
        loader: 'eslint-loader'
      }
    ],
    loaders: [
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader'
      },
      {
        test: /\.sass/,
        loader: 'style-loader!css-loader!sass-loader?outputStyle=expanded&indentedSyntax'
      },
      {
        test: /\.scss/,
        loader: 'style-loader!css-loader!sass-loader?outputStyle=expanded'
      },
      {
        test: /\.less/,
        loader: 'style-loader!css-loader!less-loader'
      },
      {
        test: /\.styl/,
        loader: 'style-loader!css-loader!stylus-loader'
      },
      {
        test: /\.(png|jpg|gif)$/,
        loader: 'url-loader?limit=8192'
      },
      {
        test: /\.(mp4|mp3|ogg|vm)$/,
        loader: 'file-loader'
      },
      {
        test: /\.(woff|woff2|svg|eot|ttf)\??.*$/,
        loader: 'url-loader?limit=8192&name=[path][name].[ext]'
      },
      {
        test: /\.json$/,
        loader: 'json'
      }
    ]
  };
}

module.exports = {
  srcPath: srcPath,
  publicPath: '/iunet-static/',
  port: dfltPort,
  getDefaultModules: getDefaultModules
};
