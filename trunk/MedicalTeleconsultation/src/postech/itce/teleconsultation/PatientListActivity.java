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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.videolan.vlc.*;
import org.videolan.vlc.gui.video.*;

import postech.itce.team8.util.Constants;
import postech.itce.team8.util.HttpPostRequester;

public class PatientListActivity extends Activity {
	public static final String TAG = "MainActivity";
	//
	private static final int ID_DIALOG_REGISTER_IP = 0;
	private static final int ID_DIALOG_FETCHING = 0;

	//
	private Button buttonConnect;
	private ListView listViewPatientList;

	private ArrayList<HashMap<String, String>> patientList;

	// handler for network operations
	protected Handler mHandler;
	
	// result(s) for network operations
	private final Runnable getAgendaResult = new Runnable() {
		@Override
		public void run(){
			//update list view
            ListAdapter adapter = new SimpleAdapter(PatientListActivity.this, patientList,
                    R.layout.row_agenda_list,
                    new String[] { "id", "patientFullName", "expectedTime", "status" }, new int[] {
                            R.id.txtId, R.id.txtPatientName, R.id.txtExpectedTime, R.id.txtStatus });
     
            listViewPatientList.setAdapter(adapter);
            listViewPatientList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	};
	
	private final Runnable registerIpResult = new Runnable() {
		@Override
		public void run(){
			Toast toast = Toast.makeText(getApplicationContext(), "IP registered to server !", 
					Toast.LENGTH_SHORT);
			toast.show();
			
			//2.
			// temp
			String doctorName = "hiepnh"; 
			showDialog(ID_DIALOG_FETCHING);
	        startFetchingAgenda(doctorName);
		}
	};
	
	// Ctor
	public PatientListActivity() {
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
		//
        mHandler = new Handler();
		//
		patientList = new ArrayList<HashMap<String, String>>();

		buttonConnect = (Button)findViewById(R.id.buttonConnect);
		listViewPatientList = (ListView)findViewById(R.id.listViewPatientList);

		buttonConnect.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				int pos = listViewPatientList.getCheckedItemPosition();
				if (pos == ListView.INVALID_POSITION)
					return;

				String patientid = patientList.get(pos).get("id");
				String patientCurrentIp = patientList.get(pos).get("patientCurrentIp");
				String defaultURL = patientList.get(pos).get("defaultURL");
				String cameraUser = patientList.get(pos).get("cameraUser");
				String cameraPass = patientList.get(pos).get("cameraPass");

				String streamURL = "rtsp://" + cameraUser + ":" + cameraPass + "@" + 
						patientCurrentIp + ":" + defaultURL;
				
				startConsultation(patientid, streamURL);
			}
		});
		
		//
		refreshPatientList();
				
	}
	
	private void startRegisterIp(final String url){
		Thread t = new Thread() {
			@Override
			public void run() {
				
				// Register my stream URL (IP) to the application server
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", "hiepnh");
				map.put("currentIp", url);
				HttpPostRequester.postSecureData(Constants.REGISTER_DOCTOR_IP_URL, map, (VLCApplication)getApplicationContext());
				
				dismissDialog(ID_DIALOG_REGISTER_IP);
				mHandler.post(registerIpResult);
			}
    	};
		t.start();
	}
	
	private void startFetchingAgenda(final String doctorName){
    	Thread t = new Thread() {
			@Override
			public void run() {
				
				getAgenda(doctorName);
				
				dismissDialog(ID_DIALOG_FETCHING);
				mHandler.post(getAgendaResult);
			}
    	};
		t.start();
			
    }
	

	//
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
			// return RTSP
			//url = String.format("rtsp://%d.%d.%d.%d:8086", i & 0xff, i >> 8 & 0xff,i >> 16 & 0xff,i >> 24 & 0xff);
			// return IP
			url = String.format("%d.%d.%d.%d",i & 0xff, i >> 8 & 0xff,i >> 16 & 0xff,i >> 24 & 0xff);
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

//		if(!getPatientListFromServer()) {
//			Toast.makeText(this, "Failed to get the patient list from the server.", Toast.LENGTH_LONG).show();
//			return false;
//		}
		
		return true;
	}

	// Register my stream URL for the tele-consultation to the application server
	protected boolean registerMyStreamUrlToServer() {
		String url;
		if ((url = getMyStreamUrl()) == null)
			return false;
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		showDialog(ID_DIALOG_REGISTER_IP);
        startRegisterIp(url);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		return true;
	}

	// Get the patient list from the application server and then fill listview of patient list  
	protected boolean getPatientListFromServer() {
		patientList.clear();
		
		/////////////////////////////////////////////////////////////////////////////////////////////

		// while (...) {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("Id", "0");
//		map.put("Name", "Brown Smith");
//		//map.put("StreamUrl", "rtsp://admin:4321@119.202.84.47:554/onvif/profile4/media.smp");
//		map.put("StreamUrl", "rtsp://root:itce600@141.223.83.27:554/axis-media/media.amp?videocodec=h264&streamprofile=hiep");
//		patientList.add(map);
		// }
		
		

		/////////////////////////////////////////////////////////////////////////////////////////////
		
		
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
	
	//
    private void getAgenda(String doctorName){
    	
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("doctorName", doctorName);
		
		String responseStr = HttpPostRequester.postSecureData(Constants.AGENDA_URL, param,
				(VLCApplication)getApplicationContext());
		
		Log.i(TAG, "getAgenda - response : " + responseStr);
		
		//parse JSON response
		
		
		try {
			JSONObject jObj = new JSONObject(responseStr);
			JSONArray cList = jObj.getJSONArray("consultationList");
			for(int i = 0; i < cList.length(); i++){
		        JSONObject c = cList.getJSONObject(i);
		        
		        Log.d(TAG, "i = " + i + " , name=" + c.getString("patientFullName") + ", time="
		        		+ c.getString("expectedTime"));
		        
		        HashMap<String, String> map = new HashMap<String, String>();
		        
                // adding each child node to HashMap key => value
                map.put("id", c.getString("id"));
                map.put("patientFullName", c.getString("patientFullName"));
                map.put("patientCurrentIp", c.getString("patientCurrentIp"));
                map.put("defaultURL", c.getString("defaultURL"));
                map.put("cameraUser", c.getString("cameraUser"));
                map.put("cameraPass", c.getString("cameraPass"));
                map.put("expectedTime", c.getString("expectedTime"));
                map.put("status", c.getString("status"));
                
                patientList.add(map);
                
                
			}
			
			//
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    @Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
    	if(id == ID_DIALOG_REGISTER_IP){
			ProgressDialog registeringDialog = new ProgressDialog(this);
			registeringDialog.setMessage("Registering your IP ...");
			registeringDialog.setIndeterminate(true);
			registeringDialog.setCancelable(true);
			return registeringDialog;
			
		}
    	
		if(id == ID_DIALOG_FETCHING){
			ProgressDialog uploadingDialog = new ProgressDialog(this);
			uploadingDialog.setMessage("Fetching agenda list ...");
			uploadingDialog.setIndeterminate(true);
			uploadingDialog.setCancelable(true);
			return uploadingDialog;
			
		}
		
		//
		return super.onCreateDialog(id);
	}
}
