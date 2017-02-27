'use strict';

import 'styles/iunet.css';
import 'fullcalendar/dist/fullcalendar.min.css';
import 'animate.css/animate.min.css';
import 'sweetalert/dist/sweetalert.css';
import 'malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.css';
import 'styles/material-design-iconic-font.min.css';
import 'styles/font-awesome.min.css';

import 'models/base/jquery';
import 'models/base/iunet';
import 'bootstrap/dist/js/bootstrap.min';
import 'sweetalert/dist/sweetalert.min';
import 'malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min';

if (iunet.browser.mobile) {
  $("html").addClass("ismobile");
}

iunet.post({
  url: iunet.services.getUserInfo,
  error: function () {
    require('models/app/loginLoad');
  },
  success: function (data, textStatus) {
    if (data && data.errorCode == "0") {
      iunet.store.add("iunet.userInfo", JSON.stringify(data.res));
      require('models/app/indexLoad');
    } else {
      swal({
        title: "温馨提示",
        text: data.errorMessage,
        type: "error",
        confirmButtonText: "确定"
      }, function (a) {
        if (a) {
          require('models/app/loginLoad');
        }
      });
    }
  }
});
