/*
 * AesCipher.java
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
import com.hammingweight.kiss.crypto.IRandomNumberGenerator;
import com.hammingweight.kiss.xmlencryption.AXmlBlockCipher;

public class AesCipher extends AXmlBlockCipher {

	public AesCipher(IBlockCipher aesCipher, IRandomNumberGenerator rng) {
		super(aesCipher, 16, rng);
	}
	
	public String getAlgorithm() {
		return "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
	}

}
