package net.biyee.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ActivityWebView extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    WebView localWebView = new WebView(this);
    localWebView.getSettings().setJavaScriptEnabled(true);
    localWebView.setWebViewClient(new WebViewClientAync(this));
    localWebView.loadUrl(getIntent().getExtras().getString("url"));
    setContentView(localWebView);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ActivityWebView
 * JD-Core Version:    0.6.0
 */