(function() {
	
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
	
	
	var validate_version = "1.7";//jquery.validate.js版本号 1.6/1.7
	var form_version = "2.36";//jquery.form.js版本号  当jquery.validate.js版本为1.6时，搭配2.32;为1.7时，搭配2.36
	
	//file = src.replace(/jquery\.js$/, "jquery-" + version + ".js");
	var file = GLOBAL.WEBROOT+"/plugin/";	

	document.write("<link href=\"" + file + "jquery-validate/jquery.validate.css\" rel=\"stylesheet\" type=\"text/css\"/>");
	document.write("<script src=\"" + file + "jquery-validate/jquery.validate-" + validate_version + ".js\" type=\"text/javascript\"></script>");
	document.write("<script src=\"" + file + "jquery-validate/jquery.form-" + form_version + ".js\" type=\"text/javascript\"></script>");
	document.write("<script src=\"" + file + "jquery-validate/messages_cn.js\" type=\"text/javascript\"></script>");
	
})();

