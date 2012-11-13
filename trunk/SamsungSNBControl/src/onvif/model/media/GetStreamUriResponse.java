package onvif.model.media;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import test.XMLParser;

public class GetStreamUriResponse {

	private String uri;
	private boolean invalidAfterConnect;
	private boolean invalidAfterReboot;
	private String timeout;
	
	//Ctor
	public GetStreamUriResponse(){
		
	}
	
	public GetStreamUriResponse(String response){
		
		XMLParser parser = new XMLParser();
		Document doc = parser.getDomElement(response);
		//
		NodeList nl = doc.getElementsByTagName("trt:MediaUri");
		//
		Element e = (Element)nl.item(0);
		uri = parser.getValue(e, "tt:Uri");
		invalidAfterConnect = Boolean.parseBoolean(parser.getValue(e, "tt:InvalidAfterConnect"));
		invalidAfterReboot = Boolean.parseBoolean(parser.getValue(e, "tt:InvalidAfterReboot"));
		timeout = parser.getValue(e, "tt:Timeout");
	}

	//
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public boolean isInvalidAfterConnect() {
		return invalidAfterConnect;
	}
	public void setInvalidAfterConnect(boolean invalidAfterConnect) {
		this.invalidAfterConnect = invalidAfterConnect;
	}
	public boolean isInvalidAfterReboot() {
		return invalidAfterReboot;
	}
	public void setInvalidAfterReboot(boolean invalidAfterReboot) {
		this.invalidAfterReboot = invalidAfterReboot;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	
}
