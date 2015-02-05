package com.sunnyestate.data;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;

public class CategoryData extends BaseData {
	private static final String CATEGORY_LIST_API = "category";
	private static final String CATEGORY_DETAILS_API = "getlist";
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
		String result = HttpUrlHelper.getUrlData(CATEGORY_DETAILS_API + "/"
				+ url);
		RetError ret = RetError.NONE;
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
						detail.setProducttype(getIntValueByTagName(e,
								"producttype"));
						detail.setProducttile(getValueByTagName(e,
								"producttitle"));
						detail.setDefaultimg(getValueByTagName(e, "defaultimg"));
						detail.setSmalltitle(getValueByTagName(e, "smalltitle"));
						detail.setDescription(getValueByTagName(e,
								"description"));
						detail.setPrice(getFloatValueByTagName(e, "price"));
						detail.setOriginalprice(getFloatValueByTagName(e,
								"originalprice"));
						detail.setNotproduct(getIntValueByTagName(e,
								"notproduct"));
						detail.setBrands(getIntValueByTagName(e, "brands"));
						detail.setSweet(getIntValueByTagName(e, "sweet"));
						detail.setTypes(getIntValueByTagName(e, "types"));
						detail.setIstop(getIntValueByTagName(e, "istop"));
						detail.setConfiginfo(getValueByTagName(e, "configinfo"));
						detailList.add(detail);
					}
				}
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;
	}

	public RetError loadMore(String url) {
		detailList.clear();
		String result = HttpUrlHelper.getUrlDataLoadMore(url);
		RetError ret = RetError.NONE;
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
						detail.setProducttype(getIntValueByTagName(e,
								"producttype"));
						detail.setProducttile(getValueByTagName(e,
								"producttitle"));
						detail.setDefaultimg(getValueByTagName(e, "defaultimg"));
						detail.setSmalltitle(getValueByTagName(e, "smalltitle"));
						detail.setDescription(getValueByTagName(e,
								"description"));
						detail.setPrice(getFloatValueByTagName(e, "price"));
						detail.setOriginalprice(getFloatValueByTagName(e,
								"originalprice"));
						detail.setNotproduct(getIntValueByTagName(e,
								"notproduct"));
						detail.setBrands(getIntValueByTagName(e, "brands"));
						detail.setSweet(getIntValueByTagName(e, "sweet"));
						detail.setTypes(getIntValueByTagName(e, "types"));
						detail.setIstop(getIntValueByTagName(e, "istop"));
						detail.setConfiginfo(getValueByTagName(e, "configinfo"));
						detailList.add(detail);
					}
				}
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;
	}

	public RetError refushCategoryList() {
		String result = HttpUrlHelper.getUrlData(CATEGORY_LIST_API);
		RetError ret = RetError.NONE;
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
		Element root = (Element) resultArr[1];
		try {

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
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;

	}
}
