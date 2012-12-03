/*
   Copyright 2010 Voice Technology Ind. e Com. Ltda.
 
   This file is part of RTSPClientLib.

    RTSPClientLib is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    RTSPClientLib is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with RTSPClientLib.  If not, see <http://www.gnu.org/licenses/>.

 */
package postech.itce;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import br.com.voicetechnology.rtspclient.RTSPClient;
import br.com.voicetechnology.rtspclient.concepts.Client;
import br.com.voicetechnology.rtspclient.concepts.ClientListener;
import br.com.voicetechnology.rtspclient.concepts.Request;
import br.com.voicetechnology.rtspclient.concepts.Response;
import br.com.voicetechnology.rtspclient.transport.PlainTCP;

public class DigestOPTIONSTest implements ClientListener {
	private static int reqCount = 0;
	
	public static void main(String[] args) throws Throwable {
		
		new DigestOPTIONSTest();
		
		//extractNonce();
	}

	private static void extractNonce(){
		String response = 
		"RTSP/1.0 401 Unauthorized\n"+
		"CSeq: 0\n" + 
		"Date: Mon Dec  3 06:14:49 2012 GMT\n" + 
		"Expires: Mon Dec  3 06:14:49 2012 GMT\n" + 
		"Cache-Control: must-revalidate\n" + 
		"WWW-Authenticate: Digest realm=\"iPOLiS\", nonce=\"0000000000000000000000007940488A\"";
		
		String nonce = response.substring(response.indexOf("nonce") + 7, response.length() - 1);
		
		System.out.println(nonce);
		
	}
	
	
	private DigestOPTIONSTest() throws Exception {
		DigestRTSPClient client = new DigestRTSPClient();

		client.setTransport(new PlainTCP());
		client.setClientListener(this);
		// HiepNH
		// Unauthorized
		client.options("rtsp://119.202.84.41:554/onvif/profile1/media.smp", new URI("rtsp://119.202.84.41/"));

		
	}
	

	@Override
	public void requestFailed(Client client, Request request, Throwable cause) {
		System.out.println("Request failed \n" + request);
	}

	@Override
	public void response(Client client, Request request, Response response) {
		if (reqCount == 2)
			return;
		
		reqCount++;			
		
		System.out.println("Got response: \n" + response);
		System.out.println("for the request: \n" + request);
		
		if (reqCount == 1){
			//
			String userName = "admin";
			String password = "4321";
			String realm = "iPOLiS";
			String method = "OPTIONS";
			String uri = "rtsp://119.202.84.41:554/onvif/profile1/media.smp";
			//
			String res = response.toString();
			String nonce = res.substring(res.indexOf("nonce") + 7, res.indexOf("nonce") + 39);
			
			try {
				((DigestRTSPClient)client).optionsWithDigest(uri, new URI("rtsp://119.202.84.41/"), 
						userName, password, realm, nonce);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void generalError(Client client, Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void mediaDescriptor(Client client, String descriptor) {
		// TODO Auto-generated method stub

	}
}
