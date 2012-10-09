/*
 * UsernameTokenHashed.java
 *  
 * Copyright 2008 C.A. Meijer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hammingweight.kiss.usernametoken;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.kobjects.base64.Base64;
import org.kxml2.kdom.Element;

import com.hammingweight.kiss.NameSpaces;
import com.hammingweight.kiss.crypto.IHashAlgorithm;
import com.hammingweight.kiss.crypto.IRandomNumberGenerator;

public class UsernameTokenHashed extends UsernameToken {

	private void init(String username, byte[] pwd, Date datetime, byte[] nonce,
			IHashAlgorithm hashAlg) {

		Element passwordElement = new Element();
		passwordElement.setName("Password");
		passwordElement.setNamespace(NameSpaces.WSSE_NAMESPACE);
		passwordElement
				.setAttribute(
						NameSpaces.WSSE_NAMESPACE,
						"Type",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#PasswordDigest");
		addChild(Element.ELEMENT, passwordElement);

		if (nonce.length > 0) {
			Element nonceElement = new Element();
			nonceElement.setName("Nonce");
			nonceElement.setNamespace(NameSpaces.WSSE_NAMESPACE);
			nonceElement
					.setAttribute(
							NameSpaces.WSSE_NAMESPACE,
							"EncodingType",
							"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#Base64Binary");
			nonceElement.addChild(Element.TEXT, Base64.encode(nonce));
			addChild(Element.ELEMENT, nonceElement);
		}

		String createdText = null;
		if (datetime != null) {
			Element createdElement = new Element();
			createdElement.setName("Created");
			createdElement
					.setNamespace("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			cal.setTime(datetime);
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
			createdElement.addChild(Element.TEXT, createdText);
			addChild(Element.ELEMENT, createdElement);
		}

		hashAlg.update(nonce);
		if (createdText != null) {
			hashAlg.update(createdText.getBytes());
		}
		
		//HiepNH - change the order: --> createdText + nonce + password
//		if (createdText != null) {
//			hashAlg.update(createdText.getBytes());
//		}
//		hashAlg.update(nonce);
		
		byte[] hash = hashAlg.doFinal(pwd);
		passwordElement.addChild(Element.TEXT, Base64.encode(hash));
	}

	public UsernameTokenHashed(String username, String password, Date datetime,
			IRandomNumberGenerator rng, int nonceLength, IHashAlgorithm hashAlg) {
		this(username, password.getBytes(), datetime, rng, nonceLength, hashAlg);
	}

	public UsernameTokenHashed(String username, byte[] pwd, Date datetime,
			IRandomNumberGenerator rng, int nonceLength, IHashAlgorithm hashAlg) {
		super(username);
		byte[] nonce = new byte[nonceLength];
		rng.generate(nonce);
		this.init(username, pwd, datetime, nonce, hashAlg);
	}

	public UsernameTokenHashed(String username, String password, Date datetime,
			byte[] nonce, IHashAlgorithm hashAlg) {
		this(username, password.getBytes(), datetime, nonce, hashAlg);
	}

	public UsernameTokenHashed(String username, byte[] pwd, Date datetime,
			byte[] nonce, IHashAlgorithm hashAlg) {
		super(username);
		if (nonce == null) {
			nonce = new byte[0];
		}
		init(username, pwd, datetime, nonce, hashAlg);
	}
}
