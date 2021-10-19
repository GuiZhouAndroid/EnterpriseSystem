package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import io.dtchain.dao.MangageDao;
import io.dtchain.entity.EmpInfo;
import io.dtchain.service.MangageService;
import io.dtchain.utils.Result;
import io.dtchain.utils.Utils;

@Service("mangageService")
public class MangageServiceImpl implements MangageService {
	@Resource
	private MangageDao mangageDao;

	public Result<Object> addEmp(EmpInfo emp) {
		Result<Object> result = new Result<Object>();
		String empId = Utils.createId();
		emp.setEmpId(empId);
		emp.setEmpPass(Utils.Md5(emp.getEmpName(), emp.getEmpNum()));
		int n = mangageDao.addEmp(emp);
		if (n == 1) {
			result.setMsg("添加员工成功");
			result.setState(1);
			result.setData(empId);
		} else {
			result.setMsg("添加员工失败");
			result.setState(0);
		}
		return result;
	}

	public Result<List<EmpInfo>> queryDeptEmpInfo(String searchValue, int page) {
		Result<List<EmpInfo>> result = new Result<List<EmpInfo>>();
		List<EmpInfo> list = new ArrayList<EmpInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		int begin = (page - 1) * 10;
		map.put("begin", begin);
		map.put("info", searchValue);
		list = mangageDao.queryDeptEmpInfo(map);

		result.setData(list);
		result.setMsg("查询成功");
		result.setState(1);
		return result;
	}

	public Result<Object> delEmpInfo(String empId) {
		Result<Object> result = new Result<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empId", empId.substring(empId.indexOf("=")+1));
		int n = mangageDao.delEmpInfo(map);
		if (n > 0) {
			result.setMsg("删除信息成功");
			result.setState(1);
		} else {
			result.setMsg("删除信息是失败");
			result.setState(0);
		}
		return result;
	}

	public Result<Object> upEmpInfo(EmpInfo emp) {
		Result<Object> result = new Result<Object>();
		int n = mangageDao.upEmpInfo(emp);
		if (n > 0) {
			result.setMsg("更新信息成功");
			result.setState(1);
		} else {
			result.setMsg("更新信息失败");
			result.setState(0);
		}
		return result;
	}

	@Override
	public Result<Object> login(String username, String pass) {
		
		Result<Object> result=new Result<Object>();
		Subject subject = SecurityUtils.getSubject();
		 UsernamePasswordToken token=new UsernamePasswordToken(username,pass);
		 try {
	            subject.login(token);
	        } catch(UnknownAccountException ue) {
	        	token.clear();
	        	System.out.println("用户不存在");
	        	result.setMsg("用户不存在");
				result.setState(0);
				return result;
	        	
	        } catch(IncorrectCredentialsException  ie) {
	        	token.clear();
	        	System.out.println("密码错误");
	        	result.setMsg("密码错误");
				result.setState(0);
				return result;
	        } catch (LockedAccountException lae) {
	            token.clear();
	            System.out.println("账号不可用");
	            result.setMsg("账号不可用");
				result.setState(0);
				return result;
	        } catch (ExcessiveAttemptsException  e) {
	            token.clear();
	            System.out.println("尝试次数超限");
	            result.setMsg("尝试次数超限");
				result.setState(0);
				return result;
	        }
		 result.setState(1);
		 result.setMsg("登陆请求成功");
		return result;
	}

	@Override
	public Result<List<EmpInfo>> queryApprovalInfo(int page,int limit) {
		page=page<1?1:page;
		limit=mangageDao.queryEmpCount();
		
		int pageNum=(page-1)*limit;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", pageNum);
		map.put("limit", limit);
		List<EmpInfo> list=mangageDao.queryApprovalInfo(map);
		Result<List<EmpInfo>> result=new Result<List<EmpInfo>>();
		result.setData(list);
		result.setState(1);
		result.setCount(limit);
		return result;
	}

	@Override
	public Result<Object> queryCount(String searchValue) {
		Result<Object> result=new Result<Object>();
		result.setCount(mangageDao.queryCount(searchValue));
		result.setMsg("请求数据成功");
		result.setState(1);
		return result;
	}

	@Override
	public Result<Object> updatePass(String oriPass, String newPass) {
		Result<Object> result=new Result<Object>();
		Map<String,Object> map=new HashMap<String,Object>();
		EmpInfo user=(EmpInfo)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		map.put("empId",user.getEmpId());
		map.put("empPass", Utils.Md5(user.getEmpName(), newPass));
		if(!user.getEmpPass().equals(Utils.Md5(user.getEmpName(), oriPass))) {
			result.setMsg("原密码错误");
			result.setState(0);
		}else {
			mangageDao.updatePass(map);
			result.setMsg("密码修改成功");
			result.setState(1);
		}
		return result;
	}

	@Override
	public Result<Object> getInitInfoCount() {
		Result<Object> result=new Result<Object>();
		result.setCount(mangageDao.getInitInfoCount());
		return result;
	}

	@Override
	public Result<List<EmpInfo>> getInitInfo(int page) {
		Result<List<EmpInfo>> result=new Result<List<EmpInfo>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("begin", (page-1)*10);
		List<EmpInfo> list=new ArrayList<EmpInfo>();
		list=mangageDao.getInitInfo(map);
		result.setData(list);
		return result;
	}

	@Override
	public Result<Object> passwordReset(String empId) {
		Map<String,Object> map=new HashMap<String,Object>();
		Result<Object> result=new Result<Object>();
		EmpInfo empInfo=mangageDao.getNum(empId);
		map.put("empId", empId);
		map.put("empPass", Utils.Md5(empInfo.getEmpName(), empInfo.getEmpNum()));
		int n=mangageDao.passwordReset(map);
		if(n>0) {
			result.setMsg("密码重置成功");
			result.setState(1);
		}else {
			result.setMsg("密码重置失败");
			result.setState(0);
		}
		return result;
	}

	@Override
	public Result<EmpInfo> getEmpInfoById(String empId) {
		EmpInfo emp=mangageDao.getEmpInfoById(empId);
		Result<EmpInfo> result=new Result<EmpInfo>();
		if(emp!=null) {
			result.setData(emp);
			result.setMsg("获取更改信息成功");
			result.setState(1);
		}else {
			result.setMsg("获取更改信息失败");
			result.setState(0);
		}
		return result;
	}
}
