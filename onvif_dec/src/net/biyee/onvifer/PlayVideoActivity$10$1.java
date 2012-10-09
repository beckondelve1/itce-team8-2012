package net.biyee.onvifer;

import android.view.View;
import android.widget.TextView;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.StreamInfo;
import net.biyee.android.ONVIF.VideoEncoderConfiguration;
import net.biyee.android.ONVIF.VideoResolution;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

class PlayVideoActivity$10$1
  implements Runnable
{
  public void run()
  {
    if (PlayVideoActivity.10.access$0(this.this$1).p.VideoEncoderConfiguration.Resolution.Width * PlayVideoActivity.10.access$0(this.this$1).p.VideoEncoderConfiguration.Resolution.Height > 345600)
      PlayVideoActivity.10.access$0(this.this$1).findViewById(2131296324).setVisibility(0);
    while (true)
    {
      if (PlayVideoActivity.10.access$0(this.this$1).p.AudioEncoderConfiguration != null)
      {
        PlayVideoActivity.10.access$0(this.this$1).findViewById(2131296321).setVisibility(0);
        label92: if (PlayVideoActivity.10.access$0(this.this$1).p.PTZConfiguration == null)
          break label230;
        PlayVideoActivity.10.access$0(this.this$1).findViewById(2131296311).setVisibility(0);
      }
      try
      {
        while (true)
        {
          ((TextView)PlayVideoActivity.10.access$0(this.this$1).findViewById(2131296317)).setText("Profile: " + utilityONVIF.findProfile(PlayVideoActivity.10.access$0(this.this$1).si.sProfileToken, PlayVideoActivity.10.access$0(this.this$1).od.listProfiles).Name);
          return;
          PlayVideoActivity.10.access$0(this.this$1).findViewById(2131296324).setVisibility(8);
          break;
          PlayVideoActivity.10.access$0(this.this$1).findViewById(2131296321).setVisibility(8);
          break label92;
          label230: PlayVideoActivity.10.access$0(this.this$1).findViewById(2131296311).setVisibility(8);
        }
      }
      catch (Exception localException)
      {
        while (true)
          utility.logd("Error in showing profile information: ", localException.getMessage());
      }
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.10.1
 * JD-Core Version:    0.6.0
 */