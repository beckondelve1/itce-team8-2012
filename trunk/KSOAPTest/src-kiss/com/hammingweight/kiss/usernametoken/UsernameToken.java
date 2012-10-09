/*
 * UsernameToken.java
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

package com.hammingweight.kiss.usernametoken;

import org.kxml2.kdom.Element;

import com.hammingweight.kiss.NameSpaces;

public class UsernameToken extends Element {

	public UsernameToken(String username) {
		setName("UsernameToken");
		setNamespace(NameSpaces.WSSE_NAMESPACE);

		// Add the user name.
		Element user = new Element();
		user.setName("Username");
		user.setNamespace(NameSpaces.WSSE_NAMESPACE);
		user.addChild(Element.TEXT, username);
		addChild(Element.ELEMENT, user);
	}

	public UsernameToken(String username, String password) {
		this(username);

		// Add the password.
		Element passwordElement = new Element();
		passwordElement.setName("Password");
		passwordElement.setNamespace(NameSpaces.WSSE_NAMESPACE);
		passwordElement
				.setAttribute(
						NameSpaces.WSSE_NAMESPACE,
						"Type",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#PasswordText");

		passwordElement.addChild(Element.TEXT, password);
		addChild(Element.ELEMENT, passwordElement);
	}

}
