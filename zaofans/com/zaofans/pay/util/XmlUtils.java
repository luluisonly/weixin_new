package com.zaofans.pay.util;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.bokesoft.myerp.common.io.CloseUtil;

public class XmlUtils {
	public static String maptoXml(Map<?, ?> map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("xml");
		for (Object obj : map.keySet()) {
			Element keyElement = nodeElement.addElement(String.valueOf(obj));
			keyElement.setText(String.valueOf(map.get(obj)));
		}
		return doc2String(document);
	}
	public static String doc2String(Document document) {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			// 使用输出流来进行转化
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			format.setSuppressDeclaration(true);
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			return out.toString("UTF-8");
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		} finally {
			CloseUtil.close(out);
		}
	}
	public static Map<String, String> parseContent(String content) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Document document = DocumentHelper.parseText(content);
			Element employees = document.getRootElement();
			for (Iterator<?> i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				map.put(employee.getName(), employee.getText());
			}
			return map;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
