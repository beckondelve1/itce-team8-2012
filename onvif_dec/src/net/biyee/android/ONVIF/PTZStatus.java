package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class PTZStatus
{

  @Element(required=false)
  public PTZMoveStatus MoveStatus;

  @Element(required=false)
  public PTZVector Position;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.PTZStatus
 * JD-Core Version:    0.6.0
 */