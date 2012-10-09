package net.biyee.onvifer;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;
import java.util.List;
import net.biyee.android.BoolClass;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.ONVIF.ListDevice;

class DeviceTileAdapter$1 extends Thread
{
  public void run()
  {
    try
    {
      Thread.sleep(500L);
      i = 0;
      if (i >= this.this$0.listDevice.listDevices.size())
      {
        super.run();
        return;
      }
      GridView localGridView = (GridView)DeviceTileAdapter.access$0(this.this$0).findViewById(2131296258);
      DeviceInfo localDeviceInfo = (DeviceInfo)this.this$0.listDevice.listDevices.get(i);
      View localView = localGridView.getChildAt(i);
      DeviceTileAdapter.access$0(this.this$0).runOnUiThread(new DeviceTileAdapter.1.1(this, localView, localDeviceInfo));
    }
    catch (InterruptedException localInterruptedException1)
    {
      try
      {
        while (true)
        {
          int i;
          Thread.sleep(200L);
          label104: if (((OnviferActivity)DeviceTileAdapter.access$0(this.this$0)).bDisposed.bValue)
            continue;
          i++;
        }
        localInterruptedException1 = localInterruptedException1;
      }
      catch (InterruptedException localInterruptedException2)
      {
        break label104;
      }
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DeviceTileAdapter.1
 * JD-Core Version:    0.6.0
 */