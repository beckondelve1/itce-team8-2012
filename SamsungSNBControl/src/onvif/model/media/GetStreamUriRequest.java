package onvif.model.media;

import onvif.model.device.OnvifRequest;

public class GetStreamUriRequest extends OnvifRequest {

	//Ctor
	public GetStreamUriRequest(){
		
	}
	
	public GetStreamUriRequest(String url, String username, String password){
		super(url, username, password);
		//
		this.header = "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">" +
				"<s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
				"<UsernameToken><Username>" +
				"%s</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" +
				"%s</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" +
				"%s</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
				"%s</Created></UsernameToken></Security></s:Header>";
		
		this.body =	"<s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
				"<GetStreamUri xmlns=\"http://www.onvif.org/ver10/media/wsdl\">" +
				"<StreamSetup><Stream xmlns=\"http://www.onvif.org/ver10/schema\">" +
				"%s</Stream>" +
				"<Transport xmlns=\"http://www.onvif.org/ver10/schema\"><Protocol>" +
				"%s</Protocol>" +
				"</Transport></StreamSetup><ProfileToken>" +
				"%s</ProfileToken>" +
				"</GetStreamUri></s:Body></s:Envelope>";
		
		this.action = "http://www.onvif.org/ver10/media/wsdl/GetStreamUri";
		
		//
	}
}
