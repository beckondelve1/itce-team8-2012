/*
 * RsaOaep.java
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

import com.hammingweight.kiss.crypto.IHashAlgorithm;
import com.hammingweight.kiss.crypto.IRandomNumberGenerator;
import com.hammingweight.kiss.crypto.IRsaCipher;
import com.hammingweight.kiss.xmlencryption.IXmlKeyWrap;

public class RsaOaep implements IXmlKeyWrap {

	// The SHA-1 hash size.
	private int hLen;

	private IRsaCipher rsaCipher;

	private IHashAlgorithm hash;

	private IRandomNumberGenerator rng;

	private byte[] mgf(byte[] mgfSeed, int maskLen) {
		int numIterations = (maskLen + hLen - 1) / hLen;

		// T will contain the generated mask (plus, possibly, some additional
		// bytes).
		byte[] T = new byte[numIterations * hLen];

		// mgfC contains mgfSeed || C where C is a 4 byte counter.
		byte[] mgfC = new byte[mgfSeed.length + 4];
		System.arraycopy(mgfSeed, 0, mgfC, 0, mgfSeed.length);

		for (int c = 0; c < numIterations; c++) {
			mgfC[mgfC.length - 1] = (byte) c;
			byte[] hash = this.hash.doFinal(mgfC);
			System.arraycopy(hash, 0, T, c * hLen, hLen);
		}

		return T;
	}

	public RsaOaep(IRsaCipher rsaCipher, IRandomNumberGenerator rng,
			IHashAlgorithm shaHash) {
		this.rsaCipher = rsaCipher;
		this.hash = shaHash;
		this.rng = rng;
	}

	public byte[] encrypt(byte[] plaintext) {
		this.hLen = this.hash.getHashSize();
		
		int modLen = this.rsaCipher.getModulusLength();
		if (plaintext.length > modLen - 2 * hLen - 2) {
			throw new IllegalArgumentException("message too long");
		}

		// Get lHash which is Hash([]).
		byte[] lHash = this.hash.doFinal(new byte[0]);

		// We need 20 random bytes.
		byte[] seed = new byte[hLen];
		this.rng.generate(seed);

		// Create DB.
		byte[] DB = new byte[modLen - hLen - 1];
		System.arraycopy(lHash, 0, DB, 0, hLen);
		DB[DB.length - plaintext.length - 1] = (byte) 1;
		System.arraycopy(plaintext, 0, DB, DB.length - plaintext.length,
				plaintext.length);
		byte[] dbMask = mgf(seed, DB.length);
		for (int i = 0; i < DB.length; i++) {
			DB[i] ^= dbMask[i];
		}
		
		// Get the seed mask.
		byte[] seedMask = this.mgf(DB, hLen);
		for (int i = 0; i < hLen; i++) {
			seed[i] ^= seedMask[i];
		}
		
		// Create the block to encrypt.
		byte[] block = new byte[modLen];
		System.arraycopy(seed, 0, block, 1, hLen);
		System.arraycopy(DB, 0, block, hLen + 1, DB.length);
		this.rsaCipher.exponentiate(block);
		
		return block;
	}

	public String getAlgorithm() {
		return "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
	}

}
