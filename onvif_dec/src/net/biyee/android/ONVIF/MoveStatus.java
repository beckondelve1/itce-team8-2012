package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public enum MoveStatus
{
  static
  {
    MoveStatus[] arrayOfMoveStatus = new MoveStatus[3];
    arrayOfMoveStatus[0] = IDLE;
    arrayOfMoveStatus[1] = MOVING;
    arrayOfMoveStatus[2] = UNKNOWN;
    ENUM$VALUES = arrayOfMoveStatus;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.MoveStatus
 * JD-Core Version:    0.6.0
 */