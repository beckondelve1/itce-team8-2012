package net.biyee.onvifer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.List;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.ONVIF.ListDevice;
import net.biyee.android.ONVIF.utilityONVIF;

class OnviferActivity$1
  implements AdapterView.OnItemClickListener
{
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Intent localIntent = new Intent(this.this$0, PlayVideoActivity.class);
    String str = ((DeviceInfo)utilityONVIF.getListDevice(this.this$0).listDevices.get(paramInt)).uid;
    if ("H.264".equals(this.this$0.getSharedPreferences("default_streaming_mode", 0).getString(str, "JPEG")))
      localIntent.putExtra("param", str + ", H.264, UDP");
    while (true)
    {
      this.this$0.startActivity(localIntent);
      return;
      localIntent.putExtra("param", str + ", JPEG, HTTP");
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.1
 * JD-Core Version:    0.6.0
 */