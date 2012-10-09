package net.biyee.android.ONVIF;

public enum TransportProtocol
{
  static
  {
    TCP = new TransportProtocol("TCP", 1);
    RTSP = new TransportProtocol("RTSP", 2);
    HTTP = new TransportProtocol("HTTP", 3);
    TransportProtocol[] arrayOfTransportProtocol = new TransportProtocol[4];
    arrayOfTransportProtocol[0] = UDP;
    arrayOfTransportProtocol[1] = TCP;
    arrayOfTransportProtocol[2] = RTSP;
    arrayOfTransportProtocol[3] = HTTP;
    ENUM$VALUES = arrayOfTransportProtocol;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.TransportProtocol
 * JD-Core Version:    0.6.0
 */