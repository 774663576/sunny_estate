package com.sunnyestate.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ShoppingCar extends AbstractData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int shopping_id; 
	private int count;
	private float price;
	private float member_price;
	private String title = "";
	private String img_url = ""; 
	private boolean isSelect = false;
	private boolean isEdit = false;

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public int getShopping_id() {
		return shopping_id;
	}

	public void setShopping_id(int shopping_id) {
		this.shopping_id = shopping_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getMember_price() {
		return member_price;
	}

	public void setMember_price(float member_price) {
		this.member_price = member_price;
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

	@Override
	public void write(SQLiteDatabase db) {
		String tableName = com.sunnyestate.db.Const.SHOPPING_CAR_TABLE_NAME;
		if (this.status == Status.DEL) {
			db.delete(tableName, "shopping_id=?", new String[] { shopping_id
					+ "" });
			return;
		}
		ContentValues values = new ContentValues();
		values.put("shopping_id", shopping_id);
		values.put("count", count);
		values.put("price", price);
		values.put("member_price", member_price);
		values.put("title", title);
		values.put("img_url", img_url);
		if (this.status == Status.UPDATE) {
			db.update(tableName, values, "shopping_id=? ",
					new String[] { this.shopping_id + "" });
			return;
		}
		db.insert(tableName, null, values);
	}
}
