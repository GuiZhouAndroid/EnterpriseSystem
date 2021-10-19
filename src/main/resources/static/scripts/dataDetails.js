var laypage="";
var layer="";
$(document).ready(function(){
	$(".com").load("com");
	//$(".preview").click(preview);
	//$(".dowload").click(dowload);
	initDocument("" ,0);
	$(".resetBtn").click(reload);
	
});


function initDocument(value,state){
	var count = 0;
	$.ajax({
		url : "dataShare/getAllDocumentCount.io",
		type : "get",
		async : false,
		data : {"value" : value , "state" : state},
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
					paging(obj.curr,value,state);
				}
			});
		}
		
		if(count==0){
			layer.msg("没有记录");
		}
	});
}
function paging(page,value,state) {
	$.ajax({
		url : "dataShare/initDocument.io",
		type : "get",
		dataType : "json",
		data : {
			"page" : page,"value" : value , "state" : state},
		success : function(result) {
			console.log(result);
			if (result.state == 1) {
				var data = result.data;
				
				delTr();
				for (var i = 0; i < data.length; i++) {
					createTr(i+1,data[i].title, data[i].createTime, data[i].empName,
							data[i].empDept,data[i].id);
				}
			}
		},
		error : function() {
			alert("查询出错");
		}
	});
}
function createTr(num,title, createTime, empName, empDept,id) {

	var times = new Date();
	times.setTime(createTime);
	var tr = "<tr>";
	tr +="<td>"+num+"</td>";
	tr += "<td><a href='docKnowledge.io?id=" + id + "'>" + title + "</a></td>";
	tr += "<td><b>" + empName + "</b></td>"
	tr += "<td>" + (empDept==null?'--------------':empDept) + "</td>"
	tr += "<td>" + times.toLocaleDateString() + "</td>"
	tr += "</tr>";
	var $tr = $(tr);
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
function doSearch (value){
	if(value==null || value == ""){
		layer.msg("输入搜索值")
	}else{
		initDocument(value,1);
	}
	
}
function reload(){
	initDocument("" ,0);
}