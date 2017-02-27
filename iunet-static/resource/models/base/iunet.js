'use strict';

import {store} from 'models/base/store';
import {router} from 'models/base/router';
import {notify} from "models/base/notify";
import {post} from "models/base/post";

let u = navigator.userAgent,
  app = navigator.appVersion;

let iunet = {
  timeout: 99999999999999,//5000 99999999999999
  store: new store(),
  Store: new store(true),
  router: router,
  notify: notify,
  post: post,
  services: {
    getVerifyCode: "common/getVerifyCode.do?" + Math.floor(Math.random() * 100),
    getUserInfo: "common/getUserInfo.do",
    logout: "common/logout.do",
    userLogin: "login/userLogin.do",
    userRegister: "register/register.do",
    userActivation: "register/activation.do",
    userReset: "reset/reset.do",
    sendResetEmail: "reset/sendEmail.do",
    getUserFunctionTree: "index/getUserFunctionTree.do",
    mainSearch: "index/mainSearch.do",
    changeSkin: "index/changeSkin.do"
  },
  browser: {
    userAgent: u,
    appVersion: app,
    language: (navigator.browserLanguage || navigator.language).toLowerCase(),
    trident: u.indexOf('Trident') > -1, //IE内核
    presto: u.indexOf('Presto') > -1, //opera内核
    webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
    mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
    android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
    iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
    iPad: u.indexOf('iPad') > -1, //是否iPad
    webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
  }
};

iunet.util = {};

window.iunet = iunet;
export default iunet;
