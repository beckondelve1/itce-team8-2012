package net.biyee.android.ONVIF;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(strict=false)
public class AudioSourceConfiguration
{
  public String Name;
  public String SourceToken;
  public int UseCount;

  @Attribute
  public String token;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.AudioSourceConfiguration
 * JD-Core Version:    0.6.0
 */