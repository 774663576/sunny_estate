package com.sunnyestate.data;

public class HomeCategoryItem {
	private int id;
	private String title = "";
	private String brand = "";// Æ·ÅÆ
	private String img_url = "";
	private int member_price;
	private int price;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getMember_price() {
		return member_price;
	}

	public void setMember_price(int member_price) {
		this.member_price = member_price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

}
