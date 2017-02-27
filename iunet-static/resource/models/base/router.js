'use strict';

import {indexMain} from "models/index/indexMain";
import {userSettings} from "models/user/userSettings";
import {userManage} from "models/user/userManage";
import {personManage} from "models/user/personManage";
import {groupManage} from "models/user/groupManage";
import {roleManage} from "models/user/roleManage";
import {permissionManage} from "models/user/permissionManage";
import {controlWordManage} from "models/net/controlWordManage";

let vm = {
  indexMain: require("models/index/indexMain.vm"),
  userSettings: require("models/user/userSettings.vm"),
  userManage: require("models/user/userManage.vm"),
  personManage: require("models/user/personManage.vm"),
  groupManage: require("models/user/groupManage.vm"),
  roleManage: require("models/user/roleManage.vm"),
  permissionManage: require("models/user/permissionManage.vm"),
  controlWordManage: require("models/net/controlWordManage.vm"),
};

export let router = function (param) {
  if(param == "logout") {
    swal({
      title: "登出系统",
      text: "您确定要登出系统？",
      type: "warning",
      showCancelButton: true,
      closeOnConfirm: false,
      confirmButtonText: "登出",
      cancelButtonText: "取消"
    }, function () {
      iunet.post({
        url: iunet.services.logout,
        success: function () {
          window.location.reload();
        }
      });
    });
    return;
  }
  $("#main-content").html("");
  $("#main-left-nav").find(".active").removeClass("active");
  $("#" + param).parent().addClass("active");
  if(param != "indexMain") {
    $("#" + param).parent().parent().parent().addClass("active");
  }
  if (vm[param] != null) {
    $.get(vm[param], function (content) {
      $("#main-content").html(content);
      switch (param) {
        case "indexMain":
          indexMain();
          break;
        case "userSettings":
          userSettings();
          break;
        case "userManage":
          userManage();
          break;
        case "personManage":
          personManage()
          break;
        case "groupManage":
          groupManage()
          break;
        case "roleManage":
          roleManage()
          break;
        case "permissionManage":
          permissionManage()
          break;
        case "controlWordManage":
          controlWordManage()
          break;
      }
    });
  }
}
