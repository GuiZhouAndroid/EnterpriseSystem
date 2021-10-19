package io.dtchain.entity;

public class RecordTable implements Comparable<RecordTable> {
	private String empName;
	private String dates;
	private String workMorn;
	private String atNoon;
	private String workAfter;
	private String atNight;
	private String dept;
	private String conBin;

	public String getConBin() {
		return conBin;
	}

	public void setConBin(String conBin) {
		this.conBin = conBin;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
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
		return "RecordTable [empName=" + empName + ", dates=" + dates + ", workMorn=" + workMorn + ", atNoon=" + atNoon
				+ ", workAfter=" + workAfter + ", atNight=" + atNight + ", dept=" + dept + ", conBin=" + conBin + "]";
	}

	public int compareTo(RecordTable o) {

		return this.getConBin().compareTo(o.getConBin());
	}

}
