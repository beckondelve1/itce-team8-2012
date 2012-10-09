package net.biyee.onvifer;

import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import net.biyee.android.BoolClass;

class PlayVideoActivity$3
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    if (this.this$0.bMuted.bValue)
    {
      this.this$0.bMuted.bValue = false;
      if (PlayVideoActivity.access$0(this.this$0) != null)
        PlayVideoActivity.access$0(this.this$0).setVolume(this.this$0.fVolumeLeft, this.this$0.fVolumeRight);
      this.val$btAudio.setContentDescription("Mute");
      this.val$btAudio.setImageResource(2130837518);
    }
    while (true)
    {
      return;
      this.this$0.bMuted.bValue = true;
      if (PlayVideoActivity.access$0(this.this$0) != null)
        PlayVideoActivity.access$0(this.this$0).setVolume(0.0F, 0.0F);
      this.val$btAudio.setContentDescription("Unmute");
      this.val$btAudio.setImageResource(2130837511);
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.3
 * JD-Core Version:    0.6.0
 */