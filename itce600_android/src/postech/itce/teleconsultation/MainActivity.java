package postech.itce.teleconsultation;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.util.*;
import org.videolan.vlc.*;

public class MainActivity extends Activity {
	private String DeviceName;
	private String StreamUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String cameraip = "119.202.84.112";
		
		NetworkCameraService camera = new NetworkCameraService();
		if(!camera.setCameraIp(cameraip)) {
			if(cameraip != null) {
				Log.e("InvalidCameraIP", cameraip);
			} else {
				Log.e("InvalidCameraIP", "null");
			}
			return;
		}
		// this.DeviceName = camera.getDeviceInformation();
		
		// For ONVIF test
		// this.StreamUrl = camera.getStreamUrl();
		
		// For Axis camera 
		// this.StreamUrl = camera.getStreamUrlAxis("quality_h264");
		
		//HiepNH - commented
		//this.StreamUrl = camera.getHttpStreamUrl();
		this.StreamUrl = camera.getStreamUrlAxis("hiep");
		
		if(this.StreamUrl != null)
			Log.d("StreamURL", this.StreamUrl);
		
		setContentView(R.layout.tc_main);
		
		try {
			Intent intent = new Intent(this, VideoStreamingActivity.class);
			intent.putExtra("StreamUrl", this.StreamUrl);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
