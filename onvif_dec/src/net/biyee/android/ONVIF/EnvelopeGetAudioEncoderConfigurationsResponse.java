package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class EnvelopeGetAudioEncoderConfigurationsResponse
{

  @Element(name="Body")
  public BodyGetAudioEncoderConfigurationsResponse BodyGetAudioEncoderConfigurationsResponse;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.EnvelopeGetAudioEncoderConfigurationsResponse
 * JD-Core Version:    0.6.0
 */