package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class IPAddress
{

  @Element(required=false)
  public String IPv4Address;

  @Element(required=false)
  public String IPv6Address;
  public IPType Type;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.IPAddress
 * JD-Core Version:    0.6.0
 */