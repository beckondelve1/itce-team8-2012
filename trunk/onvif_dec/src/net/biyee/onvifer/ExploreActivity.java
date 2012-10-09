package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.VideoEncoding;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class ExploreActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str = getIntent().getExtras().getString("uid");
    ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, str);
    WifiManager localWifiManager = (WifiManager)getSystemService("wifi");
    if ((localONVIFDevice != null) && (localONVIFDevice.sAddress.contains("192.168")) && (!localWifiManager.isWifiEnabled()) && (!"sdk".equals(Build.PRODUCT)))
      utility.ShowMessage(this, "The device address appears to be a local address. It may not work for your current cellular connection");
    if (localONVIFDevice != null)
    {
      setContentView(2130903046);
      ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
      LinearLayout localLinearLayout = (LinearLayout)findViewById(2131296265);
      int i = getResources().getDisplayMetrics().widthPixels * getResources().getDisplayMetrics().heightPixels;
      Profiles localProfiles1 = utilityONVIF.findOptimalProfile(i, 921600, VideoEncoding.JPEG, localONVIFDevice.listProfiles);
      if (localProfiles1 != null)
        utility.addButton(this, str + ",JPEG,HTTP," + localProfiles1.token, "Stream Video (JPEG/HTTP)", localLinearLayout, PlayVideoActivity.class);
      Profiles localProfiles2 = utilityONVIF.findOptimalProfile(i, 921600, VideoEncoding.H264, localONVIFDevice.listProfiles);
      if (localProfiles2 != null)
        utility.addButton(this, str + ",H.264,UDP," + localProfiles2.token, "Stream Video (H.264/UDP)", localLinearLayout, PlayVideoActivity.class);
      utility.addButton(this, str, "Device Information", localLinearLayout, DeviceInfoActivity.class);
      utility.addButton(this, str, "Capabilities", localLinearLayout, CapabilitiesActivity.class);
      utility.addButton(this, str, "Media Profiles", localLinearLayout, ProfilesActivity.class);
      if ((localONVIFDevice.listVideoEncoderConfigurations != null) && (localONVIFDevice.listVideoEncoderConfigurations.size() > 0))
        utility.addButton(this, str, "Video Encoder Configurations", localLinearLayout, VideoEncoderConfigurationsActivity.class);
      if ((localONVIFDevice.listAudioEncoderConfigurations != null) && (localONVIFDevice.listAudioEncoderConfigurations.size() > 0))
        utility.addButton(this, str, "Audio Encoder Configurations", localLinearLayout, AudioEncoderConfigurationsActivity.class);
      if ((localONVIFDevice.listPTZConfigurations != null) && (localONVIFDevice.listPTZConfigurations.size() > 0))
        utility.addButton(this, str, "PTZ Configurations", localLinearLayout, PTZConfigurationsActivity.class);
    }
    while (true)
    {
      return;
      utility.ShowMessage(this, getString(2131099668));
      finish();
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.ExploreActivity
 * JD-Core Version:    0.6.0
 */