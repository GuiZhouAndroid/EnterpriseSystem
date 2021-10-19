package io.dtchain.entity;

public class QueryLeave {
	private String value;
	private String name;
	private String startDate;
	private String endDate;
	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "QueryLeave [value=" + value + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", page=" + page + "]";
	}

}
