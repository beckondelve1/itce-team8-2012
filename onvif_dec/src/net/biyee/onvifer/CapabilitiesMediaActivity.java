package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import net.biyee.android.ONVIF.Capabilities;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.Media;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.StreamingCapabilities;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class CapabilitiesMediaActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, getIntent().getExtras().getString("param"));
    setContentView(2130903046);
    ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
    ((TextView)findViewById(2131296263)).setText("Capabilities > Media");
    TableLayout localTableLayout = (TableLayout)findViewById(2131296266);
    utility.addTableRow(this, localTableLayout, "XAddr", localONVIFDevice.Capabilities.Media.XAddr);
    utility.addTableRow(this, localTableLayout, "RTP/RTSP/TCP:", localONVIFDevice.Capabilities.Media.StreamingCapabilities.RTP_RTSP_TCP);
    utility.addTableRow(this, localTableLayout, "RTP over TCP:", localONVIFDevice.Capabilities.Media.StreamingCapabilities.RTP_TCP);
    utility.addTableRow(this, localTableLayout, " RTP multicast:", localONVIFDevice.Capabilities.Media.StreamingCapabilities.RTPMulticast);
    utility.addTableRow(this, localTableLayout, " RTP multicast:", localONVIFDevice.Capabilities.Media.StreamingCapabilities.NonAggregateControl);
    utility.addTableRow(this, localTableLayout, " RTP multicast:", localONVIFDevice.Capabilities.Media.StreamingCapabilities.NonAggregateControl);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.CapabilitiesMediaActivity
 * JD-Core Version:    0.6.0
 */