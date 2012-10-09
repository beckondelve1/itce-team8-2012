package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class MulticastConfiguration
{
  public IPAddress Address = new IPAddress();
  public boolean AutoStart;
  public int Port;
  public int TTL;

  public String toString()
  {
    String str = "IP Address:";
    switch ($SWITCH_TABLE$net$biyee$android$ONVIF$IPType()[this.Address.Type.ordinal()])
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(str)).append(", Port: ").append(this.Port).toString())).append(", TTL: ").append(this.TTL).toString() + ", Auto Start: " + this.AutoStart;
      str = str + "IPv4: " + this.Address.IPv4Address;
      continue;
      str = str + "IPv6: " + this.Address.IPv6Address;
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.MulticastConfiguration
 * JD-Core Version:    0.6.0
 */