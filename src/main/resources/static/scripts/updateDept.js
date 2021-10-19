var id="";
var form,laydate,layer;
$(function(){
	$(".com").load("com");
	layui.use(['form','laydate','layer'], function(){
		  form = layui.form;
		  laydate = layui.laydate;
		  layer=layui.layer;
		  laydate.render({
				elem : '#time'
			});
		});
	getDeptInfo();
	$("#sub_btn").click(updataDeptInfo);
})
function getDeptInfo(){
	var url = location.href;
	var start = url.indexOf("=");
	id = url.substr(start + 1);
	if(id!=""){
		$.ajax({
			url:"dept/getDeptInfoById.io",
			type:"get",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				if(result.state==1){
					var data=result.data;
					$(".deptName").val(data.deptName);
					$(".director").val(data.director);
					$(".address").val(data.address);
					$(".phone").val(data.phone);
					$(".remark").val(data.remark);
				}else{
					alert("没有获取到该数据，重新获取");
				}
			},
			error:function(){
				alert("获取部门信息失败")
			}
		});
	}
}
function updataDeptInfo(){
	var deptName=$(".deptName").val().trim();
	var remark=$(".remark").val().trim();
	var director=$(".director").val().trim();
	var address=$(".address").val().trim();
	var phone=$(".phone").val().trim();
	if(id!=""){
		$.ajax({
			url : "dept/upDept.io",
			type : "put",
			dataType : "json",
			data:{"id":id,"deptName":deptName,"remark":remark,"director":director,"address":address,"phone":phone},
			success : function(result) {
				if (result.state == 1) {
					layer.msg("修改部门成功");
				}
			},
			error : function() {
				alert("修改部门名称失败");
			}
		});
	}
	return true;
}
