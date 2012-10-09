package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;

public class EnvelopGetCapabilitiesResponse extends Envelope
{

  @Element(name="Body")
  public BodyGetCapabilitiesResponse BodyGetCapabilitiesResponse;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.EnvelopGetCapabilitiesResponse
 * JD-Core Version:    0.6.0
 */