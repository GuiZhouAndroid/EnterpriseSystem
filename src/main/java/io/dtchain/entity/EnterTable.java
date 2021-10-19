package io.dtchain.entity;

public class EnterTable {
	private String empName;
	private String dates;
	private String workMorn;
	private String workAfter;
	private String minTime;

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getWorkMorn() {
		return workMorn;
	}

	public void setWorkMorn(String workMorn) {
		this.workMorn = workMorn;
	}

	public String getWorkAfter() {
		return workAfter;
	}

	public void setWorkAfter(String workAfter) {
		this.workAfter = workAfter;
	}

	@Override
	public String toString() {
		return "EnterTable [empName=" + empName + ", dates=" + dates + ", workMorn=" + workMorn + ", workAfter="
				+ workAfter + "]";
	}

}
