package net.biyee.onvifer;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.widget.Button;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.biyee.android.utility;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

class OnviferActivity$6 extends AsyncTask<Void, Void, String>
{
  protected String doInBackground(Void[] paramArrayOfVoid)
  {
    PackageManager localPackageManager = this.this$0.getPackageManager();
    Object localObject = null;
    try
    {
      PackageInfo localPackageInfo = localPackageManager.getPackageInfo(this.this$0.getPackageName(), 0);
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      BasicResponseHandler localBasicResponseHandler = new BasicResponseHandler();
      HttpGet localHttpGet1;
      if (localPackageInfo.versionCode % 2 == 1)
        localHttpGet1 = new HttpGet("https://play.google.com/store/apps/details?id=net.biyee.onvifer");
      while (true)
      {
        try
        {
          String str1 = (String)localDefaultHttpClient.execute(localHttpGet1, localBasicResponseHandler);
          Matcher localMatcher1 = Pattern.compile("softwareVersion\">(\\d+?\\.\\d+?)</dd>", 8).matcher(str1);
          if (!localMatcher1.find())
            continue;
          localMatcher1.find(0);
          if (Integer.parseInt(localMatcher1.group(1).replace(".", "")) <= localPackageInfo.versionCode)
            continue;
          String str2 = localMatcher1.group(1);
          localObject = str2;
          label144: return localObject;
        }
        catch (ClientProtocolException localClientProtocolException1)
        {
          utility.logMessageAsync(this.this$0, "Error in retrieving the Onvifer page of Googl Playe: " + localClientProtocolException1.getMessage());
          continue;
        }
        catch (IOException localIOException1)
        {
          utility.logMessageAsync(this.this$0, "Error in retrieving the Onvifer page of Googl Playe: " + localIOException1.getMessage());
          continue;
        }
        catch (Exception localException1)
        {
          utility.logMessageAsync(this.this$0, "Error in retrieving the Onvifer page of Googl Playe: " + localException1.getMessage());
          continue;
        }
        HttpGet localHttpGet2 = new HttpGet("http://www.amazon.com/Biyee-SciTech-Inc-Onvifer/dp/B007OW2WZI");
        try
        {
          String str3 = (String)localDefaultHttpClient.execute(localHttpGet2, localBasicResponseHandler);
          Matcher localMatcher2 = Pattern.compile("<b>Version:</b>.*?(\\d+?\\.\\d+?)\\D", 40).matcher(str3);
          if (!localMatcher2.find())
            continue;
          localMatcher2.find(0);
          if (Integer.parseInt(localMatcher2.group(1).replace(".", "")) <= localPackageInfo.versionCode)
            continue;
          String str4 = localMatcher2.group(1);
          localObject = str4;
        }
        catch (ClientProtocolException localClientProtocolException2)
        {
          utility.logMessageAsync(this.this$0, "Error in retrieving the Onvifer page of Amazon Appstore: " + localClientProtocolException2.getMessage());
        }
        catch (IOException localIOException2)
        {
          utility.logMessageAsync(this.this$0, "Error in retrieving the Onvifer page of Amazon Appstore: " + localIOException2.getMessage());
        }
        catch (Exception localException2)
        {
          utility.logMessageAsync(this.this$0, "Error in retrieving the Onvifer page of Amazon Appstore: " + localException2.getMessage());
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label144;
    }
  }

  protected void onPostExecute(String paramString)
  {
    if (paramString != null)
    {
      Button localButton = (Button)this.this$0.findViewById(2131296281);
      localButton.setVisibility(0);
      localButton.setText("Newer version (" + paramString + ") is available.");
    }
    super.onPostExecute(paramString);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.6
 * JD-Core Version:    0.6.0
 */