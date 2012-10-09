package net.biyee.onvifer;

import android.view.View;
import android.widget.TextView;

class NewActivity$1$2
  implements Runnable
{
  public void run()
  {
    if (NewActivity.1.access$0(this.this$1)._iDeviceCount > 0)
    {
      NewActivity.1.access$0(this.this$1).findViewById(2131296290).setVisibility(0);
      ((TextView)NewActivity.1.access$0(this.this$1).findViewById(2131296289)).setText("Number of discovered devices:" + String.valueOf(NewActivity.1.access$0(this.this$1)._iDeviceCount));
    }
    while (true)
    {
      return;
      NewActivity.1.access$0(this.this$1).findViewById(2131296290).setVisibility(8);
      ((TextView)NewActivity.1.access$0(this.this$1).findViewById(2131296289)).setText("No device has been discovered on your local network.");
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity.1.2
 * JD-Core Version:    0.6.0
 */