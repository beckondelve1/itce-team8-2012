/*
 * AesKeyWrap.java
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

import com.hammingweight.kiss.crypto.IBlockCipher;
import com.hammingweight.kiss.xmlencryption.IXmlKeyWrap;

public class AesKeyWrap implements IXmlKeyWrap {

	private IBlockCipher aesCipher;
	
	public AesKeyWrap(IBlockCipher aesCipher) {
		this.aesCipher = aesCipher;
	}
	
	private byte[] encrypt64BitKey(byte[] P) {
		byte[] C = new byte[16];
		System.arraycopy(P, 0, C, 8, 8);
		for (int i = 0; i < 8; i++) {
			C[i] = (byte)0xA6;
		}
		this.aesCipher.encryptBlock(C);
		return C;
	}
	
	public byte[] encrypt(byte[] P) {
		int pLen = P.length;
		if ((pLen == 0) || ((pLen & 7) != 0)) {
			throw new RuntimeException("Key length must be a multiple of 64 bits.");
		}
		if (pLen == 8) {
			return encrypt64BitKey(P);
		}
		
		byte[] A = new byte[]{(byte)0xA6, (byte)0xA6, (byte)0xA6, (byte)0xA6, (byte)0xA6, (byte)0xA6, (byte)0xA6, (byte)0xA6}; 
		byte[] B = new byte[pLen];
		byte[] C = new byte[pLen + 8];
		byte[] R = new byte[pLen];
		
		System.arraycopy(P, 0, R, 0, pLen);
		
		for (int j = 0; j <=5; j++) {
			for (int i = 1; i <= pLen / 8; i++) {
				int t = i + j * 2;
				System.arraycopy(A, 0, B, 0, 8);
				System.arraycopy(R, i * 8 - 8, B, 8, 8);
				this.aesCipher.encryptBlock(B);
				System.arraycopy(B, 0, A, 0, 8);
				A[7] ^= t;
				System.arraycopy(B, 8, R, 8 * i - 8, 8);
			}
		}
		
		System.arraycopy(A, 0, C, 0, 8);
		System.arraycopy(R, 0, C, 8, pLen);
		
		return C;
	}

	public String getAlgorithm() {
		return "http://www.w3.org/2001/04/xmlenc#kw-aes128";
	}

}
