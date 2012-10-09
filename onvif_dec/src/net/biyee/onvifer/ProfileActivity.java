package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import net.biyee.android.ONVIF.AudioEncoderConfiguration;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.PTZConfiguration;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.VideoEncoderConfiguration;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class ProfileActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String[] arrayOfString = getIntent().getExtras().getString("param").split(",");
    String str1;
    Profiles localProfiles;
    LinearLayout localLinearLayout;
    if (arrayOfString.length == 2)
    {
      str1 = arrayOfString[0];
      String str2 = arrayOfString[1];
      ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, str1);
      setContentView(2130903046);
      TableLayout localTableLayout = (TableLayout)findViewById(2131296266);
      ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
      ((TextView)findViewById(2131296263)).setText("Media Profiles > " + str2);
      localProfiles = utilityONVIF.findProfile(str2, localONVIFDevice.listProfiles);
      if (localProfiles != null)
      {
        utility.addTableRow(this, localTableLayout, "Name", localProfiles.Name);
        utility.addTableRow(this, localTableLayout, "Token", localProfiles.token);
        if (localProfiles.VideoEncoderConfiguration != null)
          localLinearLayout = (LinearLayout)findViewById(2131296265);
        switch ($SWITCH_TABLE$net$biyee$android$ONVIF$VideoEncoding()[localProfiles.VideoEncoderConfiguration.Encoding.ordinal()])
        {
        case 2:
        default:
          utility.addTableRow(this, localTableLayout, "Video Encoder\nConfiguration", new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder("Name: ").append(localProfiles.VideoEncoderConfiguration.Name).toString())).append("\nToken: ").append(localProfiles.VideoEncoderConfiguration.token).toString())).append("\nEncoding: ").append(localProfiles.VideoEncoderConfiguration.Encoding).toString())).append("\nH264: ").append(localProfiles.VideoEncoderConfiguration.H264).toString())).append("\nResolution: ").append(localProfiles.VideoEncoderConfiguration.Resolution).toString())).append("\nQuality: ").append(localProfiles.VideoEncoderConfiguration.Quality).toString())).append("\nSessionTimeout: ").append(localProfiles.VideoEncoderConfiguration.SessionTimeout).toString())).append("\nUse Count: ").append(localProfiles.VideoEncoderConfiguration.UseCount).toString())).append("\nVideo Rate Control: ").append(localProfiles.VideoEncoderConfiguration.RateControl).toString() + "\nMulticast: " + localProfiles.VideoEncoderConfiguration.Multicast);
          if (localProfiles.AudioEncoderConfiguration != null)
            utility.addTableRow(this, localTableLayout, "Audio Encoder\nConfiguration", new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder("Name: ").append(localProfiles.AudioEncoderConfiguration.Name).toString())).append("\nToken: ").append(localProfiles.AudioEncoderConfiguration.token).toString())).append("\nBit rate: ").append(localProfiles.AudioEncoderConfiguration.Bitrate).toString())).append("\nSample Rate: ").append(localProfiles.AudioEncoderConfiguration.SampleRate).toString())).append("\nSessionTimeout: ").append(localProfiles.AudioEncoderConfiguration.SessionTimeout).toString())).append("\nUse Count: ").append(localProfiles.AudioEncoderConfiguration.UseCount).toString())).append("\nEncoding: ").append(localProfiles.AudioEncoderConfiguration.Encoding).toString() + "\nMulticast: " + localProfiles.AudioEncoderConfiguration.Multicast);
          if (localProfiles.PTZConfiguration == null)
            break;
          String str3 = new StringBuilder(String.valueOf(new StringBuilder("Name: ").append(localProfiles.PTZConfiguration.Name).toString())).append("\nToken: ").append(localProfiles.PTZConfiguration.token).toString() + "\nUse Count: " + localProfiles.PTZConfiguration.UseCount;
          if (localProfiles.PTZConfiguration.NodeToken != null)
            str3 = str3 + "\nNode Token: " + localProfiles.PTZConfiguration.NodeToken;
          if (localProfiles.PTZConfiguration.DefaultAbsoluteZoomPositionSpace != null)
            str3 = str3 + "\nDefault Absolute Zoom Position Space: " + localProfiles.PTZConfiguration.DefaultAbsoluteZoomPositionSpace;
          if (localProfiles.PTZConfiguration.DefaultRelativePanTiltTranslationSpace != null)
            str3 = str3 + "\nDefault Relative Pan Tilt Translation Space: " + localProfiles.PTZConfiguration.DefaultRelativePanTiltTranslationSpace;
          if (localProfiles.PTZConfiguration.DefaultRelativeZoomTranslationSpace != null)
            str3 = str3 + "\nDefault Relative Zoom Translation Space: " + localProfiles.PTZConfiguration.DefaultRelativeZoomTranslationSpace;
          if (localProfiles.PTZConfiguration.DefaultContinuousPanTiltVelocitySpace != null)
            str3 = str3 + "\nDefault Continuous Pan Tilt Velocity Space: " + localProfiles.PTZConfiguration.DefaultContinuousPanTiltVelocitySpace;
          if (localProfiles.PTZConfiguration.DefaultContinuousZoomVelocitySpace != null)
            str3 = str3 + "\nDefault Continuous Zoom Velocity Space: " + localProfiles.PTZConfiguration.DefaultContinuousZoomVelocitySpace;
          if (localProfiles.PTZConfiguration.DefaultPTZSpeed != null)
            str3 = str3 + "\nDefault PTZ Speed: " + localProfiles.PTZConfiguration.DefaultPTZSpeed;
          if (localProfiles.PTZConfiguration.DefaultPTZTimeout != null)
            str3 = str3 + "\nDefault PTZ Timeout: " + localProfiles.PTZConfiguration.DefaultPTZTimeout;
          if (localProfiles.PTZConfiguration.PanTiltLimits != null)
            str3 = str3 + "\nPan/Tilt Limits: " + localProfiles.PTZConfiguration.PanTiltLimits;
          if (localProfiles.PTZConfiguration.ZoomLimits != null)
            str3 = str3 + "\nZoom Limits: " + localProfiles.PTZConfiguration.ZoomLimits;
          utility.addTableRow(this, localTableLayout, "PTZ Configuration\nConfiguration", str3);
        case 1:
        case 3:
        }
      }
    }
    while (true)
    {
      return;
      utility.addButton(this, str1 + ",JPEG,HTTP," + localProfiles.token, "Stream Video (JPEG)", localLinearLayout, PlayVideoActivity.class);
      break;
      utility.addButton(this, str1 + ",H.264,UDP," + localProfiles.token, "Stream Video (H.264)", localLinearLayout, PlayVideoActivity.class);
      break;
      utility.ShowMessage(this, "Error: no profile token");
      utility.logMessageAsync(this, "Error in ProfileActivityonCreate: no token");
      finish();
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.ProfileActivity
 * JD-Core Version:    0.6.0
 */