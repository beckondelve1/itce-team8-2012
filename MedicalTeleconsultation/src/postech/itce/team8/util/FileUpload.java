package postech.itce.team8.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.videolan.vlc.VLCApplication;


import android.util.Log;

public class FileUpload {
	private static final String LOG_TAG = "FileUpload";
	
	//
	public static String doFileUpload(String selectedPath, String fileName, String urlString, 
			String userName) {
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		DataInputStream inStream = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		String responseFromServer = "";
		
		//String urlString = "http://119.202.84.76/upload_audio_test/upload_audio.php";
		//String urlString = "http://141.223.83.139/upload_file_test/upload_file.php";
		//String urlString = "http://141.223.83.139:8080/upload";		//CherryPy (python)
		//String urlString = "http://141.223.83.139:8080/itce600_server/UploadServlet";	//Tomcat (java)
		
		try {
			// ------------------ CLIENT REQUEST
			FileInputStream fileInputStream = new FileInputStream(new File(selectedPath));
			// open a URL connection to the Servlet
			URL url = new URL(urlString);
			// Open a HTTP connection to the URL
			conn = (HttpURLConnection) url.openConnection();
			// Allow Inputs
			conn.setDoInput(true);
			// Allow Outputs
			conn.setDoOutput(true);
			// Don't use a cached copy.
			conn.setUseCaches(false);
			
			// Use a post method.
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			dos = new DataOutputStream(conn.getOutputStream());
			
			//1.
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"fileUpload\"; filename=\"" + fileName + "\"" + lineEnd);
			dos.writeBytes("Content-Type: audio/wav" + lineEnd);
			dos.writeBytes(lineEnd);
			
			// create a buffer of maximum size
			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];
			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			
			//2. 
			dos.writeBytes("Content-Disposition: form-data; name=\"userName\"" + lineEnd);
			dos.writeBytes(lineEnd);
			
			dos.writeBytes(userName + lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			
			// close streams
			Log.e(LOG_TAG, "File is written");
			fileInputStream.close();
			dos.flush();
			dos.close();
		} catch (MalformedURLException ex) {
			Log.e(LOG_TAG, "error: " + ex.getMessage(), ex);
		} catch (IOException ioe) {
			Log.e(LOG_TAG, "error: " + ioe.getMessage(), ioe);
		}
		// ------------------ read the SERVER RESPONSE
		try {
			inStream = new DataInputStream(conn.getInputStream());
			String str;
			String response = "";
			while ((str = inStream.readLine()) != null) {
				Log.e(LOG_TAG, "Server Response " + str);
				response += str;
			}
			inStream.close();
			
			return response;

		} catch (IOException ioex) {
			Log.e(LOG_TAG, "error: " + ioex.getMessage(), ioex);
			
			return "ERROR";
		}
	}
	
	
	
	
	//doSecureFileUpload (12-11-14)
	public static String doSecureFileUpload(String selectedPath, String fileName, String urlString, 
			String userName, VLCApplication app) {
		
		final SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 8080));
		schemeRegistry.register(new Scheme("https", app.createAdditionalCertsSSLSocketFactory(), 8443));

		// and then however you create your connection manager, I use ThreadSafeClientConnManager
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

        //
		final ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params,schemeRegistry);
		
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
        
		
		//
		HttpPost httpPost = new HttpPost(urlString);
	    File file = new File(selectedPath);

	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file, "audio/wav");
	    mpEntity.addPart("fileUpload", cbFile);
	    

	    try{
	    	mpEntity.addPart("filename", new StringBody(fileName));
	    	mpEntity.addPart("userName", new StringBody(userName));
	    	
		    httpPost.setEntity(mpEntity);
		    
		    //System.out.println("executing request " + httpPost.getRequestLine());
		    
		    //
		    HttpResponse response = httpClient.execute(httpPost);
		    HttpEntity resEntity = response.getEntity();
	
		    //System.out.println(response.getStatusLine());
		    
		    if (resEntity != null) {
		    	//System.out.println(EntityUtils.toString(resEntity));
		    	
		    }
		    /*
		    if (resEntity != null) {
		    	resEntity.consumeContent();
		    }
		    */
		    String responseStr = EntityUtils.toString(resEntity);
		    
		    httpClient.getConnectionManager().shutdown();
		    return responseStr;
		    
	    }catch(Exception e){
	    	Log.e("Debug", "error: " + e.getMessage(), e);
	    	
	    	httpClient.getConnectionManager().shutdown();
	    	return null;
	    }

	    
	    
	}
	
}
