package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.PTZConfiguration;
import net.biyee.android.ONVIF.PTZSpeed;
import net.biyee.android.ONVIF.PanTiltLimits;
import net.biyee.android.ONVIF.ZoomLimits;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class PTZConfigurationActivity extends Activity
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
      PTZConfiguration localPTZConfiguration = utilityONVIF.findPTZConfiguration(str2, localONVIFDevice.listPTZConfigurations);
      if (localPTZConfiguration != null)
      {
        ((TextView)findViewById(2131296263)).setText("PTZ Configurations > " + localPTZConfiguration.Name);
        utility.addTableRow(this, localTableLayout, "Name", localPTZConfiguration.Name);
        utility.addTableRow(this, localTableLayout, "Token", localPTZConfiguration.token);
        if (localPTZConfiguration.NodeToken != null)
          utility.addTableRow(this, localTableLayout, "Node Token", localPTZConfiguration.NodeToken);
        if (localPTZConfiguration.DefaultAbsolutePantTiltPositionSpace != null)
          utility.addTableRow(this, localTableLayout, "Default Absolute Pant Til tPosition Space", localPTZConfiguration.DefaultAbsolutePantTiltPositionSpace);
        if (localPTZConfiguration.DefaultRelativePanTiltTranslationSpace != null)
          utility.addTableRow(this, localTableLayout, "Default Relative Pan Tilt Translation Space", localPTZConfiguration.DefaultRelativePanTiltTranslationSpace);
        if (localPTZConfiguration.DefaultRelativeZoomTranslationSpace != null)
          utility.addTableRow(this, localTableLayout, "Default Relative Zoom Translation Space", localPTZConfiguration.DefaultRelativeZoomTranslationSpace);
        if (localPTZConfiguration.DefaultContinuousPanTiltVelocitySpace != null)
          utility.addTableRow(this, localTableLayout, "Default Continuous Pan Tilt Velocity Space", localPTZConfiguration.DefaultContinuousPanTiltVelocitySpace);
        if (localPTZConfiguration.DefaultContinuousZoomVelocitySpace != null)
          utility.addTableRow(this, localTableLayout, "Default Continuous Zoom Velocity Space", localPTZConfiguration.DefaultContinuousZoomVelocitySpace);
        if (localPTZConfiguration.DefaultPTZSpeed != null)
          utility.addTableRow(this, localTableLayout, "Default PTZ Speed", localPTZConfiguration.DefaultPTZSpeed.toString());
        if (localPTZConfiguration.DefaultPTZTimeout != null)
          utility.addTableRow(this, localTableLayout, "Default PTZ Timeout", localPTZConfiguration.DefaultPTZTimeout);
        if (localPTZConfiguration.PanTiltLimits != null)
          utility.addTableRow(this, localTableLayout, "Pan/Tilt Limits", localPTZConfiguration.PanTiltLimits.toString());
        if (localPTZConfiguration.ZoomLimits != null)
          utility.addTableRow(this, localTableLayout, "PZoom Limits", localPTZConfiguration.ZoomLimits.toString());
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
 * Qualified Name:     net.biyee.onvifer.PTZConfigurationActivity
 * JD-Core Version:    0.6.0
 */