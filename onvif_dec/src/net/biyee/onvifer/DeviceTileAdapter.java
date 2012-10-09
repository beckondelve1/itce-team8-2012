package net.biyee.onvifer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.ONVIF.ListDevice;
import net.biyee.android.ONVIF.utilityONVIF;

public class DeviceTileAdapter extends BaseAdapter
{
  private Activity activity;
  ListDevice listDevice;

  public DeviceTileAdapter(Activity paramActivity)
  {
    this.activity = paramActivity;
  }

  private void populateGridViewSnapshots()
  {
    new DeviceTileAdapter.1(this).start();
  }

  public int getCount()
  {
    this.listDevice = utilityONVIF.getListDevice(this.activity);
    return this.listDevice.listDevices.size();
  }

  public Object getItem(int paramInt)
  {
    return Integer.toString(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      new View(this.activity);
    for (View localView = ((LayoutInflater)this.activity.getSystemService("layout_inflater")).inflate(2130903044, paramViewGroup, false); ; localView = paramView)
    {
      ((ImageView)localView.findViewById(2131296270)).setImageBitmap(null);
      ((TextView)localView.findViewById(2131296273)).setText("");
      ((TextView)localView.findViewById(2131296271)).setText(((DeviceInfo)this.listDevice.listDevices.get(paramInt)).sName);
      ((TextView)localView.findViewById(2131296272)).setText(((DeviceInfo)this.listDevice.listDevices.get(paramInt)).sModel);
      return localView;
    }
  }

  public void notifyDataSetChanged()
  {
    super.notifyDataSetChanged();
  }

  public void notifyDataSetInvalidated()
  {
    super.notifyDataSetInvalidated();
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DeviceTileAdapter
 * JD-Core Version:    0.6.0
 */