package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.DeptInfo;
import io.dtchain.utils.Result;

public interface DeptService {
	
	/**
	 * 添加部门
	 * 
	 * @param deptName		部门名称
	 * @param remark		备注内容
	 * @return
	 */
	public Result<Object> addDept(DeptInfo dept);

	/**
	 * 删除部门 
	 * @param id			部门id
	 * @return
	 */
	public Result<Object> delDept(String id);

	/**
	 * 查询部门信息
	 * @param  				当前页数
	 * @return
	 */
	public Result<List<DeptInfo>> queryDept(int page);

	/**
	 * 修改部门信息
	 * @param dept			部门信息
	 * @return
	 */
	public Result<Object> upDept(DeptInfo dept);
	
	/**
	 * 部门总数
	 * @return
	 */
	public Result<Object> queryCount();
	
	/**
	 * 部门信息
	 * @return
	 */
	public Result<List<DeptInfo>> queryDeptInfo();
	
	/**
	 * 获取需要修改的部门信息
	 * @param id
	 * @return
	 */
	public Result<DeptInfo> getDeptInfoById(String id);
}
