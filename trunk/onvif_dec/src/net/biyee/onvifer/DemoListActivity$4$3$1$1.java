package net.biyee.onvifer;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import net.biyee.android.ActivityWebView;

class DemoListActivity$4$3$1$1
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    Intent localIntent = new Intent(DemoListActivity.4.access$2(DemoListActivity.4.3.access$0(DemoListActivity.4.3.1.access$0(this.this$3))), ActivityWebView.class);
    localIntent.putExtra("url", "https://www.ipcent.com/Mobile/ONVIFNVT");
    DemoListActivity.4.access$2(DemoListActivity.4.3.access$0(DemoListActivity.4.3.1.access$0(this.this$3))).startActivity(localIntent);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DemoListActivity.4.3.1.1
 * JD-Core Version:    0.6.0
 */