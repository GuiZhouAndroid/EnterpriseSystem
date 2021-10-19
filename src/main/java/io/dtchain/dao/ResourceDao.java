package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.Resource;

public interface ResourceDao {
	
	/**
	 * 加载用户资源
	 * 
	 * @param map
	 * @return
	 */
	public List<Resource> loadUserResources(Map<String, Object> map);

	/**
	 * 获取所有资源
	 * 
	 * @return
	 */
	public List<Resource> queryAll();

	/**
	 * 获取资源id
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> queryId(String userId);

	/**
	 * 获取资源名称
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> queryResourceName(String userId);
}
