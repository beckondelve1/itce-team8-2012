package test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class OnvifResponseParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseGetSystemDateAndTime();

	}
	
	private static void parseGetSystemDateAndTime(){
		String response = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:SOAP-ENC=\"http://www.w3.org/2003/05/soap-encoding\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:c14n=\"http://www.w3.org/2001/10/xml-exc-c14n#\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsa=\"http://tempuri.org/wsa.xsd\" xmlns:xmime=\"http://tempuri.org/xmime.xsd\" xmlns:xop=\"http://www.w3.org/2004/08/xop/include\" xmlns:tt=\"http://www.onvif.org/ver10/schema\" xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\" xmlns:wsrf-bf=\"http://docs.oasis-open.org/wsrf/bf-2\" xmlns:wstop=\"http://docs.oasis-open.org/wsn/t-1\" xmlns:tds=\"http://www.onvif.org/ver10/device/wsdl\" xmlns:ter=\"http://www.onvif.org/ver10/error\"><SOAP-ENV:Body><tds:GetSystemDateAndTimeResponse><tds:SystemDateAndTime><tt:DateTimeType>Manual</tt:DateTimeType><tt:DaylightSavings>false</tt:DaylightSavings><tt:TimeZone><tt:TZ>STWT0STWST,M3.5.0/1,M10.5.0</tt:TZ></tt:TimeZone><tt:UTCDateTime><tt:Time><tt:Hour>3</tt:Hour><tt:Minute>13</tt:Minute><tt:Second>55</tt:Second></tt:Time><tt:Date><tt:Year>2012</tt:Year><tt:Month>11</tt:Month><tt:Day>11</tt:Day></tt:Date></tt:UTCDateTime></tds:SystemDateAndTime></tds:GetSystemDateAndTimeResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		//
		String TIME_ITEM = "tt:Time";
		String DATE_ITEM = "tt:Date"; 
		
		
		XMLParser parser = new XMLParser();
		Document doc = parser.getDomElement(response);
		//Time
		NodeList nl = doc.getElementsByTagName(TIME_ITEM);
		//
		Element e = (Element)nl.item(0);
		System.out.println(parser.getValue(e, "tt:Hour"));
		System.out.println(parser.getValue(e, "tt:Minute"));
		System.out.println(parser.getValue(e, "tt:Second"));
		
		//Date
		nl = doc.getElementsByTagName(DATE_ITEM);
		//
		e = (Element)nl.item(0);
		System.out.println(parser.getValue(e, "tt:Year"));
		System.out.println(parser.getValue(e, "tt:Month"));
		System.out.println(parser.getValue(e, "tt:Day"));
	}

}
