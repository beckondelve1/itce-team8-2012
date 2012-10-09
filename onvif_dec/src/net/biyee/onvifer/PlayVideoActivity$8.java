package net.biyee.onvifer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Button;
import java.io.File;
import java.io.FileNotFoundException;
import net.biyee.android.ONVIF.StreamInfo;
import net.biyee.android.utility;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

class PlayVideoActivity$8 extends AsyncTask<Void, Void, Void>
{
  protected Void doInBackground(Void[] paramArrayOfVoid)
  {
    if (PlayVideoActivity.access$1(this.this$0) == null)
      this.this$0._sURLFileName = (this.val$sUID + "_streaming_info_" + this.this$0.sCodec + ".xml");
    try
    {
      File localFile = new File(this.this$0.getDir("StreamingInfo", 0), this.this$0._sURLFileName);
      Persister localPersister = new Persister();
      this.this$0.si = ((StreamInfo)localPersister.read(StreamInfo.class, localFile));
      label106: return null;
    }
    catch (Exception localException)
    {
      while (true)
        utility.logMessageAsync(this.this$0, "Error in reading video stream URL file: " + localException.getMessage());
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      break label106;
    }
  }

  protected void onPostExecute(Void paramVoid)
  {
    if (this.this$0.si == null)
    {
      if (PlayVideoActivity.access$1(this.this$0) == null)
        this.this$0.pd.setMessage("First time streaming takes a longer time to start.  Retrieving streaming URI...");
      new PlayVideoActivity.8.1(this, this.val$sUID, this.val$btModeSwitch).start();
    }
    while (true)
    {
      return;
      this.this$0.playVideo();
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.8
 * JD-Core Version:    0.6.0
 */