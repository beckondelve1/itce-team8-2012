package net.biyee.onvifer;

import android.widget.Button;

class DemoListActivity$4$3$1
  implements Runnable
{
  public void run()
  {
    Button localButton = (Button)DemoListActivity.4.access$2(DemoListActivity.4.3.access$0(this.this$2)).findViewById(2131296269);
    localButton.setText(DemoListActivity.4.access$2(DemoListActivity.4.3.access$0(this.this$2)).getString(2131099692) + "(" + this.val$iCount + ")");
    localButton.setVisibility(0);
    localButton.setOnClickListener(new DemoListActivity.4.3.1.1(this));
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.DemoListActivity.4.3.1
 * JD-Core Version:    0.6.0
 */