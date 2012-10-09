package net.biyee.onvifer;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.Date;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.utility;

class DeviceTileAdapter$1$1
  implements Runnable
{
  public void run()
  {
    try
    {
      ImageView localImageView = (ImageView)this.val$vGrid.findViewById(2131296270);
      File localFile = new File(DeviceTileAdapter.access$0(DeviceTileAdapter.1.access$0(this.this$1)).getDir("Snapshot", 0), this.val$diCurrent.uid + ".jpg");
      localImageView.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
      TextView localTextView = (TextView)this.val$vGrid.findViewById(2131296273);
      if (localFile.exists())
        localTextView.setText(new Date(localFile.lastModified()).toString());
      else
        localTextView.setText("");
    }
    catch (Exception localException)
    {
      utility.logMessageAsync(DeviceTileAdapter.access$0(DeviceTileAdapter.1.access$0(this.this$1)), "Error when updating a device tile snapshot from a saved image file: " + localException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DeviceTileAdapter.1.1
 * JD-Core Version:    0.6.0
 */