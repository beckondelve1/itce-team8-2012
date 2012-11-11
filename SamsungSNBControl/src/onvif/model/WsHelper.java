package onvif.model;

import java.io.IOException;

import onvif.model.device.OnvifRequest;

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

public class WsHelper {
	
	public static String callWebService(OnvifRequest request) {
		
		final DefaultHttpClient httpClient = new DefaultHttpClient();
		// request parameters
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		HttpConnectionParams.setSoTimeout(params, 15000);
		// set parameter
		HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), false);	//IMPORTANT !!

		// POST the envelope
		HttpPost httppost = new HttpPost(request.getUrl());
		// add headers
		//httppost.setHeader("SOAPAction", soapAction);
		httppost.setHeader("Content-Type", "application/soap+xml; charset=utf-8; action=\"" + request.getAction() + "\"");

		String responseString = "";
		try {

			// the entity holds the request
			HttpEntity entity = new StringEntity(request.getEnvelope());
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
}
