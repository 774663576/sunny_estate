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
import com.sunnyestate.utils.SharedUtils;

public class OrderData extends BaseData {
	private static final String DEL_ORDER_API = "delorder.html";

	private int orderid;
	private int addressid;
	private int order_status;
	private String statusname = "";
	private String createtime = "";
	private String paytime = "";
	private int paytype;
	private int deliveryprice;
	private String deliverycorp = "";
	private String deliveryno = "";
	private int duecharges;// 应收价格
	private int realcharges;// 实收价格
	private List<OrderItem> itemList = new ArrayList<OrderItem>();

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getAddressid() {
		return addressid;
	}

	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	public int getDeliveryprice() {
		return deliveryprice;
	}

	public void setDeliveryprice(int deliveryprice) {
		this.deliveryprice = deliveryprice;
	}

	public String getDeliverycorp() {
		return deliverycorp;
	}

	public void setDeliverycorp(String deliverycorp) {
		this.deliverycorp = deliverycorp;
	}

	public String getDeliveryno() {
		return deliveryno;
	}

	public void setDeliveryno(String deliveryno) {
		this.deliveryno = deliveryno;
	}

	public int getDuecharges() {
		return duecharges;
	}

	public void setDuecharges(int duecharges) {
		this.duecharges = duecharges;
	}

	public int getRealcharges() {
		return realcharges;
	}

	public void setRealcharges(int realcharges) {
		this.realcharges = realcharges;
	}

	public List<OrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItem> itemList) {
		this.itemList = itemList;
	}

	public RetError delOrder() {
		String result = HttpUrlHelper.getUrlData(DEL_ORDER_API + "?uid="
				+ SharedUtils.getUid() + "&orderid=" + this.orderid);
		if (result == null) {
			return RetError.INVALID;
		}
		int code = -1;
		try {
			InputStream inputStream = new ByteArrayInputStream(
					result.getBytes());
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);
			Element rootElement = doc.getDocumentElement();
			NodeList nodes = rootElement.getElementsByTagName("result");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				code = getIntValueByTagName(e, "code");
				if (code == 0) {
					return RetError.NONE;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.NONE;
	}
}
