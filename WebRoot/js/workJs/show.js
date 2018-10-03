$(function() {
    // 查询列表数据初始化

    initFlexiGrid();
    //查询
    $("#qryBtn").bind("click", function() {
        query();
    });
    // 清空
    $("#resetBtn").click(function() {
        resetForm('queryForm');
    });
    //导出全部
    $("#expDataAllBtn").bind("click",function(){
        $("#listTable").flexExpDataAll();
    });
});

var colModels = [ {
    "display" : "姓名",
    "name" : "STUDENTNAME",
    "width" : 160
}, {
    "display" : "学号",
    "name" : "STUDENTNUM",
    "width" : 160
},{
    "display" : "班级",
    "name" : "CLASSID",
    "width" : 160
},{
	"display" : "提交状态",
    "name" : "SUBMITSTATUS",
    "width" : 160
},{
        name:"SET_UP",
        display:"设置",
        width : 200,
        align : 'center',
        formatter : function(html, rowdata) {
            return [ '<a href="javascript:update( \'', rowdata.cell.NAME, '\')">', '设置', '</a>' ].join('');
        }
    }
];

function update(regionid){
    alert(regionid);
}

var initFlexiGrid = function() {
    $("#listTable")
            .flexigrid(
                    {
                        url : GLOBAL.WEBROOT+ "/Corrections.do?selectFlexgridByExample",
                        colModel : colModels,
                        // title : "查询结果",
                        checkBox : true, // 需要选择框
                        striped : true,
                        usepager : true, // 是否分页
                        nowrap : true, // 是否不换行
                        useRp : true, // 是否可以动态设置每页显示的结果数
                        rp : 10,
                        rpOptions : [ 10, 15, 20 ],// 可选择设定的每页结果数
                        height : "auto",
                        maxheight : "680",// 最大高度
                        autoload : true,
                        expData : false,// 是否导出
                        params : $.formParams("#queryForm"),
                        singleSelect : false,
                        showRowNum : false,
                        onSuccess:function(){
                            resizeH();
                         }
                    });
};
//表格自适应的处理
function resizeH(){
    var reportPageHeight= $(window.parent.document).find("#tabDiv .panel-body.panel-body-noheader.panel-body-noborder").height();
    var fieldSetHeight=$("fieldset").height();
    var pDivHeight=$(".pDiv").height();
    var hDivHeight=$(".hDiv").height();
    var pageHeight=reportPageHeight-fieldSetHeight-pDivHeight-hDivHeight-16;
    $("#listTable").flexResize(pageHeight);
}
function query() {
    $("#listTable").flexSearch($.formParams("#queryForm"));
}
