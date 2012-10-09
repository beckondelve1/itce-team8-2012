package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;

import com.hammingweight.kiss.KissEnvelope;
import com.hammingweight.kiss.SecurityHeader;
import com.hammingweight.kiss.crypto.Aes128;
import com.hammingweight.kiss.crypto.Sha1Hash;
import com.hammingweight.kiss.crypto.X931Generator;
import com.hammingweight.kiss.usernametoken.UsernameTokenHashed;

public class Connect {

	private static String ADDRESS = "http://119.202.84.99"; 
	private static String USERNAME = "admin";
	private static String PASSWORD = "4321";
	
	//Kiss Envelope
    private static byte[] nonce = { (byte) 104, (byte) -60, (byte) -99, (byte) 43,
			(byte) -55, (byte) -46, (byte) 94, (byte) 76, (byte) 24,
			(byte) 39, (byte) -38, (byte) 64, (byte) 40, (byte) 72,
			(byte) -124, (byte) 111 };
    private static byte[] testKey = new byte[] { (byte) 0x2b, (byte) 0x7e, (byte) 0x15,
			(byte) 0x16, (byte) 0x28, (byte) 0xae, (byte) 0xd2,
			(byte) 0xa6, (byte) 0xab, (byte) 0xf7, (byte) 0x15,
			(byte) 0x88, (byte) 0x09, (byte) 0xcf, (byte) 0x4f, (byte) 0x3c };
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		//testGetSystemDateAndTime();
		
		//testGetDeviceInformation();
		
		//testGetCapabilities();
		
		testGetScopes();
		
		//testGetImagingSettings();
		
	}
	
	
	
	
	
	
	
	
	//GetSystemDateAndTime
	private static void testGetSystemDateAndTime(){
		String SOAP_ACTION = "http://www.onvif.org/ver10/device/wsdl/GetSystemDateAndTime";
		
		String NAMESPACE = "http://www.onvif.org/ver10/device/wsdl";
		String METHOD_NAME = "GetSystemDateAndTime";
		
		String URL = ADDRESS + "/onvif/device_service";
		
		//Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);        
       
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(request);
     
        // Make the soap call.
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
        	
        	//this is the actual part that will call the webservice
			androidHttpTransport.call(SOAP_ACTION, envelope);        
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
        
		// Get the SoapResult from the envelope body.		
		SoapObject result = (SoapObject)envelope.bodyIn;
		
		System.out.println(result.getProperty(0).toString());
	}
	
	
	
	//GetSystemDateAndTime2
	private static void testGetSystemDateAndTime2(){
		
		
		
	}
	
	
	
	
	//GetDeviceInformation
	private static void testGetDeviceInformation(){
		String SOAP_ACTION = "http://www.onvif.org/ver10/device/wsdl/GetDeviceInformation";
		
		String NAMESPACE = "http://www.onvif.org/ver10/device/wsdl";
		String METHOD_NAME = "GetDeviceInformation";
		
		String URL = ADDRESS + "/onvif/device_service";
		
		//Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);        
       
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(request);
     
        // Make the soap call.
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
        	
        	//this is the actual part that will call the webservice
			androidHttpTransport.call(SOAP_ACTION, envelope);        
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
        
		// Get the SoapResult from the envelope body.		
		SoapObject result = (SoapObject)envelope.bodyIn;
		
		System.out.println(result.getProperty(0).toString());
	}
	
	
	
	//GetCapabilities
	private static void testGetCapabilities(){
		String SOAP_ACTION = "http://www.onvif.org/ver10/device/wsdl/GetCapabilities";
		
		String NAMESPACE = "http://www.onvif.org/ver10/device/wsdl";
		String METHOD_NAME = "GetCapabilities";
		
		String URL = ADDRESS + "/onvif/device_service";
		
		//Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);        
        PropertyInfo pi = new PropertyInfo();
        pi.setName("Category");
        pi.setValue("All");
        pi.setType(PropertyInfo.STRING_CLASS);  
        request.addProperty(pi);

        
        UsernameTokenHashed tokenHashed = new UsernameTokenHashed(USERNAME, PASSWORD, new Date(), 
        		new X931Generator(testKey, testKey), 32, new Sha1Hash());
        SecurityHeader header = new SecurityHeader(tokenHashed);
        
        KissEnvelope envelope = new KissEnvelope(SoapEnvelope.VER12);
        header.appendToEnvelope(envelope);
        
        envelope.setOutputSoapObject(request);
        
        // Make the soap call.
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		//androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
		
        try {
        	//this is the actual part that will call the webservice
        	androidHttpTransport.call(SOAP_ACTION, envelope);
			
			// Get the SoapResult from the envelope body.		
			SoapObject result = (SoapObject)envelope.getResponse();
			
			System.out.println(result.getProperty(0).toString());
			
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
        
	}
	
	//GetScopes
	private static void testGetScopes(){
		String SOAP_ACTION = "http://www.onvif.org/ver10/device/wsdl/GetScopes";
		
		String NAMESPACE = "http://www.onvif.org/ver10/device/wsdl";
		String METHOD_NAME = "GetScopes";
		
		String URL = ADDRESS + "/onvif/device_service";
		
		//Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);        
        
        
        UsernameTokenHashed tokenHashed = new UsernameTokenHashed(USERNAME, PASSWORD, new Date(), 
        		new X931Generator(testKey, testKey), 32, new Sha1Hash());
        SecurityHeader header = new SecurityHeader(tokenHashed);
        
        KissEnvelope envelope = new KissEnvelope(SoapEnvelope.VER12);
        header.appendToEnvelope(envelope);
        
        envelope.setOutputSoapObject(request);
        
        // Make the soap call.
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
        	//this is the actual part that will call the webservice
        	androidHttpTransport.call(SOAP_ACTION, envelope);
			
			// Get the SoapResult from the envelope body.		
			SoapObject result = (SoapObject)envelope.getResponse();
			
			System.out.println(result.getProperty(0).toString());
			
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
        
	}
	
	//GetImagingSettings
	private static void testGetImagingSettings(){
		String SOAP_ACTION = "http://www.onvif.org/ver20/imaging/wsdl/GetImagingSettings";
		
		String NAMESPACE = "http://www.onvif.org/ver20/imaging/wsdl";
		String METHOD_NAME = "GetImagingSettings";
		
		String URL = ADDRESS + "/onvif/imaging_service";
		
		//Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);        
        PropertyInfo pi = new PropertyInfo();
        pi.setName("n2:VideoSourceToken");
        pi.setValue("bd449516-4c96-44a6-a163-3ad5981c09fd");
        pi.setType(PropertyInfo.STRING_CLASS);
        request.addProperty(pi);
        
               
//	        UsernameTokenHashed tokenHashed = new UsernameTokenHashed(USERNAME, PASSWORD, new Date(), 
//	        		nonce, new Sha1Hash());
        
        UsernameTokenHashed tokenHashed = new UsernameTokenHashed(USERNAME, PASSWORD, new Date(), 
        		new X931Generator(testKey, testKey), 32, new Sha1Hash());
        SecurityHeader header = new SecurityHeader(tokenHashed);
        
        KissEnvelope envelope = new KissEnvelope(SoapEnvelope.VER12);
        header.appendToEnvelope(envelope);
        
        envelope.setOutputSoapObject(request);
        
        // Make the soap call.
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
        	//this is the actual part that will call the webservice
        	androidHttpTransport.call(SOAP_ACTION, envelope);
			
			// Get the SoapResult from the envelope body.		
			SoapObject result = (SoapObject)envelope.getResponse();
			
			System.out.println(result.getProperty(0).toString());
			
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
        
	}
		
	//dotNet - authentication
	private static void testConnect(){
		String NAMESPACE = "http://www.namespace.com/";
		String METHOD_NAME = "MethodName";
		String SOAP_ACTION = "http://www.namespace.com/MethodName";
		String URL = "https://www.namespace.com/services/Service.asmx";

	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        // Set all input params   
        request.addProperty("property", "value");   
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        // Enable the below property if consuming .Net service
	    envelope.dotNet = true;
	    envelope.setOutputSoapObject(request);

	    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	    try 
	    {
	        List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
	        headerList.add(new HeaderProperty("Authorization", "Basic " + org.kobjects.base64.Base64.encode("username:password".getBytes())));
	        
	        androidHttpTransport.call(SOAP_ACTION, envelope, headerList);
         
	        SoapObject response = (SoapObject)envelope.getResponse();
	        //response.getProperty(0).toString();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }

	}
}
