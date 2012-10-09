package net.biyee.onvifer;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.List;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.ONVIF.ListDevice;
import net.biyee.android.ONVIF.utilityONVIF;

class OnviferAppWedgetConfigure$1
  implements AdapterView.OnItemClickListener
{
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Bundle localBundle = this.this$0.getIntent().getExtras();
    if (localBundle != null)
      this.this$0.mAppWidgetId = localBundle.getInt("appWidgetId", 0);
    if (this.this$0.mAppWidgetId == 0)
      this.this$0.finish();
    String str = ((DeviceInfo)utilityONVIF.getListDevice(this.this$0).listDevices.get(paramInt)).uid;
    SharedPreferences.Editor localEditor = this.this$0.getSharedPreferences("app_widget_device_uid", 0).edit();
    localEditor.putString(String.valueOf(this.this$0.mAppWidgetId), str);
    localEditor.commit();
    AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(this.this$0);
    OnviferAppWidgetProvider.updateAppWidget(this.this$0, localAppWidgetManager, this.this$0.mAppWidgetId);
    Intent localIntent = new Intent();
    localIntent.putExtra("appWidgetId", this.this$0.mAppWidgetId);
    this.this$0.setResult(-1, localIntent);
    this.this$0.finish();
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferAppWedgetConfigure.1
 * JD-Core Version:    0.6.0
 */