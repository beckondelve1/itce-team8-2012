package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://www.onvif.org/ver10/device/wsdl")
@Root(strict=false)
public class GetCapabilitiesResponse
{

  @Element
  public Capabilities Capabilities;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.GetCapabilitiesResponse
 * JD-Core Version:    0.6.0
 */