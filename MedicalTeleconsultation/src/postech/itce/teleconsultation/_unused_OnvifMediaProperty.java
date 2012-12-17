package postech.itce.teleconsultation;

import org.ksoap2.serialization.*;

import android.util.*;

public class _unused_OnvifMediaProperty {
	public _unused_OnvifMediaProperty() {}
	
	private SoapObject MediaRequest = null;
	
	public SoapObject getStreamUriSoapObject(
			String namespace, 
			String methodname, 
			String stream, 
			String protocol, 
			String profiletoken) {
		Log.d("MediaProperty:namespace", namespace);
		Log.d("MediaProperty:methodname", methodname);
		Log.d("MediaProperty:stream", stream);
		Log.d("MediaProperty:protocol", protocol);
		Log.d("MediaProperty:profiletoken", profiletoken);
		
		SoapObject transport = new SoapObject(_unused_OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE, "Transport"); // Schema
		transport.addProperty("Protocol", protocol);

		/*
		AttributeInfo streamsetuptype = new AttributeInfo();
		streamsetuptype.setName("type");
		streamsetuptype.setValue("StreamSetup");
		streamsetuptype.setNamespace(OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE);
		*/
		
		SoapObject streamsetup = new SoapObject(namespace, "StreamSetup");
		//streamsetup.addAttribute(streamsetuptype);
		
		//streamsetup.addProperty("Stream", stream);
		
		PropertyInfo pi_stream = new PropertyInfo();
		pi_stream.setName("Stream");
		pi_stream.setValue(stream);
		pi_stream.setType(PropertyInfo.STRING_CLASS);
		pi_stream.setNamespace(_unused_OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE); // Schema
		//pi_stream.setElementType(pi_streamtype);
		
		streamsetup.addProperty(pi_stream);
		streamsetup.addSoapObject(transport);
		
		this.MediaRequest = new SoapObject(namespace, methodname);
		this.MediaRequest.addSoapObject(streamsetup);
		this.MediaRequest.addProperty("ProfileToken", profiletoken);
		
		return this.MediaRequest;
	}
	
	/*
	 * PropertyInfo pi_streamtype = new PropertyInfo();
		pi_streamtype.setName("StreamType");
		
		PropertyInfo pi_stream = new PropertyInfo();
		pi_stream.setName("Stream");
		pi_stream.setValue(stream);
		pi_stream.setType(PropertyInfo.STRING_CLASS);
		pi_stream.setNamespace(OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE); // Schema
		pi_stream.setElementType(pi_streamtype);
	
		PropertyInfo pi_protocol = new PropertyInfo();
		pi_protocol.setName("Protocol");
		pi_protocol.setValue(protocol);
		pi_protocol.setType(PropertyInfo.STRING_CLASS);
		pi_protocol.setNamespace(OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE);
	
		PropertyInfo pi_profiletokentype = new PropertyInfo();
		pi_profiletokentype.setName("ReferenceToken");
		
		PropertyInfo pi_profiletoken = new PropertyInfo();
		pi_profiletoken.setName("ProfileToken");
		pi_profiletoken.setValue(profiletoken);
		pi_profiletoken.setType(PropertyInfo.STRING_CLASS);
		pi_profiletoken.setNamespace(namespace);
		pi_profiletoken.setElementType(pi_profiletokentype);
		
		SoapObject transport = new SoapObject(OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE, "Transport"); // Schema
		transport.addProperty(pi_protocol);
	
		SoapObject streamsetup = new SoapObject(namespace, "StreamSetup");
		streamsetup.addProperty(pi_stream);
		streamsetup.addSoapObject(transport);
	
		this.MediaRequest = new SoapObject(namespace, methodname);
		this.MediaRequest.addSoapObject(streamsetup);
		this.MediaRequest.addProperty(pi_profiletoken);
		this.MediaRequest.addAttribute("xmlns:tt", OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE);
	
		return this.MediaRequest;
	 */
	
	/*
	 * PropertyInfo pi_stream = new PropertyInfo();
		pi_stream.setName("Stream");
		pi_stream.setValue(stream);
		pi_stream.setType(PropertyInfo.STRING_CLASS);
		pi_stream.setNamespace(OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE); // Schema
		
		PropertyInfo pi_protocol = new PropertyInfo();
		pi_protocol.setName("Protocol");
		pi_protocol.setValue(protocol);
		pi_protocol.setType(PropertyInfo.STRING_CLASS);
		pi_protocol.setNamespace(OnvifService.ONVIF_MEDIA_VER10_NAMESPACE);
		
		PropertyInfo pi_profiletoken = new PropertyInfo();
		pi_profiletoken.setName("ProfileToken");
		pi_profiletoken.setValue(profiletoken);
		pi_profiletoken.setType(PropertyInfo.STRING_CLASS);
		pi_profiletoken.setNamespace(OnvifService.ONVIF_MEDIA_VER10_NAMESPACE);
		
		SoapObject transport = new SoapObject(OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE, "Transport"); // Schema
		transport.addProperty(pi_protocol);
		
		SoapObject streamsetup = new SoapObject(OnvifService.ONVIF_MEDIA_VER10_NAMESPACE, "StreamSetup");
		streamsetup.addProperty(pi_stream);
		streamsetup.addSoapObject(transport);
		
		this.MediaRequest = new SoapObject(namespace, methodname);
		this.MediaRequest.addSoapObject(streamsetup);
		this.MediaRequest.addProperty(pi_profiletoken);
		
		return this.MediaRequest;
	 */
	
	/*
	 * 
		SoapObject transport = new SoapObject(OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE, "Transport"); // Schema
		transport.addProperty("Protocol", "0");

		SoapObject streamsetup = new SoapObject(namespace, "StreamSetup");
		streamsetup.addProperty("Stream", "0");
		streamsetup.addSoapObject(transport);
		
		this.MediaRequest = new SoapObject(namespace, methodname);
		this.MediaRequest.addSoapObject(streamsetup);
		this.MediaRequest.addProperty("ProfileToken", profiletoken);
		
		return this.MediaRequest;

	 */
	
	
	/*
	public SoapObject getStreamUriSoapObject(
			String namespace, 
			String methodname, 
			String stream, 
			String protocol, 
			String profiletoken) {
		PropertyInfo pi_stream = new PropertyInfo();
		pi_stream.setName("Stream");
		pi_stream.setValue(stream);
		pi_stream.setType(String.class);
		pi_stream.setNamespace(OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE);
		
		SoapObject transport = new SoapObject(OnvifService.ONVIF_MEDIA_VER10_NAMESPACE, "Transport");
		transport.addAttribute("xmlns", OnvifService.ONVIF_SCHEMA_VER10_NAMESPACE);
		transport.addProperty("Protocol", protocol);
		
		SoapObject streamsetup = new SoapObject(OnvifService.ONVIF_MEDIA_VER10_NAMESPACE, "StreamSetup");
		streamsetup.addProperty(pi_stream);
		streamsetup.addSoapObject(transport);
		
		Log.d("MediaProperty:NameSpace", namespace);
		Log.d("MediaProperty:MethodName", methodname);
		
		SoapObject request = new SoapObject(namespace, methodname);
		request.addSoapObject(streamsetup);
		request.addProperty("ProfileToken", profiletoken);
		
		return request;
	}
	*/
}
