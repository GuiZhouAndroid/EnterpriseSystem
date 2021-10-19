package io.dtchain.service;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.Resource;
import io.dtchain.utils.Result;

public interface ResourceService {
	
	/**
	 * 加载用户资源
	 * 
	 * @param map		用户信息
	 * @return
	 */
	public List<Resource> loadUserResources(Map<String, Object> map);

	/**
	 * 加载全部资源
	 * 
	 * @return
	 */
	public List<Resource> queryAll();
}
