var form = "", layer = "", laydate = "", laypage = "";
$(document).ready(function() {
					$(".com").load("com.html");
					$(".table_info").on("click",".adopt",agree)
					$(".table_info").on("click",".refuse",refuse);
					
					
					$(".table_info").on("click",".del",delApproval);
					
					$("#approvedSearch").click(approvedSearch);
					$(".resetBtn").click(reload);

					layui.use(['form', 'laydate', 'laypage','layer' ],
									function() {
										form = layui.form, layer = layui.layer, laydate = layui.laydate, laypage = layui.laypage;
										laydate.render({
											elem : '#start',
											calendar : true
										});
										laydate.render({
											elem : '#end',
											calendar : true
										});
										laydate.render({
											elem : '#start1',
											calendar : true
										});
										laydate.render({
											elem : '#end1',
											calendar : true
										});
										//待审批
										pendingApproval();
										//已审批
										$("#approved").click(approval("","","",0,0));
									
									});
				})
				
function reload(){
	approval("","","",0,0);
}
				
				
//已审批搜索
function approvedSearch(){
	var applicant=$("#applicant").val().trim();
	var createStartTime=$("#start1").val().trim();
	var createEndTime=$("#end1").val().trim();
	var approverName=$("select#approverStatue option:selected").text().trim()
	var approverStatue=2;
	if(approverName=="拒绝"){
		approverStatue=0;
	}

	if(approverName=="通过"){
		approverStatue=1;
	}
	

	if(applicant!="" || createStartTime!="" || createEndTime!="" || approverName!="==请选择=="){
		approval(applicant,createStartTime,createEndTime,approverStatue,1);
	}else{
		layer.msg("搜索条件不能为空");
		
	}
}
//已审批
function approval(applicant,createStartTime,createEndTime,approverStatue,statue){
	var countNum = 0;
	$.ajax({
		url : "approval/queryApprovalCount.io",
		type : "get",
		data : {"applicant" : applicant,"createStartTime" : createStartTime,"createEndTime" : createEndTime,"approverStatue" : approverStatue,"statue" : statue},
		async : false,
		success : function(result) {
			countNum = result.count;
		},
		error : function() {
			alert("查询失败");
		}
	});
	if(countNum>0){
		laypage.render({
			elem : 'paging1',
			count : countNum,// 数据总数,
			groups : 4,
			jump : function(obj) {
				var str = "第"
						+ ((obj.curr - 1) * 10 + 1)
						+ "条到第"
						+ (obj.curr * 10 > obj.count ? obj.count
								: obj.curr * 10)
						+ "条，共"
						+ (obj.count)
						+ "条";
				
					$("#countRed1").text(str);
				
				delTr("#approval");
				approvalPaging(obj.curr,applicant,createStartTime,createEndTime,approverStatue,statue);
			}
		});
	}else{
		//layer.msg("没有该结果数据，请从新查询");
	}
	
}
				
				
				
//待审批
function pendingApproval(){
	var count = 0;
	$.ajax({
		url : "approval/queryCount.io",
		type : "get",
		dataType : "json",
		data : {
			"approverStatue" : 2
		},
		async : false,
		success : function(result) {
			count = result.count;
			console.log("总数:"+count);
		},
		error : function() {
			alert("查询失败");
		}
	});
	if(count>0){
		laypage.render({
			elem : 'paging',
			count : count,// 数据总数,
			groups : 4,
			jump : function(obj) {
				var str = "第"
						+ ((obj.curr - 1) * 10 + 1)
						+ "条到第"
						+ (obj.curr * 10 > obj.count ? obj.count
								: obj.curr * 10)
						+ "条，共"
						+ (obj.count)
						+ "条";
				$("#countRed")
						.text(str);
				delTr("#pendingApproval");
				paging(obj.curr,2);
				
			}
		});
	}else{
			delTr("#pendingApproval");
			$("#countRed").text("");
			console.log("记录为0");
			layer.msg("对不起，没有该记录");
	}
}
//
function paging(page,approverStatue){
	$.ajax({
		url:"approval/getPendingApproval.io",
		type:"get",
		dataType:"json",
		data:{"page":page,"approverStatue":approverStatue},
		success:function(result){
			if(result.state==1){
				var n=authorityUrl();
				var data=result.data;
				for(var i=0;i<data.length;i++){
					createTr(data[i],"#pendingApproval",n);
				}
			}
		},
		error:function(){
			alert("请求数据失败");
		}
	});
}
//删除某条待审批记录
function delApproval() {
	var $tr=$(this).parent().parent();
	var id=$tr.data("id");
	
	if(id!=""){
		$.ajax({
			url:"approval/delApproval.io",
			type:"delete",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				if(result.state==1){
					pendingApproval();
					layer.msg("删除成功");
					$tr.remove();
				}
			},
			error:function(){
				alert("删除失败");
			}
		});
	}
}
//已审批
function approvalPaging(page,applicant,createStartTime,createEndTime,approverStatue,statue){
	$.ajax({
		url:"approval/getApproval.io",
		type:"get",
		dataType:"json",
		data:{"page":page,"applicant" : applicant,"createStartTime" : createStartTime,"createEndTime" : createEndTime,"approverStatue" : approverStatue,"statue" : statue},
		success:function(result){
			if(result.state==1){
				var n=authorityUrl();
				var data=result.data;
				for(var i=0;i<data.length;i++){
					createTr(data[i],"#approval",n);
				}
			}
		},
		error:function(){
			alert("请求数据失败");
		}
	});
}
function authorityUrl(){
	var n=0;
	$.ajax({
		url:"approval/queryAuthority.io",
		type:"get",
		dataType:"json",
		data:{"url":"approvalauthority"},
		async : false,
		success:function(result){
			n=result.state;
		},
		error:function(){
			alert("查询权限失败");
		}
	});
	return n;
}
function createTr(data,id,n) {
	var state="";
	if(data.approverStatue==0){
		state="拒绝";
	}
	if(data.approverStatue==1){
		state="同意";
	}
	if(data.approverStatue==2){
		state="待审批";
	}
	var tr = '<tr>';
	tr += '<th style="width: 100px;">'+data.applicant+'</th>';
	tr += '<th style="width: 100px;">'+data.leaveType+'</th>';
	tr += '<th style="width: 100px;" id="appStatus">'+state+'</th>';
	tr += '<th style="width: 150px;">'+data.startTime+' 至' +data.endTime+'</th>';
	tr += '<th style="width: 100px;">'+data.leaveNum+'</th>';
	tr += '<th style="width: 289px;"><textarea readonly class="layui-textarea">'+data.leaveRegard+'</textarea>';
	tr += '</th>';
	tr += '<th style="width: 250px;">';
	if(n==1 && data.applicant!=$(".admin").text()){
		tr += '<button class="layui-btn layui-btn-primary layui-btn-s adopt">';
		tr += '	<i class="layui-icon" style="font-size: 15px">&#xe672;</i>';
		tr += '</button>同意';
		tr += '<button class="layui-btn layui-btn-primary layui-btn-s refuse">';
		tr += '	<i class="layui-icon" style="font-size: 15px">&#x1006;</i>';
		tr += '</button>拒绝';
	}else if(id=="#approval"){	//已审批的不允许删除
		tr +='无';
	}else{
		tr += '<button class="layui-btn layui-btn-primary layui-btn-s del">';
		tr += '<i class="layui-icon" style="font-size: 20px">&#xe640;</i>';
		tr += '</button>删除';
	}
	tr += '</th>';
	tr += '</tr>';
	var $tr=$(tr);
	
	$tr.data("id",data.id);
	
	$(""+id).append($tr);
}

//删除
function delTr(str){
	var data = $(""+str).find("tr");
	for (var i = 1; i < data.length; i++) {
		data[i].remove();
	}
}
//同意
function agree(){
	var $tr=$(this).parent().parent();
	var id=$tr.data("id");
	$tr.find("#appStatus").text("通过");
	if(id!=""){
		operation(id,1);
	}
	pendingApproval();
}
//拒绝
function refuse(){
	var $tr=$(this).parent().parent();
	var id=$tr.data("id");
	$tr.find("#appStatus").text("拒绝");
	if(id!=""){
		operation(id,0);
	}
	pendingApproval();
}
//操作
function operation(id,approverStatue){
	$.ajax({
		url:"approval/operation.io",
		type:"put",
		dataType:"json",
		async : false,
		data:{"id":id,"approverStatue":approverStatue},
		success:function(result){
			console.log(result);
		},
		error:function(){
			alert("操作失败");
		}
	});
}