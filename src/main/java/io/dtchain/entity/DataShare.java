package io.dtchain.entity;

import java.sql.Timestamp;

public class DataShare {
	private String id;
	private String userId;
	private String title;
	private String ramark;
	private String docTitle;
	private Timestamp createTime;
	private int fabulous;
	private int stamp;
	private String empName;
	private String empDept;
	private String suffix;

	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpDept() {
		return empDept;
	}
	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRamark() {
		return ramark;
	}
	public void setRamark(String ramark) {
		this.ramark = ramark;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public int getFabulous() {
		return fabulous;
	}
	public void setFabulous(int fabulous) {
		this.fabulous = fabulous;
	}
	public int getStamp() {
		return stamp;
	}
	public void setStamp(int stamp) {
		this.stamp = stamp;
	}
	@Override
	public String toString() {
		return "DataShare [id=" + id + ", userId=" + userId + ", title=" + title + ", ramark=" + ramark + ", docTitle="
				+ docTitle + ", createTime=" + createTime + ", fabulous=" + fabulous + ", stamp=" + stamp + ", empName="
				+ empName + ", empDept=" + empDept + ", suffix=" + suffix + "]";
	}
	
}
