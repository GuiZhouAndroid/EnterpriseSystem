package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.EmpInfo;

public interface MangageDao {

	/**
	 * 添加员工信息
	 * 
	 * @param emp			员工信息
	 * @return
	 */
	public int addEmp(EmpInfo emp);

	/**
	 * 搜索员工信息
	 * 
	 * @param map			部门名称,limit开始的数据标号
	 * @return
	 */
	public List<EmpInfo> queryDeptEmpInfo(Map<String, Object> map);

	/**
	 * 删除员工信息
	 * 
	 * @param map			员工id
	 * @return
	 */
	public int delEmpInfo(Map<String, Object> map);

	/**
	 * 更新员工信息
	 * 
	 * @param emp			员工信息
	 * @return
	 */
	public int upEmpInfo(EmpInfo emp);

	/**
	 * 查询员工部门信息
	 * 
	 * @param empName		员工名字
	 * @return
	 */
	public String queryDept(String empName);

	/**
	 * 查询员工名字与工号
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryNum();

	/**
	 * 查询员工账号密码
	 * 
	 * @param name			员工名字
	 * @return
	 */
	public EmpInfo queryInfo(String name);
	
	/**
	 * 获取审批人信息
	 * 
	 * @param map			当前页面,每页多少条
	 * @return
	 */
	public List<EmpInfo> queryApprovalInfo(Map<String,Object> map);
	
	/**
	 * 查询员工总数(查询审批人  含admin账号)
	 * 
	 * @return
	 */
	public int queryEmpCount();
	
	/**
	 * 搜索员工总数
	 * 
	 * @param searchValue    部门名称或员工名字
	 * @return
	 */
	public int queryCount(String searchValue);
	
	/**
	 * 修改密码
	 * 
	 * @param map
	 * @return
	 */
	public int updatePass(Map<String,Object> map);
	
	/**
	 * 获取初始化员工记录
	 * @return
	 */
	public List<EmpInfo> getInitInfo(Map<String,Object> map);
	
	/**
	 * 初始化员工总数
	 * @return
	 */
	public int getInitInfoCount();
	
	/**
	 * 密码重置
	 * @return
	 */
	public int passwordReset(Map<String,Object> map);
	
	/**
	 * 获取工号
	 * @param empId		员工id
	 * @return
	 */
	public EmpInfo getNum(String empId);
	
	/**
	 * 根据id获取需要更改的员工信息
	 * @param empId
	 * @return
	 */
	public EmpInfo getEmpInfoById(String empId);
}
