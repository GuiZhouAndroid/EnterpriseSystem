$(function(){
	$(".com").load("com");
	initDocument();
	$('.notice_list').on("click",".delete",deleteDoc);
	$('.close').click(clickClose);
	$(".del_sure").click(delSure);
});
function deleteDoc(){
	$(".opacity_bg").show();
	$(".sure_del").show();
	// 将tr一行绑定在删除按钮上
	$(".del_sure").data("del", $(this).parent().parent());
	var tr = $(this).parent().parent().find("td");
	// 在确认删除按钮上绑定empId
	var id = $(this).parent().parent().data("id");
	$(".del_sure").data("id", id);
}
function initDocument(){
	var count = 0;
	$.ajax({
		url : "dataShare/getReleaseDocumentCount.io",
		type : "get",
		async : false,
		success : function(result) {
			count = result.count;
		},
		error : function() {
			alert("查询总数出错");
		}
	});

	layui.use([ 'laypage', 'layer' ], function() {
		laypage = layui.laypage, layer = layui.layer;
		// 总页数大于页码总数
		if(count>0){
			laypage.render({
				elem : 'demo1',
				count : count,// 数据总数,
				groups : 4,
				jump : function(obj) {
					var str = "第" + ((obj.curr - 1) * 10 + 1) + "条到第"
							+ (obj.curr * 10 > obj.count ? obj.count : obj.curr * 10)
							+ "条，共" + (obj.count) + "条";
					$("#countRed").text(str);
					paging(obj.curr);
				}
			});
		}
		
		if(count==0){
			layer.msg("没有记录");
		}
	});
}
function paging(page) {
	$.ajax({
		url : "dataShare/getReleaseDocument.io",
		type : "get",
		dataType : "json",
		data : {
			"page" : page},
		success : function(result) {
			console.log(result);
			if (result.state == 1) {
				var data = result.data;
				
				delTr();
				for (var i = 0; i < data.length; i++) {
					createTr(i+1,data[i].title,data[i].empName,data[i].empDept,data[i].id);
				}
			}
		},
		error : function() {
			alert("查询出错");
		}
	});
}
function createTr(num,title,empName,empDept,id) {
	var tr = "<tr>";
	tr +="<td>"+num+"</td>";
	tr += "<td><b>" + title + "</b></td>";
	tr += "<td><b>" + empName + "</b></td>";
	tr += "<td><b>" + (empDept==null?'--------------':empDept) + "</b></td>";
	tr += "<td><span class='delete'><b>删除</b></span></td>"
	tr += "</tr>";
	var $tr = $(tr);
	$tr.data("id",id);
	$(".notice_list").append($tr);

}
//删除表格行
function delTr() {
	// 获取元表格数据，逐行删除
	var data = $(".notice_list").find("tr");
	for (var i = 1; i < data.length; i++) {
		data[i].remove();
	}
}
//点击取消
function clickClose() {
	$(".opacity_bg").hide();
	$(".sure_del").hide();
}
function delSure(){
	var id = $(this).data("id");
	$.ajax({
		url : "dataShare/delDoc.io",
		dataType : "json",
		type : "delete",
		data : {
			"id" : id
		},
		success : function(result) {
			if (result.state == 1) {
				delTr();
				initDocument();
			}
		},
		error : function() {
			alert("删除失败");
		}
	});

	$(".sure_del").hide();
	$(".opacity_bg").hide();
}