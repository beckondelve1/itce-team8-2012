package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://www.onvif.org/ver10/schema")
@Root(strict=false)
public class Network
{

  @Element(required=false)
  public Boolean DynDNS;

  @Element
  public Boolean IPFilter;

  @Element
  public Boolean IPVersion6;

  @Element
  public Boolean ZeroConfiguration;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.Network
 * JD-Core Version:    0.6.0
 */