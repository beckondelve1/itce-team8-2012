package net.biyee.onvifer;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import net.biyee.android.BoolClass;

class PlayVideoActivity$6 extends AsyncTask<Void, Void, Void>
{
  protected Void doInBackground(Void[] paramArrayOfVoid)
  {
    while (true)
    {
      if (this.this$0.bDisposed.bValue)
        return null;
      try
      {
        publishProgress(new Void[0]);
        Thread.sleep(100L);
      }
      catch (Exception localException)
      {
        Log.d("publishProgress", localException.getMessage());
      }
    }
  }

  protected void onProgressUpdate(Void[] paramArrayOfVoid)
  {
    if (System.currentTimeMillis() > this.this$0.lTimeActionBar)
      this.this$0.findViewById(2131296310).setVisibility(8);
    while (true)
    {
      return;
      this.this$0.findViewById(2131296310).setVisibility(0);
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.6
 * JD-Core Version:    0.6.0
 */