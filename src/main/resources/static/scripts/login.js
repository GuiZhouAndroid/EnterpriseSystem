var form="";
$(document).ready(function() {
	var layer="";
	layui.use(['form','layer'], function() {
		form = layui.form;
		layer = layui.layer;
		getLoginStatus();
	});
	
	$("#login").click(function(){
		var username=$("#count").val().trim();
		var pass=$("#password").val().trim();
		var rebPass=$('input:checkbox[name="loginStatus"]:checked').val();

		if(username==""||pass==""){
			layer.msg("用户名或密码不能为空");
		}else if(pass.length>=5){
			$.ajax({
				url:"mangage/login.io",
				type:"post",
				dataType:"json",
				data:{"username":username,"pass":pass},
				success:function(result){
					if(result.state==1){
						if(rebPass=="on"){
							SetCookie("loginName",username);
							SetCookie("loginPassword",pass);
						}else{
							delCookie("loginName");
							delCookie("loginPassword");
						}
						location.href="index";
					}else{
						layer.msg(result.msg);
					}
				},
				error:function(){
					alert("登陆失败");
				}
			});
		}else{
			layer.msg("密码长度不能小于5位");
		}
		return false;
	});

});

function getLoginStatus(){
	var username=getCookie("loginName");
	var pass=getCookie("loginPassword");
	if((username!=null && username!="") || (pass!=null && pass!="")){
		$(".loginStatus").attr("checked",true);
		
		form.render("checkbox");
		$("#count").val(username);
		$("#password").val(pass);
	}
}