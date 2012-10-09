package net.biyee.android.ONVIF;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class Vector2D
{

  @Attribute
  public String space;

  @Attribute
  public float x;

  @Attribute
  public float y;

  public String toString()
  {
    return String.valueOf(this.x) + "," + String.valueOf(this.y);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.Vector2D
 * JD-Core Version:    0.6.0
 */