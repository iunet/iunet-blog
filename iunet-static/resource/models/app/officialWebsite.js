'use strict';

export let getOfficialWebsite = function (email) {
  let mail = {
    'qq.com': 'https://mail.qq.com/',
    'gmail.com': 'http://gmail.google.com',
    'sina.com': 'http://mail.sina.com.cn',
    '163.com': 'http://mail.163.com',
    '126.com': 'http://mail.126.com',
    'yeah.net': 'http://www.yeah.net/',
    'sohu.com': 'http://mail.sohu.com/',
    'tom.com': 'http://mail.tom.com/',
    '139.com': 'http://mail.10086.cn/',
    'hotmail.com': 'https://outlook.live.com/owa/',
    'live.com': 'https://outlook.live.com/owa/',
    'live.cn': 'https://outlook.live.com/owa/',
    'live.com.cn': 'https://outlook.live.com/owa/',
    'outlook.com': 'https://outlook.live.com/owa/',
    '189.com': 'http://webmail16.189.cn/webmail/',
    'yahoo.com.cn': 'https://mail.yahoo.com/',
    'yahoo.cn': 'https://mail.yahoo.com/',
    'eyou.com': 'http://www.eyou.com/',
    '21cn.com': 'http://mail.21cn.com/',
    '188.com': 'http://www.188.com/',
    'foxmail.com': 'http://mail.foxmail.com'
  };
  let key = email.split('@')[1].toLowerCase();
  if (mail[key]) {
    return mail[key];
  } else {
    return "";
  }
}
