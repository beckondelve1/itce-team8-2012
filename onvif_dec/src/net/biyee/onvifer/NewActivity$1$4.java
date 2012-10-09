package net.biyee.onvifer;

import android.widget.TextView;
import net.biyee.android.utility;

class NewActivity$1$4
  implements Runnable
{
  public void run()
  {
    ((TextView)NewActivity.1.access$0(this.this$1).findViewById(2131296289)).setText("Discovery error: " + this.val$ex.getMessage());
    utility.logMessageAsync(NewActivity.1.access$0(this.this$1), "Error in discovery: " + this.val$ex.getMessage());
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity.1.4
 * JD-Core Version:    0.6.0
 */