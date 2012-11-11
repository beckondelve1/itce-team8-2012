package onvif.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.kobjects.base64.Base64;

import com.hammingweight.kiss.crypto.IHashAlgorithm;
import com.hammingweight.kiss.crypto.Sha1Hash;

public class TokenGenerator {
	private String nonceBase64;
	private String createdText;
	private String hashedFinal;
	
	
	public TokenGenerator(){
		
	}
	
	public TokenGenerator(byte[] nonce, String password, int serverTimeOffset){
		nonceBase64 = Base64.encode(nonce);
		
		//
		
		Date datetime = new Date();
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(datetime);
		//plus serverTimeOffset
		if (serverTimeOffset != 0){
			cal.add(Calendar.MILLISECOND, serverTimeOffset);
		}
		
		//
		String year = Integer.toString(cal.get(Calendar.YEAR));
		int m = cal.get(Calendar.MONTH) + 1;
		String month = m > 9 ? Integer.toString(m) : "0"
				+ Integer.toString(m);
		int d = cal.get(Calendar.DAY_OF_MONTH);
		String day = d > 9 ? Integer.toString(d) : "0"
				+ Integer.toString(d);
		int h = cal.get(Calendar.HOUR_OF_DAY);
		String hour = h > 9 ? Integer.toString(h) : "0"
				+ Integer.toString(h);
		int mn = cal.get(Calendar.MINUTE);
		String minute = mn > 9 ? Integer.toString(mn) : "0"
				+ Integer.toString(mn);
		int s = cal.get(Calendar.SECOND);
		String seconds = s > 9 ? Integer.toString(s) : "0"
				+ Integer.toString(s);
		int ms = cal.get(Calendar.MILLISECOND);
		String millisecond = Integer.toString(ms);
		while (millisecond.length() < 3) {
			millisecond = "0" + millisecond;
		}
		createdText = year + "-" + month + "-" + day + "T" + hour + ":"
				+ minute + ":" + seconds + "." + millisecond + "Z";
		
		System.out.println("createdText=" + createdText);
		
		//
		IHashAlgorithm hashAlg = new Sha1Hash();
		hashAlg.update(nonce);
		if (createdText != null) {
			hashAlg.update(createdText.getBytes());
		}
		byte[] hash = hashAlg.doFinal(password.getBytes());
		hashedFinal = Base64.encode(hash);
		
	}

	public String getNonceBase64() {
		return nonceBase64;
	}

	public String getCreatedText() {
		return createdText;
	}

	public String getHashedFinal() {
		return hashedFinal;
	}

		
}
