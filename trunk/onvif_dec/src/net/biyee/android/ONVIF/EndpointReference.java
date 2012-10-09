package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://schemas.xmlsoap.org/ws/2004/08/addressing")
@Root(strict=false)
public class EndpointReference
{

  @Element
  public String Address;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.EndpointReference
 * JD-Core Version:    0.6.0
 */