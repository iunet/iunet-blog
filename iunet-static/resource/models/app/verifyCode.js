'use strict';

export let verifyCode = function () {
  $(".verifyCodeImg").find("img").hide().attr("src", iunet.services.getVerifyCode).fadeIn();
}
