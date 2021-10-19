package io.dtchain.entity;

import java.sql.Timestamp;

public class LeaveTable
{
	private String id;
	private String leaveType;
	private String startTime;
	private String endTime;
	private int leaveNum;
	private Timestamp createTime;
	private String leaveRegard;
	private String approver;
	private int  approverStatue;
	private String applicant;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getLeaveNum() {
		return leaveNum;
	}
	public void setLeaveNum(int leaveNum) {
		this.leaveNum = leaveNum;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getLeaveRegard() {
		return leaveRegard;
	}
	public void setLeaveRegard(String leaveRegard) {
		this.leaveRegard = leaveRegard;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public int getApproverStatue() {
		return approverStatue;
	}
	public void setApproverStatue(int approverStatue) {
		this.approverStatue = approverStatue;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	@Override
	public String toString() {
		return "LeaveTable [id=" + id + ", leaveType=" + leaveType + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", leaveNum=" + leaveNum + ", createTime=" + createTime + ", leaveRegard=" + leaveRegard
				+ ", approver=" + approver + ", approverStatue=" + approverStatue + ", applicant=" + applicant + "]";
	}
	
	
}
