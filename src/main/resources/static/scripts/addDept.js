var layer;
$(document).ready(function() {
	$(".com").load("com");
	layui.use([ 'form', 'laydate', 'table' ],function(){
		layer = layui.layer;
	});
	$(".sub_btn").click(addDept);
	
})

function addDept(){
	var deptName=$(".deptName").val().trim();
	var remark=$(".remark").val().trim();
	var director=$(".director").val().trim();
	var address=$(".address").val().trim();
	var phone=$(".phone").val().trim();
	if(!paraJude(deptName,remark,director,address,phone)){
		return true;
	}
		$.ajax({
			url:"dept/addDept.io",
			type:"post",
			dataType:"json",
			data:{"deptName":deptName,"remark":remark,"director":director,"address":address,"phone":phone},
			success:function(result){
				
				if(result.state==1){
					layer.msg("添加部门成功");
					//$(".deptName").val("");
					//$(".remark").val("");
				}else{
					layer.msg("重复添加部门");
				}
			},
			error:function(){
				alert("添加部门失败");
			}
		});
	return false;
}
function paraJude(deptName,remark,director,address,phone){
	if(deptName==""||deptName==null){
		//layer.msg("部门名称不能为空");
		return false;
	}
	if(remark==""||remark==null){
		//layer.msg("备注不能为空");
		return false;
	}
	if(director==""||director==null){
		//layer.msg("主管不能为空");
		return false;
	}
	if(address==""||address==null){
		//layer.msg("地址不能为空");
		return false;
	}
	if(phone==""||phone==null){
		//layer.msg("电话不能为空");
		return false;
	}
	return true;
}