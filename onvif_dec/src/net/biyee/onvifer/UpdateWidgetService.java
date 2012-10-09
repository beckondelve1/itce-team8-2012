package net.biyee.onvifer;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RemoteViews;
import java.util.Date;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.VideoEncoding;
import net.biyee.android.ONVIF.utilityONVIF;

public class UpdateWidgetService extends Service
{
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
    Context localContext = getApplicationContext();
    SharedPreferences localSharedPreferences = localContext.getSharedPreferences("app_widget_device_uid", 0);
    int i = paramIntent.getIntExtra("WidgetID", -1);
    String str = localSharedPreferences.getString(String.valueOf(i), "");
    if (!"".equals(str));
    try
    {
      RemoteViews localRemoteViews = new RemoteViews(localContext.getPackageName(), 2130903050);
      ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(localContext, str);
      Resources localResources = getResources();
      float f = localResources.getDisplayMetrics().density;
      int j = (int)(f * localResources.getDimension(2131165184));
      Profiles localProfiles = utilityONVIF.findOptimalProfile(j * (int)(f * localResources.getDimension(2131165185)), VideoEncoding.JPEG, localONVIFDevice.listProfiles);
      localRemoteViews.setTextViewText(2131296326, localONVIFDevice.sName);
      localRemoteViews.setTextViewText(2131296327, new Date().toString());
      localRemoteViews.setViewVisibility(2131296328, 8);
      new UpdateWidgetService.1(this, localONVIFDevice, localProfiles, j, localRemoteViews, localContext, str, i, localAppWidgetManager).execute(new Void[0]);
      stopSelf();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Log.d("Widget update error: ", localException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.UpdateWidgetService
 * JD-Core Version:    0.6.0
 */