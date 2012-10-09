package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Namespace(reference="http://www.onvif.org/ver10/schema")
@Root(strict=false)
public class Capabilities
{
  public Device Device;

  @Element(required=false)
  public Events Events;

  @Element(required=false)
  public Imaging Imaging;

  @Element
  public Media Media;

  @Element(required=false)
  public PTZ PTZ;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.Capabilities
 * JD-Core Version:    0.6.0
 */