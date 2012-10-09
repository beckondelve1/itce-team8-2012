package net.biyee.android.ONVIF;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import net.biyee.android.ICallback;
import net.biyee.android.utility;
import org.ksoap2.serialization.PropertyInfo;

public class RetrieveIPSecurityDeviceInfoAsync extends AsyncTask<String, String, ONVIFDevice>
{
  public ICallback callback;
  public Context context;
  public ONVIFDevice od;
  public ProgressDialog pd;

  public RetrieveIPSecurityDeviceInfoAsync(ICallback paramICallback, ProgressDialog paramProgressDialog, Context paramContext)
  {
    this.pd = paramProgressDialog;
    this.callback = paramICallback;
    this.context = paramContext;
  }

  protected ONVIFDevice doInBackground(String[] paramArrayOfString)
  {
    String[] arrayOfString1 = new String[1];
    arrayOfString1[0] = "Preparing ONVIF web service query...";
    publishProgress(arrayOfString1);
    this.od = new ONVIFDevice();
    this.od.sAddress = paramArrayOfString[0];
    this.od.sUserName = paramArrayOfString[1];
    this.od.sPassword = paramArrayOfString[2];
    String str1 = "http://" + this.od.sAddress + "/onvif/device_service";
    try
    {
      String[] arrayOfString3 = new String[1];
      arrayOfString3[0] = "Request GetDeviceInformation has been sent. Waiting for response and processing...";
      publishProgress(arrayOfString3);
      EnvelopeGetDeviceInformationResponse localEnvelopeGetDeviceInformationResponse = (EnvelopeGetDeviceInformationResponse)utilityONVIF.callSOAPService(EnvelopeGetDeviceInformationResponse.class, "http://www.onvif.org/ver10/device/wsdl", "GetDeviceInformation", str1, this.od.sUserName, this.od.sPassword, null);
      if (localEnvelopeGetDeviceInformationResponse != null)
      {
        this.od.di = localEnvelopeGetDeviceInformationResponse.BodyGetDeviceInformationResponse.GetDeviceInformationResponse;
        String[] arrayOfString4 = new String[1];
        arrayOfString4[0] = "GetDeviceInformation responsse has been received and processed";
        publishProgress(arrayOfString4);
        PropertyInfo localPropertyInfo = new PropertyInfo();
        localPropertyInfo.setName("Category");
        localPropertyInfo.setValue("All");
        localPropertyInfo.setType(String.class);
        String[] arrayOfString5 = new String[1];
        arrayOfString5[0] = "Request GetCapabilities has been sent. Waiting for response and processing...";
        publishProgress(arrayOfString5);
        String str2 = this.od.sUserName;
        String str3 = this.od.sPassword;
        PropertyInfo[] arrayOfPropertyInfo = new PropertyInfo[1];
        arrayOfPropertyInfo[0] = localPropertyInfo;
        EnvelopGetCapabilitiesResponse localEnvelopGetCapabilitiesResponse = (EnvelopGetCapabilitiesResponse)utilityONVIF.callSOAPService(EnvelopGetCapabilitiesResponse.class, "http://www.onvif.org/ver10/device/wsdl", "GetCapabilities", str1, str2, str3, arrayOfPropertyInfo);
        if (localEnvelopGetCapabilitiesResponse == null)
          break label781;
        this.od.Capabilities = localEnvelopGetCapabilitiesResponse.BodyGetCapabilitiesResponse.GetCapabilitiesResponse.Capabilities;
        String[] arrayOfString6 = new String[1];
        arrayOfString6[0] = "GetCapabilities responsse has been received and processed";
        publishProgress(arrayOfString6);
        String str4 = utilityONVIF.GetCorrectedONVIFUrl(this.od.sAddress, this.od.Capabilities.Media.XAddr);
        String[] arrayOfString7 = new String[1];
        arrayOfString7[0] = "Request GetProfiles has been sent. Waiting for response and processing...";
        publishProgress(arrayOfString7);
        EnvelopGetProfilesResponse localEnvelopGetProfilesResponse = (EnvelopGetProfilesResponse)utilityONVIF.callSOAPService(EnvelopGetProfilesResponse.class, "http://www.onvif.org/ver10/media/wsdl", "GetProfiles", str4, this.od.sUserName, this.od.sPassword, null);
        if (localEnvelopGetProfilesResponse == null)
          break label800;
        this.od.listProfiles = localEnvelopGetProfilesResponse.BodyGetProfilesResponse.GetProfilesResponse.listProfiles;
        String[] arrayOfString8 = new String[1];
        arrayOfString8[0] = "GetProfiles responsse has been received and processed";
        publishProgress(arrayOfString8);
        String[] arrayOfString9 = new String[1];
        arrayOfString9[0] = "Request GetVideoEncoderConfigurations has been sent. Waiting for response and processing...";
        publishProgress(arrayOfString9);
        EnvelopeGetVideoEncoderConfigurationsResponse localEnvelopeGetVideoEncoderConfigurationsResponse = (EnvelopeGetVideoEncoderConfigurationsResponse)utilityONVIF.callSOAPService(EnvelopeGetVideoEncoderConfigurationsResponse.class, "http://www.onvif.org/ver10/media/wsdl", "GetVideoEncoderConfigurations", str4, this.od.sUserName, this.od.sPassword, null);
        if (localEnvelopeGetVideoEncoderConfigurationsResponse == null)
          break label819;
        this.od.listVideoEncoderConfigurations = localEnvelopeGetVideoEncoderConfigurationsResponse.BodyGetGetVideoEncoderConfigurationsResponse.GetVideoEncoderConfigurationsResponse.listVideoEncoderConfiguration;
        String[] arrayOfString10 = new String[1];
        arrayOfString10[0] = "GetVideoEncoderConfigurations responsse has been received and processed";
        publishProgress(arrayOfString10);
        String[] arrayOfString11 = new String[1];
        arrayOfString11[0] = "Request GetAudioEncoderConfigurations has been sent. Waiting for response and processing...";
        publishProgress(arrayOfString11);
        EnvelopeGetAudioEncoderConfigurationsResponse localEnvelopeGetAudioEncoderConfigurationsResponse = (EnvelopeGetAudioEncoderConfigurationsResponse)utilityONVIF.callSOAPService(EnvelopeGetAudioEncoderConfigurationsResponse.class, "http://www.onvif.org/ver10/media/wsdl", "GetAudioEncoderConfigurations", str4, this.od.sUserName, this.od.sPassword, null);
        if (localEnvelopeGetAudioEncoderConfigurationsResponse == null)
          break label837;
        this.od.listAudioEncoderConfigurations = localEnvelopeGetAudioEncoderConfigurationsResponse.BodyGetAudioEncoderConfigurationsResponse.GetAudioEncoderConfigurationsResponse.listAudioEncoderConfiguration;
        String[] arrayOfString16 = new String[1];
        arrayOfString16[0] = "GetAudioEncoderConfigurations responsse has been received and processed";
        publishProgress(arrayOfString16);
      }
      while (true)
      {
        if (this.od.Capabilities.PTZ != null)
        {
          String str5 = utilityONVIF.GetCorrectedONVIFUrl(this.od.sAddress, this.od.Capabilities.PTZ.XAddr);
          String[] arrayOfString14 = new String[1];
          arrayOfString14[0] = "Request GetConfigurations has been sent. Waiting for response and processing...";
          publishProgress(arrayOfString14);
          EnvelopPTZGetConfigurationsResponse localEnvelopPTZGetConfigurationsResponse = (EnvelopPTZGetConfigurationsResponse)utilityONVIF.callSOAPService(EnvelopPTZGetConfigurationsResponse.class, "http://www.onvif.org/ver20/ptz/wsdl", "GetConfigurations", str5, this.od.sUserName, this.od.sPassword, null);
          if (localEnvelopPTZGetConfigurationsResponse == null)
            break;
          this.od.listPTZConfigurations = localEnvelopPTZGetConfigurationsResponse.BodyPTZGetConfigurationsResponse.GetConfigurationsResponse.listPTZConfiguration;
          String[] arrayOfString15 = new String[1];
          arrayOfString15[0] = "GetConfigurations responsse has been received and processed";
          publishProgress(arrayOfString15);
        }
        String[] arrayOfString13 = new String[1];
        arrayOfString13[0] = "All information has been retrieved.";
        publishProgress(arrayOfString13);
        ONVIFDevice localONVIFDevice = this.od;
        while (true)
        {
          return localONVIFDevice;
          this.od.sError = "GetDeviceInformation failed";
          localONVIFDevice = this.od;
          continue;
          label781: this.od.sError = "GetCapabilities failed";
          localONVIFDevice = this.od;
          continue;
          label800: this.od.sError = "GetProfiles failed";
          localONVIFDevice = this.od;
          continue;
          label819: this.od.sError = "GetVideoEncoderConfigurations";
          localONVIFDevice = this.od;
        }
        label837: String[] arrayOfString12 = new String[1];
        arrayOfString12[0] = "Audio is not available.";
        publishProgress(arrayOfString12);
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        utility.logMessageAsync(this.context, "Error in RetrieveIPSecurityDeviceInfoAsync: " + localException.getMessage());
        this.od.sError = "Failed in retrieving device information";
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = "Failed in retrieving device information";
        publishProgress(arrayOfString2);
        continue;
        this.od.Capabilities.PTZ = null;
      }
    }
  }

  protected void onPostExecute(ONVIFDevice paramONVIFDevice)
  {
    this.callback.callback(this.od);
    super.onPostExecute(paramONVIFDevice);
  }

  protected void onProgressUpdate(String[] paramArrayOfString)
  {
    if (this.pd != null)
      this.pd.setMessage(paramArrayOfString[0]);
    super.onProgressUpdate(paramArrayOfString);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.RetrieveIPSecurityDeviceInfoAsync
 * JD-Core Version:    0.6.0
 */