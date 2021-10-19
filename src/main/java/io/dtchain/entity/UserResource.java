package io.dtchain.entity;

public class UserResource {
	private String userId;
	private Integer resourceId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public String toString() {
		return "UserResource [userId=" + userId + ", resourceId=" + resourceId + "]";
	}
}
