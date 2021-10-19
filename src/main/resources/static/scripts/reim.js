var layer,laydate;
var index=0;
var num=/^\d+(\.\d+)?$/;
var Tr;
$(function(){
	$(".com").load("com");
	layui.use([ 'form', 'laydate' ],function(){
		layer = layui.layer;
		laydate = layui.laydate;
	});
	$(".body_layer").mCustomScrollbar({
		setWidth:1500,
		setHeight:680,
		setTop:-170,
		axis:"y",
		scrollInertia:1,
		theme: "3d-dark",
		mouseWheel:true,
		 scrollButtons:{
		    enable:true,
		 },
		 advanced:{ 
		    updateOnContentResize:true,
		}
		
	});
	
	var date=new Date();
	var Y=date.getFullYear()+"-";
	var M=(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	var D=D = date.getDate() + ' ';
	$(".time").text(Y+M+D);
	
	$(".simg").click(function(){
		WdatePicker({el:$dp.$('stime'),dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:dateDiff});
	})
	$(".eimg").click(function(){
		WdatePicker({el:$dp.$('etime'),dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:dateDiff});
	})
	$(".ccxc").on("click",".sbtn",function(){
		var id=$($(this).parent().siblings()[1]).text();
		WdatePicker({el:$dp.$('stime'+id),dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:dateDiff});
	});
	$(".ccxc").on("click",".ebtn",function(){
		var id=$($(this).parent().siblings()[1]).text();
		WdatePicker({el:$dp.$('etime'+id),dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:dateDiff});
	});
	$(".ccxc").on("click",".del",delTr);
	$(".ccxc").on("click",".getTr",getTr);
//	$(".simg1").click(function(){
//		var id=$($(this).parent().siblings()[1]).text();
//		WdatePicker({el:$dp.$('stime'+id),dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:dateDiff});
//	})
//	$(".eimg1").click(function(){
//		var id=$($(this).parent().siblings()[1]).text();
//		WdatePicker({el:$dp.$('etime'+id),dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:dateDiff});
//	})
})
function dateDiff() {
	var id=$($(this).parent().siblings()[1]).text();
	if(id==null||id==""){
		id="";
	}
   var sDate=$("#stime"+id).val().trim();
   var eDate=$("#etime"+id).val().trim();
   if(sDate!=null&&sDate!=""&&eDate!=null&&eDate!=""){
	   var s=new Date(sDate);
	   var e=new Date(eDate);
	   if(s.getTime()<=e.getTime()){
		   $("#gtime"+id).val((e.getTime()-s.getTime())/1000/60/60/24+1);
	   }else{
		   alert("结束时间要大于等于开始时间");
		   $("#gtime"+id).val(0);
	   }
	  
   }
}
function addCCXC(){
	index++;
		var tr="";
		tr+='<tr>';
		tr+='<td style="width: 30px" class="center"><input type="checkbox"></td>';
		tr+='<td style="width: 30px" class="center">'+index+'</td>';
		tr+='<td style="width: 120px"><input id="stime'+index+'"';
		tr+='style="color: hsl(209, 77%, 53%)" class="inputDate" /><img ';
		tr+='alt="" src="date/skin/datePicker.gif" class="simg'+index+' cursor sbtn"><br>';
		tr+='&nbsp;&nbsp;&nbsp;&nbsp; 至<br> <input id="etime'+index+'"';
		tr+='style="color: hsl(209, 77%, 53%)" class="inputDate" /><img ';
		tr+='alt="" src="date/skin/datePicker.gif" class="eimg'+index+' cursor ebtn">';
		tr+='</td>';
		tr+='<td style="width: 120px"><input class="inputDate"><br>';
		tr+='&nbsp;&nbsp;&nbsp;&nbsp;至<br> <input class="inputDate">';
		tr+='</td>';
		tr+='<td style="width: 80px"><input class="inputNum center" value="1">';
		tr+='</td>';
		tr+='<td style="width: 80px"><input class="inputNum center"';
		tr+='id="gtime'+index+'"></td>';
		tr+='<td style="width: 100px"><select class="selectList">';
		tr+='	<option value>==请选择==</option>';
		tr+='<option value="1">火车软卧</option>';
		tr+='<option value="2">火车硬卧</option>';
		tr+='<option value="3">动车一等票</option>';
		tr+='<option value="4">动车二等票</option>';
		tr+='<option value="5">飞机经济舱</option>';
		tr+='<option value="6">轮船二等舱</option>';
		tr+='<option value="7">轮船三等舱</option>';
		tr+='<option value="8">公共汽车</option>';
		tr+='<option value="9">搭便车</option>';
		tr+='<option value="10">其他</option>';
		tr+='<option value="11">无</option>';
		tr+='</select></td>';
		tr+='<td style="width: 80px">';
		tr+='<input class="inputNum center traffic'+index+' getTr" onblur="sumMon()">';
		tr+='</td>';
		tr+='<td style="width: 100px"><select class="selectList">';
		tr+='<option value>==请选择==</option>';
		tr+='<option value="360">360</option>';
		tr+='<option value="320">320</option>';
		tr+='<option value="300">300</option>';
		tr+='<option value="280">280</option>';
		tr+='<option value="260">260</option>';
		tr+='<option value="220">220</option>';
		tr+='<option value="180">180</option>';
		tr+='<option value="100">100</option>';
		tr+='<option value="0" selected>无</option>';

		tr+='</select></td>';
		tr+='<td style="width: 80px">';
		tr+='<input class="inputNum center stay'+index+' getTr" onblur="sumMon()">';
		tr+='</td>';
		tr+='<td style="width: 80px">';
		tr+='<input class="inputNum center other'+index+' getTr" onblur="sumMon()">';
		tr+='</td>';
		tr+='<td style="width: 80px">';
		tr+='<input class="inputNum center sumMon'+index+'">';
		tr+='</td>';
		tr+='<td class="center">';
		tr+='<img src="images/del.png" class="del">';
		tr+='</td>';
		tr+='</tr>';
		var $tr=$(tr);
		$(".appendTr").before($tr);
}
function delTr(){
	index--;
	var tr=$(this).parent();
	var point=$(tr.siblings()[1]).text();
	tr.parent().remove();
	var trList=$(".ccxc").find("tr");
	for(var i=point;i<trList.length;i++){
		$($(trList[i]).find("td")[1]).text(i);
	}
}
function sumMon(){
	var id=$($(Tr[0]).find("td")[1]).text();
	var traffic=jude($(".traffic"+id).val().trim());
	var stay=jude($(".stay"+id).val().trim());
	var other=jude($(".other"+id).val().trim());
	var sum;
	if(!num.test(traffic)||!num.test(stay)||!num.test(other)){
		alert("输入正确格式的数字");
		return true;
	}else{
		sum=Number(traffic)+Number(stay)+Number(other);
		$(".sumMon"+id).val(sum);
	}
}
function getTr(){
	Tr=$(this).parent().parent();
}
function jude(jude){
	if(jude==null||jude==""){
		jude=0;
	}
	return jude
}