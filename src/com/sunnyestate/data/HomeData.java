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

public class HomeData extends BaseData {
	private static final String HOME_URL = "home.html";
	private List<HomeCategory> list_categorys = new ArrayList<HomeCategory>();
	private List<Recomment> lsitRecomments = new ArrayList<Recomment>();

	public List<Recomment> getLsitRecomments() {
		return lsitRecomments;
	}

	public void setLsitRecomments(List<Recomment> lsitRecomments) {
		this.lsitRecomments = lsitRecomments;
	}

	public List<HomeCategory> getList_categorys() {
		return list_categorys;
	}

	public void setList_categorys(List<HomeCategory> list_categorys) {
		this.list_categorys = list_categorys;
	}

	public RetError refush() {
		String result = HttpUrlHelper.getUrlData(HOME_URL);
		if (result == null) {
			return RetError.INVALID;
		}
		InputStream inputStream = new ByteArrayInputStream(result.getBytes());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(inputStream);
			Element root = doc.getDocumentElement();
			// 推荐大图
			NodeList recommendsNodeList = root
					.getElementsByTagName("recommends");
			if (recommendsNodeList != null
					&& recommendsNodeList.getLength() > 0) {
				Element e_recommends = (Element) recommendsNodeList.item(0);

				NodeList recommendNodeList = e_recommends
						.getElementsByTagName("recommend");
				if (recommendNodeList != null) {
					for (int i = 0; i < recommendNodeList.getLength(); i++) {
						Element node = (Element) recommendNodeList.item(i);
						Recomment recomment = new Recomment();
						recomment.setId(Integer.parseInt(node
								.getAttribute("id")));
						recomment.setTitle(node.getAttribute("title"));
						recomment.setImg_url(node.getAttribute("imageurl"));
						lsitRecomments.add(recomment);
					}
				}
			}
			// 数据
			NodeList nodes = root.getElementsByTagName("categorys");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				nodes = e.getElementsByTagName("category");
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						e = (Element) nodes.item(i);
						HomeCategory categoryInfo = new HomeCategory();
						categoryInfo.setId(getIntAttributeValueByTagName(e,
								"id"));
						categoryInfo.setTitle(getAttributeValueByTagName(e,
								"title"));
						NodeList itemNodes = e.getElementsByTagName("item");

						List<HomeCategoryItem> items = new ArrayList<HomeCategoryItem>();
						if (itemNodes != null) {
							for (int j = 0; j < itemNodes.getLength(); j++) {
								Element eItem = (Element) itemNodes.item(j);
								HomeCategoryItem category_item = new HomeCategoryItem();
								category_item.setId(getIntValueByTagName(eItem,
										"id"));
								category_item.setTitle(getValueByTagName(eItem,
										"title"));
								category_item.setBrand(getValueByTagName(eItem,
										"brand"));
								category_item.setImg_url(getValueByTagName(
										eItem, "imageurl"));
								category_item.setPrice(getIntValueByTagName(
										eItem, "price"));
								category_item
										.setMember_price(getIntValueByTagName(
												eItem, "memberprice"));
								items.add(category_item);
							}
						}
						categoryInfo.setItems(items);
						list_categorys.add(categoryInfo);
					}
				}
				return RetError.NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("e:::::::::::::" + e.toString());
		}
		return RetError.INVALID;

	}
}
