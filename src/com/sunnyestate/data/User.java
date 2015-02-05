package com.sunnyestate.data;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;
import com.sunnyestate.utils.SharedUtils;

public class User extends BaseData {
	private static final String FREEDBACK_API = "feedback.html";
	private static final String REGISTER_API = "userReg";
	private static final String SEND_CODE_API = "sendMessage";
	private static final String LOGIN_API = "userLogin";
	private static final String FIND_PASSWORD_API = "pwdGet";
	private static final String EDIT_PASSWORD_API = "pwdUpdate";

	private int uid;
	private String mobile = "";
	private String pwd = "";
	private String username = "";
	private String nickname = "";
	private int sex;
	private String rname = "";
	private String avatar = "";
	private String areaval = "";
	private String addressval = "";
	private String birthday = "";
	private int score;
	private String levels = "";

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAreaval() {
		return areaval;
	}

	public void setAreaval(String areaval) {
		this.areaval = areaval;
	}

	public String getAddressval() {
		return addressval;
	}

	public void setAddressval(String addressval) {
		this.addressval = addressval;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	/**
	 * ·´À¡
	 * 
	 * @param content
	 * @return
	 */
	public RetError freedBack(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("content", uid);
		HttpUrlHelper.postData(map, FREEDBACK_API);
		return RetError.NONE;

	}

	/**
	 * »ñÈ¡ÑéÖ¤Âë
	 * 
	 * @return
	 */
	public RetError getVerfityCodeError() {
		RetError ret = RetError.NONE;

		String result = HttpUrlHelper.getUrlData(SEND_CODE_API + "/mobile/"
				+ mobile);
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

	/**
	 * ÐÞ¸ÄÃÜÂë
	 * 
	 * @return
	 */
	public RetError edidPwd(String old_pwd, String new_pwd) {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(EDIT_PASSWORD_API + "/oldpwd/"
				+ old_pwd + "/newpwd/" + new_pwd + "/username/"
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
		SharedUtils.setPasswordKey(message);
		return ret;
	}

	/**
	 * ÕÒ»ØÃÜÂë
	 * 
	 * @return
	 */
	public RetError findPassword() {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(FIND_PASSWORD_API
				+ "/username/" + username);
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

	/**
	 * . ×¢²á
	 * 
	 * @param code
	 * @return
	 */
	public RetError register(String code) {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(REGISTER_API + "/username/"
				+ username + "/password/" + pwd + "/mobile/" + mobile
				+ "/code/" + code);
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
		Element rootElement = (Element) resultArr[1];
		try {
			NodeList nodes = rootElement.getElementsByTagName("userinfo");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				this.score = getIntValueByTagName(e, "score");
				this.username = getValueByTagName(e, "username");
				this.addressval = getValueByTagName(e, "addressval");
				this.areaval = getValueByTagName(e, "areaval");
				this.avatar = getValueByTagName(e, "avatar");
				this.birthday = getValueByTagName(e, "birthday");
				this.levels = getValueByTagName(e, "levels");
				this.nickname = getValueByTagName(e, "nickname");
				this.rname = getValueByTagName(e, "rname");
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;

	}

	public RetError getUserInfo() {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData("userLoginByback"
				+ "/username/" + SharedUtils.getUserName() + "/password/"
				+ SharedUtils.getPasswordKey());
		int res_code = -1;
		Object[] resultArr = getRootElement(result);
		res_code = (Integer) resultArr[0];
		String message = (String) resultArr[2];
		ret.setMessage(message);
		if (res_code != 0) {
			ret = RetError.INVALID;
			ret.setMessage(message);
			return ret;
		}
		Element rootElement = (Element) resultArr[1];
		try {
			NodeList nodes = rootElement.getElementsByTagName("userinfo");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				this.score = getIntValueByTagName(e, "score");
				this.username = getValueByTagName(e, "username");
				this.addressval = getValueByTagName(e, "addressval");
				this.areaval = getValueByTagName(e, "areaval");
				this.avatar = getValueByTagName(e, "avatar");
				this.birthday = getValueByTagName(e, "birthday");
				this.levels = getValueByTagName(e, "levels");
				this.nickname = getValueByTagName(e, "nickname");
				this.rname = getValueByTagName(e, "rname");
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;

	}

	/**
	 * µÇÂ¼
	 * 
	 * @return
	 */
	public RetError login() {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(LOGIN_API + "/username/"
				+ username + "/password/" + pwd);
		int res_code = -1;
		Object[] resultArr = getRootElement(result);
		res_code = (Integer) resultArr[0];
		String message = (String) resultArr[2];
		ret.setMessage(message);
		if (res_code != 0) {
			ret = RetError.INVALID;
			ret.setMessage(message);
			return ret;
		}
		Element rootElement = (Element) resultArr[1];
		try {
			NodeList nodes = rootElement.getElementsByTagName("userinfo");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				this.score = getIntValueByTagName(e, "score");
				this.username = getValueByTagName(e, "username");
				this.addressval = getValueByTagName(e, "addressval");
				this.areaval = getValueByTagName(e, "areaval");
				this.avatar = getValueByTagName(e, "avatar");
				this.birthday = getValueByTagName(e, "birthday");
				this.levels = getValueByTagName(e, "levels");
				this.nickname = getValueByTagName(e, "nickname");
				this.rname = getValueByTagName(e, "rname");
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;

	}
}
