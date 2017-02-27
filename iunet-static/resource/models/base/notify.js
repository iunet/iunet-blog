'use strict';

import 'bootstrap-notify/bootstrap-notify.min';

export let notify = function({
  icon = "fa-info-circle",
  title = "",
  msg = "hello bootstrap-notify",
  type = "info",
  url = "javascript:void()",
  delay = "2000",
  from = "top",
  align = "right",
  animIn = "animated fadeInDown",
  animOut = "animated fadeOutUp"
}) {
  $.notify({
    // options
    icon: "fa " + icon,
    title: title,
    message: msg,
    url: url,
    target: '_blank'
  }, {
    // settings
    element: 'body',
    position: null,
    type: type,
    allow_dismiss: true,
    newest_on_top: false,
    showProgressbar: false,
    placement: {
      from: from,
      align: align
    },
    offset: 20,
    spacing: 10,
    z_index: 1031,
    delay: delay,
    timer: 1000,
    url_target: '_blank',
    mouse_over: null,
    animate: {
      enter: animIn,
      exit: animOut
    },
    onShow: null,
    onShown: null,
    onClose: null,
    onClosed: null,
    icon_type: 'class',
    template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
    '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
    '<span data-notify="icon"></span> ' +
    '<span data-notify="title">{1}</span> ' +
    '<span data-notify="message">{2}</span>' +
    '<div class="progress" data-notify="progressbar">' +
    '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
    '</div>' +
    '<a href="{3}" target="{4}" data-notify="url"></a>' +
    '</div>'
  });
}
