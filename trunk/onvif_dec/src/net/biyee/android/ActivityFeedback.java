package net.biyee.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ActivityFeedback extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    WebView localWebView = new WebView(this);
    localWebView.getSettings().setJavaScriptEnabled(true);
    localWebView.setWebViewClient(new WebViewClientAync(this));
    Bundle localBundle = getIntent().getExtras();
    localWebView.loadUrl("http://www.biyee.net/mobile/Feedback.aspx?app=" + localBundle.getString("app"));
    setContentView(localWebView);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ActivityFeedback
 * JD-Core Version:    0.6.0
 */