$(document).ready(function(){
	$(".com").load("com");
	queryNoticeCount();
	$(".notice_list").on("click",".del",delNotice);
});

function createTr(num,title, time, id) {
	var times = new Date();
	times.setTime(time);
	var tr = "<tr>";
	tr += "<td>"+num+"</td>";
	tr += "<td>" + title +"</td>";
	tr += "<td>" + times.toLocaleString() + "</td>"
	tr += "<td><a href='updateNotice.io?id=" + id + "'>编辑</a></td>";
	tr += "<td><span class='del'>删除</span></td>";
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
function delNotice(){
	var $tr=$(this).parent().parent();
	var id=$tr.data("id");
	if(id!=""){
		$.ajax({
			url:"notice/delNotice.io",
			type:"delete",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				if(result.state==1){
					queryNoticeCount();
				}
			},
			error:function(){
				alert("删除公告失败");
			}
		});
	}
}