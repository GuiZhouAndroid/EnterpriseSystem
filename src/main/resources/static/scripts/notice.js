$(document).ready(function() {
	$(".com").load("com");
	queryNoticeCount();
})
function createTr(num,title, time, id) {
	var times = new Date();
	times.setTime(time);
	var tr = "<tr>";
	tr +="<td>"+num+"</td>";
	tr += "<td><a href='notice.io?id=" + id + "'>" + title + "</a></td>";
	tr += "<td>" + times.toLocaleString() + "</td>"
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