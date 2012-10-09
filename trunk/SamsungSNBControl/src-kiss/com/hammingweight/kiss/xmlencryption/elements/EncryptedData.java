/*
 * EncryptedData.java
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Element;

import com.hammingweight.kiss.KissEnvelope;
import com.hammingweight.kiss.NameSpaces;
import com.hammingweight.kiss.xmlencryption.IXmlCipher;

public class EncryptedData extends Element {
	
	private CipherData cipherValue;
	
	private EncryptedData(IXmlCipher xmlCipher, String id, String type) {
		setName("EncryptedData");
		setNamespace(NameSpaces.XENC_NAMESPACE);
		setAttribute(null, "Id", id);
		setAttribute(null, "Type", NameSpaces.XENC_NAMESPACE + type);
		
		// Add the encryption method element.
		addChild(Element.ELEMENT, new EncryptionMethod(xmlCipher));
		
		// Add the CipherData element
		// Add the CipherValue element
		this.cipherValue = new CipherData();
		addChild(Element.ELEMENT, this.cipherValue);
	}
	
	private void encryptObjectProperty(PropertyInfo pi, Object obj, IXmlCipher xmlCipher, KissEnvelope env) {
		KXmlSerializer writer = new KXmlSerializer();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			writer.setOutput(baos, null);
			writer.startTag(pi.namespace, pi.name);
			env.writeProperty(writer, obj, pi);
			writer.endTag(pi.namespace, pi.name);
		} catch (IOException ioe) {
			throw new RuntimeException("Unable to serialize the SOAP object.");
		}
		byte[] encData = xmlCipher.encrypt(baos.toByteArray());
		this.cipherValue.setValue(encData);
	}
	
	EncryptedData(PropertyInfo pi, SoapObject soapObj, IXmlCipher xmlCipher, String id, KissEnvelope env) {
		this(xmlCipher, id, "Element");
		encryptObjectProperty(pi, soapObj, xmlCipher, env);
	}
	
	public EncryptedData(PropertyInfo pi, SoapObject soapObj, IXmlCipher xmlCipher, String id) {
		this(pi, soapObj, xmlCipher, id, new KissEnvelope(SoapEnvelope.VER11));
	}

	EncryptedData(SoapObject soapObj, IXmlCipher xmlCipher, String id, KissEnvelope env) {
		this(xmlCipher, id, "Element");
		KXmlSerializer writer = new KXmlSerializer();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		env.bodyOut = soapObj;
		try {
			writer.setOutput(baos, null);
			env.writeBody(writer);
		} catch (IOException ioe) {
			throw new RuntimeException("Unable to serialize the SOAP object.");
		}
		byte[] encData = xmlCipher.encrypt(baos.toByteArray());
		this.cipherValue.setValue(encData);
	}
	
	public EncryptedData(SoapObject soapObj, IXmlCipher xmlCipher, String id) {
		this(soapObj, xmlCipher, id, new KissEnvelope(SoapEnvelope.VER11));
	}
	
	EncryptedData(PropertyInfo pi, SoapPrimitive soapPrim, IXmlCipher xmlCipher, String id, KissEnvelope env) {
		this(xmlCipher, id, "Element");
		encryptObjectProperty(pi, soapPrim, xmlCipher, env);
	}
	
	public EncryptedData(PropertyInfo pi, SoapPrimitive soapPrim, IXmlCipher xmlCipher, String id) {
		this(pi, soapPrim, xmlCipher, id, new KissEnvelope(SoapEnvelope.VER11));
	}

	public EncryptedData(String characterData, IXmlCipher xmlCipher, String id) {
		this(xmlCipher, id, "Content");
		byte[] encData = xmlCipher.encrypt(characterData.getBytes());
		this.cipherValue.setValue(encData);
	}
}
