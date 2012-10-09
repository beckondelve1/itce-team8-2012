package net.biyee.android.ONVIF;

import android.content.Context;
import net.biyee.android.utility;

class utilityONVIF$2 extends Thread
{
  public void run()
  {
    try
    {
      String str1 = this.val$sURL;
      String str2 = this.val$od.sUserName;
      String str3 = this.val$od.sPassword;
      SoapParam[] arrayOfSoapParam = new SoapParam[3];
      arrayOfSoapParam[0] = new SoapParam(this.val$ProfileToken, "ProfileToken");
      arrayOfSoapParam[1] = new SoapParam(this.val$Translation, "Translation");
      arrayOfSoapParam[2] = new SoapParam(this.val$Speed, "Speed");
      utilityONVIF.callSOAPServiceEx(null, "http://www.onvif.org/ver20/ptz/wsdl", "RelativeMove", str1, str2, str3, arrayOfSoapParam);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        utility.logMessageAsync(this.val$context, "Zoom() error: " + localException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.utilityONVIF.2
 * JD-Core Version:    0.6.0
 */