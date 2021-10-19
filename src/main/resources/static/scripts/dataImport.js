$(document).ready(function() {
	$(".com").load("com");
	layui.use('laydate', function() {
		var laydate = layui.laydate;

		// 执行一个laydate实例
		laydate.render({
			elem : '#date' // 指定元素

		});
		laydate.render({
			elem : '#date1'// 指定元素
		});
	});
})