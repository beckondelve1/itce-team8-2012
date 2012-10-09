/*
 * X931Generator.java
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

package com.hammingweight.kiss.crypto;

import java.util.Date;

public class X931Generator extends ARandomNumberGenerator {

	private IBlockCipher blockCipher;

	private byte[] I;

	private byte[] R;

	private byte[] V;

	private int rOffset;

	private void init(byte[] V) {
		int blockSize = this.blockCipher.getBlockSize();
		this.I = new byte[blockSize];
		this.V = new byte[blockSize];
		this.R = new byte[blockSize];
		System.arraycopy(V, 0, this.V, 0, blockSize);
		this.rOffset = this.I.length;
	}

	public X931Generator(byte[] key, byte[] V) {
		this.blockCipher = new Aes128();
		this.blockCipher.setKey(key);
		init(V);
	}

	public X931Generator(IBlockCipher blockCipher, byte[] V) {
		this.blockCipher = blockCipher;
		init(V);
	}

	public byte generateByte() {
		if (this.rOffset == this.I.length) {
			this.update(new Date());
		}
		return this.R[this.rOffset++];
	}

	void update(Date date) {
		long t = date.getTime();
		for (int i = 0; i < this.I.length; i++) {
			this.I[i] = (byte) t;
			t >>>= 8;
		}
		this.blockCipher.encryptBlock(I);

		// Generate the bytes of R.
		for (int i = 0; i < this.I.length; i++) {
			R[i] = (byte) (I[i] ^ V[i]);
		}
		this.blockCipher.encryptBlock(R);
		
		// Generate the bytes of V.
		for (int i = 0; i < this.I.length; i++) {
			V[i] = (byte) (I[i] ^ R[i]);
		}
		this.blockCipher.encryptBlock(V);
		this.rOffset = 0;
	}
}
