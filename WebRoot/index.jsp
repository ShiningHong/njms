<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
  <head>
    <title>福建联通综合服务支撑平台</title>
	<script type="text/javascript" src="${webroot}/js/globalUtil.js"></script>
  </head>
  <body>
    <script language="javascript">
      var ref =  GetRefValue();
      var url = "${webroot}/home.do";
      if(isNoNull(ref)){
          url = ref;
      }
      window.top.location = url;
   </script>
  </body>
</html>
