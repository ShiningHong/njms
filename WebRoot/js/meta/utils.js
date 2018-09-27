/*
 *  depend jquery-1.1.js+
 * 
*/
/*常用工具类*/

var WEB = {};
WEB.WEBROOT = window.GLOBAL.WEBROOT||"/njms";
WEB.$d = function (id) {
	var obj = document.getElementById(id);
	if (obj != null){
		return obj;
	}else{
		try{
			obj = top.window.frames['frm_top'].document.getElementById(id);	
		}catch(e){
			obj = null;
		}
		if (obj != null){
			return obj;
		}
	}
	return null;
};
	 
WEB.checkNum = function(obj) {
	if(event.keyCode == 13 ||event.keyCode == 8){
	   return;	
	}
	if ((event.keyCode < 44 || event.keyCode > 58)) {
		alert("\u8bf7\u8f93\u5165\u6570\u5b57");
		obj.focus();
		event.returnValue = false;
	}
}; 

WEB.pad = function (str,length,flag,pos){//格式化字符串为length长度,不足用flag补,pos{"r":右补,其它:左补}
	str = str + "";
	var len = length - (str.length);
	var p = "";
	for (var i = 1; i <= len; i++) {
		p += flag;
	}
	if(pos=="r"){
		str = str + "" + p;
	}else{
		str = p + "" + str;
	}
	return str;
};
	
WEB.lpad = function(str,len){//左补零
	var t = "0000000000000000"+str;
	return t.substring(t.length-len,t.length);
};

WEB.rpad = function(str,len){//右补零
	var t = str+"0000000000000000";
	return t.substring(0,len);
};

WEB.replaceAll = function(str,f,t){
	return  str.replace(new RegExp(f,"gm"),t);
};

WEB.trim = function (strValue){
   return strValue.replace(/^\s*|\s*$/g,"");
};	

WEB.placeholder =function(){	     
  var ary = [];			   
  for(i = 1 ; i < arguments.length ; i++){	  
   ary.push(arguments[i]);
  }	 
  return arguments[0].replace(/\{(\d+)\}/g,function(m ,i){
    return ary[i];
  });
};

WEB.htmlEscape = function(str){
	return str.replace(/<[^>].*?>/g,"");
};	
	
//math
WEB.round=function (number,X) {//4舍5入
    //rounds number to X decimal places, defaults to 2
	X = (!X ? 2 : X);
	return Math.round(number*Math.pow(10,X))/Math.pow(10,X);
};

WEB.random=function(first,last){//产生 first~last范围内的随时数
  var max = last-first+1;
  return Math.floor(Math.random()*max+first);
};
	
//checkbox event
WEB.isSelected=function (str, substr) {
	var strArr = str.split(",");
	for (var i = 0; i < strArr.length; i++) {
		if (strArr[i] == substr) {
			return true;
		}
	}
	return false;
};	

WEB.isChecked=function(obj){
   var d=document.getElementsByName(obj);
   for(var i=0;i<d.length;i++){
      if(d[i].checked) return true;
   }
   return false;
};	

WEB.checkRev=function (cname) {
	var obj = document.getElementsByName(cname);
	var len = obj.length;
	for (var i = 0; i < len; i++) {
		if (obj[i].checked) {
			obj[i].checked = false;
		} else {
			obj[i].checked = true;
		}
	}
};

WEB.checkNone=function (cname) {
	var obj = document.getElementsByName(cname);
	var len = obj.length;
	for (var i = 0; i < len; i++) {
		obj[i].checked = false;
	}
};

WEB.checkAll=function (cname) {
	var obj = document.getElementsByName(cname);
	var len = obj.length;
	for (var i = 0; i < len; i++) {
		obj[i].checked = true;
	}
};

WEB.checkToggle=function (cname) {
	var obj = document.getElementsByName(cname);
	var len = obj.length;
	var o = event.srcElement;
	for (var i = 0; i < len; i++) {
		obj[i].checked = o.checked;
	}
};

//String
WEB.NVL=function(str){
	return (str == null) ? "" : str;
};
	 
WEB.getValue2=function (str, de) {
    str = WEB.NVL(str);
	return (str == "") ? de : str;
}; 
WEB.getValue=function (str) {
	return WEB.NVL(str);
}; 

	
WEB.popParam=function(options){
  var p="";
  if(options.param){//window parameter	      
	  if(options.param.w!=undefined){p+="dialogWidth:"+options.param.w+"px;";}else{p+="dialogWidth:360px;";}
	  if(options.param.h!=undefined){p+="dialogHeight:"+options.param.h+"px;";}else{p+="dialogHeight:300px;";}
	  if(options.param.s!=undefined){p+="status:"+options.param.s+";";}else{p+="status:no;";}
	  if(options.param.m!=undefined){p+="menubar:"+options.param.m+";";}else{p+="menubar:no;";}
	  if(options.param.r!=undefined){p+="resizable:"+options.param.r+";";}else{p+="resizable:yes;";}
	  if(options.param.p!=undefined){p+="help:"+options.param.p+";";}else{p+="help:no;";}
         var x = e=window.event?(window.event.screenX):0;	
         var y = e=window.event?(window.event.screenY+13):0;
	  if(options.param.y!=undefined){p += "dialogTop:"+(y+options.param.y)+"px;";}
	  if(options.param.x!=undefined){p += "dialogLeft:"+(x+options.param.x)+"px;";}
	  p+="maximize:yes";
	  //minimize:yes;
  }else{
      p+="dialogWidth:360px;";
      p+="dialogHeight:300px;";
      p+="status:no;";
      p+="resizable:yes;";
      p+="menubar:no;";
      p+="help:no;";	  
  }		  
  return p;

};
	
	//window
WEB.popDlg = function (url, w, h,t,l) {   
    var x = e=window.event?(window.event.screenX):0;	
    var y = e=window.event?(window.event.screenY+13):0;	      
	var p = "";
	p += "help:no";
	p += ";status:no";
	p += ";dialogWidth:" + w + "px";
	p += ";dialogHeight:" + h + "px";
	if(t!=undefined){p += ";dialogTop:"+(y+t)+"px";}
	if(l!=undefined){p += ";dialogLeft:"+(x+l)+"px";}
	p += ";menubar:no";
	p += ";resizable:yes";
	p += ";maximize:yes";
	//;minimize:yes
	return window.showModalDialog(url, self, p);
};
	
WEB.pop = function (url, w, h,t,l) { 
	if(!w) w = 500;
	if(!h) h = 300;
    var u = WEB.WEBROOT+"/common/pop.jsp?url="+url;
	return WEB.popDlg(u,w,h,t,l);
};	
	
WEB.popWin = function(options){
  return WEB.popObj(options);
};
	
WEB.popObj = function(options){//格式为:{url:url,data:[],param:{},where:{}}
	if(!(options instanceof Object)){
		alert("options参数错误,必须为json格式!");
		return;
	}
	
	if(!options.url){ 
	  alert("url参数错误(不能为空)");
	  return;
	}	 
	if(!options.data){
	  alert("data参数错误(不能为空):"+options.data);
	  return;
	}	   
	var p = WEB.popParam(options);  
	//prompt("",p);
	//return window.showModelessDialog(options.url, options.data, p);
	var u = options.url;
	
	//alert(options.where)
	if(options.where!=undefined){//追加where参数
		var flag = (u.indexOf("?")>=0)?"&":"?";
		u += WEB.json2Param(options.where,flag);
	}
	
	if(u.indexOf("/common/pop.jsp")<0){
		u = WEB.WEBROOT+"/common/pop.jsp?url="+u;
	}
	
	return window.showModalDialog(u, options.data, p);
};
	
	
WEB.getElementWidth = function(o) {
	//x = document.getElementById(objectId);
	return x.offsetWidth;
},
	
WEB.getAbsoluteLeft = function(o) {
	// Get an object left position from the upper left viewport corner
	//o = document.getElementById(objectId)
	oLeft = o.offsetLeft            // Get left position from the parent object
	while(o.offsetParent!=null) {   // Parse the parent hierarchy up to the document element
		oParent = o.offsetParent    // Get parent object reference
		oLeft += oParent.offsetLeft // Add parent left position
		o = oParent
	}
	return oLeft
};
	
WEB.getAbsoluteTop = function(o) {
		// Get an object top position from the upper left viewport corner
		//o = document.getElementById(objectId)
		oTop = o.offsetTop            // Get top position from the parent object
		while(o.offsetParent!=null) { // Parse the parent hierarchy up to the document element
			oParent = o.offsetParent  // Get parent object reference
			oTop += oParent.offsetTop // Add parent top position
			o = oParent
		}
		return oTop
	};	
	
	//cookie
WEB.getCookie = function(l){
  var i="",I=l+"=";
  if(document.cookie.length>0){
     offset=document.cookie.indexOf(I);
     if(offset!=-1){
        offset+=I.length;
        end=document.cookie.indexOf(";",offset);
        if(end==-1)end=document.cookie.length;
        i=unescape(document.cookie.substring(offset,end))
      }
   };return i
};

WEB.setCookie = function(O,o,l,I){
	   var i="",c="";
	   if(l!=null){
	     i=new Date((new Date).getTime()+l*3600000);
	     i="; expires="+i.toGMTString();
	   }
	   if(I!=null){
	     c=";domain="+I;
	   }
	   document.cookie=O+"="+escape(o)+i+c
	 };
	 
WEB.request = function(strName){
 	var strHref = window.document.location.href; 
 	var intPos = strHref.indexOf("?"); 
 	var strRight = strHref.substr(intPos + 1); 
 	var arrTmp = strRight.split("&");
 	for(var i = 0; i < arrTmp.length; i++) { 
 		var arrTemp = arrTmp[i].split("="); 
 		if(arrTemp[0].toUpperCase() == strName.toUpperCase()) 
 		return arrTemp[1]; 
 	}
 	return "";
};

WEB.addLog = function(s){
	WEB.$d("log").innerHTML +=(s+"<br/>");
	var h = WEB.$d("log").offsetHeight;
	WEB.$d("wlog").scrollTop = h;
};

WEB.clearLog = function(){
	WEB.$d("log").innerHTML ="";
};

//date
WEB.today = function(){
  var d = new Date();
  var y = d.getYear();
  var m = d.getMonth() + 1;
      m = (m>=10)?m:"0"+m;
  var dy = d.getDate();
      dy= (dy>=10)?dy:"0"+dy;
  var s = y+"-"+m+"-"+dy;
  return s;	
};

WEB.now = function(){
  var d = new Date();	
  var t = WEB.today()
  var h = d.getHours();
  h = (h>=10)?h:"0"+h;
  var m= d.getMinutes();
  m = (m>=10)?m:"0"+m;
  var s = d.getSeconds();
  s = (s>=10)?s:"0"+s;
  return t+" "+h+":"+m+":"+s;	
};


	
//file
WEB.fileName=function(dir){
  return dir.substring(dir.lastIndexOf("\\")+1,dir.length);	
};
	
WEB.fileExt=function(dir){
  return dir.substring(dir.lastIndexOf(".")+1,dir.length);
};

WEB.fileShortName=function(dir){
  return dir.substring(dir.lastIndexOf("\\")+1,dir.lastIndexOf("."));
};
	
/*设置当前位置*/
WEB.setPath = function(url){		
    if(top && top.frm_top){
       top.frm_top.showPath();
	   top.frm_top.setPath(url);
	   return;
	}else if(parent && parent.GLOBALPATH){
	   parent.GLOBALPATH.showPath();
	   parent.GLOBALPATH.setPath(url);
	   return;
	}else if(parent.parent && parent.parent.GLOBALPATH){
	   parent.parent.GLOBALPATH.showPath();
	   parent.parent.GLOBALPATH.setPath(url);
	   return;
	}else{
	   return;		
	}
};
	
WEB.hidePath=function(){
    if(top && top.frm_top){
        top.frm_top.hidePath();
        return;
	}else if(parent && parent.GLOBALPATH){
	   parent.GLOBALPATH.hidePath();
	   return;
	}else if(parent.parent && parent.parent.GLOBALPATH){
	   parent.parent.GLOBALPATH.hidePath();
	   return;
	}else{
	   return;		
	}
};

WEB.showPath=function(){
   if(top && top.frm_top){
        top.frm_top.showPath();
        return;
	}else if(parent && parent.GLOBALPATH){
	   parent.GLOBALPATH.showPath();
	   return;
	}else if(parent.parent && parent.parent.GLOBALPATH){
	   parent.parent.GLOBALPATH.showPath();
	   return;
	}else{
	   return;		
	}
}; 	



/**================
     页签全局API
   ================**/
WEB.setTopHref = function(url){
	if(url){
		if(window.TAB){
			window.href = url;
		}else if(window.parent && window.parent.TAB){
			window.parent.href = url;
		}else if(window.parent.parent && window.parent.parent.TAB){
			 window.parent.parent.href = url;
		}else if(window.parent.parent.parent && window.parent.parent.parent.TAB){
			 window.parent.parent.parent.href = url;
		}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.TAB){
			 window.parent.parent.parent.parent.href = url;
		}else{
			return ;
		}
	}
}
 
function getTabObj(){
	if(window.TAB){
		return window.TAB;
	}else if(window.parent && window.parent.TAB){
		return window.parent.TAB;
	}else if(window.parent.parent && window.parent.parent.TAB){
		return window.parent.parent.TAB;
	}else if(window.parent.parent.parent && window.parent.parent.parent.TAB){
		return window.parent.parent.parent.TAB;
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.TAB){
		return window.parent.parent.parent.parent.TAB;
	}else{
		return ;
	}
}
/*新增页签
 *option.title:页签名称,必须
 *option.url:路径(包含${ctx}的全路径),必须
 *refresh:如果页面已存在,是否刷新页面{true:刷新,false:不刷新},可选,默认不刷新
*/
WEB.addTab = function(option){
	
	if(!option.menuId){
		if(option.url){
			var url = option.url;
			if(url.indexOf('currentMenuId')<0){
				var currentMenuId = getCurrentMenuId();
				if(currentMenuId){
					option.menuId = currentMenuId;
				}
			}
		}
	}
	
	var tabObj = getTabObj();
	if(tabObj){
		tabObj.add(option);
		
	}else{
		if(option && option.url){
			//alert(WEB.WEBROOT+option.url);
			window.open(WEB.WEBROOT+"/"+option.url,"_blank");
			//window.open(WEB.WEBROOT+"/"+option.url,"_blank","width=800,height=500;");
		}else{
			alert("url参数不能为空");
		}
	}
};

/*
 * 显示主页
 * refresh:是否刷新页面{true:刷新,false:不刷新},可选,默认不刷新 
 */
WEB.homeTab = function(refresh){

	var tabObj = getTabObj();
	if(tabObj){
		tabObj.home(refresh);
	}else{
		return;
	}
	
};

/**
* 根据title选择页签
* refresh:是否刷新页面{true:刷新,false:不刷新},可选,默认不刷新
*/
WEB.selectTab = function(title,refresh){

	var tabObj = getTabObj();
	if(tabObj){
		tabObj.select(title,refresh);
	}else{
		return;
	}
	
};

/**
* 根据title删除页签
*/
WEB.removeTab = function(title){	

	var tabObj = getTabObj();
	if(tabObj){
		tabObj.close(title);
	}else{
		return;
	}

};

/**
* 根据title刷新页签
* refresh:是否刷新页面{true:刷新,false:不刷新},可选,默认不刷新
*/
WEB.refreshTab = function(title,refresh){

	var tabObj = getTabObj();
	if(tabObj){
		tabObj.refresh(title,refresh);
	}else{
		return;
	}
}

/**
* 取当前标签iframe对象
*/
WEB.getTabSelectFrame = function(){

	var tabObj = getTabObj();
	if(tabObj){
		tabObj.getSelectFrame();
	}else{
		return;
	}
}
/**
* 触发tab中的方法
*/
WEB.buttonClick = function(title,buttonId){	

	var tabObj = getTabObj();
	if(tabObj){
		tabObj.buttonClick(title,buttonId);
	}else{
		return;
	}
}

/**=========
  全局窗口操作
=============**/
function getWINObj(){
	if(window.WIN){
		return window.WIN;
	}else if(window.parent && window.parent.WIN){
		return window.parent.WIN;
	}else if(window.parent.parent && window.parent.parent.WIN){
		return window.parent.parent.WIN;
	}else if(window.parent.parent.parent && window.parent.parent.parent.WIN){
		return window.parent.parent.parent.WIN;
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.WIN){
		return window.parent.parent.parent.parent.WIN;
	}else if(window.parent.parent.parent.parent.parent && window.parent.parent.parent.parent.parent.WIN) {
        return window.parent.parent.parent.parent.parent.WIN;
    }else{
		return ;
	}
}

/**
    打开全局窗口
**/
WEB.openWin = function(option){	
	if(option.url){
		var url = option.url;
		if(url.indexOf('currentMenuId')<0){
			var currentMenuId = getCurrentMenuId();
			if(currentMenuId){
				if(url.indexOf('?')>-1){
					url += '&';
				}else{
					url += '?';
				}
				url += "currentMenuId="+currentMenuId;
				option.url = url;
			}
		}
	}
	
	var winObj = getWINObj();
	if(winObj){
		winObj.open(option);
	}else{
		if(option && option.url){
			//alert(WEB.WEBROOT+option.url);
			window.open(WEB.WEBROOT+"/"+option.url,"_blank","width=800,height=500,resizable=y");
		}else{
			alert("url参数不能为空");
		}
	}
};

//关闭全局窗口
WEB.closeWin = function(id,param){

	var winObj = getWINObj();
	if(winObj){
		winObj.close(id,param);
	}else{
		return;	
	}
};


WEB.openMsn = function(option){
	if(window.MSN){
		window.MSN.open(option);
		return;
	}else if(window.parent && window.parent.MSN){
		window.parent.MSN.open(option);
	}else if(window.parent.parent && window.parent.parent.MSN){
		window.parent.parent.MSN.open(option);
	}else if(window.parent.parent.parent && window.parent.parent.parent.MSN){
		window.parent.parent.parent.MSN.open(option);
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.MSN){
		window.parent.parent.parent.parent.MSN.open(option);
	}else{
		//alert(option.msn);
	}
};

WEB.openMsnthis = function(option){
	if(typeof option =="string"){
        option = {
            "msg":option
        }
    }
	var title = option.title||"提示";
	var msg = option.msg||"操作成功!";
	var iconCls = option.iconCls||null;
	var fn = option.fn||null;
	$.messager.alert(title,msg,iconCls,fn);
}

//关闭全局消息窗口
WEB.closeMsn = function(){
	if(window.MSN){
		window.MSN.close();
		return;
	}else if(window.parent && window.parent.MSN){
		window.parent.MSN.close();
	}else if(window.parent.parent && window.parent.parent.MSN){
		window.parent.parent.MSN.close();
	}else if(window.parent.parent.parent && window.parent.parent.parent.MSN){
		window.parent.parent.parent.MSN.close();
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.MSNMSN){
		window.parent.parent.parent.parent.MSN.close();
	}else{
		return;	
	}
};


/*
//showmodel方式添加附件
WEB.getAttach = function(){
	var url = WEB.WEBROOT+"/tools/upload/upload.jsp?ref=1";
	var u = WEB.WEBROOT+"/common/pop.jsp?url="+url;	
	var options = {url:u,data:[],param:{w:620,h:390,r:"no"}};
	return WEB.popObj(options);
};

//设置附件列表
WEB.addAttach = function(attachId){
	var divId = "_div_"+attachId;
	var ulId =  "_ul_"+attachId;
	var docId = attachId+"_docIds";
	var list = WEB.getAttach();
	var li = "";
	var docIds=$("#"+docId).val();		
	var docArr=docIds.split(",");
	if(list!=undefined){
		li = ""; //<ul>			
		for(var i=0;i<list.length;i++){
			var did = list[i].docId;
			var isExist = WEB.attachIsExist(did,docArr);				
			if(!isExist){
				li += "<li id='doc_"+docId+did+"'>";
				li += list[i].docTitle;
				li += "<img src='"+WEB.WEBROOT+"/images/icon/del.gif' title='删除附件' onclick=\"WEB.delAttach('"+attachId+"','"+did+"')\">;";
				li += "</li>";
				docIds +=","+did;
			}
		}
		li +=""; //</ul>
		if(docIds.substring(0,1)==","){
			docIds = docIds.substring(1,docIds.length);
		}
		$("#"+ulId).append(li);
		$("#"+docId).val(docIds);
		//alert($("#"+docId).val());
	}	
};

//附件是否已关联,存在:true
WEB.attachIsExist = function(did,docArr){		
	//var flag = false;
	for(var j=0;j<docArr.length;j++){			
		if(docArr[j]==did){
			return true; 
		}
	}
	return false;
};

	
//删除附件
WEB.delAttach = function (attachId,did){
	if(!confirm("确定删除吗?")){
		return;
	}
	var divId = "_div_"+attachId;
	//var docId = "_"+attachId;
	var docId = attachId+"_docIds";
	//删除li列表
	$("#doc_"+docId+did).remove();
	
	//更新docId
	var docIds = $("#"+docId).val();
	var arr = docIds.split(",");
	var newArr = $.grep( arr, function(n,i){
	    return (n != did);
	});		
	$("#"+docId).val(newArr.toString());		
	//alert(arrH.toString()+"\n"+arr.toString()); 		
	return ;
};

//查看附件
WEB.viewAttach = function(did,target){	    
    if(document.getElementById("attachFrm")==null){	
        var ifm = document.createElement("<iframe name='attachFrm' id='attachFrm' width='0' height='0' src='"+WEB.WEBROOT+"/common/blank.jsp'>");
	    		    
	    //ifm.setAttribute("name",target);		    
	    //ifm.name = target; 	//不支持name设置	    
	    //ifm.id=target;
	    //ifm.width=10;
	    //ifm.height=10;		    
	    ////ifm.style.display="none";
	    
	    document.body.appendChild(ifm);			    
	}else{		
		var obj = document.frames["attachFrm"].document.body;
		if(obj!=null){
		   obj.innerText = "";
		}			
	}

	var win = window.open(WEB.WEBROOT+"/download.action?docId="+did,"attachFrm");
    return;	
};

//追加附件 opt = {refId:"357",refMoudle:"bus_chg_info",docType:"attach",newDocType:"attach2"}
WEB.appendAttach = function(opt){
	var refId = opt.refId||"";
	var refModule= opt.refModule||"";
	var docType = opt.docType||"";
	var newDocType = opt.newDocType||"attach";
	if(refId=="" || refModule==""){
		//alert("refId,refMoudle参数不能为空");
		return;
	}
	var p = {"sqlName":"common.attachQuery"
			,"page.autoCount":"true"
			,"page.pageNo":"1"
			,"page.pageSize":"100"
			,"params.refId":refId
			,"params.refModule":refModule
			,"params.docType":docType			
			}
	var url = WEB.WEBROOT+"/common/ibatisQuery.action";
	$.getJSON(url, p,
		function(data){
		   var str='';
		   var li="";
		   var docIds="";
		   var result = data.result;
		   for(var i=0;i<result.length;i++){
			  li +=("<li id='doc_"+newDocType+"_docIds"+result[i].DOC_ID+"'>");
			  li +=("<a href=\"javascript:WEB.viewAttach('"+result[i].DOC_ID+"','attachFrm')\">");
			  li +=(result[i].DOC_TITLE+"."+result[i].DOC_KIND);
			  li +=("</a>");
			  li +=("<img src='images/icon/del.gif' title='删除附件' onclick=\"WEB.delAttach('"+newDocType+"','"+result[i].DOC_ID+"')\">");
			  li +=("</li>");
			  docIds += ","+result[i].DOC_ID;  
		      //$("#attach").html();
		   }
		   if(docIds!=""){docIds = docIds.substring(1);}
		   $("#_ul_"+newDocType).html(li);
		   $("#"+newDocType+"_docIds").val(docIds);
		   //alert($("#"+newDocType+"_docIds").val());
		   //alert(str);
	})

}
*/	

//清空引用
WEB.resetRef = function(id){
	$("#"+id).val("");
	$("#"+id+"_text").val("");	
}

/*
//提示信息
WEB.message = function(data){
		var url = WEB.WEBROOT+"/common/message.jsp";
		return window.showModalDialog(url, data, "dialogWidth:320px;dialogHeight:220px;");
};
*/

/*
 *提示信息
 *显示提示信息,n秒后隐藏
 */
WEB.alt = function(id,str,n){
	n = n||5000;
	$('#'+id).fadeIn(500,function(){
		$(this).html(str);
		$(this).fadeOut(n,function(){$(this).html("");});
	}); 
}

/**
 * 把json解析为url参数
 * o:jsonObject
 * pre:之前参数
 */
WEB.json2Param=function(o, pre){
	var value, buf = [], key, e = encodeURIComponent; 
	for(key in o){ 
		value = (o[key]==undefined)?"":e(o[key]);
		buf.push("&", e(key), "=",value);
	} 
	if(!pre){ 
		buf.shift(); 
		pre = ""; 
	} 
	return pre + buf.join(''); 
}; 

/**
 * 把url参数解析为json
 * string: url
 * overwrite:
 */
WEB.param2Json=function(string, overwrite){
	var obj = {}, pairs = string.split('&'),d = decodeURIComponent,name,value; 
	$.each(pairs, function(i,pair) { 
		pair = pair.split('='); 
		name = d(pair[0]); 
		value = d(pair[1]); 
		obj[name] = overwrite || !obj[name] ? value : [].concat(obj[name]).concat(value); 
	}); 
	return obj; 
}; 


/*
 * 报表导出函数, 注意列表中如果有操作列，操作列的名称必须是act。参数说明如下：
 * url：指请求Action，后面不带任何参数，有需要跟参数的，请将这些参数设置在condition中
 * thNames:  列表的表头数组, jqgrid自身的列表表头(colNames属性)即可
 * colModel: 列表的属性列数组(必须与thNames数组的长度一致),jqgrid自身的列表属性列数组(colModel)即可
 * condition: 这是一个JSON对象, 数据查询的过滤条件以及一些其他的参数设置
 * isExpHidCols: boolean类型,指"是否导出隐藏列"标志, 默认不导出隐藏列,true表示导出隐藏列,false表示不导出隐藏列
 */
WEB.exportXls = function(url, colNames, colModel, condition, isExpHidCols) {
	var thNames = new Array();
	thNames = thNames.concat(colNames);
	var colNames = new Array();
	var j = 0;
	if(isExpHidCols == true) {		
		for(var i=0; i < colModel.length; i++){
			if(colModel[i].name=='act' || colModel[i].name == 'rn' ||  colModel[i].name == 'cb'){ 				
				thNames = thNames.remove(j);
				continue;
			}			
			j++;
			colNames.push(colModel[i].name);
		}
	} else {	    
	    for(var i=0; i < colModel.length; i++){
			if(colModel[i].hidden || colModel[i].name=='act' || colModel[i].name == 'rn'||  colModel[i].name == 'cb'){				
				thNames = thNames.remove(j);
				continue;
			}			
			j++;
			colNames.push(colModel[i].name);
		}
	}
	j=0;
	var params = "thNames="+encodeURIComponent(thNames.toString())+"&colNames="+encodeURIComponent(colNames.toString());
	/*
	for(var p in condition) {
		params += "&"+p+"="+condition[p];
	}
	*/	
	params=WEB.json2Param(condition,params);
	var ifm = document.getElementById("exportXlsFrm");
    if(ifm == null){	
        ifm = document.createElement("iframe");
		ifm.id = "exportXlsFrm";
		ifm.style.position = "absolute";
		ifm.style.zIndex = "1";
		ifm.style.visibility = "hidden";
		ifm.style.height="0px";
		ifm.style.width="0px";
		ifm.style.top="0px";
		ifm.style.left="0px";
		document.body.appendChild(ifm);
    }    
    ifm.src = url + "?" + params;	
}

/*
 * 报表导出函数。自定义导出报表表头名称和报表导出的属性列。参数说明如下：
 * url：指请求Action，后面不带任何参数，有需要跟参数的，请将这些参数设置在condition中
 * thNames:  导出报表的表头数组
 * colModel: 导出报表的属性列数组(必须与thNames数组的长度一致)
 * condition: JSON对象, 数据查询的过滤条件以及一些其他的参数设置
 */
WEB.cusExportXls = WEB.myExportXls = function(url, thNames, colNames, condition){ 
	var params = "thNames="+encodeURIComponent(thNames.toString())+"&colNames="+encodeURIComponent(colNames.toString());
	/*
	for(var p in condition) {
		params += "&"+p+"="+condition[p];
	}
	*/
	params=WEB.json2Param(condition,params);	
    var ifm = document.getElementById("exportXlsFrm");
    if(ifm == null){	
        ifm = document.createElement("iframe");
		ifm.id = "exportXlsFrm";
		ifm.style.position = "absolute";
		ifm.style.zIndex = "1";
		ifm.style.visibility = "hidden";
		ifm.style.height="0px";
		ifm.style.width="0px";
		ifm.style.top="0px";
		ifm.style.left="0px";
		document.body.appendChild(ifm);
    }
    ifm.src = url + "?" + params;
}





/**删除数组对象中某个元素,jquery有现成的*/
Array.prototype.remove = function(n) {  
	//n表示第几项，从0开始算起。
	//prototype为对象原型，注意这里为对象增加自定义方法的方法。
	var len = this.length;
	if(n < 0 || n > len){  //如果n<0或者n>数组长度时，则不进行任何操作。
		return this;
	} else {
		if( n == 0){ this.shift(); return this;} //删除第一个元素
		if( n == len - 1){ this.pop(); return this;} //删除最后一个元素
		return this.slice(0,n).concat(this.slice(n+1,this.length));
   	}
}


//.caption自动添加开关功能
$(document).ready(function(){
	//.caption添加开关
	$(".caption").prepend('<span class="icon-switch icon-plus"></span>').end()
	$(".caption .icon-switch").click(function() {	
		$(this).toggleClass("icon-minu");
		var oid = $(this).attr("id");
		if(oid==""){
			$(this).parents(".caption:first").next().toggle();
			//$(this).parents(".caption:first").next().slideToggle(300);
		}else{
			$("[id^='"+oid+"_']").toggle();
			//$("[id^='"+oid+"_']").slideToggle(300);
		}		
	});								
});

//legend.fswitch自动添加开关功能
$(document).ready(function(){
	//$("legend.fswitch").css({"cursor":"pointer"});
	$("legend.fswitch").prepend('<span class="icon-switch icon-plus">&nbsp;&nbsp;&nbsp;&nbsp;</span>').end();
	$("legend.fswitch").click(function() {	
		$(this).next().toggle();
		$(this).children("span.icon-switch").toggleClass("icon-minu");
		$(this).parent("fieldset").toggleClass("box_border");
	});	
});

/*
$(document).ready(function(){
	//$("legend.fswitch").css({"cursor":"pointer"});
	$("legend.unfswitch").prepend('<span class="icon-switch icon-plus icon-minu">&nbsp;&nbsp;&nbsp;&nbsp;</span>').end();
	$("legend.unfswitch").parent("fieldset").addClass("box_border");
	$("legend.unfswitch").next().toggle();
	$("legend.unfswitch").click(function() {
		if($(this).attr("onclick"))
		$(this).next().toggle();
		$(this).children("span.icon-switch").toggleClass("icon-minu");
		$(this).parent("fieldset").toggleClass("box_border");
	});	
});
*/

//.enlarge 单击放大
$(document).ready(function(){
	$("input.enlarge").click(function() {
		var srcValue = $(this).val();
		var url= WEB.WEBROOT+"/common/enlarge.jsp";
		var data = [{"src":srcValue,ref:"result",obj:$(this)}];
		var param = {w:300,h:200};
		var result = WEB.popObj({url:url,data:data,param:param});	
		if(result){
		   $(this).val(result);
		}
	});	
});

//只允许输入数字和小数点
function clearNoNum(obj){   
 obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  

 obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 

 obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   

 obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}



WEB.alert = function(option,openType){
	if(openType!=null){
		WEB.openMsnthis(option);
	}else{
		WEB.openMsn(option);
	}
	
}

$(document).ready(function(){
	if($('input[type="text"].read')[0]){
		//$('input[type="text"].read').css({"border":"none"});
		//$('input[type="text"].read').css({"background":"none"});
		//$('input[type="text"].read').focus(function(){this.blur()});
		$('input[type="text"].read').attr("readonly","readonly");
		$('input[type="text"].read').removeAttr("onclick");
	}
	if($('textarea.read')[0]){
		//$('textarea.read').css({"border":"none"});
		//$('textarea.read').css({"background":"none"});
		//$('textarea.read').focus(function(){this.blur()});
		$('textarea.read').attr("readonly","readonly");
	}
	if($('button[permission="false"]')[0]){
		//$('button[permission="false"]').css({"display":"none"});
		$('button[permission="false"]').remove();
	}
	if($('input[permission="false"]')[0]){
		//$('input[permission="false"]').css({"display":"none"});
		$('input[permission="false"]').remove();
	}
	if($('a[permission="false"]')[0]){
		//$('a[permission="false"]').css({"display":"none"});
		$('a[permission="false"]').remove();
	}
	/*
	if($('input[permission=""]')[0]){
		$('input[permission=""]').remove();
	}*/
});

WEB.gridLoading = function(option){
	if(window.WIN){
		return window.$.gridLoading(option);
	}else if(window.parent && window.parent.WIN){
		return window.parent.$.gridLoading(option);
	}else if(window.parent.parent && window.parent.parent.WIN){
		return window.parent.parent.$.gridLoading(option);
	}else if(window.parent.parent.parent && window.parent.parent.parent.WIN){
		return window.parent.parent.parent.$.gridLoading(option);
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.WIN){
		return window.parent.parent.parent.parent.$.gridLoading(option);
	}else{
		return ;
	}

}

WEB.gridUnLoading = function(){
	if(window.WIN){
		return window.$.gridUnLoading();
	}else if(window.parent && window.parent.WIN){
		return window.parent.$.gridUnLoading();
	}else if(window.parent.parent && window.parent.parent.WIN){
		return window.parent.parent.$.gridUnLoading();
	}else if(window.parent.parent.parent && window.parent.parent.parent.WIN){
		return window.parent.parent.parent.$.gridUnLoading();
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.WIN){
		return window.parent.parent.parent.parent.$.gridUnLoading();
	}else{
		return ;
	}
}

/*确认提示*/
WEB.confirm = function(option){
	var title = option.title||"提示";
	var msg = option.msg||"确定操作？";
	var fn = option.fn||null;
	
	if(window.WIN){
		return window.$.messager.confirm(title,msg,fn);
	}else if(window.parent && window.parent.WIN){
		return window.parent.$.messager.confirm(title,msg,fn);
	}else if(window.parent.parent && window.parent.parent.WIN){
		return window.parent.parent.$.messager.confirm(title,msg,fn);
	}else if(window.parent.parent.parent && window.parent.parent.parent.WIN){
		return window.parent.parent.parent.$.messager.confirm(title,msg,fn);
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.WIN){
		return window.parent.parent.parent.parent.$.messager.confirm(title,msg,fn);
	}else{
		return ;
	}
}

/*取tree选中节点Id字符串，多个用,分隔，*/
function getTreeCheckedRowIds(treeId,index,unNull,msg){
	if((index || index>=0) &&( typeof index != 'object'))  return index;
	var row = $("#"+treeId).tree('getChecked');
	var ids = "";
	if(row && !row.length){
		  if(row.id)
		  	ids += row.id;
	}else{
		  for(var i=0;i<row.length;i++){
			  ids += row[i].id+",";
		  }
		  ids = ids.substring(0,ids.length-1);
	}
	if(ids && ids.length>0){
		  return ids;
	}else{
		if(unNull){
			if(!msg){msg = '请先选择记录 ！';}
		  	WEB.alert({msg:msg,iconCls:'warning'});
		}
		return null;
	}
}
/*取treeGrid选中节点Id字符串，多个用,分隔，*/
function getTreeGridCheckedRowIds(tableId,index,unNull,msg){
	  if((index || index>=0) &&( typeof index != 'object'))  return index;
	  var row = $("#"+tableId).treegrid('getChecked');
	  var ids = "";
	  if(row && !row.length){
		  if(row.id)
		  	ids += row.id;
	  }else{
		  for(var i=0;i<row.length;i++){
			  ids += row[i].id+",";
		  }
		  ids = ids.substring(0,ids.length-1);
	  }
	  if(ids && ids.length>0){
		  return ids;
	  }else{
	  	  if(unNull){
			  if(!msg){msg = '请先选择记录 ！';}
			  //$.messager.alert('提示',msg,'info');
			  WEB.alert({msg:msg,iconCls:'warning'});
		  }
		  return null;
	  }
}

/*隐藏当前菜单ID*/
$(document).ready(function(){
 try{  
      if($('form')[0]){
      	//页面已手动添加
      	if($('form').find('input[id="currentMenuId"]')[0])return ;
	    var currentMenuId = getCurrentMenuId();
	    if(currentMenuId){
	    	var menuIdInput = '<input type="hidden" name="currentMenuId" id="currentMenuId" value="'+currentMenuId+'" />';
	  		//$('#'+$('form').attr("id")).appendChild(menuIdInput);
	  		//document.getElementById($('form').attr("id")).appendChild(menuIdInput);
	  		 $('form').append(menuIdInput);
	  		 //重置按钮事件，append添加的元素会被清空，需要重置menuId
	         if(!$('form').find("input, button").filter(":reset")[0])return ;
	         $('form').find("input, button").filter(":reset").blur(function() {
	         	  $('#currentMenuId').val(currentMenuId);
             });
	    }	    
	  }
  }catch(e){}
});


/*重置表单,带响应方法*/
function resetForm(formId,callback){
	//document.getElementById(formId).reset(); 
	$('#'+formId)[0].reset();
	if(callback)callback();
	//重置menuId
	if(!$('#'+formId).find('input[id="currentMenuId"]')[0])return ;
	$('#'+formId).find('input[id="currentMenuId"]').val(getCurrentMenuId());
}

/*为URL添加必要参数*/
function formatUrlWithParams(url){
	if(!url) return url;
	if(url.indexOf('currentMenuId')<0){
			var currentMenuId = getCurrentMenuId();
			if(currentMenuId){//传入当前菜单ID
				if(url.indexOf('?')>-1){
					url += '&';
				}else{
					url += '?';
				}
				url += "currentMenuId="+currentMenuId;
			}
	}
	return url;
}
/*打开审批业务公用窗口
WEB.openApplyBusView = function(option){
	var applyPara = option.url;
	var title = option.title;
	
	var url = GLOBAL.WEBROOT + applyPara;
	WEB.openWin({
		id:APPLY_BUS_WIN.ID,
		title : title,
		url : url,
		width : '1000',
		height : '640',
		top:5,
		onClose : function(params) {
			
		}
	});
}
*/
/*关闭审批业务公用窗口
WEB.closeApplyBusView =  function(){
	WEB.closeWin(APPLY_BUS_WIN.ID);
}
*/

/*消息设置为已读，callback为响应方法
WEB.readNotices =  function(noticeUuid,callback){
	var url = GLOBAL.WEBROOT + "/sys/sysApply!setNoticeRead.action?";
	if(noticeUuid)url += 'searchModel.noticeUuidStr='+noticeUuid;
	$.appAjax({
		url : url,
	    dataType : 'json',
	    method : "post",
	   	success : function(data) {	
	   		if(data){
	   			var result = data.result;
				if(result&&callback){
					//ajaxList('notice');//刷新通知
					callback();
				}
	   		}
	   	}//,
   		//complete:function(){
   		//  	$.gridUnLoading();
   		//}
	});
}
*/

 
String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str) return true;
	else return false;
	//return true;
}
String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str) return true;
	else return false;
	//return true;
} 

//获取iframe元素
function getIFrameDOM(id){
	return window.document.getElementById(id).contentDocument || window.frames[id].document;
};

function getCurrentMenuId(){
	/*var currentMenuId = '${currentMenuId}';*/
	var currentMenuId = GLOBAL.MENU_ID;
	if(currentMenuId&&currentMenuId!=''){
		return currentMenuId;
	}
	return null;
}


/*tree的<unSelect>方法扩展,使用方法:$("#treeId").tree("unSelect",node.target);*/
(function ($) {
	$.extend($.fn.tree.methods,{
		unSelect:function(jq,target){
			return jq.each(function(){
				$(target).removeClass("tree-node-selected");
			});
		}
	});
})(jQuery);


var AppUtils = {
    "equals": function(obj1,obj2){
        if((typeof obj1=="undefined")||(typeof obj2=="undefined")) {
            return (typeof obj1)==(typeof obj2);
        }
        return obj1 == obj2;
    },
    "isNull": function(str){
        if(typeof str=="undefined"){
            return true;
        }
        return (str.replace(/\s/,'').length ==0);
    },
    "isNotNull": function(str){
        return !this.isNull(str);
    },
    "getDefautl": function(obj, defaultVal){
        if(!!obj){
            return obj;
        } else {
            return defaultVal;
        }
    }

}

/**
  * @description 从某个iframe开始递归下面的所有iframe来寻找指定元素对象
  * @param doc 指定的iframe的dom对象
  * @param id 要查找的元素的id
  * @author NoteShare
  */
function cycleIframe(doc,id){
    try{
        var resultObj = $("#" + id,doc)[0];
        //如果找到则直接返回，如果没有找到则遍历底层iframe进行寻找
        if(resultObj){
            return resultObj;
        }else{
            var sunIframes = doc.getElementsByTagName("iframe");
            if(sunIframes.length > 0){
                for(var i = 0;i < sunIframes.length ;i ++){
                    var result_tem =  cycleIframe(sunIframes[i].contentWindow.document,id);
                    if(result_tem){
                        return result_tem;
                    }
                }
            }else{
                return null;
            }
        }
    }catch (e) {
    }
}


/**
 * 依赖jQuery倒计时类
 * @param $id jQuery id
 * @param label 显示label
 * @param defaultCount 默认倒计时启动值
 * @param startFun 启动函数
 * @param stopFun 结束函数
 * @constructor
 */
function Clock($id, label, defaultCount, startFun, stopFun) {

    defaultCount = defaultCount || 60;

    this.id = $id;
    this.$obj = $("#" + $id);
    this.defaultLabel = this.$obj.val();
    this.label = label;
    this.defaultCount = defaultCount;
    this.count = defaultCount;
    this.startFun = startFun || function () {
    };
    this.stopFun = stopFun || function () {
    };
    ;
    this.stopFlag = true;

    var that = this;

    this.start = function () {
        if (that.count == 0) {
            that.stopFlag = true;
            that.count = that.defaultCount;
            that.$obj.val(that.defaultLabel);
            that.stopFun();
            return;
        }

        if (that.stopFlag) {
            that.stopFlag = false;
            that.startFun();
        }
        that.count = --that.count;
        var lbl = that.label.replace(/i/, that.count);
        that.$obj.val(lbl);
        setTimeout(that.start, 1000);
    }
    this.isOver = function () {
        return that.stopFlag;
    }

}

/**
 * 取checkbox选中值
 * @param name
 * @returns
 */
function getCheckBoxValues(name){
	var id_array = new Array();
	$('input[name="'+name+'"]:checked').each(function(){
		 id_array.push($(this).val());//向数组中添加元素
	});
	var idstr=id_array.join(',');//将数组元素连接起来以构建一个字符串
	return idstr;
}
/**
 * 取同名input的值
 * @param name
 * @returns
 */
function getInputArrValues(name){
	var id_array = new Array();
	$('input[name="'+name+'"]').each(function(){
		 if($(this).val()!=''){
			 id_array.push($(this).val());//向数组中添加元素
		 }
	});
	var idstr=id_array.join(',');//将数组元素连接起来以构建一个字符串
	return idstr;
}

/**
 * 修改未读消息的数量
 */
WEB.changeNoticeUnReadNum = function(){
	if(window.NOTICE){
		 window.NOTICE.changeNoticeUnReadNum();
	}else if(window.parent && window.parent.NOTICE){
		 window.parent.NOTICE.changeNoticeUnReadNum();
	}else if(window.parent.parent && window.parent.parent.NOTICE){
		 window.parent.parent.NOTICE.changeNoticeUnReadNum();
	}else if(window.parent.parent.parent && window.parent.parent.parent.NOTICE){
		 window.parent.parent.parent.NOTICE.changeNoticeUnReadNum();
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.NOTICE){
		 window.parent.parent.parent.parent.NOTICE.changeNoticeUnReadNum();
	}else{
		return ;
	}
	
}
/**
 * 公告置为已读/不再提醒
 * @param keys 空，设置所有
 * @param read 0-不再提醒 1-已读
 * @param loading 是否回调列表刷新/遮罩
 */
WEB.readNotice = function(keys,read,loading){
	
	var readNoticeUrl = GLOBAL.WEBROOT +'/sysNoticeController.do?readNotice';
	var url = readNoticeUrl;
	if(keys)url +="&keys="+keys;
	if(!read||read!='1'){
		read = "0";
	}
	url += "&read="+read;
	if(loading){
		WEB.gridLoading({message:"设置中......."});
	}else{
		$(".messager-body").window('close');
	}
	$.appAjax({
	    url : url,
	    dataType : 'json',
	    method : "post",
	   	success : function(data) {					   	    
		   	if(data){
		   		var success = data.success;
		   		if(loading){
					var message = data.message;
					WEB.alert({msg:message});
					if(success){
						 $("#listTable").flexSearch($.formParams("#queryForm"));
					}
		   		}
		   		if(success&&read=='1'){
		   			//修改未读消息的数量
		   			WEB.changeNoticeUnReadNum();
		   		}
		   	} else {
		   	   	//WEB.alert({msg:"删除失败!请联系管理员"});
		   	}
        },
   		complete:function(){
   		  	if(loading)WEB.gridUnLoading();
   		}
	});
}

/**
 * 让textarea支持tab键缩进
 */
function textareaDoTab(){
	$("textarea").bind(
	        'keydown',
	        function(e) {
	            if (e.keyCode == 9) {
	                e.preventDefault();
	                var indent = '    ';
	                var start = this.selectionStart;
	                var end = this.selectionEnd;
	                var selected = window.getSelection().toString();
	                selected = indent + selected.replace(/\n/g, '\n' + indent);
	                this.value = this.value.substring(0, start) + selected
	                        + this.value.substring(end);
	                this.setSelectionRange(start + indent.length, start
	                        + selected.length);
	            }
	});
}

/*让textarea支持tab键缩进*/
$(document).ready(function(){
	textareaDoTab();
});

/*选择导航菜单*/
WEB.selectHeadMenu = function(option){
	if(option.menuId){
		window.top.queryMenuTree(option.menuId,false,option.tabMenuId);
	}
}

/**
 * 文件类型校验
 */
var validate_file_type = function(fileInputId,fileTypes){
	var flag = false;
	var file= $("#"+fileInputId).val();
	var filename=file.replace(/.*(\/|\\)/, ""); 
	if(filename==''){
		WEB.alert({msg:"请上传文件",iconCls:'warning'});
		return false;
	}
	
	if(fileTypes&&fileTypes.length>0){
		var fileExt=(/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : ''; 
		var str = "";
		for(var i=0;i<fileTypes.length;i++){
			var fileType = fileTypes[i];
			str += "/"+fileType;
			if(fileType==fileExt[0].toUpperCase() ||fileType==fileExt[0].toLowerCase()){
				flag = true;
			}
		}
		if(!flag){
			if(str.length<1){
				str = "正确格式";
			}else{
				str = str.substring(1)+"格式";
			}
			WEB.alert({msg:"请上传"+str+"的文件",iconCls:'warning'});
		}
	}else{
		flag = true;
	}
	
	return flag;
}

WEB.modifyWinTitle = function(id,title){

	var winObj = getWINObj();
	if(winObj){
		winObj.modifyTitle(id,title);
	}else{
		return;	
	}
};

///////////////////////批量选中回显元素 beg///////////////////
/**
 * 创建tag
 */
function createSpanTag(option){
	var divId =  option.divId;
	if(!divId){
		return ;
	}
	var divObj = document.getElementById(divId);
	if(divObj==null){
		return ;
	}
	var inputName = option.inputName;
	if(!inputName){
		return ;
	}
	var inputValue = option.inputValue;
	if(!inputValue){
		return ;
	}
	var inputId = option.inputId;
	if(!inputId){
		inputId = inputValue;
	}
	var spanText = option.spanText;
	if(!spanText||spanText.length<1){
		return ;
	}
	var removeAble = (option.removeAble==undefined)? false:option.removeAble;
	var idUnique = option.idUnique||true;
	if(idUnique){//值唯一校验
		var unique = true;
		var inputObjs =  $("input[name='"+inputName+"']");
		if(inputObjs[0]){
			for(var i=0;i<inputObjs.length;i++){
				var va_ = inputObjs[i].value;
				if(inputValue==va_){//值相同
					unique = false;
					break;
				}
			}
		}
		if(!unique){
			return ;
		}
	}
	
	var ul = "<ul class=\"batchTagUl\" id=\"batchTagUl_"+inputName+"_"+inputId+"\">";
	ul += "<input id=\""+inputName+"_"+inputId+"\" name=\""+inputName+"\" type=\"hidden\" value=\""+inputValue+"\"/>";
	ul += "<span>"+spanText+"</span>";
	if(removeAble)ul += "<a href=\"javascript:void()\" onclick=\"removeSpanTag('batchTagUl_"+inputName+"_"+inputId+"')\" class=\"icon-cancel icon-img-btn\"></a>";
	ul += "</ul>";
	$(divObj).append(ul);
	
}
/**
 * 移除tag
 */
function removeSpanTag(batchTagUlId){
	$("#"+batchTagUlId).fadeToggle();
	$("#"+batchTagUlId).empty().remove();
}
/**
 * 移除所有
 */
function removeAllSpanTag(divId){
	if(!divId){
		return ;
	}
	var divObj = document.getElementById(divId);
	if(divObj==null){
		return ;
	}
	var uls = $(divObj).children();
	if(uls[0]){
		
		$(divObj).children().fadeToggle();
		$(divObj).children().empty().remove();
	}
}

///////////////////////批量选中回显元素 end///////////////////

/* 防止backspace键后退网页 */    
document.onkeydown = function(event) {    
    if (event.keyCode == 8) {// backspace的keycode=8    
        var type = document.activeElement.type;// 获取焦点类型    
        if (type == "text" || type == "textarea" || type == "password"    
                || type == "select") {// 判断焦点类型，无法输入的类型一律屏蔽    
            if (document.activeElement.readOnly == false)// 如果不是只读，则执行本次backspace按键    
                return true;    
        }    
        event.keyCode = 0;// 将本次按键设为0（即无按键）    
        event.returnValue = false;    
        return false;    
    }    
};    

WEB.openNoticeView = function(key){
	if(window.NOTICE){
		return window.NOTICE.openNoticeView(key);
	}else if(window.parent && window.parent.NOTICE){
		return window.parent.NOTICE.openNoticeView(key);
	}else if(window.parent.parent && window.parent.parent.NOTICE){
		return window.parent.parent.NOTICE.openNoticeView(key);
	}else if(window.parent.parent.parent && window.parent.parent.parent.NOTICE){
		return window.parent.parent.parent.NOTICE.openNoticeView(key);
	}else if(window.parent.parent.parent.parent && window.parent.parent.parent.parent.NOTICE){
		return window.parent.parent.parent.parent.NOTICE.openNoticeView(key);
	}else{
		return ;
	}
}
