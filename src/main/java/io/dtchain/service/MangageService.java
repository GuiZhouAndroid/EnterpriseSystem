package io.dtchain.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import io.dtchain.entity.EmpInfo;
import io.dtchain.utils.Result;
import io.swagger.annotations.ApiParam;

public interface MangageService {
	/**
	 * 添加员工信息
	 * 
	 * @param emp			员工信息
	 * @return
	 */
	public Result<Object> addEmp(EmpInfo emp);

	/**
	 * 搜索员工信息
	 * 
	 * @param searchValue		部门名称	或者员工名字
	 * @param page				当前页数
	 * @return
	 */
	public Result<List<EmpInfo>> queryDeptEmpInfo(String searchValue, int page);

	/**
	 * 删除员工信息
	 * 
	 * @param empId			员工id
	 * @return
	 */
	public Result<Object> delEmpInfo(String empId);

	/**
	 * 更新员工信息
	 * 
	 * @param emp			员工信息
	 * @return
	 */
	public Result<Object> upEmpInfo(EmpInfo emp);

	
	
	/**
	 * 登陆校验
	 * 
	 * @param username		用户名
	 * @param pass			密码
	 * @return
	 */
	public Result<Object> login(String username,String pass);
	
	/**
	 * 查询审批人
	 * 
	 * @param page			当前页数
	 * @param limit			每页多少条
	 * @return
	 */
	public Result<List<EmpInfo>> queryApprovalInfo(int page,int limit);
	
	/**
	 * 搜索员工总数
	 * 
	 * @param searchValue	部门名称或者员工名字
	 * @return
	 */
	public Result<Object> queryCount(String searchValue);
	
	/**
	 * 修改密码
	 * 
	 * @param oriPass		原密码
	 * @param newPass		新密码
	 * @return
	 */
	public Result<Object> updatePass(String oriPass,String newPass);
	
	/**
	 * 获取初始化员工信息总数
	 * @return
	 */
	public Result<Object> getInitInfoCount();
	
	/**
	 * 初始化员工信息记录
	 * @param				当前页数
	 * @return
	 */
	public Result<List<EmpInfo>> getInitInfo(int page);
	
	/**
	 * 密码重置
	 * @param empId			员工id
	 * @return
	 */
	public Result<Object> passwordReset(String empId);
	
	/**
	 * 根据id获取需要更改员工信息
	 * @param empId
	 * @return
	 */
	public Result<EmpInfo> getEmpInfoById( String empId);
}
