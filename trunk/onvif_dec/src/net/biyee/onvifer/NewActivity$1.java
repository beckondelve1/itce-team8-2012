package net.biyee.onvifer;

import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.biyee.android.ONVIF.BodyProbeMatches;
import net.biyee.android.ONVIF.EndpointReference;
import net.biyee.android.ONVIF.EnvelopeProbeMatches;
import net.biyee.android.ONVIF.ProbeMatch;
import net.biyee.android.ONVIF.ProbeMatches;
import net.biyee.android.utility;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeBuilder;

class NewActivity$1 extends Thread
{
  public void run()
  {
    try
    {
      localDatagramSocket = new DatagramSocket(3702);
      localDatagramSocket.setSoTimeout(4000);
      InetAddress localInetAddress = InetAddress.getByName("239.255.255.250");
      if (localInetAddress == null)
        utility.logMessageAsync(this.this$0, "IetAddress.getByName() for multicast returns null");
      arrayOfByte1 = new byte[2048];
      byte[] arrayOfByte2 = this.this$0.getString(2131099672).replaceFirst("<a:MessageID>uuid:.+?</a:MessageID>", "<a:MessageID>uuid:" + UUID.randomUUID().toString() + "</a:MessageID>").getBytes();
      DatagramPacket localDatagramPacket1 = new DatagramPacket(arrayOfByte2, arrayOfByte2.length, localInetAddress, 3702);
      localDatagramSocket.send(localDatagramPacket1);
      localDatagramSocket.send(localDatagramPacket1);
      localDatagramSocket.send(localDatagramPacket1);
      localArrayList = new ArrayList();
      if (this.this$0._bDisposed)
      {
        localDatagramSocket.disconnect();
        localDatagramSocket.close();
        super.run();
        return;
      }
    }
    catch (Exception localException1)
    {
      while (true)
      {
        try
        {
          DatagramSocket localDatagramSocket;
          byte[] arrayOfByte1;
          ArrayList localArrayList;
          DatagramPacket localDatagramPacket2 = new DatagramPacket(arrayOfByte1, arrayOfByte1.length);
          localDatagramSocket.receive(localDatagramPacket2);
          this.this$0.sResponseDiscovery = new String(localDatagramPacket2.getData());
          InputNode localInputNode = NodeBuilder.read(new StringReader(this.this$0.sResponseDiscovery));
          EnvelopeProbeMatches localEnvelopeProbeMatches = (EnvelopeProbeMatches)new Persister().read(EnvelopeProbeMatches.class, localInputNode);
          if (localEnvelopeProbeMatches.BodyProbeMatches.ProbeMatches.listProbeMatches.size() <= 0)
            continue;
          ProbeMatch localProbeMatch = (ProbeMatch)localEnvelopeProbeMatches.BodyProbeMatches.ProbeMatches.listProbeMatches.get(0);
          if (localArrayList.contains(localProbeMatch.EndpointReference.Address))
            continue;
          localArrayList.add(localProbeMatch.EndpointReference.Address);
          NewActivity localNewActivity5 = this.this$0;
          localNewActivity5._iDeviceCount = (1 + localNewActivity5._iDeviceCount);
          if (this.this$0._bDisposed)
            continue;
          NewActivity localNewActivity6 = this.this$0;
          NewActivity.1.1 local1 = new NewActivity.1.1(this);
          localNewActivity6.runOnUiThread(local1);
        }
        catch (SocketTimeoutException localSocketTimeoutException)
        {
          if (this.this$0._bDisposed)
            continue;
          if (this.this$0._bDisposed)
            continue;
          NewActivity localNewActivity4 = this.this$0;
          NewActivity.1.2 local2 = new NewActivity.1.2(this);
          localNewActivity4.runOnUiThread(local2);
          continue;
          localException1 = localException1;
          utility.logMessageAsync(this.this$0, "Search ONVIF device error:" + localException1.getMessage());
          NewActivity localNewActivity1 = this.this$0;
          NewActivity.1.5 local5 = new NewActivity.1.5(this, localException1);
          localNewActivity1.runOnUiThread(local5);
          continue;
        }
        catch (Exception localException2)
        {
          if (this.this$0.sResponseDiscovery != null)
          {
            NewActivity localNewActivity3 = this.this$0;
            NewActivity.1.3 local3 = new NewActivity.1.3(this);
            utility.showConfirmationDialog(localNewActivity3, "Unable to decode the discovery response?  Do you want to report the response to help improve Onvifer?", local3);
            continue;
          }
        }
        if (this.this$0._bDisposed)
          continue;
        NewActivity localNewActivity2 = this.this$0;
        NewActivity.1.4 local4 = new NewActivity.1.4(this, localException2);
        localNewActivity2.runOnUiThread(local4);
      }
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity.1
 * JD-Core Version:    0.6.0
 */