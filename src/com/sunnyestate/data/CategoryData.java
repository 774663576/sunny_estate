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

public class CategoryData extends BaseData {
	private static final String CATEGORY_LIST_API = "category.html";
	private static final String CATEGORY_DETAILS_API = "getlist.html";
	private List<Category> categoryList = new ArrayList<Category>();
	private List<CategoryDataDetail> detailList = new ArrayList<CategoryDataDetail>();
	private String nextPage = "";

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<CategoryDataDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<CategoryDataDetail> detailList) {
		this.detailList = detailList;
	}

	public RetError refushCategoryData(String url) {
		detailList.clear();
		String result = HttpUrlHelper.getUrlData(CATEGORY_DETAILS_API + "?"
				+ url);
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
			NodeList nodes = rootElement.getElementsByTagName("category");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				String nextpage = getAttributeValueByTagName(e, "nextpage");
				this.nextPage = nextpage;
				nodes = e.getElementsByTagName("item");
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						e = (Element) nodes.item(i);

						CategoryDataDetail detail = new CategoryDataDetail();
						detail.setId(getIntValueByTagName(e, "id"));
						detail.setTitle(getValueByTagName(e, "title"));
						detail.setPrice(getIntValueByTagName(e, "price"));
						detail.setMember_price(getIntValueByTagName(e,
								"memberprice"));
						detail.setImage_url(getValueByTagName(e, "imageurl"));
						detail.setSweet(getIntValueByTagName(e, "td"));
						detailList.add(detail);
					}
				}
				return RetError.NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;
	}

	public RetError loadMore(String url) {
		detailList.clear();
		String result = HttpUrlHelper.getUrlDataLoadMore(url);
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
			NodeList nodes = rootElement.getElementsByTagName("category");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				String nextpage = getAttributeValueByTagName(e, "nextpage");
				this.nextPage = nextpage;
				nodes = e.getElementsByTagName("item");
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						e = (Element) nodes.item(i);

						CategoryDataDetail detail = new CategoryDataDetail();
						detail.setId(getIntValueByTagName(e, "id"));
						detail.setTitle(getValueByTagName(e, "title"));
						detail.setPrice(getIntValueByTagName(e, "price"));
						detail.setMember_price(getIntValueByTagName(e,
								"memberprice"));
						detail.setImage_url(getValueByTagName(e, "imageurl"));
						detail.setSweet(getIntValueByTagName(e, "td"));
						detailList.add(detail);
					}
				}
				return RetError.NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;
	}

	public RetError refushCategoryList() {
		String result = HttpUrlHelper.getUrlData(CATEGORY_LIST_API);
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
			NodeList categorysNodeList = root.getElementsByTagName("categorys");
			if (categorysNodeList != null && categorysNodeList.getLength() > 0) {
				Element e_categorys = (Element) categorysNodeList.item(0);
				NodeList categoryNodeList = e_categorys
						.getElementsByTagName("category");
				if (categoryNodeList != null) {
					for (int i = 0; i < categoryNodeList.getLength(); i++) {
						Element node = (Element) categoryNodeList.item(i);
						Category category = new Category();
						category.setId(Integer.parseInt(node.getAttribute("id")));
						category.setTitle(node.getAttribute("title"));
						NodeList node_filter = node
								.getElementsByTagName("filter");
						List<CategoryFilter> filter_list = new ArrayList<CategoryFilter>();
						if (node_filter != null && node_filter.getLength() > 0) {
							for (int j = 0; j < node_filter.getLength(); j++) {
								Element e = (Element) node_filter.item(j);
								CategoryFilter categoryFilter = new CategoryFilter();
								categoryFilter
										.setId(getIntAttributeValueByTagName(e,
												"id"));
								categoryFilter
										.setName(getAttributeValueByTagName(e,
												"name"));
								NodeList itemNodes = e
										.getElementsByTagName("item");
								List<CategoryFilterItem> filter_item_list = new ArrayList<CategoryFilterItem>();
								if (itemNodes != null) {
									for (int k = 0; k < itemNodes.getLength(); k++) {
										Element eItem = (Element) itemNodes
												.item(k);
										CategoryFilterItem filter_item = new CategoryFilterItem();
										filter_item
												.setId(getIntAttributeValueByTagName(
														eItem, "id"));
										filter_item
												.setName(getAttributeValueByTagName(
														eItem, "name"));
										filter_item_list.add(filter_item);
									}
									CategoryFilterItem filter_item = new CategoryFilterItem();
									filter_item.setId(0);
									filter_item.setName("È«²¿");
									filter_item_list.add(0, filter_item);
								}
								categoryFilter
										.setFilter_item_list(filter_item_list);
								filter_list.add(categoryFilter);
							}
						}
						category.setFilter_list(filter_list);
						categoryList.add(category);
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
