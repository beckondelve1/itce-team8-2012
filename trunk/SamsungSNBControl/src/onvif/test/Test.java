package onvif.test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

import onvif.model.WsHelper;
import onvif.model.device.GetCapabilitiesRequest;
import onvif.model.device.GetCapabilitiesResponse;
import onvif.model.device.GetSystemDateAndTimeRequest;
import onvif.model.device.GetSystemDateAndTimeResponse;
import onvif.model.media.GetProfileRequest;
import onvif.model.media.GetProfilesRequest;
import onvif.model.media.GetServiceCapabilititesRequest;
import onvif.model.media.GetStreamUriRequest;
import onvif.model.media.GetStreamUriResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import test.XMLParser;

public class Test {
	//private static final String DEVICE_URL = "http://119.202.84.112/onvif/device_service";
	//private static final String MEDIA_URL = "http://119.202.84.112/onvif/media_service";
	
	private static final String DEVICE_URL = "https://119.202.84.112/onvif/device_service";		//SSL
	private static final String MEDIA_URL = "https://119.202.84.112/onvif/media_service";		//SSL

	//
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
		
		/*--- MEDIA ---*/
		testMediaGetServiceCapabilities();
		testGetProfiles();
		testGetProfile();
		testGetStreamUri();
		
		
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
		request.prepareSOAPRequest(serverTimeOffset, Arrays.asList("All"));
		
		String result = WsHelper.callWebService(request);
		System.out.println("result="+result);
		
		GetCapabilitiesResponse response = new GetCapabilitiesResponse(result);
	}
	
	//MEDIA
	private static void testMediaGetServiceCapabilities(){
		GetServiceCapabilititesRequest request = new GetServiceCapabilititesRequest(MEDIA_URL, USERNAME, PASSWORD);
		request.prepareSOAPRequest(serverTimeOffset, null);
		
		String result = WsHelper.callWebService(request);
		System.out.println("result="+result);
		
		//GetCapabilitiesResponse response = new GetCapabilitiesResponse(result);
	}
	
	private static void testGetProfiles(){
		GetProfilesRequest request = new GetProfilesRequest(MEDIA_URL, USERNAME, PASSWORD);
		request.prepareSOAPRequest(serverTimeOffset, null);
		
		String result = WsHelper.callWebService(request);
		System.out.println("result="+result);
		
		//GetCapabilitiesResponse response = new GetCapabilitiesResponse(result);
	}
	
	private static void testGetProfile(){
		GetProfileRequest request = new GetProfileRequest(MEDIA_URL, USERNAME, PASSWORD);
		request.prepareSOAPRequest(serverTimeOffset, Arrays.asList("786bd0f6-bcb5-4492-8e12-e096a979c250"));
		
		String result = WsHelper.callWebService(request);
		System.out.println("result="+result);
		
		//GetCapabilitiesResponse response = new GetCapabilitiesResponse(result);
	}
	
	private static void testGetStreamUri(){
		GetStreamUriRequest request = new GetStreamUriRequest(MEDIA_URL, USERNAME, PASSWORD);
		request.prepareSOAPRequest(serverTimeOffset, Arrays.asList("RTP-Unicast", "UDP", 
				"786bd0f6-bcb5-4492-8e12-e096a979c250"));
		
		String result = WsHelper.callWebService(request);
		System.out.println("result="+result);
		
		GetStreamUriResponse response = new GetStreamUriResponse(result);
		
		System.out.println("Uri="+response.getUri());
	}
}
