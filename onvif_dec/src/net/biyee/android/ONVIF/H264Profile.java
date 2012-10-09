package net.biyee.android.ONVIF;

public enum H264Profile
{
  static
  {
    Extended = new H264Profile("Extended", 2);
    High = new H264Profile("High", 3);
    H264Profile[] arrayOfH264Profile = new H264Profile[4];
    arrayOfH264Profile[0] = Baseline;
    arrayOfH264Profile[1] = Main;
    arrayOfH264Profile[2] = Extended;
    arrayOfH264Profile[3] = High;
    ENUM$VALUES = arrayOfH264Profile;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.H264Profile
 * JD-Core Version:    0.6.0
 */