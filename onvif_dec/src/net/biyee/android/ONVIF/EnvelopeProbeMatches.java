package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class EnvelopeProbeMatches extends Envelope
{

  @Element(name="Body")
  public BodyProbeMatches BodyProbeMatches;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.EnvelopeProbeMatches
 * JD-Core Version:    0.6.0
 */