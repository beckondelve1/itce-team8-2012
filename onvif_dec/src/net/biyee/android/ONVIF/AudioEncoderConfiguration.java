package net.biyee.android.ONVIF;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class AudioEncoderConfiguration
{
  public int Bitrate;
  public AudioEncoding Encoding;

  @Element(required=false)
  public MulticastConfiguration Multicast;
  public String Name;
  public int SampleRate;
  public String SessionTimeout;
  public Integer UseCount;

  @Attribute
  public String token;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.AudioEncoderConfiguration
 * JD-Core Version:    0.6.0
 */