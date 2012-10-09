package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class DeviceInfo
{

  @Element
  public String sAddress;

  @Element(required=false)
  public String sFeatures = "";

  @Element
  public String sModel;

  @Element
  public String sName;

  @Element(required=false)
  public String sPassword;

  @Element(required=false)
  public String sUserName;

  @Element
  public String uid;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.DeviceInfo
 * JD-Core Version:    0.6.0
 */