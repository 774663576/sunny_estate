package com.sunnyestate.data;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;
import com.sunnyestate.utils.SharedUtils;

public class Adress extends AbstractData {
	private static final String ADD_ADDRESS_API = "addressAdd";
	private static final String SET_DEFAULT_ADDRESS_API = "addressUpdate";
	private static final String DEL_ADDRESS_API = "addressDelById";
	private static final String UPDATE_ADDRESS_API = "addressUpdate";

	private static final long serialVersionUID = 1L;
	private int id;
	private String phone = "";
	private int privenceid;
	private int cityid;
	private int areaid;
	private int isdefault;
	private String region = "";// 区域
	private String consgneedname = "";// 收货人
	private String fulladdress = "";// 详细地址
	private String addtime = "";
	private String zip = "";// 邮编

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getConsgneedname() {
		return consgneedname;
	}

	public void setConsgneedname(String consgneedname) {
		this.consgneedname = consgneedname;
	}

	public String getFulladdress() {
		return fulladdress;
	}

	public void setFulladdress(String fulladdress) {
		this.fulladdress = fulladdress;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public int getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}

	public int getPrivenceid() {
		return privenceid;
	}

	public void setPrivenceid(int privenceid) {
		this.privenceid = privenceid;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public RetError updateAddress() {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(UPDATE_ADDRESS_API + "/id/"
				+ id + "/consignee_name/" + consgneedname + "/region/" + areaid
				+ "/zip/" + zip + "/phone/" + phone + "/is_default/" + 0
				+ "/address/" + fulladdress + "/username/"
				+ SharedUtils.getUserName() + "/password/"
				+ SharedUtils.getPasswordKey());
		if (result == null) {
			return RetError.INVALID;
		}
		int res_code = -1;
		Object[] resultArr = getRootElement(result);
		res_code = (Integer) resultArr[0];
		String message = (String) resultArr[2];
		if (res_code != 0) {
			ret = RetError.INVALID;
			ret.setMessage(message);
			return ret;
		}
		return ret;
	}

	public RetError addAddress() {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(ADD_ADDRESS_API
				+ "/consignee_name/" + consgneedname + "/region/" + areaid
				+ "/zip/" + zip + "/phone/" + phone + "/is_default/" + 0
				+ "/address/" + fulladdress + "/username/"
				+ SharedUtils.getUserName() + "/password/"
				+ SharedUtils.getPasswordKey());
		if (result == null) {
			return RetError.INVALID;
		}
		int res_code = -1;
		Object[] resultArr = getRootElement(result);
		res_code = (Integer) resultArr[0];
		String message = (String) resultArr[2];
		if (res_code != 0) {
			ret = RetError.INVALID;
			ret.setMessage(message);
			return ret;
		}
		this.id = Integer.valueOf(message);
		return ret;
	}

	public RetError setDefaultAddress() {
		RetError ret = RetError.NONE;

		String result = HttpUrlHelper.getUrlData(SET_DEFAULT_ADDRESS_API
				+ "/id/" + this.id + "/is_default/1/username/"
				+ SharedUtils.getUserName() + "/password/"
				+ SharedUtils.getPasswordKey());
		if (result == null) {
			return RetError.INVALID;
		}
		int res_code = -1;
		Object[] resultArr = getRootElement(result);
		res_code = (Integer) resultArr[0];
		String message = (String) resultArr[2];
		if (res_code != 0) {
			ret = RetError.INVALID;
			ret.setMessage(message);
			return ret;
		}
		return ret;

	}

	public RetError delAddress() {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(DEL_ADDRESS_API + "/id/"
				+ this.id + "/username/" + SharedUtils.getUserName()
				+ "/password/" + SharedUtils.getPasswordKey());
		if (result == null) {
			return RetError.INVALID;
		}
		int res_code = -1;
		Object[] resultArr = getRootElement(result);
		res_code = (Integer) resultArr[0];
		String message = (String) resultArr[2];
		if (res_code != 0) {
			ret = RetError.INVALID;
			ret.setMessage(message);
			return ret;
		}
		return ret;

	}
}
