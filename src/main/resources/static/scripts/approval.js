$(document).ready(function() {
					$(".com").load("com");
					$('#add').click(approver);
					layui
							.use(
									[ 'form', 'laydate', 'table' ],
									function() {
										var form = layui.form, layer = layui.layer, laydate = layui.laydate, table = layui.table;
										laydate.render({
											elem : '#start',
											type : 'datetime'
										});
										laydate.render({
											elem : '#end',
											type : 'datetime'
										});
										table.render({
											elem : '#tt',
											url : 'approval/approval.io',
											height:410,
											response : {
												statusName : 'state' // 规定数据状态的字段名称，默认：code
												,
												statusCode : 1 // 规定成功的状态码，默认：0
											},
											cols : [ [ {
												type : 'radio'
											}, {
												field : 'empName',
												width : 180,
												title : '用户名'
											},{
												field : 'empDept',
												width : 180,
												title : '部门'
											},{
												field : 'empPosition',
												width : 180,
												title : '职位'
											} ] ]
										});					
										 active = {
											    getCheckData: function(){ //获取选中数据
											      var checkStatus = table.checkStatus('tt')
											      ,data = checkStatus.data;
											      delLi();
											     for(var i=0;i<data.length;i++){
											    	 createLi(data[i].empName);
											     }
											     btnClose();
											    }
											  };
											  
											  $('.btnGroup .layui-btn').on('click', function(){
											    var type = $(this).data('type');
											    active[type] ? active[type].call(this) : '';
											  });						  			  
										
									});

					$(".close").on("click", btnClose);	
					$(".ulList").on("click",".imgDel",delImg);
					$(".sub_btn").click(submitBtn);
				})

function approver() {
	$(".opacity_bg").show();
	$(".show").show();
}
function btnClose() {
	$(".opacity_bg").hide();
	$(".show").hide();
}
function createLi(empName){
	
	var li='<li><span>'+empName+'</span><img src="images/icon_delete_red.png" class="imgDel"/></li>&nbsp;&nbsp;&nbsp;';
	$(".ulList").append(li);
}
function delLi(){
	var $li=$(".ulList").empty();
	
}
function delImg(){
	$(this).parent().remove();
}
function submitBtn(){
	var leaveType = $("select#leaveType option:selected").text().trim()
	var start=$("#start").val();
	var end=$("#end").val();
	var leaveNum=$("#leaveNum").val();
	var leaveRegard=$("#leaveRegard").val();
	var approver=$(".ulList").find("li").text();
	var numReg=/^[0-9]*$/;
	if(!numReg.test(leaveNum)){
		alert("请假天数只能是数字");
		return true;
	}
	if(leaveType!="" && approver!="" && start!="" && end!="" && leaveNum!="" && leaveRegard!=""){
		$.ajax({
			url:"approval/leaveApplication.io",
			type:"post",
			dataType:"json",
			data:{"leaveType":leaveType,"start":start,"end":end,"leaveNum":leaveNum,"leaveRegard":leaveRegard,"approver":approver},
			success:function(result){
				if(result.state==1){
					window.location.href="myApproval";
				}
			},
			error:function(){
				alert("提交失败");
			}
		});
	}else{
		alert("审批人不能为空");
	}
}