<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Review about the work</title>
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
		<span style="float:left;padding-left:50px;font-size:25px">作业名称:</span>
		<a href="http://localhost:8080/njms/Corrections/CorrectionInit.jsp"><span style="padding-right:50px">返回学生列表</span></a>
		</p>
		<p style="margin:10px 0 0 0">
		<span style="float:left;padding-left:50px;font-size:17px">姓名:</span>
		<span style="float:left;padding-left:50px;font-size:17px">学号/账号:</span>
		<span style="float:left;padding-left:50px;font-size:17px">班级:</span>
		</p>
	</div>
	<div id="control">
		<p align="center">
		客观题得分：<input type="text" value="0.0分"/>&nbsp;&nbsp;&nbsp;
		总分：<input type="text" value="0.0"/>&nbsp;&nbsp;&nbsp;
		<input type="button" value="保存并进入下一份作业"/>&nbsp;&nbsp;&nbsp;
		<input type="button" value="作业打回重做"/>&nbsp;&nbsp;&nbsp;
		</p>
	</div>
	<div id="footer">@福建工程学院</div>
</body>
</html>