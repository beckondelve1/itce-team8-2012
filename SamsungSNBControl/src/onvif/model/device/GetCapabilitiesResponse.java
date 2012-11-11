package onvif.model.device;

import org.w3c.dom.Document;

import test.XMLParser;

public class GetCapabilitiesResponse {

	
	//Ctor
	public GetCapabilitiesResponse(){
		
	}
	
	public GetCapabilitiesResponse(String response){
		
		
		//
		XMLParser parser = new XMLParser();
		Document doc = parser.getDomElement(response);
		//
		
	}
}
