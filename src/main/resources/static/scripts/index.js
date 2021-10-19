$(document).ready(function() {
	$(".com").load("com");
	
	var data=initData();
	var ctx = document.getElementById('MyChart1').getContext('2d');
	var chart = new Chart(ctx, {
		type : 'bar',

		data : {
			labels : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ],
			datasets : [ {
				label : "迟到次数",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : data[0],
				backgroundColor : "#84c1ff"
			}, {
				label : "早退次数",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : data[1],
				backgroundColor : "#aaffaa"
			}, {
				label : "加班小时数",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : data[2],
				backgroundColor : "#ffb693"
			} ]
		},
		options : {
			responsive : false,
			aspectRatio : 1
		}
	});

	var ctx1 = document.getElementById('MyChart2').getContext('2d');
	var chart = new Chart(ctx1, {
		type : 'line',
		data : {
			labels : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ],
			datasets : [ {
				label : "上班天数",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				borderColor : "#84c1ff",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : data[3]
			} ]
		},
		options : {
			responsive : false,
			aspectRatio : 1
		}
	});
	
	
	
	$("#link1").click(function(){
		var url_base64 = document.getElementById("MyChart1").toDataURL("image/png");
        link1.href = url_base64;
        var url = link1.href.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
	});
	$("#link2").click(function(){
		var ur2_base64 = document.getElementById("MyChart2").toDataURL("image/png");
        link2.href = ur2_base64;
        var url = link2.href.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
	});
})

function initData(){
	var data;
	$.ajax({
		url:"chart/getChartData.io",
		type:"get",
		async:false,
		success:function(result){
			data=result.data;
		},
		error:function(){
			alert("初始化数据失败");
		}
	});
	return data;
}