<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow: hidden;">
<head>
<title>福建工程学院网络作业平台</title>

<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${webroot}/css/index.css" />
<link rel="stylesheet" type="text/css" href="${webroot}/css/home.css" />
<link rel="stylesheet" type="text/css" href="${webroot}/css/menuIcon.css" />
<script type="text/javascript" src="${webroot}/js/index.js"></script>
<script type="text/javascript" src="${webroot}/js/home.js"></script>
</head>

<body class="easyui-layout" style="overflow: hidden;position':fixed,width:100%;">
	<div id="paper-back">
		<nav>
		<div class="close"></div>
		<c:if test="${sessionScope.UserSession.role=='教师'}" >
			<a href="#">添加课程</a>
			<a href="#">添加班级</a>
		</c:if>
		<c:if test="${sessionScope.UserSession.role=='学生'}" >
			<a href="#">课程</a>
			<a href="#">注册</a>
		</c:if>
		</nav>
	</div>
	<div id="paper-window">
		<div id="paper-front">
			<div class="hamburger">
				<span></span>
			</div>
			<div id="container">
				<div class="san_head">
					<div class="san_head_logo">
						<p style="font-size:40px;position:absolute;margin-top:-13px;font-weight:bold;color:	#F0F8FF">福建工程学院网络作业平台</p>
					</div>
					<div class="san_head_right" style="position:absolute;margin-left:1000px;">
						<i><a title="退出系统" href="javascript:void(0)"
							onclick="javascript:doLoginOut();"><img
								src="${webroot}/images/work/head_icon6.png" width="24"
								height="24" alt="" /> </a> </i> <i><a title="修改密码"
							href="javascript:void(0)" onclick="javascript:changeMyPaw();"><img
								src="${webroot}/images/work/head_icon4.png" width="24"
								height="24" alt="" /> </a> </i> <i><a title="首页"
							href="javascript:void(0)" onclick="javascript:bindEmaiPhone();"><img
								src="${webroot}/images/work/head_icon2.png" width="24"
								height="24" alt="" /> </a> </i>
						<p>
							<span style="width:100px;height:100px">${sessionScope.UserSession.name}
							</span>,欢迎您！
						</p>
					</div>
				</div>
				
				<iframe id="content" src="${webroot}/login.do?corseUI"
					style="width:100%;height:555px;padding:0;margin:0;" frameborder="0"></iframe>

			</div>
		</div>
	</div>
	<!-- 全局窗口 -->
	<div id="globalModalWindow1" class="easyui-window" title="全局操作窗口"
		minimizable="false" collapsible="false" closed="true"
		iconCls="icon-edit" inline="false" modal="true" shadow="false"
		style="width:500px;height:400px;padding:0px;overflow:hidden">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding:0px;border:0px;overflow:hidden">
				<iframe src="about:blank" style="width:100%;height:100%;"
					frameborder="0" id="globalModalFrm1"></iframe>
			</div>
		</div>
	</div>


	<!--global model window2-->
	<div id="globalModalWindow2" class="easyui-window" title="全局操作窗口2"
		minimizable="false" collapsible="false" closed="true"
		iconCls="icon-edit" inline="false" modal="true" shadow="false"
		style="width:500px;height:400px;padding:0px;overflow:hidden">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding:0px;border:0px;overflow:hidden">
				<iframe src="about:blank"
					style="width:100%;height:100%;padding:0;margin:0;" frameborder="0"
					id="globalModalFrm2"></iframe>
			</div>
		</div>
	</div>

	<!--global model window3-->
	<div id="globalModalWindow3" class="easyui-window" title="全局操作窗口3"
		minimizable="false" collapsible="false" closed="true"
		iconCls="icon-edit" inline="false" modal="true" shadow="false"
		style="width:500px;height:400px;padding:0px;overflow:hidden">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding:0px;border:0px;overflow:hidden">
				<iframe src="about:blank" style="width:100%;height:100%;"
					frameborder="0" id="globalModalFrm3"></iframe>
			</div>
		</div>
	</div>

	<!--global model window4-->
	<div id="globalModalWindow4" class="easyui-window" title="全局操作窗口4"
		minimizable="false" collapsible="false" closed="true"
		iconCls="icon-edit" inline="false" modal="true" shadow="false"
		style="width:500px;height:400px;padding:0px;overflow:hidden">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding:0px;border:0px;overflow:hidden">
				<iframe src="about:blank" style="width:100%;height:100%;"
					frameborder="0" id="globalModalFrm4"></iframe>
			</div>
		</div>
	</div>

	<!--global model window5-->
	<div id="globalModalWindow5" class="easyui-window" title="全局操作窗口5"
		minimizable="false" collapsible="false" closed="true"
		iconCls="icon-edit" inline="false" modal="true" shadow="false"
		style="width:500px;height:400px;padding:0px;overflow:hidden">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding:0px;border:0px;overflow:hidden">
				<iframe src="about:blank" style="width:100%;height:100%;"
					frameborder="0" id="globalModalFrm5"></iframe>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${webroot}/plugin/jquery/jquery-1.11.0.min.js""></script>
	<script>
	  jQuery.noConflict();
      var paperMenu = {
	    jQuerywindow: jQuery('#paper-window'),
	    jQuerypaperFront:jQuery('#paper-front'),
	    jQueryhamburger: jQuery('.hamburger'),
	    offset: 1800,
	    pageHeight: jQuery('#paper-front').outerHeight(),
	    open: function () {
	        this.jQuerywindow.addClass('tilt');
	        this.jQueryhamburger.off('click');
	        jQuery('#container, .hamburger').on('click', this.close.bind(this));
	        this.hamburgerFix(true);
	        // console.log('opening...');
	    },
	    close: function () {
	        this.jQuerywindow.removeClass('tilt');
	        jQuery('#container, .hamburger').off('click');
	        this.jQueryhamburger.on('click', this.open.bind(this));
	        this.hamburgerFix(false);
	        // console.log('closing...');
	    },
	    updateTransformOrigin: function () {
	        scrollTop = this.jQuerywindow.scrollTop();
	        equation = (scrollTop + this.offset) / this.pageHeight * 100;
	        this.jQuerypaperFront.css('transform-origin', 'center ' + equation + '%');
	    },
	    hamburgerFix: function (opening) {
	        if (opening) {
	           jQuery('.hamburger').css({
	                position: 'absolute',
	                top: this.jQuerywindow.scrollTop() + 20 + 'px'
	            });
	        } else {
	            setTimeout(function () {
	                jQuery('.hamburger').css({
	                    position: 'fixed',
	                    top: '20px'
	                });
	            }, 300);
	        }
	    },
	    bindEvents: function () {
	        this.jQueryhamburger.on('click', this.open.bind(this));
	        jQuery('.close').on('click', this.close.bind(this));
	        this.jQuerywindow.on('scroll', this.updateTransformOrigin.bind(this));
	    },
	    init: function () {
	        this.bindEvents();
	        this.updateTransformOrigin();
	    }
	};
	paperMenu.init();
    </script>
</body>
</html>
