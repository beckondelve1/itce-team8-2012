package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Iterator;
import java.util.List;
import net.biyee.android.ONVIF.AudioEncoderConfiguration;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class AudioEncoderConfigurationsActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str = getIntent().getExtras().getString("param");
    ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, str);
    setContentView(2130903046);
    ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
    ((TextView)findViewById(2131296263)).setText("Audio Encoder Configurations");
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131296265);
    Iterator localIterator = localONVIFDevice.listAudioEncoderConfigurations.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      AudioEncoderConfiguration localAudioEncoderConfiguration = (AudioEncoderConfiguration)localIterator.next();
      utility.addButton(this, str + "," + localAudioEncoderConfiguration.token, localAudioEncoderConfiguration.Name, localLinearLayout, AudioEncoderConfigurationActivity.class);
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.AudioEncoderConfigurationsActivity
 * JD-Core Version:    0.6.0
 */