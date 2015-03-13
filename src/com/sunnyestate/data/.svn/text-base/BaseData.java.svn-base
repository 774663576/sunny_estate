package com.sunnyestate.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class BaseData {
	public String getValueByTagName(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			Element e = (Element) nodeList.item(0);
			if (e != null && e.getFirstChild() != null) {
				return e.getFirstChild().getNodeValue().trim();
			}
		}
		return "";
	}

	public String getAttributeValueByTagName(Element element, String tagName) {
		String s = element.getAttribute(tagName);
		return s;
	}

	public int getIntValueByTagName(Element element, String tagName) {
		String s = getValueByTagName(element, tagName);
		if (s == "")
			return 0;

		return Integer.parseInt(s);
	}

	public Float getFloatValueByTagName(Element element, String tagName) {
		String s = getValueByTagName(element, tagName);
		if (s == "")
			return 0f;

		return Float.valueOf(s);
	}

	public int getIntAttributeValueByTagName(Element element, String tagName) {
		String s = getAttributeValueByTagName(element, tagName);
		if (s == null || s.length() < 1 || "".equals(s))
			return 0;
		return Integer.parseInt(s);
	}

	public Object[] getRootElement(String result) {
		int code = 0;
		String message = "";
		Element rootElement = null;
		try {
			InputStream inputStream = new ByteArrayInputStream(
					result.getBytes());
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);
			rootElement = doc.getDocumentElement();
			NodeList nodes = rootElement.getElementsByTagName("result");
			if (nodes != null && nodes.getLength() > 0) {
				Element e = (Element) nodes.item(0);
				code = getIntValueByTagName(e, "code");
				message = getValueByTagName(e, "message");
				if (code == 0) {
					return new Object[] { code, rootElement, message };
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Object[] { -1, rootElement, message };

	}
}
