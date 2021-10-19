var form,laydate,layer;
var phoneReg=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
var emailReg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var numReg=/^[A-Za-z0-9]+$/;
var cardReg=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
var ageReg=/^[1-9]\d*|0$/;
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
	$(".addEmp").click(addEmp);
	queryDept();
})
function addEmp(){
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
	var empId = "";
	$.ajax({
		url : "mangage/addEmp.io",
		type : "post",
		async : true,
		dataType : "json",
		data : {
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
				layer.msg("恭喜新员工入职");
			}else{
				layer.msg("重复添加员工");
			}
		},
		error : function() {
			alert("添加员工失败");
		}
	});
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
