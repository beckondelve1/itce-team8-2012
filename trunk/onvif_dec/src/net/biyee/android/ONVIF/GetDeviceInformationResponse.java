package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Namespace(reference="http://www.onvif.org/ver10/device/wsdl")
@Root(strict=false)
public class GetDeviceInformationResponse
{

  @Element(required=false)
  public String FirmwareVersion;

  @Element(required=false)
  public String HardwareId;

  @Element(required=false)
  public String Manufacturer;

  @Element(required=false)
  public String Model = "N/A";

  @Element(required=false)
  public String SerialNumber;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.GetDeviceInformationResponse
 * JD-Core Version:    0.6.0
 */