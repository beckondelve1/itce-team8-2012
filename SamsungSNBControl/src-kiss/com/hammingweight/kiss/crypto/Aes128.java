/*
 * Aes128.java
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

public class Aes128 implements IBlockCipher {

	private int[] expandedKey;

	private byte[] matrixMult = new byte[16];

	public static final byte[] sBox = { (byte) 0x63, (byte) 0x7c, (byte) 0x77,
			(byte) 0x7b, (byte) 0xf2, (byte) 0x6b, (byte) 0x6f, (byte) 0xc5,
			(byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2b, (byte) 0xfe,
			(byte) 0xd7, (byte) 0xab, (byte) 0x76, (byte) 0xca, (byte) 0x82,
			(byte) 0xc9, (byte) 0x7d, (byte) 0xfa, (byte) 0x59, (byte) 0x47,
			(byte) 0xf0, (byte) 0xad, (byte) 0xd4, (byte) 0xa2, (byte) 0xaf,
			(byte) 0x9c, (byte) 0xa4, (byte) 0x72, (byte) 0xc0, (byte) 0xb7,
			(byte) 0xfd, (byte) 0x93, (byte) 0x26, (byte) 0x36, (byte) 0x3f,
			(byte) 0xf7, (byte) 0xcc, (byte) 0x34, (byte) 0xa5, (byte) 0xe5,
			(byte) 0xf1, (byte) 0x71, (byte) 0xd8, (byte) 0x31, (byte) 0x15,
			(byte) 0x04, (byte) 0xc7, (byte) 0x23, (byte) 0xc3, (byte) 0x18,
			(byte) 0x96, (byte) 0x05, (byte) 0x9a, (byte) 0x07, (byte) 0x12,
			(byte) 0x80, (byte) 0xe2, (byte) 0xeb, (byte) 0x27, (byte) 0xb2,
			(byte) 0x75, (byte) 0x09, (byte) 0x83, (byte) 0x2c, (byte) 0x1a,
			(byte) 0x1b, (byte) 0x6e, (byte) 0x5a, (byte) 0xa0, (byte) 0x52,
			(byte) 0x3b, (byte) 0xd6, (byte) 0xb3, (byte) 0x29, (byte) 0xe3,
			(byte) 0x2f, (byte) 0x84, (byte) 0x53, (byte) 0xd1, (byte) 0x00,
			(byte) 0xed, (byte) 0x20, (byte) 0xfc, (byte) 0xb1, (byte) 0x5b,
			(byte) 0x6a, (byte) 0xcb, (byte) 0xbe, (byte) 0x39, (byte) 0x4a,
			(byte) 0x4c, (byte) 0x58, (byte) 0xcf, (byte) 0xd0, (byte) 0xef,
			(byte) 0xaa, (byte) 0xfb, (byte) 0x43, (byte) 0x4d, (byte) 0x33,
			(byte) 0x85, (byte) 0x45, (byte) 0xf9, (byte) 0x02, (byte) 0x7f,
			(byte) 0x50, (byte) 0x3c, (byte) 0x9f, (byte) 0xa8, (byte) 0x51,
			(byte) 0xa3, (byte) 0x40, (byte) 0x8f, (byte) 0x92, (byte) 0x9d,
			(byte) 0x38, (byte) 0xf5, (byte) 0xbc, (byte) 0xb6, (byte) 0xda,
			(byte) 0x21, (byte) 0x10, (byte) 0xff, (byte) 0xf3, (byte) 0xd2,
			(byte) 0xcd, (byte) 0x0c, (byte) 0x13, (byte) 0xec, (byte) 0x5f,
			(byte) 0x97, (byte) 0x44, (byte) 0x17, (byte) 0xc4, (byte) 0xa7,
			(byte) 0x7e, (byte) 0x3d, (byte) 0x64, (byte) 0x5d, (byte) 0x19,
			(byte) 0x73, (byte) 0x60, (byte) 0x81, (byte) 0x4f, (byte) 0xdc,
			(byte) 0x22, (byte) 0x2a, (byte) 0x90, (byte) 0x88, (byte) 0x46,
			(byte) 0xee, (byte) 0xb8, (byte) 0x14, (byte) 0xde, (byte) 0x5e,
			(byte) 0x0b, (byte) 0xdb, (byte) 0xe0, (byte) 0x32, (byte) 0x3a,
			(byte) 0x0a, (byte) 0x49, (byte) 0x06, (byte) 0x24, (byte) 0x5c,
			(byte) 0xc2, (byte) 0xd3, (byte) 0xac, (byte) 0x62, (byte) 0x91,
			(byte) 0x95, (byte) 0xe4, (byte) 0x79, (byte) 0xe7, (byte) 0xc8,
			(byte) 0x37, (byte) 0x6d, (byte) 0x8d, (byte) 0xd5, (byte) 0x4e,
			(byte) 0xa9, (byte) 0x6c, (byte) 0x56, (byte) 0xf4, (byte) 0xea,
			(byte) 0x65, (byte) 0x7a, (byte) 0xae, (byte) 0x08, (byte) 0xba,
			(byte) 0x78, (byte) 0x25, (byte) 0x2e, (byte) 0x1c, (byte) 0xa6,
			(byte) 0xb4, (byte) 0xc6, (byte) 0xe8, (byte) 0xdd, (byte) 0x74,
			(byte) 0x1f, (byte) 0x4b, (byte) 0xbd, (byte) 0x8b, (byte) 0x8a,
			(byte) 0x70, (byte) 0x3e, (byte) 0xb5, (byte) 0x66, (byte) 0x48,
			(byte) 0x03, (byte) 0xf6, (byte) 0x0e, (byte) 0x61, (byte) 0x35,
			(byte) 0x57, (byte) 0xb9, (byte) 0x86, (byte) 0xc1, (byte) 0x1d,
			(byte) 0x9e, (byte) 0xe1, (byte) 0xf8, (byte) 0x98, (byte) 0x11,
			(byte) 0x69, (byte) 0xd9, (byte) 0x8e, (byte) 0x94, (byte) 0x9b,
			(byte) 0x1e, (byte) 0x87, (byte) 0xe9, (byte) 0xce, (byte) 0x55,
			(byte) 0x28, (byte) 0xdf, (byte) 0x8c, (byte) 0xa1, (byte) 0x89,
			(byte) 0x0d, (byte) 0xbf, (byte) 0xe6, (byte) 0x42, (byte) 0x68,
			(byte) 0x41, (byte) 0x99, (byte) 0x2d, (byte) 0x0f, (byte) 0xb0,
			(byte) 0x54, (byte) 0xbb, (byte) 0x16 };

	private static final int rCon[] = { 0, 0x01000000, 0x02000000, 0x04000000,
			0x08000000, 0x10000000, 0x20000000, 0x40000000, 0x80000000,
			0x1b000000, 0x36000000 };

	private static final byte[] mult2 = { (byte) 0, (byte) 2, (byte) 4,
			(byte) 6, (byte) 8, (byte) 10, (byte) 12, (byte) 14, (byte) 16,
			(byte) 18, (byte) 20, (byte) 22, (byte) 24, (byte) 26, (byte) 28,
			(byte) 30, (byte) 32, (byte) 34, (byte) 36, (byte) 38, (byte) 40,
			(byte) 42, (byte) 44, (byte) 46, (byte) 48, (byte) 50, (byte) 52,
			(byte) 54, (byte) 56, (byte) 58, (byte) 60, (byte) 62, (byte) 64,
			(byte) 66, (byte) 68, (byte) 70, (byte) 72, (byte) 74, (byte) 76,
			(byte) 78, (byte) 80, (byte) 82, (byte) 84, (byte) 86, (byte) 88,
			(byte) 90, (byte) 92, (byte) 94, (byte) 96, (byte) 98, (byte) 100,
			(byte) 102, (byte) 104, (byte) 106, (byte) 108, (byte) 110,
			(byte) 112, (byte) 114, (byte) 116, (byte) 118, (byte) 120,
			(byte) 122, (byte) 124, (byte) 126, (byte) 128, (byte) 130,
			(byte) 132, (byte) 134, (byte) 136, (byte) 138, (byte) 140,
			(byte) 142, (byte) 144, (byte) 146, (byte) 148, (byte) 150,
			(byte) 152, (byte) 154, (byte) 156, (byte) 158, (byte) 160,
			(byte) 162, (byte) 164, (byte) 166, (byte) 168, (byte) 170,
			(byte) 172, (byte) 174, (byte) 176, (byte) 178, (byte) 180,
			(byte) 182, (byte) 184, (byte) 186, (byte) 188, (byte) 190,
			(byte) 192, (byte) 194, (byte) 196, (byte) 198, (byte) 200,
			(byte) 202, (byte) 204, (byte) 206, (byte) 208, (byte) 210,
			(byte) 212, (byte) 214, (byte) 216, (byte) 218, (byte) 220,
			(byte) 222, (byte) 224, (byte) 226, (byte) 228, (byte) 230,
			(byte) 232, (byte) 234, (byte) 236, (byte) 238, (byte) 240,
			(byte) 242, (byte) 244, (byte) 246, (byte) 248, (byte) 250,
			(byte) 252, (byte) 254, (byte) 27, (byte) 25, (byte) 31, (byte) 29,
			(byte) 19, (byte) 17, (byte) 23, (byte) 21, (byte) 11, (byte) 9,
			(byte) 15, (byte) 13, (byte) 3, (byte) 1, (byte) 7, (byte) 5,
			(byte) 59, (byte) 57, (byte) 63, (byte) 61, (byte) 51, (byte) 49,
			(byte) 55, (byte) 53, (byte) 43, (byte) 41, (byte) 47, (byte) 45,
			(byte) 35, (byte) 33, (byte) 39, (byte) 37, (byte) 91, (byte) 89,
			(byte) 95, (byte) 93, (byte) 83, (byte) 81, (byte) 87, (byte) 85,
			(byte) 75, (byte) 73, (byte) 79, (byte) 77, (byte) 67, (byte) 65,
			(byte) 71, (byte) 69, (byte) 123, (byte) 121, (byte) 127,
			(byte) 125, (byte) 115, (byte) 113, (byte) 119, (byte) 117,
			(byte) 107, (byte) 105, (byte) 111, (byte) 109, (byte) 99,
			(byte) 97, (byte) 103, (byte) 101, (byte) 155, (byte) 153,
			(byte) 159, (byte) 157, (byte) 147, (byte) 145, (byte) 151,
			(byte) 149, (byte) 139, (byte) 137, (byte) 143, (byte) 141,
			(byte) 131, (byte) 129, (byte) 135, (byte) 133, (byte) 187,
			(byte) 185, (byte) 191, (byte) 189, (byte) 179, (byte) 177,
			(byte) 183, (byte) 181, (byte) 171, (byte) 169, (byte) 175,
			(byte) 173, (byte) 163, (byte) 161, (byte) 167, (byte) 165,
			(byte) 219, (byte) 217, (byte) 223, (byte) 221, (byte) 211,
			(byte) 209, (byte) 215, (byte) 213, (byte) 203, (byte) 201,
			(byte) 207, (byte) 205, (byte) 195, (byte) 193, (byte) 199,
			(byte) 197, (byte) 251, (byte) 249, (byte) 255, (byte) 253,
			(byte) 243, (byte) 241, (byte) 247, (byte) 245, (byte) 235,
			(byte) 233, (byte) 239, (byte) 237, (byte) 227, (byte) 225,
			(byte) 231, (byte) 229 };

	private int bytesToWord(byte[] b, int offset) {
		return (b[offset++] << 24) + ((b[offset++] & 0xff) << 16)
				+ ((b[offset++] & 0xff) << 8) + (b[offset] & 0xff);
	}

	private int rotWord(int w) {
		return (w << 8) | (w >>> 24);
	}

	private int subWord(int w) {
		int res = sBox[w >>> 24] & 0xFF;
		res <<= 8;
		res += sBox[(w >>> 16) & 0xFF] & 0xFF;
		res <<= 8;
		res += sBox[(w >>> 8) & 0xFF] & 0xFF;
		res <<= 8;
		res += sBox[w & 0xFF] & 0xFF;

		return res;
	}

	public void setKey(byte[] key128) {
		if (this.expandedKey == null) {
			this.expandedKey = new int[44];
		}

		int i = 0;
		for (; i < 4; i++) {
			this.expandedKey[i] = this.bytesToWord(key128, i * 4);
		}

		while (i < 44) {
			int temp = this.expandedKey[i - 1];
			if ((i & 3) == 0) {
				temp = this.subWord(this.rotWord(temp)) ^ rCon[i / 4];
			}
			this.expandedKey[i] = temp ^ this.expandedKey[i - 4];
			i++;
		}
	}

	private void addRoundKey(byte[] state, int keyOffset) {
		int stateOffset = 0;
		for (int i = 0; i < 4; i++) {
			int keyWord = this.expandedKey[keyOffset++];
			state[stateOffset++] ^= keyWord >> 24;
			state[stateOffset++] ^= keyWord >> 16;
			state[stateOffset++] ^= keyWord >> 8;
			state[stateOffset++] ^= keyWord;
		}
	}

	private void subBytes(byte[] block) {
		for (int i = 0; i < 16; i++) {
			block[i] = sBox[block[i] & 0xFF];
		}
	}

	private void shiftRows(byte[] block) {
		byte temp = block[1];
		block[1] = block[5];
		block[5] = block[9];
		block[9] = block[13];
		block[13] = temp;

		temp = block[2];
		block[2] = block[10];
		block[10] = temp;
		temp = block[6];
		block[6] = block[14];
		block[14] = temp;

		temp = block[3];
		block[3] = block[15];
		block[15] = block[11];
		block[11] = block[7];
		block[7] = temp;
	}

	private void mixColumns(byte[] block) {
		for (int i = 0; i < 16; i += 4) {
			byte d = (byte) (block[i] ^ block[i + 1] ^ block[i + 2] ^ block[i + 3]);
			this.matrixMult[i] = (byte) (d ^ block[i] ^ mult2[block[i] & 0xff] ^ mult2[block[i + 1] & 0xff]);
			this.matrixMult[i + 1] = (byte) (d ^ block[i + 1]
					^ mult2[block[i + 1] & 0xff] ^ mult2[block[i + 2] & 0xff]);
			this.matrixMult[i + 2] = (byte) (d ^ block[i + 2]
					^ mult2[block[i + 2] & 0xff] ^ mult2[block[i + 3] & 0xff]);
			this.matrixMult[i + 3] = (byte) (d ^ block[i + 3]
					^ mult2[block[i + 3] & 0xff] ^ mult2[block[i] & 0xff]);
		}

		System.arraycopy(this.matrixMult, 0, block, 0, 16);
	}

	public void encryptBlock(byte[] block) {
		for (int i = 0; i < 36; i += 4) {
			addRoundKey(block, i);
			subBytes(block);
			shiftRows(block);
			mixColumns(block);
		}
		addRoundKey(block, 36);
		subBytes(block);
		shiftRows(block);
		addRoundKey(block, 40);
	}

	public int getBlockSize() {
		return 16;
	}
}
