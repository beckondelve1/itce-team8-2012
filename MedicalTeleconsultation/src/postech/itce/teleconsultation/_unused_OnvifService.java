package postech.itce.teleconsultation;

public class _unused_OnvifService {
	// HTTP/HTTPS Protocol & ONVIF Servie URI
	public static final String HTTP_PROTOCOL = "http://";
	public static final String HTTPS_PROTOCOL = "https://";
	public static final String ONVIF_URI = "/onvif/services";
	
	// ONVIF_NAMESPACE
	public static final String ONVIF_SCHEMA_VER10_NAMESPACE = "http://www.onvif.org/ver10/schema/";
	public static final String ONVIF_DEVICE_VER10_NAMESPACE = "http://www.onvif.org/ver10/device/wsdl";
	public static final String ONVIF_MEDIA_VER10_NAMESPACE = "http://www.onvif.org/ver10/media/wsdl";
	
	// ONVIF_METHOD
	public static final String ONVIF_DEVICE_VER10_METHOD_GETDEVICEINFORMATION = "GetDeviceInformation";
	public static final String ONVIF_MEDIA_VER10_METHOD_GETSTREAMURI = "GetStreamUri";
	
	// ONVIF_SOAPACTION
	public static final String ONVIF_DEVICE_VER10_SOAPACTION = "http://www.onvif.org/ver10/device/wsdl/GetDeviceInformation";
	public static final String ONVIF_MEDIA_VER10_SOAPACTION = "http://www.onvif.org/ver10/media/wsdl/GetStreamUri";
	
	private String ServerIp;
	private String ServerUrl;
	private boolean Https = false;
	
	public _unused_OnvifService() {}
	
	public _unused_OnvifService(String serverip) {
		setServerIp(serverip);
	}
	
	public _unused_OnvifService(String serverip, boolean https) {
		setServerIp(serverip, https);
	}
	
	public boolean getHttps() {
		return this.Https;
	}
	
	public String getServerUrl() {
		return this.ServerUrl;
	}
	
	public String getServerIp() {
		return this.ServerIp;
	}
	
	public boolean setServerIp(String serverip) {
		return setServerIp(serverip, false);
	}
	
	public boolean setServerIp(String serverip, boolean https) {
		if(serverip == null || serverip == "") {
			this.ServerIp = null;
			this.ServerUrl = null;
			this.Https = false;
			return false;
		}
		
		this.ServerIp = serverip;
		if(https) {
			this.ServerUrl = _unused_OnvifService.HTTPS_PROTOCOL;
			this.Https = true;
		} else {
			this.ServerUrl = _unused_OnvifService.HTTP_PROTOCOL;
			this.Https = false;
		}	
		this.ServerUrl += serverip + _unused_OnvifService.ONVIF_URI;
		
		return true;
	}
	
	private String request(String serverurl, String soapaction, String namespace, String methodname) {
		return request(serverurl, soapaction, namespace, methodname, null);
	}
	
	private String request(String serverurl, String soapaction, String namespace, String methodname, Object soapobject) {
		if(serverurl == null || soapaction == null || namespace == null || methodname == null)
			return null;
		
		_unused_SoapService soap = new _unused_SoapService(serverurl, soapaction, namespace, methodname);
		soap.request(soapobject);
		return soap.getResponse();
	}
	
	public String getDeviceInformation() {
		return request(
				this.ServerUrl,
				ONVIF_DEVICE_VER10_SOAPACTION,
				ONVIF_DEVICE_VER10_NAMESPACE,
				ONVIF_DEVICE_VER10_METHOD_GETDEVICEINFORMATION
				);
	}
	
	public String getStreamUri(String stream, String protocol, String profiletoken) {
		return request(
				this.ServerUrl,
				ONVIF_MEDIA_VER10_SOAPACTION,
				ONVIF_MEDIA_VER10_NAMESPACE + "/",
				ONVIF_MEDIA_VER10_METHOD_GETSTREAMURI,
				new _unused_OnvifMediaProperty().getStreamUriSoapObject(
						ONVIF_MEDIA_VER10_NAMESPACE,
						ONVIF_MEDIA_VER10_METHOD_GETSTREAMURI,
						stream,
						protocol,
						profiletoken)
				);
	}
}
