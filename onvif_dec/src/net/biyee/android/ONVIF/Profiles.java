package net.biyee.android.ONVIF;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Namespace(reference="http://www.onvif.org/ver10/media/wsdl")
@Root(strict=false)
public class Profiles
{

  @Element(required=false)
  public AudioEncoderConfiguration AudioEncoderConfiguration;

  @Element(required=false)
  public AudioSourceConfiguration AudioSourceConfiguration;
  public String Name;

  @Element(required=false)
  public PTZConfiguration PTZConfiguration;

  @Element(required=false)
  public VideoEncoderConfiguration VideoEncoderConfiguration;

  @Element(required=false)
  public VideoSourceConfiguration VideoSourceConfiguration;

  @Attribute(required=false)
  public Boolean fixed;

  @Attribute
  public String token;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.Profiles
 * JD-Core Version:    0.6.0
 */