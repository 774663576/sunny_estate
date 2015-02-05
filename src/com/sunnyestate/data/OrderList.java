package com.sunnyestate.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;
import com.sunnyestate.utils.SharedUtils;

public class OrderList extends AbstractData {
	private static final String ORDER_LIST_API = "orderList";
	private List<OrderData> list = new ArrayList<OrderData>();
	private String nextPage = "";

	public List<OrderData> getList() {
		return list;
	}

	public void setList(List<OrderData> list) {
		this.list = list;
	}

	public RetError refushData() {
		this.list.clear();
		RetError ret = RetError.NONE;
		String result = HttpUrlHelper.getUrlData(ORDER_LIST_API + "/username/"
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
				// String nextpage = getAttributeValueByTagName(e, "nextpage");
				// this.nextPage = nextpage;
				nodes = e.getElementsByTagName("item");
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						e = (Element) nodes.item(i);
						OrderData order = new OrderData();
						order.setAddtime(getValueByTagName(e, "addtime"));
						order.setId(getIntValueByTagName(e, "id"));
						order.setIsship(getIntValueByTagName(e, "isship"));
						order.setOrdercode(getValueByTagName(e, "ordercode"));
						order.setOrderprice(getFloatValueByTagName(e,
								"orderprice"));
						order.setPaycode(getIntValueByTagName(e, "paycode"));
						order.setPayprice(getFloatValueByTagName(e, "payprice"));
						order.setPaytime(getValueByTagName(e, "paytime"));
						order.setShipcode(getValueByTagName(e, "shipcode"));
						order.setShiptime(getValueByTagName(e, "shiptime"));
						order.setOrder_status(getIntValueByTagName(e, "status"));
						order.setTeader(getIntValueByTagName(e, "theader"));
						order.setTtype(getIntValueByTagName(e, "ttype"));
						order.setUid(getIntValueByTagName(e, "uid"));
						order.setAdress(getAddress(getValueByTagName(e,
								"addressval")));

						NodeList address_node = e
								.getElementsByTagName("addressinfos");
						if (address_node != null) {
							for (int k = 0; k < address_node.getLength(); k++) {
								Adress address = new Adress();
								Element eItem = (Element) address_node.item(k);
								address.setId(getIntValueByTagName(eItem, "id"));
								address.setConsgneedname(getValueByTagName(
										eItem, "consgneename"));
								address.setRegion(getValueByTagName(eItem,
										"region"));
								address.setFulladdress(getValueByTagName(eItem,
										"fulladdress"));
								address.setZip(getValueByTagName(eItem, "zip"));
								address.setPhone(getValueByTagName(eItem,
										"phone"));
								address.setIsdefault(getIntValueByTagName(
										eItem, "isdefault"));
								address.setAddtime(getValueByTagName(eItem,
										"addtime"));
								String regionid = getValueByTagName(e,
										"regionid");
								String ids[] = regionid.split(",");
								if (ids != null && ids.length != 0) {
									address.setPrivenceid(Integer
											.valueOf(ids[0]));
									address.setCityid(Integer.valueOf(ids[1]));
									address.setAreaid(Integer.valueOf(ids[2]));
								}
								order.setAdress(address);
							}

						}

						NodeList itemNodes = e.getElementsByTagName("child");
						List<OrderItem> orderItemList = new ArrayList<OrderItem>();
						if (itemNodes != null) {
							for (int k = 0; k < itemNodes.getLength(); k++) {
								Element eItem = (Element) itemNodes.item(k);
								OrderItem order_item = new OrderItem();
								order_item.setId(getIntValueByTagName(eItem,
										"id"));
								order_item.setCoverpath(getValueByTagName(
										eItem, "coverpath"));
								order_item
										.setProduceprice(getFloatValueByTagName(
												e, "productprice"));
								order_item.setProductnun(getIntValueByTagName(
										e, "productnum"));
								order_item.setTitleval(getValueByTagName(eItem,
										"titleval"));
								order_item.setTypetitle(getValueByTagName(
										eItem, "typetitle"));
								orderItemList.add(order_item);
							}

						}
						order.setItemList(orderItemList);
						list.add(order);
					}
				}
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("e::::::::::::::" + e.toString());
		}
		return RetError.INVALID;
	}

	private Adress getAddress(String str) {
		Adress address = new Adress();
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(str);
			address.setId(jsonObj.getInt("address_id"));
			address.setConsgneedname(jsonObj.getString("consignee_name"));
			address.setRegion(jsonObj.getString("region"));
			address.setFulladdress(jsonObj.getString("address"));
			address.setZip(jsonObj.getString("zip"));
			address.setPhone(jsonObj.getString("phone"));
			address.setAddtime(jsonObj.getString("add_time"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;

	}
}
