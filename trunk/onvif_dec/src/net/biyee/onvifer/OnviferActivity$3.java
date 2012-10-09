package net.biyee.onvifer;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import net.biyee.android.ActivityWebView;

class OnviferActivity$3
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    Intent localIntent = new Intent(this.this$0, ActivityWebView.class);
    localIntent.putExtra("url", "https://www.ipcent.com/mobile/Help/Onvifer");
    this.this$0.startActivity(localIntent);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.3
 * JD-Core Version:    0.6.0
 */