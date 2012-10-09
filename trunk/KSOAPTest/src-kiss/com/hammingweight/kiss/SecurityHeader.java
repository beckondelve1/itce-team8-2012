/*
 * SecurityHeader.java
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

package com.hammingweight.kiss;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.kxml2.kdom.Element;

/**
 * The SecurityHeader class represents an XML element that may be added to 
 * the header of a SOAP message.
 * 
 * @author C.A. Meijer
 * @since KiSS 0.1
 */

public class SecurityHeader extends Element {

	/**
	 * A constructor that wraps an XML element in a WS-Security element.
	 * 
	 * @param element The XML element to be wrapped.
	 */
	public SecurityHeader(Element element) {
		setName("Security");
		setNamespace(NameSpaces.WSSE_NAMESPACE);
		addChild(Element.ELEMENT, element);
	}

	/**
	 * Inserts this security header into a kSOAP envelope. If there are
	 * already SOAP headers, this header is inserted at the end of the 
	 * headers.
	 * 
	 * @param envelope The envelope that contains this security header.
	 */
//	public void appendToEnvelope(SoapSerializationEnvelope envelope) {
//		if (envelope.headerIn == null) {
//			envelope.headerIn = new Element[] { this };
//		} else {
//			int numElems = envelope.headerIn.length;
//			Element[] elems = new Element[numElems + 1];
//			System.arraycopy(envelope.headerIn, 0, elems, 0, numElems);
//			elems[numElems] = this;
//			envelope.headerIn = elems;
//		}
//	}
	
	//HiepNH
	public void appendToEnvelope(SoapSerializationEnvelope envelope) {
		if (envelope.headerOut == null) {
			envelope.headerOut = new Element[] { this };
		} else {
			int numElems = envelope.headerOut.length;
			Element[] elems = new Element[numElems + 1];
			System.arraycopy(envelope.headerOut, 0, elems, 0, numElems);
			elems[numElems] = this;
			envelope.headerOut = elems;
		}
	}
}
