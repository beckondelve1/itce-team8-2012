package net.biyee.android.ONVIF;

import android.util.Base64;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.http.impl.auth.DigestScheme;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SOAPHttpTransport extends HttpTransportSE
{
  static String sHeaderTemplate = "DQo8czpIZWFkZXI+DQoJPFNlY3VyaXR5IHM6bXVzdFVuZGVyc3RhbmQ9IjEiDQoJCXhtbG5zPSJodHRwOi8vZG9jcy5vYXNpcy1vcGVuLm9yZy93c3MvMjAwNC8wMS9vYXNpcy0yMDA0MDEtd3NzLXdzc2VjdXJpdHktc2VjZXh0LTEuMC54c2QiPg0KCQk8VXNlcm5hbWVUb2tlbg0KCQkJeG1sbnM9Imh0dHA6Ly9kb2NzLm9hc2lzLW9wZW4ub3JnL3dzcy8yMDA0LzAxL29hc2lzLTIwMDQwMS13c3Mtd3NzZWN1cml0eS1zZWNleHQtMS4wLnhzZCI+DQoJCQk8VXNlcm5hbWU+W3VzZXJuYW1lXTwvVXNlcm5hbWU+DQoJCQk8UGFzc3dvcmQNCgkJCQlUeXBlPSJodHRwOi8vZG9jcy5vYXNpcy1vcGVuLm9yZy93c3MvMjAwNC8wMS9vYXNpcy0yMDA0MDEtd3NzLXVzZXJuYW1lLXRva2VuLXByb2ZpbGUtMS4wI1Bhc3N3b3JkRGlnZXN0Ij5bcGFzc3dvcmRkaWdlc3RdPC9QYXNzd29yZD4NCgkJCTxOb25jZT5bbm9uY2VdPC9Ob25jZT4NCgkJCTxDcmVhdGVkDQoJCQkJeG1sbnM9Imh0dHA6Ly9kb2NzLm9hc2lzLW9wZW4ub3JnL3dzcy8yMDA0LzAxL29hc2lzLTIwMDQwMS13c3Mtd3NzZWN1cml0eS11dGlsaXR5LTEuMC54c2QiPltjcmVhdGVkXTwvQ3JlYXRlZD4NCgkJPC9Vc2VybmFtZVRva2VuPg0KCTwvU2VjdXJpdHk+DQo8L3M6SGVhZGVyPg0KDQo=";
  String sMethodName = null;
  String sParams = null;
  String sPassword;
  String sUserName;

  static
  {
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(SOAPHttpTransport.class.getClassLoader().getResourceAsStream("res/raw/header_ws__usernametoken.xml")));
      StringBuilder localStringBuilder = new StringBuilder();
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
        {
          sHeaderTemplate = localStringBuilder.toString();
          sHeaderTemplate = sHeaderTemplate.replaceAll("\t", "");
          sHeaderTemplate = sHeaderTemplate.replace("﻿", "");
          break;
        }
        localStringBuilder.append(str);
      }
    }
    catch (Exception localException)
    {
    }
  }

  public SOAPHttpTransport(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1);
    this.sUserName = paramString2;
    this.sPassword = paramString3;
  }

  public SOAPHttpTransport(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    super(paramString1);
    this.sUserName = paramString2;
    this.sPassword = paramString3;
    this.sParams = paramString4;
  }

  protected byte[] createRequestData(SoapEnvelope paramSoapEnvelope)
    throws IOException
  {
    Object localObject = new String(super.createRequestData(paramSoapEnvelope)).replaceAll("i:type=\".+?\"", "").replaceAll("n\\d:", "").replaceAll("xmlns:n\\d", "xmlns");
    if (this.sParams != null)
      localObject = ((String)localObject).replace("<PlaceHolder>PlaceHolder</PlaceHolder>", this.sParams);
    try
    {
      byte[] arrayOfByte1 = DigestScheme.createCnonce().substring(16).getBytes();
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      String str1 = localSimpleDateFormat.format(new Date());
      byte[] arrayOfByte2 = str1.getBytes();
      if (this.sPassword == null)
        this.sPassword = "";
      byte[] arrayOfByte3 = this.sPassword.getBytes();
      byte[] arrayOfByte4 = new byte[arrayOfByte1.length + arrayOfByte2.length + arrayOfByte3.length];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte4, 0, arrayOfByte1.length);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte4, arrayOfByte1.length, arrayOfByte2.length);
      System.arraycopy(arrayOfByte3, 0, arrayOfByte4, arrayOfByte1.length + arrayOfByte2.length, arrayOfByte3.length);
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localMessageDigest.update(arrayOfByte4);
      String str2 = new String(Base64.encode(localMessageDigest.digest(), 2));
      String str3 = ((String)localObject).replaceAll("<v:Header.*?/>", sHeaderTemplate.replace("[username]", this.sUserName).replace("[passworddigest]", str2).replace("[nonce]", Base64.encodeToString(arrayOfByte1, 2)).replace("[created]", str1));
      localObject = str3;
      return ((String)localObject).getBytes();
    }
    catch (Exception localException)
    {
      while (true)
        Log.d("createRequestData", localException.getMessage());
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.SOAPHttpTransport
 * JD-Core Version:    0.6.0
 */