package com.sunnyestate.data;

import java.io.Serializable;

public class CategoryDataDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String title = "";
	private String image_url = "";
	private int price;
	private int member_price;
	private int sweet = 0;// Μπ¶Θ

	public int getSweet() {
		return sweet;
	}

	public void setSweet(int sweet) {
		this.sweet = sweet;
	}

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

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMember_price() {
		return member_price;
	}

	public void setMember_price(int member_price) {
		this.member_price = member_price;
	}

}
