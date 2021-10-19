package io.dtchain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.EmpInfo;
import io.dtchain.service.AuthorityService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value="/authority")
@Api(value = "/authority", description = "权限管理相关接口")
@CrossOrigin
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;
	
	@ApiOperation(value = "查询员工信息")
	@GetMapping(value="/empInfo.io")
	@ResponseBody
	public Result<List<EmpInfo>> queryEmpInfo(@ApiParam(value = "当前页面", required = true) @RequestParam(value = "page") Integer page,
											  @ApiParam(value = "搜索值", required = false) @RequestParam(value = "value") String value,
											  @ApiParam(value = "是否搜索状态",required = true) @RequestParam(value = "statue") Integer statue){
		return authorityService.queryEmpInfo(page,value,statue);
	}
	
	@ApiOperation(value = "查询员工总数")
	@GetMapping(value="/queryCount.io")
	@ResponseBody
	public Result<Object> queryCount(@ApiParam(value = "搜索值", required = false) @RequestParam(value = "value") String value,
								     @ApiParam(value = "是否搜索状态",required = true) @RequestParam(value = "statue") Integer statue){
		return authorityService.queryCount(value,statue);
	}
	
	@ApiOperation(value = "添加权限")
	@PostMapping(value="/addAuthority.io")
	@ResponseBody
	public Result<Object> addAuthority(@ApiParam(value = "资源id", required = true) @RequestParam(value="resourceId[]") Integer[] resourceId,
									   @ApiParam(value = "员工id", required = true) @RequestParam(value="empId") String empId){
		
		return authorityService.addAuthority(resourceId, empId);
	}
	
	@ApiOperation(value = "查询权限")
	@GetMapping(value="/queryAuthorityId.io")
	@ResponseBody
	public Result<List<Integer>> queryAuthorityId(@ApiParam(value = "员工id", required = true)  @RequestParam(value="empId") String empId){
		return authorityService.queryAuthorityId(empId);
	}
}
