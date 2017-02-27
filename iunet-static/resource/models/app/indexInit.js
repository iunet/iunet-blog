'use strict';

function scrollBar(selector, theme, mousewheelaxis) {
  $(selector).mCustomScrollbar({
    theme: theme,
    scrollInertia: 100,
    axis: "yx",
    mouseWheel: {enable: !0, axis: mousewheelaxis, preventDefault: !0}
  })
}

function search() {
  let keyword = $("#main-search-input").val();

  iunet.post({
    url: iunet.services.mainSearch,
    data: {
      "keyword": keyword
    },
    success: function (data, textStatus) {
      if (data && data.errorCode == "0") {
        console.log(data.res);
      } else if (data && data.errorCode == "1") {
        console.log("没有相关记录");
      } else {
        swal({
          title: "温馨提示",
          text: data.errorMessage,
          type: "error",
          confirmButtonText: "确定"
        });
      }
    }
  });

  let history = iunet.store.get("search-history", true).split(',');
  if (!history.includes(keyword)) {
    history.push(keyword);
    iunet.store.add("search-history", history, true);
  }
}

//init event
$("body").on("click", "[data-ma-action]", function (e) {
  function launchIntoFullscreen(element) {
    element.requestFullscreen ? element.requestFullscreen() : element.mozRequestFullScreen ? element.mozRequestFullScreen() : element.webkitRequestFullscreen ? element.webkitRequestFullscreen() : element.msRequestFullscreen && element.msRequestFullscreen()
  }

  e.preventDefault();
  let $this = $(this), action = $(this).data("ma-action");
  switch (action) {
    /* 二级菜单导航头像 */
    case"profile-menu-toggle":
      $this.parent().toggleClass("toggled"), $this.next().slideToggle(200);
      break;
    /* 二级菜单 */
    case"submenu-toggle":
      $this.next().slideToggle(200), $this.parent().toggleClass("toggled");
      break;
    /* 打开检索 */
    case"search-open":
      $("#header").addClass("search-toggled"), $("#top-search-wrap input").focus();
      break;
    /* 关闭检索 */
    case"search-close":
      $("#header").removeClass("search-toggled");
      search();
      break;
    /* 显示检索历史 */
    case"search-history":
      console.log(iunet.store.get("search-history", true));
      //$("#main-search-input").val("");
      break;
    /* 切换全屏 */
    case"fullscreen":
      launchIntoFullscreen(document.documentElement);
      break;
    /* 清除本地存储 */
    case"clear-localstorage":
      swal({
        title: "确定清除?",
        text: "所有你保存的本地存储的值将被删除",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "删除",
        cancelButtonText: "取消"
      }, function () {
        localStorage.clear(), swal("完成!", "localStorage清除成功", "success")
      });
      break;
    /* 更换主题颜色 */
    case"change-skin":
      let skin = $this.data("ma-skin");
      $("[data-ma-theme]").attr("data-ma-theme", skin);
      iunet.post({
        url: iunet.services.changeSkin,
        data:{
          skin: skin
        },
        success: function (data, textStatus) {
          if (data && data.errorCode == "0") {
            let userInfo = iunet.store.get("iunet.userInfo");
            userInfo = userInfo ? JSON.parse(userInfo) : "";
            if(userInfo != "") {
              userInfo.appParam = skin;
              iunet.store.add("iunet.userInfo", JSON.stringify(userInfo));
            }
            $(".hi-menu").find(".open").removeClass("open");
            swal({
              title: "温馨提示",
              text: "更换主题成功",
              type: "success",
              showConfirmButton: false,
              timer: 1000
            });
          } else {
            swal({
              title: "温馨提示",
              text: data.errorMessage,
              type: "error",
              confirmButtonText: "确定"
            });
          }
        }
      });
      break;
    /* sidebar */
    case"sidebar-open":
      let target = $this.data("ma-target"), backdrop = '<div data-ma-action="sidebar-close" class="ma-backdrop" />';
      $("body").addClass("sidebar-toggled"), $("#header, #header-alt, #main").append(backdrop), $this.addClass("toggled"), $(target).addClass("toggled");
      break;
    case"sidebar-close":
      $("body").removeClass("sidebar-toggled"), $(".ma-backdrop").remove(), $(".sidebar, .ma-trigger").removeClass("toggled");
      break;
    /* 清除消息 */
    case"clear-notification":
      let x = $this.closest(".list-group"), y = x.find(".list-group-item"), z = y.size();
      $this.parent().fadeOut(), x.find(".list-group").prepend('<i class="grid-loading hide-it"></i>'), x.find(".grid-loading").fadeIn(1500);
      let w = 0;
      y.each(function () {
        let z = $(this);
        setTimeout(function () {
          z.addClass("animated fadeOutRightBig").delay(1e3).queue(function () {
            z.remove()
          })
        }, w += 150)
      }), setTimeout(function () {
        $(".him-notification").addClass("empty")
      }, 150 * z + 200);
      break;
    case"print":
      window.print();
      break;
    case"profile-edit":
      $this.closest(".pmb-block").toggleClass("toggled");
      break;
    case"profile-edit-cancel":
      $(this).closest(".pmb-block").removeClass("toggled");
      break;
    case"action-header-open":
      ahParent = $this.closest(".action-header").find(".ah-search"), ahParent.fadeIn(300), ahParent.find(".ahs-input").focus();
      break;
    case"action-header-close":
      ahParent.fadeOut(300), setTimeout(function () {
        ahParent.find(".ahs-input").val("")
      }, 350);
      break;
    case"wall-comment-open":
      $this.closest(".wic-form").hasClass("toggled") || $this.closest(".wic-form").addClass("toggled");
      break;
    case"wall-comment-close":
      $this.closest(".wic-form").find("textarea").val(""), $this.closest(".wic-form").removeClass("toggled");
      break;
    case"todo-form-open":
      $this.closest(".t-add").addClass("toggled");
      break;
    case"todo-form-close":
      $this.closest(".t-add").removeClass("toggled"), $this.closest(".t-add").find("textarea").val("");
      break;
  }
});

$(".dropdown")[0] && ($("body").on("click", ".dropdown.open .dropdown-menu", function (e) {
  e.stopPropagation()
}), $(".dropdown").on("shown.bs.dropdown", function (e) {
  $(this).attr("data-animation") && ($animArray = [], $animation = $(this).data("animation"), $animArray = $animation.split(","), $animationIn = "animated " + $animArray[0], $animationOut = "animated " + $animArray[1], $animationDuration = "", $animArray[2] ? $animationDuration = $animArray[2] : $animationDuration = 500, $(this).find(".dropdown-menu").removeClass($animationOut), $(this).find(".dropdown-menu").addClass($animationIn))
}), $(".dropdown").on("hide.bs.dropdown", function (e) {
  $(this).attr("data-animation") && (e.preventDefault(), $this = $(this), $dropdownMenu = $this.find(".dropdown-menu"), $dropdownMenu.addClass($animationOut), setTimeout(function () {
    $this.removeClass("open")
  }, $animationDuration))
}));

$("html").hasClass("ismobile") || $(".page-loader")[0] && setTimeout(function () {
  $(".page-loader").fadeOut();
}, 500);
$("html").hasClass("ismobile") || $(".c-overflow")[0] && scrollBar(".c-overflow", "minimal-dark", "y");

let Waves = require("models/base/waves/waves.min");
Waves.attach(".btn:not(.btn-icon):not(.btn-float)"), Waves.attach(".btn-icon, .btn-float", ["waves-circle", "waves-float"]), Waves.init()

//welcome notify
setTimeout(function () {
  iunet.notify({
    icon: "fa-sign-in",
    msg: "欢迎使用 iunetWeb",
    type: "inverse",
    url: "https://github.com/iunet/iunetweb"
  });
}, 1000);
