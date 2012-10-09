package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class VideoRateControl
{
  public int BitrateLimit;
  public int EncodingInterval;
  public int FrameRateLimit;

  public String toString()
  {
    return "Frame Rate Limit:" + this.FrameRateLimit + ", EncodingI nterval:" + this.EncodingInterval + ", Bitrate Limit:" + this.BitrateLimit;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.VideoRateControl
 * JD-Core Version:    0.6.0
 */