package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.dtchain.dao.AuthorityDao;
import io.dtchain.dao.ResourceDao;
import io.dtchain.entity.EmpInfo;
import io.dtchain.service.AuthorityService;
import io.dtchain.utils.Result;

@Service("AuthorityService")
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private ResourceDao resourcesDao;
	
	@Override
	public Result<List<EmpInfo>> queryEmpInfo(Integer page,String value,Integer statue) {
		Map<String, Object> map = new HashMap<String, Object>();
		int n = (page <= 0 ? 1 : (page - 1) * 8);
		map.put("page", n);
		Result<List<EmpInfo>> result = new Result<List<EmpInfo>>();
		List<EmpInfo> list=new ArrayList<EmpInfo>();
		if(statue==1) {
			map.put("value", value);
			list=authorityDao.querySearchEmpInfo(map);
		}else {
			list = authorityDao.queryEmpInfo(map);
		}
		result.setData(list);
		result.setState(1);
		return result;
	}

	@Override
	public Result<Object> queryCount(String value,Integer statue) {
		
		Result<Object> result = new Result<Object>();
		if(statue==1) {
			result.setData(authorityDao.querySearchCount(value));
		}else {
			result.setData(authorityDao.queryCount());
		}
		result.setState(1);
		return result;
	}

	@Override
	public Result<Object> addAuthority(Integer[] resourceIdArray, String empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Result<Object> result = new Result<Object>();
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
		// 从数据库获取，该权限
		List<String> list = resourcesDao.queryResourceName(userId);
		if (list == null || list.size() < 1 || !list.contains("/jurisdiction")) {
			result.setMsg("联系管理员获取该权限");
			result.setState(-1);
			return result;
		}

		List<Integer> listId = resourcesDao.queryId(empId);
		if ((listId == null || listId.size() == 0) && (resourceIdArray.length == 1)) {
			result.setMsg("无权限分配");
			result.setState(0);
			return result;
		}
		if (listId == null || listId.size() == 0) {
			for (int i = 1; i < resourceIdArray.length; i++) {
				map.put("userId", empId);
				map.put("resourceId", resourceIdArray[i]);
				authorityDao.addAuthority(map);
				map.clear();
			}
			result.setMsg("权限分配成功");
			result.setState(1);
			return result;
		}

		if (resourceIdArray.length == 1) {
			for (int i = 0; i < listId.size(); i++) {
				map.put("userId", empId);
				map.put("resourceId", listId.get(i));
				authorityDao.delAuthority(map);
				map.clear();
			}
			result.setMsg("清空权限成功");
			result.setState(2);
			return result;
		}

		for (int i = 1; i < resourceIdArray.length; i++) {
			if (!listId.contains(resourceIdArray[i])) {
				map.put("userId", empId);
				map.put("resourceId", resourceIdArray[i]);
				authorityDao.addAuthority(map);
				map.clear();
			}
		}

		for (int i = 0; i < listId.size(); i++) {
			boolean flog = true;
			for (int j = 1; j < resourceIdArray.length; j++) {
				if (listId.get(i) == resourceIdArray[j]) {
					flog = false;
					break;
				}
			}
			if (flog) {
				map.put("userId", empId);
				map.put("resourceId", listId.get(i));
				authorityDao.delAuthority(map);
				map.clear();
				flog = true;
			}
		}
		result.setMsg("修改权限成功");
		result.setState(3);
		return result;
	}

	@Override
	public Result<List<Integer>> queryAuthorityId(String empId) {
		List<Integer> listId = resourcesDao.queryId(empId);
		Result<List<Integer>> result = new Result<List<Integer>>();
		result.setData(listId);
		return result;
	}
	@Override
	public Result<Object> authorityUrl(String url) {
		Result<Object> result = new Result<Object>();
		String getAuthotity = (String) SecurityUtils.getSubject().getSession().getAttribute(url);
		if (getAuthotity != null && getAuthotity.length() > 0) {
			result.setMsg("拥有该权限");
			result.setState(1);
		} else {
			result.setMsg("没有改权限");
			result.setState(0);
		}
		return result;
	}

}
