package io.dtchain.entity;

public class Resource {
	private Integer id;
	private String name;
	private String resUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", resUrl=" + resUrl + "]";
	}

}
