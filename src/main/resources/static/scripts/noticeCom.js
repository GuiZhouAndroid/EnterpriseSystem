function queryNoticeCount() {
	var count = 0;
	$.ajax({
		url : "notice/queryNoticeCount.io",
		type : "get",
		async : false,
		success : function(result) {
			if (result.state == 1) {
				count = result.data;
			}
		},
		error : function() {
			alert("查询通知总数出错");
		}
	});

	layui.use([ 'laypage', 'layer' ], function() {
		var laypage = layui.laypage, layer = layui.layer;
		if(count>0){
			laypage.render({
				elem : 'demo1',
				count : count,
				groups : 4,
				jump : function(obj) {
					queryNoticeTitle(obj.curr);
				}
			});
		}
	});

}

function queryNoticeTitle(page) {
	$.ajax({
		url : "notice/queryNoticeTitle.io",
		type : "get",
		dataType : "json",
		data : {
			"page" : page
		},
		success : function(result) {
			if (result.state == 1) {
				var data = result.data;
				delTr();
				for (var i = 0; i < data.length; i++) {
					createTr((i+1),data[i].title, data[i].time, data[i].id);
				}
			}
		},
		error : function() {
			alert("查询失败");
		}
	});
}

