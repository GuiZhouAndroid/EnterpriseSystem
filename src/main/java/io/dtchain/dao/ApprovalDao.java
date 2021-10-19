package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.dtchain.entity.LeaveTable;

public interface ApprovalDao {
	
	/**
	 * 请假申请
	 * 
	 * @param leave			请假信息
	 * @return
	 */
	public int leaveApplication(LeaveTable leave);
	
	/**
	 * 获取待审批总数
	 * 
	 * @param map			待审批参数
	 * @return
	 */
	public int queryCount(Map<String,Object> map);
	
	/**
	 * 获取待审批记录
	 * 
	 * @param map			待审批参数
	 * @return
	 */
	public List<LeaveTable> getPendingApproval(Map<String,Object>map);
	
	/**
	 * 已审批总数
	 * @param applicant
	 * @return
	 */
	public int queryApprovalCount(@Param(value = "applicant")String applicant);
	
	/**
	 * 根据搜索条件查询总数(已审批)
	 * @param map
	 * @return
	 */
	public int querySearchApprovalCount(Map<String,Object> map);
	
	/**
	 * 获取已审批记录
	 * @param map
	 * @return
	 */
	public List<LeaveTable> getApproval(Map<String,Object> map);
	
	/**
	 * 根据搜索条件查询审批记录
	 * @param map
	 * @return
	 */
	public List<LeaveTable> getSearchApproval(Map<String,Object> map);
	
	/**
	 * 审批操作
	 * @param map
	 * @return
	 */
	public int operation(Map<String,Object> map);
	
	/**
	 * 删除待审批记录
	 * @param id	
	 * @return
	 */
	public int delApproval(String id);
}
