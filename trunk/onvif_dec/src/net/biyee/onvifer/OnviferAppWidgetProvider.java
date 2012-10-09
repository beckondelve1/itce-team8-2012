package net.biyee.onvifer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class OnviferAppWidgetProvider extends AppWidgetProvider
{
  static void updateAppWidget(Context paramContext, AppWidgetManager paramAppWidgetManager, int paramInt)
  {
    Intent localIntent = new Intent(paramContext.getApplicationContext(), UpdateWidgetService.class);
    localIntent.putExtra("WidgetID", paramInt);
    paramContext.startService(localIntent);
  }

  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt.length)
      {
        super.onUpdate(paramContext, paramAppWidgetManager, paramArrayOfInt);
        return;
      }
      updateAppWidget(paramContext, paramAppWidgetManager, paramArrayOfInt[i]);
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferAppWidgetProvider
 * JD-Core Version:    0.6.0
 */