package net.biyee.onvifer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;

class PlayVideoActivity$7
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    Intent localIntent = new Intent(this.this$0, PlayVideoActivity.class);
    SharedPreferences.Editor localEditor = this.this$0.getSharedPreferences("default_streaming_mode", 0).edit();
    if ("H.264".equals(this.this$0.sCodec))
    {
      localIntent.putExtra("param", this.val$sUID + ", JPEG, HTTP");
      localEditor.putString(this.val$sUID, "JPEG");
    }
    while (true)
    {
      localEditor.commit();
      this.this$0.startActivity(localIntent);
      this.this$0.finish();
      return;
      localIntent.putExtra("param", this.val$sUID + ", H.264, UDP");
      localEditor.putString(this.val$sUID, "H.264");
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity.7
 * JD-Core Version:    0.6.0
 */