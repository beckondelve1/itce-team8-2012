/*
 * BinarySecurityToken.java
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

import org.kobjects.base64.Base64;
import org.kxml2.kdom.Element;

import com.hammingweight.kiss.NameSpaces;

public class BinarySecurityToken extends Element {
	public BinarySecurityToken(String id, String valueType, byte[] value) {
		setNamespace(NameSpaces.WSSE_NAMESPACE);
		setName("BinarySecurityToken");
		setAttribute(NameSpaces.WSU_NAMESPACE, "Id", id);
		setAttribute(null, "ValueType", valueType);
		setAttribute(
				null,
				"EncodingType",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
		addChild(Element.TEXT, Base64.encode(value));
	}
}
