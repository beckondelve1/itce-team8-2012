package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Namespace(reference="http://www.onvif.org/ver10/schema")
@Root(strict=false)
public class PTZMoveStatus
{

  @Element(required=false)
  public MoveStatus PanTilt;

  @Element(required=false)
  public boolean PanTiltSpecified;

  @Element(required=false)
  public MoveStatus Zoom;

  @Element(required=false)
  public boolean ZoomSpecified;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.PTZMoveStatus
 * JD-Core Version:    0.6.0
 */