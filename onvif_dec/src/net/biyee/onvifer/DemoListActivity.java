package net.biyee.onvifer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import net.biyee.android.ActivityWebView;
import net.biyee.android.utility;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class DemoListActivity extends Activity
{
  boolean bCanceled = false;
  boolean bDisposed = false;
  ProgressDialog pd;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903043);
    findViewById(2131296262).setVisibility(8);
    ((TextView)findViewById(2131296263)).setText("New > Demo");
    this.pd = new ProgressDialog(this);
    this.pd.setMessage("Retrieving the demo list...");
    this.pd.setProgressStyle(0);
    this.pd.setCancelable(false);
    this.pd.setButton(-1, "Cancel", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        DemoListActivity.this.bCanceled = true;
      }
    });
    this.pd.show();
    ((Button)findViewById(2131296267)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(DemoListActivity.this, ActivityWebView.class);
        localIntent.putExtra("url", "https://www.ipcent.com/mobile/ShareNVT");
        DemoListActivity.this.startActivity(localIntent);
      }
    });
    ((Button)findViewById(2131296268)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(DemoListActivity.this, ActivityWebView.class);
        localIntent.putExtra("url", "https://www.ipcent.com/mobile/RemoveSharedNVT");
        DemoListActivity.this.startActivity(localIntent);
      }
    });
    new AsyncTask()
    {
      protected String doInBackground(Void[] paramArrayOfVoid)
      {
        try
        {
          AndroidHttpClient localAndroidHttpClient = AndroidHttpClient.newInstance("IPCENTCOM");
          String str2 = "$filter=Active eq true".replace(" ", "%20");
          HttpGet localHttpGet = new HttpGet("https://www.ipcent.com/Video.svc/tblDemoONVIFs?" + str2);
          localHttpGet.addHeader("password", "ipcentcom");
          localHttpGet.addHeader("Accept ", "application/json");
          str1 = EntityUtils.toString(localAndroidHttpClient.execute(localHttpGet).getEntity());
          localAndroidHttpClient.close();
          return str1;
        }
        catch (Exception localException)
        {
          while (true)
          {
            utility.logMessageAsync(DemoListActivity.this, "Error in onCreate() of DemoListActivity");
            String str1 = null;
          }
        }
      }

      protected void onPostExecute(String paramString)
      {
        DemoListActivity.this.pd.dismiss();
        Gson localGson = new Gson();
        Type localType = new DemoListActivity.4.1(this).getType();
        try
        {
          JSONObject localJSONObject = new JSONObject(paramString);
          localJSONObject.get("d");
          Iterator localIterator = ((Collection)localGson.fromJson(localJSONObject.get("d").toString(), localType)).iterator();
          while (true)
          {
            boolean bool = localIterator.hasNext();
            if (!bool)
            {
              super.onPostExecute(paramString);
              return;
            }
            DemoListActivity.tblDemoONVIF localtblDemoONVIF = (DemoListActivity.tblDemoONVIF)localIterator.next();
            Intent localIntent = new Intent();
            localIntent.putExtra("address", localtblDemoONVIF.Address);
            localIntent.putExtra("name", localtblDemoONVIF.Name);
            localIntent.putExtra("username", localtblDemoONVIF.UserName);
            localIntent.putExtra("password", localtblDemoONVIF.Password);
            Button localButton = new Button(DemoListActivity.this);
            localButton.setText(localtblDemoONVIF.Name);
            localButton.setOnClickListener(new DemoListActivity.4.2(this, localIntent));
            ((LinearLayout)DemoListActivity.this.findViewById(2131296265)).addView(localButton);
            new DemoListActivity.4.3(this).start();
          }
        }
        catch (Exception localException)
        {
          while (true)
            utility.logMessageAsync(DemoListActivity.this, "Error in processing demo list return in DemoListActivity: " + localException.getMessage());
        }
      }
    }
    .execute(new Void[0]);
  }

  class tblDemoONVIF
  {
    public boolean Active;
    public String Address;
    public String DeletePassword;
    public String Email;
    public String Manufacturer;
    public String Name;
    public String Password;
    public int RecordID;
    public String UserName;

    tblDemoONVIF()
    {
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DemoListActivity
 * JD-Core Version:    0.6.0
 */