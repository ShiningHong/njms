$(document).ready(function(){
    //绑定登录事件
    $("#loginBtn").bind("click", function(){
    	 var url = GLOBAL.WEBROOT+ "/login.do?loginIndex";
         window.top.location = url;
        //doLogin($("#loginBtn"));
    });
    document.onkeydown = function(e){ 
         var ev = document.all ? window.event : e;
         if(ev.keyCode==13) {
             doLogin($("#loginBtn"));
        }
    };
    //初始化记住密码按钮
    initRememberPwsd();
    

    //绑定回车事件
    /*$(document.all).keydown(function(event){
        var e = event || window.event;
        var keyCode = e.keyCode || e.which;
        if(keyCode != 13) return;
        doLogin($("#loginBtn"));
    });*/
    //绑定重置事件
    $("#loginResetBtn").bind("click", function(){
        resetLoginForm();
    });
     //绑定焦点获取和市区时文本域所触发的事件
    $("#userName").val('登录名');
    $("#userName").focus(function(){
            $(this).css("color","black");
            if(this.value=='登录名'){
                  $("#userName").val("");
            }
      });
    $("#userName").blur(function(){
            if(this.value == ''){
                $(this).css("color","#DDD");
                $("#userName").val("登录名");
            }
      });
});

//重置form表单
resetLoginForm = function(){
    $("<input type='reset' style='display:none;' id='formResetBtn' />").appendTo($("#loginForm"));
    $("#formResetBtn").click();
    $("#formResetBtn").empty().remove();
    $("#login_result_tip").html("");
};

//校验
validate = function(){
    $("#login_result_tip").html("");
    if($.trim($("#userName").val()) == "" || $.trim($("#userName").val()) == "登陆名"){
        $("#userName").focus();
        $("#login_result_tip").html("请输入登陆名!");
        //MSN.open({msg:"请输入登陆名或手机号!"});
        return false;
    }
    if($.trim($("#userPwd").val()) == "" ){
        $("#userPwd").focus();
        $("#login_result_tip").html("请输入密码!");
        return false;
    }
    /*
    if($.trim($("#validCode").val()) == "" || $.trim($("#validCode").val()) == "校验码"){
        $("#validCode").focus();
        $("#login_result_tip").html("请输入校验码!");
        return false;
    }*/
    return true;
};

//执行登录
doLogin = function(obj){
    if(!validate()) return;
    var loginTip = "登录";
    var logining = "登录中..";
    var spanTxt = $.trim($(obj).find("span").html());
    if(spanTxt == logining) return;
    $(obj).find("span").empty().append(logining);
    var data = $("#loginForm").serialize();
    rememberPwsd();
    $.ajax({
        url: GLOBAL.WEBROOT+'/login/checkLogin.do',
        type: 'post',
        dataType: 'json',
        cache: false,
        async: true,
        data: data,
        success: function(result){
            $(obj).find("span").html(loginTip);
            if(!result.success){
                $("#login_result_tip").empty().append(result.message);
            }else{
                if(result.params=='1'){
                    var ref = GetRefValue();
                    var url = GLOBAL.WEBROOT+"/index.jsp";
                    if(isNoNull(ref)){
                        url =url+"?ref="+ref ;
                    }
                    window.location = url;
                }else{
                    var winWidth = '500';
                    var winHeight = '500';
                    if(result.paramMap){
                        winWidth = result.paramMap.winWidth;
                        winHeight = result.paramMap.winHeight;
                    }
                    openChooseUserRoleWin(winWidth,winHeight);
                }
                //window.location = GLOBAL.WEBROOT+"/index.jsp";
            }
        },
        error:function(result){
            $(obj).find("span").empty().append(loginTip);
        }
     });
};

function openChooseUserRoleWin(winWidth,winHeight){
    if(!winWidth)winWidth = '400';
    if(!winHeight)winHeight = '500';
    var tst = "工号切换";
    var url = GLOBAL.WEBROOT+'/login/chooseUserRole.do'
   
    WIN.open({
        id:1,
        title : tst,
        url : url,
        width : winWidth,
        height : winHeight,
        //closable:false,
        onClose : function(params) {
            if(params){
                window.top.location = GLOBAL.WEBROOT+"/index.jsp";
            }
        }
    });
}

//获取校验码
toGetVerlidateCode = function(){
    $("#validCodeImg").attr("src",GLOBAL.WEBROOT+"/verifiedCode.do?"+new Date().getTime());
};


var WIN = {
        id:"1",
        param:false,
        title:"操作",
        url:GLOBAL.WEBROOT+"/common/blank.jsp",
        width:750,
        height:400,
        left:20,
        top:20,
        right:20,
        shadow:false,
        modal:true,
        closable:true   
    };

WIN.open = function(option){    
    var objId = option.id||this.id
    var title = option.title||this.title;
    var width = option.width||this.width;
    var height = option.height||this.height;
    var left = (window.screen.width - width)/2;
    var top = (window.screen.height - height)/2 - 100;
    var right = option.right||this.right;
    
    //如果设置left,top参数,则覆盖默认居中
    left = option.left||left;
    top = option.top||top;
    
    var shadow = typeof(option.shadow)=="boolean"?option.shadow:this.shadow;
    var modal = typeof(option.modal)=="boolean"?option.modal:this.modal;    
    var closable = typeof(option.closable)=="boolean"?option.closable:this.closable;
    var closed = option.closed||false;
    var url = option.url||"about:blank";
    //var iconCls = option.iconCls||"icon-edit";
    var iconCls = option.iconCls||'';
    
    if(url==""){
        alert("url参数不能为空");
    }   
    
    if(url!="about:blank" && url.indexOf("http")<0){
        url = GLOBAL.WEBROOT+"/"+url;
    }
    
    $('#globalModalWindow'+objId).window({
        title: title,
        width: width,
        height: height,
        top:top,        
        left:left,
        closable: closable,
        modal: modal,
        shadow: shadow,
        iconCls:iconCls,
        closed: closed,
        onClose: function(){
          /*
          if(option.onClose){
            option.onClose(WIN.param);
            WIN.param = false; 
          }*/
          if(option.onClose){
            if(WIN.param){
                option.onClose(WIN.param);
                WIN.param = false;
            }
          }
        },
        onBeforeClose:function(){
          $("#globalModalFrm"+objId).attr({src:"about:blank"});
          if(option.onBeforeClose){
            option.onBeforeClose();
          }
        }       
    });
    
    $("#globalModalFrm"+objId).attr({src:url});
    //$('#globalModalWindow').window('open');
}

WIN.close = function(id,param){
    if(id==undefined){id = this.id;}
    WIN.param = param;          
    $("#globalModalFrm"+id).attr({src:"about:blank"});
    $('#globalModalWindow'+id).window('close');
}

MSN = {};
MSN.open = function(option){
    var title = option.title||"提示";
    var msg = option.msg||"操作成功!";
    var iconCls = option.iconCls||null;
    var fn = option.fn||null;
    
    $.messager.alert(title,msg,iconCls,fn);
}

function initRememberPwsd(){
    var  userName  = $.cookie('userName');
    var  userPwd  = $.cookie('userPwd');
    var  remeberPwsd =  $.cookie('remeberPwsd');
    if(userName!=null&&userName!="null"){
        $("#userName").val(userName);
    }
    if(userPwd!=null&&userPwd!="null"){
        $("#userPwd").val(userPwd);
    }
    if(remeberPwsd!=null&&remeberPwsd=="0"){
        $("#remeberPwsd").attr("checked","");
    }
}
function rememberPwsd() {
     var checked =  $("#remeberPwsd").is(':checked');
     if(checked==false){
         $.cookie('userName', null);
         $.cookie('userPwd', null);
         $.cookie('remeberPwsd', "0");
     }else {
         $.cookie('remeberPwsd',null);
         $.cookie('userName', $("#userName").val());
         $.cookie('userPwd', $("#userPwd").val());
     }
}