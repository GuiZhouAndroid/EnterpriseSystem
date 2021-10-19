var layer="";
$(document).ready(function() {
	$(".com").load("com");

	layui.use('layer', function(){ 
		 layer = layui.layer;});
	$("#subBtn").click(updatePass);
})
function updatePass(){
	var oriPass=$("#oriPass").val().trim();
	var newPass=$("#newPass").val().trim();
	var surePass=$("#surePass").val().trim();
	var flog=true;
	if(oriPass==""||newPass==""||surePass==""){
		layer.msg("密码不能为空");
		flog=false;
		return false;
	}
	if(oriPass.length<5||newPass.length<5||surePass.length<5){
		layer.msg("密码长度不能小于5位");
		flog=false;
		return false;
	}
	if(newPass!=surePass){
		layer.msg("密码不相同");
		flog=false;
		return false;
	}
	if(flog){
		$.ajax({
			url:"mangage/updatePass.io",
			type:"put",
			dataType:"json",
			data:{"oriPass":oriPass,"newPass":newPass},
			success:function(result){
				layer.msg(result.msg);
				if(result.state==1){
					window.location.href="index";
				}
			},
			error:function(){
				layer.msg("修改密码失败");
			}
		});
	}
	return false;
}