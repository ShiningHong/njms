/*
 * Flexigrid for jQuery - v1.1
 * 
 * Copyright (c) 2008 Paulo P. Marinas (code.google.com/p/flexigrid/) Dual
 * licensed under the MIT or GPL Version 2 licenses. http://jquery.org/license
 * 
 */
(function($) {
    $.addFlex = function(t, p) {
        if (t.grid)
            return false; // return if already exist
        p = $.extend({ // apply default properties
            height : 200, // default height
            width : 'auto', // auto width
            striped : true, // apply odd even stripes
            novstripe : false,
            minwidth : 30, // min width of columns
            minheight : 160, // min height of columns
            //maxwidth : null,//2017-08-24 add
            maxheight: '',//2017-08-24 add
            resizable : true, // allow table resizing
            url : false, // URL if using data from AJAX
            method : 'POST', // data sending method
            dataType : 'json', // type of data for AJAX, either xml or json 修改为默认是  json
            errormsg : '查询异常',
            usepager : false,
            nowrap : true,
            page : 1, // current page
            total : 1, // total pages
            useRp : true, // use the results per page select box
            rp : 15, // results per page
            rpOptions : [10, 15, 20, 30, 50], // allowed per-page values
            title : false,
            pagestat : '显示记录从{from}到{to}，合计：{total}条',
            pagetext : '页：',
            outof : 'of',
            findtext : 'Find',
            procmsg : '数据查询中, 请稍等 ...',
            query : '',//搜索查询的条件
            qtype : '',//搜索查询的类别
            nomsg : '没有符合条件的记录',
            minColToggle : 1, // minimum allowed column to be hidden
            showToggleBtn : false, // 是否展示列的隐藏选择；
            hideOnSubmit : true, //是否在回调时显示遮盖
            autoload : true,  //自动加载，即第一次发起Ajax请求
            blockOpacity : 0.5, //透明度设置
            preProcess : false,  
            onDragCol : false,
            onToggleCol : false,
            onChangeSort : false,  //改变排序的时候，可在此方法中重写，自行实现客户端排序
            onSuccess : false,  //成功后执行
            onError : false,  //失败后执行
            onSubmit : false, // using a custom populate function
            ///隐藏属性：singleSelect 是否单选；
            // /修改，增加的参数；add by yugn
            searchModel : false ,// 查询模型；服务端的
            expData:true ,//是否导出数据；
            checkBox: true,  //是否需要checkbox
            showRowNum:true, //是否显示行号；
            maxRp:100000, //导出的最大记录数；
            sortname:"",
            sortorder:"asc",
            onRowClicked:false, ///行单击事件
            rowStyler : false  ///行样式处理函数；该函数需要返回 style的值；传入2个参数：行的当前序列号，以及行的数据；
        }, p);
        $(t).show() // show if hidden
                .attr({
                            cellPadding : 0,
                            cellSpacing : 0,
                            border : 0
                        }) // remove padding and spacing
                .removeAttr('width'); // remove width properties
        // create grid class
        var g = {
            hset : {},
            rePosDrag : function() {
                var cdleft = 0 - this.hDiv.scrollLeft;
                if (this.hDiv.scrollLeft > 0)
                    cdleft -= Math.floor(p.cgwidth / 2);
                $(g.cDrag).css({
                            top : g.hDiv.offsetTop + 1
                        });
                var cdpad = this.cdpad;
                $('div', g.cDrag).hide();
                $('thead tr:first th:visible', this.hDiv).each(function() {
                            var n = $('thead tr:first th:visible', g.hDiv).index(this);
                            var cdpos = parseInt($('div', this).width());
                            if (cdleft == 0)
                                cdleft -= Math.floor(p.cgwidth / 2);
                            cdpos = cdpos + cdleft + cdpad;
                            if (isNaN(cdpos)) {
                                cdpos = 0;
                            }
                            $('div:eq(' + n + ')', g.cDrag).css({
                                        'left' : cdpos + 'px'
                                    }).show();
                            cdleft = cdpos;
                        });
            },
            fixHeight : function(newH) {
                newH = false;
                if (!newH)
                    newH = $(g.bDiv).height();
                var hdHeight = $(this.hDiv).height();
                $('div', this.cDrag).each(function() {
                            $(this).height(newH + hdHeight);
                        });
                var nd = parseInt($(g.nDiv).height());    
                
                
                if (nd > newH)
                    $(g.nDiv).height(newH).width(200);
                else
                    $(g.nDiv).height('auto').width('auto');
                $(g.block).css({
                            height : newH,
                            marginBottom : (newH * -1)
                        });
                var hrH = g.bDiv.offsetTop + newH;
                if (p.height != 'auto' && p.resizable)
                    hrH = g.vDiv.offsetTop;
                $(g.rDiv).css({
                            height : hrH
                        });
            },
            dragStart : function(dragtype, e, obj) { // default drag function
            	 //alert(dragtype);
                if (dragtype == 'colresize') {// column resize
                    $(g.nDiv).hide();
                    $(g.nBtn).hide();
                    var n = $('div', this.cDrag).index(obj);
                    var ow = $('th:visible div:eq(' + n + ')', this.hDiv).width();
                    $(obj).addClass('dragging').siblings().hide();
                    $(obj).prev().addClass('dragging').show();
                    this.colresize = {
                        startX : e.pageX,
                        ol : parseInt(obj.style.left),
                        ow : ow,
                        n : n
                    };
                    $('body').css('cursor', 'col-resize');
                } else if (dragtype == 'vresize') {// table resize
                    var hgo = false;
                    $('body').css('cursor', 'row-resize');
                    if (obj) {
                        hgo = true;
                        $('body').css('cursor', 'col-resize');
                    }
                    this.vresize = {
                        h : p.height,
                        //mh: p.maxheight,
                        sy : e.pageY,
                        w : p.width,
                        sx : e.pageX,
                        hgo : hgo
                    };
                } else if (dragtype == 'colMove') {// column header drag
                    $(g.nDiv).hide();
                    $(g.nBtn).hide();
                    this.hset = $(this.hDiv).offset();
                    this.hset.right = this.hset.left + $('table', this.hDiv).width();
                    this.hset.bottom = this.hset.top + $('table', this.hDiv).height();
                    this.dcol = obj;
                    this.dcoln = $('th', this.hDiv).index(obj);
                    this.colCopy = document.createElement("div");
                    this.colCopy.className = "colCopy";
                    this.colCopy.innerHTML = obj.innerHTML;
                    if ($.browser.msie) {
                        this.colCopy.className = "colCopy ie";
                    }
                    $(this.colCopy).css({
                                'position' : 'absolute',
                                'float' : 'left',
                                'display' : 'none',
                                'textAlign' : obj.align
                            });
                    $('body').append(this.colCopy);
                    $(this.cDrag).hide();
                }
                $('body').noSelect();
            },
            dragMove : function(e) {
                if (this.colresize) {// column resize
                    var n = this.colresize.n;
                    var diff = e.pageX - this.colresize.startX;
                    var nleft = this.colresize.ol + diff;
                    var nw = this.colresize.ow + diff;
                    if (nw > p.minwidth) {
                        $('div:eq(' + n + ')', this.cDrag).css('left', nleft);
                        this.colresize.nw = nw;
                    }
                } else if (this.vresize) {// table resize
                    var v = this.vresize;
                    var y = e.pageY;
                    var diff = y - v.sy;
                    if (!p.defwidth)
                        p.defwidth = p.width;
                    if (p.width != 'auto' && !p.nohresize && v.hgo) {
                        var x = e.pageX;
                        var xdiff = x - v.sx;
                        var newW = v.w + xdiff;
                        if (newW > p.defwidth) {
                            this.gDiv.style.width = newW + 'px';
                            p.width = newW;
                        }
                    }
                    var newH = v.h + diff;
                    if ((newH > p.minheight || p.height < p.minheight) && !v.hgo) {
                        this.bDiv.style.height = newH + 'px';
                        p.height = newH;
                        this.fixHeight(newH);
                        //alert("newH:"+newH);
                    }
                    v = null;
                } else if (this.colCopy) {
                    $(this.dcol).addClass('thMove').removeClass('thOver');
                    if (e.pageX > this.hset.right || e.pageX < this.hset.left || e.pageY > this.hset.bottom
                            || e.pageY < this.hset.top) {
                        // this.dragEnd();
                        $('body').css('cursor', 'move');
                    } else {
                        $('body').css('cursor', 'pointer');
                    }
                    $(this.colCopy).css({
                                top : e.pageY + 10,
                                left : e.pageX + 20,
                                display : 'block'
                            });
                }
            },
            dragEnd : function() {
                if (this.colresize) {
                    var n = this.colresize.n;
                    var nw = this.colresize.nw;
                    $('th:visible div:eq(' + n + ')', this.hDiv).css('width', nw);
                    $('tr', this.bDiv).each(function() {
                                $('td:visible div:eq(' + n + ')', this).css('width', nw);
                            });
                    this.hDiv.scrollLeft = this.bDiv.scrollLeft;
                    $('div:eq(' + n + ')', this.cDrag).siblings().show();
                    $('.dragging', this.cDrag).removeClass('dragging');
                    this.rePosDrag();
                    this.fixHeight();
                    this.colresize = false;
                } else if (this.vresize) {
                    this.vresize = false;
                } else if (this.colCopy) {
                    $(this.colCopy).remove();
                    if (this.dcolt != null) {
                        if (this.dcoln > this.dcolt)
                            $('th:eq(' + this.dcolt + ')', this.hDiv).before(this.dcol);
                        else
                            $('th:eq(' + this.dcolt + ')', this.hDiv).after(this.dcol);
                        this.switchCol(this.dcoln, this.dcolt);
                        $(this.cdropleft).remove();
                        $(this.cdropright).remove();
                        this.rePosDrag();
                        if (p.onDragCol) {
                            p.onDragCol(this.dcoln, this.dcolt);
                        }
                    }
                    this.dcol = null;
                    this.hset = null;
                    this.dcoln = null;
                    this.dcolt = null;
                    this.colCopy = null;
                    $('.thMove', this.hDiv).removeClass('thMove');
                    $(this.cDrag).show();
                }
                $('body').css('cursor', 'default');
                $('body').noSelect(false);
            },
            toggleCol : function(cid, visible) {
                var ncol = $("th[axis='col" + cid + "']", this.hDiv)[0];
                var n = $('thead th', g.hDiv).index(ncol);
                var cb = $('input[value=' + cid + ']', g.nDiv)[0];
                if (visible == null) {
                    visible = ncol.hidden;
                }
                if ($('input:checked', g.nDiv).length < p.minColToggle && !visible) {
                    return false;
                }
                if (visible) {
                    ncol.hidden = false;
                    $(ncol).show();
                    cb.checked = true;
                } else {
                    ncol.hidden = true;
                    $(ncol).hide();
                    cb.checked = false;
                }
                $('tbody tr', t).each(function() {
                            if (visible) {
                                $('td:eq(' + n + ')', this).show();
                            } else {
                                $('td:eq(' + n + ')', this).hide();
                            }
                        });
                this.rePosDrag();
                if (p.onToggleCol) {
                    p.onToggleCol(cid, visible);
                }
                return visible;
            },
            switchCol : function(cdrag, cdrop) { // switch columns
                $('tbody tr', t).each(function() {
                            if (cdrag > cdrop)
                                $('td:eq(' + cdrop + ')', this).before($('td:eq(' + cdrag + ')', this));
                            else
                                $('td:eq(' + cdrop + ')', this).after($('td:eq(' + cdrag + ')', this));
                        });
                // switch order in nDiv
                if (cdrag > cdrop) {
                    $('tr:eq(' + cdrop + ')', this.nDiv).before($('tr:eq(' + cdrag + ')', this.nDiv));
                } else {
                    $('tr:eq(' + cdrop + ')', this.nDiv).after($('tr:eq(' + cdrag + ')', this.nDiv));
                }
                if ($.browser.msie && $.browser.version < 7.0) {
                    $('tr:eq(' + cdrop + ') input', this.nDiv)[0].checked = true;
                }
                this.hDiv.scrollLeft = this.bDiv.scrollLeft;
            },
            scroll : function() {
                this.hDiv.scrollLeft = this.bDiv.scrollLeft;
                this.rePosDrag();
            },
            addData : function(data) { // parse data
                if (p.dataType == 'json') {
                    data = $.extend({
                                rows : [],
                                page : 0,
                                total : 0
                            }, data);
                }
                if (p.preProcess) {
                    data = p.preProcess(data);
                }
                //初始化选中值
                g.gDiv.checkedObjects = [];
                //初始化结果值
                g.gDiv.result = data;
                
                $('.pReload', this.pDiv).removeClass('loading');
                this.loading = false;
                if (!data) {
                    $('.pPageStat', this.pDiv).html(p.errormsg);
                    return false;
                }
                if (p.dataType == 'xml') {
                    p.total = +$('rows total', data).text();
                } else {
                    p.total = data.total;
                }
                if (p.total == 0) {
                    $('tr, a, td, div', t).unbind();
                    $(t).empty();
                    p.pages = 1;
                    p.page = 1;
                    this.buildpager();
                    $('.pPageStat', this.pDiv).html(p.nomsg);
                    //add by chenJiaYing on 20150821
                    if (p.onSuccess) {
                        p.onSuccess(this);
                    }
                    return false;
                }
                p.pages = Math.ceil(p.total / p.rp);
                if (p.dataType == 'xml') {
                    p.page = +$('rows page', data).text();
                } else {
                    p.page = data.page;
                }
                this.buildpager();
                // build new body
                var tbody = document.createElement('tbody');
                if (p.dataType == 'json') {
                    //设置本页的初始行数
                    var pageStartNum = ( p.page - 1 ) * p.rp;
                    $.each(data.rows, function(i, row) {
                                var tr = document.createElement('tr');
                                if (i % 2 && p.striped) {
                                    tr.className = 'erow';
                                }
                                if (row.id) {
                                    $(tr).attr("id",'row' + row.id).attr("row_id",row.id);
                                }
                                $(tr).attr("row-index=\""+i+"\"");
                                //行样式处理，传入两个参数：第N行，以及行的数据
                                if(p.rowStyler && $.isFunction(p.rowStyler)){
                                    tr.style = p.rowStyler(i,row);
                                }
                                
                                $('thead tr:first th', g.hDiv).each( function() {
                                    // add cell
                                    var td = document.createElement('td');
                                    var idx = $(this).attr('axis').substr(3);
                                    td.align = this.align;
                                    if(idx=="rownum"){
                                        //如果是行号，做以下处理
                                        var currentNum = pageStartNum + i + 1;
                                        $(td).attr("width","20").attr("id","gridRowNum"+row.id).css({"font-weight" : "bold"}).text(currentNum);
                                    } else if(idx=="check"){
                                        ///选择框，单选，出现radio,多选出现 checkbox
                                        var html = "";
                                        if(p.singleSelect){
                                            html = $("table", g.bDiv).attr("id");
                                            html = "<input id='"+row.id+"' value='"+row.id+"' type='radio' class='itemradio' name='"+html+"'></input>";
                                        } else {
                                            html = "<input id='"+row.id+"' value='"+row.id+"' type='checkbox' class='itemchk'></input>";
                                        }
                                        $(td).attr("width","20").attr("id",'select'+row.id).html(html);
                                    } else {
                                        // If the json elements aren't named (which is typical), use numeric order
                                      if (typeof row.cell[idx] != "undefined") {
                                        td.innerHTML = (row.cell[idx] != null) ? row.cell[idx] : '';// null-check
                                        // for Opera-browser
                                      } else {
                                        ///增加部分功能，针对name为空，表示没有与数据区的列映射；
                                        td.innerHTML = row.cell[p.colModel[idx].name] ? row.cell[p.colModel[idx].name]:'';
                                      }
                                    }
                                    $(td).attr('abbr', $(this).attr('abbr'));
                                    $(tr).addClass("databodyrow").append(td);
                                    td = null;
                                });
                                if ($('thead', this.gDiv).length < 1) {// handle
                                    // if grid has no headers
                                    for (idx = 0; idx < cell.length; idx++) {
                                        var td = document.createElement('td');
                                        // If the json elements aren't named (which is typical), use numeric order
                                        if (typeof row.cell[idx] != "undefined") {
                                            td.innerHTML = (row.cell[idx] != null) ? row.cell[idx] : '';// null-check
                                            // for Opera-browser
                                        } else {
                                            //td.innerHTML = row.cell[p.colModel[idx].name];
                                            ///增加部分功能，针对name为空，表示没有与数据区的列映射；
                                            td.innerHTML = row.cell[p.colModel[idx].name] ? row.cell[p.colModel[idx].name]:'';
                                        }
                                        $(tr).append(td);
                                        td = null;
                                    }
                                }
                                $(tbody).append(tr);
                                tr = null;
                            });
                } else if (p.dataType == 'xml') {
                    var i = 1;
                    $("rows row", data).each(function() {
                                i++;
                                var tr = document.createElement('tr');
                                if (i % 2 && p.striped) {
                                    tr.className = 'erow';
                                }
                                var nid = $(this).attr('id');
                                if (nid) {
                                    tr.id = 'row' + nid;
                                }
                                nid = null;
                                var robj = this;
                                $('thead tr:first th', g.hDiv).each(function() {
                                            var td = document.createElement('td');
                                            var idx = $(this).attr('axis').substr(3);
                                            td.align = this.align;
                                            td.innerHTML = $("cell:eq(" + idx + ")", robj).text();
                                            $(td).attr('abbr', $(this).attr('abbr'));
                                            $(tr).append(td);
                                            td = null;
                                        });
                                if ($('thead', this.gDiv).length < 1) {// handle
                                    // if
                                    // grid
                                    // has
                                    // no
                                    // headers
                                    $('cell', this).each(function() {
                                                var td = document.createElement('td');
                                                td.innerHTML = $(this).text();
                                                $(tr).append(td);
                                                td = null;
                                            });
                                }
                                $(tbody).append(tr);
                                tr = null;
                                robj = null;
                            });
                }
                $('tr', t).unbind();
                $(t).empty();
                $(t).append(tbody);
                this.addCellProp();
                this.addRowProp();
                this.rePosDrag();
                tbody = null;
                data = null;
                i = null;
                if (p.onSuccess) {
                    p.onSuccess(this);
                }
                if (p.hideOnSubmit) {
                    $(g.block).remove();
                }
                this.hDiv.scrollLeft = this.bDiv.scrollLeft;
                if ($.browser.opera) {
                    $(t).css('visibility', 'visible');
                }
            },
            changeSort : function(th) { // change sortorder
                if (this.loading) {
                    return true;
                }
                $(g.nDiv).hide();
                $(g.nBtn).hide();
                if (p.sortname == $(th).attr('abbr')) {
                    if (p.sortorder == 'asc') {
                        p.sortorder = 'desc';
                    } else {
                        p.sortorder = 'asc';
                    }
                }
                $(th).addClass('sorted').siblings().removeClass('sorted');
                $('.sdesc', this.hDiv).removeClass('sdesc');
                $('.sasc', this.hDiv).removeClass('sasc');
                $('div', th).addClass('s' + p.sortorder);
                p.sortname = $(th).attr('abbr');
                if (p.onChangeSort) {
                    p.onChangeSort(p.sortname, p.sortorder);
                } else {
                    this.populate();
                }
            },
            buildpager : function() { // rebuild pager based on new properties
                $('.pcontrol input', this.pDiv).val(p.page);
                $('.pcontrol span', this.pDiv).html(p.pages);
                var r1 = (p.page - 1) * p.rp + 1;
                var r2 = r1 + p.rp - 1;
                if (p.total < r2) {
                    r2 = p.total;
                }
                var stat = p.pagestat;
                stat = stat.replace(/{from}/, r1);
                stat = stat.replace(/{to}/, r2);
                stat = stat.replace(/{total}/, p.total);
                $('.pPageStat', this.pDiv).html(stat);
            },
            populate : function() { // get latest data
                if (this.loading) {
                    return true;
                }
                if (p.onSubmit) {
                    var gh = p.onSubmit();
                    if (!gh) {
                        return false;
                    }
                }
                this.loading = true;
                if (!p.url) {
                    return false;
                }
                $('.pPageStat', this.pDiv).html(p.procmsg);
                $('.pReload', this.pDiv).addClass('loading');
                $(g.block).css({
                            top : g.bDiv.offsetTop
                        });
                if (p.hideOnSubmit) {
                    $(this.gDiv).prepend(g.block);
                }
                if ($.browser.opera) {
                    $(t).css('visibility', 'hidden');
                }
                if (!p.newp) {
                    p.newp = 1;
                }
                if (p.page > p.pages) {
                    p.page = p.pages;
                }
                /*
                var param = [{
                            name : p.searchModel ? p.searchModel + '.page' : 'searchModel.page',
                            value : p.newp
                        }, {
                            name : p.searchModel ? p.searchModel + '.rp' : 'searchModel.rp',
                            value : p.rp
                        }, {
                            name : p.searchModel ? p.searchModel + '.sortname' : 'searchModel.sortname',
                            value : p.sortname
                        }, {
                            name : p.searchModel ? p.searchModel + '.sortorder' : 'searchModel.sortorder',
                            value : p.sortorder
                        }, {
                            name : p.searchModel ? p.searchModel + '.query' : 'searchModel.query',
                            value : p.query
                        }, {
                            name : p.searchModel ? p.searchModel + '.qtype' : 'searchModel.qtype',
                            value : p.qtype
                        }];
                */
                var param = [{
                    name : p.searchModel ? p.searchModel + '.page' : 'page',
                    value : p.newp
                }, {
                    name : p.searchModel ? p.searchModel + '.rp' : 'rp',
                    value : p.rp
                }, {
                    name : p.searchModel ? p.searchModel + '.sortname' : 'sortname',
                    value : p.sortname
                }, {
                    name : p.searchModel ? p.searchModel + '.sortorder' : 'sortorder',
                    value : p.sortorder
                }, {
                    name : p.searchModel ? p.searchModel + '.query' : 'query',
                    value : p.query
                }, {
                    name : p.searchModel ? p.searchModel + '.qtype' : 'qtype',
                    value : p.qtype
                }];
                
                if (p.params) {
                    for (var pi = 0; pi < p.params.length; pi++) {
                        param[param.length] = p.params[pi];
                    }
                }
                $.appAjax({
                            type : p.method,
                            url : p.url,
                            data : param,
                            async: true,
                            dataType : p.dataType,
                            success : function(data) {
                            	/*
                                if (!data.searchResultModel) {
                                    alert("您的查询结果不是flexigrid所需要的格式！");
                                    return;
                                }
                                g.addData(eval("(" + data.searchResultModel + ")"));
                                */
                            	if(!data){
                            		 alert("您的查询结果异常！请联系管理员");
                            		 return;
                            	}
                            	if (!data.searchResultModel) {
                            		g.addData(data);
                            	}else{
                            		g.addData(eval("(" + data.searchResultModel + ")"));
                            	}

                                // g.addData(data);
                            },
                            error : function(XMLHttpRequest, textStatus, errorThrown) {
                                try {
                                    if (p.onError)
                                        p.onError(XMLHttpRequest, textStatus, errorThrown);
                                } catch (e) {
                                }
                            }
                        });
            },
            doSearch : function() {
                p.query = $('input[name=q]', g.sDiv).val();
                p.qtype = $('select[name=qtype]', g.sDiv).val();
                p.newp = 1;
                this.populate();
            },
            changePage : function(ctype) { // change page
                if (this.loading) {
                    return true;
                }
                switch (ctype) {
                    case 'first' :
                        p.newp = 1;
                        break;
                    case 'prev' :
                        if (p.page > 1) {
                            p.newp = parseInt(p.page) - 1;
                        }
                        break;
                    case 'next' :
                        if (p.page < p.pages) {
                            p.newp = parseInt(p.page) + 1;
                        }
                        break;
                    case 'last' :
                        p.newp = p.pages;
                        break;
                    case 'input' :
                        var nv = parseInt($('.pcontrol input', this.pDiv).val());
                        if (isNaN(nv)) {
                            nv = 1;
                        }
                        if (nv < 1) {
                            nv = 1;
                        } else if (nv > p.pages) {
                            nv = p.pages;
                        }
                        $('.pcontrol input', this.pDiv).val(nv);
                        p.newp = nv;
                        break;
                }
                if (p.newp == p.page) {
                    return false;
                }
                if (p.onChangePage) {
                    p.onChangePage(p.newp);
                } else {
                    this.populate();
                }
            },
            addCellProp : function() {
                $('tbody tr td', g.bDiv).each(function() {
                    var tdDiv = document.createElement('div');
                    var n = $('td', $(this).parent()).index(this);
                    var pth = $('th:eq(' + n + ')', g.hDiv).get(0);
                    if (pth != null) {
                        if (p.sortname == $(pth).attr('abbr') && p.sortname) {
                            this.className = 'sorted';
                        }
                        $(tdDiv).css({
                                    textAlign : pth.align,
                                    width : $('div:first', pth)[0].style.width
                                });
                        if (pth.hidden) {
                            $(this).css('display', 'none');
                        }
                    }
                    if (p.nowrap == false) {
                        $(tdDiv).css('white-space', 'normal');
                    }
                    if (this.innerHTML == '') {
                        this.innerHTML = '&nbsp;';
                    }
                    
                    var prnt = $(this).parent()[0];
                    var pid = false;
                    if (prnt.id) {
                        pid = prnt.id.substr(3);
                    }
                    
                    ///格式化值；
                    if(pth && pth.formatter && $.isFunction(pth.formatter)){
                        
                        tdDiv.innerHTML = pth.formatter(this.innerHTML,g.getRowDataById(pid));
                    } else {
                        tdDiv.innerHTML = this.innerHTML;
                    }
                    
                    if (pth != null) {
                        if (pth.process)
                            pth.process(tdDiv, pid);
                    }
                    $(this).empty().append(tdDiv).removeAttr('width'); // wrap content
                    });
            },
            getCellDim : function(obj) {// get cell prop for editable event
                var ht = parseInt($(obj).height());
                var pht = parseInt($(obj).parent().height());
                var wt = parseInt(obj.style.width);
                var pwt = parseInt($(obj).parent().width());
                var top = obj.offsetParent.offsetTop;
                var left = obj.offsetParent.offsetLeft;
                var pdl = parseInt($(obj).css('paddingLeft'));
                var pdt = parseInt($(obj).css('paddingTop'));
                return {
                    ht : ht,
                    wt : wt,
                    top : top,
                    left : left,
                    pdl : pdl,
                    pdt : pdt,
                    pht : pht,
                    pwt : pwt
                };
            },
            addRowProp : function() {
                $('tbody tr', g.bDiv).each(function() {
                    //////针对选择框的处理，add by yugn
                    if(p.singleSelect){
                        //单选
                        $(":radio.itemradio",this).click(function(e){
                            var obj = (e.target || e.srcElement);
                            var tr = $(obj).closest("tr.databodyrow");
                            var id = $(obj).val();
                            var currentRow = g.getRowDataById(id);
                            g.gDiv.checkedObjects = [currentRow];
                            $(tr).addClass("trSelected");
                            $(tr).siblings().removeClass('trSelected');
                            //触发行单击事件
                            if (p.onRowClicked) {
                                  p.onRowClicked(obj.checked, currentRow);
                            }
                        });
                    } else {
                        $(":checkbox.itemchk",this).click(function(e){
                            var obj = (e.target || e.srcElement);
                            var tr = $(obj).closest("tr.databodyrow");
                            var id = $(obj).val();
                            var currentRow = g.getRowDataById(id);
                            if(obj.checked){
                                $(tr).addClass("trSelected");
                                g.pushCurrentRowIntoChecked(currentRow);
                            } else {
                                $(tr).removeClass("trSelected");
                                g.popRowFromCheckedById(id);
                            }
                            //触发行单击事件
                            if (p.onRowClicked) {
                                  p.onRowClicked(obj.checked, currentRow);
                            }
                        });
                    };
                    
                    $(this).click(function(e){
                        var obj = (e.target || e.srcElement);
                        if (obj.href ||obj.type)
                        //2015.08.24 使勾选可以触发行点击事件
                        //if (obj.href || (obj.type && obj.type!='checkbox' && obj.type!='radio'))
                            return true;
                        var id = $(this).attr("row_id");
                        var currentRow = g.getRowDataById(id);
                        if(p.checkBox){
                            //需要checkBox
                            if (p.singleSelect){
                                //有单选；
                                $(":radio.itemradio",this)[0].click();
                                /*
                                if (p.onRowClicked) {
                                  p.onRowClicked($(":radio.itemradio",this).attr("checked"), currentRow);
                                }
                                */
                            } else {
                                $(":checkbox.itemchk",this)[0].click();
                                /*
                                if (p.onRowClicked) {
                                  p.onRowClicked($(":checkbox.itemchk",this).attr("checked"), currentRow);
                                }
                                */
                            }
                        } else {
                            //不需要checkBox
                            //$(this).toggleClass('trSelected');
                            if(p.singleSelect || p.onRowClicked) {
                            	$(this).toggleClass('trSelected');
                            }
                            if (p.singleSelect) {
                            	$(this).siblings().removeClass('trSelected');
                            }
                            if (p.onRowClicked) {
                                p.onRowClicked($(this).hasClass("trSelected"), currentRow);
                            }
                        }
                    })
                            /*$(this).click(function(e) {
                                        var obj = (e.target || e.srcElement);
                                        if (obj.href || obj.type)
                                            return true;
                                        $(this).toggleClass('trSelected');
                                        if (p.singleSelect)
                                            $(this).siblings().removeClass('trSelected');
                                    }).mousedown(function(e) {
                                        if (e.shiftKey) {
                                            $(this).toggleClass('trSelected');
                                            g.multisel = true;
                                            this.focus();
                                            $(g.gDiv).noSelect();
                                        }
                                    }).mouseup(function() {
                                        if (g.multisel) {
                                            g.multisel = false;
                                            $(g.gDiv).noSelect(false);
                                        }
                                    }).hover(function(e) {
                                        if (g.multisel) {
                                            $(this).toggleClass('trSelected');
                                        }
                                    }, function() {
                                    });*/
                            if ($.browser.msie && $.browser.version < 7.0) {
                                $(this).hover(function() {
                                            $(this).addClass('trOver');
                                        }, function() {
                                            $(this).removeClass('trOver');
                                        });
                            }
                        });
            },
            pager : 0,
            getRowDataById:function(id){
                var currentRow = null;
                $.each(g.gDiv.result.rows,function(i,n){
                     if(n.id == id){
                         currentRow = n;
                         return false;
                     }
                });
                return currentRow;
            },
            pushCurrentRowIntoChecked:function(currentRow){
                if(!currentRow){
                    return ;
                }
                var currentId = currentRow.id;
                var exists = false;
                $.each(g.gDiv.checkedObjects,function(i,n){
                    if(currentId == n.id){
                        exists = true;
                        return false;
                    }
                });
                
                if(exists){
                    //如果已经存在，忽略
                } else {
                    g.gDiv.checkedObjects.push(currentRow);
                }
                
                //如果选中的记录数 == 当前页的总记录数，将 header 部分的checkBox设置为checked
                if(g.gDiv.checkedObjects.length == g.gDiv.result.rows.length){
                    $("#checkAll:checkbox",g.hDiv).attr("checked","true");
                }
            },
            popRowFromCheckedById:function(rowid){
                var length = g.gDiv.checkedObjects.length;
                for(var i=0;i<length;i++){
                    if(rowid == g.gDiv.checkedObjects[i].id){
                        //循环，后面一个值赋给前面的值；
                        for(var j=i+1;j<length;j++){
                            g.gDiv.checkedObjects[j-1]=g.gDiv.checkedObjects[j];
                        }
                        //最后一个弹出；
                        g.gDiv.checkedObjects.pop();
                        break;
                    }
                }
                //如果选中的记录数 == 当前页的总记录数，将 header 部分的checkBox设置为checked
                if(g.gDiv.checkedObjects.length != g.gDiv.result.rows.length){
                    $("#checkAll:checkbox",g.hDiv).removeAttr("checked");
                }
            },
            buildHeader:function(){
                var thead = document.createElement('thead');
                var tr = document.createElement('tr');
                
                //当设置显示行号时，增加行号列
                if (p.showRowNum){
                    $(tr).append("<th width='24' axis='colrownum' adduser=true align='center' id='gridRowNumColumn'>行号</th>");
                }
                
                //是否需要checkbox
                if (p.checkBox == true) {
                    //单选
                    if(p.singleSelect){
                      $(tr).append("<th width='20' id='check' align='center' axis='colcheck' adduser=true ><div id='checkAll'></div></th>");
                    } else {
                        //多选
                        var th_ch = document.createElement('th');
                        $(th_ch).attr('id', "check").attr('align', "center").attr('adduser', true).attr('axis', "colcheck").attr("width","20");
                        var ch = document.createElement('input');
                        $(ch).attr('type', "checkbox").attr('id', "checkAll");
                        $(th_ch).append(ch);
                        $(tr).append(th_ch);
                    }
                }
                ///实际配置的数据列
                for (var i = 0; i < p.colModel.length; i++) {
                    var cm = p.colModel[i];
                    var th = document.createElement('th');
                    th.innerHTML = cm.display;
                    if (cm.name && cm.sortable) {
                      $(th).attr('abbr', cm.name);
                    }
                    $(th).attr('axis', 'col' + i);
                    if (cm.align) {
                      th.align = cm.align;
                    }
                    if (cm.width) {
                      $(th).attr('width', cm.width);
                    }
                    if(cm.hide){
                        th.hidden = true;
                    }
                    /*if ($(cm).attr('hide')) {
                      th.hidden = true;
                    }*/
                    if (cm.process) {
                      th.process = cm.process;
                    }
                    //列追加formatter属性，用于格式化显示数据；
                    if(cm.formatter){
                        th.formatter = cm.formatter;
                    }
                    $(tr).append(th);
                }
                $(thead).append(tr);
                return thead;
            },
            checkAllOrNot: function(obj) {
                var ischeck = $(obj).attr("checked");
                $('tbody tr', g.bDiv).each(function() {
                    if (ischeck) {
                        $(this).addClass("trSelected");
                    }
                    else {
                        $(this).removeClass("trSelected");
                    }
                });
                $("input.itemchk", g.bDiv).each(function() {
                    //先将当前的选择设置为与总体选择不一致的check;而后调用各自的单击事件；
                    this.checked = !ischeck;
                    this.click();
                    //Raise Event
                    /*
                    if (p.onRowClicked) {
                        p.onRowClicked(this.checked,g.getRowDataById($(this).val()));
                    }*/
                });
            },
            serializeColModel : function(){
                //对于p.colModel做转换
                if(p.colModel){
                    var colModelArray = new Array();
                    $.each(p.colModel,function(i,n){
                        colModelArray.push("{'display':'"+n.display+"','name':'"+n.name+"'}");
                    });
                    return "["+colModelArray.join(",")+"]";
                } else {
                    return "";
                }
            },
            getCheckedObjects:function(){
                return g.gDiv.checkedObjects;
            }
        };
        
        if (p.colModel){
            $(t).prepend(g.buildHeader());
        }
        
        /*if (p.colModel) { // create model if any
            thead = document.createElement('thead');
            var tr = document.createElement('tr');
            for (var i = 0; i < p.colModel.length; i++) {
                var cm = p.colModel[i];
                var th = document.createElement('th');
                th.innerHTML = cm.display;
                if (cm.name && cm.sortable) {
                    $(th).attr('abbr', cm.name);
                }
                $(th).attr('axis', 'col' + i);
                if (cm.align) {
                    th.align = cm.align;
                }
                if (cm.width) {
                    $(th).attr('width', cm.width);
                }
                if ($(cm).attr('hide')) {
                    th.hidden = true;
                }
                if (cm.process) {
                    th.process = cm.process;
                }
                $(tr).append(th);
            }
            $(thead).append(tr);
            $(t).prepend(thead);
        }*/ // end if p.colmodel
        // init divs
        g.gDiv = document.createElement('div'); // create global container
        g.mDiv = document.createElement('div'); // create title container
        g.hDiv = document.createElement('div'); // create header container
        g.bDiv = document.createElement('div'); // create body container
        g.vDiv = document.createElement('div'); // create grip
        g.rDiv = document.createElement('div'); // create horizontal resizer
        g.cDrag = document.createElement('div'); // create column drag
        g.block = document.createElement('div'); // creat blocker
        g.nDiv = document.createElement('div'); // create column show/hide popup
        g.nBtn = document.createElement('div'); // create column show/hide
        // button
        g.iDiv = document.createElement('div'); // create editable layer
        g.tDiv = document.createElement('div'); // create toolbar
        g.sDiv = document.createElement('div');
        g.pDiv = document.createElement('div'); // create pager container
        if (!p.usepager) {
            g.pDiv.style.display = 'none';
            //如果不需要分页，那么设置每页的容量为 -1；
            p.rp = -1;
        }
        g.hTable = document.createElement('table');
        g.gDiv.className = 'flexigrid';
        
        // 存放返回的结果
        g.gDiv.result = new Object();
        // 存放选中的对象
        g.gDiv.checkedObjects = [];
        
        if (p.width != 'auto') {
            g.gDiv.style.width = p.width + 'px';
        }
        // add conditional classes
        if ($.browser.msie) {
            $(g.gDiv).addClass('ie');
        }
        if (p.novstripe) {
            $(g.gDiv).addClass('novstripe');
        }
        $(t).before(g.gDiv);
        $(g.gDiv).append(t);
        // set toolbar
        if (p.buttons) {
            g.tDiv.className = 'tDiv';
            var tDiv2 = document.createElement('div');
            tDiv2.className = 'tDiv2';
            for (var i = 0; i < p.buttons.length; i++) {
                var btn = p.buttons[i];
                if (!btn.separator) {
                    var btnDiv = document.createElement('div');
                    btnDiv.className = 'fbutton';
                    btnDiv.innerHTML = "<div><span>" + btn.name + "</span></div>";
                    if (btn.bclass)
                        $('span', btnDiv).addClass(btn.bclass).css({
                                    paddingLeft : 20
                                });
                    btnDiv.onpress = btn.onpress;
                    btnDiv.name = btn.name;
                    if (btn.onpress) {
                        $(btnDiv).click(function() {
                                    this.onpress(this.name, g.gDiv);
                                });
                    }
                    $(tDiv2).append(btnDiv);
                    if ($.browser.msie && $.browser.version < 7.0) {
                        $(btnDiv).hover(function() {
                                    $(this).addClass('fbOver');
                                }, function() {
                                    $(this).removeClass('fbOver');
                                });
                    }
                } else {
                    $(tDiv2).append("<div class='btnseparator'></div>");
                }
            }
            $(g.tDiv).append(tDiv2);
            $(g.tDiv).append("<div style='clear:both'></div>");
            $(g.gDiv).prepend(g.tDiv);
        }
        g.hDiv.className = 'hDiv';
        $(t).before(g.hDiv);
        g.hTable.cellPadding = 0;
        g.hTable.cellSpacing = 0;
        $(g.hDiv).append('<div class="hDivBox"></div>');
        $('div', g.hDiv).append(g.hTable);
        var thead = $("thead:first", t).get(0);
        if (thead)
            $(g.hTable).append(thead);
        thead = null;
        if (!p.colmodel)
            var ci = 0;
            
        //表头处理，在th和内容之间增加一个div，
        $('thead tr:first th', g.hDiv).each(function() {
            var thdiv = document.createElement('div');
            if ($(this).attr('abbr')) {
                $(this).click(function(e) {
                            if (!$(this).hasClass('thOver'))
                                return false;
                            var obj = (e.target || e.srcElement);
                            if (obj.href || obj.type)
                                return true;
                            g.changeSort(this);
                        });
                if ($(this).attr('abbr') == p.sortname) {
                    this.className = 'sorted';
                    thdiv.className = 's' + p.sortorder;
                }
            }
            if (this.hidden) {
                $(this).hide();
            }
            if (!p.colmodel && !$(this).attr("adduser")) {
                $(this).attr('axis', 'col' + ci++);
            }
            $(thdiv).css({
                        textAlign : this.align,
                        width : this.width + 'px'
                    });
            thdiv.innerHTML = this.innerHTML;
            $(this).empty().append(thdiv).removeAttr('width');
            //增加表头下的checkbox的点击事件
            $("input[type='checkbox']", this).click(function(e){
               g.checkAllOrNot(e.target || e.srcElement);
            });
            if(!$(this).attr("adduser") && $(this).attr("abbr")){
                ///增加这一个判断，用于控制自定义的两个列：行号和checkBox不进行列顺序调整；
                //而且有定义abbr ,即：colmodel的name属性有值；
                $(this).mousedown(function(e) {
                        g.dragStart('colMove', e, this);
                    }).hover(function() {
                        if (!g.colresize && !$(this).hasClass('thMove') && !g.colCopy) {
                            $(this).addClass('thOver');
                        }
                        if ($(this).attr('abbr') != p.sortname && !g.colCopy && !g.colresize && $(this).attr('abbr')) {
                            $('div', this).addClass('s' + p.sortorder);
                        } else if ($(this).attr('abbr') == p.sortname && !g.colCopy && !g.colresize
                                && $(this).attr('abbr')) {
                            var no = (p.sortorder == 'asc') ? 'desc' : 'asc';
                            $('div', this).removeClass('s' + p.sortorder).addClass('s' + no);
                        }
                        if (g.colCopy) {
                            var n = $('th', g.hDiv).index(this);
                            if (n == g.dcoln) {
                                return false;
                            }
                            if (n < g.dcoln) {
                                $(this).append(g.cdropleft);
                            } else {
                                $(this).append(g.cdropright);
                            }
                            g.dcolt = n;
                        } else if (!g.colresize) {
                            var nv = $('th:visible', g.hDiv).index(this);
                            var onl = parseInt($('div:eq(' + nv + ')', g.cDrag).css('left'));
                            var nw = jQuery(g.nBtn).outerWidth();
                            var nl = onl - nw + Math.floor(p.cgwidth / 2);
                            $(g.nDiv).hide();
                            $(g.nBtn).hide();
                            $(g.nBtn).css({
                                        'left' : nl,
                                        top : g.hDiv.offsetTop
                                    }).show();
                            var ndw = parseInt($(g.nDiv).width());
                            $(g.nDiv).css({
                                        top : g.bDiv.offsetTop
                                    });
                            if ((nl + ndw) > $(g.gDiv).width()) {
                                $(g.nDiv).css('left', onl - ndw + 1);
                            } else {
                                $(g.nDiv).css('left', nl);
                            }
                            if ($(this).hasClass('sorted')) {
                                $(g.nBtn).addClass('srtd');
                            } else {
                                $(g.nBtn).removeClass('srtd');
                            }
                        }
                    }, function() {
                        $(this).removeClass('thOver');
                        if ($(this).attr('abbr') != p.sortname) {
                            $('div', this).removeClass('s' + p.sortorder);
                        } else if ($(this).attr('abbr') == p.sortname) {
                            var no = (p.sortorder == 'asc') ? 'desc' : 'asc';
                            $('div', this).addClass('s' + p.sortorder).removeClass('s' + no);
                        }
                        if (g.colCopy) {
                            $(g.cdropleft).remove();
                            $(g.cdropright).remove();
                            g.dcolt = null;
                        }
                    }); // wrap content
            }
              
        });
        // set bDiv
        g.bDiv.className = 'bDiv';
        $(t).before(g.bDiv);
        
        /*$(g.bDiv).css({
            height : (p.height == 'auto') ? 'auto' : p.height + "px"//,
            //max-height:maxheight
            //max-height:maxheight
        }).scroll(function(e) {
            g.scroll()
        }).append(t);*/
        
        //高度自适应
        var hh = p.height;
        var mh;
        if(p.height=='fit'){
        	hh = "auto";
        	mh = $(document).height() - $(document.body).outerHeight(true)-$(g.bDiv).parents('.flexigrid').eq(0).height()-5;
        }
        
        $(g.bDiv).css({
            height : (hh == 'auto') ? 'auto' : hh + "px"
        }).scroll(function(e) {
            g.scroll()
        }).append(t);
        
        if(mh){
        	$(g.bDiv).css({"max-height":mh+"px"});
        }
                
        //2017-08-24 add 最大高度 start
        var maxheight;
        if(p.maxheight!=''){
        	maxheight = p.maxheight + "px";
        	$(g.bDiv).css({"max-height":maxheight});
        }
        //2017-08-24 add 最大高度 end 
        
        //if (p.height == 'auto') {
            $('table', g.bDiv).addClass('autoht');
        //}
            
        // add td & row properties
        g.addCellProp();
        g.addRowProp();
        // set cDrag
        var cdcol = $('thead tr:first th:first', g.hDiv).get(0);
        if (cdcol != null) {
            g.cDrag.className = 'cDrag';
            g.cdpad = 0;
            g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderLeftWidth'))) ? 0 : parseInt($('div', cdcol)
                    .css('borderLeftWidth')));
            g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderRightWidth'))) ? 0 : parseInt($('div', cdcol)
                    .css('borderRightWidth')));
            g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingLeft'))) ? 0 : parseInt($('div', cdcol)
                    .css('paddingLeft')));
            g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingRight'))) ? 0 : parseInt($('div', cdcol)
                    .css('paddingRight')));
            g.cdpad += (isNaN(parseInt($(cdcol).css('borderLeftWidth')))
                    ? 0
                    : parseInt($(cdcol).css('borderLeftWidth')));
            g.cdpad += (isNaN(parseInt($(cdcol).css('borderRightWidth'))) ? 0 : parseInt($(cdcol)
                    .css('borderRightWidth')));
            g.cdpad += (isNaN(parseInt($(cdcol).css('paddingLeft'))) ? 0 : parseInt($(cdcol).css('paddingLeft')));
            g.cdpad += (isNaN(parseInt($(cdcol).css('paddingRight'))) ? 0 : parseInt($(cdcol).css('paddingRight')));
            $(g.bDiv).before(g.cDrag);
            var cdheight = $(g.bDiv).height();
            var hdheight = $(g.hDiv).height();
            $(g.cDrag).css({
                        top : -hdheight + 'px'
                    });
            $('thead tr:first th', g.hDiv).each(function() {
                        var cgDiv = document.createElement('div');
                        $(g.cDrag).append(cgDiv);
                        if (!p.cgwidth) {
                            p.cgwidth = $(cgDiv).width();
                        }
                        $(cgDiv).css({
                                    height : cdheight + hdheight
                                }).mousedown(function(e) {
                                    g.dragStart('colresize', e, this);
                                });
                        if ($.browser.msie && $.browser.version < 7.0) {
                            g.fixHeight($(g.gDiv).height());
                            $(cgDiv).hover(function() {
                                        g.fixHeight();
                                        $(this).addClass('dragging')
                                    }, function() {
                                        if (!g.colresize)
                                            $(this).removeClass('dragging')
                                    });
                        }
                    });
        }
        // add strip
        if (p.striped) {
            $('tbody tr:odd', g.bDiv).addClass('erow');
        }
        if (p.resizable && p.height != 'auto') {
            g.vDiv.className = 'vGrip';
            $(g.vDiv).mousedown(function(e) {
                        g.dragStart('vresize', e)
                    }).html('<span></span>');
            $(g.bDiv).after(g.vDiv);
        }
        if (p.resizable && p.width != 'auto' && !p.nohresize) {
            g.rDiv.className = 'hGrip';
            $(g.rDiv).mousedown(function(e) {
                        g.dragStart('vresize', e, true);
                    }).html('<span></span>').css('height', $(g.gDiv).height());
            if ($.browser.msie && $.browser.version < 7.0) {
                $(g.rDiv).hover(function() {
                            $(this).addClass('hgOver');
                        }, function() {
                            $(this).removeClass('hgOver');
                        });
            }
            $(g.gDiv).append(g.rDiv);
        }
        // add pager
        if (p.usepager) {
            g.pDiv.className = 'pDiv';
            g.pDiv.innerHTML = '<div class="pDiv2"></div>';
            $(g.bDiv).after(g.pDiv);
            var html = ' <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol">'
                    + p.pagetext
                    + ' <input type="text" size="4" value="1" /> '
                    + p.outof
                    + ' <span> 1 </span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"></span></div>';
            $('div', g.pDiv).html(html);
            $('.pReload', g.pDiv).click(function() {
                        g.populate()
                    });
            $('.pFirst', g.pDiv).click(function() {
                        g.changePage('first')
                    });
            $('.pPrev', g.pDiv).click(function() {
                        g.changePage('prev')
                    });
            $('.pNext', g.pDiv).click(function() {
                        g.changePage('next')
                    });
            $('.pLast', g.pDiv).click(function() {
                        g.changePage('last')
                    });
            $('.pcontrol input', g.pDiv).keydown(function(e) {
                        if (e.keyCode == 13)
                            g.changePage('input')
                    });
            if ($.browser.msie && $.browser.version < 7)
                $('.pButton', g.pDiv).hover(function() {
                            $(this).addClass('pBtnOver');
                        }, function() {
                            $(this).removeClass('pBtnOver');
                        });
            if (p.useRp) {
                var opt = '', sel = '';
                for (var nx = 0; nx < p.rpOptions.length; nx++) {
                    if (p.rp == p.rpOptions[nx])
                        sel = 'selected="selected"';
                    else
                        sel = '';
                    opt += "<option value='" + p.rpOptions[nx] + "' " + sel + " >" + p.rpOptions[nx]
                            + "&nbsp;&nbsp;</option>";
                }
                $('.pDiv2', g.pDiv).prepend("<div class='pGroup'><select name='rp' style='width:40px'>" + opt
                        + "</select></div> <div class='btnseparator'></div>");
                $('select', g.pDiv).change(function() {
                            if (p.onRpChange) {
                                p.onRpChange(+this.value);
                            } else {
                                p.newp = 1;
                                p.rp = +this.value;
                                g.populate();
                            }
                        });
            }
            // add search button
            if (p.searchitems) {
                $('.pDiv2', g.pDiv)
                        .prepend("<div class='pGroup'> <div class='pSearch pButton'><span></span></div> </div>  <div class='btnseparator'></div>");
                $('.pSearch', g.pDiv).click(function() {
                            $(g.sDiv).slideToggle('fast', function() {
                                        $('.sDiv:visible input:first', g.gDiv).trigger('focus');
                                    });
                        });
                // add search box
                g.sDiv.className = 'sDiv';
                var sitems = p.searchitems;
                var sopt = '', sel = '';
                for (var s = 0; s < sitems.length; s++) {
                    if (p.qtype == '' && sitems[s].isdefault == true) {
                        p.qtype = sitems[s].name;
                        sel = 'selected="selected"';
                    } else {
                        sel = '';
                    }
                    sopt += "<option value='" + sitems[s].name + "' " + sel + " >" + sitems[s].display
                            + "&nbsp;&nbsp;</option>";
                }
                if (p.qtype == '') {
                    p.qtype = sitems[0].name;
                }
                $(g.sDiv).append("<div class='sDiv2'>" + p.findtext + " <input type='text' value='" + p.query
                        + "' size='30' name='q' class='qsbox' /> " + " <select name='qtype'>" + sopt
                        + "</select></div>");
                // Split into separate selectors because of bug in jQuery 1.3.2
                $('input[name=q]', g.sDiv).keydown(function(e) {
                            if (e.keyCode == 13) {
                                g.doSearch();
                            }
                        });
                $('select[name=qtype]', g.sDiv).keydown(function(e) {
                            if (e.keyCode == 13) {
                                g.doSearch();
                            }
                        });
                $('input[value=Clear]', g.sDiv).click(function() {
                            $('input[name=q]', g.sDiv).val('');
                            p.query = '';
                            g.doSearch();
                        });
                $(g.bDiv).after(g.sDiv);
            }
        }
        $(g.pDiv, g.sDiv).append("<div style='clear:both'></div>");
        // add title
        if (p.title) {
            g.mDiv.className = 'mDiv';
            g.mDiv.innerHTML = '<div class="ftitle">' + p.title + '</div>';
            $(g.gDiv).prepend(g.mDiv);
            //是否导出数据
            if (p.expData) {
                /*
                $(g.mDiv).append('<div class="expData expDataLocal" title="导出本页">导出本页</div>');
                $(g.mDiv).append('<div class="expData expDataAll" title="导出全部">导出全部</div>');
                */
            	/*
                $(g.mDiv).append('<div class="expData expDataLocal" title="导出本页"></div>');
                $(g.mDiv).append('<div class="expData expDataAll" title="导出全部"></div>');
                */
            	$(g.mDiv).append('<div class="expData expDataLocal"><input type="button" class="d_button" value="导出本页" /></div>');
                $(g.mDiv).append('<div class="expData expDataAll"><input type="button" class="d_button" value="导出全部"  /></div>');
            	
                $(g.mDiv).append("<div style='display:none'><form id='flexiGridDownForm_"+t.id+"' method='post'><input type='hidden' name='flexiGirdColModel'/></form><div>");
                //下载处理；
                $("div.expData",g.mDiv).click(function(){
                    var rp = p.rp; 
                    var page = p.page; //当前页；
                    var setTimeSeconds = 2000;
                    if($(this).hasClass("expDataAll")){
                      
                      rp = p.maxRp; //如果是导出全部，那么将导出最大记录数设置为提供的参数；默认5000；
                      page = 1;
                      if(p.total>rp){
	                      if(!confirm("查询结果过大，默认导出前"+rp+"条记录，确定导出？")) return;
	                  }
	                  /**/
	                  if(p.total>50&&p.total<200)setTimeSeconds = 4000;
	                  else if(p.total>=200&&p.total<500)setTimeSeconds = 7000;
	                  else if(p.total>=500&&p.total<1000)setTimeSeconds = 10000;
	                  else if(p.total>=1000&&p.total<1500)setTimeSeconds = 12000;
	                  else if(p.total>=1500&&p.total<2000)setTimeSeconds = 14000;
	                  else if(p.total>=2000)setTimeSeconds = 16000;
	                  
	                  /*
	                  if(p.total>50&&p.total<500){
	                  	 setTimeSeconds += p.total*7;
	                  }else if(p.total>=500&&p.total<1000){
	                  	 setTimeSeconds += p.total*6;
	                  }else if(p.total>=1000&&p.total<2000){
	                  	 setTimeSeconds += p.total*5;
	                  }else if(p.total>=2000){
	                  	 setTimeSeconds += p.total*4;
	                  }*/
	                  
                    }
                    
                    /*
                    var param = [{
                                name : p.searchModel ? p.searchModel + '.page'  : 'searchModel.page',
                                value : page
                            }, {
                                name : p.searchModel ? p.searchModel + '.rp' : 'searchModel.rp',
                                value : rp
                            } , {
                                name : p.searchModel ? p.searchModel + '.sortname' : 'searchModel.sortname',
                                value : p.sortname
                            }, {
                                name : p.searchModel ? p.searchModel + '.sortorder' : 'searchModel.sortorder',
                                value : p.sortorder
                            },{
                                name : 'flexiGridType',
                                value : 'FlexiGridExcelDown'
                            }];
                    */
                    //spring
                    var param = [{
                        name : p.searchModel ? p.searchModel + '.page'  : 'page',
                        value : page
                    }, {
                        name : p.searchModel ? p.searchModel + '.rp' : 'rp',
                        value : rp
                    } , {
                        name : p.searchModel ? p.searchModel + '.sortname' : 'sortname',
                        value : p.sortname
                    }, {
                        name : p.searchModel ? p.searchModel + '.sortorder' : 'sortorder',
                        value : p.sortorder
                    },{
                        name : 'flexiGridType',
                        value : 'FlexiGridExcelDown'
                    }];
                    
                    if (p.params) {
                      for (var pi = 0; pi < p.params.length; pi++) {
                        param[param.length] = p.params[pi];
                      }
                    }
                    var url = "";
                    $.each(param,function(i,n){
                        if(i>0){
                            url = url+"&";
                        }
                        url = url+n.name+"="+encodeURI(n.value);
                    })
                    //设置action，其实就是查询的时候，所需要的URL ;
                    //$("#flexiGridDownForm_"+t.id,g.mDiv).attr('action', p.url+"?"+url);
                    $("#flexiGridDownForm_"+t.id,g.mDiv).attr('action',  p.url+"&"+url);
                    //列选择；//序列化
                    if (p.colModel) {
                        $("#flexiGridDownForm_"+t.id+" :hidden[name='flexiGirdColModel']",g.mDiv).val(g.serializeColModel());
                    }
                	$.gridLoading({
                		message : "正在处理..."
                	});
                    $("#flexiGridDownForm_"+t.id,g.mDiv).submit();
                    setTimeout("$.gridUnLoading()",setTimeSeconds);
                    /*
                    //记录导出日志  add by 徐广林
                    $.appAjax({
                    	url:GLOBAL.WEBROOT+"/sys/exportLog!saveExportLog.action",
                    	type:"post",
                    	dataType:"json",
                        data:{
                        	"exportLogBean.menuId":GLOBAL.MENU_ID,
                        	"exportLogBean.tabTitle":p.title,
                        	"exportLogBean.exportNum":p.total>rp?rp:p.total
                        },
                        success:function(data){
//                         	if(data.success){
//                        		alert("导出日志成功！");	
//                        	}
                        	//$.gridUnLoading();
                        	setTimeout("$.gridUnLoading()",setTimeSeconds);
                        },
                        error:function(){
                        	setTimeout("$.gridUnLoading()",setTimeSeconds);
                        }
                    });
                    */
                });
            }
            
            if (p.showTableToggleBtn) {
                $(g.mDiv).append('<div class="ptogtitle" title="Minimize/Maximize Table"><span></span></div>');
                $('div.ptogtitle', g.mDiv).click(function() {
                            $(g.gDiv).toggleClass('hideBody');
                            $(this).toggleClass('vsble');
                        });
            }
        }
        // setup cdrops
        g.cdropleft = document.createElement('span');
        g.cdropleft.className = 'cdropleft';
        g.cdropright = document.createElement('span');
        g.cdropright.className = 'cdropright';
        // add block
        g.block.className = 'gBlock';
        var gh = $(g.bDiv).height();
        var gtop = g.bDiv.offsetTop;
        $(g.block).css({
                    width : g.bDiv.style.width,
                    height : gh,
                    background : 'white',
                    position : 'relative',
                    marginBottom : (gh * -1),
                    zIndex : 1,
                    top : gtop,
                    left : '0px'
                });
        $(g.block).fadeTo(0, p.blockOpacity);
        // add column control
        if ($('th', g.hDiv).length) {
            g.nDiv.className = 'nDiv';
            g.nDiv.innerHTML = "<table cellpadding='0' cellspacing='0'><tbody></tbody></table>";
            $(g.nDiv).css({
                        marginBottom : (gh * -1),
                        display : 'none',
                        top : gtop
                    }).noSelect();
            var cn = 0;
            $('th div', g.hDiv).each(function() {
                var kcol = $("th[axis='col" + cn + "']", g.hDiv)[0];
                if(kcol==null) return;
                var chk = 'checked="checked"';
                if (kcol.style.display == 'none') {
                    chk = '';
                }
                $('tbody', g.nDiv).append('<tr><td class="ndcol1"><input type="checkbox" ' + chk
                        + ' class="togCol" value="' + cn + '" /></td><td class="ndcol2">' + this.innerHTML
                        + '</td></tr>');
                cn++;
            });
            if ($.browser.msie && $.browser.version < 7.0)
                $('tr', g.nDiv).hover(function() {
                            $(this).addClass('ndcolover');
                        }, function() {
                            $(this).removeClass('ndcolover');
                        });
            $('td.ndcol2', g.nDiv).click(function() {
                        if ($('input:checked', g.nDiv).length <= p.minColToggle
                                && $(this).prev().find('input')[0].checked)
                            return false;
                        return g.toggleCol($(this).prev().find('input').val());
                    });
            $('input.togCol', g.nDiv).click(function() {
                        if ($('input:checked', g.nDiv).length < p.minColToggle && this.checked == false)
                            return false;
                        $(this).parent().next().trigger('click');
                    });
            $(g.gDiv).prepend(g.nDiv);
            $(g.nBtn).addClass('nBtn').html('<div></div>').attr('title', 'Hide/Show Columns').click(function() {
                        $(g.nDiv).toggle();
                        return true;
                    });
            if (p.showToggleBtn) {
                $(g.gDiv).prepend(g.nBtn);
            }
        }
        // add date edit layer
        $(g.iDiv).addClass('iDiv').css({
                    display : 'none'
                });
        $(g.bDiv).append(g.iDiv);
        // add flexigrid events
        $(g.bDiv).hover(function() {
                    $(g.nDiv).hide();
                    $(g.nBtn).hide();
                }, function() {
                    if (g.multisel) {
                        g.multisel = false;
                    }
                });
        $(g.gDiv).hover(function() {
                }, function() {
                    $(g.nDiv).hide();
                    $(g.nBtn).hide();
                });
        // add document events
        $(document).mousemove(function(e) {
                    g.dragMove(e)
                }).mouseup(function(e) {
                    g.dragEnd()
                }).hover(function() {
                }, function() {
                    g.dragEnd()
                });
        // browser adjustments
        if ($.browser.msie && $.browser.version < 7.0) {
            $('.hDiv,.bDiv,.mDiv,.pDiv,.vGrip,.tDiv, .sDiv', g.gDiv).css({
                        width : '100%'
                    });
            $(g.gDiv).addClass('ie6');
            if (p.width != 'auto') {
                $(g.gDiv).addClass('ie6fullwidthbug');
            }
        }
        g.rePosDrag();
        g.fixHeight();
        // make grid functions accessible
        t.p = p;
        t.grid = g;
        // load data
        if (p.url && p.autoload) {
            g.populate();
        }
        return t;
    };
    var docloaded = false;
    $(document).ready(function() {
                docloaded = true
            });
    $.fn.flexigrid = function(p) {
        return this.each(function() {
                    if (!docloaded) {
                        $(this).hide();
                        var t = this;
                        $(document).ready(function() {
                                    $.addFlex(t, p);
                                });
                    } else {
                        $.addFlex(this, p);
                    }
                });
    }; // end flexigrid
    
    
    $.fn.flexResize = function(h,w){  
    	 return this.each(function(){    		 
    		 if(w!=undefined){
	    		 if(w=='auto') {
	    			 this.grid.bDiv.style.width ='auto';
	    			 this.p.width = 'auto';
	    		 }else{
	    			 this.grid.bDiv.style.width = w + 'px'
	    			 this.p.width = w;
	    		 }
    		 }
    		 
    		 if(h>this.p.minheight){
    			this.grid.bDiv.style.height = h + 'px'
    		 	this.p.height = h;
    		 }
    	 });
    }
    
    $.fn.flexReload = function(p) { // function to reload grid
        return this.each(function() {
                    if (this.grid && this.p.url)
                        this.grid.populate();
                });
    }; // end flexReload
    $.fn.flexOptions = function(p) { // function to update general options
        return this.each(function() {
                    if (this.grid)
                        $.extend(this.p, p);
                });
    }; // end flexOptions
    $.fn.flexToggleCol = function(cid, visible) { // function to reload grid
        return this.each(function() {
                    if (this.grid)
                        this.grid.toggleCol(cid, visible);
                });
    }; // end flexToggleCol
    $.fn.flexAddData = function(data) { // function to add data to grid
        return this.each(function() {
                    if (this.grid)
                        this.grid.addData(data);
                });
    };
    $.fn.getCheckedObjects = function(){
        var ob = new Array;
        this.each(function() {
            if (this.grid)
                ob = this.grid.getCheckedObjects();
        });
        return ob;
    };
    $.fn.getRowById = function(rowId){
        var ob = null;
        this.each(function() {
            if (this.grid)
              ob = this.grid.getRowDataById(rowId);
        });
        return ob;
    };
    /**
     * 获取选中的ID，返回数组；
     */
    $.fn.getCheckedIds = function(){
        var ids = new Array;
        this.each(function() {
            if (this.grid){
                var obs = this.grid.getCheckedObjects();
                $.each(obs,function(i,n){
                  if(n){
                    ids.push(n.id);
                  }
                })
            }
        });
        return ids;
    }
    
    /**
     * 获取选中的指定字段，返回数组；
     */
    $.fn.getCheckedParams = function(param){
    	
    	var vals = new Array;
    	this.each(function() {
            if (this.grid){
                var obs = this.grid.getCheckedObjects();
                $.each(obs,function(i,n){
                  if(n){
                	  vals.push(n.cell[param]);
                  }
                })
            }
        });
        return vals;
    }
    
    
    /**
     * 根据传入的查询条件，进行数据查询；params要求[{name:"",value:""},{name:"",value:""}]
     */
    $.fn.flexSearch = function(params){
        return this.each(function(){
            this.p.params = new Array;
            //修改，默认调用该方法的时候，翻页到第一页；
            this.p.newp=1;
            $.extend(this.p.params,params);
            this.grid.populate();
        })
    }   

    
    $.fn.noSelect = function(p) { // no select plugin by me :-)
        var prevent = (p == null) ? true : p;
        if (prevent) {
            return this.each(function() {
                        if ($.browser.msie || $.browser.safari)
                            $(this).bind('selectstart', function() {
                                        return false;
                                    });
                        else if ($.browser.mozilla) {
                            $(this).css('MozUserSelect', 'none');
                            $('body').trigger('focus');
                        } else if ($.browser.opera)
                            $(this).bind('mousedown', function() {
                                        return false;
                                    });
                        else
                            $(this).attr('unselectable', 'on');
                    });
        } else {
            return this.each(function() {
                        if ($.browser.msie || $.browser.safari)
                            $(this).unbind('selectstart');
                        else if ($.browser.mozilla)
                            $(this).css('MozUserSelect', 'inherit');
                        else if ($.browser.opera)
                            $(this).unbind('mousedown');
                        else
                            $(this).removeAttr('unselectable', 'on');
                    });
        }
    }; // end noSelect
    
    /**
    * 移除按钮 参数为下标数组 [0,1,2]
    * add by Anna
    */
     $.fn.removeButtons = function(p) {
     	return this.each(function() {
                   //alert(this.grid.tDiv);
                    if (this.grid.tDiv&&$("div.tDiv2")[0]) {
                    	var d_l = $("div.tDiv2").children("div").length;
                        for(var i=0;i<p.length;i++){
                        	var index = p[i];
                        	if(index<d_l){
                        		var d = $("div.tDiv2").children("div")[index];
                        		$(d).remove();
                        	}
                        }
                    }
                });
     };
     
   /**
    * 移除所有按钮 无参数
    * add by Anna
    */
     $.fn.removeAllButtons = function() {
     	 return this.each(function() {
                    if (this.grid.tDiv&&$("div.tDiv2")[0]) {
                    	var ds = $("div.tDiv2").children("div");
                        for(var i=0;i<ds.length;i++){
                        	var d = ds[i];
                        	$(d).remove();
                        }
                    }
                });
     };
     
   /**
    * 添加按钮 参数为按钮数组 [{name : '名称', bclass : 'delete',onpress: function },{separator : true}]
    * add by Anna
    */
     $.fn.addButtons = function(p) {
     	 return this.each(function() {
     	 	if (this.grid.tDiv) {
     	 	 	 if (p) {
		            //this.grid.tDiv.className = 'tDiv';
		            //alert($(this.grid.gDiv).children("div.tDiv")[0]);
		            if(this.grid.tDiv.className != 'tDiv'){
		            	//$(this.grid.gDiv).children("div",this.grid.tDiv).addClass("tDiv");
		            	this.grid.tDiv.className = 'tDiv';
		            	$(this.grid.gDiv).prepend(this.grid.tDiv);
		            }
		            var tDiv2 = $(this.grid.gDiv).children("div",this.grid.tDiv).children("div.tDiv2");
		            if(!tDiv2[0]){
		            	tDiv2 = document.createElement('div');
		            	tDiv2.className = 'tDiv2';
		            	$(this.grid.tDiv).append(tDiv2);
            			$(this.grid.tDiv).append("<div style='clear:both'></div>");
		            }
		            for (var i = 0; i < p.length; i++) {
		                var btn = p[i];
		                if (!btn.separator) {
		                    var btnDiv = document.createElement('div');
		                    btnDiv.className = 'fbutton';
		                    btnDiv.innerHTML = "<div><span>" + btn.name + "</span></div>";
		                    if (btn.bclass)
		                        $('span', btnDiv).addClass(btn.bclass).css({
		                                    paddingLeft : 20
		                                });
		                    btnDiv.onpress = btn.onpress;
		                    btnDiv.name = btn.name;
		                    if (btn.onpress){
		                        $(btnDiv).click(function() {
		                                    this.onpress();
		                                });
		                    }
		                    $(tDiv2).append(btnDiv);
		                    if ($.browser.msie && $.browser.version < 7.0) {
		                        $(btnDiv).hover(function() {
		                                    $(this).addClass('fbOver');
		                                }, function() {
		                                    $(this).removeClass('fbOver');
		                                });
		                    }
		                } else {
		                    $(tDiv2).append("<div class='btnseparator'></div>");
		                }
		            }
		            
		     	 }
     	 	}
     	 
     	 });
     
     };
     //导出本页
     $.fn.flexExpDataLocal = function() {
    	 return this.each(function(){
	    	 var rp = this.p.rp; 
	         var page = this.p.page; //当前页；
	         var setTimeSeconds = 2000;         
	         
	         //spring
	         var param = [{
	             name : this.p.searchModel ? this.p.searchModel + '.page'  : 'page',
	             value : page
	         }, {
	             name : this.p.searchModel ? this.p.searchModel + '.rp' : 'rp',
	             value : rp
	         } , {
	             name : this.p.searchModel ? this.p.searchModel + '.sortname' : 'sortname',
	             value : this.p.sortname
	         }, {
	             name : this.p.searchModel ? this.p.searchModel + '.sortorder' : 'sortorder',
	             value : this.p.sortorder
	         },{
	             name : 'flexiGridType',
	             value : 'FlexiGridExcelDown'
	         }];
	         
	         if (this.p.params) {
	           for (var pi = 0; pi < this.p.params.length; pi++) {
	             param[param.length] = this.p.params[pi];
	           }
	         }
	         var url = "";
	         $.each(param,function(i,n){
	             if(i>0){
	                 url = url+"&";
	             }
	             url = url+n.name+"="+encodeURI(n.value);
	         });
	         if(!this.p.title&&!$("#flexiGridDownForm_"+this.id,this.grid.mDiv)[0]){
	        	 this.grid.mDiv.className = 'mhiDiv';
	             $(this.grid.gDiv).prepend(this.grid.mDiv);
	             $(this.grid.mDiv).append("<div style='display:none'><form id='flexiGridDownForm_"+this.id+"' method='post'><input type='hidden' name='flexiGirdColModel'/></form><div>");
	         }
	         
	         //设置action，其实就是查询的时候，所需要的URL ;
	         //$("#flexiGridDownForm_"+this.id,this.g.mDiv).attr('action', this.p.url+"?"+url);
	         $("#flexiGridDownForm_"+this.id,this.grid.mDiv).attr('action',  this.p.url+"&"+url);
	         //列选择；//序列化
	         if (this.p.colModel) {
	             $("#flexiGridDownForm_"+this.id+" :hidden[name='flexiGirdColModel']",this.grid.mDiv).val(this.grid.serializeColModel());
	         }
	     	$.gridLoading({
	     		message : "正在处理..."
	     	});
	         $("#flexiGridDownForm_"+this.id,this.grid.mDiv).submit();
	         setTimeout("$.gridUnLoading()",setTimeSeconds);
    	 });
     };
     //导出全部
     $.fn.flexExpDataAll = function() {
    	 return this.each(function(){
	    	 var rp = this.p.rp; 
	         var page = this.p.page; //当前页；
	         var setTimeSeconds = 2000;
		       rp = this.p.maxRp; //如果是导出全部，那么将导出最大记录数设置为提供的参数；默认5000；
		       page = 1;
		       if(this.p.total>rp){
		           if(!confirm("查询结果过大，默认导出前"+rp+"条记录，确定导出？")) return;
		       }
		       /**/
		       if(this.p.total>50&&this.p.total<200)setTimeSeconds = 4000;
		       else if(this.p.total>=200&&this.p.total<500)setTimeSeconds = 7000;
		       else if(this.p.total>=500&&this.p.total<1000)setTimeSeconds = 10000;
		       else if(this.p.total>=1000&&this.p.total<1500)setTimeSeconds = 12000;
		       else if(this.p.total>=1500&&this.p.total<2000)setTimeSeconds = 14000;
		       else if(this.p.total>=2000)setTimeSeconds = 16000;
	         //spring
	         var param = [{
	             name : this.p.searchModel ? this.p.searchModel + '.page'  : 'page',
	             value : page
	         }, {
	             name : this.p.searchModel ? this.p.searchModel + '.rp' : 'rp',
	             value : rp
	         } , {
	             name : this.p.searchModel ? this.p.searchModel + '.sortname' : 'sortname',
	             value : this.p.sortname
	         }, {
	             name : this.p.searchModel ? this.p.searchModel + '.sortorder' : 'sortorder',
	             value : this.p.sortorder
	         },{
	             name : 'flexiGridType',
	             value : 'FlexiGridExcelDown'
	         }];
	         
	         if (this.p.params) {
	           for (var pi = 0; pi < this.p.params.length; pi++) {
	             param[param.length] = this.p.params[pi];
	           }
	         }
	         var url = "";
	         $.each(param,function(i,n){
	             if(i>0){
	                 url = url+"&";
	             }
	             url = url+n.name+"="+encodeURI(n.value);
	         })
	         if(!this.p.title&&!$("#flexiGridDownForm_"+this.id,this.grid.mDiv)[0]){
	        	 this.grid.mDiv.className = 'mhiDiv';
	             $(this.grid.gDiv).prepend(this.grid.mDiv);
	             $(this.grid.mDiv).append("<div style='display:none'><form id='flexiGridDownForm_"+this.id+"' method='post'><input type='hidden' name='flexiGirdColModel'/></form><div>");
	         }
	         //设置action，其实就是查询的时候，所需要的URL ;
	         //$("#flexiGridDownForm_"+this.id,this.grid.mDiv).attr('action', this.p.url+"?"+url);
	         $("#flexiGridDownForm_"+this.id,this.grid.mDiv).attr('action',  this.p.url+"&"+url);
	         //列选择；//序列化
	         if (this.p.colModel) {
	             $("#flexiGridDownForm_"+this.id+" :hidden[name='flexiGirdColModel']",this.grid.mDiv).val(this.grid.serializeColModel());
	         }
	     	$.gridLoading({
	     		message : "正在处理..."
	     	});
	         $("#flexiGridDownForm_"+this.id,this.grid.mDiv).submit();
	         setTimeout("$.gridUnLoading()",setTimeSeconds);
    	});
     };
})(jQuery);