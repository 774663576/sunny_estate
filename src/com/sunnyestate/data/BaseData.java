package com.sunnyestate.data;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class BaseData {
	public static String getValueByTagName(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			Element e = (Element) nodeList.item(0);
			if (e != null && e.getFirstChild() != null) {
				return e.getFirstChild().getNodeValue().trim();
			}
		}
		return "";
	}

	public static String getAttributeValueByTagName(Element element,
			String tagName) {
		String s = element.getAttribute(tagName);
		return s;
	}

	public static int getIntValueByTagName(Element element, String tagName) {
		String s = getValueByTagName(element, tagName);
		if (s == "")
			return 0;

		return Integer.parseInt(s);
	}

	public static Float getFloatValueByTagName(Element element, String tagName) {
		String s = getValueByTagName(element, tagName);
		if (s == "")
			return 0f;

		return Float.valueOf(s);
	}

	public static int getIntAttributeValueByTagName(Element element,
			String tagName) {
		String s = getAttributeValueByTagName(element, tagName);
		if (s == null || s.length() < 1)
			return 0;
		return Integer.parseInt(s);
	}
}
