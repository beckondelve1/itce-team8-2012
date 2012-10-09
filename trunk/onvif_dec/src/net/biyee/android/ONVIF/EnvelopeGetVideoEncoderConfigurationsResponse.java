package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class EnvelopeGetVideoEncoderConfigurationsResponse
{

  @Element(name="Body")
  public BodyGetVideoEncoderConfigurationsResponse BodyGetGetVideoEncoderConfigurationsResponse;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.EnvelopeGetVideoEncoderConfigurationsResponse
 * JD-Core Version:    0.6.0
 */