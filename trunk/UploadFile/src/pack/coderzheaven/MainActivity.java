package pack.coderzheaven;

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
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import stackoverflow.util.AdditionalKeyStoresSSLSocketFactory;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class MainActivity extends Activity {

	private final String SECURE_UPLOAD_URL = "https://141.223.83.139:8443/itce600_server/UploadServlet";
	
	private static final int SELECT_AUDIO = 2;
	String selectedPath = "";
	String fileName = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new UploadFileTask().execute();
		
		//openGalleryAudio();
	}

	public void openGalleryAudio() {

		Intent intent = new Intent();
		intent.setType("audio/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Audio "),
				SELECT_AUDIO);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

			if (requestCode == SELECT_AUDIO) {
				System.out.println("SELECT_AUDIO");
				Uri selectedAudioUri = data.getData();
				selectedPath = getPath(selectedAudioUri);
				System.out.println("SELECT_AUDIO Path : " + selectedPath);
				fileName = selectedPath.substring(selectedPath.lastIndexOf("/")+1);
				System.out.println("SELECT_AUDIO Name : " + fileName);
				
				//doFileUpload();
				new UploadFileTask().execute();
			}

		}
	}

	public String getPath(Uri uri) {
		return uri.getPath();
	}

	private void doFileUpload() {
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
		String urlString = "http://141.223.83.139/upload_file_test/upload_file.php";	//XAMPP (php)
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
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ fileName + "\"" + lineEnd);
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
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			// close streams
			Log.e("Debug", "File is written");
			fileInputStream.close();
			dos.flush();
			dos.close();
		} catch (MalformedURLException ex) {
			Log.e("Debug", "error: " + ex.getMessage(), ex);
		} catch (IOException ioe) {
			Log.e("Debug", "error: " + ioe.getMessage(), ioe);
		}
		// ------------------ read the SERVER RESPONSE
		try {
			inStream = new DataInputStream(conn.getInputStream());
			String str;

			while ((str = inStream.readLine()) != null) {
				Log.e("Debug", "Server Response " + str);
			}
			inStream.close();

		} catch (IOException ioex) {
			Log.e("Debug", "error: " + ioex.getMessage(), ioex);
		}
	}
	
	
	//createAdditionalCertsSSLSocketFactory
	protected org.apache.http.conn.ssl.SSLSocketFactory createAdditionalCertsSSLSocketFactory() {
	    try {
	        final KeyStore ks = KeyStore.getInstance("BKS");

	        // the bks file we generated above
	        final InputStream in = getApplicationContext().getResources().openRawResource( R.raw.hiepnh_tomcat);  
	        try {
	            // don't forget to put the password used above in strings.xml/mystore_password
	            //ks.load(in, context.getString( R.string.mystore_password ).toCharArray());
	        	ks.load(in, "asdfgh".toCharArray());
	        } finally {
	            in.close();
	        }

	        return new AdditionalKeyStoresSSLSocketFactory(ks);

	    } catch( Exception e ) {
	        throw new RuntimeException(e);
	    }
	}
	
	
	//doSecureFileUpload (12-11-12)
	private void doSecureFileUpload() {
		final SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 8080));
		schemeRegistry.register(new Scheme("https", createAdditionalCertsSSLSocketFactory(), 8443));

		// and then however you create your connection manager, I use ThreadSafeClientConnManager
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

        //
		final ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params,schemeRegistry);
		
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
        
		
		//
		HttpPost httpPost = new HttpPost(SECURE_UPLOAD_URL);
	    File file = new File("/sdcard/FAML_Sr3.wav");

	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file, "audio/wav");
	    mpEntity.addPart("file", cbFile);


	    try{
		    httpPost.setEntity(mpEntity);
		    System.out.println("executing request " + httpPost.getRequestLine());
		    HttpResponse response = httpClient.execute(httpPost);
		    HttpEntity resEntity = response.getEntity();
	
		    System.out.println(response.getStatusLine());
		    if (resEntity != null) {
		    	System.out.println(EntityUtils.toString(resEntity));
		    }
		    if (resEntity != null) {
		    	resEntity.consumeContent();
		    }
	    }catch(Exception e){
	    	Log.e("Debug", "error: " + e.getMessage(), e);
	    }

	    httpClient.getConnectionManager().shutdown();
	    
	}
	
	//
	private class UploadFileTask extends AsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			//doFileUpload();
			doSecureFileUpload();
			
			return null;
		}
		
		
	}
}