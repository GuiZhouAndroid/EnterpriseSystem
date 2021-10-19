$(document).ready(function() {
	$(".com").load("com");
	showNotice();
})
function showNotice() {
	var url = location.href;
	var start = url.indexOf("=");
	var id = url.substr(start + 1);
	if (id != "") {
		$.ajax({
			url : "notice/queryNoticeContent.io",
			type : "get",
			dataType : "json",
			data : {
				"id" : id
			},
			success : function(result) {
				console.log(result);
				if (result.state == 1) {
					$("#title").text(result.data.title);
					createNotice(result.data.content, result.data.time);
				}
			},
			error : function() {
				alert("查询通知详情出错");
			}
		});
	}
}
function createNotice(content, time) {
	var date = new Date();
	date.setTime(time);
	var str =content;
	
	$(".content").append(str);

}