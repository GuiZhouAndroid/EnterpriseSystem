package io.dtchain.utils;

import java.io.Serializable;

public class Result<T> implements Serializable {
	private int state;
	private String msg;
	private int count;
	private T data;
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}



	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [state=" + state + ", msg=" + msg + ", data=" + data + "]";
	}

}
