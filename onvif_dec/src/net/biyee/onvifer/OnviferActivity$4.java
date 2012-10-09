package net.biyee.onvifer;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import net.biyee.android.ActivityFeedback;

class OnviferActivity$4
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    Intent localIntent = new Intent(this.this$0, ActivityFeedback.class);
    localIntent.putExtra("app", "Onvifer");
    this.this$0.startActivity(localIntent);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.4
 * JD-Core Version:    0.6.0
 */