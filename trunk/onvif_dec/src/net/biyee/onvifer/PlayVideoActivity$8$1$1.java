package net.biyee.onvifer;

import android.app.ProgressDialog;
import java.io.File;
import net.biyee.android.ONVIF.GetStreamUriResponse;
import net.biyee.android.ONVIF.MediaUri;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.StreamInfo;
import net.biyee.android.utility;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

class PlayVideoActivity$8$1$1
  implements Runnable
{
  public void run()
  {
    if (this.val$GetStreamUriResponse != null)
      PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).sURLVideo = this.val$GetStreamUriResponse.MediaUri.Uri;
    while (true)
    {
      try
      {
        PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).si = new StreamInfo();
        PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).si.sProfileToken = PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).p.token;
        PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).si.sStreamURL = PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).sURLVideo;
        PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).si.sAddress = PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).od.sAddress;
        PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).si.sUserName = PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).od.sUserName;
        PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).si.sPassword = PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).od.sPassword;
        PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).si.sUID = this.val$sUID;
        if (PlayVideoActivity.access$1(PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2))) != null)
          continue;
        File localFile = new File(PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).getDir("StreamingInfo", 0), PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2))._sURLFileName);
        new Persister().write(PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).si, localFile);
        PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).playVideo();
        return;
      }
      catch (Exception localException)
      {
        utility.logMessageAsync(PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)), "Error in writing stream URL file: " + localException.getMessage());
        continue;
      }
      PlayVideoActivity.8.access$2(PlayVideoActivity.8.1.access$0(this.this$2)).pd.setMessage("Unable to retrieve streaming URI");
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.8.1.1
 * JD-Core Version:    0.6.0
 */