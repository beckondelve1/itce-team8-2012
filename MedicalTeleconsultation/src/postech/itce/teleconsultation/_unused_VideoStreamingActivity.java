package postech.itce.teleconsultation;

import android.app.*;
import android.content.*;
import android.os.*;

import org.videolan.vlc.gui.video.*;

public class _unused_VideoStreamingActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		String streamurl = getIntent().getExtras().getString("StreamUrl", null);
		
		if(streamurl != null) {
			try {
				Intent intent = new Intent(this, VideoPlayerActivity.class);
				intent.putExtra("itemLocation", streamurl);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		super.onCreate(savedInstanceState);
	}
}
