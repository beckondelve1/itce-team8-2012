package onvif.model.device;

import java.util.List;

import test.TokenGenerator;

public abstract class OnvifRequest {
	protected String url;
	protected String envelope;
	protected String header;
	protected String body;
	protected String action;
	protected String username;
	protected String password;
	
	//
	public OnvifRequest(){
		
	}
	
	public OnvifRequest(String url, String username, String password){
		//
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	protected void prepareHeader(int serverTimeOffset){
		// recover nonce from hexString
    	String hexString = "68C7090569FFAF65C5976B163AC0602ED30431DB32B5D4ED7CF862D5944C0AB0";
		byte[] nonce = new byte[32];
		for (int i = 0;i < nonce.length;i++)
			nonce[i] += (byte)Integer.parseInt(hexString.substring(2*i, 2*i+2), 16);
    	
		//
		TokenGenerator gen = new TokenGenerator(nonce, password, serverTimeOffset);
		
		//
		this.header = String.format(this.header, this.username, 
				gen.getHashedFinal(), gen.getNonceBase64(), gen.getCreatedText());
	}
	
	protected void prepareBody(List<String> params){
		if (params == null)
			return;
		for (String param:params){
			this.body = this.body.replaceFirst("%s", param);
		}
	}
	
		
	//
	public void prepareSOAPRequest(int serverTimeOffset, List<String> params){
		prepareHeader(serverTimeOffset);
		prepareBody(params);
		
		this.envelope = this.header + this.body;
	}
	
	//
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEnvelope() {
		return envelope;
	}
	public void setEnvelope(String envelope) {
		this.envelope = envelope;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//
	
}
