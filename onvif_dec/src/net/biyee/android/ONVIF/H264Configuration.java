package net.biyee.android.ONVIF;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class H264Configuration
{
  int GovLength;
  H264Profile H264Profile;

  public String toString()
  {
    return "Profile: " + this.H264Profile + ", Gov Length: " + this.GovLength;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.H264Configuration
 * JD-Core Version:    0.6.0
 */