package itce.team8.onvif;

import java.io.IOException;

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
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



public class KSOAPTestActivity extends Activity {
	private static final String LOG_TAG = "KSOAPTestActivity";
	
	//
	String envelopeGetSystemDateAndTime = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
		    "<s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + 
		        "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" + 
		        "<GetSystemDateAndTime xmlns=\"http://www.onvif.org/ver10/device/wsdl\"/>"+
		    "</s:Body></s:Envelope>";
	//
	String deviceURL = "http://119.202.84.112/onvif/device_service";
	String actionGetSystemDateAndTime = "http://www.onvif.org/ver10/device/wsdl/GetSystemDateAndTime";
	
	//
	String envelopeGetImagingSettings = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
			"<s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
			"<UsernameToken><Username>admin</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" +
			"eL6TFq6jDpIxZJANeR76UWEfY8g=</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" +
			"NrPuIZ+D0UOrIOAHwnHClBIAAAAAAAAAxTF5vgIAAAA=</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
			"2012-05-21T08:31:12.192Z</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
			"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetImagingSettings xmlns=\"http://www.onvif.org/ver20/imaging/wsdl\"><VideoSourceToken>" +
			"bd449516-4c96-44a6-a163-3ad5981c09fd</VideoSourceToken></GetImagingSettings></s:Body></s:Envelope>";
	String imagingURL = "http://119.202.84.99/onvif/imaging_service";
	String actionGetImagingSettings = "http://www.onvif.org/ver10/device/wsdl/GetSystemDateAndTime";
	
	//
	String envelopeGetCapabilities = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
			"<s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
			"<UsernameToken><Username>admin</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" +
			"F1gq12qlIhFNRYEcK7qSySCZP70=</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" +
			"8jiuCl3XWU265VVkxPke4h4AAAAAAAAA33LhuxEAAAA=</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
			"2012-05-04T09:12:22.001Z</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
			"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetCapabilities xmlns=\"http://www.onvif.org/ver10/device/wsdl\"><Category>All</Category>" +
			"</GetCapabilities></s:Body></s:Envelope>";
	String actionGetCapabilities = "http://www.onvif.org/ver10/device/wsdl/GetCapabilities";
	
	//
	String actionGetScopes = "http://www.onvif.org/ver10/device/wsdl/GetScopes";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //String result = callWebService(deviceURL, actionGetSystemDateAndTime, envelopeGetSystemDateAndTime);
        //String result = callWebService(imagingURL, actionGetImagingSettings, envelopeGetImagingSettings);
        //String result = callWebService(deviceURL, actionGetCapabilities, envelopeGetCapabilities);
        
        //
        String result = callWebService(deviceURL, actionGetCapabilities, prepareEnvelopeGetCapabilities());
        //String result = callWebService(deviceURL, actionGetScopes, prepareEnvelopeGetScopes());
        
        if(result != null){
			TextView t = (TextView)this.findViewById(R.id.resultbox);
			t.setText("SOAP response:\n\n" + result);
			
			Log.i(LOG_TAG, "SOAP response:" + result);
		}
    }
    
    
    
    //[GetCapabilities]
    private String prepareEnvelopeGetCapabilities(){
    	// recover nonce from hexString
    	String hexString = "68C7090569FFAF65C5976B163AC0602ED30431DB32B5D4ED7CF862D5944C0AB0";
		byte[] nonce = new byte[32];
		for (int i = 0;i < nonce.length;i++)
			nonce[i] += (byte)Integer.parseInt(hexString.substring(2*i, 2*i+2), 16);
    	
		//
		TokenGenerator gen = new TokenGenerator(nonce, "4321");
		
		//
		String envelopeString = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
				"<s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
				"<UsernameToken><Username>admin</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" +
				"%s</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" +
				"%s</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
				"%s</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetCapabilities xmlns=\"http://www.onvif.org/ver10/device/wsdl\"><Category>All</Category>" +
				"</GetCapabilities></s:Body></s:Envelope>";
		
		//
		return String.format(envelopeString, gen.getHashedFinal(), gen.getNonceBase64(), 
				gen.getCreatedText());
		
    }
    
    //[GetScopes]
    private String prepareEnvelopeGetScopes(){
    	// recover nonce from hexString
    	String hexString = "68C7090569FFAF65C5976B163AC0602ED30431DB32B5D4ED7CF862D5944C0AB0";
		byte[] nonce = new byte[32];
		for (int i = 0;i < nonce.length;i++)
			nonce[i] += (byte)Integer.parseInt(hexString.substring(2*i, 2*i+2), 16);
    	
		//
		TokenGenerator gen = new TokenGenerator(nonce, "4321");
		
		//
		String envelopeString = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
				"<s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
				"<UsernameToken><Username>admin</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" +
				"%s</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" +
				"%s</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
				"%s</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><GetScopes xmlns=\"http://www.onvif.org/ver10/device/wsdl\">" +
				"</GetScopes></s:Body></s:Envelope>";
		
		//
		return String.format(envelopeString, gen.getHashedFinal(), gen.getNonceBase64(), 
				gen.getCreatedText());
		
    }
    
    //testKSoap
    private void testKSoap(){
    	String SOAP_ACTION = "http://endpoint.helloservice/sayHello";
    	String NAMESPACE = "http://endpoint.helloservice/";
    	String METHOD_NAME = "sayHello";
    	String URL = "http://172.168.155.39:8080/helloservice/hello?wsdl";
    	
    	//Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);        
        PropertyInfo pi = new PropertyInfo();
        pi.setName("arg0");
        pi.setValue("Hiep ga");
        pi.setType(PropertyInfo.STRING_CLASS);
        request.addProperty(pi);
        
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
				
		if(result != null){
			TextView t = (TextView)this.findViewById(R.id.resultbox);
			t.setText("SOAP response:\n\n" + result.getProperty(0).toString());
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
			Log.v("exception", e.toString());
		}

		// close the connection
		httpClient.getConnectionManager().shutdown();
		return responseString;
	}
}