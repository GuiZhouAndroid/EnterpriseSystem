$(document).ready(function() {
					$(".com").load("com");
					// 检索信息
					$("#search_info").click(searchInfo);
					// 数据导出
					$("#dataImport").click(dataImport);
					// 绑定双击事件
					$("#tt")
							.datagrid(
									{
										onDblClickRow : function(rowIndex) {
											var start = calecon($("#start")
													.val().trim());
											var end = calecon($("#end").val()
													.trim());
											$(".layer").show();
											$(".opacity_bg").show();
											$("#tt").datagrid("selectRow",
													rowIndex);
											var currentRow = $("#tt").datagrid(
													"getSelected");
											var empName = currentRow.name;
											$
													.ajax({
														url : "record/detailedInfo.io",
														type : "get",
														dataType : "json",
														data : {
															"empName" : empName,
															"start" : start,
															"end" : end
														},
														success : function(
																result) {
															delTr();
															if (result.state == 1) {
																var dataRes = result.data;

																for (var i = 0; i < dataRes.length; i++) {
																	create(
																			dataRes[i].dates,
																			dataRes[i].workMorn,
																			dataRes[i].atNoon,
																			dataRes[i].workAfter,
																			dataRes[i].atNight);
																}

															}
														},
														error : function() {
															alert("查看失败");
														}
													});

											// 取消选中
											$("#tt").datagrid("unselectRow",
													rowIndex);
										}
									});
					// 关闭显示以及背景层
					$("#close_show").click(function() {
						$(".layer").hide();
						$(".opacity_bg").hide();
					});
					// 获取部门信息
					queryDept();
					// 限定日历不能选取今天之后的日期
					dateValid();
				})

// 检索信息
function searchInfo() {
	var name = $("#name").val();
	var dept = $("#dept").combobox("getText");
	// 需要转化日期格式且转换为Date对象
	var start = calecon($("#start").val().trim());
	;
	var end = calecon($("#end").val().trim());
	// 判断检索条件
	if (dept != "" && $("#start").val().trim() != ""
			&& $("#end").val().trim() != "" && end >= start) {
		// 清空表格数据
		deleteDataGrid();
		$.ajax({
			url : "attend/statistic.io",
			type : "get",
			dataType : "json",
			data : {
				"empName" : name,
				"empDept" : dept,
				"start" : start,
				"end" : end
			},
			success : function(result) {
				var data = result.data;
				if (result.state == 1) {

					for (var i = 0; i < data.length; i++) {
						createTr(data[i].empName, data[i].dept, data[i].days,
								data[i].hours, data[i].late, data[i].overTime,
								data[i].earlyRetr);
					}
				} else {
					createTr("没有查询到该数据");
				}

			},
			error : function() {
				alert("查询出错");
			}
		});
	}
}
// 动态插入返回的数据
function createTr(name, dept, time, hours, late, overTime, early) {
	if (name != "没有查询到该数据") {
		$('#tt').datagrid('insertRow', {
			row : {
				name : name,
				dept : dept,
				time : time + "天",
				hours : hours + "小时",
				late : late + "次",
				leaveEarly : early + "次",
				overTime : overTime + "小时",
				total : (hours + overTime) + "小时"
			}
		});
	} else {
		$('#tt').datagrid('insertRow', {
			row : {
				name : name,
				dept : "",
				time : "",
				hours : "",
				late : "",
				leaveEarly : "",
				overTime : "",
				total : ""
			}
		});
	}
}
/*
 * 数据导出
 */
function dataImport() {
	var data = "";
	var DATA = "";
	var name = $("#name").val();
	var dept = $("#dept").combobox("getText");
	// 需要转化日期格式且转换为Date对象
	var start = calecon($("#start").val().trim());
	var end = calecon($("#end").val().trim());
	if (dept != "" && $("#start").val().trim() != ""
		&& $("#end").val().trim() != "" && end >= start){
		
		location.href ="attend/downloadExcel.io?name=" + name
		+ "&dept=" + dept + "&start=" + start + "&end=" + end;
	}
}
// 创建tr
function create(dates, morn, noon, after, nigth) {
	var tr = "<tr>";
	tr += "<td>" + dates + "</td>";
	tr += "<td>" + morn + "</td>";
	tr += "<td>" + noon + "</td>";
	tr += "<td>" + after + "</td>";
	tr += "<td>" + nigth + "</td>";
	tr += "</tr>";
	var $tr = $(tr);
	$(".tab_t").append($tr);
}
// 删除表格行
function delTr() {
	// 获取元表格数据，逐行删除
	var data = $(".tab_t").find("tr");
	for (var i = 1; i < data.length; i++) {
		data[i].remove();
	}
}
