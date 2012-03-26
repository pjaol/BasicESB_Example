package com.pjaol.ESB;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RSSDomParser {
	
	public static List<RSSItem> getRSSItems(String rss){
		
		List<RSSItem> items = new ArrayList<RSSItem>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom = null;
		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			//dom = db.parse(rss);
			dom = db.parse(new ByteArrayInputStream(rss.getBytes("UTF-8")));


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		NodeList rssItems = dom.getElementsByTagName("item");
		int sz = rssItems.getLength();
		for(int i =0; i< sz; i++){
			
			Element el =(Element) rssItems.item(i);
			String title = getTextValue(el, "title");
			String link = getTextValue(el, "link");
			String description = getTextValue(el, "description");
			
			RSSItem ri = new RSSItem(title, link, description);
			items.add(ri);
		}
		
		
		return items;
		
	}
	
	
	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

}
