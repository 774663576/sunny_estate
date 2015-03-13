package com.sunnyestate.data;

import java.io.Serializable;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.HttpUrlHelper;

public class CategoryDataDetail extends BaseData implements Serializable {
	private static final String DETAIL_API = "detail/id/";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int producttype;
	private String producttile = "";
	private String defaultimg = "";
	private String smalltitle = "";
	private String description = "";
	private float price;
	private float originalprice;// ԭ��
	private int notproduct;
	private int brands;
	private int sweet;
	private int types;
	private int istop;
	private String configinfo = "";

	@Override
	public String toString() {
		return "id:" + this.id + " title:" + this.smalltitle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProducttype() {
		return producttype;
	}

	public void setProducttype(int producttype) {
		this.producttype = producttype;
	}

	public String getProducttile() {
		return producttile;
	}

	public void setProducttile(String producttile) {
		this.producttile = producttile;
	}

	public String getDefaultimg() {
		return defaultimg;
	}

	public void setDefaultimg(String defaultimg) {
		this.defaultimg = defaultimg;
	}

	public String getSmalltitle() {
		return smalltitle;
	}

	public void setSmalltitle(String smalltitle) {
		this.smalltitle = smalltitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(float originalprice) {
		this.originalprice = originalprice;
	}

	public int getNotproduct() {
		return notproduct;
	}

	public void setNotproduct(int notproduct) {
		this.notproduct = notproduct;
	}

	public int getBrands() {
		return brands;
	}

	public void setBrands(int brands) {
		this.brands = brands;
	}

	public int getSweet() {
		return sweet;
	}

	public void setSweet(int sweet) {
		this.sweet = sweet;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public int getIstop() {
		return istop;
	}

	public void setIstop(int istop) {
		this.istop = istop;
	}

	public String getConfiginfo() {
		return configinfo;
	}

	public void setConfiginfo(String configinfo) {
		this.configinfo = configinfo;
	}

	public RetError refushDetailByID() {
		String result = HttpUrlHelper.getUrlData(DETAIL_API + id);
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
			NodeList nodes = rootElement.getElementsByTagName("item");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				setId(getIntValueByTagName(e, "id"));
				setProducttype(getIntValueByTagName(e, "producttype"));
				setProducttile(getValueByTagName(e, "producttitle"));
				setDefaultimg(getValueByTagName(e, "defaultimg"));
				setSmalltitle(getValueByTagName(e, "smalltitle"));
				setDescription(getValueByTagName(e, "description"));
				setPrice(getFloatValueByTagName(e, "price"));
				setOriginalprice(getFloatValueByTagName(e, "originalprice"));
				setNotproduct(getIntValueByTagName(e, "notproduct"));
				setBrands(getIntValueByTagName(e, "brands"));
				setSweet(getIntValueByTagName(e, "sweet"));
				setTypes(getIntValueByTagName(e, "types"));
				setIstop(getIntValueByTagName(e, "istop"));
				setConfiginfo(getValueByTagName(e, "configinfo"));
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetError.INVALID;
	}
}
