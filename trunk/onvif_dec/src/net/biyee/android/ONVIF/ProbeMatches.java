package net.biyee.android.ONVIF;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://schemas.xmlsoap.org/ws/2005/04/discovery")
@Root(strict=false)
public class ProbeMatches
{

  @ElementList(entry="ProbeMatch", inline=true)
  public List<ProbeMatch> listProbeMatches;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.ProbeMatches
 * JD-Core Version:    0.6.0
 */