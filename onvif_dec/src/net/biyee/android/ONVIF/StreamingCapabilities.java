package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://www.onvif.org/ver10/schema")
@Root(strict=false)
public class StreamingCapabilities
{

  @Element(required=false)
  public Boolean NonAggregateControl;

  @Element
  public Boolean RTPMulticast;

  @Element
  public Boolean RTP_RTSP_TCP;

  @Element
  public Boolean RTP_TCP;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.StreamingCapabilities
 * JD-Core Version:    0.6.0
 */