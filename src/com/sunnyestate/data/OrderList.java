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

public class OrderList extends AbstractData {
	private static final String ORDER_LIST_API = "getmyorderlist.html";
	private List<OrderData> list = new ArrayList<OrderData>();
	private int uid;
	private String nextPage = "";

	public OrderList(int uid) {
		this.uid = uid;
	}

	public List<OrderData> getList() {
		return list;
	}

	public void setList(List<OrderData> list) {
		this.list = list;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public RetError refushData() {
		String result = HttpUrlHelper
				.getUrlData(ORDER_LIST_API + "?uid=" + uid);
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
				String nextpage = getAttributeValueByTagName(e, "nextpage");
				this.nextPage = nextpage;
				nodes = e.getElementsByTagName("order");
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						e = (Element) nodes.item(i);
						OrderData order = new OrderData();
						order.setAddressid(getIntValueByTagName(e, "addressid"));
						order.setCreatetime(getValueByTagName(e, "createtime"));
						order.setDeliverycorp(getValueByTagName(e,
								"deliverycorp"));
						order.setDeliveryno(getValueByTagName(e, "deliveryno"));
						order.setDeliveryprice(getIntValueByTagName(e,
								"deliveryprice"));
						order.setDuecharges(getIntValueByTagName(e,
								"duecharges"));
						order.setOrder_status(getIntValueByTagName(e,
								"order_status"));
						order.setOrderid(getIntValueByTagName(e, "orderid"));
						order.setPaytime(getValueByTagName(e, "paytime"));
						order.setPaytype(getIntValueByTagName(e, "paytype"));
						order.setRealcharges(getIntValueByTagName(e,
								"realcharges"));
						order.setStatusname(getValueByTagName(e, "statusname"));
						NodeList node_filter = e
								.getElementsByTagName("itemlist");
						if (node_filter != null && node_filter.getLength() > 0) {
							for (int j = 0; j < node_filter.getLength(); j++) {
								Element e_item = (Element) node_filter.item(j);
								NodeList itemNodes = e_item
										.getElementsByTagName("item");
								List<OrderItem> orderItemList = new ArrayList<OrderItem>();
								if (itemNodes != null) {
									for (int k = 0; k < itemNodes.getLength(); k++) {
										Element eItem = (Element) itemNodes
												.item(k);
										OrderItem order_item = new OrderItem();
										order_item.setId(getIntValueByTagName(
												eItem, "id"));
										order_item.setTitle(getValueByTagName(
												eItem, "title"));
										order_item
												.setPrice(getIntValueByTagName(
														eItem, "price"));
										order_item
												.setMemberprice(getIntValueByTagName(
														eItem, "memberprice"));
										order_item
												.setImageurl(getValueByTagName(
														eItem, "imageurl"));
										order_item.setTd(getIntValueByTagName(
												eItem, "td"));
										order_item.setNum(getIntValueByTagName(
												eItem, "num"));
										orderItemList.add(order_item);
									}

								}
								order.setItemList(orderItemList);
							}
						}
						list.add(order);
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
