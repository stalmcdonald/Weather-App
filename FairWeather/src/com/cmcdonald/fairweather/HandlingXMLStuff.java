package com.cmcdonald.fairweather;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class HandlingXMLStuff extends DefaultHandler{

	private XMLDataCollected info = new XMLDataCollected();
	
	public String getInformation(){
		return info.dataToString();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		
		if (localName.equals("mycity")){
			String mycity = attributes.getValue("city");
			info.setCity(mycity);
		}else if  (localName.equals("temp_f")){
			String t = attributes.getValue("city");
			int temp = Integer.parseInt(t);
			info.setTemp(temp);
		}
		
	}	
	
}