package net.biyee.onvifer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class OnviferAppWedgetConfigure extends Activity
{
  int mAppWidgetId = 0;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setResult(0);
    setContentView(2130903040);
    GridView localGridView = (GridView)findViewById(2131296258);
    localGridView.setAdapter(new DeviceTileAdapter(this));
    localGridView.setOnItemClickListener(new OnviferAppWedgetConfigure.1(this));
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferAppWedgetConfigure
 * JD-Core Version:    0.6.0
 */