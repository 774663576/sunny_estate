package com.sunnyestate.data;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;

public class HomeData extends BaseData {
	private static final String HOME_URL = "getAppIndexShow";
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
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(HOME_URL);
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
			// 推荐大图
			NodeList recommendsNodeList = rootElement
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
						String id = node.getAttribute("id");
						if (id == null || "".equals(id)) {
							id = "0";
						}
						recomment.setId(Integer.parseInt(id));
						recomment.setTitle(node.getAttribute("title"));
						recomment.setImg_url(node.getAttribute("imageurl"));
						lsitRecomments.add(recomment);
						System.out.println("result:::::::::::::::recomment:"
								+ recomment.getTitle());
					}
				}
			}
			// 数据
			NodeList nodes = rootElement.getElementsByTagName("categorys");
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
								category_item
										.setProducttype(getIntValueByTagName(
												eItem, "producttype"));
								category_item.setProducttile(getValueByTagName(
										eItem, "producttitle"));
								category_item.setDefaultimg(getValueByTagName(
										eItem, "defaultimg"));
								category_item.setSmalltitle(getValueByTagName(
										eItem, "smalltitle"));
								category_item.setDescription(getValueByTagName(
										eItem, "description"));
								category_item.setPrice(getFloatValueByTagName(
										eItem, "price"));
								category_item
										.setOriginalprice(getFloatValueByTagName(
												eItem, "originalprice"));
								category_item
										.setNotproduct(getIntValueByTagName(
												eItem, "notproduct"));
								category_item.setBrandstitle(getValueByTagName(
										eItem, "brandstitle"));
								category_item.setBrands(getIntValueByTagName(
										eItem, "brands"));
								category_item.setSweet(getIntValueByTagName(
										eItem, "sweet"));
								category_item.setTypes(getIntValueByTagName(
										eItem, "types"));
								category_item.setIstop(getIntValueByTagName(
										eItem, "istop"));
								category_item.setConfiginfo(getValueByTagName(
										eItem, "configinfo"));
								items.add(category_item);
							}
						}
						categoryInfo.setItems(items);
						list_categorys.add(categoryInfo);
					}
				}
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("e:::::::::::::" + e.toString());
		}
		return RetError.INVALID;

	}
}
