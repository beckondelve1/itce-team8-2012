package net.biyee.onvifer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.biyee.android.ONVIF.BodyProbeMatches;
import net.biyee.android.ONVIF.EndpointReference;
import net.biyee.android.ONVIF.EnvelopeProbeMatches;
import net.biyee.android.ONVIF.GetDeviceInformationResponse;
import net.biyee.android.ONVIF.ProbeMatch;
import net.biyee.android.ONVIF.ProbeMatches;
import net.biyee.android.utility;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeBuilder;

public class DiscoverActivity extends Activity
{
  boolean bCanceled = false;
  boolean bDisposed = false;
  int iAddressTestedCount;
  int iTotalAddresseCount;
  ProgressDialog pd;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903046);
    findViewById(2131296262).setVisibility(8);
    ((TextView)findViewById(2131296263)).setText("New > Discover");
    this.pd = new ProgressDialog(this);
    this.pd.setMessage("Searching ONVIF conformant IP devices...");
    this.pd.setProgressStyle(0);
    this.pd.setCancelable(false);
    this.pd.setButton(-2, "Cancel", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        DiscoverActivity.this.bCanceled = true;
      }
    });
    this.pd.show();
    new Thread()
    {
      public void run()
      {
        if ((((WifiManager)DiscoverActivity.this.getSystemService("wifi")).isWifiEnabled()) || ("sdk".equals(Build.PRODUCT)));
        try
        {
          DatagramSocket localDatagramSocket = new DatagramSocket();
          localDatagramSocket.setSoTimeout(4000);
          InetAddress localInetAddress = InetAddress.getByName("239.255.255.250");
          if (localInetAddress == null)
            utility.logMessageAsync(DiscoverActivity.this, "IetAddress.getByName() for multicast returns null");
          byte[] arrayOfByte1 = new byte[2048];
          byte[] arrayOfByte2 = DiscoverActivity.this.getString(2131099672).replaceFirst("<a:MessageID>uuid:.+?</a:MessageID>", "<a:MessageID>uuid:" + UUID.randomUUID().toString() + "</a:MessageID>").getBytes();
          DatagramPacket localDatagramPacket1 = new DatagramPacket(arrayOfByte2, arrayOfByte2.length, localInetAddress, 3702);
          localDatagramSocket.send(localDatagramPacket1);
          localDatagramSocket.send(localDatagramPacket1);
          localDatagramSocket.send(localDatagramPacket1);
          ArrayList localArrayList = new ArrayList();
          while (true)
          {
            if ((DiscoverActivity.this.bDisposed) || (DiscoverActivity.this.bCanceled))
            {
              localDatagramSocket.disconnect();
              localDatagramSocket.close();
              super.run();
              return;
            }
            try
            {
              DatagramPacket localDatagramPacket2 = new DatagramPacket(arrayOfByte1, arrayOfByte1.length);
              localDatagramSocket.receive(localDatagramPacket2);
              String str1 = new String(localDatagramPacket2.getData());
              try
              {
                StringReader localStringReader = new StringReader(str1);
                InputNode localInputNode = NodeBuilder.read(localStringReader);
                EnvelopeProbeMatches localEnvelopeProbeMatches = (EnvelopeProbeMatches)new Persister().read(EnvelopeProbeMatches.class, localInputNode);
                if (localEnvelopeProbeMatches.BodyProbeMatches.ProbeMatches.listProbeMatches.size() <= 0)
                  continue;
                ProbeMatch localProbeMatch = (ProbeMatch)localEnvelopeProbeMatches.BodyProbeMatches.ProbeMatches.listProbeMatches.get(0);
                if (localArrayList.contains(localProbeMatch.EndpointReference.Address))
                  continue;
                localArrayList.add(localProbeMatch.EndpointReference.Address);
                DiscoverActivity.this.pd.dismiss();
                localIntent = new Intent();
                String[] arrayOfString1 = localProbeMatch.XAddrs.split("\\s");
                try
                {
                  URL localURL = new URL(arrayOfString1[(-1 + arrayOfString1.length)]);
                  localIntent.putExtra("address", localURL.getHost());
                  localObject = "";
                  arrayOfString2 = localProbeMatch.Scopes.split("\\s");
                  int i = arrayOfString2.length;
                  j = 0;
                  if (j < i)
                    break;
                  Button localButton = new Button(DiscoverActivity.this);
                  localButton.setText((CharSequence)localObject);
                  DiscoverActivity.2.1 local1 = new DiscoverActivity.2.1(this, localIntent);
                  localButton.setOnClickListener(local1);
                  LinearLayout localLinearLayout = (LinearLayout)DiscoverActivity.this.findViewById(2131296265);
                  DiscoverActivity localDiscoverActivity = DiscoverActivity.this;
                  DiscoverActivity.2.2 local2 = new DiscoverActivity.2.2(this, localLinearLayout, localButton);
                  localDiscoverActivity.runOnUiThread(local2);
                }
                catch (MalformedURLException localMalformedURLException)
                {
                  utility.logMessageAsync(DiscoverActivity.this, "Cannot parse xAddr: " + arrayOfString1[0]);
                }
              }
              catch (Exception localException3)
              {
                utility.logMessageAsync(DiscoverActivity.this, "Parse ONVIF search result error: " + localException3.getMessage() + "  Response: " + str1);
              }
            }
            catch (Exception localException2)
            {
            }
            if (localArrayList.size() != 0)
              continue;
            utility.ShowMessage(DiscoverActivity.this, "No ONVIF conformant devices have been discovered.");
          }
        }
        catch (Exception localException1)
        {
          while (true)
          {
            Intent localIntent;
            Object localObject;
            String[] arrayOfString2;
            int j;
            utility.logMessageAsync(DiscoverActivity.this, "Search ONVIF device error:" + localException1.getMessage());
            continue;
            String str2 = arrayOfString2[j];
            if (str2.contains("onvif://www.onvif.org/name/"))
            {
              String str6 = str2.replace("onvif://www.onvif.org/name/", "").replace("%20", " ");
              localIntent.putExtra("name", str6);
              localObject = localObject + " " + str6;
            }
            else if (str2.contains("onvif://www.onvif.org/type/video_encoder"))
            {
              localIntent.putExtra("video_encoder", "true");
            }
            else if (str2.contains("onvif://www.onvif.org/type/audio_encoder"))
            {
              localIntent.putExtra("audio_encoder", "true");
            }
            else if (str2.contains("onvif://www.onvif.org/hardware/"))
            {
              String str5 = str2.replace("onvif://www.onvif.org/hardware/", "");
              localIntent.putExtra("hardware", str5);
              localObject = localObject + " " + str5;
            }
            else if (str2.contains("onvif://www.onvif.org/location/"))
            {
              String str3 = str2.replace("onvif://www.onvif.org/location/", "");
              localIntent.putExtra("location", str3);
              String str4 = localObject + " " + str3;
              localObject = str4;
            }
            j++;
          }
        }
      }
    }
    .start();
  }

  protected void onPause()
  {
    super.onPause();
    this.bDisposed = true;
  }

  class IPDevice
  {
    public GetDeviceInformationResponse getDeviceInformationResponse;
    public String sIP;

    public IPDevice(String paramGetDeviceInformationResponse, GetDeviceInformationResponse arg3)
    {
      this.sIP = paramGetDeviceInformationResponse;
      Object localObject;
      this.getDeviceInformationResponse = localObject;
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DiscoverActivity
 * JD-Core Version:    0.6.0
 */