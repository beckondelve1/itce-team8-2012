package postech.itce.team8.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import postech.itce.team8.App;

public class HttpPostRequester {

	public static String postData(String url, Map<String, String> map) {
	    // Create a new HttpClient and Post Header
	    HttpClient httpClient = new DefaultHttpClient();
	    HttpPost httpPost = new HttpPost(url);

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(map.size());
	        for (String name:map.keySet())
	        	nameValuePairs.add(new BasicNameValuePair(name, map.get(name)));
	        
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpClient.execute(httpPost);
	        return inputStreamToString(response.getEntity().getContent()).toString();
	        
	    } catch (ClientProtocolException e) {
	    	return null;
	    } catch (IOException e) {
	    	return null;
	    }
	}
	
	public static String postSecureData(String url, Map<String, String> map, App app) {
		final SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 8080));
		schemeRegistry.register(new Scheme("https", app.createAdditionalCertsSSLSocketFactory(), 8443));

		// and then however you create your connection manager, I use ThreadSafeClientConnManager
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		
	    // Create a new HttpClient and Post Header
        final ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params,schemeRegistry);
		
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
		
		//
	    HttpPost httpPost = new HttpPost(url);

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(map.size());
	        for (String name:map.keySet())
	        	nameValuePairs.add(new BasicNameValuePair(name, map.get(name)));
	        
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        		
	        System.out.println("request URI = " + httpPost.getURI());

	        // Execute HTTP Post Request
	        HttpResponse response = httpClient.execute(httpPost);
	        return inputStreamToString(response.getEntity().getContent()).toString();
	        
	    } catch (ClientProtocolException e) {
	    	return null;
	    } catch (IOException e) {
	    	return null;
	    }
	}
	
	
	
	private static StringBuilder inputStreamToString(InputStream is) throws IOException{
	    String line = "";
	    StringBuilder total = new StringBuilder();
	    
	    // Wrap a BufferedReader around the InputStream
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));

	    // Read response until the end
	    while ((line = rd.readLine()) != null) { 
	        total.append(line); 
	    }
	    
	    // Return full string
	    return total;
	}

}
