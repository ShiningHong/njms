$(document).ready(function(){
    //绑定登录事件
    $("#loginBtn").bind("click", function(){
    	if(validate()==true){
    		doLogin($("#loginBtn"));
	       	 /*var url = GLOBAL.WEBROOT+ "/login.do?loginIndex";
	         window.top.location = url;*/
    	}
    });
    document.onkeydown = function(e){ 
         var ev = document.all ? window.event : e;
         if(ev.keyCode==13) {
             doLogin($("#loginBtn"));
        }
    };
    //绑定焦点获取和失去文本域所触发的事件
    focusAndOutInput();
    //初始化记住密码按钮
    initRememberPwsd();
    //重置表单
    $("#loginResetBtn").bind("click", function(){
        resetLoginForm();
    });
});

//焦点获取和失去文本域所触发的事件
focusAndOutInput=function(){
	 $("#userName").val('登录名');
	    $("#Txtidcode").val('验证码');
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
	    $("#Txtidcode").focus(function(){
	        $(this).css("color","black");
	        this.style.fontSize = '25px';
	        if(this.value=='验证码'){
	              $("#Txtidcode").val("");
	        }
		});
		$("#Txtidcode").blur(function(){
		        if(this.value == ''){
		            $(this).css("color","#DDD");
		            this.style.fontSize = '15px';
		            $("#Txtidcode").val("验证码");
		        }
		  });
};

//重置form表单
resetLoginForm = function(){
    $("#Txtidcode").css("color","#DDD");
    $("#Txtidcode").css("font-size",'15px');
    $("#Txtidcode").val("验证码");
    $("#userName").css("color","#DDD");
    $("#userName").val("登录名");
    $("<input type='reset' style='display:none;' id='formResetBtn' />").appendTo($("#loginForm"));
    $("#formResetBtn").click();
    $("#formResetBtn").empty().remove();
    $("#login_result_tip").html("");
    $.idcode.setCode();

};

//校验
validate = function(){
    $("#login_result_tip").html("");
    if($.trim($("#userName").val()) == "" || $.trim($("#userName").val()) == "登录名"){
        $("#userName").focus();
        $("#login_result_tip").html("请输入登录名!");
        return false;
    }
    if($.trim($("#userPwd").val()) == "" ){
        $("#userPwd").focus();
        $("#login_result_tip").html("请输入密码!");
        return false;
    }
    if($("#Txtidcode").val()=='验证码'){
    	$("#Txtidcode").focus();
        $("#login_result_tip").html("请输入验证码!");
        return false;
	}
    var IsBy = $.idcode.validateCode(); 
	if(IsBy==false){
        $("#Txtidcode").val("");
		$("#Txtidcode").focus();
        $("#login_result_tip").html("验证码输入错误!");
        return false;
	}
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
    //判断密码是否正确
    $.ajax({
	    url: GLOBAL.WEBROOT+'/login/checkLogin.do',
	    type: 'post',
	    dataType: 'json',
	    data: data,
	    success: function(data){
	    	$(obj).find("span").html(loginTip);
	    	if(data==true){
	    		var url = GLOBAL.WEBROOT+ "/login.do?loginIndex";
	            window.top.location = url;
	    	}
	    	else{
	    		 $("#login_result_tip").html("用户不存在或密码错误!");
	    	}
	    	//window.location = GLOBAL.WEBROOT+"/index.jsp";
	    },
	    error:function(result){
	    	$(obj).find("span").empty().append(loginTip);
	  	}
	 });
};

//获取记住的密码
function initRememberPwsd(){
    var  userName  = $.cookie('userName');
    var  userPwd  = $.cookie('userPwd');
    var  remeberPwsd =  $.cookie('remeberPwsd');
    if(userName!=null&&userName!="null"){
    	$("#userName").css("color","black");
        $("#userName").val(userName);
    }
    if(userPwd!=null&&userPwd!="null"){
        $("#userPwd").val(userPwd);
    }
    if(remeberPwsd!=null&&remeberPwsd=="0"){
        $("#remeberPwsd").attr("checked","");
    }
}

//记住密码操作
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