package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.Iterator;
import java.util.List;
import net.biyee.android.ONVIF.Capabilities;
import net.biyee.android.ONVIF.Device;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.IO;
import net.biyee.android.ONVIF.Network;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Security;
import net.biyee.android.ONVIF.SupportedVersions;
import net.biyee.android.ONVIF.System;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class CapabilitiesDeviceActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, getIntent().getExtras().getString("param"));
    setContentView(2130903046);
    ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
    ((TextView)findViewById(2131296263)).setText("Capabilities > Device");
    TableLayout localTableLayout = (TableLayout)findViewById(2131296266);
    utility.addTableRow(this, localTableLayout, "XAddr", localONVIFDevice.Capabilities.Device.XAddr);
    String str = "";
    Iterator localIterator = localONVIFDevice.Capabilities.Device.System.listSupportedVersions.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        utility.addTableRow(this, localTableLayout, "System", "Discovery Bye: " + localONVIFDevice.Capabilities.Device.System.DiscoveryBye + "\nDiscovery Resolve: " + localONVIFDevice.Capabilities.Device.System.DiscoveryResolve + "\nFirmware Upgrade: " + localONVIFDevice.Capabilities.Device.System.FirmwareUpgrade + "\nRemote Discovery: " + localONVIFDevice.Capabilities.Device.System.RemoteDiscovery + "\nSupported Versions: " + str + "\nSystemBackup: " + localONVIFDevice.Capabilities.Device.System.SystemBackup + "\nSystemLogging: " + localONVIFDevice.Capabilities.Device.System.SystemLogging);
        utility.addTableRow(this, localTableLayout, "IO", "Input Connectors: " + localONVIFDevice.Capabilities.Device.IO.InputConnectors + "\nRelay Outputs: " + localONVIFDevice.Capabilities.Device.IO.RelayOutputs);
        utility.addTableRow(this, localTableLayout, "Network", "DynDNS: " + localONVIFDevice.Capabilities.Device.Network.DynDNS + "\nIP Filter: " + localONVIFDevice.Capabilities.Device.Network.IPFilter + "\nIP Version6: " + localONVIFDevice.Capabilities.Device.Network.IPVersion6 + "\nZero Configuration: " + localONVIFDevice.Capabilities.Device.Network.ZeroConfiguration);
        utility.addTableRow(this, localTableLayout, "Security", "Access Policy Config: " + localONVIFDevice.Capabilities.Device.Security.AccessPolicyConfig + "\nKerberos Token: " + localONVIFDevice.Capabilities.Device.Security.KerberosToken + "\nOnboard Key Generation: " + localONVIFDevice.Capabilities.Device.Security.OnboardKeyGeneration + "\nREL Token: " + localONVIFDevice.Capabilities.Device.Security.RELToken + "\nSAML Token: " + localONVIFDevice.Capabilities.Device.Security.SAMLToken + "\nTLS1.1: " + localONVIFDevice.Capabilities.Device.Security.TLS11 + "\nTLS1.2: " + localONVIFDevice.Capabilities.Device.Security.TLS12 + "\nX.509: " + localONVIFDevice.Capabilities.Device.Security.X509Token);
        return;
      }
      SupportedVersions localSupportedVersions = (SupportedVersions)localIterator.next();
      str = str + localSupportedVersions.Major.toString() + "." + localSupportedVersions.Minor.toString() + "   ";
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.CapabilitiesDeviceActivity
 * JD-Core Version:    0.6.0
 */