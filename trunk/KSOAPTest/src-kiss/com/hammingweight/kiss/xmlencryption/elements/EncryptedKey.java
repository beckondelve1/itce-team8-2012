/*
 * EncryptedKey.java
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

package com.hammingweight.kiss.xmlencryption.elements;

import org.kxml2.kdom.Element;

import com.hammingweight.kiss.NameSpaces;
import com.hammingweight.kiss.xmlencryption.IXmlKeyWrap;

public class EncryptedKey extends Element {

	private Element refList;
	
	public EncryptedKey(IXmlKeyWrap xmlKeyWrap, byte[] plaintextKey) {
		this(xmlKeyWrap, null, plaintextKey);
	}

	public EncryptedKey(IXmlKeyWrap xmlKeyWrap, KeyInfo keyInfo,
			byte[] plaintextKey) {
		setName("EncryptedKey");
		setNamespace(NameSpaces.XENC_NAMESPACE);
		addChild(Element.ELEMENT, new EncryptionMethod(xmlKeyWrap));
		if (keyInfo != null) {
			addChild(Element.ELEMENT, keyInfo);
		}
		CipherData cd = new CipherData();
		cd.setValue(xmlKeyWrap.encrypt(plaintextKey));
		addChild(Element.ELEMENT, cd);
	}

	public void addKeyReference(String refId) {
		if (this.refList == null) {
			this.refList = new Element();
			this.refList.setName("ReferenceList");
			this.refList.setNamespace(NameSpaces.XENC_NAMESPACE);
			this.addChild(Element.ELEMENT, this.refList);
		}
		
		Element dataRef = new Element();
		dataRef.setName("DataReference");
		dataRef.setNamespace(NameSpaces.XENC_NAMESPACE);
		dataRef.setAttribute(null, "URI", "#" + refId);
		this.refList.addChild(Element.ELEMENT, dataRef);
	}
}
