'use strict';

import 'jquery-validation/dist/jquery.validate.min';
import 'jquery-validation/dist/localization/messages_zh';
import {verifyCode} from "models/app/verifyCode";
import {getOfficialWebsite} from "models/app/officialWebsite";

//init validate
[$("#l-login"), $("#l-register"), $("#l-forget-password"), $("#l-forget-password2")].forEach(function (a) {
  a.validate({
    debug: true,
    showErrors: function (errorMap, errorList) {
      if (errorList.length <= 0) {
        a.find(".input-group").removeClass("has-error");
      } else {
        errorList.forEach(function (a) {
          $(a.element).parent().parent().addClass("has-error");
          iunet.notify({
            icon: "fa-exclamation-triangle",
            msg: a.message + ":" + $(a.element).attr("placeholder"),
            type: "danger",
            from: "top",
            align: "left",
            animIn: "animated fadeInLeft",
            animOut: "animated fadeOutLeft"
          });
        });
      }
    }
  });
});

//init event
[$("#userName"), $("#password")].forEach(function (a) {
  a.on("keydown", function (e) {
    if (e.keyCode == 13) setTimeout(function () {
      $("#l-login").find(".btn-login").click();
    }, 500);
  });
});
[$("#registerUserName"), $("#registerNickname"), $("#registerEmail"), $("#registerPassword"), $("#registerPassword2")].forEach(function (a) {
  a.on("keydown", function (e) {
    if (e.keyCode == 13) setTimeout(function () {
      $("#l-register").find(".btn-login").click();
    }, 500);
  });
});

[$("#resetVerifyCode"), $("#resetPassword"), $("#resetPassword2")].forEach(function (a) {
  a.on("keydown", function (e) {
    if (e.keyCode == 13) setTimeout(function () {
      $("#l-forget-password2").find(".btn-login").click();
    }, 500);
  });
});

$("#resetEmail").on("keydown", function (e) {
  if (e.keyCode == 13) setTimeout(function () {
    $("#l-forget-password").find(".btn-login").click();
  }, 500);
});

$("body").on("click", "[data-ma-action]", function (e) {
  e.preventDefault();
  let $this = $(this), action = $(this).data("ma-action");
  switch (action) {
    case"login-switch":
      let loginblock = $this.data("ma-block"), loginParent = $this.closest(".lc-block");
      loginParent.removeClass("toggled"), setTimeout(function () {
        $(loginblock).addClass("toggled")
      });
      $("#registerUserName").val("");
      $("#registerNickname").val("");
      $("#registerEmail").val("");
      $("#registerPassword").val("");
      $("#registerPassword2").val("");
      $("#userName").val("");
      $("#password").val("");
      $("#remember").prop("checked", false);
      $("#resetEmail").val("");
      break;
    case"login":
      $("#resetEmail").val("");
      $("#registerUserName").val("");
      $("#registerNickname").val("");
      $("#registerEmail").val("");
      $("#registerPassword").val("");
      $("#registerPassword2").val("");
      if ($("#l-login").valid()) {
        showVerifyCode({
          "userName": $("#userName").val(),
          "password": $("#password").val()
        }, login, "登录");
      }
      break;
    case"register":
      $("#resetEmail").val("");
      $("#userName").val("");
      $("#password").val("");
      $("#remember").prop("checked", false);
      if ($("#l-register").valid()) {
        if ($("#registerPassword").val() === $("#registerPassword2").val()) {
          showVerifyCode({
            "userName": $("#registerUserName").val(),
            "nickname": $("#registerNickname").val(),
            "password": $("#registerPassword").val(),
            "email": $("#registerEmail").val()
          }, register, "注册");
        } else {
          iunet.notify({
            icon: "fa-exclamation-triangle",
            msg: "密码不一致，请重新输入！",
            type: "danger",
            from: "top",
            align: "left",
            animIn: "animated fadeInLeft",
            animOut: "animated fadeOutLeft"
          });
          $("#registerPassword").val("");
          $("#registerPassword2").val("");
        }
      }
      break;
    case"reset":
      $("#registerUserName").val("");
      $("#registerNickname").val("");
      $("#registerEmail").val("");
      $("#registerPassword").val("");
      $("#registerPassword2").val("");
      $("#userName").val("");
      $("#password").val("");
      $("#remember").prop("checked", false);
      if ($("#l-forget-password").valid()) {
        showVerifyCode({
          "email": $("#resetEmail").val()
        }, resetSendEmail, "下一步");
      }
      break;
    case"reset2":
      if ($("#l-forget-password2").valid()) {
        if ($("#resetPassword").val() === $("#resetPassword2").val()) {
          reset({
            "verifyCode": $("#resetVerifyCode").val(),
            "password": $("#resetPassword").val()
          });
        } else {
          iunet.notify({
            icon: "fa-exclamation-triangle",
            msg: "密码不一致，请重新输入！",
            type: "danger",
            from: "top",
            align: "left",
            animIn: "animated fadeInLeft",
            animOut: "animated fadeOutLeft"
          });
          $("#resetPassword").val("");
          $("#resetPassword2").val("");
        }
      }
      break;
  }
  ;
});
$("body").on("focus", ".fg-line .form-control", function () {
  $(this).closest(".fg-line").addClass("fg-toggled")
});
$("body").on("blur", ".form-control", function () {
  let p = $(this).closest(".form-group, .input-group"), i = p.find(".form-control").val();
  p.hasClass("fg-float") ? 0 == i.length && $(this).closest(".fg-line").removeClass("fg-toggled") : $(this).closest(".fg-line").removeClass("fg-toggled")
});

function showVerifyCode(data, callback, confirmButtonText) {
  swal({
    title: "请输入验证码",
    text: "<a class='verifyCodeImg' href='javascript:"+verifyCode()+";'><img src='"+iunet.services.getVerifyCode+"' /></a>",
    type: "input",
    html: true,
    showCancelButton: true,
    closeOnConfirm: false,
    animation: "slide-from-top",
    inputPlaceholder: "请输入验证码",
    confirmButtonText: confirmButtonText,
    cancelButtonText: "取消"
  }, function (verifyCode) {
    if (verifyCode === false)
      return false;
    if (verifyCode.length != 6) {
      swal.showInputError("请输入图片中的6位验证码!");
      return false
    }
    data.verifyCode = verifyCode;
    callback(data);
  });
  $("fieldset").find("input").attr("maxlength", "6");
}

function login(data) {
  iunet.post({
    url: iunet.services.userLogin,
    data: data,
    success: function (data, textStatus) {
      if (data && data.errorCode == "0") {
        iunet.store.add("iunet.userInfo", JSON.stringify(data.res));
        window.top.location.reload();
      } else if (data && data.errorCode == "1") {
        verifyCode();
        swal.showInputError(data.errorMessage);
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
}

function register(data) {
  let userName = data.userName;
  let email = data.email;
  iunet.post({
    url: iunet.services.userRegister,
    data: data,
    success: function (data, textStatus) {
      if (data && data.errorCode == "0") {
        let officialWebsite = getOfficialWebsite(email);
        if (officialWebsite == "") {
          swal({
            title: "注册成功！",
            text: "我们已将激活邮件发送邮件至您的邮箱：" + email + "中请注意查收。",
            type: "success",
            confirmButtonText: "确定"
          });
        } else {
          swal({
            title: "注册成功！",
            text: "我们已将激活邮件发送邮件至您的邮箱：" + email + "中请注意查收。<a href='" + officialWebsite + "' target='_blank'>点击登录</a>",
            type: "success",
            html: true,
            confirmButtonText: "确定"
          });
        }
        $("#registerUserName").val("");
        $("#registerNickname").val("");
        $("#registerEmail").val("");
        $("#registerPassword").val("");
        $("#registerPassword2").val("");
        $("#l-register").removeClass("toggled");
        $("#l-login").addClass("toggled");
      } else if (data && data.errorCode == "1") {
        verifyCode();
        swal.showInputError(data.errorMessage);
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
}

function resetSendEmail(data) {
  let email = data.email;
  iunet.post({
    url: iunet.services.sendResetEmail,
    data: data,
    success: function (data, textStatus) {
      if (data && data.errorCode == "0") {
        swal.close();
        $("#resetMessage").html("验证码已发送至您输入的邮箱：" + email + " 请注意查收！");
        $("#l-forget-password").removeClass("toggled");
        $("#l-forget-password2").addClass("toggled");
      } else if (data && data.errorCode == "1") {
        verifyCode();
        swal.showInputError(data.errorMessage);
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
}

function reset(data) {
  iunet.post({
    url: iunet.services.userReset,
    data: data,
    success: function (data, textStatus) {
      if (data && data.errorCode == "0") {
        swal({
          title: "重置密码成功！",
          text: "",
          type: "success",
          confirmButtonText: "确定"
        }, function () {
          window.location.reload();
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
}
