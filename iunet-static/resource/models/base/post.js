'use strict';

export let post = function ({
  url = "",
  data = {},
  async = true,
  success = function () {
    return;
  },
  error = function () {
    swal({
      title: "系统错误",
      text: "请联系管理员!",
      type: "error",
      confirmButtonText: "确定",
    });
  },
  complete = function () {
    if (status == "timeout") {
      method.abort();
      swal({
        title: "连接超时",
        text: "请稍候再试..",
        type: "error",
        confirmButtonText: "确定"
      });
    }
  },
  method = null,
}) {
  method = $.ajax({
    type: "post",
    url: url,
    timeout: iunet.timeout,
    data: JSON.stringify(data),
    dataType: "json",
    contentType: "application/json;charset=UTF-8",
    async: async,
    error: function (textstatus, e) {
      error();
    },
    complete: function (XMLHttpRequest, status) {
      complete();
    },
    success: function (data, textStatus) {
      success(data, textStatus);
    }
  });
}
