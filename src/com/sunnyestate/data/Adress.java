package com.sunnyestate.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Adress extends AbstractData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name = "";
	private String cellphone = "";
	private String code = "";
	private String adress = "";
	private String adress_detail = "";
	private int default_adress = 0;

	public int getDefault_adress() {
		return default_adress;
	}

	public void setDefault_adress(int default_adress) {
		this.default_adress = default_adress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getAdress_detail() {
		return adress_detail;
	}

	public void setAdress_detail(String adress_detail) {
		this.adress_detail = adress_detail;
	}

	@Override
	public void write(SQLiteDatabase db) {
		String tableName = com.sunnyestate.db.Const.ADRESS_TABLE_NAME;
		if (this.status == Status.DEL) {
			db.delete(tableName, "_id=?", new String[] { id + "" });
			return;
		}
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("cellphone", cellphone);
		values.put("code", code);
		values.put("adress_detail", adress_detail);
		values.put("default_adress", default_adress);
		values.put("adress", adress);
		if (this.status == Status.UPDATE) {
			db.update(tableName, values, "_id=? ",
					new String[] { this.id + "" });
			return;
		}
		db.insert(tableName, null, values);
		Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
		if (cursor.moveToFirst())
			id = cursor.getInt(0);
	}
}
