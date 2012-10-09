package net.biyee.android.ONVIF;

public enum VideoEncoding
{
  static
  {
    H264 = new VideoEncoding("H264", 2);
    NA = new VideoEncoding("NA", 3);
    VideoEncoding[] arrayOfVideoEncoding = new VideoEncoding[4];
    arrayOfVideoEncoding[0] = JPEG;
    arrayOfVideoEncoding[1] = MPEG4;
    arrayOfVideoEncoding[2] = H264;
    arrayOfVideoEncoding[3] = NA;
    ENUM$VALUES = arrayOfVideoEncoding;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.VideoEncoding
 * JD-Core Version:    0.6.0
 */