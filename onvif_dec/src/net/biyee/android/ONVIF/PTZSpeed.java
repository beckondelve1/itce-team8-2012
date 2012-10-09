package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class PTZSpeed
{

  @Element(required=false)
  public Vector2D PanTilt;

  @Element(required=false)
  public Vector1D Zoom;

  public String toString()
  {
    return "Pan/Tilt (" + this.PanTilt + "), Zoom (" + this.Zoom + ")";
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.PTZSpeed
 * JD-Core Version:    0.6.0
 */