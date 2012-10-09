package net.biyee.onvifer;

import net.biyee.android.DialogConfirmation;
import net.biyee.android.utility;

class NewActivity$1$3 extends DialogConfirmation
{
  public void processDialogConfirmationResult(boolean paramBoolean)
  {
    super.processDialogConfirmationResult(paramBoolean);
    if (paramBoolean)
      utility.logMessageAsync(NewActivity.1.access$0(this.this$1), "Error in discovery.  Response: " + NewActivity.1.access$0(this.this$1).sResponseDiscovery);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity.1.3
 * JD-Core Version:    0.6.0
 */