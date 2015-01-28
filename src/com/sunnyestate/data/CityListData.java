package com.sunnyestate.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;

public class CityListData extends AbstractData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String GET_CITY_LIST = "getcitylist.html";

	private List<Province> lists = new ArrayList<Province>();

	public List<Province> getLists() {
		return lists;
	}

	public void setLists(List<Province> lists) {
		this.lists = lists;
	}

	public RetError getCityList() {
		String result = HttpUrlHelper.getUrlData(GET_CITY_LIST);
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
				nodes = e.getElementsByTagName("province");
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						e = (Element) nodes.item(i);
						Province province = new Province();
						province.setProvince_id(getIntAttributeValueByTagName(
								e, "id"));
						province.setProvince_name(getAttributeValueByTagName(e,
								"name"));
						NodeList node_filter = e.getElementsByTagName("city");
						List<City> cityList = new ArrayList<City>();
						if (node_filter != null && node_filter.getLength() > 0) {
							for (int j = 0; j < node_filter.getLength(); j++) {
								Element e_item = (Element) node_filter.item(j);
								City city = new City();
								city.setCity_id(getIntAttributeValueByTagName(
										e_item, "id"));
								city.setCity_name(getAttributeValueByTagName(
										e_item, "name"));
								NodeList itemNodes = e_item
										.getElementsByTagName("area");
								List<Area> areaList = new ArrayList<Area>();
								if (itemNodes != null) {
									for (int k = 0; k < itemNodes.getLength(); k++) {
										Element eItem = (Element) itemNodes
												.item(k);
										Area area = new Area();
										area.setArea_id(getIntAttributeValueByTagName(
												eItem, "id"));
										area.setArea_name(getAttributeValueByTagName(
												eItem, "name"));
										areaList.add(area);
									}

								}
								city.setAreaLists(areaList);
								cityList.add(city);
								province.setCityLists(cityList);
							}
						}
						lists.add(province);
					}
				}
				return RetError.NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;
	}
}
