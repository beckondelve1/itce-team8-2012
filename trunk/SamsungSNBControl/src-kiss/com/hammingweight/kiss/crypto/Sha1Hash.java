/*
 * Sha1Hash.java
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

public class Sha1Hash implements IHashAlgorithm {

	private static final int K0 = 0x5a827999;

	private static final int K1 = 0x6ed9eba1;

	private static final int K2 = 0x8f1bbcdc;

	private static final int K3 = 0xca62c1d6;

	private int[] w = new int[80];

	private byte[] block = new byte[64];

	private int h0, h1, h2, h3, h4;

	private int numBytesDigested;

	private void init() {
		h0 = 0x67452301;
		h1 = 0xefcdab89;
		h2 = 0x98badcfe;
		h3 = 0x10325476;
		h4 = 0xc3d2e1f0;

		this.numBytesDigested = 0;
	}

	public Sha1Hash() {
		init();
	}

	private int cyclicShift(int x, int r) {
		int y = x << r;
		x >>>= (32 - r);
		return x + y;
	}

	private void bytesToInts() {
		int index = 0;
		for (int i = 0; i < 16; i++) {
			int c = (this.block[index++] & 0xFF) << 24;
			c += (this.block[index++] & 0xFF) << 16;
			c += (this.block[index++] & 0xFF) << 8;
			this.w[i] = c + (this.block[index++] & 0xFF);
		}

		for (int i = 16; i < 80; i++) {
			int c = w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16];
			w[i] = cyclicShift(c, 1);
		}
	}

	private void compress() {
		int a = h0;
		int b = h1;
		int c = h2;
		int d = h3;
		int e = h4;

		for (int i = 0; i < 20; i++) {
			int temp = cyclicShift(a, 5) + ((b & c) | ((~b) & d)) + e + w[i]
					+ K0;
			e = d;
			d = c;
			c = cyclicShift(b, 30);
			b = a;
			a = temp;
		}
		for (int i = 20; i < 40; i++) {
			int temp = cyclicShift(a, 5) + (b ^ c ^ d) + e + w[i] + K1;
			e = d;
			d = c;
			c = cyclicShift(b, 30);
			b = a;
			a = temp;
		}
		for (int i = 40; i < 60; i++) {
			int temp = cyclicShift(a, 5) + ((b & c) | (b & d) | (c & d)) + e
					+ w[i] + K2;
			e = d;
			d = c;
			c = cyclicShift(b, 30);
			b = a;
			a = temp;
		}
		for (int i = 60; i < 80; i++) {
			int temp = cyclicShift(a, 5) + (b ^ c ^ d) + e + w[i] + K3;
			e = d;
			d = c;
			c = cyclicShift(b, 30);
			b = a;
			a = temp;
		}

		h0 += a;
		h1 += b;
		h2 += c;
		h3 += d;
		h4 += e;
	}

	private void updateByte(byte b) {
		int blockOffset = (int) (0x3F & this.numBytesDigested++);
		this.block[blockOffset] = b;
		if (blockOffset == 0x3F) {
			bytesToInts();
			compress();
		}
	}

	public void update(byte[] messageBlock) {
		for (int i = 0; i < messageBlock.length; i++) {
			this.updateByte(messageBlock[i]);
		}
	}

	public byte[] doFinal(byte[] messageBlock) {
		update(messageBlock);
		int messageLengthInBits = this.numBytesDigested * 8;
		updateByte((byte) 0x80);

		// Pad until we get to byte 56 in the last block. 
		// The SHA spec refers to bit 448.  448 / 8 = 56;
		// so bit 448 corresponds to byte 56.
		while ((this.numBytesDigested & 0x3F) != 56) {
			updateByte((byte) 0);
		}

		// Encode the length of the original message in the last
		// 8 bytes.
		for (int i = 0; i < 8; i++) {
			updateByte(i < 4 ? (byte) 0
					: (byte) (messageLengthInBits >>> (56 - 8 * i)));
		}

		// Convert the hash words to an array of bytes.
		byte[] hash = new byte[20];
		for (int i = 0; i < 4; i++) {
			int s = 24 - i * 8;
			hash[i] = (byte) (h0 >>> s);
			hash[i + 4] = (byte) (h1 >>> s);
			hash[i + 8] = (byte) (h2 >>> s);
			hash[i + 12] = (byte) (h3 >>> s);
			hash[i + 16] = (byte) (h4 >>> s);
		}
		
		// This resets h0..h4 so that we can reuse this hash object.
		init();
		
		return hash;
	}

	public int getHashSize() {
		return 20;
	}
}
