var layer;
var laypage;
$(document).ready(function() {
	$(".com").load("com.html");

	// 删除
	$("#tab").on("click", ".delete", delDept);
	// 查询
	queryDept();
	// 修改
	$("#tab").on("click", ".update", updateDept);
	// 取消
	$("#close").click(function() {
		$(".show").hide();
		$(".opacity_bg").hide();
	});
	// 确定
	$("#sureUP").click(sureUp);
})

/*
 * 删除部门
 * 
 */
function delDept() {
	var $tr = $(this).parent().parent();
	var id = $tr.data("id");
	if (id != "") {
		$.ajax({
			url : "dept/delDept.io",
			type : "delete",
			dataType : "json",
			data : {
				"id" : id
			},
			success : function(result) {
				if (result.state == 1) {
					$tr.remove();
					queryDept();
				}
			},
			error : function() {
				alert("删除失败");
			}
		});
	}

}
/*
 * 查询部门
 */
function queryDept() {
	var count = 0;
	$.ajax({
		url : "dept/queryCount.io",
		type : "get",
		async : false,
		success : function(result) {
			count = result.count;
		},
		error : function() {
			alert("查询总数出错");
		}
	});

	layui.use([ 'form', 'layer', 'table', 'laypage' ], function() {
		layer = layui.layer;
		laypage = layui.laypage;


		if (count > 0) {
			laypage
					.render({
						elem : 'demo1',
						count : count,// 数据总数,
						groups : 4,
						jump : function(obj) {
							var str = "第"
									+ ((obj.curr - 1) * 10 + 1)
									+ "条到第"
									+ (obj.curr * 10 > obj.count ? obj.count
											: obj.curr * 10) + "条，共"
									+ (obj.count) + "条";
							$("#countRed").text(str);
							paging(obj.curr);
						}
					});
		}
		if(count==0){
			layer.msg("没有部门记录");
		}

	})

	
}
function paging(page){
	$.ajax({
		url : "dept/queryDept.io",
		type : "get",
		dataType:"json",
		data:{"page":page},
		success : function(result) {
			if (result.state == 1) {
				delTr();
				var data = result.data;
				for (var i = 0; i < data.length; i++) {
					createTr(i, data[i]);
				}
			}
		},
		error : function() {
			alert("查询部门失败");
		}
	});
}

/*
 * 修改部门
 */
function updateDept() {
	 var id=$(this).parent().parent().data("id");
	if(id!=""&&id!=null) window.location.href="updateDept.io?id="+id;
//	var $tr = $(this).parent().parent();
//	var deptName = $tr.find("th").eq(1).text().trim();
//	var remark = $tr.find("th").eq(2).text().trim();
//	$("#upDept").val(deptName);
//	$(".remark").val(remark);
//	$(".show").show();
//	$(".opacity_bg").show();
//	$("#sureUP").data("tr", $tr);
}
/*
 * 修改确定
 */
function sureUp() {
	var deptName = $("#upDept").val().trim();
	var remark = $(".remark").val().trim();
	var $tr = $(this).data("tr");
	var id = $tr.data("id");
	if (deptName == "" || deptName == null) {
		layer.msg("部门名称不能为空");
		return false;
	}
	if (deptName.length < 2) {
		layer.msg("部门名称不能小于2");
		return false;
	}
	if (remark == "" || remark == null) {
		layer.msg("备注内容不能为空");
		return false;
	}
	if (id != "") {
		$.ajax({
			url : "dept/upDept.io",
			type : "put",
			dataType : "json",
			data : {
				"id" : id,
				"deptName" : deptName,
				"remark" : remark
			},
			success : function(result) {
				if (result.state == 1) {
					$tr.find("th").eq(1).text(deptName);
					$tr.find("th").eq(2).text(remark);
					layer.msg("修改部门成功");
				}
			},
			error : function() {
				alert("修改部门名称失败");
			}
		});
		$(".show").hide();
		$(".opacity_bg").hide();
	}

}
/*
 * 动态创建tr
 */
function createTr(num, data) {
	var state = 0;
	$.ajax({
		url : "dept/authorityUrl.io",
		type : "get",
		async : false,
		dataType : "json",
		data : {
			"url" : "deptauthority"
		},
		success : function(result) {
			state = result.state;
		},
		error : function() {
			alert("权限url查询失败");
		}
	});
	var str = "<tr>";
	str += "<th style='width:200px;'>" + (num + 1) + "</th>";
	str += "<th style='width:200px;'>" + data.deptName + "</th>";
	str += "<th style='width:200px;'>" + data.director + "</th>";
	str += "<th style='width:200px;'>" + data.address + "</th>";
	str += "<th style='width:200px;'>" + data.phone + "</th>";
	str += "<th style='width:700px;'>" + data.remark + "</th>";
	if (state == 1) {
		/*
		 * str+="<th style='width:200px;'><button type='button'
		 * class='layui-btn layui-btn-normal delete'>删除</button></th>";
		 * str+="<th style='width:200px;'><button type='button'
		 * class='layui-btn layui-btn-normal update'>修改</button></th>";
		 */
		str += "<th style='width:200px;'><span class='update'>修改</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='delete'>删除</span></th>";
	}
	str += "</tr>";
	var $str = $(str);
	$str.data("id", data.id);
	$('#tab').append($str);
}
// 删除表格行
function delTr() {
	// 获取元表格数据，逐行删除
	var data = $("#tab").find("tr");
	for (var i = 1; i < data.length; i++) {
		data[i].remove();
	}
}
