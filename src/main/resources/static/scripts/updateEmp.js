var form,laydate,layer;
var phoneReg=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
var emailReg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var numReg=/^[A-Za-z0-9]+$/;
var cardReg=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
var ageReg=/^[1-9]\d*|0$/;
var empId=""
$(function(){
	queryDept();
	$(".com").load("com");
	layui.use(['form','laydate','layer'], function(){
		  form = layui.form;
		  laydate = layui.laydate;
		  layer=layui.layer;
		  laydate.render({
				elem : '#time'
			});
		});
	getEmpInfo();
	$(".updateEmp").click(updateEmp);
});
function getEmpInfo(){
	var url = location.href;
	var start = url.indexOf("=");
	empId = url.substr(start + 1);
	if(empId!=""&&empId!=null){
		$.ajax({
			url:"/mangage/getEmpInfoById.io",
			type:"get",
			dataType:"json",
			data:{"empId" : empId},
			success:function(result){
				var data=result.data;
				if(result.state==1){
					$(".name").val(data.empName);
					if (data.empSex == '男') {
						$('.empSex:radio[value=' + data.empSex + ']').prop("checked", true);
					} else {
						$('.empSex:radio[value=' + data.empSex + ']').prop("checked", true);
					}
					$(".nation").val(data.empNation);
					$("#time").val(data.empBirth);
					if (data.empMarriage == '未婚') {
						$('.marriage:radio[value=' + data.empMarriage + ']').prop("checked", true);
					} else  if(data.empMarriage=="已婚"){
						$('.marriage:radio[value=' + data.empMarriage + ']').prop("checked", true);
					}else{
						$('.marriage:radio[value=' + data.empMarriage + ']').prop("checked", true);
					}
					$(".political").val(data.empPolitical);
					$(".numId").val(data.empNumCard);
					// 获取当前个人部门信息用于反选
					var perDept = data.empDept;
					var deptSelect = $("#seledept").html();
					var res = $(deptSelect);
					for (var j = 0; j < res.length; j++) {
						if (perDept == res[j].text) {
							$("#seledept option[value='" + res[j].value + "']").prop(
									"selected", true);
						}
					}
					
					$(".empNum").val(data.empNum);
					$(".iphone").val(data.empPhone);
					$(".mail").val(data.empMail);
					$(".age").val(data.empAge);
					var perEdu = data.empEducation;
					var educationSelect = $("#education").html();
					var res = $(educationSelect);
					for (var j = 0; j < res.length; j++) {
						if (perEdu == res[j].text) {
							$("#education option[value='" + res[j].value + "']").prop(
									"selected", true);
						}
					}
					$(".major").val(data.empMajor);
					$(".school").val(data.empSchool);
					$(".position").val(data.empPosition);
					$(".address").val(data.empAddress);
					$(".salary").val(data.empSalary);
					form.render('radio');
					form.render("select");
					
				}else{
					layer.msg("请重新刷新")
				}
				
			},
			error:function(){
				layer.msg("获取更改信息失败");
			}
		});
	}else{
		layer.msg("获取id失败");
	}
	
}
function updateEmp(){
	var empName=$(".name").val().trim();
	var empSex=$('.empSex:radio:checked').val();
	var empNation=$(".nation").val().trim();
	var empBirth=$("#time").val();
	var empMarriage=$(".marriage:radio:checked").val();
	var empPolitical=$(".political").val().trim();
	var empNumCard=$(".numId").val().trim();
	var empDept=$("select#seledept option:selected").text().trim();
	var empNum=$(".empNum").val().trim();
	var empPhone=$(".iphone").val().trim();
	var empMail=$(".mail").val().trim();
	var empAge=$(".age").val().trim();
	var empEducation=$("select#education option:selected").text().trim();
	var empMajor=$(".major").val().trim();
	var empSchool=$(".school").val().trim();
	var empPosition=$(".position").val().trim();
	var empAddress=$(".address").val().trim();
	var empSalary=$(".salary").val().trim();
	if(!regformat(empNum,empPhone,empMail,empNumCard,empAge)){
		return false;
	}
	if(empId!=""&&empId!=null){
		$.ajax({
			url : "mangage/upEmpInfo.io",
			type : "put",
			async : true,
			dataType : "json",
			data : {
				"empId" : empId,
				"empName" : empName,
				"empDept" : empDept,
				"empNum" : empNum,
				"empPhone" : empPhone,
				"empSex" : empSex,
				"empMail" : empMail,
				"empNation" : empNation,
				"empBirth" : empBirth,
				"empMarriage" : empMarriage,
				"empPolitical" : empPolitical,
				"empNumCard" : empNumCard,
				"empAge" : empAge,
				"empEducation" : empEducation,
				"empMajor" : empMajor,
				"empSchool" : empSchool,
				"empPosition" : empPosition,
				"empAddress" : empAddress,
				"empSalary" : empSalary
			},
			success : function(result) {
				if (result.state == 1) {
					layer.msg("更改信息成功");
					
				}
			},
			error : function() {
				alert("更改信息失败");
			}
		});
	}else{
		layer.msg("id获取失败");
	}
}
//检查格式
function regformat(empNum,empPhone,empMail,empNumCard,empAge){
	if(!numReg.test(empNum)){
		layer.msg("编号只能输入英文或数字");
		return false;
	}
	if(empNum.length<5){
		layer.msg("编号长度至少五位");
		return false;
	}
	if(!phoneReg.test(empPhone)){
		layer.msg("请输入有效手机号码");
		return false;
	}
	if(!emailReg.test(empMail)){
		layer.msg("请输入有效邮箱");
		return false;
	}
	if(!cardReg.test(empNumCard)){
		layer.msg("请输入有效证件号");
		return false;
	}
	if(!ageReg.test(empAge)){
		layer.msg("请输入有效年龄");
		return false;
	}
	return true;
}