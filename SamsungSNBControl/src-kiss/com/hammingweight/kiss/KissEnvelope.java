/*
 * KissEnvelope.java
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

import java.io.IOException;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlSerializer;

public class KissEnvelope extends SoapSerializationEnvelope {
	public KissEnvelope(int version) {
		super(version);
	}

	public void writeBody(XmlSerializer writer) throws IOException {
		if (this.bodyOut instanceof Element) {
			Element e = (Element) this.bodyOut;
			e.write(writer);
		} else {
			super.writeBody(writer);
		}
	}

	public void writeObjectBody(XmlSerializer writer, KvmSerializable obj)
			throws IOException {
		PropertyInfo info = new PropertyInfo();
		int cnt = obj.getPropertyCount();
		for (int i = 0; i < cnt; i++) {
			Object subObject = obj.getProperty(i);
			if (subObject instanceof Element) {
				((Element) subObject).write(writer);
			} else {
				obj.getPropertyInfo(i, properties, info);
				if ((info.flags & PropertyInfo.TRANSIENT) == 0) {
					String nsp = info.namespace;
					writer.startTag(nsp, info.name);
					writeProperty(writer, subObject, info);
					writer.endTag(nsp, info.name);
				}
			}
		}
	}

	public void writeProperty(XmlSerializer writer, Object obj,
			PropertyInfo type) throws IOException {
		super.writeProperty(writer, obj, type);
	}
}
