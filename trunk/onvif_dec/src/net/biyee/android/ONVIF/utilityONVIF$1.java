package net.biyee.android.ONVIF;

import java.io.IOException;
import net.biyee.android.utility;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

class utilityONVIF$1 extends Thread
{
  public void run()
  {
    try
    {
      this.val$androidHttpTransport.call(this.val$sSoapAction, this.val$envelope);
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        utility.logd("callSOAPService", localIOException.getMessage());
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      while (true)
        utility.logd("callSOAPService", localXmlPullParserException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.utilityONVIF.1
 * JD-Core Version:    0.6.0
 */