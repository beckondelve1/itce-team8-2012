package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class EnvelopGetStreamUriResponse
{

  @Element(name="Body")
  public BodyGetStreamUriResponse BodyGetStreamUriResponse;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.EnvelopGetStreamUriResponse
 * JD-Core Version:    0.6.0
 */