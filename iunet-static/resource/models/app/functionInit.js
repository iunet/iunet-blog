'use strict';

iunet.post({
  url: iunet.services.getUserFunctionTree,
  success: function (data, textStatus) {
    if (data && data.errorCode == "0") {
      createFunctionTree(data.res);
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

function createFunctionTree(data) {
  let childNav,nav = [],functions = data.functions;
  nav.push('<li class="active"><a id="indexMain" href="javascript:iunet.router(`indexMain`)"><i class="zmdi zmdi-home"></i> 主页</a></li>');

  let root = new Array();
  functions.forEach(function(a,b) {
    if (a.id != 0 && a.parentId == 0)
      root.push(a.id);
  });
  if(root.length == 0) {
    //没有权限
  } else {
    root.sort().forEach(function(a,b){
      let child = new Array();
      functions.forEach(function(c,d) {
        if(a == c.parentId) {
          child.push(c.id);
        }
      });
      functions.forEach(function(c,d) {
        if(a == c.id) {
          childNav = "<li class='sub-menu'><a href='' data-ma-action='submenu-toggle'><i class='fa "+c.icon+"'  aria-hidden='true'></i> "+c.name+"</a><ul>";
        }
      });
      child.sort().forEach(function(e,f){
        functions.forEach(function(c,d) {
          if(e == c.id) {
            childNav += "<li><a id='"+c.param+"' href='javascript:iunet.router(`"+c.param+"`)'>"+c.name+"</a></li>";
          }
        });
      });
      childNav += '</ul></li>';
      nav.push(childNav);
    });
  }
  $("#main-left-nav").html(nav.join(""));
  iunet.router("indexMain");
}
