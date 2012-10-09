package net.biyee.onvifer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import net.biyee.android.ICallback;
import net.biyee.android.ONVIF.Capabilities;
import net.biyee.android.ONVIF.Device;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.IO;
import net.biyee.android.ONVIF.ListDevice;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.SupportedVersions;
import net.biyee.android.ONVIF.System;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class NewActivity extends Activity
  implements ICallback
{
  public static final int DEMO = 101;
  public static final int DISCOVERY = 100;
  boolean _bDisposed;
  int _iDeviceCount = 0;
  Bitmap bitmapBackground = null;
  ONVIFDevice od;
  ProgressDialog pd;
  public String sAddress;
  String sResponseDiscovery;

  private void showBackgroundImage()
  {
    if (this.bitmapBackground != null)
      ((ImageView)findViewById(2131296256)).setImageBitmap(this.bitmapBackground);
    while (true)
    {
      return;
      ((ImageView)findViewById(2131296256)).setImageResource(2130837521);
    }
  }

  public void Save()
  {
    try
    {
      this.od.sName = ((EditText)findViewById(2131296295)).getText().toString();
      if (this.od.sName.trim().equals(""))
        this.od.sName = "My Device";
      this.od.sAddress = ((EditText)findViewById(2131296298)).getText().toString();
      this.od.sUserName = ((EditText)findViewById(2131296301)).getText().toString();
      this.od.sPassword = ((EditText)findViewById(2131296304)).getText().toString();
      utilityONVIF.saveDeviceInfo(this, this.od);
      DeviceInfo localDeviceInfo = new DeviceInfo();
      localDeviceInfo.uid = this.od.uid;
      localDeviceInfo.sAddress = this.od.sAddress;
      localDeviceInfo.sName = this.od.sName;
      localDeviceInfo.sUserName = this.od.sUserName;
      localDeviceInfo.sPassword = this.od.sPassword;
      localDeviceInfo.sModel = this.od.di.Model;
      ListDevice localListDevice = utilityONVIF.getListDevice(this);
      localListDevice.listDevices.add(localDeviceInfo);
      utilityONVIF.saveListDevice(this, localListDevice);
      if (this.bitmapBackground != null)
      {
        int i = this.bitmapBackground.getHeight() * this.bitmapBackground.getWidth();
        if (i > 14400)
        {
          double d = Math.sqrt(14400 / i);
          this.bitmapBackground = Bitmap.createScaledBitmap(this.bitmapBackground, (int)(d * this.bitmapBackground.getWidth()), (int)(d * this.bitmapBackground.getHeight()), true);
        }
        File localFile = new File(getDir("Snapshot", 0), this.od.uid + ".jpg");
        this.bitmapBackground.compress(Bitmap.CompressFormat.JPEG, 100, new BufferedOutputStream(new FileOutputStream(localFile)));
      }
      finish();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        utility.logMessageAsync(getApplicationContext(), "Error in saving device info: " + localException.getMessage());
    }
  }

  public void callback(Object paramObject)
  {
    this.od = ((ONVIFDevice)paramObject);
    TableLayout localTableLayout = (TableLayout)findViewById(2131296305);
    localTableLayout.removeViews(0, localTableLayout.getChildCount());
    Iterator localIterator;
    if (((this.od.sError == null) || (this.od.sError == "")) && (this.od.di != null))
    {
      utility.addTableRow(this, localTableLayout, "Manufacturer", this.od.di.Manufacturer);
      utility.addTableRow(this, localTableLayout, "Model", this.od.di.Model);
      utility.addTableRow(this, localTableLayout, "Serial Number", this.od.di.SerialNumber);
      if (this.od.Capabilities.Device.System.listSupportedVersions != null)
      {
        localIterator = this.od.Capabilities.Device.System.listSupportedVersions.iterator();
        if (localIterator.hasNext());
      }
      else
      {
        if (this.od.Capabilities.PTZ != null)
          utility.addTableRow(this, localTableLayout, "PTZ", "Yes");
        if ((this.od.Capabilities.Device.IO != null) && (this.od.Capabilities.Device.IO.RelayOutputs != null))
          utility.addTableRow(this, localTableLayout, "Relay outputs", this.od.Capabilities.Device.IO.RelayOutputs.toString());
        if ((this.od.Capabilities.Device.IO != null) && (this.od.Capabilities.Device.IO.InputConnectors != null))
          utility.addTableRow(this, localTableLayout, "Relay Inputs", this.od.Capabilities.Device.IO.InputConnectors.toString());
        this.od.sAddress = ((EditText)findViewById(2131296298)).getText().toString();
        this.od.sUserName = ((EditText)findViewById(2131296301)).getText().toString();
        this.od.sPassword = ((EditText)findViewById(2131296304)).getText().toString();
        View localView = findViewById(2131296287);
        int i = localView.getWidth() * localView.getHeight();
        Profiles localProfiles = utilityONVIF.findOptimalProfile(i, this.od.listProfiles);
        this.pd.setMessage("Retrieving a snapshot...");
        new NewActivity.6(this, i, localProfiles).execute(new Void[0]);
      }
    }
    while (true)
    {
      return;
      SupportedVersions localSupportedVersions = (SupportedVersions)localIterator.next();
      utility.addTableRow(this, localTableLayout, "ONVIF Version", localSupportedVersions.Major.toString() + "." + localSupportedVersions.Minor.toString());
      break;
      utility.ShowMessage(this, "An error ocurred during device information retrieval.  Please let us know if you are sure this device is ONVIF conformant.  We will try our best to resolve the issue.  Error message: " + this.od.sError);
    }
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    default:
      return;
    case 100:
    case 101:
    }
    EditText localEditText1;
    label105: EditText localEditText2;
    label138: EditText localEditText3;
    if (paramInt2 == -1)
    {
      if (paramIntent.getStringExtra("address") != null)
      {
        String str = paramIntent.getStringExtra("address");
        ((EditText)findViewById(2131296298)).setText(str);
      }
      localEditText1 = (EditText)findViewById(2131296295);
      if (paramIntent.getStringExtra("name") == null)
        break label205;
      localEditText1.setText(paramIntent.getStringExtra("name"));
      localEditText2 = (EditText)findViewById(2131296301);
      if (paramIntent.getStringExtra("username") == null)
        break label244;
      localEditText2.setText(paramIntent.getStringExtra("username"));
      localEditText3 = (EditText)findViewById(2131296304);
      if (paramIntent.getStringExtra("password") == null)
        break label254;
      localEditText3.setText(paramIntent.getStringExtra("password"));
    }
    while (true)
    {
      this.bitmapBackground = null;
      ((TableLayout)findViewById(2131296305)).removeAllViews();
      ((ImageButton)findViewById(2131296307)).setVisibility(4);
      break;
      break;
      label205: if (paramIntent.getStringExtra("hardware") != null)
      {
        localEditText1.setText(paramIntent.getStringExtra("hardware"));
        break label105;
      }
      localEditText1.setText(paramIntent.getStringExtra(""));
      break label105;
      label244: localEditText2.setText("");
      break label138;
      label254: localEditText3.setText("");
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903048);
    findViewById(2131296290).setVisibility(8);
    if ((((WifiManager)getSystemService("wifi")).isWifiEnabled()) || ("sdk".equals(Build.PRODUCT)))
    {
      ((TextView)findViewById(2131296289)).setText("Discovering...");
      new NewActivity.1(this).start();
    }
    while (true)
    {
      ((Button)findViewById(2131296290)).setOnClickListener(new NewActivity.2(this));
      ((Button)findViewById(2131296291)).setOnClickListener(new NewActivity.3(this));
      ((ImageButton)findViewById(2131296306)).setOnClickListener(new NewActivity.4(this));
      ImageButton localImageButton = (ImageButton)findViewById(2131296307);
      localImageButton.setVisibility(8);
      localImageButton.setOnClickListener(new NewActivity.5(this));
      return;
      ((TextView)findViewById(2131296289)).setText("Discovery is available only for Wi-Fi connections.");
    }
  }

  protected void onPause()
  {
    this._bDisposed = true;
    super.onPause();
  }

  protected void onResume()
  {
    this._bDisposed = false;
    showBackgroundImage();
    super.onResume();
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity
 * JD-Core Version:    0.6.0
 */