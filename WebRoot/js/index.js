$(document).ready(function(){
	//initWinOpen();
});

//预定义三个弹出窗
initWinOpen = function(){
	for(var i=1; i<=3; i++){
		if(!document.getElementById("globalModalWindow"+i)){
			var globalModalWindow = 
			'<div id="globalModalWindow'+i+'" class="easyui-window" title="全局操作窗口'+i+'" minimizable="false" collapsible="false" closed="true" iconCls="icon-edit" inline="false" modal="true" shadow="false" style="padding:5px;background: #fafafa;overflow:hidden">'+
				'<div class="easyui-layout" fit="true">'+
					'<div region="center" border="false" style="width:100%;height:100%;padding:0px;background:#fff;border:0px solid #ccc;overflow:hidden">'+
						'<iframe src="about:blank" style="width:100%;height:100%;" frameborder="0" id="globalModalFrm'+i+'"></iframe>'+
					'</div>'+
				'</div>'+
			'</div>';
			$("body").append(globalModalWindow);
		}
	}
};


/*
 *  首页脚本 by fangll
 *  depend util.js,jquery-1.2+.js
 *  version:0.3
*/

var TAB = {
	index:0,
	count:1,
	id:"tabBar",
	//homeTitle:"工作台"
	homeTitle:"首页"
};

//添加页签
TAB.add = function(option){
	TAB.index++;
	var title = option.title||"新页签_"+TAB.index;
	var url = option.url||"about:blank";	
	var menuId = option.menuId||"0";
	var refresh = option.refresh||false;
	var closable = option.closable||true;
	var scrollable = option.scrollable||"no";
	url = WEB.trim(url);	
	var httpReg = /^http/;
	if(httpReg.test(url)){
	  ///以http开头，替换参数
	  //url = WEB.replaceAll(url,"{userLoginName}","5900015504");
	} else {
	  if(url!="about:blank"&&url!="developing"){
		url = GLOBAL.WEBROOT+"/"+url;
	  }else{
		  url = GLOBAL.WEBROOT+"/common/developing.html";
	  }
	}
	var addLog = false;
	if(url.indexOf('currentMenuId')<0){
		if(menuId!='0'){//传入当前菜单ID
			if(url.indexOf('?')>-1){
				url += '&';
			}else{
				url += '?';
			}
			url += "currentMenuId="+menuId;
			addLog = true;
		}
	}
	
	//根据title选择tab
	//$("#tabBar").tabs('select','网格经理工作台-主页');
	
	//如存在则显示,否则新建页签
	if($("#"+this.id).tabs('exists',title)){
		TAB.select(title,refresh,url);
		TAB.refresh(title,url);
	}else{
		//判断打开页签个数
		var tabs = $("div.tabs-panels >div");
		var count = tabs.length;
		if(count>=10){
			MSN.open("您已经打开超过10个页签,请关闭部分页签!\n\n超过10个页签可能会造成运行速度缓慢");
		}

		//新增页签
		$('#'+this.id).tabs('add',{
			fit : true,
			title:title,
			content:'<iframe scrolling="'+scrollable+'" style="width:100%;height:100%;" src="'+url+'" name="tabIframe_'+TAB.index+'" id="tabIframe_'+TAB.index+'" frameborder="0"></iframe>',
			closable:closable
		});
	}
};

//根据title选择页签,refresh{true:"刷新页面",false:"不刷新页面"}
TAB.select = function(title,refresh,url){
	$("#"+this.id).tabs('select',title);
	if(refresh){
		TAB.refresh(title,url);
	}
};

//取当前标签iframe对象
TAB.getSelectFrame = function(){
	var obj = $('#'+this.id).tabs('getSelected');
	var tab = obj.panel('options').tab;
	var id = obj.children().attr("id");
	var name = obj.children().attr("name");
	var src = obj.children().attr("src");
	var frameObj = {"name":name,"id":id,"src":src};
	return frameObj;
};

//根据title刷新页签
TAB.refresh = function(title,url){				
	var tab = $("#"+this.id).tabs('getTab',title);
	if(tab){
		if(url){
			tab.children().attr({src:url});
		}else{
			var oldsrc = tab.children().attr("src");
			if(oldsrc){
				tab.children().attr({src:oldsrc});
			}
		}
		
	}
};

//根据title关闭页签
TAB.close = function(title){
	var tab = $("#"+this.id).tabs('close',title);
};

//显示主页,取index=0的页签
TAB.home = function(refresh){		
	$("#"+this.id).tabs('select',TAB.homeTitle);
};	

//触发tab中的方法
TAB.buttonClick = function(title,buttonId){			
	var tab = $("#tabBar").tabs('getTab',title);
	if(tab){
	    tab.children().contents().find("#"+buttonId).click();
	}
};

//全局窗口
var WIN = {
	id:"1",
	param:false,
	title:"操作",
	url:WEB.WEBROOT+"/common/blank.jsp",
	width:750,
	height:400,
	left:20,
	top:20,
	right:20,
	shadow:false,
	modal:true,
	closable:true	
};

WIN.open = function(option){	
	var objId = option.id||this.id
	var title = option.title||this.title;
	var width = option.width||this.width;
	var height = option.height||this.height;
	if(width>$(window).width()){
		width = $(window).width()-10;
	}
	if(height>$(window).height()){
		height = $(window).height()-10;
	}
	var	left = (window.screen.width - width)/2;
	var	top = (window.screen.height - height)/2 - 100;
	var right = option.right||this.right;
	
	//如果设置left,top参数,则覆盖默认居中
	left = option.left||left;
	top = option.top||top;
	
	var shadow = typeof(option.shadow)=="boolean"?option.shadow:this.shadow;
	var modal = typeof(option.modal)=="boolean"?option.modal:this.modal;	
	var closable = typeof(option.closable)=="boolean"?option.closable:this.closable;
	var closed = option.closed||false;
	var url = option.url||"about:blank";
	//var iconCls = option.iconCls||"icon-edit";
	var iconCls = option.iconCls||'';
	
    if(url==""){
		alert("url参数不能为空");
	}
	
	if(url!="about:blank"&&url!="developing"){
		if(url.indexOf("http")<0){
			url = GLOBAL.WEBROOT+"/"+url;
		}
	}else{
		url = GLOBAL.WEBROOT+"/common/developing.html";
	}
	
	$('#globalModalWindow'+objId).window({
		title: title,
		width: width,
		height: height,
		top:top,
		left:left,
		closable: closable,
		modal: modal,
		shadow: shadow,
		iconCls:iconCls,
		closed: closed,
		onClose: function(){
		  /*
	      if(option.onClose){
    		option.onClose(WIN.param);
    		WIN.param = false; 
	      }*/
	      if(option.onClose){
	    	if(WIN.param){
	    		option.onClose(WIN.param);
	    		WIN.param = false;
	    	}
	      }
	    },
		onBeforeClose:function(){
	      $("#globalModalFrm"+objId).attr({src:"about:blank"});
		  if(option.onBeforeClose){
		    option.onBeforeClose();
		  }
		}		
	});
	
	$("#globalModalFrm"+objId).attr({src:url});
	//$('#globalModalWindow').window('open');
};

WIN.close = function(id,param){
	if(id==undefined){id = this.id;}
	WIN.param = param;			
	$("#globalModalFrm"+id).attr({src:"about:blank"});
	$('#globalModalWindow'+id).window('close');
};
WIN.modifyTitle = function(id,title){
	$('#globalModalWindow'+id).panel({title:title});
};

var ALARM = {
	id:"#alarmWindow",
	width:300,
	height:400
};

ALARMInit = function(){
	setInerval(this.show(),5000);
};

ALARM.show = function(){	
	$(this.id).window({closed: false});
};

ALARM.close = function(){
	
};

ALARM.getAlarmInfoList = function(){
	
};

ALARM.getAlarmDetail = function(){
	
};


