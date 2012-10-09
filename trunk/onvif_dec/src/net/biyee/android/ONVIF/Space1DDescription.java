package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class Space1DDescription
{
  public String URI;
  public FloatRange XRange;

  public String toString()
  {
    return String.valueOf(this.XRange);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.Space1DDescription
 * JD-Core Version:    0.6.0
 */