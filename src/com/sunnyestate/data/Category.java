package com.sunnyestate.data;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private int id;
	private String title = "";
	private List<CategoryFilter> filter_list = new ArrayList<CategoryFilter>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<CategoryFilter> getFilter_list() {
		return filter_list;
	}

	public void setFilter_list(List<CategoryFilter> filter_list) {
		this.filter_list = filter_list;
	}

}
