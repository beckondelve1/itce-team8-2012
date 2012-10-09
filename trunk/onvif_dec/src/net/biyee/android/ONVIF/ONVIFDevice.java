package net.biyee.android.ONVIF;

import java.util.List;
import java.util.UUID;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class ONVIFDevice
{

  @Element(required=false)
  public Capabilities Capabilities;

  @Element(required=false)
  public GetDeviceInformationResponse di;

  @ElementList(required=false)
  public List<AudioEncoderConfiguration> listAudioEncoderConfigurations;

  @ElementList(required=false)
  public List<PTZConfiguration> listPTZConfigurations;

  @ElementList(required=false)
  public List<Profiles> listProfiles;

  @ElementList(required=false)
  public List<VideoEncoderConfiguration> listVideoEncoderConfigurations;

  @Element
  public String sAddress;

  @Element(required=false)
  public String sError;

  @Element
  public String sName;

  @Element(required=false)
  public String sPassword;

  @Element(required=false)
  public String sUserName;

  @Element
  public String uid = UUID.randomUUID().toString();
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.ONVIFDevice
 * JD-Core Version:    0.6.0
 */