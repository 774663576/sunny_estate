package com.sunnyestate.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;
import com.sunnyestate.utils.SharedUtils;

public class Adress extends AbstractData {
	private static final String ADD_ADDRESS_API = "addaddress.html";
	private static final String SET_DEFAULT_ADDRESS_API = "setdefaultaddress.html";

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

	public RetError addAddress() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", SharedUtils.getIntUid());
		map.put("receiver", receiver);
		map.put("phone", phone);
		map.put("postcode", postcode);
		map.put("provinceid", provinceid);
		map.put("provincename", provincename);
		map.put("cityid", cityid);
		map.put("cityname", cityname);
		map.put("areaid", areaid);
		map.put("areaname", areaname);
		map.put("detail", detail);
		String result = HttpUrlHelper.postData(map, ADD_ADDRESS_API);
		if (result == null) {
			return RetError.INVALID;
		}
		int code = -1;
		Object[] resultArr = getRootElement(result);
		code = (Integer) resultArr[0];
		if (code == 0) {
			return RetError.NONE;
		}
		return RetError.INVALID;

	}

	public RetError setDefaultAddress() {
		String result = HttpUrlHelper.getUrlData(SET_DEFAULT_ADDRESS_API
				+ "?userid=" + SharedUtils.getUid() + "&addressid=" + this.id);
		if (result == null) {
			return RetError.INVALID;
		}
		int code = -1;
		Object[] resultArr = getRootElement(result);
		code = (Integer) resultArr[0];
		if (code == 0) {
			return RetError.NONE;
		}
		return RetError.INVALID;

	}

}
