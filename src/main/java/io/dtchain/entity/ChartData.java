package io.dtchain.entity;

public class ChartData {
	
	private int late;
	private int earlyRetr;
	private int overTime;
	private int days;
	private int month;
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
	public int getOverTime() {
		return overTime;
	}
	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "ChartData [late=" + late + ", earlyRetr=" + earlyRetr + ", overTime=" + overTime + ", daysList="
				+ days + ", month=" + month + "]";
	}
	
}
