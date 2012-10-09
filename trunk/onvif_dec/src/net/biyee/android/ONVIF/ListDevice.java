package net.biyee.android.ONVIF;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class ListDevice
{

  @ElementList(entry="DeviceInfo", inline=true, required=false)
  public List<DeviceInfo> listDevices = new ArrayList();
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.ListDevice
 * JD-Core Version:    0.6.0
 */