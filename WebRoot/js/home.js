$(function(){
    var height_w = document.body.clientHeight-$('#headerDiv').height();
    var width_w = $('#menuDiv').width();
    var width_c = document.body.clientWidth-width_w;
  
    $('body').layout('resize');
    
    var url = GLOBAL.WEBROOT + '/login.do?corseUI';
    $.ajax({
        url:url,
        type:'post',
        dataType:'html',
        error: function(XMLHttpRequest,textStatus){
            alert("error:"+XMLHttpRequest.status);
        },
        success:function(result){
            $('#ablane').html(result);
        }
    });

});

//点击退出系统
function doLoginOut(){
    $.messager.confirm('退出提示', '确定退出系统？', function(r){
        if (r){
                    window.onunload = null;
                    window.location.href= GLOBAL.WEBROOT+"/login.jsp";
        }
    });
}

function loadMenuTree(){
    var url = GLOBAL.WEBROOT + '/login.do?loginLeftIndex';
    $.ajax({
        url:url,
        type:'post',
        dataType:'html',
        error: function(XMLHttpRequest,textStatus){
            alert("error:"+XMLHttpRequest.status);
        },
        success:function(result){
            $('#lmenuDiv').html(result);
            openMenuTab({title:'首页',url:'/login.do?corseUI','menuId':'3','scrollable':'no'});
        }
    });
}

//打开窗口
function openMenuTab(option){
    TAB.add(option);//添加右侧菜单
}


