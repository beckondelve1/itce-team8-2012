package net.biyee.android.ONVIF;

import org.simpleframework.xml.Namespace;

@Namespace(reference="http://www.onvif.org/ver10/schema")
public enum StreamType
{
  static
  {
    RTPMulticast = new StreamType("RTPMulticast", 1);
    StreamType[] arrayOfStreamType = new StreamType[2];
    arrayOfStreamType[0] = RTPUnicast;
    arrayOfStreamType[1] = RTPMulticast;
    ENUM$VALUES = arrayOfStreamType;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.StreamType
 * JD-Core Version:    0.6.0
 */