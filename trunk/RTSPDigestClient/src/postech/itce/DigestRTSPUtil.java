package postech.itce;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import br.com.voicetechnology.rtspclient.concepts.Client;
import br.com.voicetechnology.rtspclient.concepts.ClientListener;
import br.com.voicetechnology.rtspclient.concepts.Request;
import br.com.voicetechnology.rtspclient.concepts.Response;
import br.com.voicetechnology.rtspclient.transport.PlainTCP;

public class DigestRTSPUtil {
	private static String nonce = "";
	private static boolean firstCallFinished = false;
	private static boolean secondCallFinished = false; 
	
	public static void sendAuthenticationMessages(String rtspURI, String host) throws Exception{
		DigestRTSPClient client = new DigestRTSPClient();

		client.setTransport(new PlainTCP());
		client.setClientListener(new ClientListener() {
			
			@Override
			public void response(Client client, Request request, Response response) {
				//
				if (response.getStatusCode() == 401){	//Unauthorized (for first request)
					String res = response.toString();
					nonce = res.substring(res.indexOf("nonce") + 7, res.indexOf("nonce") + 39);
					firstCallFinished = true;
				}
				
				if (response.getStatusCode() == 200)	//Authorized (for second request)
					secondCallFinished = true;
				
			}
			
			@Override
			public void requestFailed(Client client, Request request, Throwable cause) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaDescriptor(Client client, String descriptor) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void generalError(Client client, Throwable error) {
				// TODO Auto-generated method stub
				
			}
		});
		// HiepNH
		// Unauthorized
		client.options(rtspURI, new URI(host));

		//waiting...
		while (!firstCallFinished);
		
		//
		String userName = "admin";
		String password = "4321";
		String realm = "iPOLiS";
		
		client.optionsWithDigest(rtspURI, new URI(host), userName, password, realm, nonce);
		
		//waiting...
		while (!secondCallFinished);
		
		//reset
		firstCallFinished = false;
		secondCallFinished = false;
	}
}
