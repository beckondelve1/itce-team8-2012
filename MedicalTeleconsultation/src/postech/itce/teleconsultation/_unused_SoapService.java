package postech.itce.teleconsultation;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.*;
import java.net.*;
import android.util.*;

public class _unused_SoapService {
	private String ServerUrl;
	private String SoapAction;
	private String NameSpace;
	private String MethodName;
	
	private int SoapVersion = SoapEnvelope.VER12;
	private boolean DotNet = false;
	private String Response;
	
	public _unused_SoapService(String serverurl, String soapaction, String namespace, String methodname) {
		this.ServerUrl = serverurl;
		this.SoapAction = soapaction;
		this.NameSpace = namespace;
		this.MethodName = methodname;
	}
		
	private boolean checkSoapMemberVariable() {
		if(this.ServerUrl == null || this.SoapAction == null || this.NameSpace == null || this.MethodName == null)
			return false;
		return true;
	}
	
	private boolean checkHttps() {
		if(this.ServerUrl != null && this.ServerUrl.toLowerCase().startsWith("https"))
			return true;
		return false;
	}
	
	public String getServerUrl() {
		return this.ServerUrl;
	}
	
	public boolean setServerUrl(String serverurl) {
		if(serverurl == null)
			return false;
		
		this.ServerUrl = serverurl;
		return true;
	}
	
	public String getSoapAction() {
		return this.SoapAction;
	}
	
	public boolean setSoapAction(String soapaction) {
		if(soapaction == null)
			return false;
		
		this.SoapAction = soapaction;
		return true;
	}
	
	public String getNameSpace() {
		return this.NameSpace;
	}
	
	public boolean setNameSpace(String namespace) {
		if(namespace == null)
			return false;
		
		this.NameSpace = namespace;
		return true;
	}
	
	public String getMethodName() {
		return this.MethodName;
	}
	
	public boolean setMethodName(String methodname) {
		if(methodname == null)
			return false;
		
		this.MethodName = methodname;
		return true;
	}
	
	public int getSoapVersion() {
		return this.SoapVersion;
	}
	
	public void setSoapVersion(int soapversion) {
		this.SoapVersion = soapversion;
	}
	
	public boolean getDotNet() {
		return this.DotNet;
	}
	
	public void setDotNet(boolean dotnet) {
		this.DotNet = dotnet;
	}
	
	public String getResponse() {
		return this.Response;
	}
	
	public boolean request() {
		return request(null);
	}
	
	public boolean request(Object soaprequest) {
		if(!checkSoapMemberVariable())
			return false;

		ServiceRequest req = new ServiceRequest(
				this.ServerUrl, 
				this.SoapAction, 
				this.NameSpace, 
				this.MethodName,
				this.SoapVersion,
				this.DotNet,
				(SoapObject)soaprequest);
		Thread thread = new Thread(req);
		
		try {
			thread.start();
			thread.join();
		} catch(Exception e) {
			this.Response = null;
			e.printStackTrace();
			return false;
		}
		
		this.Response = req.getResponse();
		if(this.Response == null)
			return false;
		return true;
	}
		
	private class ServiceRequest implements Runnable {
		private String ServerUrl;
		private String SoapAction;
		private String MethodName;
		private String NameSpace;
		private int SoapVersion;
		private boolean DotNet;
		private SoapObject SoapRequest;
		private String Response; 
		
		ServiceRequest(
				String serverurl, 
				String soapaction, 
				String namespace, 
				String methodname, 
				int soapversion, 
				boolean dotnet, 
				SoapObject soaprequest) {
			this.ServerUrl = serverurl;
			this.SoapAction = soapaction;
			this.NameSpace = namespace;
			this.MethodName = methodname;
			this.SoapVersion = soapversion;
			this.DotNet = dotnet;
			this.SoapRequest = soaprequest;
		}
		
		public String getResponse() {
			return this.Response;
		}
		
		public void run() {
			Log.d("SOAP_REQ:NameSpace", this.NameSpace);
			Log.d("SOAP_REQ:MethodName", this.MethodName);
			if(this.SoapRequest == null) {
				this.SoapRequest = new SoapObject(this.NameSpace, this.MethodName);
			}
			Log.d("SOAP_SoapRequest*", this.SoapRequest.toString());
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(this.SoapVersion);
			envelope.dotNet = this.DotNet;
			envelope.implicitTypes = true;
			envelope.setAddAdornments(false);
			//envelope.avoidExceptionForUnknownProperty = true;
			envelope.setOutputSoapObject(this.SoapRequest);
			
			Log.d("SOAP_REQ:ServerUrl", this.ServerUrl);
			Log.d("SOAP_REQ:SoapAction", this.SoapAction);
			HttpTransportSE httptransport = new HttpTransportSE(this.ServerUrl);
			httptransport.debug = true;
			
			try {
				httptransport.call(this.SoapAction, envelope);
				Log.d("SOAP_REQ:Dump*", httptransport.requestDump);
				this.Response = envelope.getResponse().toString();
				if(this.Response != null)
					Log.i("SOAP_RSP(ksoap2)", this.Response);
			} catch(Exception e) {
				this.Response = null;
				e.printStackTrace();
			}
			
			// testing...
			if(this.Response == null && httptransport.requestDump != null) {
				this.Response = transportHttpMessage(httptransport.requestDump);
				if(this.Response != null)
					Log.i("SOAP_RSP(javanet)", this.Response);
			}
		}
		
		private String transportHttpMessage(String message) {
			URL serverurl;
			HttpURLConnection connection;
			
			try {
				serverurl = new URL(this.ServerUrl);
				connection = (HttpURLConnection)serverurl.openConnection();
				connection.setDefaultUseCaches(false);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
			OutputStreamWriter outstream;
			try {
				outstream = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
				outstream.write(message);
				outstream.flush();
				/*
				PrintWriter writer = new PrintWriter(outstream);
				writer.write(message);
				writer.flush();
				*/
				outstream.close();
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
			StringBuilder strbuilder = new StringBuilder();
			try {
				InputStreamReader instream = new InputStreamReader(connection.getInputStream(), "UTF-8");
				BufferedReader reader = new BufferedReader(instream);
				
				String str;
				while ((str = reader.readLine()) != null)
					strbuilder.append(str + "\r\n");
				
				instream.close();
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}

			connection.disconnect();
			return strbuilder.toString();
		}
	}
}
