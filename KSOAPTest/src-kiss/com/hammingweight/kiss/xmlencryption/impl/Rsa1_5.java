/*
 * Rsa1_5.java
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

package com.hammingweight.kiss.xmlencryption.impl;

import com.hammingweight.kiss.crypto.IRandomNumberGenerator;
import com.hammingweight.kiss.crypto.IRsaCipher;
import com.hammingweight.kiss.xmlencryption.IXmlKeyWrap;

public class Rsa1_5 implements IXmlKeyWrap {

	private IRsaCipher rsaCipher;

	private IRandomNumberGenerator rng;

	public Rsa1_5(IRsaCipher rsaCipher, IRandomNumberGenerator rng) {
		this.rsaCipher = rsaCipher;
		this.rng = rng;
	}

	public byte[] encrypt(byte[] plaintext) {
		int modLen = this.rsaCipher.getModulusLength();
		byte[] blockToEncrypt = new byte[modLen];
		blockToEncrypt[1] = (byte) 2;
		int numRandomBytes = modLen - 3 - plaintext.length;
		int numBytesGenerated = 0;
		while (numBytesGenerated != numRandomBytes) {
			byte b = this.rng.generateByte();
			if (b != 0) {
				blockToEncrypt[2 + numBytesGenerated++] = b;
			}
		}
		System.arraycopy(plaintext, 0, blockToEncrypt, modLen
				- plaintext.length, plaintext.length);
		this.rsaCipher.exponentiate(blockToEncrypt);

		return blockToEncrypt;
	}

	public String getAlgorithm() {
		return "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
	}

}
