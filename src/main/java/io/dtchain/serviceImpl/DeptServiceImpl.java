package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import io.dtchain.dao.DeptDao;
import io.dtchain.entity.DeptInfo;
import io.dtchain.service.DeptService;
import io.dtchain.utils.Result;
import io.dtchain.utils.Utils;

@Service("DeptService")
public class DeptServiceImpl implements DeptService {
	@Resource
	private DeptDao deptDao;

	public Result<Object> addDept(DeptInfo dept) {
		Result<Object> result = new Result<Object>();
		dept.setId(Utils.createId());
		int n = deptDao.addDept(dept);
		if (n > 0) {
			result.setData(dept.getId());
			result.setMsg("添加部门成功");
			result.setState(1);
		}
		return result;
	}

	public Result<Object> delDept(String id) {
		Result<Object> result = new Result<Object>();
		int n = deptDao.delDept(id.substring(id.indexOf("=")+1));
		if (n > 0) {
			result.setState(1);
			result.setMsg("删除成功");
		}
		return result;
	}

	public Result<List<DeptInfo>> queryDept(int page) {
		Result<List<DeptInfo>> result = new Result<List<DeptInfo>>();
		List<DeptInfo> list = new ArrayList<DeptInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		int begin = (page - 1) * 10;
		map.put("begin", begin);
		list = deptDao.queryDept(map);
		if (!list.isEmpty()) {
			result.setData(list);
			result.setMsg("查询成功");
			result.setState(1);
		}
		return result;
	}

	public Result<Object> upDept(DeptInfo dept) {
		Result<Object> result = new Result<Object>();
		deptDao.upDept(dept);
		result.setMsg("修改成功");
		result.setState(1);
		return result;
	}

	@Override
	public Result<Object> queryCount() {
		Result<Object> result=new Result<Object>();
		result.setCount(deptDao.queryCount());
		result.setMsg("获取部门总数成功");
		return result;
	}

	@Override
	public Result<List<DeptInfo>> queryDeptInfo() {
		Result<List<DeptInfo>> result=new Result<List<DeptInfo>>();
		List<DeptInfo> list=new ArrayList<DeptInfo>();
		list=deptDao.queryDeptInfo();
		if(list!=null&&list.size()>0) {
			result.setData(list);
			result.setState(1);
			result.setMsg("获取部门信息成功");
		}else {
			result.setState(0);
			result.setMsg("获取部门信息失败");
		}
		return result;
	}

	@Override
	public Result<DeptInfo> getDeptInfoById(String id) {
		DeptInfo dept=deptDao.getDeptInfoById(id);
		Result<DeptInfo> result=new Result<DeptInfo>();
		if(dept!=null) {
			result.setData(dept);
			result.setState(1);
			result.setMsg("获取部门信息成功");
		}else {
			result.setState(0);
			result.setMsg("获取部门信息失败");
		}
		return result;
	}

}
