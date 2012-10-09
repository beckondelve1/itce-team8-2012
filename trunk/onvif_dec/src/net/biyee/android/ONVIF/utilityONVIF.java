package net.biyee.android.ONVIF;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.biyee.android.utility;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserException;

public class utilityONVIF
{
  public static String GetCorrectedONVIFUrl(String paramString1, String paramString2)
    throws URISyntaxException
  {
    Object localObject;
    try
    {
      String str1 = paramString2.replace("\n", "");
      if ((str1.contains("rtsp:")) && (paramString1.contains(":")))
        paramString1 = paramString1.split(":")[0];
      Uri localUri = Uri.parse(str1);
      String str2 = localUri.getAuthority();
      if (localUri.getPort() == -1)
      {
        localObject = str1.replace(str2, paramString1);
      }
      else
      {
        String str3 = str2.split(":")[0];
        if (paramString1.contains(":"))
        {
          localObject = str1.replace(str3, paramString1.split(":")[0]);
        }
        else
        {
          String str4 = str1.replace(str3, paramString1);
          localObject = str4;
        }
      }
    }
    catch (Exception localException)
    {
      localObject = paramString2;
    }
    return (String)localObject;
  }

  public static void PanTilt(Context paramContext, ONVIFDevice paramONVIFDevice, Profiles paramProfiles, float paramFloat1, float paramFloat2)
  {
    if ((paramProfiles != null) && (paramProfiles.PTZConfiguration != null))
    {
      PTZConfiguration localPTZConfiguration = findPTZConfiguration(paramProfiles.PTZConfiguration.token, paramONVIFDevice.listPTZConfigurations);
      float f1 = localPTZConfiguration.PanTiltLimits.Range.XRange.Max - localPTZConfiguration.PanTiltLimits.Range.XRange.Min;
      float f2 = localPTZConfiguration.PanTiltLimits.Range.YRange.Max - localPTZConfiguration.PanTiltLimits.Range.YRange.Min;
      Vector2D localVector2D = new Vector2D();
      localVector2D.space = localPTZConfiguration.DefaultRelativePanTiltTranslationSpace;
      localVector2D.x = (paramFloat1 * f1);
      localVector2D.y = (paramFloat2 * f2);
      Vector1D localVector1D = new Vector1D();
      localVector1D.space = localPTZConfiguration.DefaultAbsoluteZoomPositionSpace;
      localVector1D.x = 0.0F;
      PropertyInfo localPropertyInfo1 = new PropertyInfo();
      localPropertyInfo1.setName("ProfileToken");
      localPropertyInfo1.setValue(paramProfiles.token);
      localPropertyInfo1.setType(String.class);
      PTZVector localPTZVector = new PTZVector();
      localPTZVector.PanTilt = localVector2D;
      localPTZVector.Zoom = localVector1D;
      PropertyInfo localPropertyInfo2 = new PropertyInfo();
      localPropertyInfo2.setName("Translation");
      localPropertyInfo2.setValue(localPTZVector);
      localPropertyInfo2.setType(PTZVector.class);
      PTZSpeed localPTZSpeed = ((PTZConfiguration)paramONVIFDevice.listPTZConfigurations.get(0)).DefaultPTZSpeed;
      PropertyInfo localPropertyInfo3 = new PropertyInfo();
      localPropertyInfo3.setName("Speed");
      localPropertyInfo3.setValue(localPTZSpeed);
      localPropertyInfo3.setType(PTZSpeed.class);
      String str1 = paramProfiles.token;
      try
      {
        String str2 = GetCorrectedONVIFUrl(paramONVIFDevice.sAddress, paramONVIFDevice.Capabilities.PTZ.XAddr);
        if (Looper.getMainLooper().equals(Looper.myLooper()))
        {
          new utilityONVIF.2(str2, paramONVIFDevice, str1, localPTZVector, localPTZSpeed, paramContext).start();
        }
        else
        {
          String str3 = paramONVIFDevice.sUserName;
          String str4 = paramONVIFDevice.sPassword;
          SoapParam[] arrayOfSoapParam = new SoapParam[3];
          arrayOfSoapParam[0] = new SoapParam(str1, "ProfileToken");
          arrayOfSoapParam[1] = new SoapParam(localPTZVector, "Translation");
          arrayOfSoapParam[2] = new SoapParam(localPTZSpeed, "Speed");
          callSOAPServiceEx(null, "http://www.onvif.org/ver20/ptz/wsdl", "RelativeMove", str2, str3, str4, arrayOfSoapParam);
        }
      }
      catch (Exception localException)
      {
        utility.logMessageAsync(paramContext, "PanTilt() error: " + localException.getMessage());
      }
    }
  }

  public static Bitmap RetrieveSnapshot(int paramInt, ONVIFDevice paramONVIFDevice, Profiles paramProfiles, ProgressDialog paramProgressDialog)
  {
    Object localObject = null;
    if (paramProfiles != null);
    try
    {
      paramProgressDialog.setMessage("Retrieving a snapshot...");
      try
      {
        label13: String str = getURLSnapshot(paramONVIFDevice, paramProfiles);
        if (str != null)
        {
          localObject = utility.loadBitmap(str, paramONVIFDevice.sUserName, paramONVIFDevice.sPassword);
          if (localObject != null)
          {
            int i = ((Bitmap)localObject).getHeight() * ((Bitmap)localObject).getWidth();
            if (i > paramInt)
            {
              double d = Math.sqrt(paramInt / i);
              Bitmap localBitmap = Bitmap.createScaledBitmap((Bitmap)localObject, (int)(d * ((Bitmap)localObject).getWidth()), (int)(d * ((Bitmap)localObject).getHeight()), true);
              localObject = localBitmap;
            }
          }
        }
        return localObject;
      }
      catch (Exception localException2)
      {
        while (true)
          Log.d("Onvifer", localException2.getMessage());
      }
    }
    catch (Exception localException1)
    {
      break label13;
    }
  }

  public static void Zoom(Context paramContext, ONVIFDevice paramONVIFDevice, Profiles paramProfiles, float paramFloat)
  {
    if ((paramProfiles != null) && (paramProfiles.PTZConfiguration != null))
      try
      {
        PTZConfiguration localPTZConfiguration = findPTZConfiguration(paramProfiles.PTZConfiguration.token, paramONVIFDevice.listPTZConfigurations);
        float f = localPTZConfiguration.ZoomLimits.Range.XRange.Max - localPTZConfiguration.ZoomLimits.Range.XRange.Min;
        Vector1D localVector1D = new Vector1D();
        localVector1D.space = localPTZConfiguration.DefaultRelativePanTiltTranslationSpace;
        localVector1D.x = (paramFloat * f);
        Vector2D localVector2D = new Vector2D();
        localVector2D.space = localPTZConfiguration.DefaultRelativePanTiltTranslationSpace;
        localVector2D.x = 0.0F;
        localVector2D.y = 0.0F;
        PTZVector localPTZVector = new PTZVector();
        localPTZVector.PanTilt = localVector2D;
        localPTZVector.Zoom = localVector1D;
        String str1 = paramProfiles.token;
        String str2 = GetCorrectedONVIFUrl(paramONVIFDevice.sAddress, paramONVIFDevice.Capabilities.PTZ.XAddr);
        if (Looper.getMainLooper().equals(Looper.myLooper()))
        {
          new utilityONVIF.3(str2, paramONVIFDevice, str1, localPTZVector, paramContext).start();
        }
        else
        {
          String str3 = paramONVIFDevice.sUserName;
          String str4 = paramONVIFDevice.sPassword;
          SoapParam[] arrayOfSoapParam = new SoapParam[3];
          arrayOfSoapParam[0] = new SoapParam(str1, "ProfileToken");
          arrayOfSoapParam[1] = new SoapParam(localPTZVector, "Translation");
          arrayOfSoapParam[2] = new SoapParam(((PTZConfiguration)paramONVIFDevice.listPTZConfigurations.get(0)).DefaultPTZSpeed, "Speed");
          callSOAPServiceEx(null, "http://www.onvif.org/ver20/ptz/wsdl", "RelativeMove", str2, str3, str4, arrayOfSoapParam);
        }
      }
      catch (Exception localException)
      {
        utility.logMessageAsync(paramContext, "Zoom() error: " + localException.getMessage());
      }
  }

  public static <T> T callSOAPService(Class<? extends T> paramClass, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, PropertyInfo[] paramArrayOfPropertyInfo)
    throws Exception
  {
    Object localObject1 = null;
    String str = paramString1 + "/" + paramString2;
    SoapObject localSoapObject = new SoapObject(paramString1, paramString2);
    int i;
    int j;
    if (paramArrayOfPropertyInfo != null)
    {
      i = paramArrayOfPropertyInfo.length;
      j = 0;
    }
    while (true)
    {
      SoapSerializationEnvelope localSoapSerializationEnvelope;
      SOAPHttpTransport localSOAPHttpTransport;
      if (j >= i)
      {
        localSoapSerializationEnvelope = new SoapSerializationEnvelope(120);
        localSoapSerializationEnvelope.addMapping("http://www.onvif.org/ver10/schema", "Transport", Transport.class);
        localSoapSerializationEnvelope.dotNet = true;
        localSoapSerializationEnvelope.implicitTypes = true;
        localSoapSerializationEnvelope.setAddAdornments(false);
        localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
        localSOAPHttpTransport = new SOAPHttpTransport(paramString3, paramString4, paramString5);
        localSOAPHttpTransport.debug = true;
      }
      try
      {
        if (Looper.getMainLooper().equals(Looper.myLooper()))
        {
          utilityONVIF.1 local1 = new utilityONVIF.1(localSOAPHttpTransport, str, localSoapSerializationEnvelope);
          local1.start();
          local1.join();
        }
        while (true)
        {
          if (localSOAPHttpTransport.responseDump.contains(paramString2 + "Response"))
          {
            StringReader localStringReader = new StringReader(localSOAPHttpTransport.responseDump.replaceAll("<tt:Encoding>0</tt:Encoding>", "<tt:Encoding>JPEG</tt:Encoding>"));
            Object localObject2 = new Persister().read(paramClass, localStringReader);
            localObject1 = localObject2;
          }
          return localObject1;
          localSoapObject.addProperty(paramArrayOfPropertyInfo[j]);
          j++;
          break;
          localSOAPHttpTransport.call(str, localSoapSerializationEnvelope);
        }
      }
      catch (XmlPullParserException localXmlPullParserException)
      {
        while (true)
          utility.logd("callSOAPService", localXmlPullParserException.getMessage());
      }
      catch (IOException localIOException)
      {
        while (true)
          utility.logd("callSOAPService", localIOException.getMessage());
      }
      catch (Exception localException)
      {
        while (true)
        {
          utility.logd("Onvifer", localException.getMessage());
          localObject1 = null;
        }
      }
    }
  }

  public static <T> T callSOAPServiceEx(Class<? extends T> paramClass, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, SoapParam[] paramArrayOfSoapParam)
    throws Exception
  {
    String str1 = paramString1 + "/" + paramString2;
    SoapObject localSoapObject = new SoapObject(paramString1, paramString2);
    Object localObject1 = null;
    String str2 = null;
    Persister localPersister;
    StringWriter localStringWriter;
    int i;
    int j;
    if ((paramArrayOfSoapParam != null) && (paramArrayOfSoapParam.length > 0))
    {
      CamelCaseStyle localCamelCaseStyle = new CamelCaseStyle(true);
      Format localFormat = new Format(localCamelCaseStyle);
      localPersister = new Persister(localFormat);
      localStringWriter = new StringWriter();
      i = paramArrayOfSoapParam.length;
      j = 0;
    }
    while (true)
    {
      String str3;
      int m;
      SoapSerializationEnvelope localSoapSerializationEnvelope;
      SOAPHttpTransport localSOAPHttpTransport;
      if (j >= i)
      {
        str3 = localStringWriter.toString();
        localStringWriter.close();
        int k = paramArrayOfSoapParam.length;
        m = 0;
        if (m < k)
          break label541;
        str2 = str3.replace("RTPUnicast</Stream>", "RTP-Unicast</Stream>").replace("RTPMulticast</Stream>", "RTP-Multicast</Stream>");
        PropertyInfo localPropertyInfo = new PropertyInfo();
        localPropertyInfo.setName("PlaceHolder");
        localPropertyInfo.setValue("PlaceHolder");
        localPropertyInfo.setType(String.class);
        localSoapObject.addProperty(localPropertyInfo);
        localSoapSerializationEnvelope = new SoapSerializationEnvelope(120);
        localSoapSerializationEnvelope.addMapping("http://www.onvif.org/ver10/schema", "Transport", Transport.class);
        localSoapSerializationEnvelope.dotNet = true;
        localSoapSerializationEnvelope.implicitTypes = true;
        localSoapSerializationEnvelope.setAddAdornments(false);
        localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
        localSOAPHttpTransport = new SOAPHttpTransport(paramString3, paramString4, paramString5, str2);
        localSOAPHttpTransport.debug = true;
      }
      try
      {
        localSOAPHttpTransport.call(str1, localSoapSerializationEnvelope);
        if ((paramClass != null) && (localSOAPHttpTransport.responseDump.contains(paramString2 + "Response")))
        {
          DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
          localDocumentBuilderFactory.setNamespaceAware(true);
          DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
          InputSource localInputSource = new InputSource();
          localInputSource.setCharacterStream(new StringReader(localSOAPHttpTransport.responseDump));
          NodeList localNodeList1 = localDocumentBuilder.parse(localInputSource).getElementsByTagNameNS("http://www.w3.org/2003/05/soap-envelope", "Body");
          if ((localNodeList1 != null) && (localNodeList1.getLength() == 1))
          {
            NodeList localNodeList2 = localNodeList1.item(0).getChildNodes();
            if ((localNodeList2 != null) && (localNodeList2.getLength() == 1))
            {
              Transformer localTransformer = TransformerFactory.newInstance().newTransformer();
              DOMSource localDOMSource = new DOMSource(localNodeList2.item(0));
              ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
              StreamResult localStreamResult = new StreamResult(localByteArrayOutputStream);
              localTransformer.transform(localDOMSource, localStreamResult);
              StringReader localStringReader = new StringReader(localByteArrayOutputStream.toString());
              Object localObject2 = new Persister().read(paramClass, localStringReader);
              localObject1 = localObject2;
            }
          }
        }
        return localObject1;
        localPersister.write(paramArrayOfSoapParam[j].oParam, localStringWriter);
        j++;
        continue;
        label541: SoapParam localSoapParam = paramArrayOfSoapParam[m];
        String str4 = localSoapParam.oParam.getClass().getSimpleName();
        String str6;
        if (str4.equalsIgnoreCase("String"))
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = localSoapParam.sName;
          arrayOfObject[1] = localSoapParam.sName;
          str6 = String.format("<%s>$2</%s>", arrayOfObject);
        }
        String str5;
        for (str3 = str3.replaceFirst("<(S|s)tring>(.+?)</(S|s)tring>", str6); ; str3 = str3.replace(str4, str5))
        {
          m++;
          break;
          str5 = localSoapParam.sName;
        }
      }
      catch (Exception localException)
      {
        while (true)
          Log.d("Onvifer", localException.getMessage());
      }
    }
  }

  public static AudioEncoderConfiguration findAudioEncoderConfiguration(String paramString, List<AudioEncoderConfiguration> paramList)
  {
    Object localObject = null;
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localObject;
      AudioEncoderConfiguration localAudioEncoderConfiguration = (AudioEncoderConfiguration)localIterator.next();
      if (!localAudioEncoderConfiguration.token.equals(paramString))
        continue;
      localObject = localAudioEncoderConfiguration;
    }
  }

  public static Profiles findOptimalProfile(int paramInt1, int paramInt2, VideoEncoding paramVideoEncoding, List<Profiles> paramList)
  {
    Object localObject = null;
    int i = 2147483647;
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localObject;
      Profiles localProfiles = (Profiles)localIterator.next();
      if ((localProfiles.VideoEncoderConfiguration == null) || (localProfiles.VideoEncoderConfiguration.Encoding != paramVideoEncoding))
        continue;
      int j = localProfiles.VideoEncoderConfiguration.Resolution.Width * localProfiles.VideoEncoderConfiguration.Resolution.Height;
      if (j > paramInt2)
        continue;
      int k = Math.abs(j - paramInt1);
      if (k >= i)
        continue;
      i = k;
      localObject = localProfiles;
    }
  }

  public static Profiles findOptimalProfile(int paramInt, List<Profiles> paramList)
  {
    Object localObject = null;
    int i = 2147483647;
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localObject;
      Profiles localProfiles = (Profiles)localIterator.next();
      if (localProfiles.VideoEncoderConfiguration == null)
        continue;
      int j = localProfiles.VideoEncoderConfiguration.Resolution.Width * localProfiles.VideoEncoderConfiguration.Resolution.Height;
      if (j > 2147483647)
        continue;
      int k = Math.abs(j - paramInt);
      if (k >= i)
        continue;
      i = k;
      localObject = localProfiles;
    }
  }

  public static Profiles findOptimalProfile(int paramInt, VideoEncoding paramVideoEncoding, List<Profiles> paramList)
  {
    return findOptimalProfile(paramInt, 2147483647, paramVideoEncoding, paramList);
  }

  public static PTZConfiguration findPTZConfiguration(String paramString, List<PTZConfiguration> paramList)
  {
    Object localObject = null;
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localObject;
      PTZConfiguration localPTZConfiguration = (PTZConfiguration)localIterator.next();
      if (!localPTZConfiguration.token.equals(paramString))
        continue;
      localObject = localPTZConfiguration;
    }
  }

  public static Profiles findProfile(String paramString, List<Profiles> paramList)
  {
    Object localObject = null;
    try
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Profiles localProfiles = (Profiles)localIterator.next();
        boolean bool = localProfiles.token.equals(paramString);
        if (!bool)
          continue;
        localObject = localProfiles;
      }
    }
    catch (Exception localException)
    {
      utility.logd("findProfile", localException.getMessage());
    }
    return localObject;
  }

  public static VideoEncoderConfiguration findVideoEncoderConfiguration(String paramString, List<VideoEncoderConfiguration> paramList)
  {
    Object localObject = null;
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localObject;
      VideoEncoderConfiguration localVideoEncoderConfiguration = (VideoEncoderConfiguration)localIterator.next();
      if (!localVideoEncoderConfiguration.token.equals(paramString))
        continue;
      localObject = localVideoEncoderConfiguration;
    }
  }

  public static ListDevice getListDevice(Context paramContext)
  {
    ListDevice localListDevice = new ListDevice();
    Persister localPersister = new Persister();
    File localFile1;
    if (!paramContext.getFileStreamPath("ListDevice.xml").exists())
      localFile1 = new File(paramContext.getFilesDir(), "ListDevice.xml");
    try
    {
      localPersister.write(localListDevice, localFile1);
      localFile2 = new File(paramContext.getFilesDir(), "ListDevice.xml");
    }
    catch (Exception localException1)
    {
      try
      {
        File localFile2;
        localListDevice = (ListDevice)localPersister.read(ListDevice.class, localFile2);
        return localListDevice;
        localException1 = localException1;
        utility.logMessageAsync(paramContext, "Error in creating ListDevice.xml: " + localException1.getMessage());
      }
      catch (Exception localException2)
      {
        while (true)
          utility.logMessageAsync(paramContext, "Error in reading ListDevice.xml: " + localException2.getMessage());
      }
    }
  }

  public static ONVIFDevice getONVIFDevice(Context paramContext, String paramString)
  {
    try
    {
      File localFile = new File(paramContext.getDir("Devices", 0), paramString + ".xml");
      localONVIFDevice = (ONVIFDevice)new Persister().read(ONVIFDevice.class, localFile);
      return localONVIFDevice;
    }
    catch (Exception localException)
    {
      while (true)
      {
        utility.logMessageAsync(paramContext, "Error in getONVIFDevice: " + localException.getMessage());
        ONVIFDevice localONVIFDevice = null;
      }
    }
  }

  public static String getURLSnapshot(ONVIFDevice paramONVIFDevice, Profiles paramProfiles)
    throws URISyntaxException, Exception
  {
    String str1 = GetCorrectedONVIFUrl(paramONVIFDevice.sAddress, paramONVIFDevice.Capabilities.Media.XAddr);
    PropertyInfo localPropertyInfo = new PropertyInfo();
    localPropertyInfo.setName("ProfileToken");
    localPropertyInfo.setValue(paramProfiles.token);
    localPropertyInfo.setType(String.class);
    String str2 = paramONVIFDevice.sUserName;
    String str3 = paramONVIFDevice.sPassword;
    PropertyInfo[] arrayOfPropertyInfo = new PropertyInfo[1];
    arrayOfPropertyInfo[0] = localPropertyInfo;
    EnvelopGetSnapshotUriResponse localEnvelopGetSnapshotUriResponse = (EnvelopGetSnapshotUriResponse)callSOAPService(EnvelopGetSnapshotUriResponse.class, "http://www.onvif.org/ver10/media/wsdl", "GetSnapshotUri", str1, str2, str3, arrayOfPropertyInfo);
    if (localEnvelopGetSnapshotUriResponse != null);
    for (String str4 = GetCorrectedONVIFUrl(paramONVIFDevice.sAddress, localEnvelopGetSnapshotUriResponse.BodyGetSnapshotUriResponse.GetSnapshotUriResponse.MediaUri.Uri); ; str4 = null)
    {
      return str4;
      paramONVIFDevice.sError = "GetSnapshotUri failed";
    }
  }

  public static Float getZoom(Context paramContext, ONVIFDevice paramONVIFDevice, Profiles paramProfiles)
  {
    findPTZConfiguration(paramProfiles.PTZConfiguration.token, paramONVIFDevice.listPTZConfigurations);
    String str1 = paramProfiles.token;
    try
    {
      String str2 = GetCorrectedONVIFUrl(paramONVIFDevice.sAddress, paramONVIFDevice.Capabilities.PTZ.XAddr);
      String str3 = paramONVIFDevice.sUserName;
      String str4 = paramONVIFDevice.sPassword;
      SoapParam[] arrayOfSoapParam = new SoapParam[1];
      arrayOfSoapParam[0] = new SoapParam(str1, "ProfileToken");
      GetStatusResponse localGetStatusResponse = (GetStatusResponse)callSOAPServiceEx(GetStatusResponse.class, "http://www.onvif.org/ver20/ptz/wsdl", "GetStatus", str2, str3, str4, arrayOfSoapParam);
      if ((localGetStatusResponse != null) && (localGetStatusResponse.PTZStatus.Position != null) && (localGetStatusResponse.PTZStatus.Position.Zoom != null))
      {
        Float localFloat2 = Float.valueOf(localGetStatusResponse.PTZStatus.Position.Zoom.x);
        localFloat1 = localFloat2;
        return localFloat1;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        utility.logMessageAsync(paramContext, "PanTilt() error: " + localException.getMessage());
        Float localFloat1 = null;
      }
    }
  }

  public static void saveDeviceInfo(Context paramContext, ONVIFDevice paramONVIFDevice)
    throws Exception
  {
    File localFile = new File(paramContext.getDir("Devices", 0), paramONVIFDevice.uid.toString() + ".xml");
    new Persister().write(paramONVIFDevice, localFile);
  }

  public static void saveListDevice(Context paramContext, ListDevice paramListDevice)
  {
    Persister localPersister = new Persister();
    File localFile = new File(paramContext.getFilesDir(), "ListDevice.xml");
    try
    {
      localPersister.write(paramListDevice, localFile);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        utility.logMessageAsync(paramContext, "Error in saving ListDevice.xml: " + localException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.utilityONVIF
 * JD-Core Version:    0.6.0
 */