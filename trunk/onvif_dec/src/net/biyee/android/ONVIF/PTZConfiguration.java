package net.biyee.android.ONVIF;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class PTZConfiguration
{

  @Element(required=false)
  public String DefaultAbsolutePantTiltPositionSpace;

  @Element(required=false)
  public String DefaultAbsoluteZoomPositionSpace;

  @Element(required=false)
  public String DefaultContinuousPanTiltVelocitySpace;

  @Element(required=false)
  public String DefaultContinuousZoomVelocitySpace;

  @Element(required=false)
  public PTZSpeed DefaultPTZSpeed;

  @Element(required=false)
  public String DefaultPTZTimeout;

  @Element(required=false)
  public String DefaultRelativePanTiltTranslationSpace;

  @Element(required=false)
  public String DefaultRelativeZoomTranslationSpace;
  public String Name;
  public String NodeToken;

  @Element(required=false)
  public PanTiltLimits PanTiltLimits;
  public Integer UseCount;

  @Element(required=false)
  public ZoomLimits ZoomLimits;

  @Attribute
  public String token;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.PTZConfiguration
 * JD-Core Version:    0.6.0
 */