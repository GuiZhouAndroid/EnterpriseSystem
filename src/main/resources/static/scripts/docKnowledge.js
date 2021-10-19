$(document).ready(function(){
	$(".com").load("com");
	show();
});
function show(){
	var url = location.href;
	var start = url.indexOf("=");
	var id = url.substr(start + 1);
	if(id!=''){
		$.ajax({
			url:"dataShare/getDocumentContent.io",
			type:"get",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				
				if(result.state==1){
					createLi(result.data);
				}
			},
			error:function(){
				
			}
		});
	}
}
function createLi(data){
	console.log(data);
	var times = new Date();
	times.setTime(data.createTime);
	var li="";
	li+='<li class="docTitle">'+data.title+'</li>';
	li+='<li class="docInfo">作者:&nbsp;&nbsp;&nbsp;<b>'+data.empName+'</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	if(data.empDept!=null){
		li+='部门:&nbsp;&nbsp;&nbsp;'+data.empDept+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	li+='时间:&nbsp;&nbsp;&nbsp;'+times.toLocaleDateString();
	li+='</li>';
	li+='<li><img alt="" src="images/link.png">文档附件(1)</li>';
	li+='<li><br></li>';
	li+='<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+data.ramark;
	li+='</li>';
	li+='<li><hr></li>';
	li+='<li><img src="images/doc.png">&nbsp;<a href="http://localhost:9000/file/'+data.id+".pdf"+'">'+data.docTitle+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	//li+='<a href="preview/web/viewer.html?id='+data.id+'" target="_blank"><img src="images/open.png" title="打开" ></a>';
	//var pdf=data.docTitle.substring(0,data.docTitle.indexOf("."))+".pdf";
	var pdf=data.id+".pdf";
	li+='<a href="http://localhost:9000/file/'+pdf+'" target="_blank"><img src="images/open.png" title="打开" ></a>';
	li+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	li+='<a href="http://localhost:9000/file/'+data.id+data.suffix+'"><img src="images/dowload.png" title="下载"></a>';
	li+='</li>';
	var $li=$(li);
	$('.ulList').append($li);
}