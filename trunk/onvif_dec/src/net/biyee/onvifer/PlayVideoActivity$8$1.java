package net.biyee.onvifer;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Button;
import net.biyee.android.BoolClass;
import net.biyee.android.ONVIF.Capabilities;
import net.biyee.android.ONVIF.GetStreamUriResponse;
import net.biyee.android.ONVIF.Media;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.SoapParam;
import net.biyee.android.ONVIF.StreamSetup;
import net.biyee.android.ONVIF.StreamType;
import net.biyee.android.ONVIF.Transport;
import net.biyee.android.ONVIF.TransportProtocol;
import net.biyee.android.ONVIF.VideoEncoding;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

class PlayVideoActivity$8$1 extends Thread
{
  public void run()
  {
    try
    {
      PlayVideoActivity.8.access$2(this.this$1).od = utilityONVIF.getONVIFDevice(PlayVideoActivity.8.access$2(this.this$1), this.val$sUID);
      if (PlayVideoActivity.access$1(PlayVideoActivity.8.access$2(this.this$1)) == null)
      {
        int i = PlayVideoActivity.8.access$2(this.this$1).getResources().getDisplayMetrics().widthPixels * PlayVideoActivity.8.access$2(this.this$1).getResources().getDisplayMetrics().heightPixels;
        if ("JPEG".equals(PlayVideoActivity.8.access$2(this.this$1).sCodec))
        {
          PlayVideoActivity localPlayVideoActivity2 = PlayVideoActivity.8.access$2(this.this$1);
          Profiles localProfiles2 = utilityONVIF.findOptimalProfile(i, 921600, VideoEncoding.JPEG, PlayVideoActivity.8.access$2(this.this$1).od.listProfiles);
          localPlayVideoActivity2.p = localProfiles2;
          if (localProfiles2 == null)
            utility.ShowMessage(PlayVideoActivity.8.access$2(this.this$1), "The device does not have a suitable profile for mobile JPEG");
        }
        while (true)
        {
          if (PlayVideoActivity.8.access$2(this.this$1).p == null)
            break label555;
          String str1 = utilityONVIF.GetCorrectedONVIFUrl(PlayVideoActivity.8.access$2(this.this$1).od.sAddress, PlayVideoActivity.8.access$2(this.this$1).od.Capabilities.Media.XAddr);
          StreamSetup localStreamSetup = new StreamSetup();
          localStreamSetup.Stream = StreamType.RTPUnicast;
          localTransport = new Transport();
          if (!"HTTP".equals(PlayVideoActivity.8.access$2(this.this$1).sTransportProtocol))
            break;
          localTransport.Protocol = TransportProtocol.HTTP;
          localStreamSetup.Transport = localTransport;
          String str2 = PlayVideoActivity.8.access$2(this.this$1).od.sUserName;
          String str3 = PlayVideoActivity.8.access$2(this.this$1).od.sPassword;
          SoapParam[] arrayOfSoapParam = new SoapParam[2];
          arrayOfSoapParam[0] = new SoapParam(localStreamSetup, "StreamSetup");
          arrayOfSoapParam[1] = new SoapParam(PlayVideoActivity.8.access$2(this.this$1).p.token, "ProfileToken");
          GetStreamUriResponse localGetStreamUriResponse = (GetStreamUriResponse)utilityONVIF.callSOAPServiceEx(GetStreamUriResponse.class, "http://www.onvif.org/ver10/media/wsdl", "GetStreamUri", str1, str2, str3, arrayOfSoapParam);
          if (PlayVideoActivity.8.access$2(this.this$1).bDisposed.bValue)
            return;
          PlayVideoActivity.8.access$2(this.this$1).runOnUiThread(new PlayVideoActivity.8.1.1(this, localGetStreamUriResponse, this.val$sUID));
          return;
          if (!"H.264".equals(PlayVideoActivity.8.access$2(this.this$1).sCodec))
            continue;
          PlayVideoActivity localPlayVideoActivity1 = PlayVideoActivity.8.access$2(this.this$1);
          Profiles localProfiles1 = utilityONVIF.findOptimalProfile(i, 921600, VideoEncoding.H264, PlayVideoActivity.8.access$2(this.this$1).od.listProfiles);
          localPlayVideoActivity1.p = localProfiles1;
          if (localProfiles1 != null)
            continue;
          utility.ShowMessage(PlayVideoActivity.8.access$2(this.this$1), "The device does not have a suitable profile for mobile H.264");
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Transport localTransport;
        utility.logMessageAsync(PlayVideoActivity.8.access$2(this.this$1), "Error in retrieving streaming URI: " + localException.getMessage());
        break;
        PlayVideoActivity.8.access$2(this.this$1).p = utilityONVIF.findProfile(PlayVideoActivity.access$1(PlayVideoActivity.8.access$2(this.this$1)), PlayVideoActivity.8.access$2(this.this$1).od.listProfiles);
        continue;
        localTransport.Protocol = TransportProtocol.UDP;
      }
      label555: PlayVideoActivity.8.access$2(this.this$1).runOnUiThread(new PlayVideoActivity.8.1.2(this, this.val$btModeSwitch));
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.8.1
 * JD-Core Version:    0.6.0
 */