package io.dtchain.entity;

public class OutTable {
	private String empName;
	private String dates;
	private String atNoon;
	private String atNight;
	private String maxTime;
	private String overTime;

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
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

	public String getAtNoon() {
		return atNoon;
	}

	public void setAtNoon(String atNoon) {
		this.atNoon = atNoon;
	}

	public String getAtNight() {
		return atNight;
	}

	public void setAtNight(String atNight) {
		this.atNight = atNight;
	}

	@Override
	public String toString() {
		return "OutTable [empName=" + empName + ", dates=" + dates + ", atNoon=" + atNoon + ", atNight=" + atNight
				+ ", maxTime=" + maxTime + ", overTime=" + overTime + "]";
	}

}
