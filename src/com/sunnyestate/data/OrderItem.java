package com.sunnyestate.data;

public class OrderItem {
	private int id;
	private int td;// Μπ¶Θ
	private int num;
	private int price;
	private int memberprice;
	private String title = "";
	private String brand = "";
	private String imageurl = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTd() {
		return td;
	}

	public void setTd(int td) {
		this.td = td;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMemberprice() {
		return memberprice;
	}

	public void setMemberprice(int memberprice) {
		this.memberprice = memberprice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

}
