/*
 * AXmlEncryption.java
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

package com.hammingweight.kiss.xmlencryption;

import com.hammingweight.kiss.crypto.IBlockCipher;
import com.hammingweight.kiss.crypto.IRandomNumberGenerator;

public abstract class AXmlBlockCipher implements IXmlCipher {

	private IBlockCipher cipher;

	private int blockSize;

	private IRandomNumberGenerator rng;

	private byte[] block;

	public AXmlBlockCipher(IBlockCipher cipher, int blockSize,
			IRandomNumberGenerator rng) {
		this.cipher = cipher;
		this.blockSize = blockSize;
		this.rng = rng;
		this.block = new byte[blockSize];
	}

	public byte[] encrypt(byte[] plaintext) {
		int encLen = plaintext.length + 2 * this.blockSize;
		encLen /= this.blockSize;
		encLen *= this.blockSize;
		byte[] enc = new byte[encLen];

		// Get an IV and encrypt it.
		this.rng.generate(this.block);
		this.cipher.encryptBlock(this.block);
		System.arraycopy(this.block, 0, enc, 0, this.blockSize);

		int blockOffset = 0;
		int encOffset = this.blockSize;
		for (int i = 0; i < plaintext.length; i++) {
			this.block[blockOffset++] ^= plaintext[i];
			if (blockOffset == this.blockSize) {
				this.cipher.encryptBlock(block);
				blockOffset = 0;
				System.arraycopy(block, 0, enc, encOffset, this.blockSize);
				encOffset += this.blockSize;
			}
		}
		
		// Add padding to the last block.
		this.rng.generate(this.block, blockOffset, this.blockSize - blockOffset - 1);
		this.block[this.blockSize - 1] ^= (byte) (this.blockSize - blockOffset);
		this.cipher.encryptBlock(this.block);
		System.arraycopy(block, 0, enc, encOffset, this.blockSize);
		
		return enc;
	}

	public abstract String getAlgorithm();

}
