package test;

import java.io.IOException;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;


public class WsByHttpPostTest {

	//
	Calendar serverTimeOffset;
	//
	String deviceURL = "http://119.202.84.112/onvif/device_service";
	String imagingURL = "http://119.202.84.112/onvif/imaging_service";
	
	//
	String envelopeGetSystemDateAndTime = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetSystemDateAndTime xmlns=\"http://www.onvif.org/ver10/device/wsdl\"/></s:Body></s:Envelope>";
	String actionGetSystemDateAndTime = "http://www.onvif.org/ver10/device/wsdl/GetSystemDateAndTime";
	
	//
	String envelopeGetNetworkInterfaces = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"><s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><UsernameToken><Username>admin</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">sQG5LKtTO6ruG0pa0f61e+/ECXI=</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">9UsSib/j/UCKuWrmRWJmbRQAAAAAAA==</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">2012-11-10T10:23:48.001Z</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetNetworkInterfaces xmlns=\"http://www.onvif.org/ver10/device/wsdl\"/></s:Body></s:Envelope>";
	String actionGetNetworkInterfaces = "http://www.onvif.org/ver10/device/wsdl/GetNetworkInterfaces";
	
	//
	String envelopeGetDeviceInformation = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"><s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><UsernameToken><Username>admin</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">dfKg9cDbQouDKIElfvGa66xp8bc=</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">KYINf1gXrUSo4Ia2lD2HWAEAAAAAAA==</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">2012-11-09T09:29:30.010Z</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetDeviceInformation xmlns=\"http://www.onvif.org/ver10/device/wsdl\"/></s:Body></s:Envelope>";
	
	//
	String envelopeGetCapabilities = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"><s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><UsernameToken><Username>admin</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">E8DfOqP1XqZ+PsgMRzcXGpLSU7Y=</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">KYINf1gXrUSo4Ia2lD2HWAUAAAAAAA==</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">2012-11-09T09:29:30.013Z</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetCapabilities xmlns=\"http://www.onvif.org/ver10/device/wsdl\"><Category>All</Category></GetCapabilities></s:Body></s:Envelope>";
	String actionGetCapabilities = "http://www.onvif.org/ver10/device/wsdl/GetCapabilities";
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WsByHttpPostTest obj = new WsByHttpPostTest();
		//
		//always compute TIME-OFFSET and save it to 'serverTimeOffset'
		obj.testGetSystemDateAndTime();
		obj.testGetNetworkInterfaces();
		obj.testGetCapabilities();

	}
	
	

	//
	private void testGetSystemDateAndTime(){
		System.out.println("testGetSystemDateAndTime");
		//
        String result = callWebService(deviceURL, actionGetSystemDateAndTime, prepareEnvelopeGetSystemDateAndTime());
        
        if(result != null){
			System.out.println(result);
		}
	}
	
	//
	private void testGetNetworkInterfaces(){
		System.out.println("testGetNetworkInterfaces");
		//
		String request = prepareEnvelopeGetNetworkInterfaces();
		System.out.println("request = " + request);
		//
        String result = callWebService(deviceURL, actionGetNetworkInterfaces, request);
        
        if(result != null){
			System.out.println(result);
		}
	}
	
	//
	private void testGetCapabilities(){
		System.out.println("testGetCapabilities");
		//
		String request = prepareEnvelopeGetCapabilities();
		System.out.println("request = " + request);
		//
		//String result = callWebService(deviceURL, actionGetCapabilities, envelopeGetCapabilities);
        String result = callWebService(deviceURL, actionGetCapabilities, request);
        
        if(result != null){
			System.out.println(result);
		}
	}
	
	
	//callWebService
	private String callWebService(String url, String soapAction, String envelope) {
		
		final DefaultHttpClient httpClient = new DefaultHttpClient();
		// request parameters
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		HttpConnectionParams.setSoTimeout(params, 15000);
		// set parameter
		HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), false);	//IMPORTANT !!

		// POST the envelope
		HttpPost httppost = new HttpPost(url);
		// add headers
		//httppost.setHeader("SOAPAction", soapAction);
		httppost.setHeader("Content-Type", "application/soap+xml; charset=utf-8; action=\"" + soapAction + "\"");

		String responseString = "";
		try {

			// the entity holds the request
			HttpEntity entity = new StringEntity(envelope);
			httppost.setEntity(entity);

			// Response handler
			ResponseHandler<String> rh = new ResponseHandler<String>() {
				// invoked when client receives response
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {

					// get response entity
					HttpEntity entity = response.getEntity();

					// read the response as byte array
					StringBuffer out = new StringBuffer();
					byte[] b = EntityUtils.toByteArray(entity);

					// write the response byte array to a string buffer
					out.append(new String(b, 0, b.length));
					return out.toString();
				}
			};

			responseString = httpClient.execute(httppost, rh);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// close the connection
		httpClient.getConnectionManager().shutdown();
		return responseString;
	}
	
	//
	//[GetSystemDateAndTime]
    private String prepareEnvelopeGetSystemDateAndTime(){
		//
		String envelopeString = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
				"<s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
				"<GetSystemDateAndTime xmlns=\"http://www.onvif.org/ver10/device/wsdl\"/></s:Body></s:Envelope>";
		
		//
		return envelopeString;
		
    }
	
	//
	//[GetNetworkInterfaces]
    private String prepareEnvelopeGetNetworkInterfaces(){
    	// recover nonce from hexString
    	String hexString = "68C7090569FFAF65C5976B163AC0602ED30431DB32B5D4ED7CF862D5944C0AB0";
		byte[] nonce = new byte[32];
		for (int i = 0;i < nonce.length;i++)
			nonce[i] += (byte)Integer.parseInt(hexString.substring(2*i, 2*i+2), 16);
    	
		//
		TokenGenerator gen = new TokenGenerator(nonce, "4321", null);
		
		//
		String envelopeString = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
				"<s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
				"<UsernameToken><Username>" +
				"%s</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" +
				"%s</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" +
				"%s</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
				"%s</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetNetworkInterfaces xmlns=\"http://www.onvif.org/ver10/device/wsdl\"/></s:Body></s:Envelope>";
		
		//
		return String.format(envelopeString, "admin", gen.getHashedFinal(), gen.getNonceBase64(), 
				gen.getCreatedText());
		
    }
    
    //
	//[GetCapabilities]
    private String prepareEnvelopeGetCapabilities(){
    	// recover nonce from hexString
    	String hexString = "68C7090569FFAF65C5976B163AC0602ED30431DB32B5D4ED7CF862D5944C0AB0";
		byte[] nonce = new byte[32];
		for (int i = 0;i < nonce.length;i++)
			nonce[i] += (byte)Integer.parseInt(hexString.substring(2*i, 2*i+2), 16);
    	
		//
		TokenGenerator gen = new TokenGenerator(nonce, "4321", null);
		
		//
		String envelopeString = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
				"<s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
				"<UsernameToken><Username>" +
				"%s</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" +
				"%s</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" +
				"%s</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
				"%s</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetCapabilities xmlns=\"http://www.onvif.org/ver10/device/wsdl\"><Category>All</Category>" +
				"</GetCapabilities></s:Body></s:Envelope>";
		
		//
		return String.format(envelopeString, "admin", gen.getHashedFinal(), gen.getNonceBase64(), 
				gen.getCreatedText());
		
    }
}
