package postech.itce;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//Java Base64 Encoder / Java Base64 Decoder Example

public class Base64Test {

	public static void main(String[] args) {
		BASE64Decoder decoder = new BASE64Decoder();
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			//String encodedBytes = encoder.encodeBuffer("JavaTips.net".getBytes());
			//System.out.println("encodedBytes " + encodedBytes);
			
			String encodedBytes = "396dfe41721897f2fe684891e37e5820";
			byte[] decodedBytes = decoder.decodeBuffer(encodedBytes);
			System.out.println("decodedBytes " + new String(decodedBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
