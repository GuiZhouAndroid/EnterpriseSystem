var layedit;
var index;
$(document).ready(function() {
	$(".com").load("com");

	layui.use('layedit', function() {
		layedit = layui.layedit;

		layedit.set({
			uploadImage : {
				url : 'notice/image.io', // 接口url
				type : 'post', // 默认post
				auto : false,
				bindAction : '#save'
			}
		});

		index = layedit.build('demo', {
			tool : [ 'strong' // 加粗
			, 'italic' // 斜体
			, 'underline' // 下划线
			, 'del' // 删除线
			, '|' // 分割线
			, 'left' // 左对齐
			, 'center' // 居中对齐
			, 'right' // 右对齐
			, 'face' // 表情
			, 'image' // 插入图片
			],
			height : 325
		}); // 建立编辑器
		layedit.sync(index);

		showNotice(layedit, index);
		$("#save").click(save);
	});

});
function showNotice(layedit, index) {
	var url = location.href;
	var start = url.indexOf("=");
	var id = url.substr(start + 1);
	if (id != "" && id != null) {
		$.ajax({
			url : "notice/queryNoticeContent.io",
			type : "get",
			dataType : "json",
			data : {
				"id" : id
			},
			success : function(result) {
				if (result.state == 1) {
					$(".title").val(result.data.title);
					layedit.setContent(index, result.data.content, false);
				}
			},
			error : function() {
				alert(查询公告详情失败);
			}
		});
	}
}
function save() {
	var url = location.href;
	var start = url.indexOf("=");
	var id = url.substr(start + 1);
	layedit.sync(index);
	var content = layedit.getContent(index);
	var title = $(".title").val().trim();
	if (id != "" && id != null && title!="") {
		$.ajax({
			url:"notice/updateNotice.io",
			type:"put",
			dataType:"json",
			data:{"id":id,"title":title,"content":content},
			success:function(result){
				if(result.state==1){
					window.location.href="index";
				}
			},
			error:function(){
				alert("修改公告失败");
			}
		});
	}
}
