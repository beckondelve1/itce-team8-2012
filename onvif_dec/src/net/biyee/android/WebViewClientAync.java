package net.biyee.android;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewClientAync extends WebViewClient
{
  Activity _activity;
  ProgressDialog pd;

  public WebViewClientAync(Activity paramActivity)
  {
    this._activity = paramActivity;
    this.pd = new ProgressDialog(paramActivity);
    this.pd.setMessage("Please wait...");
    this.pd.setProgressStyle(0);
    this.pd.show();
  }

  public void onLoadResource(WebView paramWebView, String paramString)
  {
  }

  public void onPageFinished(WebView paramWebView, String paramString)
  {
    this.pd.dismiss();
  }

  public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    new AlertDialog.Builder(this._activity).create();
    utility.ShowMessage(this._activity, paramString1);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.WebViewClientAync
 * JD-Core Version:    0.6.0
 */