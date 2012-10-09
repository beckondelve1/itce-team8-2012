package net.biyee.onvifer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import java.net.URISyntaxException;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

class UpdateWidgetService$1 extends AsyncTask<Void, Void, Bitmap>
{
  protected Bitmap doInBackground(Void[] paramArrayOfVoid)
  {
    Object localObject = null;
    try
    {
      Bitmap localBitmap = utility.loadBitmap(utilityONVIF.getURLSnapshot(this.val$od, this.val$pOptimal), this.val$od.sUserName, this.val$od.sPassword);
      localObject = localBitmap;
      label35: return localObject;
    }
    catch (Exception localException)
    {
      break label35;
    }
    catch (URISyntaxException localURISyntaxException)
    {
      break label35;
    }
  }

  protected void onPostExecute(Bitmap paramBitmap)
  {
    try
    {
      Bitmap localBitmap = Bitmap.createScaledBitmap(paramBitmap, this.val$iWidgetWidth, paramBitmap.getHeight() * this.val$iWidgetWidth / paramBitmap.getWidth(), true);
      this.val$views.setImageViewBitmap(2131296270, localBitmap);
      localIntent = new Intent(this.val$context, PlayVideoActivity.class);
      if ("H.264".equals(this.val$context.getSharedPreferences("default_streaming_mode", 0).getString(this.val$sUID, "JPEG")))
      {
        localIntent.putExtra("param", this.val$sUID + ", H.264, UDP");
        PendingIntent localPendingIntent = PendingIntent.getActivity(this.val$context, this.val$appWidgetId, localIntent, 268435456);
        this.val$views.setOnClickPendingIntent(2131296308, localPendingIntent);
        this.val$appWidgetManager.updateAppWidget(this.val$appWidgetId, this.val$views);
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Intent localIntent;
        utility.logMessageAsync(this.val$context, "Widget error: " + localException.getMessage());
        continue;
        localIntent.putExtra("param", this.val$sUID + ", JPEG, HTTP");
      }
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.UpdateWidgetService.1
 * JD-Core Version:    0.6.0
 */