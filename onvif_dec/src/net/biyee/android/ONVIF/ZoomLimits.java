package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class ZoomLimits
{
  public Space1DDescription Range;

  public String toString()
  {
    return this.Range.toString();
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.ZoomLimits
 * JD-Core Version:    0.6.0
 */