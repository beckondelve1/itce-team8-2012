package net.biyee.onvifer;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

class PlayVideoActivity$2
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    switch ($SWITCH_TABLE$android$widget$ImageView$ScaleType()[this.this$0.stStretechMode.ordinal()])
    {
    default:
    case 2:
    case 3:
    }
    while (true)
    {
      ((ImageView)this.this$0.findViewById(2131296270)).setScaleType(this.this$0.stStretechMode);
      this.this$0.setSurfaceSize();
      return;
      this.this$0.stStretechMode = ImageView.ScaleType.CENTER_INSIDE;
      this.val$btStretch.setImageResource(2130837517);
      continue;
      this.this$0.stStretechMode = ImageView.ScaleType.CENTER_CROP;
      this.val$btStretch.setImageResource(2130837516);
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.2
 * JD-Core Version:    0.6.0
 */