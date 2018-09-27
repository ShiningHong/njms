/**
 * datagrid的excel导出方法扩展
 * 
 * @param {Object}
 *            jq
 * @param {Object}
 *            title:导出的excel的sheet的名字 gridType:表示了数据列表的类型,取值为datagrid或是treegrid
 *            用法: var data = $('#dg').datagrid('getExcelXml', { title:
 *            'exports','exports',gridType:gridType}); var form=$("<form>");//定义一个form表单
 *            form.attr("style","display:none"); form.attr("id","export_Form");
 *            form.attr("target",""); form.attr("method","post");
 *            form.attr("action","reportController.do?exports&title=exports");
 *            var input=$("<input>"); input.attr("type","hidden");
 *            input.attr("name","exportData"); input.attr("value",_data);
 *            $("body").append(form);//将表单放置在page中 form.append(input);
 *            form.submit();//表单提交 form=$("#export_Form").remove();
 */
$.extend($.fn.datagrid.methods,{
					getExcelXml : function(jq, param) {
						var worksheet = this.createWorkSheet(jq, param);
						var totalWidth = 0;
						var cfs = $(jq).datagrid("getColumnFields");
						for (var i = 0; i < cfs.length; i++) {
							totalWidth += $(jq).datagrid("getColumnOption",
									cfs[i]).width;
						}
						/*
						return "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
								+ "<ss:Workbook xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">"
								+ "<o:DocumentProperties><o:Title>"
								+ param.title
								+ "</o:Title></o:DocumentProperties>"
								+ "<ss:ExcelWorkbook>"
								+ "<ss:WindowHeight>"
								+ worksheet.height
								+ "</ss:WindowHeight>"
								+ "<ss:WindowWidth>"
								+ worksheet.width
								+ "</ss:WindowWidth>"
								+ "<ss:ProtectStructure>False</ss:ProtectStructure>"
								+ "<ss:ProtectWindows>False</ss:ProtectWindows>"
								+ "</ss:ExcelWorkbook>"
								+ "<ss:Styles>"
								+ "<ss:Style ss:ID=\"Default\">"
								+ "<ss:Alignment ss:Vertical=\"Top\"  />"
								+ "<ss:Font ss:FontName=\"arial\" ss:Size=\"10\" />"
								+ "<ss:Borders>"
								+ "<ss:Border  ss:Weight=\"1\" ss:LineStyle=\"Continuous\" ss:Position=\"Top\" />"
								+ "<ss:Border  ss:Weight=\"1\" ss:LineStyle=\"Continuous\" ss:Position=\"Bottom\" />"
								+ "<ss:Border  ss:Weight=\"1\" ss:LineStyle=\"Continuous\" ss:Position=\"Left\" />"
								+ "<ss:Border ss:Weight=\"1\" ss:LineStyle=\"Continuous\" ss:Position=\"Right\" />"
								+ "</ss:Borders>"
								+ "<ss:Interior />"
								+ "<ss:NumberFormat />"
								+ "<ss:Protection />"
								+ "</ss:Style>"
								+ "<ss:Style ss:ID=\"title\">"
								+ "<ss:Borders />"
								+ "<ss:Font />"
								+ "<ss:Alignment  ss:Vertical=\"Center\" ss:Horizontal=\"Center\" />"
								+ "<ss:NumberFormat ss:Format=\"@\" />"
								+ "</ss:Style>"
								+ "<ss:Style ss:ID=\"headercell\">"
								+ "<ss:Font ss:Bold=\"1\" ss:Size=\"10\" />"
								+ "<ss:Alignment  ss:Horizontal=\"Center\" />"
								+ "<ss:Interior ss:Pattern=\"Solid\"  />"
								+ "</ss:Style>"
								+ "<ss:Style ss:ID=\"even\">"
								+ "<ss:Interior ss:Pattern=\"Solid\"  />"
								+ "</ss:Style>"
								+ "<ss:Style ss:Parent=\"even\" ss:ID=\"evendate\">"
								+ "<ss:NumberFormat ss:Format=\"yyyy-mm-dd\" />"
								+ "</ss:Style>"
								+ "<ss:Style ss:Parent=\"even\" ss:ID=\"evenint\">"
								+ "<ss:NumberFormat ss:Format=\"0\" />"
								+ "</ss:Style>"
								+ "<ss:Style ss:Parent=\"even\" ss:ID=\"evenfloat\">"
								+ "<ss:NumberFormat ss:Format=\"0.00\" />"
								+ "</ss:Style>"
								+ "<ss:Style ss:ID=\"odd\">"
								+ "<ss:Interior ss:Pattern=\"Solid\"  />"
								+ "</ss:Style>"
								+ "<ss:Style ss:Parent=\"odd\" ss:ID=\"odddate\">"
								+ "<ss:NumberFormat ss:Format=\"yyyy-mm-dd\" />"
								+ "</ss:Style>"
								+ "<ss:Style ss:Parent=\"odd\" ss:ID=\"oddint\">"
								+ "<ss:NumberFormat ss:Format=\"0\" />"
								+ "</ss:Style>"
								+ "<ss:Style ss:Parent=\"odd\" ss:ID=\"oddfloat\">"
								+ "<ss:NumberFormat ss:Format=\"0.00\" />"
								+ "</ss:Style>"
								+ "</ss:Styles>"
								+ worksheet.xml + "</ss:Workbook>";
								*/
						return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
								+"<?mso-application progid=\"Excel.Sheet\"?>"
								+"<!DOCTYPE html><Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\""
								+" xmlns:o=\"urn:schemas-microsoft-com:office:office\""
								+" xmlns:x=\"urn:schemas-microsoft-com:office:excel\""
								+" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\""
								+" xmlns:html=\"http://www.w3.org/TR/REC-html40\">"
								+" <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">"
								+"  <Author>gdbi</Author>"
								+"  <LastAuthor>gdbi</LastAuthor>"
								+"  <Created>"+(new Date())+"</Created>"
								+"  <Version>15.00</Version>"
								+" </DocumentProperties>"
								+" <ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">"
								+"  <WindowHeight>"+worksheet.height+"</WindowHeight>"
								+"  <WindowWidth>"+worksheet.width+"</WindowWidth>"
								+"  <WindowTopX>0</WindowTopX>"
								+"  <WindowTopY>0</WindowTopY>"
								+"  <ProtectStructure>False</ProtectStructure>"
								+"  <ProtectWindows>False</ProtectWindows>"
								+" </ExcelWorkbook>"
								+" <Styles>"
								+"  <Style ss:ID=\"Default\" ss:Name=\"Normal\">"
								+"   <Alignment ss:Vertical=\"Center\"/>"
								+"   <Borders/>"
								+"   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"11\" ss:Color=\"#000000\"/>"
								+"   <Interior/>"
								+"   <NumberFormat/>"
								+"   <Protection/>"
								+"  </Style>"
								+"  <Style ss:ID=\"headercell\">"
								+"   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>"
								+"   <Borders>"
								+"    <Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"
								+"    <Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"
								+"    <Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"
								+"    <Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"
								+"   </Borders>"
								+"   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"11\" ss:Color=\"#000000\""
								+"    ss:Bold=\"1\"/>"
								+"  </Style>"
								+"  <Style ss:ID=\"datacell\">"
								+"  <Alignment ss:Horizontal=\"Right\" ss:Vertical=\"Center\"/>"
								+"   <Borders>"
								+"    <Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"
								+"    <Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"
								+"    <Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"
								+"    <Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"
								+"   </Borders>"
								+"  </Style>"
								+" </Styles>"
								+ worksheet.xml
								+"</Workbook>";
					},
					createWorkSheet : function(jq, param) {
						var cellType = [];
						var cellTypeClass = [];
						var totalWidthInPixels = 0;
						var colXml = '';
						var headerXml = '';
						var visibleColumnCountReduction = 0;
						var cfs = $(jq).datagrid('getColumnFields');
						var colCount = cfs.length;
						var fzCols = $(jq).datagrid('getColumnFields',true);
						
						for(var i = 0; i < param.header.length; i ++){
							var _rw = param.header[i];
							headerXml += '<Row>';
							for(var j = 0 ; j < _rw.length; j ++){
								var _col = _rw[j];
								var w = _col.width;
								totalWidthInPixels += w||0;
								if(_col.hidden){
									cellType.push("None");
									cellTypeClass.push("");
									++visibleColumnCountReduction;
								}else{
									//colXml += '<Column ss:AutoFitWidth="1" ss:Width="80" ss:StyleID="datacell"/>';
									//if(i==0)alert(_col.field);
									var _sIndex = _col.sIndex ? ' ss:Index="' + _col.sIndex + '"' : '';
									headerXml += '<Cell ss:StyleID="headercell"'
											+ (_col.rowspan && _col.rowspan > 1 ? ' ss:MergeDown="' + (_col.rowspan-1) + '"' : '')
											+ (_col.colspan && _col.colspan > 1 ? ' ss:MergeAcross="' + (_col.colspan-1) + '"' : '')
											+ (_sIndex!='' ? _sIndex : '')
											+ '><Data ss:Type="String">'
											+ _col.title
											+ '</Data>'
											+ '</Cell>';
									cellType.push("String");
									cellTypeClass.push("");
								}
							}
							headerXml += '</Row>';
						}
						var visibleColumnCount = cellType.length
								- visibleColumnCountReduction;
						var result = {
							height : 9000,
							width : Math.floor(totalWidthInPixels * 30) + 50
						};
						var rows = [];
						if (param.gridType == 'datagrid')
							rows = $(jq).datagrid('getRows');
						else
							rows = $(jq).treegrid('getData');
						var t = '<Worksheet ss:Name="'+param.title+'">'
								+ '<Table ss:StyleID="datacell" x:FullRows="1" x:FullColumns="1"'
								+ ' ss:ExpandedColumnCount="'
								+ (visibleColumnCount + 2)
								+ '" ss:ExpandedRowCount="' + (rows.length + param.header.length)
								+ '">' + colXml
								+ headerXml;
						for (var i = 0, it = rows, l = it.length; i < l; i++) {
							t += '<Row ss:AutoFitHeight="1">';
							//var cellClass = (i & 1) ? 'odd' : 'even';
							r = it[i];
							var k = 0;
							
							if(fzCols.length){
								//处理冻结列数据
								for (var j = 0; j < fzCols.length; j++) {
									if (fzCols[j] != '') {
										var v = r[fzCols[j]];
										if (cellType[k] !== "None") {
											t += '<Cell ss:StyleID="datacell"><Data ss:Type="'
													+ cellType[k] + '">';
											if (cellType[k] == 'DateTime') {
												t += v.format('Y-m-d');
											} else {
												t += v;
											}
											t += '</Data></Cell>';
										}
										k++;
									}
								}
							}
							k = 0;
							for (var j = 0; j < colCount; j++) {
								if (cfs[j] != '') {
									var v = r[cfs[j]];
									if (cellType[k] !== "None") {
										var _st = cellType[k];
										if(!isNaN(v)){
											_st = "Number";
										}
										t += '<Cell ss:StyleID="datacell"><Data ss:Type="'
												+ cellType[k] + '">';
										if (cellType[k] == 'DateTime') {
											t += v.format('Y-m-d');
										} else {
											t += v;
										}
										t += '</Data></Cell>';
									}
									k++;
								}
							}
							t += '</Row>';
						}
						result.xml = t
								+ '</Table>'
								+ '<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">'
								+ '<PageSetup>'
								+ ' <Header x:Margin=\"0.3\"/>'
								+ ' <Footer x:Margin=\"0.3\"/>'
								+ ' <PageMargins x:Bottom=\"0.75\" x:Left=\"0.7\" x:Right=\"0.7\" x:Top=\"0.75\"/>'
								+ '</PageSetup>'
								+ '<Print>'
								+ ' <ValidPrinterInfo/>'
								+ ' <PaperSizeIndex>9</PaperSizeIndex>'
								+ ' <HorizontalResolution>600</HorizontalResolution>'
								+ ' <VerticalResolution>600</VerticalResolution>'
								+ '</Print>'
								+ '<Panes>'
								+ ' <Pane>'
								+ '  <Number>3</Number>'
								+ '  <ActiveRow>12</ActiveRow>'
								+ '  <ActiveCol>4</ActiveCol>'
								+ ' </Pane>'
								+ '</Panes>'
								+ '<ProtectObjects>False</ProtectObjects>'
								+ '<ProtectScenarios>False</ProtectScenarios>'
								+ '</WorksheetOptions></Worksheet>';
						return result;
					}
				});

/**
 * datagrid的自动对行的某些列进行行合并
 * 
 * @param {Object}
 *            jq
 * @param {Object}
 *            fields:表示被合并的列的字段名,可以同时对多个列进行合并
 *            gridType:表示了数据列表的类型,取值为datagrid或是treegrid 用法:
 *            $('#dg').datagrid('autoMergeCells', { fields:
 *            ['exports',...],gridType:'datagrid'});
 */
$.extend($.fn.datagrid.methods, {
	autoMergeCells : function(jq, params) {
		return jq.each(function() {
			var target = $(this);
			if (!params.fields) {
				fields = target.datagrid("getColumnFields");
			} else {
				fields = params.fields;
			}
			if (params.gridType == "datagrid")
				var rows = target.datagrid("getRows");
			else
				var rows = target.treegrid("getData");
			var i = 0, j = 0, temp = {};
			for (; i < rows.length; i++) {
				var row = rows[i];
				j = 0;
				for (; j < fields.length; j++) {
					var field = fields[j];
					var tf = temp[field];
					if (!tf) {
						tf = temp[field] = {};
						tf[row[field]] = [ i ];
					} else {
						var tfv = tf[row[field]];
						if (tfv) {
							tfv.push(i);
						} else {
							tfv = tf[row[field]] = [ i ];
						}
					}
				}
			}
			$.each(temp, function(field, colunm) {
				$.each(colunm, function() {
					var group = this;
					if (group.length > 1) {
						var before, after, megerIndex = group[0];
						for (var i = 0; i < group.length; i++) {
							before = group[i];
							after = group[i + 1];
							if (after && (after - before) == 1) {
								continue;
							}
							var rowspan = before - megerIndex + 1;
							if (rowspan > 1) {
								target.datagrid('mergeCells', {
									index : megerIndex,
									field : field,
									rowspan : rowspan
								});
							}
							if (after && (after - before) != 1) {
								megerIndex = after;
							}
						}
					}
				});
			});
		});
	}
});

$.extend($.fn.combobox.methods, {
	createCheckAllBlock : function(jq, params) {
		var body = jq.combobox('panel').panel("body");
		var target = $(body[0]);
		body[0].innerHTML="<div class='gcombo'><a href='javascript:void(0);' class='checkAll'>全选</a><a href='javascript:void(0);' style='margin-left:20px;' class='uncheckAll'>全不选</a></div>" + body[0].innerHTML;
		//全选事件
		target.find(".checkAll").live("click",function(){
			$(this).closest(".panel-body").find(".combobox-item").addClass("combobox-item-selected").find("input[type=checkbox]").attr("checked","true");
			var ds = jq.combobox("getData");
			var as = [];
			for(var i = 0; i < ds.length ;i ++){
				as.push(ds[i].VALUE);
			}
			jq.combobox("setValues",as);
			jq.combobox('setText',jq.combobox("getValues").length+'项已选中');
		});
		//全反选事件
		target.find(".uncheckAll").live("click",function(){
			$(this).closest(".panel-body").find(".combobox-item").removeClass("combobox-item-selected").find("input[type=checkbox]").removeAttr("checked");
			jq.combobox("setValues",[]);
			jq.combobox('setText',jq.combobox("getValues").length+'项已选中');
		});
		
		//给分组注册事件
		target.find(".combobox-group").live("click",function(){
			var s = $(this).index();
			var e = $(this).nextAll(".combobox-group:first").index();
			var os = jq.combobox("getValues");
			var ds = jq.combobox("getData");
			var pos = target.find(".combobox-group").index($(this)) + 1;
			var cs = [];
			if(e > 0){
				for(var i = s-pos; i < e-pos-1; i ++){
					cs.push(ds[i].VALUE);
				}
				if(target.find("div:lt("+e+"):gt("+s+").combobox-item-selected").length > 0){
					//target.find("div:lt("+e+"):gt("+s+")").removeClass("combobox-item-selected");
					var ns1 = os.slice(0);
					for(var j = os.length-1; j >=0; j --){
						if(cs.contains(os[j])){
							ns1.splice(j,1);
						}
					}
					jq.combobox("setValues",ns1);
					jq.combobox('setText',jq.combobox("getValues").length+'项已选中');
					target.find("div:lt("+e+"):gt("+s+") input[type=checkbox]").removeAttr("checked");
				}else{
					//target.find("div:lt("+e+"):gt("+s+")").addClass("combobox-item-selected");
					var ns1 = os.slice(0);
					for(var j = cs.length-1; j >=0; j --){
						ns1.push(cs[j]);
					}
					jq.combobox("setValues",ns1);
					jq.combobox('setText',jq.combobox("getValues").length+'项已选中');
					target.find("div:lt("+e+"):gt("+s+") input[type=checkbox]").attr("checked","true");
				}
			}else{
				for(var i = s-pos; i < ds.length; i ++){
					cs.push(ds[i].VALUE);
				}
				if($(this).nextAll(".combobox-item-selected").length > 0){
					//$(this).nextAll().removeClass("combobox-item-selected");
					var ns1 = os.slice(0);
					for(var j = os.length-1; j >=0; j --){
						if(cs.contains(os[j])){
							ns1.splice(j,1);
						}
					}
					jq.combobox("setValues",ns1);
					jq.combobox('setText',jq.combobox("getValues").length+'项已选中');
					$(this).nextAll().find("input[type=checkbox]").removeAttr("checked");
				}else{
					//$(this).nextAll().addClass("combobox-item-selected");
					var ns1 = os.slice(0);
					for(var j = cs.length-1; j >=0; j --){
						ns1.push(cs[j]);
					}
					jq.combobox("setValues",ns1);
					jq.combobox('setText',jq.combobox("getValues").length+'项已选中');
					$(this).nextAll().find("input[type=checkbox]").attr("checked","true");
				}
			}
		});
	}
});