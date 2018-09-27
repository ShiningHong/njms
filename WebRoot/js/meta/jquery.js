(function() {
/*
var parts = document.location.search.slice( 1 ).split( "&" ),
	length = parts.length,
	scripts = document.getElementsByTagName("script"),
	src = scripts[ scripts.length - 1].src,
	i = 0,
	current,
	version = "1.11.1",
	file = "http://code.jquery.com/jquery-git.js";

for ( ; i < length; i++ ) {
	current = parts[ i ].split( "=" );
	if ( current[ 0 ] === "jquery" ) {
		version = current[ 1 ];
		break;
	}
}

if (version != "git") {
	file = src.replace(/jquery\.js$/, "jquery-" + version + ".js");
}


document.write( "<script src='" + file + "'></script>" );


*/
	
	

	var otherStatics = {
        /**
         * 是否为webkit内核的浏览器
         */
        isWebkit : function() {
          return checkUserAgent(/webkit/);
        },
        /**
         * 是否为火狐浏览器
         */
        isFirefox : function() {
          return checkUserAgent(/firefox/);
        },
        /**
         * 是否为谷歌浏览器
         */
        isChrome : function() {
          return !otherStatics.isOpera() && checkUserAgent(/chrome/);
        },
        /**
         * 是否为Opera浏览器
         */
        isOpera : function() {
          return checkUserAgent(/opr/);
        },
        /**
         * 检测是否为Safari浏览器
         */
        isSafari : function() {
          // google chrome浏览器中也包含了safari
          return !otherStatics.isChrome() && !otherStatics.isOpera() && checkUserAgent(/safari/);
        }
	};
	
	//alert(otherStatics.isChrome())
	//alert(isIE9())
	//alert(isIE11())
	
	//var browser = getBrowserInfo() ;
	//alert(browser); 

	//var verinfo = (browser+"").replace(/[^0-11.]/ig,""); 
	//alert(verinfo);
	
	
	var jquery_version = "1.4.4";//jquery版本号  ie8-11(1.4.2/1.4.4),ie9-11(1.7.2)
	var easyui_version = "1.3.6";//easyui版本号
	
	var file = GLOBAL.WEBROOT+"/plugin/";	
	var jquery_file = file+"jquery/jquery-"+jquery_version+".min.js";
	var easyui_file = file+"easyui/jquery-easyui-"+easyui_version+"/";
	
	document.write( "<script language=\"javascript\" src=\"" + jquery_file + "\"></script>" );
	document.write( "<link type=\"text/css\" rel=\"stylesheet\" href=\""+easyui_file+"themes/icon.css\"/> " );
	document.write( "<link type=\"text/css\" rel=\"stylesheet\" href=\""+easyui_file+"themes/gray/easyui.css\"/> " );
	document.write( "<script language=\"javascript\" src=\"" +easyui_file+"jquery.easyui.min.js\"></script>" );
	document.write( "<script language=\"javascript\" src=\"" + "js/" + "meta/jquery.metadata.js\"></script>");
	
})();


//检测函数
function checkUserAgent(r) {
    return r.test(navigator.userAgent.toLowerCase());
};
 
function isIE() { //ie?  
	 if (!!window.ActiveXObject || "ActiveXObject" in window)  
		 return true;  
	 else  
		 return false;  
}  

function isIE6() {
	// ie6是不支持window.XMLHttpRequest的
	return (isIE()&&!window.XMLHttpRequest);
}

function isIE7() {
	//只有IE8+才支持document.documentMode
	return (isIE()&&window.XMLHttpRequest&&!document.documentMode);
}

function isIE8(){
	// alert(!-[1,])
	// IE678返回NaN 所以!NaN为true 标准浏览器(9+)返回-1 所以!-1为false
	return (isIE()&&!-[1,]&&document.documentMode==8);
}

function isIE9(){
	return (isIE()&&-[1,]==-1&&document.documentMode==9);
}

function isIE11(){
	//IE11浏览器中是不支持原来IE中独有的事件绑定attachEvent
	return (isIE()&&!window.attachEvent);
}


/*第二种方式：浏览器信息*/
function getBrowserInfo(){

	var agent = navigator.userAgent.toLowerCase() ;
	//alert(agent)
	var regStr_ie = /msie [\d.]+;/gi ;
	var regStr_ff = /firefox\/[\d.]+/gi
	var regStr_chrome = /chrome\/[\d.]+/gi ;
	var regStr_saf = /safari\/[\d.]+/gi ;
	var regStr_ie11 = /rv:[\d.]+/gi ;

	//IE6-10
	if(agent.indexOf("msie") > 0){
		return agent.match(regStr_ie) ;
		
	}else if(isIE()){//IE11 没有 msie
		return (agent.match(regStr_ie11)+"").replace("rv:","msie ");
	}
	//firefox
	if(agent.indexOf("firefox") > 0){
		return agent.match(regStr_ff) ;
	}
	
	//Chrome
	if(agent.indexOf("chrome") > 0){
		return agent.match(regStr_chrome) ;
	}
	
	//Safari
	if(agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0){
		return agent.match(regStr_saf) ;
	}


}
