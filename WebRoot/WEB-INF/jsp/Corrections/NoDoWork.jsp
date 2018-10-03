<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>About NoDowork</title>
<style>
#header {
	background-color: black;
	color: white;
	text-align: center;
	padding: 5px;
}

#login {
	background-color: red;
	padding: 1px;
}

.userStyle {
	text-align: right;
}

#navigation {
	background-color: yellow;
	padding: 1px;
}

#main {
	margin-left: 100px;
	height:300px;
}
#control{
	margin-left:150px;
	padding:5px;
}
#footer {
	background-color: black;
	color: white;
	clear: both;
	text-align: center;
	padding: 5px;
}
</style>
</head>
<body>
	<div id="header">
		<h1>福建工程学院</h1>
		<h1>网络作业管理平台</h1>
	</div>
	<div id="login">
		<p class="userStyle">
		<span  style="padding-right:20px">用户:ShiningHong&nbsp;&nbsp;&nbsp;</span>
		<a href="https://www.baidu.com/"><span  style="padding-right:20px">退出</span></a></p>
	</div>
	<div id="navigation">
		<p align="right">
		<span  style="float:left;font-weight:bold;padding-left:100px">课程名称：Web应用程序设计</span>
		<a href="https://www.baidu.com/"><span style="padding-right:20px">首页</span></a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="https://www.baidu.com/"><span  style="padding-right:20px">作业</span></a>
		</p>
	</div>
	<div id="main">
		<p align="right">
		<span style="float:left;padding-left:50px;font-size:25px">作业名称:</span><br>
		<a href="https://www.baidu.com/"><span style="padding-right:50px">返回</span></a>
		</p>
		<p style="padding-left:50px;margin:20px 0 18px 0">
		<span style="float:left;font-size:25px">作业名称:</span>
		</p><br>
		<p style="padding-left:50px;margin:20px 0 18px 0">
		<span style="float:left;font-size:20px">发送对象:</span>
		<span style="float:left;font-size:20px">有效时间段:</span>
		</p><br>
		<p style="padding-left:50px;margin:20px 0 18px 0">
		<input type="text" value="请输入学号或姓名"/>
		<input type="button" value="查找"/>
		</p>
	</div>
	<div id="control">
		<p align="center">
		<input type="button" value="导出名单"/>&nbsp;&nbsp;&nbsp;
		</p>
	</div>
	<div id="footer">@福建工程学院</div>
</body>
</html>