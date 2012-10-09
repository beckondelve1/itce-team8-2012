package net.biyee.android.ONVIF;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://www.onvif.org/ver10/media/wsdl")
@Root(strict=false)
public class GetProfilesResponse
{

  @ElementList(entry="Profiles", inline=true)
  public List<Profiles> listProfiles;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.GetProfilesResponse
 * JD-Core Version:    0.6.0
 */