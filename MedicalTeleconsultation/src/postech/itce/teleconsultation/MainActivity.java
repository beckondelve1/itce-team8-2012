package postech.itce.teleconsultation;

import java.util.*;

import android.app.*;
import android.content.*;
import android.net.wifi.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.graphics.drawable.*;

import net.majorkernelpanic.spydroid.*;

import org.videolan.vlc.*;
import org.videolan.vlc.gui.video.*;

import postech.itce.teleconsultation.medicalrecord.*; 

public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";

	private Button buttonConnect;
	private ListView listViewPatientList;

	private ArrayList<HashMap<String, String>> patientList;

	public MainActivity() {
		super();

		Log.e(TAG + ": BOARD", Build.BOARD);
		Log.e(TAG + ": BRAND", Build.BRAND);
		Log.e(TAG + ": CPU_ABI", Build.CPU_ABI);
		Log.e(TAG + ": DEVICE", Build.DEVICE);
		Log.e(TAG + ": DISPLAY", Build.DISPLAY);
		Log.e(TAG + ": FINGERPRINT", Build.FINGERPRINT);
		Log.e(TAG + ": HOST", Build.HOST);
		Log.e(TAG + ": ID", Build.ID);
		Log.e(TAG + ": MANUFACTURER", Build.MANUFACTURER);
		Log.e(TAG + ": MODEL", Build.MODEL);
		Log.e(TAG + ": PRODUCT", Build.PRODUCT);
		Log.e(TAG + ": TAGS", Build.TAGS);
		Log.e(TAG + ": TYPE", Build.TYPE);
		Log.e(TAG + ": USER", Build.USER);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tc_main);

		patientList = new ArrayList<HashMap<String, String>>();

		buttonConnect = (Button)findViewById(R.id.buttonConnect);
		listViewPatientList = (ListView)findViewById(R.id.listViewPatientList);

		buttonConnect.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				int pos = listViewPatientList.getCheckedItemPosition();
				if (pos == ListView.INVALID_POSITION)
					return;

				String patientid = patientList.get(pos).get("Id");
				String streamurl = patientList.get(pos).get("StreamUrl");

				startConsultation(patientid, streamurl);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		refreshPatientList();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
		case R.id.options:
			intent = new Intent(this.getBaseContext(), OptionsActivity.class);
			startActivityForResult(intent, 0);
			return true;
		case R.id.quit:
			finish();	
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private String getMyStreamUrl() {
		String url;
		WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo();
		if (info != null && info.getNetworkId() > -1) {
			int i = info.getIpAddress();
			url = String.format("rtsp://%d.%d.%d.%d:8086", i & 0xff, i >> 8 & 0xff,i >> 16 & 0xff,i >> 24 & 0xff);
		} else {
			return null;
		}
		return url;
	}
	
	public boolean refreshPatientList() {
		if(!registerMyStreamUrlToServer()) {
			Toast.makeText(this, "Failed to register my stream URL to the server.", Toast.LENGTH_LONG).show();
			return false;
		}

		if(!getPatientListFromServer()) {
			Toast.makeText(this, "Failed to get the patient list from the server.", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}

	// Register my stream URL for the tele-consultation to the application server
	protected boolean registerMyStreamUrlToServer() {
		String url;
		if ((url = getMyStreamUrl()) == null)
			return false;
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		// Register my stream URL to the application server
		// ...
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		return true;
	}

	// Get the patient list from the application server and then fill listview of patient list  
	protected boolean getPatientListFromServer() {
		patientList.clear();
		
		/////////////////////////////////////////////////////////////////////////////////////////////

		// while (...) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Id", "0");
		map.put("Name", "Brown Smith");
		//map.put("StreamUrl", "rtsp://admin:4321@119.202.84.47:554/onvif/profile4/media.smp");
		map.put("StreamUrl", "rtsp://root:itce600@141.223.83.27:554/axis-media/media.amp?videocodec=h264&streamprofile=hiep");
		patientList.add(map);
		// }

		/////////////////////////////////////////////////////////////////////////////////////////////

		SimpleAdapter adapter = new SimpleAdapter(
				this, 
				patientList, 
				android.R.layout.simple_list_item_activated_2, 
				new String[] { "Name", "Id" },
				new int[] { android.R.id.text1, android.R.id.text2 });
		listViewPatientList.setAdapter(adapter);
		listViewPatientList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		return true;
	}

	protected boolean startConsultation(String patientId, String streamUrl) {
		if(patientId == null || patientId == "" || streamUrl == null || streamUrl == "") {
			return false;
		}

		try {
			Intent intent = new Intent(this, VideoPlayerActivity.class);
			intent.putExtra("patientId", patientId);
			intent.putExtra("itemLocation", streamUrl);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
