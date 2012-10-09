package net.biyee.onvifer;

import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.StreamInfo;
import net.biyee.android.ONVIF.utilityONVIF;

class PlayVideoActivity$10
  implements Runnable
{
  public void run()
  {
    try
    {
      Thread.sleep(2000L);
      label6: this.this$0.od = utilityONVIF.getONVIFDevice(this.this$0, this.this$0.si.sUID);
      this.this$0.p = utilityONVIF.findProfile(this.this$0.si.sProfileToken, this.this$0.od.listProfiles);
      if (this.this$0.p != null)
        this.this$0.runOnUiThread(new PlayVideoActivity.10.1(this));
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label6;
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.10
 * JD-Core Version:    0.6.0
 */