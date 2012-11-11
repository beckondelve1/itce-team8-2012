package onvif.model.device;

public class GetSystemDateAndTimeRequest extends OnvifRequest{
	
	
	//Ctor
	public GetSystemDateAndTimeRequest(){
		
	}
	
	public GetSystemDateAndTimeRequest(String url){
		this.url = url;
		this.envelope = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
				"<s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
				"<GetSystemDateAndTime xmlns=\"http://www.onvif.org/ver10/device/wsdl\"/></s:Body></s:Envelope>";
		this.action = "http://www.onvif.org/ver10/device/wsdl/GetSystemDateAndTime";
		//
		
	}

	@Override
	public void prepareSOAPRequest(int serverTimeOffset) {
		//Do nothing
	}
	
	
	
}
