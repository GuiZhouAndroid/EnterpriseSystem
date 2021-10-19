package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.LeaveTable;
import io.dtchain.utils.Result;

public interface ApprovalService {
	/**
	 * 请假申请
	 * 
	 * @param leaveType			请假类型
	 * @param startTime			开始时间
	 * @param endTime			结束时间
	 * @param leaveNum			请假天数
	 * @param leaveRegard		请假事由
	 * @param approver			审批人
	 * @return
	 */
	public Result<Object> leaveApplication(String leaveType,String startTime, String endTime,int leaveNum, String leaveRegard, String approver);
	
	/**
	 * 待审批总数
	 * 
	 * @param approverStatue 	审批状态
	 * @return
	 */
	public Result<Object> queryCount(int approverStatue);
	
	/**
	 * 获取待审批记录
	 * 
	 * @param page				当前页数		
	 * @param approverStatue	审批状态
	 * @return
	 */
	public Result<List<LeaveTable>> getPendingApproval(int page,int approverStatue);
	
	/**
	 * 已审批总数
	 * @return
	 */
	public Result<Object> queryApprovalCount(String applicant,String createStartTime,String createEndTime,int approverStatue,int statue);
	
	/**
	 * 获取已审批记录
	 * @param page				当前页数
	 * @return
	 */
	public Result<List<LeaveTable>> getApproval(int page,String applicant,String createStartTime,String createEndTime,int approverStatue,int statue);
	
	/**
	 * 审批操作
	 * @param id				请假记录id
	 * @param approverStatue	审批状态
	 * @return
	 */
	public Result<Object> operation(String id,int approverStatue);
	
	
	/**
	 * 删除待审批记录
	 * @param id				请假记录id
	 * @return
	 */
	public Result<Object> delApproval(String id);
}
