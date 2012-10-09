package net.biyee.onvifer;

import android.widget.Button;

class OnviferActivity$9$1
  implements Runnable
{
  public void run()
  {
    Button localButton = (Button)OnviferActivity.9.access$0(this.this$1).findViewById(2131296269);
    localButton.setText(OnviferActivity.9.access$0(this.this$1).getString(2131099692) + "(" + this.val$iCount + ")");
    localButton.setVisibility(0);
    localButton.setOnClickListener(new OnviferActivity.9.1.1(this));
    OnviferActivity.access$0(OnviferActivity.9.access$0(this.this$1));
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity.9.1
 * JD-Core Version:    0.6.0
 */