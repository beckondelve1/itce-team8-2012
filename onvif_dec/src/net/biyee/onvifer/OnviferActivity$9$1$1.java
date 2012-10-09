package net.biyee.onvifer;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import net.biyee.android.ActivityWebView;

class OnviferActivity$9$1$1
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    Intent localIntent = new Intent(OnviferActivity.9.access$0(OnviferActivity.9.1.access$0(this.this$2)), ActivityWebView.class);
    localIntent.putExtra("url", "https://www.ipcent.com/Mobile/ONVIFNVT");
    OnviferActivity.9.access$0(OnviferActivity.9.1.access$0(this.this$2)).startActivity(localIntent);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.9.1.1
 * JD-Core Version:    0.6.0
 */