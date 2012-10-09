/*
 * RsaCipher.java
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

public class RsaCipher implements IRsaCipher {

	private static final long MASK_32_BITS = 0xFFFFFFFFL;

	private static final int NUM_BITS_PER_WORD = 32;

	// We manipulate integers on a per word, not a per byte, level where a word
	// is a 32 bit UNSIGNED integer.
	private static final int NUM_BYTES_PER_WORD = 4;

	private int modulusLengthInWords;

	private byte[] modulus;

	private byte[] exponent;

	private int modulusLength;
	
	// A method to copy a byte array.
	private static byte[] copyByteArray(byte[] b) {
		byte[] copy = new byte[b.length];
		System.arraycopy(b, 0, copy, 0, b.length);
		return copy;
	}

	// This utility method multiplies two n-bit values together. The values
	// are represented as arrays of 32 words.
	private void multiply(int[] x, int[] y, int[] xy) {
		for (int i = 0; i < 2 * modulusLengthInWords; i++) {
			xy[i] = 0;
		}

		for (int i = 0; i < modulusLengthInWords; i++) {
			long c = 0;
			long xi = x[i];
			xi &= MASK_32_BITS;
			for (int j = 0; j < modulusLengthInWords; j++) {
				long p = xi;
				long yj = y[j];
				yj &= MASK_32_BITS;
				p *= yj;
				p += ((long) xy[i + j]) & MASK_32_BITS;
				p += c;
				xy[i + j] = (int) p;
				c = p >>> NUM_BITS_PER_WORD;
			}
			xy[i + modulusLengthInWords] = (int) c;
		}
	}

	// This routine multiplies a n bit value by a 32 bit value and returns
	// the resultant n+32 bit value.
	private void multiply(int[] x, long q, int[] p) {
		long c = 0;
		for (int i = 0; i < modulusLengthInWords; i++) {
			long xi = x[i];
			xi &= MASK_32_BITS;
			long pr = xi * q + c;
			p[i] = (int) (pr);
			c = pr >>> NUM_BITS_PER_WORD;
		}
		p[modulusLengthInWords] = (int) c;
	}

	// A routine to do a modular reduction of a 2n bit value by an n bit
	// modulus.
	private void modulo(int[] x, int[] y) {
		long yt = y[this.modulusLengthInWords - 1] >>> 1;

		int[] p = new int[this.modulusLengthInWords + 1];

		for (int i = 2 * modulusLengthInWords - 1; i >= modulusLengthInWords; i--) {
			long xt1 = x[i];
			xt1 <<= NUM_BITS_PER_WORD;
			long xt2 = x[i - 1];
			xt2 &= MASK_32_BITS;
			xt2 += xt1;
			// This right shift is to ensure that xt2 is treated as positive.
			// We've already compensated for yt's sign bit above.
			xt2 >>>= 1;
			long q = xt2 / yt;

			multiply(y, q, p);
			long b = 0;
			for (int j = 0; j <= modulusLengthInWords; j++) {
				long s = x[i - modulusLengthInWords + j];
				s &= MASK_32_BITS;
				long t = p[j];
				t &= MASK_32_BITS;
				s -= t;
				s -= b;
				if (s < 0) {
					b = 1;
					s &= MASK_32_BITS;
				} else {
					b = 0;
				}
				x[i - modulusLengthInWords + j] = (int) s;
			}

			while (x[i] != 0) {
				long c = 0;
				for (int j = 0; j < modulusLengthInWords; j++) {
					long s = x[i - modulusLengthInWords + j];
					s &= MASK_32_BITS;
					long t = y[j];
					t &= MASK_32_BITS;
					s += t;
					s += c;
					x[i - modulusLengthInWords + j] = (int) s;
					c = s >>> NUM_BITS_PER_WORD;
				}
				x[i] += (int) c;
			}
		}
	}

	// A method to convert big endian byte representation to a little endian
	// word representation.
	private int[] convertBytesToWords(byte[] x) {
		int[] xi = new int[modulusLengthInWords];
		for (int i = 0; i < modulusLengthInWords; i++) {
			int xi1 = 0;
			for (int j = NUM_BYTES_PER_WORD; j > 0; j--) {
				xi1 <<= 8;
				int b = x[this.modulusLength - NUM_BYTES_PER_WORD * i - j];
				b &= 0xFF;
				xi1 += b;
			}
			xi[i] = xi1;
		}

		return xi;
	}

	// A method to convert a little endian word representation to a big endian
	// byte representation.
	private byte[] convertWordsToBytes(int[] p) {
		byte[] x = new byte[this.modulusLength];
		for (int i = 0; i < modulusLengthInWords; i++) {
			int pi1 = p[i];
			for (int j = 1; j <= NUM_BYTES_PER_WORD; j++) {
				x[this.modulusLength - 4 * i - j] = (byte) pi1;
				pi1 >>= 8;
			}
		}

		return x;
	}

	public RsaCipher() {

	}

	public RsaCipher(byte[] modulus, byte[] exponent) {
		setModulus(modulus);
		setExponent(exponent);
	}

	public void exponentiate(byte[] data) {
		int[] x = convertBytesToWords(data);
		int[] mi = convertBytesToWords(modulus);

		// y holds the result of the exponentiation.
		int[] y = new int[this.modulusLengthInWords];
		int[] y2 = new int[this.modulusLengthInWords * 2];
		y[0] = 1;

		// Copy the exponent.
		byte[] exp2 = copyByteArray(this.exponent);

		// Do the exponentiation.
		for (int i = exp2.length - 1; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if ((exp2[i] & 1) == 1) {
					multiply(x, y, y2);
					modulo(y2, mi);
					System.arraycopy(y2, 0, y, 0, this.modulusLengthInWords);
				}
				exp2[i] >>>= 1;
				
				if ((i == 0) && (exp2[i] == 0)) {
					break;
				}
				multiply(x, x, y2);
				modulo(y2, mi);
				System.arraycopy(y2, 0, x, 0, this.modulusLengthInWords);
			}
		}

		// Convert the result from words to bytes.
		byte[] temp = this.convertWordsToBytes(y);
		System.arraycopy(temp, 0, data, 0, this.modulusLength);
	}

	public int getModulusLength() {
		return this.modulusLength;
	}

	public void setExponent(byte[] exponent) {
		this.exponent = copyByteArray(exponent);
	}

	public void setModulus(byte[] modulus) {
		if ((modulus[0] & 0x80) == 0) {
			throw new IllegalArgumentException(
					"The MSB of the modulus must be set.");
		}
		if ((modulus.length & 3) != 0) {
			throw new IllegalArgumentException(
					"The modulus length must be a multiple of 32 bits.");
		}
		this.modulus = copyByteArray(modulus);
		this.modulusLength = modulus.length;
		this.modulusLengthInWords = modulus.length / NUM_BYTES_PER_WORD;
	}

}
