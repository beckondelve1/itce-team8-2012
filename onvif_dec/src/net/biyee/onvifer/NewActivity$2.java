package net.biyee.onvifer;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import net.biyee.android.utility;

class NewActivity$2
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    int i = 1;
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.this$0.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        if ((localNetworkInfo.getType() != i) && (localNetworkInfo.getType() != 9))
          i = 0;
        if ((i | "sdk".equals(Build.PRODUCT)) != 0)
        {
          Intent localIntent = new Intent(this.this$0, DiscoverActivity.class);
          this.this$0.startActivityForResult(localIntent, 100);
          return;
        }
      }
      utility.ShowMessage(this.this$0, "Please connect to a local network first.");
    }
    catch (Exception localException)
    {
      Log.d("Netowrk connection", localException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity.2
 * JD-Core Version:    0.6.0
 */