package io.dtchain.entity;

/**
 * 用于储存返回的数据处理结果
 * 
 * @author acer-yu
 *
 */
public class ResultProce {
	private String empName;
	private String dept;
	private float days;
	private int hours;
	private int late;
	private int earlyRetr;
	private int overTime;
	private String dates;

	@Override
	public String toString() {
		return "ResultProce [empName=" + empName + ", dept=" + dept + ", days=" + days + ", hours=" + hours + ", late="
				+ late + ", earlyRetr=" + earlyRetr + ", overTime=" + overTime + ", dates=" + dates + "]";
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public float getDays() {
		return days;
	}

	public void setDays(float days) {
		this.days = days;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getLate() {
		return late;
	}

	public void setLate(int late) {
		this.late = late;
	}

	public int getEarlyRetr() {
		return earlyRetr;
	}

	public void setEarlyRetr(int earlyRetr) {
		this.earlyRetr = earlyRetr;
	}

	public int getOverTime() {
		return overTime;
	}

	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}
}
