package io.dtchain.entity;

/**
 * 数据处理表
 * 
 * @author acer-yu
 *
 */
public class DataProceTable {
	private String empName;
	private String dept;
	private float days;
	private int hours;
	private int late;
	private int earlyRetr;
	private String dates;
	private int overTime;

	public int getOverTime() {
		return overTime;
	}

	public void setOverTime(int overTime) {
		this.overTime = overTime;
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

	@Override
	public String toString() {
		return "DataProceTable [empName=" + empName + ", dept=" + dept + ", days=" + days + ", hours=" + hours
				+ ", late=" + late + ", earlyRetr=" + earlyRetr + ", dates=" + dates + ", overTime=" + overTime + "]";
	}

}
