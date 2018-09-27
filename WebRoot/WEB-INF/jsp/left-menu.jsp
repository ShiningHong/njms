<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


  <!--left begin-->
<div class="easyui-accordion" fit="true" border="false" id="lmenu">
            <ul class="lmenu panel-header" >
            <li style="color: #000;text-align:center" onclick="javascript:openMenuTab({title:'首页',url:'/login.do?corseUI','menuId':'3','scrollable':'no'})">
                首页
            </li>
            <i class="panel-icon lm_icon_style headmenu-icon-menuOp"/>
        </ul>
		<ul class="lmenu panel-header" >
			<li style="color: #000;text-align:center" onclick="javascript:openMenuTab({title:' 创建课程',url:'/toplogin.do','menuId':'1','scrollable':'no'})">
			创建课程
			</li>
			<i class="panel-icon lm_icon_style headmenu-icon-menuOp"/>
		</ul>
		<ul class="lmenu panel-header" >
            <li style="color: #000;text-align:center" onclick="javascript:openMenuTab({title:' 创建班级',url:'/toplogin.do','menuId':'2','scrollable':'no'})">
                    创建班级
            </li>
            <i class="panel-icon lm_icon_style headmenu-icon-menuOp"/>
        </ul>
        <ul class="lmenu panel-header" >
            <li style="color: #000;text-align:center" onclick="javascript:openMenuTab({title:'人员管理',url:'/toplogin.do','menuId':'3','scrollable':'no'})">
                 人员管理
            </li>
            <i class="panel-icon lm_icon_style headmenu-icon-menuOp" />
        </ul>
</div>
<!--left end-->
    
   
 