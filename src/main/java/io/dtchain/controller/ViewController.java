package io.dtchain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(description = "页面跳转相关接口")
@CrossOrigin
public class ViewController {

	@ApiOperation(value = "登陆页面")
	@GetMapping(value="/login")
	public String login() {
		return "login";
	}
	
	@ApiOperation(value = "系统首页")
	@GetMapping(value="/index")
	public String index() {
		return "index";
	}
	
	@ApiOperation(value = "员工管理页面")
	@GetMapping(value="/stassMang")
	public String mangage() {
		return "mangage";
	}
	
	@ApiOperation(value = "考勤记录页面")
	@GetMapping(value="/record")
	public String record() {
		return "record";
	}
	
	@ApiOperation(value = "考勤管理页面")
	@GetMapping(value="/attend")
	public String attend() {
		return "attend";
	}
	
	@ApiOperation(value = "公告通知页面")
	@GetMapping(value="/notice")
	public String notice() {
		return "notice";
	}
	
	@ApiOperation(value = "数据导入页面")
	@GetMapping(value="/dataImport")
	public String dataImport() {
		return "dataImport";
	}
	
	@ApiOperation(value = "权限管理页面")
	@GetMapping(value="/jurisdiction")
	public String jurisdiction() {
		return "jurisdiction";
	}
	
	@ApiOperation(value = "我的审批页面")
	@GetMapping(value="/myApproval")
	public String myApproval() {
		return "myApproval";
	}
	
	@ApiOperation(value = "公共模板页面")
	@GetMapping(value="/com")
	public String com() {
		return "com";
	}
	
	@ApiOperation(value = "部门管理页面")
	@GetMapping(value="/dept")
	public String dept() {
		return "dept";
	}
	
	@ApiOperation(value = "请假申请页面")
	@GetMapping(value="/approval")
	public String approval() {
		return "approval";
	}
	
	@ApiOperation(value = "信息修改页面")
	@GetMapping(value="/info")
	public String info() {
		return "info";
	}
	
	@ApiOperation(value = "公告编辑页面")
	@GetMapping(value="/editeNotice")
	public String editeNotice() {
		return "editeNotice";
	}
	
	@ApiOperation(value = "异常页面")
	@GetMapping(value="/error")
	public String fail() {
		return "error";
	}
	
	@ApiOperation(value = "未授权页面")
	@GetMapping(value="/404")
	public String notAuthority() {
		return "404";
	}
	
	@ApiOperation(value = "公告详情页面")
	@GetMapping(value = "/noticeOperation")
	public String noticeOperation() {
		return "noticeOperation";
	}
	
	@ApiOperation(value = "修改公告页面")
	@GetMapping(value = "/upNotice")
	public String upNotice() {
		return "upNotice";
	}
	
	@ApiOperation(value = "添加部门")
	@GetMapping(value = "/addDept")
	public String addDept() {
		return "addDept";
	}
	
	@ApiOperation(value = "资料文件上传")
	@GetMapping(value = "/dataUpload")
	public String dataUpload() {
		return "dataUpload";
	}
	
	@ApiOperation(value = "资料详情")
	@GetMapping(value = "/dataDetails")
	public String dataDetails() {
		return "dataDetails";
	}
	
	@ApiOperation(value = "文档管理")
	@GetMapping(value = "/docMangage")
	public String docMangage() {
		return "docMangage";
	}
	@ApiOperation(value = "添加员工")
	@GetMapping(value = "/addEmp")
	public String addEmp() {
		return "addEmp";
	}
	
	@ApiOperation(value = "报销流程")
	@GetMapping(value = "/reim")
	public String reim() {
		return "reim";
	}
}
