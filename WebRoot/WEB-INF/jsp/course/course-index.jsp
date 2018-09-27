<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script type="text/javascript" src="${webroot}/plugin/jquery/jquery-1.9.1.min.js"></script>
    <%@ include file="/common/meta.jsp" %>
    <script type="text/javascript" src="${webroot}/js/course/course-index.js"></script>
    <title>My JSP 'course-index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <style>
	.test {
			font-size: 24px;
			color: #STHeiti;
			font-family:"STHeiti";
		}
	#addCourse {
             width: 170px;
             height: 200px;
             border:1px dashed #C0C0C0;
             display: table-cell;
		        vertical-align: middle;
		        text-align: center;
        }
	</style>
	            <table cellpadding="50">
	                   <tr>
	                       <td>
							   <div style="border:1px dashed #C0C0C0;">
                                   <img height="150px" width="170px" src="${webroot}/images/course_add.png" />
                                   <div style="background-color: #FFFAF4">
	                                   <p class="test">课程名称</p>
	                                   <p>课程简介</p>
	                               </div>
                                </div>
						  </td>
						  <td>
						        <div style="border:1px dashed #C0C0C0;">
	                               <img height="150" width="170" src="${webroot}/images/course_add.png" />
	                               <div style="background-color: #FFFAF4">
                                       <p class="test">课程名称</p>
                                       <p>课程简介</p>
                                   </div>
	                            </div>
                          </td>
                          <td>
	                            <div style="border:1px dashed #C0C0C0;">
	                               <img height="150" width="170" src="${webroot}/images/course_add.png" />
                                  <div style="background-color: #FFFAF4">
                                       <p class="test">课程名称</p>
                                       <p>课程简介</p>
                                   </div>
                                </div>
                          </td>
                          <td>
                               <div id="addCourse" ><img id="addimg" style="opacity:0.15;" height="150" width="170" src="${webroot}/images/course_add.png" />
                                </div>
                          </td>
						</tr>
	            </table>
	            <div id="dd"></div>
  </body>
</html>
