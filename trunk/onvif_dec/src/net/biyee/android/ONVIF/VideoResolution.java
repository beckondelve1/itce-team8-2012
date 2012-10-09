package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class VideoResolution
{
  public int Height;
  public int Width;

  public String toString()
  {
    return String.valueOf(this.Width) + "x" + String.valueOf(this.Height);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.VideoResolution
 * JD-Core Version:    0.6.0
 */