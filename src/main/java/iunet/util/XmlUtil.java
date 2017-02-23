package iunet.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class XmlUtil {

	public static String escapeXml(String str) {
		if (StringUtil.isNullOrEmpty(str))
			return "";
		return str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	public static String unescapeXml(String str) {
		if (StringUtil.isNullOrEmpty(str))
			return "";
		return str.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
	}

	public static String tryGetItemText(Node node, String itemName, String defaultValue) {
		if (node == null)
			return defaultValue;

		Node item = node.selectSingleNode(itemName);
		if (item == null)
			return defaultValue;

		return item.getText();
	}

	public static Document list2xml(List list, String elementName) {
		Document doc = DocumentHelper.createDocument();
		Element element = doc.addElement("dataSource");
		int i = 0;
		for (Object o : list) {
			Element nodeElement = element.addElement(elementName);
			if (o instanceof Map) {
				Map m = (Map) o;
				for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext();) {
					Entry entry = (Entry) iterator.next();
					Element keyElement = nodeElement.addElement(entry.getKey().toString());
					if (entry.getValue() instanceof List) {
						list2xml((List) entry.getValue(), keyElement);
					} else {
						keyElement.setText(entry.getValue().toString());
					}
				}
			} else if (o instanceof List) {
				list2xml((List) o, nodeElement);
			} else {
				Element keyElement = nodeElement.addElement("value");
				keyElement.addAttribute("num", String.valueOf(i));
				keyElement.setText(String.valueOf(o));
			}
			i++;
		}
		return doc;
	}

	public static Element list2xml(List list, Element element) {
		int i = 0;
		for (Object o : list) {
			Element nodeElement = element.addElement("list");
			if (o instanceof Map) {
				Map m = (Map) o;
				for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext();) {
					Entry entry = (Entry) iterator.next();
					Element keyElement = nodeElement.addElement(entry.getKey().toString());
					if (entry.getValue() instanceof List) {
						list2xml((List) entry.getValue(), keyElement);
					} else {
						keyElement.setText(entry.getValue().toString());
					}
				}
			} else if (o instanceof List) {
				list2xml((List) o, nodeElement);
			} else {
				Element keyElement = nodeElement.addElement("value");
				keyElement.addAttribute("num", String.valueOf(i));
				keyElement.setText(String.valueOf(o));
			}
			i++;
		}
		return element;
	}
}