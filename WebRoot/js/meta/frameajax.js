(function($) {
    var _timeout = "600000";// 超时
    var loading = false;

    /**
     * 框架采用的ajax提交方法；
     * 
     * @param opts.url
     *            请求路径
     * @param opts.data
     *            请求参数 数组 [{name:,value:}]
     * @param opts.async
     *            请求是否异步，true为异步、false为同步
     * @param opts.success
     *            成功返回之后的处理函数
     * @param opts.type
     *            是否是GET 还是 POST
     * @param opts.error
     *            失败返回之后的处理函数
     * @param opts.loadingMessage
     * 
     * @param opts.beforeSend
     *            在发送请求之前
     * @param opts.complete
     *            请求之后
     * @author yugn
     */
    $.appAjax = function(opts) {
        if (!opts.async) {
            opts.async = false;
        }
        /*
         * if (!opts.async && loading) { return; }
         */
        if (!opts.data) {
            opts.data = [];
        }
        var type_ ;
        if (opts.method) {
        	type_ = opts.method;
        }else if(opts.type){
        	type_ = opts.type;
        }
        if(!type_){
        	type_ = 'POST';
        }
        /*if (!opts.type) {
            opts.type = 'POST';
        }*/
        
        var dataType_ = opts.dataType;
        if(!dataType_){
        	dataType_ = 'json';
        }
        
        if (!$.isArray(opts.data)) {
            var o = [];
            $.each(opts.data, function(i, n) {
                        o.push({
                                    name : i,
                                    value : n
                                })
                    });
            opts.data = o;
        }

        // 头"//" -> "/"
        if (opts.url && opts.url.substring(0, 2) == "//") {
            opts.url = opts.url.substring(1);
        }
        //
        opts.url = formatUrlWithParams(opts.url);

        /**
         * 走jquery的ajax提交方式
         */
        $.ajax({
                    // url : opts.url + '?_=' + new Date().getTime(),
                    url : opts.url,
                    data : opts.data,
                    async : opts.async,
                    dataType : dataType_,
                    timeout : _timeout,
                    //type : opts.type,
                    type : type_,
                    contentType :opts.contentType,
                    success : function(data, textStatus) {
                        if (!opts.async) {
                            loading = false;
                        }
                        // 成功返回
                        opts.success(data, textStatus);
                    },
                    error : function(errors, textStatus) {
                        if (!opts.async) {
                            loading = false;
                        }
                        if ("timeout" == textStatus) {
                            alert("系统请求超时，请联系管理员！");
                        }
                        if (opts.error) {
                            opts.error();
                        }
                    },
                    beforeSend : function(XMLHttpRequest) {
                        if (opts.beforeSend) {
                            opts.beforeSend(XMLHttpRequest);
                        }
                    },
                    complete : function(XMLHttpRequest, textStatus) {
                        if (opts.complete) {
                            opts.complete(XMLHttpRequest, textStatus);
                        }
                    }

                });

    }

    /**
     * 获取制定表单的所有input元素，拼装成AJAX请求的参数
     * 
     * @param p
     *            页面表单的对象
     * 
     * @return data
     * 
     */
    $.formParams = function(p) {
        if (p == undefined) {
            return;
        }

        var data = new Array;

        $(p).find(":input").each(function(i, n) {
            var flag = "false";
            if ($(n).attr("param") == undefined) {
                flag = "true";
            } else {
                flag = $(n).attr("param");
            }
            if (flag == true || flag == "true") {
                if (($(n).attr('type') == "checkbox" || $(n).attr('type') == "radio") && $(n).attr('checked') == true) {
                    data.push({
                                name : $(n).attr("name"),
                                value : $(n).attr("value")
                            })
                } else if ($(n).attr('type') == "text" || $(n).attr('type') == "password"
                        || $(n).attr('type') == "hidden" || $(n).attr('type') == "textarea") {
                    data.push({
                                name : $(n).attr("name"),
                                value : $(n).attr("value")
                            })
                }
            }
        });
        $(p).find("select").each(function(i, n) {

                    var flag = "false";
                    if ($(n).attr("param") == undefined) {
                        flag = "true";
                    } else {
                        flag = $(n).attr("param");
                    }
                    if (flag == true || flag == "true") {
                        if ($.trim($(n).attr('value')) != '') {
                            data.push({
                                        name : $(n).attr("name"),
                                        value : $(n).attr("value")
                                    })
                        } else {
                            data.push({
                                        name : $(n).attr("name"),
                                        value : ''
                                    })
                        }

                    }

                })
        return data;
    };

    /**
     * 页面显示
     * 
     * @param p.url
     *            需要显示的url; 必须
     * @param p.title
     *            页面标题；
     * @param p.id
     *            页面的Id 必须；
     * @param p.width
     *            页面的宽度
     * @param p.height
     *            页面的高度
     */
    $.showWindow = function(p) {
        if (!p.url && !p.id) {
            $.alert("弹出窗口必须要设置url属性或者id属性！");
            return;
        }
        if (FrameWin) {
            FrameWin.showWindow(p);
        }
    }

    /**
     * 关闭信息
     */
    $.closeWindow = function(obj) {
        if (window.parent.FrameWin) {
            window.parent.FrameWin.closeWindow(obj);
        }
    }
    
    /**
     * 实现遮罩效果,使用用例 $.gridLoading({ message:"需要提醒的信息，例如：正在加载中.." })
     * 增加了对页面部分元素进行解除遮罩的功能，增加了参数 el：jquery获取元素的选择器
     * @param p
     *            p.el 需要进行遮罩的jquery选择器；
     *            p.message 信息；
     */
    $.gridLoading = function(p) {
        var opt = $.extend({
                    el:window,
                    message : "正在获取数据..."
                }, p)
        $(opt.el).block({
                    message : "<div class='loadLevel'>" + opt.message + "</div>",
                    overlayCSS : {
                        border : "medium none",
                        width : "100%",
                        height : "100%",
                        top : "0pt",
                        left : "0pt",
                        backgroundColor : "#000",
                        opacity : 0.3,
                        cursor : "wait",
                        position : "fixed"
                    }
                });
    }

    /**
     * 解除遮罩效果；增加了对页面部分元素进行解除遮罩的功能
     * @param p
     *     p.el 需要解除遮罩的jquery选择器
     */
    $.gridUnLoading = function(p) {
        var opt = $.extend({
                    el:window
                }, p);
         $(opt.el).unblock();
    }

    /**
     * 将url 返回的信息，填充到 select 的 options;
     * 
     * @param p
     *            参数； p.url 获取数据的url; p.select 填入的select 符合jquery选择器 例如 #id
     *            p.isBlank 是否显示空值
     */
    $.fillSelect = function(p) {
        if (!p.url || !p.select) {
            $.alert('必须设置url属性和select属性');
            return;
        }

        var opts = $.extend({
                    url : "",
                    isBlank : false
                }, p);
        var s = opts.select;
        var width = $(s).attr("width");
        if (!width) {
            width = $(s).attr("style");
        }
        $.appAjax({
                    url : opts.url,
                    data : opts.data,
                    async : true,
                    dataType : 'json',
                    type : 'POST',
                    success : function(data) {
                        var d = data;
                        if (opts.onResultData && $.isFunction(opts.onResultData)) {
                            d = opts.onResultData(data);
                        }

                        $(s).empty();
                        if (opts.isBlank) {
                            $(s).append('<option value="">－－全部－－</option>');
                        }
                        if (!d.options) {
                            return;
                        }
                        $.each(d.options, function(i, n) {
                                    var op = '<option value="' + n.value + '" ';
                                    if (n.selected) {
                                        op = op + ' selected ';
                                    }
                                    op = op + '>' + n.label + '</option>';
                                    $(s).append(op);
                                });
                        // 设置Select宽度
                        $(s).attr("style", width ? width : '150px');
                        if (opts.callback && $.isFunction(opts.callback)) {
                            opts.callback();
                        }
                    }
                });
    }

})(jQuery)

var FrameWin = {
    openWindows : [],
    closeParam : "",
    /**
     * 显示页面
     * 
     * @param {}
     *            obj
     */
    showWindow : function(p) {
        /**
         * 增加判断，如果当前已经打开了一个窗口，则不再打开了，即相同ID的窗口不能多次打开begin
         */
        this.closeParam = '';
        var flag = false;
        $.each(this.openWindows, function(i, n) {
                    if (n.id == p.id) {
                        flag = true;
                    }
                });
        if (flag) {
            return;
        }

        var fid = "_iframe";
        var opts = {
            id : p.id,
            width : p.width,
            height : p.height,
            top : p.top,
            left : p.left,
            modal : p.modal,
            resizable : false,
            buttons : p.buttons,
            onClose : function() {
                // 弹出页面的关闭事件监听；
                $("iframe", this).attr('src', '');
                $(this).dialog('destroy');
                $(this).remove();
                var _id;
                var _no = 0;
                var _relation;
                $.each(FrameWin.openWindows, function(i, n) {
                            if (n.show) {
                                _id = n.id;
                                _no = i;
                                _relation = n.relation;
                                return;
                            }
                        });

                $.each(FrameWin.openWindows, function(i, n) {
                            if (n.id == _relation) {
                                n.show = true;
                            }
                        })

                var arr = new Array;
                $.each(FrameWin.openWindows, function(i, n) {
                            if (_no != i) {
                                arr.push(n);
                            }
                        });
                FrameWin.openWindows = arr;
                arr = null;

                if (p.onClose) {
                    p.onClose(FrameWin.closeParam);
                }
            }
        };

        $.each(this.openWindows, function(i, n) {
                    n.show = false;
                });

        var _window = document.createElement('div');// 弹出窗口
        $(_window).attr('id', 'dialog' + p.id);

        if (this.openWindows.length == 0) {
            this.openWindows.push({
                        id : p.id,
                        show : true,
                        relation : ''
                    });

        } else {
            var index = this.openWindows.length;
            this.openWindows.push({
                        id : p.id,
                        show : true,
                        relation : openWindows[index - 1].id
                    });
        }
        $(_window).attr('title', p.title);
        $(_window).attr('id', 'dialog' + p.id);
        $(_window).dialog(opts);
        $(_window).empty();
        $(_window).dialog('open');

        var srcurl = p.url;
        // if (p.type == 'iframe') {
        if (p.param) {
            srcurl = srcurl + '?' + $.param(p.param);
        }

        var iframe = $('<iframe id="' + fid + '" name="' + fid + '" src="' + srcurl
                + '" frameborder="0" scrolling="auto" />').css({
                    width : '100%',
                    height : $('#dialog' + p.id).height() - 5,
                    border : 0
                }).appendTo($(_window));
        // }
    },

    /**
     * 关闭
     * 
     * @param {}
     *            obj
     */
    closeWindow : function(obj) {
        var _id;
        $.each(this.openWindows, function(i, n) {
                    if (n.show) {
                        _id = n.id;
                        return;
                    }
                });
        this.closeParam = obj;
        $('#dialog' + _id).dialog('close');
    }
}