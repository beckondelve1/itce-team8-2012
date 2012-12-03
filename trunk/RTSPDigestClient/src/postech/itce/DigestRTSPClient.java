package postech.itce;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import br.com.voicetechnology.rtspclient.MissingHeaderException;
import br.com.voicetechnology.rtspclient.RTSPClient;
import br.com.voicetechnology.rtspclient.concepts.Header;
import br.com.voicetechnology.rtspclient.concepts.Request.Method;
import br.com.voicetechnology.rtspclient.messages.RTSPOptionsRequest;

public class DigestRTSPClient extends RTSPClient {

	@Override
	public void options(String uri, URI endpoint) throws URISyntaxException,
			IOException
	{
		try
		{
			RTSPOptionsRequest message = (RTSPOptionsRequest) messageFactory
					.outgoingRequest(uri, Method.OPTIONS, nextCSeq());
			//HiepNH - commented
//			if(!getTransport().isConnected())
//				message.addHeader(new Header("Connection", "close"));
			
			send(message, endpoint);
		} catch(MissingHeaderException e)
		{
			if(clientListener != null)
				clientListener.generalError(this, e);
		}
	}
	
	
	public void optionsWithDigest(String uri, URI endpoint, String userName, String password,
			String realm, String nonce) throws URISyntaxException,
			IOException {
		try {
			RTSPOptionsRequest message = (RTSPOptionsRequest) messageFactory
					.outgoingRequest(uri, Method.OPTIONS, nextCSeq());
			//HiepNH - commented
//			if (!getTransport().isConnected())
//				message.addHeader(new Header("Connection", "close"));
			//
			
			String res = "";
			try {
				String ha1 = AeSimpleMD5.MD5(userName + ":" + realm + ":" + password);
				String ha2 = AeSimpleMD5.MD5("OPTIONS" + ":" + uri);
				res = AeSimpleMD5.MD5(ha1 + ":" + nonce + ":" + ha2);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			String digestHeader = "Digest username=\"" + userName + "\", realm=\"" + realm + "\", nonce=\"" + nonce + "\", uri=\"" + uri + "\", response=\"" + res + "\"";
			//System.out.println("digestHeader=" + digestHeader);
			
			message.addHeader(new Header("Authorization", digestHeader)); 
			
			//
			send(message, endpoint);
			
		} catch (MissingHeaderException e) {
			if (clientListener != null)
				clientListener.generalError(this, e);
		}
	}
}
