package net.biyee.onvifer;

import android.net.http.AndroidHttpClient;
import net.biyee.android.utility;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

class DemoListActivity$4$3 extends Thread
{
  public void run()
  {
    try
    {
      AndroidHttpClient localAndroidHttpClient = AndroidHttpClient.newInstance("IPCENTCOM");
      String str = "?$filter=Application_Type eq 'Network Video Transmitters'".replace(" ", "%20");
      HttpGet localHttpGet = new HttpGet("https://www.ipcent.com/Video.svc/tblONVIFProducts/$count" + str);
      localHttpGet.addHeader("password", "ipcentcom");
      int i = Integer.parseInt(EntityUtils.toString(localAndroidHttpClient.execute(localHttpGet).getEntity()));
      localAndroidHttpClient.close();
      if (!DemoListActivity.4.access$2(this.this$1).bDisposed)
        DemoListActivity.4.access$2(this.this$1).runOnUiThread(new DemoListActivity.4.3.1(this, i));
      return;
    }
    catch (Exception localException)
    {
      while (true)
        utility.logMessageAsync(DemoListActivity.4.access$2(this.this$1), "Error in retrieving the number of ONVIF NVT's: " + localException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DemoListActivity.4.3
 * JD-Core Version:    0.6.0
 */