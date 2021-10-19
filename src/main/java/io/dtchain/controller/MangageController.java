package io.dtchain.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.EmpInfo;
import io.dtchain.service.AuthorityService;
import io.dtchain.service.MangageService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@Controller
@RequestMapping(value="/mangage")
@Api(value = "/mangage", description = "员工管理相关接口")
public class MangageController {

	@Autowired
	private MangageService mangageService;
	@Autowired
	private AuthorityService authorityService;
	
	@ApiOperation(value = "登陆校验")
	@PostMapping(value="/login.io")
	@ResponseBody
	public Result<Object> login(@ApiParam(value = "用户名", required = true) @RequestParam(value = "username")String username,
					   			@ApiParam(value = "密码", required = true) @RequestParam(value = "pass")String pass,
					   			HttpServletRequest req,HttpServletResponse res) {
		res.setHeader("Access-Control-Allow-Origin", "*");
		return mangageService.login(username, pass);
	}
	 
	 
	@ApiOperation(value = "添加员工")
	@PostMapping(value="/addEmp.io")
	@ResponseBody
	public Result<Object> addEmp(EmpInfo emp) {
		return mangageService.addEmp(emp);
	}

	@ApiOperation(value = "查询部门员工信息")
	@GetMapping(value="/allSearch.io")
	@ResponseBody
	public Result<List<EmpInfo>> queryDeptEmpInfo(@ApiParam(value = "部门名称", required = true) @RequestParam(value = "searchValue") String searchValue,
											      @ApiParam(value = "当前页数", required = true) @RequestParam(value = "page") int page) {
		return mangageService.queryDeptEmpInfo( searchValue, page);
	}

	@ApiOperation(value = "删除员工信息")
	@DeleteMapping(value = "/delEmpInfo.io")
	@ResponseBody
	public Result<Object> delEmpInfo(@ApiParam(value = "员工id", required = true)  @RequestBody String empId) {
		return mangageService.delEmpInfo(empId);
	}

	@ApiOperation(value = "修改员工信息")
	@PutMapping(value="/upEmpInfo.io")
	@ResponseBody
	public Result<Object> upEmpInfo(EmpInfo emp) {
		return  mangageService.upEmpInfo(emp);
	}
	
	@ApiOperation(value = "查询是否拥有员工管理权限")
	@GetMapping(value="/authorityUrl.io")
	@ResponseBody
	public Result<Object> authorityUrl(@ApiParam(value = "员工管理资源url", required = true)  @RequestParam(value = "url") String url){
		return authorityService.authorityUrl(url);
	}
	
	@ApiOperation(value = "搜索员工总数")
	@GetMapping(value = "/queryCount.io")
	@ResponseBody
	public Result<Object> queryCount(@ApiParam(value = "部门名称", required = true) @RequestParam(value = "searchValue") String deptName){
		return mangageService.queryCount(deptName);
	}
	
	@ApiOperation(value = "修改密码")
	@PutMapping(value = "/updatePass.io")
	@ResponseBody
	public Result<Object> updatePass(@ApiParam(value = "原密码", required = true) @RequestParam(value = "oriPass") String oriPass,
									 @ApiParam(value = "新密码", required = true) @RequestParam(value = "newPass") String newPass){
		return mangageService.updatePass(oriPass, newPass);
	}
	
	@ApiOperation(value = "初始化员工信息总数")
	@GetMapping(value = "/getInitInfoCount.io")
	@ResponseBody
	public Result<Object> getInitInfoCount(){
		return mangageService.getInitInfoCount();
	}
	
	@ApiOperation(value = "初始化员工信息记录")
	@GetMapping(value = "/getInitInfo.io")
	@ResponseBody
	public Result<List<EmpInfo>> getInitInfo(@ApiParam(value = "当前页数",required = true) @RequestParam(value = "page") int page){
		return mangageService.getInitInfo(page);
	}
	
	@ApiOperation(value = "密码重置")
	@PostMapping(value = "/passwordReset.io")
	@ResponseBody
	public Result<Object> passwordReset(@ApiParam(value = "员工id",required = true) @RequestParam(value = "empId") String empId){
		return mangageService.passwordReset(empId);
	}
	
	@ApiOperation(value = "根据id获取需要更改的员工信息")
	@GetMapping(value = "/getEmpInfoById.io")
	@ResponseBody
	public Result<EmpInfo> getEmpInfoById(@ApiParam(value = "员工id",required = true) @RequestParam(value = "empId") String empId){
		return mangageService.getEmpInfoById(empId);
	}
}
