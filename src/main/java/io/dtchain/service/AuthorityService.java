package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.EmpInfo;
import io.dtchain.utils.Result;

public interface AuthorityService {
	
	/**
	 * 查询员工信息
	 * 
	 * @param page			当前页面
	 * @param value         搜索值
	 * @param statue		搜索状态
	 * @return
	 */
	public Result<List<EmpInfo>> queryEmpInfo(Integer page,String value,Integer statue);

	/**
	 * 查询员工总数
	 * @param value         搜索值
	 * @param statue		搜索状态
	 * @return
	 */
	public Result<Object> queryCount(String value,Integer statue);

	/**
	 * 添加权限
	 * 
	 * @param resourceId	资源id
	 * @param empId			员工id
	 * @return
	 */
	public Result<Object> addAuthority(Integer[] resourceId, String empId);

	/**
	 * 查询权限
	 * 
	 * @param empId			员工id
	 * @return
	 */
	public Result<List<Integer>> queryAuthorityId(String empId);
	
	/**
	 * 查询是否拥有该权限url
	 * 
	 * @param url			权限url
	 * @return
	 */
	public Result<Object> authorityUrl(String url);

}
