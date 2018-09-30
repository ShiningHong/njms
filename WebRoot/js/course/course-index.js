$(document).ready(function(){
	$("#addimg").mouseout(function(){
        $("#addimg").fadeTo("0",0.15);
    });
    $("#addimg").mouseover(function(){
       $("#addimg").fadeTo("0",0.9);
    });
    $("#addCourse").bind("click",function(){
           addCourse();
    });
    $("#course").bind("click",function(){
    	loadCourse();
    });
     $("#course2").bind("click",function(){
    });
});

//打开窗口
function openMenuTab(option){
    TAB.add(option);//添加右侧菜单
}

//添加课程
function addCourse(){
    var url =WEB.WEBROOT+'/category.do?index';
    WEB.openWin({
        id:5,
        title:"设置状态",
        url:url,
        width:'2000',
        height:'2000',
        top:'0',
        left:'0',
        onClose:function(){   
        }
    });
}

//由当前页签开始跳转
function loadCourse(){
    var url = GLOBAL.WEBROOT + '/category.do?index';
    $.ajax({
        url:url,
        type:'post',
        dataType:'html',
        error: function(XMLHttpRequest,textStatus){
            alert("error:"+XMLHttpRequest.status);
        },
        success:function(result){
            document.write(result);
        }
    });
}

