package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class EnvelopGetProfilesResponse extends Envelope
{

  @Element(name="Body")
  public BodyGetProfilesResponse BodyGetProfilesResponse;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.EnvelopGetProfilesResponse
 * JD-Core Version:    0.6.0
 */