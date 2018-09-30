<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="gridName" value="${sessionScope.NEWN_SESSION_USER.userGrid.gridName}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>    
    <%@ include file="/common/meta.jsp" %>
    
    
    <script src="${webroot}/js/meta/validate.js" type="text/javascript"></script>
    
    
     <script src="${webroot}/js/sceneInfoFind-index.js" type="text/javascript"></script>
  </head>
  
  <body>
    <fieldset>
        <legend class="condition">查询条件</legend>
        <form id="queryForm" name="queryForm">
        <table class="b-xbox">
                <td class="label" width="20px" >姓名</td>
                <td class="content">
                    <input type="text" size=15 id="name" name="name"  />
                </td>
                <td class="queryButtonArea" colspan="8">
                    <input type="button" value="重置" id="resetBtn"/>
	                <input type="button" value="查询" id="qryBtn"/>
                </td>
              </tr>
              
        </table>
        </form>
    </fieldset>
    <table id="listTable"></table>
  </body>
</html>
