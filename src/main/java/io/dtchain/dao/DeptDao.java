package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.DeptInfo;

public interface DeptDao {
	
	/**
	 * 添加部门
	 * 
	 * @param map		部门信息
	 * @return
	 */
	public int addDept(DeptInfo dept);

	/**
	 * 删除部门
	 * 
	 * @param id		部门id
	 * @return
	 */
	public int delDept(String id);

	/**
	 * 查询部门信息
	 * 
	 * @return
	 */
	public List<DeptInfo> queryDept(Map<String,Object> map);

	/**
	 * 修改部门信息
	 * 
	 * @param dept
	 * @return
	 */
	public int upDept(DeptInfo dept);
	
	/**
	 * 部门总数
	 * @return
	 */
	public int queryCount();
	
	/**
	 * 部门信息
	 * @return
	 */
	public List<DeptInfo> queryDeptInfo();
	
	public DeptInfo getDeptInfoById(String id);

}
