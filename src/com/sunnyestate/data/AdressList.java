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

public class AdressList extends BaseData {
	private static final String GET_ADDRES_API = "getaddresslist.html";
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
		String result = HttpUrlHelper
				.getUrlData(GET_ADDRES_API + "?uid=" + uid);
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
			NodeList nodes = rootElement.getElementsByTagName("list");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				nodes = e.getElementsByTagName("addressinfo");
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						e = (Element) nodes.item(i);
						Adress adress = new Adress();
						adress.setAreaid(getIntValueByTagName(e, "areaid"));
						adress.setAreaname(getValueByTagName(e, "areaname"));
						adress.setCityid(getIntValueByTagName(e, "cityid"));
						adress.setCityname(getValueByTagName(e, "cityname"));
						adress.setDetail(getValueByTagName(e, "detail"));
						adress.setId(getIntValueByTagName(e, "id"));
						adress.setIsdefault(getIntValueByTagName(e, "isdefault"));
						adress.setPhone(getValueByTagName(e, "phone"));
						adress.setPostcode(getValueByTagName(e, "postcode"));
						adress.setProvinceid(getIntValueByTagName(e,
								"provinceid"));
						adress.setProvincename(getValueByTagName(e,
								"provincename"));
						adress.setReceiver(getValueByTagName(e, "receiver"));
						System.out.println("receive:::::::::"
								+ adress.getReceiver());
						lists.add(adress);
					}
				}
				return RetError.NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
