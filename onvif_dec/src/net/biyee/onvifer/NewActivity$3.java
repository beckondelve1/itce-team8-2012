package net.biyee.onvifer;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

class NewActivity$3
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    try
    {
      Intent localIntent = new Intent(this.this$0, DemoListActivity.class);
      this.this$0.startActivityForResult(localIntent, 101);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Log.d("Netowrk connection", localException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity.3
 * JD-Core Version:    0.6.0
 */