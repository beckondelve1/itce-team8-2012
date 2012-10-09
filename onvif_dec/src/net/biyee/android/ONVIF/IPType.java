package net.biyee.android.ONVIF;

public enum IPType
{
  static
  {
    IPType[] arrayOfIPType = new IPType[2];
    arrayOfIPType[0] = IPv4;
    arrayOfIPType[1] = IPv6;
    ENUM$VALUES = arrayOfIPType;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.IPType
 * JD-Core Version:    0.6.0
 */