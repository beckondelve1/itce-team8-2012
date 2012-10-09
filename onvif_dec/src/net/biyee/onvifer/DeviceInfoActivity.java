package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class DeviceInfoActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903046);
    ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, getIntent().getExtras().getString("param"));
    ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
    ((TextView)findViewById(2131296263)).setText("Device Information");
    TableLayout localTableLayout = (TableLayout)findViewById(2131296266);
    utility.addTableRow(this, localTableLayout, "Manufacturer", localONVIFDevice.di.Manufacturer);
    utility.addTableRow(this, localTableLayout, "Model", localONVIFDevice.di.Model);
    utility.addTableRow(this, localTableLayout, "Serial Number", localONVIFDevice.di.SerialNumber);
    utility.addTableRow(this, localTableLayout, "Firmware Version", localONVIFDevice.di.FirmwareVersion);
    utility.addTableRow(this, localTableLayout, "Hardware ID", localONVIFDevice.di.HardwareId);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DeviceInfoActivity
 * JD-Core Version:    0.6.0
 */