package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.dtchain.entity.EmpInfo;
import io.dtchain.entity.UserResoure;

public interface AuthorityDao {
	
	/**
	 * 查询员工信息
	 * 
	 * @param map		当前页数
	 * @return
	 */
	public List<EmpInfo> queryEmpInfo(Map<String, Object> map);
	
	/**
	 * 搜索员工（部门或个人）
	 * @param map
	 * @return
	 */
	public List<EmpInfo> querySearchEmpInfo(Map<String,Object> map);

	/**
	 * 查询员工总数
	 * 
	 * @return
	 */
	public int queryCount();

	/**
	 * 搜索员工数(部门或个人)
	 * @return
	 */
	public int querySearchCount(@Param(value = "value") String value);
	/**
	 * 添加权限
	 * 
	 * @param map		资源id,员工id
	 * @return
	 */
	public int addAuthority(Map<String, Object> map);

	/**
	 * 删除权限
	 * 
	 * @param map		资源id,员工id
	 * @return
	 */
	public int delAuthority(Map<String, Object> map);
	
	/**
	 * 获取用户资源id
	 * @param userId
	 * @param resourceId
	 * @return
	 */
	public UserResoure getUserResoure(@Param(value = "userId") String userId,@Param(value = "resourceId") String resourceId);
}
