package com.sunnyestate.data;

public class Adress extends AbstractData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String receiver = "";
	private String phone = "";
	private String postcode = "";
	private int provinceid;
	private String provincename = "";
	private int cityid;
	private String cityname = "";
	private int areaid;
	private String areaname = "";
	private String detail = "";
	private int isdefault;

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}

	// @Override
	// public void write(SQLiteDatabase db) {
	// String tableName = com.sunnyestate.db.Const.ADRESS_TABLE_NAME;
	// if (this.status == Status.DEL) {
	// db.delete(tableName, "_id=?", new String[] { id + "" });
	// return;
	// }
	// ContentValues values = new ContentValues();
	// values.put("name", name);
	// values.put("cellphone", cellphone);
	// values.put("code", code);
	// values.put("adress_detail", adress_detail);
	// values.put("default_adress", default_adress);
	// values.put("adress", adress);
	// if (this.status == Status.UPDATE) {
	// db.update(tableName, values, "_id=? ",
	// new String[] { this.id + "" });
	// return;
	// }
	// db.insert(tableName, null, values);
	// Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
	// if (cursor.moveToFirst())
	// id = cursor.getInt(0);
	// }
}
