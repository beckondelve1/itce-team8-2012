package onvif.model.device;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import test.XMLParser;

public class GetSystemDateAndTimeResponse {
	
	private int hour;
	private int minute;
	private int second;
	private int year;
	private int month;
	private int day;

	//Ctor
	public GetSystemDateAndTimeResponse(){
		
	}
	
	public GetSystemDateAndTimeResponse(String response){
		String TIME_ITEM = "tt:Time";
		String DATE_ITEM = "tt:Date"; 
		
		
		XMLParser parser = new XMLParser();
		Document doc = parser.getDomElement(response);
		//Time
		NodeList nl = doc.getElementsByTagName(TIME_ITEM);
		//
		Element e = (Element)nl.item(0);
		hour = Integer.parseInt(parser.getValue(e, "tt:Hour"));
		minute = Integer.parseInt(parser.getValue(e, "tt:Minute"));
		second = Integer.parseInt(parser.getValue(e, "tt:Second"));
		
		//Date
		nl = doc.getElementsByTagName(DATE_ITEM);
		//
		e = (Element)nl.item(0);
		year = Integer.parseInt(parser.getValue(e, "tt:Year"));
		month = Integer.parseInt(parser.getValue(e, "tt:Month"));
		day = Integer.parseInt(parser.getValue(e, "tt:Day"));
	}

	//
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	//
	
}
