package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.H264Configuration;
import net.biyee.android.ONVIF.MulticastConfiguration;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.VideoEncoderConfiguration;
import net.biyee.android.ONVIF.VideoEncoding;
import net.biyee.android.ONVIF.VideoRateControl;
import net.biyee.android.ONVIF.VideoResolution;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class VideoEncoderConfigurationActivity extends Activity
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
      VideoEncoderConfiguration localVideoEncoderConfiguration = utilityONVIF.findVideoEncoderConfiguration(str2, localONVIFDevice.listVideoEncoderConfigurations);
      if (localVideoEncoderConfiguration != null)
      {
        ((TextView)findViewById(2131296263)).setText("Video Encoder Configurations > " + localVideoEncoderConfiguration.Name);
        utility.addTableRow(this, localTableLayout, "Name", localVideoEncoderConfiguration.Name);
        utility.addTableRow(this, localTableLayout, "Token", localVideoEncoderConfiguration.token);
        if (localVideoEncoderConfiguration.Encoding != null)
          utility.addTableRow(this, localTableLayout, "Encoding", localVideoEncoderConfiguration.Encoding.toString());
        if (localVideoEncoderConfiguration.Resolution != null)
          utility.addTableRow(this, localTableLayout, "Resolution", localVideoEncoderConfiguration.Resolution.toString());
        utility.addTableRow(this, localTableLayout, "Quality", String.valueOf(localVideoEncoderConfiguration.Quality));
        if (localVideoEncoderConfiguration.RateControl != null)
          utility.addTableRow(this, localTableLayout, "Rate Control", localVideoEncoderConfiguration.RateControl.toString());
        if (localVideoEncoderConfiguration.H264 != null)
          utility.addTableRow(this, localTableLayout, "H264", localVideoEncoderConfiguration.H264.toString());
        if (localVideoEncoderConfiguration.Multicast != null)
          utility.addTableRow(this, localTableLayout, "Multicast Configuration", localVideoEncoderConfiguration.Multicast.toString());
        if (localVideoEncoderConfiguration.SessionTimeout != null)
          utility.addTableRow(this, localTableLayout, "Session Timeout", localVideoEncoderConfiguration.SessionTimeout);
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
 * Qualified Name:     net.biyee.onvifer.VideoEncoderConfigurationActivity
 * JD-Core Version:    0.6.0
 */