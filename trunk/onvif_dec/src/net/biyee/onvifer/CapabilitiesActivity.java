package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class CapabilitiesActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str = getIntent().getExtras().getString("param");
    ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, str);
    setContentView(2130903046);
    ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
    ((TextView)findViewById(2131296263)).setText("Capabilities");
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131296265);
    utility.addButton(this, str, "Device", localLinearLayout, CapabilitiesDeviceActivity.class);
    utility.addButton(this, str, "Media", localLinearLayout, CapabilitiesMediaActivity.class);
    utility.addButton(this, str, "PTZ", localLinearLayout, CapabilitiesPTZActivity.class);
    utility.addButton(this, str, "Imaging", localLinearLayout, CapabilitiesImagingActivity.class);
    utility.addButton(this, str, "Events", localLinearLayout, CapabilitiesEventsActivity.class);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.CapabilitiesActivity
 * JD-Core Version:    0.6.0
 */