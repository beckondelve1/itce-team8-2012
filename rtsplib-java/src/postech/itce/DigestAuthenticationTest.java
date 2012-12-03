package postech.itce;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class DigestAuthenticationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String userName = "admin";
		String password = "4321";
		String realm = "iPOLiS";
		String method = "OPTIONS";
		String nonce = "0000000000000000000000003A22B768";
		String uri = "rtsp://119.202.84.41:554/onvif/profile1/media.smp";
		String response = "396dfe41721897f2fe684891e37e5820";

		//
		try {
			String ha1 = AeSimpleMD5.MD5(userName + ":" + realm + ":" + password);
			String ha2 = AeSimpleMD5.MD5(method + ":" + uri);
			String res = AeSimpleMD5.MD5(ha1 + ":" + nonce + ":" + ha2);
			
			System.out.println(ha1);
			System.out.println(ha2);
			System.out.println(res);
			
			System.out.println(res.equals(response));	//True ^^
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
