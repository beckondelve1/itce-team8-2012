package net.biyee.android.ONVIF;

public enum AudioEncoding
{
  static
  {
    AAC = new AudioEncoding("AAC", 2);
    AudioEncoding[] arrayOfAudioEncoding = new AudioEncoding[3];
    arrayOfAudioEncoding[0] = G711;
    arrayOfAudioEncoding[1] = G726;
    arrayOfAudioEncoding[2] = AAC;
    ENUM$VALUES = arrayOfAudioEncoding;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.AudioEncoding
 * JD-Core Version:    0.6.0
 */