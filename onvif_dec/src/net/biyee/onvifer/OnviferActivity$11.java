package net.biyee.onvifer;

import android.widget.GridView;
import java.util.List;
import net.biyee.android.DialogConfirmation;
import net.biyee.android.ONVIF.ListDevice;
import net.biyee.android.ONVIF.utilityONVIF;

class OnviferActivity$11 extends DialogConfirmation
{
  public void processDialogConfirmationResult(boolean paramBoolean)
  {
    super.processDialogConfirmationResult(paramBoolean);
    if (paramBoolean)
    {
      this.this$0.listDevices.listDevices.remove(this.this$0.iDeviceListPositionForContextMenu);
      utilityONVIF.saveListDevice(this.this$0, this.this$0.listDevices);
      OnviferActivity.access$1(this.this$0);
      OnviferActivity.access$0(this.this$0);
      ((GridView)this.this$0.findViewById(2131296258)).invalidateViews();
      this.this$0.cleanDir("SnapshotImage");
      this.this$0.cleanDir("SnapshotInfo");
      this.this$0.cleanDir("StreamingInfo");
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.11
 * JD-Core Version:    0.6.0
 */