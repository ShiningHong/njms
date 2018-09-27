<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>福建工程学院网络作业平台</title>
    <!--  -->
    <script type="text/javascript" src="${webroot}/plugin/jquery/jquery-1.9.1.min.js"></script>

    <%@ include file="/common/meta.jsp" %>
    <link rel="stylesheet" type="text/css" href="${webroot}/css/index.css"/>
    <link rel="stylesheet" type="text/css" href="${webroot}/css/menuIcon.css"/>
    <script type="text/javascript" src="${webroot}/js/index.js"></script>
    <script type="text/javascript" src="${webroot}/js/home.js"></script>
    <script type="text/javascript" language="javascript">
        /*
        var PWD_LEVER = "${_USER_INFO_MAP_.pwdLevel}";
		*/

        window.onbeforeunload = function(e){
            if(window.event.clientY< 0 && window.event.clientX > document.documentElement.scrollWidth-50){
                return "离开页面将会退出系统。";
            }else{
                if(window.event.altKey){
                    var evt = e?e:(window.event?window.event:null);
                    if(evt){
                        if(window.event.altKey){
                            evt.returnValue = "离开页面将会退出系统。";
                        }else if(evt.clientY<0){
                            evt.returnValue = "离开页面将会退出系统。";
                        }else if(evt.type=='beforeunload'){
                            if(window.screen.height>1000&&evt.clientY>850){
                                evt.returnValue = "离开页面将会退出系统。";
                            }else if(evt.clientY>600){
                                evt.returnValue = "离开页面将会退出系统。";
                            }

                        }
                    }
                }else{
                    window.onunload = null;
                }
            }
        }
    </script>
</head>

<body class="easyui-layout" style="overflow: hidden;">

<%-- header begin--%>
<div region="north" border="false" title="" style="overflow: hidden;height: 75px;" id="headerDiv">

    <div class="san_head" >
        <div class="san_head_logo"><a href=""><img src="${webroot}/images/work/logo.png" width="580" height="54" alt=""/></a></div>
        <div class="san_head_right">
            <i><a title="退出系统" href="javascript:void(0)"  onclick="javascript:doLoginOut();"><img src="${webroot}/images/work/head_icon6.png" width="24" height="24" alt=""/></a></i>
           <i><a title="修改密码" href="javascript:void(0)"  onclick="javascript:changeMyPaw();"><img src="${webroot}/images/work/head_icon4.png" width="24" height="24" alt=""/></a></i>
            <i><a title="首页" href="javascript:void(0)"  onclick="javascript:bindEmaiPhone();"><img src="${webroot}/images/work/head_icon2.png" width="24" height="24" alt=""/></a></i>
            <p><span>池文杉</span>，欢迎您！</p>
        </div>
       <div class="topRound" id="noticeCountDiv" style="display: none;">
            <span id="notice_unReadNumSpan"></span>
        </div>
    </div>
</div>
<%-- header end--%>

<!-- left -->
<div region="west" title=" " style="width:150px;"  class="wrap100 home" border="false" id="menuDiv"  split="false">
    <div id="lmenuDiv" style="background:url(${webroot}/images/test.jpg);" class="left">
<!--        <div style="font-size:20px;height: 30px;width:180px;text-align:center;">添加课程</div>
        <div>添加课程</div>
        <div>添加课程</div>-->
    </div> 
</div>


<!-- 打开菜单模块 -->
<div  region="center" border="false" style="overflow: hidden;background-color: #f3f3f3" id="tabDiv" class="wrap100 home" split="false">
    <div class="easyui-tabs right" fit="true" border="false"  id="tabBar" >
    </div>
</div>


<div id="globalModalWindow1" class="easyui-window" title="全局操作窗口" minimizable="false" collapsible="false" closed="true" iconCls="icon-edit" inline="false" modal="true" shadow="false" style="width:500px;height:400px;padding:0px;overflow:hidden">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding:0px;border:0px;overflow:hidden">
            <iframe src="about:blank" style="width:100%;height:100%;" frameborder="0" id="globalModalFrm1"></iframe>
        </div>
    </div>
</div>


<!--global model window2-->
<div id="globalModalWindow2" class="easyui-window" title="全局操作窗口2" minimizable="false" collapsible="false" closed="true" iconCls="icon-edit" inline="false" modal="true" shadow="false" style="width:500px;height:400px;padding:0px;overflow:hidden">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding:0px;border:0px;overflow:hidden">
            <iframe src="about:blank" style="width:100%;height:100%;padding:0;margin:0;" frameborder="0" id="globalModalFrm2"></iframe>
        </div>
    </div>
</div>

<!--global model window3-->
<div id="globalModalWindow3" class="easyui-window" title="全局操作窗口3" minimizable="false" collapsible="false" closed="true" iconCls="icon-edit" inline="false" modal="true" shadow="false" style="width:500px;height:400px;padding:0px;overflow:hidden">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding:0px;border:0px;overflow:hidden">
            <iframe src="about:blank" style="width:100%;height:100%;" frameborder="0" id="globalModalFrm3"></iframe>
        </div>
    </div>
</div>

<!--global model window4-->
<div id="globalModalWindow4" class="easyui-window" title="全局操作窗口4" minimizable="false" collapsible="false" closed="true" iconCls="icon-edit" inline="false" modal="true" shadow="false" style="width:500px;height:400px;padding:0px;overflow:hidden">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding:0px;border:0px;overflow:hidden">
            <iframe src="about:blank" style="width:100%;height:100%;" frameborder="0" id="globalModalFrm4"></iframe>
        </div>
    </div>
</div>

<!--global model window5-->
<div id="globalModalWindow5" class="easyui-window" title="全局操作窗口5" minimizable="false" collapsible="false" closed="true" iconCls="icon-edit" inline="false" modal="true" shadow="false" style="width:500px;height:400px;padding:0px;overflow:hidden">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding:0px;border:0px;overflow:hidden">
            <iframe src="about:blank" style="width:100%;height:100%;" frameborder="0" id="globalModalFrm5"></iframe>
        </div>
    </div>
</div>

</body>
</html>
