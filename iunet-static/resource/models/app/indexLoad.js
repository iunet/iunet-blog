'use strict';

let weixinImg = require("images/about/weixin.png");

$.get(require("models/app/loader.vm"), function (content) {
  $("body").append(content);

  $.get(require("models/app/header.vm"), function (content) {
    $("body").append(content);
  });

  $.get(require("models/app/main.vm"), function (content) {
    $("body").append(content);
    $.get(require("models/app/footer.vm"), function (content) {
      $("body").append(content);
      $("#footerYear").html("2016-" + new Date().getFullYear());
      $("#footer-github").on("click", function (e) {
        window.open("https://github.com/iunet");
      });
      $("#footer-weixin").on("click", function (e) {
        swal({
          title: "",
          text: "<a href='javascript:void();'><img src='" + weixinImg + "' /></a>",
          html: true,
          showCancelButton: false,
          closeOnConfirm: false,
          animation: "slide-from-bottom",
          confirmButtonText: "关闭",
        });
      });
    });
    $.get(require("models/app/public.vm"), function (content) {
      $("body").append(content);
      require("models/app/functionInit");
      require("models/app/indexInit");

      let userInfo = iunet.store.get("iunet.userInfo");
      userInfo = userInfo ? JSON.parse(userInfo) : "";
      if (userInfo.appParam) {
        $("[data-ma-theme]").attr("data-ma-theme", userInfo.appParam);
      }
      if (userInfo.userName) {
        $("#main-userName").html(userInfo.userName);
      }
      if (userInfo.photoPath) {
        $("#main-profile-pics").attr("src", userInfo.photoPath);
      }
    });
  });
});
