var layer="";
var layedit="";
var index="";
$(document).ready(function() {
	$(".com").load("com");
	
	layui.use(['layedit','layer'], function() {
		layedit = layui.layedit;
		layer=layui.layer;
		layedit.set({
			uploadImage : {
				url : 'notice/image.io', //接口url
				type : 'post', //默认post
				auto:false,
				bindAction:'#save'
			}
		});

		index = layedit.build('demo', {
			tool : [ 'strong' //加粗
			, 'italic' //斜体
			, 'underline' //下划线
			, 'del' //删除线
			, '|' //分割线
			, 'left' //左对齐
			, 'center' //居中对齐
			, 'right' //右对齐
			, 'face' //表情
			, 'image' //插入图片
			],
			height : 325
		}); //建立编辑器
		layedit.sync(index);
		
		//点击保存
		$("#save").click(save);
	});
})
function save(){
	  layedit.sync(index);
	  var content=layedit.getContent(index);
	  var title=$(".title").val().trim();
	  if(content!="" && title!=""){
		  $.ajax({
			  url:"notice/noticeContent.io",
			  type:"post",
			  dataType:"json",
			  data:{"title":title,"content":content},
			  success:function(result){
				  alert(result.msg);
				  window.location.href="editeNotice";
			  },
			  error:function(){
				  layer.msg("保存失败");
			  }
		  });
	  }
  }
