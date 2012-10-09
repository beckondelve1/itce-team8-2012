package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import net.biyee.android.ONVIF.Capabilities;
import net.biyee.android.ONVIF.Events;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class CapabilitiesEventsActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, getIntent().getExtras().getString("param"));
    setContentView(2130903046);
    ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
    ((TextView)findViewById(2131296263)).setText("Capabilities > Events");
    TableLayout localTableLayout = (TableLayout)findViewById(2131296266);
    if (localONVIFDevice.Capabilities.Events == null)
      utility.addTableRow(this, localTableLayout, "Events", "N/A");
    while (true)
    {
      return;
      utility.addTableRow(this, localTableLayout, "XAddr", localONVIFDevice.Capabilities.Events.XAddr);
      utility.addTableRow(this, localTableLayout, "WS Pausable Subscription\nManager Interface Support", localONVIFDevice.Capabilities.Events.WSPausableSubscriptionManagerInterfaceSupport);
      utility.addTableRow(this, localTableLayout, "WS Pull Point Support", localONVIFDevice.Capabilities.Events.WSPullPointSupport);
      utility.addTableRow(this, localTableLayout, "WSS ubscription Policy Support", localONVIFDevice.Capabilities.Events.WSSubscriptionPolicySupport);
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.CapabilitiesEventsActivity
 * JD-Core Version:    0.6.0
 */