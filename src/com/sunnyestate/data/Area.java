package com.sunnyestate.data;

public class Area {
	private String area_name = "";
	private int area_id;

	public String toString() {
		return "area_name:" + area_name;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

}
