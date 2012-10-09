package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class Space2DDescription
{
  public String URI;
  public FloatRange XRange;
  public FloatRange YRange;

  public String toString()
  {
    return "x-range: " + this.XRange + ", y-range: " + this.YRange;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.Space2DDescription
 * JD-Core Version:    0.6.0
 */