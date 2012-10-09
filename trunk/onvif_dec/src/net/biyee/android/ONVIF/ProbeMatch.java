package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://schemas.xmlsoap.org/ws/2005/04/discovery")
@Root(strict=false)
public class ProbeMatch
{

  @Element(required=false)
  @Namespace(reference="http://schemas.xmlsoap.org/ws/2004/08/addressing")
  public EndpointReference EndpointReference;

  @Element(required=false)
  public String MetadataVersion;

  @Element
  public String Scopes;

  @Element(required=false)
  public String Types;

  @Element
  public String XAddrs;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.ProbeMatch
 * JD-Core Version:    0.6.0
 */