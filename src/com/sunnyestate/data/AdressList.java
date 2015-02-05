package com.sunnyestate.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;
import com.sunnyestate.utils.SharedUtils;

public class AdressList extends BaseData {
	private static final String GET_ADDRES_API = "addressList";
	List<Adress> lists = new ArrayList<Adress>();
	private int uid;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public AdressList(int uid) {
		this.uid = uid;
	}

	public List<Adress> getLists() {
		getComparator();
		return lists;
	}

	public void setLists(List<Adress> lists) {
		this.lists = lists;
	}

	public RetError refushAddress() {
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(GET_ADDRES_API + "/username/"
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
		Element rootElement = (Element) resultArr[1];
		try {
			NodeList nodes = rootElement.getElementsByTagName("list");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				nodes = e.getElementsByTagName("item");
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						e = (Element) nodes.item(i);
						Adress adress = new Adress();
						String regionid = getValueByTagName(e, "regionid");
						String ids[] = regionid.split(",");
						if (ids != null && ids.length != 0) {
							adress.setPrivenceid(Integer.valueOf(ids[0]));
							adress.setCityid(Integer.valueOf(ids[1]));
							adress.setAreaid(Integer.valueOf(ids[2]));
						}
						adress.setId(getIntValueByTagName(e, "id"));
						adress.setIsdefault(getIntValueByTagName(e, "isdefault"));
						adress.setPhone(getValueByTagName(e, "phone"));
						adress.setAddtime(getValueByTagName(e, "addtime"));
						adress.setConsgneedname(getValueByTagName(e,
								"consgneename"));
						adress.setFulladdress(getValueByTagName(e,
								"fulladdress"));
						adress.setZip(getValueByTagName(e, "zip"));
						adress.setRegion(getValueByTagName(e, "region"));

						// adress.setPostcode(getValueByTagName(e, "postcode"));
						// adress.setProvinceid(getIntValueByTagName(e,
						// "provinceid"));
						// adress.setProvincename(getValueByTagName(e,
						// "provincename"));
						// adress.setReceiver(getValueByTagName(e, "receiver"));
						// System.out.println("receive:::::::::"
						// + adress.getReceiver());
						lists.add(adress);
					}
				}
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("e::::::::" + e.toString());
		}
		return RetError.INVALID;
	}

	public static Comparator<Adress> getComparator() {
		return new Comparator<Adress>() {
			@Override
			public int compare(Adress l, Adress r) {
				return l.getIsdefault() > r.getIsdefault() ? 1 : -1;
			}
		};

	}
}
