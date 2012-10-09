package net.biyee.android.ONVIF;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://www.onvif.org/ver10/media/wsdl")
@Root(strict=false)
public class GetAudioEncoderConfigurationsResponse
{

  @ElementList(entry="Configurations", inline=true)
  public List<AudioEncoderConfiguration> listAudioEncoderConfiguration;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.GetAudioEncoderConfigurationsResponse
 * JD-Core Version:    0.6.0
 */