(function(b){

	b.removeTips=function(){
		var a=document.getElementById("xiuTipBox");
		if(!a)
		return false;
		a.showTip&&clearTimeout(a.showTip);
		a.hideTip&&clearTimeout(a.hideTip);
		b(a).hide().remove()
	};
	
	b.fn.hideTips=function(){
		var a=document.getElementById("xiuTipBox");
		if(!a)
		return false;
		var h=this;
		a.showTip&&clearTimeout(a.showTip);
		
		a.hideTip=setTimeout(function(){
			b(a).data("showTipObj")===h[0]&&b(a).hide()
		},300)};
		
		b.fn.showTips=function(){
			var a=document.getElementById("xiuTipBox");
			if(!a)
			return false;
			var h=this;
			
			a.hideTip&&clearTimeout(a.hideTip);
			
			a.showTip=setTimeout(function(){
				b(a).data("showTipObj")===h[0]&&b(a).show()
			},300)};
			
			b.fn.tips=function(a){
				var h=this,e=document,g=b.extend({content:"",arrow:"bottom",outlineBox:false},a),c=g.content||h.attr("info");
				var flag = false;
				if($(h).attr("readOnlyFlag") && $(h).attr("readOnlyFlag") == "true"){
					flag = true;
				}
				var content_id = $(h).attr("flag");
				var a_id = $(h).attr("id");
				var desc_a_id = "desc_a_"+a_id;
				var content = flag ? $(h).html() : $("#"+desc_a_id).val();
				a=g.arrow;
				var d=h.offset();
				limitBox=g.outlineBox;
				if(!c||c.split("|")<4)
				return false;
				g=c.split("|");
				c=h.attr("href");
				var f=h.find("img:first").attr("src");
				if(e=e.getElementById("xiuTipBox")){
					if(b(e).data("showTipObj")===this[0]){
						h.showTips();
						return false
					}
					b.removeTips()
				}
				var txArea = "";
				if(flag){
					txArea = '<textarea readonly>'+content+'</textarea>';
				}else{
					txArea = '<textarea id="'+tip_desc_id+'" '+
						'onblur=" $(\'#'+content_id+'\').val($(\'#'+tip_desc_id+'\').val()); $(\'#'+desc_a_id+'\').val($(\'#'+tip_desc_id+'\').val()); " '+
					'>'+content+'</textarea>';
				}
				var tip_desc_id = "tip_"+content_id;
				e=b('<div class="poptip" id="xiuTipBox" style="position:absolute; display:none;">'+
						'<span class="t_bg"></span>'+
						'<span class="b_bg"></span>'+
						'<span class="arrow_'+a+'" id="tipArr"></span>'+
						'<p class="ptitle">'+
						txArea+
						'</p>'+
					'</div>').appendTo(b("body"));
				e.hover(function(){
					h.showTips()
				},function(){
					h.hideTips()
				});
				
				if(typeof h.attr("tipPos")!="undefined"){
					c=h.attr("tipPos").split("|");
					d=c[0];
					g=c[1];
					a=c[2];
					f=c[3];
					m=c[4];
					e.find("#tipImg").css({width:f+"px",height:m+"px"})
				}else{
					c=d.left;
					var l=d.top,o=e.outerHeight(true),k=e.outerWidth(true),m=h.find("img:first").height();
					f=h.find("img:first").width();
					var n=h.outerWidth(true);
					d=g="";
					e.find("#tipImg").css({width:f+"px",height:m+"px"});
					
					if(limitBox){
						var w=limitBox.offset().top,j=limitBox.offset().left,p=j+limitBox.width(),t=w+limitBox.height();
						d=c-k;
						g=l+6;
						a="left";
						if(d<j){
							d=c+n;
							g=l+6;
							a="right"
						}
						
						if(g+e.height()>t||d+k>p)
						
						if(m===220){
							d=c-k;
							g=l-l-e.height()+t-12;
							a="leftH"
						}else{
							d=c-k/2+f/2;
							d=d+k>p?c+n-k:c;
							g=l-o-6;
							a="top";
						if(g<w){
							d=c-k/2+f/2;
							d=d+k>p?c+n-k:c;
							g=l+m+6;a="bottom"
						}
					}
				}else switch(a){
					case 
						"top":d=c-k/2+f/2;
						g=l-o-6;
					break;

					case 
						"bottom":d=c-k/2+f/2;
						g=l+m+6;
					break;

					case 
						"right":d=c+n;
						g=l+6;
					break;

					case 
						"left":d=c-k;
						g=l+6;
					break;

					default:d=c-k/2+f/2;

					g=l-o;

					break
				}
			}
			
		e.css({top:g+"px",left:d+"px"}).find("#tipArr").get(0).className="arrow_"+a;
		b.fn.bgIframe&&e.bgIframe();
		h.attr("tipPos",d+"|"+g+"|"+a+"|"+f+"|"+m);
		e.data("showTipObj",h[0]);
		h.showTips();
		
		return h
	
	}
		
})(jQuery);

//绑定要弹出的层节点
showTipDiv = function(readonly){
	$(".showTip a").hover(function(){
		var a=$(this).parent().parent().parent();
		$(this).tips({
			outlineBox:a
		});
	},function(){
		$(this).hideTips()
	});
};