	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<%-- 将IE8用IE7进行渲染，使网页在IE8下正常显示
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	--%>
	<base href="${webroot}/" />
	<script language="javascript">
	var GLOBAL = {
		    WEBROOT:"${webroot}",
		    MENU_ID:"${currentMenuId}",
            FILE_MAX_SIZE: "${fileUploadMaxSize}"
		};
	</script>
	
	<%-- jquery.js 中引入jquery-版本号.min.js、jquery-easyui-版本号（js+css）、jquery.metadata.js --%>
	<script language="javascript" src="${webroot}/js/meta/jquery.js"></script>
	<link rel="stylesheet" type="text/css" href="${webroot}/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="${webroot}/css/optBtn.css"/>
	<script type="text/javascript" src="${webroot}/js/meta/utils.js"></script>
	<script language="javascript" src="${webroot}/js/meta/frameajax.js"></script>
    <link type="text/css" rel="stylesheet" href="${webroot}/css/frameajax.css"/>
	<script type="text/javascript" src="${webroot}/plugin/flexigrid/flexGridUtil.js"></script>
	
	