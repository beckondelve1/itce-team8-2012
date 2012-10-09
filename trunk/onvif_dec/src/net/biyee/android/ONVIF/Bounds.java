package net.biyee.android.ONVIF;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class Bounds
{

  @Attribute
  public int height;

  @Attribute
  public int width;

  @Attribute
  public int x;

  @Attribute
  public int y;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.Bounds
 * JD-Core Version:    0.6.0
 */