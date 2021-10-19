package io.dtchain.serviceImpl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.dtchain.dao.ResourceDao;
import io.dtchain.entity.Resource;
import io.dtchain.service.ResourceService;
import io.dtchain.utils.Result;
import io.swagger.annotations.ApiOperation;

@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceDao resourceDao;

	// 加载用户资源 
	public List<Resource> loadUserResources(Map<String, Object> map) {
		return resourceDao.loadUserResources(map);
	}

	//查询所有资源
	public List<Resource> queryAll() {
		return resourceDao.queryAll();
	}

}
