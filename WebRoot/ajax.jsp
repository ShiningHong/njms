<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript">
	var xmlhttp;
	function loadXMLDoc(url,cfunc)
	{
		//创建 XMLHttpRequest 对象
		//为了应对所有的现代浏览器，包括 IE5 和 IE6，请检查浏览器是否支持 XMLHttpRequest 对象。如果支持，则创建 XMLHttpRequest 对象。如果不支持，则创建 ActiveXObject ：
		if (window.XMLHttpRequest)
 		 {// code for IE7+, Firefox, Chrome, Opera, Safari
 				 xmlhttp=new XMLHttpRequest();
 		 }
		else
 		 {// code for IE6, IE5
 				 xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
	    xmlhttp.onreadystatechange=cfunc;
	    //将请求发送到服务器，我们使用 XMLHttpRequest 对象的 open() 和 send() 方法：
	    xmlhttp.open("POST",url,true);//true=异步，false=同步
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	}
	//callback函数
	function myFunction(str)
	{
		//空的话返回空
		if (str.length==0)
 		 {
 			 document.getElementById("myDiv").innerHTML="";
			  return;
 		 }
		loadXMLDoc("category/selectById.do",function()
   		 {
   					 if (xmlhttp.readyState==4 && xmlhttp.status==200)
 					   {
   									 document.getElementById("myDiv").innerHTML= xmlhttp.responseText;
    					}
 		 });
 		 xmlhttp.send("id="+str);
	}
	
	//增加
	function insert(str)
	{
		//空的话返回空
		loadXMLDoc("category/insert.do",function()
   		 {
   					 if (xmlhttp.readyState==4 && xmlhttp.status==200)
 					   {
   									 document.getElementById("insertDiv").innerHTML=xmlhttp.responseText;
    					}
 		 });
 		 xmlhttp.send("name="+str);
	}
	//查询所有
	function update(id,name)
	{
		//空的话返回空
		loadXMLDoc("category/update.do",function()
   		 {
   					 if (xmlhttp.readyState==4 && xmlhttp.status==200)
 					   {
   									 document.getElementById("updateDiv").innerHTML=xmlhttp.responseText;
    					}
 		 });
 		 xmlhttp.send("id="+id+"&name="+name);
	}
</script>
  </head>
  
  <body>
	<h3>请在下面的输入框中键入ID：</h3>
	<form action=""> 
		ID：<input type="text" id="txt1" onkeyup="myFunction(this.value)" />
	</form>
	<p>姓名：<span id="myDiv"></span></p> 
	姓名：<input type="text" id="txt2" />
	<button type="button" onclick="insert(txt2.value)">增加</button>
	<p>增加记录数：<span id="insertDiv"></span></p> 
	ID：<input type="text" id="txt3"  />
	姓名：：<input type="text" id="txt4" />
	<button type="button" onclick="update(txt3.value,txt4.value)">修改</button>
	<p>所有数据：<span id="updateDiv"></span></p> 
  </body>
</html>
