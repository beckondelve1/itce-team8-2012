package net.biyee.android.ONVIF;

import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://www.onvif.org/ver10/schema")
@Root(strict=false)
public class System
{

  @Element
  public Boolean DiscoveryBye;

  @Element
  public Boolean DiscoveryResolve;

  @Element
  public Boolean FirmwareUpgrade;

  @Element
  public Boolean RemoteDiscovery;

  @Element
  public Boolean SystemBackup;

  @Element
  public Boolean SystemLogging;

  @ElementList(entry="SupportedVersions", inline=true)
  public List<SupportedVersions> listSupportedVersions;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.System
 * JD-Core Version:    0.6.0
 */