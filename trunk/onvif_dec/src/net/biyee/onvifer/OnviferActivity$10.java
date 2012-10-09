package net.biyee.onvifer;

import android.view.View;
import android.widget.GridView;
import java.util.List;
import net.biyee.android.BoolClass;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.ONVIF.ListDevice;

class OnviferActivity$10 extends Thread
{
  public void run()
  {
    try
    {
      Thread.sleep(500L);
      i = 0;
      if (i >= this.this$0.listDevices.listDevices.size())
      {
        super.run();
        return;
      }
      GridView localGridView = (GridView)this.this$0.findViewById(2131296258);
      DeviceInfo localDeviceInfo = (DeviceInfo)this.this$0.listDevices.listDevices.get(i);
      View localView = localGridView.getChildAt(i);
      this.this$0.runOnUiThread(new OnviferActivity.10.1(this, localView, localDeviceInfo));
    }
    catch (InterruptedException localInterruptedException1)
    {
      try
      {
        while (true)
        {
          int i;
          Thread.sleep(200L);
          label98: if (this.this$0.bDisposed.bValue)
            continue;
          i++;
        }
        localInterruptedException1 = localInterruptedException1;
      }
      catch (InterruptedException localInterruptedException2)
      {
        break label98;
      }
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.10
 * JD-Core Version:    0.6.0
 */