<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<html>
  <head>
    <title>福建工程学院网络作业平台</title>
    <%@ include file="/common/login_meta.jsp" %>
    <%@ include file="/common/meta.jsp" %>
    <link href="${webroot}/css/login.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${webroot}/plugin/jquery.cookie/jquery.cookie.js"></script>
    <script type="text/javascript" src="${webroot}/js/login/login.js"></script>
    <!--引入验证码  -->
    <meta charset="utf-8">
    <link rel="stylesheet" href="${webroot}/plugin/identify-code/jquery.idcode.css" />
	<script type="text/javascript" src="${webroot}/plugin/identify-code/jquery.idcode.js"></script>
  </head>
  
  <body  class="login_bg">
    <input name="codeUuid" id="codeUuid" type="hidden" value=""></input> 
    
    <!--头部 begin-->
    <!--login_head-->
    <div class="login_head">
        <div class="login_logo"><img src="${webroot}/images/work/logo.png" width="580" height="54" alt=""/></div>
    </div>

    <!--头部 over-->

    <!--登录区域 begin-->



    <!--login_main-->
    <form id="loginForm" name="loginForm" autocomplete="off">
    <div class="login_mian">
        <img src="${webroot}/images/work/login_bg.jpg" width="100%" alt=""/>
        <div class="login_main_center">
            <div class="login_main_center_mid">
                <!--用户登录-->
                <div class="login_user">
                    <h1>用户登录</h1>
                    <div class="login_input_content">
                        <div class="login_input"><i><img src="${webroot}/images/work/login_user.png" width="30" height="30" alt=""/></i>
                            <input style="background: rgb(255, 255, 255); color: rgb(221, 221, 221);" type="text" name="userLoginName" id="userName" type="text" title="登录名" value="登录名" autocomplete="off"/ minChars="1"></div>
                        <div class="login_input"><i><img src="${webroot}/images/work/login_password.png" width="30" height="30" alt=""/></i>
                            <input type="password" name="userPwd" id="userPwd" type="password"  autocomplete="off"></div>
  						<div><div class="login_input" style="padding: 0 0 0 10px;width:30%">
                            <input maxlength="5" style="background: rgb(255, 255, 255); color: rgb(221, 221, 221);" class="txtVerification"; type="text" name="Txtidcode" id="Txtidcode" type="text" title="验证码" value="验证码" autocomplete="on"/></div>
                            <span style="width:344px;height:54px;position:absolute;margin-left:-53px;margin-top:-53px" id="idcode">11</span></div>
                        <p><label><input type="checkbox" id="remeberPwsd" checked="checked" value="1" /><span>记住密码</span></label></p>
                        <div class="login_btn">
                            <div class="login_btn_left"><a href="javascript:void(0);"   id="loginBtn"><span>登录</span></a></div>
                            <div class="login_btn_right"><a href="javascript:void(0);"  id="loginResetBtn"><span>重置</span></a></div>
                        </div>
                        <div class="lg_login_tip" id="login_result_tip"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </form>

    <!--登录区域 over-->
    <!--底部 begin-->
    <div class="lb_bottom">
        <div class="wrap">
            <p>@福建工程学院</p>
        </div>
    </div>
    <!--底部 over-->
    
    <div id="globalModalWindow1" class="easyui-window" title="全局操作窗口" minimizable="false" collapsible="false" closed="true" iconCls="icon-edit" inline="false" modal="true" shadow="false" style="width:500px;height:400px;padding:0px;overflow:hidden">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding:0px;border:0px;overflow:hidden">
                <iframe src="about:blank" style="width:100%;height:100%;" frameborder="0" id="globalModalFrm1"></iframe>
            </div>
        </div>
    </div>
    <script>
			$.idcode.setCode();
	</script>
  </body>
</html>
