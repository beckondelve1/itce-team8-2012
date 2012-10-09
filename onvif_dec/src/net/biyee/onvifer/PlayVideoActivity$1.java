package net.biyee.onvifer;

import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import net.biyee.android.BoolClass;

class PlayVideoActivity$1
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    if (this.this$0.bPaused.bValue)
    {
      this.this$0.bPaused.bValue = false;
      if (PlayVideoActivity.access$0(this.this$0) != null)
        PlayVideoActivity.access$0(this.this$0).start();
      this.val$btPlayPause.setContentDescription("Pause");
      this.val$btPlayPause.setImageResource(2130837512);
    }
    while (true)
    {
      return;
      this.this$0.bPaused.bValue = true;
      if (PlayVideoActivity.access$0(this.this$0) != null)
        PlayVideoActivity.access$0(this.this$0).pause();
      this.val$btPlayPause.setContentDescription("Play");
      this.val$btPlayPause.setImageResource(2130837513);
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.1
 * JD-Core Version:    0.6.0
 */