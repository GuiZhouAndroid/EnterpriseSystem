$(document).ready(function(){
	$(".com").load("com");
	layui.use(['layer','form','upload'],function(){
		var upload=layui.upload;
		upload.render({
		    elem: '#uploadFile'
		    ,url: '/dataShare/uploadDataFile.io'
		    ,accept: 'file' //普通文件
		    ,size : 0
		    ,auto:false
		    ,data:{docTitle:function(){
		    	return $(".docTitle").val().trim();
		    },docContent:function(){
		    	return $(".remark").val().trim();
		    }}
		    ,bindAction:'#upload'
		    ,done: function(res){
		      console.log(res);
		     
		    }
		  });
	});
});

