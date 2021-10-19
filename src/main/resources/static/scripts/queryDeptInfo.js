/*
查询部门
*/
function queryDept(){
$.ajax({
	url:"dept/queryDeptInfo.io",
	type:"get",
	success:function(result){
		if(result.state==1){
			delDeptTr();
			var data=result.data;
			for(var i=0;i<data.length;i++){
				createS(data[i].deptName,i);
			}
		}
	},
	error:function(){
		alert("查询部门失败");
	}
});
}
function createS(Deptname,i){
	var li= '<option value='+i+'>'+Deptname+'</option>';
	var $li=$(li);
	$(".chooseDept").append($li);
}
//删除表格行
function delDeptTr(){
	//获取元表格数据，逐行删除
	var data=$(".showtab").find("tr");
	for(var i=1;i<data.length;i++){
		data[i].remove();
	}
}