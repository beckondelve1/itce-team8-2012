package net.biyee.android.ONVIF;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://www.onvif.org/ver20/ptz/wsdl")
@Root(strict=false)
public class GetPTZConfigurationsResponse
{

  @ElementList(entry="PTZConfiguration", inline=true)
  public List<PTZConfiguration> listPTZConfiguration;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.GetPTZConfigurationsResponse
 * JD-Core Version:    0.6.0
 */