package postech.itce.teleconsultation;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.videolan.vlc.*;

public class MainActivity extends Activity {
	private static final String LOG_TAG = "MainActivity";
	
	private String DeviceName;
	private String StreamUrl;
	
	private Button btnPlay;
	private TextView txtProfile;
	
	private static final int ID_DIALOG_MEDIA_PROFILE = 0;
	private NetworkCameraService camera;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tc_main);
		
		btnPlay = (Button)findViewById(R.id.btnPlay);
		txtProfile = (TextView)findViewById(R.id.txtProfile);
		
		//
		String cameraip = "119.202.84.41";
		
		camera = new NetworkCameraService();
		if(!camera.setCameraIp(cameraip)) {
			if(cameraip != null) {
				Log.e("InvalidCameraIP", cameraip);
			} else {
				Log.e("InvalidCameraIP", "null");
			}
			return;
		}
		
		//HiepNH
//		showDialog(ID_DIALOG_MEDIA_PROFILE);	//set profile
		
		
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				camera.setProfileName(txtProfile.getText().toString());
				
				//
				Log.i(LOG_TAG, "profile=" + camera.getProfileName());
				
				// this.DeviceName = camera.getDeviceInformation();
				
				// For ONVIF test
				// this.StreamUrl = camera.getStreamUrl();
				
				// For Axis camera 
				// this.StreamUrl = camera.getStreamUrlAxis("quality_h264");
				
				//HiepNH - commented
				//this.StreamUrl = camera.getHttpStreamUrl();
				StreamUrl = camera.getStreamUrlAxis("hiep");
				
				if(StreamUrl != null)
					Log.d("StreamURL", StreamUrl);
				
				
				//
				try {
					Intent intent = new Intent(MainActivity.this, VideoStreamingActivity.class);
					intent.putExtra("StreamUrl", StreamUrl);
					startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		
	}
	
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}




	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		if(id == ID_DIALOG_MEDIA_PROFILE){
			return new AlertDialog.Builder(MainActivity.this)
            	.setTitle(R.string.select_profile)
            	.setItems(R.array.select_profile_items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String[] items = getResources().getStringArray(R.array.select_profile_items);
                    Log.i(LOG_TAG, "which=" + which);
                    Log.i(LOG_TAG, "items[which]=" + items[which]);
                    
                    camera.setProfileName(items[which]);
                }
            })
            .create();
			
			
			
			
		}
		
		//
		return super.onCreateDialog(id);
	}
	
}
