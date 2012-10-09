package net.biyee.onvifer;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import java.util.List;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.ONVIF.ListDevice;

class OnviferActivity$8$1
  implements Runnable
{
  public void run()
  {
    int i = -1;
    for (int j = 0; ; j++)
    {
      if (j >= OnviferActivity.8.access$0(this.this$1).listDevices.listDevices.size())
      {
        if (i > -1)
        {
          View localView = ((GridView)OnviferActivity.8.access$0(this.this$1).findViewById(2131296258)).getChildAt(i);
          ((ImageView)localView.findViewById(2131296270)).setImageBitmap(this.val$bm);
          ((TextView)localView.findViewById(2131296273)).setText(new Date().toString());
        }
        return;
      }
      if (!((DeviceInfo)OnviferActivity.8.access$0(this.this$1).listDevices.listDevices.get(j)).uid.equals(this.val$diCurrent.uid))
        continue;
      i = j;
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.8.1
 * JD-Core Version:    0.6.0
 */