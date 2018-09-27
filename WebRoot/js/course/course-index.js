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
});


//添加课程
function addCourse(){
    var url =WEB.WEBROOT+"/login.do?loginIndex";
    WEB.openWin({
        id:1,
        title:"设置状态",
        url:url,
        width:'550',
        height:'350',
        onClose:function(){   
        	alert("1111111");
        }
    });
}
