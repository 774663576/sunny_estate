package com.sunnyestate.data;

import java.io.Serializable;

public class CategoryDataDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int producttype;
	private String producttile = "";
	private String defaultimg = "";
	private String smalltitle = "";
	private String description = "";
	private float price;
	private float originalprice;// Ô­¼Û
	private int notproduct;
	private int brands;
	private int sweet;
	private int types;
	private int istop;
	private String configinfo = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProducttype() {
		return producttype;
	}

	public void setProducttype(int producttype) {
		this.producttype = producttype;
	}

	public String getProducttile() {
		return producttile;
	}

	public void setProducttile(String producttile) {
		this.producttile = producttile;
	}

	public String getDefaultimg() {
		return defaultimg;
	}

	public void setDefaultimg(String defaultimg) {
		this.defaultimg = defaultimg;
	}

	public String getSmalltitle() {
		return smalltitle;
	}

	public void setSmalltitle(String smalltitle) {
		this.smalltitle = smalltitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(float originalprice) {
		this.originalprice = originalprice;
	}

	public int getNotproduct() {
		return notproduct;
	}

	public void setNotproduct(int notproduct) {
		this.notproduct = notproduct;
	}

	public int getBrands() {
		return brands;
	}

	public void setBrands(int brands) {
		this.brands = brands;
	}

	public int getSweet() {
		return sweet;
	}

	public void setSweet(int sweet) {
		this.sweet = sweet;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public int getIstop() {
		return istop;
	}

	public void setIstop(int istop) {
		this.istop = istop;
	}

	public String getConfiginfo() {
		return configinfo;
	}

	public void setConfiginfo(String configinfo) {
		this.configinfo = configinfo;
	}

}
