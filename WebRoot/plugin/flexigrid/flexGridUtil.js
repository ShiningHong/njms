//封装flexgrid
/************增加内容**********************/
//1、把flex设计成自适应的高度
//2、新增鼠标移入某个单元格，弹窗提示功能
//3、移除flexgrid的左右上下全局按钮
//4、绑定鼠标移上去的时候出现该出现滚动条就出现否则鼠标移开的时候隐藏滚动条
/*************列的设置样例*****************/
/*{
	display : "用户名",//列展示名称
	name : "",//列对应数据名
	width : 220,//宽度
	tipShow ： true,//是否鼠标移上去的时候弹窗提示，如果有formatter就可以不用这个
	formatter : function(html, rowdata) {//重写列展示情况
		var bizRequestDemo = rowdata.cell["BIZ_REQUEST_DEMO"];
		var id = rowdata.id;
		return tipStr(bizRequestDemo, id);
	}
}*/
//加载鼠标移入提示文件
document.write('<link type="text/css" rel="stylesheet" href="'+GLOBAL.WEBROOT +'/plugin/tipShow/tipShow.css"/>');
document.write('<script type="text/javascript" src="'+GLOBAL.WEBROOT +'/plugin/tipShow/tipShow.js"></script>');
document.write('<link type="text/css" rel="stylesheet" href="'+GLOBAL.WEBROOT +'/plugin/flexigrid/css/flexigrid.css"/>');
document.write('<script type="text/javascript" src="'+GLOBAL.WEBROOT +'/plugin/flexigrid/js/flexigrid.js"></script>');
//定义一个flexGridUtil工具
var $flexGridUtil = {
		$opt:{
			id:"",
			flexOpt:{
				async: false,
				url : "",
				colModel : [],
				singleSelect : false,// 是否单选
				checkBox : true, // 需要选择框
				usepager : false, // 是否分页
				nowrap : true, // 是否不换行
				useRp : true, // 是否可以动态设置每页显示的结果数
				rp : 15,
				rpOptions : [ 10, 15, 20 ],// 可选择设定的每页结果数
				height : "auto",
				width : "auto",
				autoload : true,
				expData : false,
				params : $.formParams("#id"),
				//searchModel : "bean",
				searchModel:false,
				onSuccessTemp :function(){
					//用户希望成功后执行的方法
				}
			}
		},	
		$init:function(id, opt){
			//初始化flexGrid
			this.$opt.id = id;
			this.$opt.flexOpt = opt;
			//重构列对象
			this.$reConstuctColModelOpt();
			//重构成功执行的方法
			this.$resetOnsuccess();
			//执行请求
			this.$doReqFlex();
		},
		$reConstuctColModelOpt:function(){
			var that = this;
			//重构列对象
			var colModel = this.$opt.flexOpt.colModel;
			for(var i=0; i<colModel.length; i++){
				var col = colModel[i];
				if(!col.tipShow) continue;
				col.formatter = function(html, rowdata) {
					return that.$mouseTipStr(html, rowdata.id, col.width);
				};
				colModel[i] = col;
			}
			this.$opt.flexOpt.colModel = colModel;
		},
		$resetOnsuccess:function(){
			//重构成功执行的方法
			var that = this;
			//重写flex加载成功后执行的方法
			//重写成功后执行的方法
			this.$opt.flexOpt.onSuccess = function(){
				//移除flexgrid的左右上下全局按钮
				that.$removeH_VGrip();
				//绑定描述输入框以弹出层的方式编辑
				that.$showTipDiv();
				//动态设置flexgrid的高度
				that.$resetFlexGridBDivHeight();
				//回调用户的成功执行方法
				if(that.$opt.flexOpt.onSuccessTemp)
					that.$opt.flexOpt.onSuccessTemp();
				$(".flexigrid>.bDiv").css({"overflow-x":"hidden", "overflow-y":"hidden"});
				//绑定鼠标移上去的时候出现该出现滚动条就出现否则鼠标移开的时候隐藏滚动条
				$(".flexigrid>.bDiv").bind("mouseout", function(){
					$(".flexigrid>.bDiv").css({"overflow-x":"hidden", "overflow-y":"hidden"});
				});
				$(".flexigrid>.bDiv").bind("mouseover", function(){
					$(".flexigrid>.bDiv").css({"overflow-x":"auto", "overflow-y":"auto"});
				});
			};
		},
		$doReqFlex:function(){
			//执行请求
			$("#"+this.$opt.id).flexigrid(this.$opt.flexOpt);
		},
		$removeH_VGrip:function(){
			//移除flexgrid的左右上下全局按钮
			$(".flexigrid").find(".vGrip").empty().remove();
			$(".flexigrid").find(".hGrip").empty().remove();
		},
		$resetFlexGridBDivHeight:function(){
			//动态设置flexgrid的高度
			var bDivObj = $(".flexigrid").find(".bDiv");
			var trObjs = $("#"+this.$opt.id+">tbody").find("tr");
			var trLen = trObjs.length;
			var height = 0;
			if(trLen > 0){
				for(var i=0; i<trLen; i++){
					height = height + 21;
				}
			}
			//如果有设置高度的限制，一旦达到这个高度，就不实现自适应，否则自适应
			var flexGridHeight = this.$opt.flexOpt.height;
			if(flexGridHeight !== "auto"){
				flexGridHeight = parseInt(flexGridHeight);
				if(height > flexGridHeight) return;
			}
			if(trLen > 1)
				height = height + trLen*7.5;
			$(bDivObj).css({"height":height+20+"px", "overflow":"hidden", "overflow-x":"auto", "margin":"0", "padding":"0"});
			$($(bDivObj).find("table")).css({"height":height+"px"});
		},
		$showTipDiv:function(){
			//绑定描述输入框以弹出层的方式编辑
			showTipDiv();
		},
		$mouseTipStr:function(html, id, wth){
			html = html == "&nbsp;" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : html;
			//弹窗提示
			var str = 
				'<p class="picList" style="margin:0px; padding:0px;">'+
					'<ul class="showTip">'+
						'<li class="p" '+
							'style="width:'+wth+'px"; overflow:hidden;">'+
							'<a style="width:280px;" readOnlyFlag="true" flag="desc_'+id+'" id="'+id+'" href="javascript:void(0);" info="\'\'|\'\'|\'\'">'+html+'</a>'+
						'</li>'+
					'</ul>'+
				'</p>';
			return str;
		}
};

