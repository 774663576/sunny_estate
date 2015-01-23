package com.sunnyestate.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;

public class User extends BaseData {
	private static final String FREEDBACK_API = "feedback.html";
	private static final String REGISTER_API = "userreg.html";
	private static final String SEND_CODE_API = "sendvcode.html";
	private static final String LOGIN_API = "userlogin.html";
	private static final String FIND_PASSWORD_API = "getpwd.html";

	private int uid;
	private String mobile = "";
	private String pwd = "";
	private String username = "";
	private String nickname = "";
	private int score;
	private int level;
	private String headurl = "";

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public User(int uid) {
		this.uid = uid;
	}

	public User() {
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

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * 反馈
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
	 * 获取验证码
	 * 
	 * @return
	 */
	public RetError getVerfityCodeError() {
		HttpUrlHelper.getUrlData(SEND_CODE_API + "?mobile=" + mobile);
		return RetError.NONE;

	}

	/**
	 * 找回密码
	 * 
	 * @return
	 */
	public RetError findPassword() {
		HttpUrlHelper.getUrlData(FIND_PASSWORD_API + "?mobile=" + mobile);
		return RetError.NONE;

	}

	/**
	 * . 注册
	 * 
	 * @param code
	 * @return
	 */
	public RetError register(String code) {
		String result = HttpUrlHelper.getUrlData(REGISTER_API + "?mobile="
				+ mobile + "&pwd=" + pwd + "&vcode=" + code);
		if (result == null) {
			return RetError.INVALID;
		}
		try {
			InputStream inputStream = new ByteArrayInputStream(
					result.getBytes());
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);
			Element rootElement = doc.getDocumentElement();
			NodeList nodes = rootElement.getElementsByTagName("userinfo");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				this.uid = getIntValueByTagName(e, "userid");
				this.level = getIntValueByTagName(e, "level");
				this.nickname = getValueByTagName(e, "nickname");
				this.score = getIntValueByTagName(e, "score");
				this.username = getValueByTagName(e, "username");
				this.headurl = getValueByTagName(e, "headurl");
				return RetError.NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	public RetError login() {
		String result = HttpUrlHelper.getUrlData(LOGIN_API + "?mobile="
				+ mobile + "&pwd=" + pwd);
		if (result == null) {
			return RetError.INVALID;
		}
		try {
			InputStream inputStream = new ByteArrayInputStream(
					result.getBytes());
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);
			Element rootElement = doc.getDocumentElement();
			NodeList nodes = rootElement.getElementsByTagName("userinfo");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				this.uid = getIntValueByTagName(e, "userid");
				this.level = getIntValueByTagName(e, "level");
				this.nickname = getValueByTagName(e, "nickname");
				this.score = getIntValueByTagName(e, "score");
				this.username = getValueByTagName(e, "username");
				this.headurl = getValueByTagName(e, "headurl");
				return RetError.NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;
	}
}
