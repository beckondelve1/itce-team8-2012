package net.biyee.onvifer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Iterator;
import java.util.List;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class ProfilesActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str = getIntent().getExtras().getString("param");
    ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, str);
    setContentView(2130903046);
    ((TextView)findViewById(2131296262)).setText(localONVIFDevice.sName + "(" + localONVIFDevice.di.Model + ")");
    ((TextView)findViewById(2131296263)).setText("Media Profiles");
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131296265);
    Iterator localIterator = localONVIFDevice.listProfiles.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      Profiles localProfiles = (Profiles)localIterator.next();
      utility.addButton(this, str + "," + localProfiles.token, localProfiles.Name, localLinearLayout, ProfileActivity.class);
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.ProfilesActivity
 * JD-Core Version:    0.6.0
 */