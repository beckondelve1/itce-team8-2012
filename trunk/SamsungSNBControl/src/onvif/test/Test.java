package onvif.test;

import java.util.Calendar;
import java.util.TimeZone;

import onvif.model.WsHelper;
import onvif.model.device.GetCapabilitiesRequest;
import onvif.model.device.GetCapabilitiesResponse;
import onvif.model.device.GetSystemDateAndTimeRequest;
import onvif.model.device.GetSystemDateAndTimeResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import test.XMLParser;

public class Test {
	private static final String DEVICE_URL = "http://119.202.84.112/onvif/device_service";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "4321";
	
	//
	private static int serverTimeOffset = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		serverTimeOffset = (int)computeTimeOffset();
		System.out.println("serverTimeOffset=" + serverTimeOffset);
		//
		testGetCapabilities();
		
	}

	private static long computeTimeOffset(){
		GetSystemDateAndTimeRequest request = new GetSystemDateAndTimeRequest(DEVICE_URL);
		String result = WsHelper.callWebService(request);
		
		GetSystemDateAndTimeResponse response = new GetSystemDateAndTimeResponse(result);
		
		//
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.set(response.getYear(), response.getMonth()-1, response.getDay(), 
				response.getHour(), response.getMinute(), response.getSecond());
		
		return cal.getTimeInMillis() - System.currentTimeMillis();
	}
	
	private static void testGetCapabilities(){
		GetCapabilitiesRequest request = new GetCapabilitiesRequest(DEVICE_URL, USERNAME, PASSWORD);
		request.prepareSOAPRequest(serverTimeOffset);
		
		String result = WsHelper.callWebService(request);
		System.out.println("result="+result);
		
		GetCapabilitiesResponse response = new GetCapabilitiesResponse(result);
	}
}
