package net.biyee.android.ONVIF;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class VideoEncoderConfiguration
{

  @Element(required=false)
  public VideoEncoding Encoding;

  @Element(required=false)
  public H264Configuration H264;

  @Element(required=false)
  public MulticastConfiguration Multicast;
  public String Name;
  public float Quality;

  @Element(required=false)
  public VideoRateControl RateControl;
  public VideoResolution Resolution;
  public String SessionTimeout;
  public Integer UseCount;

  @Attribute
  public String token;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.VideoEncoderConfiguration
 * JD-Core Version:    0.6.0
 */