package io.dtchain.entity;

/**
 * 检索考勤记录的条件实体类
 * 
 * @author acer-yu
 *
 */
public class QueryRecord {

	private String empName;

	private String empDept;

	private String start;

	private String end;

	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "QueryRecord [empName=" + empName + ", empDept=" + empDept + ", start=" + start + ", end=" + end
				+ ", page=" + page + "]";
	}

}
