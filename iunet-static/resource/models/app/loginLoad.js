'use strict';

localStorage.clear();
sessionStorage.clear();
let keys = document.cookie.match(/[^ =;]+(?=\=)/g);
if (keys) {
  keys.forEach(function (a, b) {
    document.cookie = a + '=0;expires=' + new Date(0).toUTCString();
  });
}
$.get(require("models/app/login.vm"), function (content) {
  $("body").append(content);

  $.get(require("models/app/public.vm"), function (content) {
    $("body").append(content);
    require("models/app/login");
  });
});
