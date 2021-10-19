package io.dtchain.entity;

public class AttendTable {

	private String userName;
	private int empNum;
	private String cardNum;
	private String dates;
	private String times;
	private String direction;
	private int sourceEvent;

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getEmpNum() {
		return empNum;
	}

	public void setEmpNum(int empNum) {
		this.empNum = empNum;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getSourceEvent() {
		return sourceEvent;
	}

	public void setSourceEvent(int sourceEvent) {
		this.sourceEvent = sourceEvent;
	}

	@Override
	public String toString() {
		return "AttendTable [userName=" + userName + ", cardType=" + empNum + ", cardNum=" + cardNum + ", dates="
				+ dates + ", times=" + times + ", direction=" + direction + ", sourceEvent=" + sourceEvent + "]";
	}

}
