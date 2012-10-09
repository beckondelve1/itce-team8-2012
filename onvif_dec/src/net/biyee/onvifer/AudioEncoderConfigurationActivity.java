package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import net.biyee.android.ONVIF.AudioEncoderConfiguration;
import net.biyee.android.ONVIF.AudioEncoding;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.MulticastConfiguration;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class AudioEncoderConfigurationActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String[] arrayOfString = getIntent().getExtras().getString("param").split(",");
    if (arrayOfString.length == 2)
    {
      String str1 = arrayOfString[0];
      String str2 = arrayOfString[1];
      ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, str1);
      setContentView(2130903046);
      TableLayout localTableLayout = (TableLayout)findViewById(2131296266);
      ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
      ((TextView)findViewById(2131296263)).setText("Audio Encoder Configurations > " + str2);
      AudioEncoderConfiguration localAudioEncoderConfiguration = utilityONVIF.findAudioEncoderConfiguration(str2, localONVIFDevice.listAudioEncoderConfigurations);
      if (localAudioEncoderConfiguration != null)
      {
        ((TextView)findViewById(2131296263)).setText("Audio Encoder Configurations > " + localAudioEncoderConfiguration.Name);
        utility.addTableRow(this, localTableLayout, "Name", localAudioEncoderConfiguration.Name);
        utility.addTableRow(this, localTableLayout, "Token", localAudioEncoderConfiguration.token);
        if (localAudioEncoderConfiguration.Encoding != null)
          utility.addTableRow(this, localTableLayout, "Encoding", localAudioEncoderConfiguration.Encoding.toString());
        utility.addTableRow(this, localTableLayout, "Bit rate", String.valueOf(localAudioEncoderConfiguration.Bitrate));
        utility.addTableRow(this, localTableLayout, "Sample Rate", String.valueOf(localAudioEncoderConfiguration.SampleRate));
        if (localAudioEncoderConfiguration.Multicast != null)
          utility.addTableRow(this, localTableLayout, "Multicast Configuration", localAudioEncoderConfiguration.Multicast.toString());
        if (localAudioEncoderConfiguration.SessionTimeout != null)
          utility.addTableRow(this, localTableLayout, "Session Timeout", localAudioEncoderConfiguration.SessionTimeout);
      }
    }
    while (true)
    {
      return;
      utility.ShowMessage(this, "Error: no profile token");
      utility.logMessageAsync(this, "Error in ProfileActivityonCreate: no token");
      finish();
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.AudioEncoderConfigurationActivity
 * JD-Core Version:    0.6.0
 */