package io.dtchain.entity;

public class DeptInfo {
	private String id;
	private String deptName;
	private String remark;
	private String director;
	private String address;
	private String phone;
	
	
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "DeptInfo [id=" + id + ", deptName=" + deptName + ", remark=" + remark + ", director=" + director
				+ ", address=" + address + ", phone=" + phone + "]";
	}


}
