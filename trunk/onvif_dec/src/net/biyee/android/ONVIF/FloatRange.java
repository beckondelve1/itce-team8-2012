package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class FloatRange
{
  public float Max;
  public float Min;

  public String toString()
  {
    return this.Min + "-" + this.Max;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.FloatRange
 * JD-Core Version:    0.6.0
 */