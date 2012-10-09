package test;

import org.kobjects.base64.Base64;

import com.hammingweight.kiss.crypto.IHashAlgorithm;
import com.hammingweight.kiss.crypto.Sha1Hash;
import com.hammingweight.kiss.crypto.X931Generator;

public class Test {
	
	private static byte[] testKey = new byte[] { (byte) 0x2b, (byte) 0x7e, (byte) 0x15,
		(byte) 0x16, (byte) 0x28, (byte) 0xae, (byte) 0xd2,
		(byte) 0xa6, (byte) 0xab, (byte) 0xf7, (byte) 0x15,
		(byte) 0x88, (byte) 0x09, (byte) 0xcf, (byte) 0x4f, (byte) 0x3c };
	

	public static void main(String[] args) {
		
//		//1 - decode and get nonce (in hex)
//		byte[] nonce = Base64.decode("8jiuCl3XWU265VVkxPke4h4AAAAAAAAA33LhuxEAAAA=");
//		System.out.println(nonce.length);
//		
//		String hexString = "";
//		for (int i = 0;i < nonce.length;i++)
//			hexString += String.format("%02X", nonce[i]);
//		
//		System.out.println(hexString);
//		
//		//2 - regenerate Token 
//		String createdText = "2012-05-04T09:12:22.001Z";
//		String password = "4321";
//		
//		IHashAlgorithm hashAlg = new Sha1Hash();
//		
//		hashAlg.update(nonce);
//		hashAlg.update(createdText.getBytes());
//		byte[] hash = hashAlg.doFinal(password.getBytes());
//		
//		System.out.println(Base64.encode(hash));
//		
//		//3 - recover nonce from hexString
//		String hexString2 = "F238AE0A5DD7594DBAE55564C4F91EE21E00000000000000DF72E1BB11000000";
//		byte[] nonce2 = new byte[32];
//		for (int i = 0;i < nonce2.length;i++)
//			nonce2[i] += (byte)Integer.parseInt(hexString2.substring(2*i, 2*i+2), 16);
//		
//		System.out.println(Base64.encode(nonce2));
		
		
		//4 - generate nonce
		X931Generator rng = new X931Generator(testKey, testKey);
		int nonceLength = 32;
		byte[] nonce3 = new byte[nonceLength];
		rng.generate(nonce3);
		
		String hexString3 = "";
		for (int i = 0;i < nonce3.length;i++)
			hexString3 += String.format("%02X", nonce3[i]);
		System.out.println(hexString3);
		
	}
	
}
