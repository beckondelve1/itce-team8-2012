package net.biyee.onvifer;

import android.graphics.Bitmap;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.biyee.android.BoolClass;
import net.biyee.android.ONVIF.DeviceInfo;

class OnviferActivity$8 extends Thread
{
  public void run()
  {
    while (true)
    {
      if (this.this$0.bDisposed.bValue)
      {
        super.run();
        return;
      }
      DeviceInfo localDeviceInfo = (DeviceInfo)this.this$0.listDevicesForSnapshotUpdating.remove();
      this.this$0.listDevicesForSnapshotUpdating.add(localDeviceInfo);
      Bitmap localBitmap = this.this$0.updateSnapshot(localDeviceInfo.uid);
      if (localBitmap != null)
        this.this$0.runOnUiThread(new OnviferActivity.8.1(this, localDeviceInfo, localBitmap));
      try
      {
        Thread.sleep(2000L);
      }
      catch (InterruptedException localInterruptedException)
      {
      }
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.8
 * JD-Core Version:    0.6.0
 */