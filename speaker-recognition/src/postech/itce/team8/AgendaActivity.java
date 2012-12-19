package postech.itce.team8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import postech.itce.team8.util.Constants;
import postech.itce.team8.util.HttpPostRequester;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AgendaActivity extends Activity {

	private static final String LOG_TAG = "AgendaActivity";
	//
	private static final int ID_DIALOG_FETCHING = 0;
	//
	private ListView lstAgenda;
	private ArrayList<HashMap<String, String>> agendaList = new ArrayList<HashMap<String, String>>();

	protected Handler mHandler;
	
	private final Runnable postResults = new Runnable() {
		@Override
		public void run(){
			//update list view
            ListAdapter adapter = new SimpleAdapter(AgendaActivity.this, agendaList,
                    R.layout.row_agenda_list,
                    new String[] { "id", "patientFullName", "expectedTime", "status" }, new int[] {
                            R.id.txtId, R.id.txtPatientName, R.id.txtExpectedTime, R.id.txtStatus });
     
            lstAgenda.setAdapter(adapter);
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        //
        mHandler = new Handler();
        //
        lstAgenda = (ListView) findViewById(R.id.lstAgenda);
        
        lstAgenda.setOnItemClickListener(lstAgendaListener);
        
        //
        //always call before any subsequent activities
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String serverAddress = sharedPrefs.getString("server_addr", "NULL");
		Log.i(LOG_TAG, "first call, server_addr=" + serverAddress);
		Constants.updateURLs(serverAddress);
        
        //
		Bundle savedBundle = this.getIntent().getExtras();
		String doctorName = savedBundle.getString("doctorName");
		
        showDialog(ID_DIALOG_FETCHING);
        startFetchingAgenda(doctorName);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_agenda, menu);
        return true;
    }
    
    private void startFetchingAgenda(final String doctorName){
    	Thread t = new Thread() {
			@Override
			public void run() {
				
				getAgenda(doctorName);
				
				dismissDialog(ID_DIALOG_FETCHING);
				mHandler.post(postResults);
			}
    	};
		t.start();
			
    }
    
    //
    private void getAgenda(String doctorName){
    	
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("doctorName", doctorName);
		
		String responseStr = HttpPostRequester.postSecureData(Constants.AGENDA_URL, param,
				(App)getApplicationContext());
		
		Log.i(LOG_TAG, "getAgenda - response : " + responseStr);
		
		//parse JSON response
		
		
		try {
			JSONObject jObj = new JSONObject(responseStr);
			JSONArray cList = jObj.getJSONArray("consultationList");
			for(int i = 0; i < cList.length(); i++){
		        JSONObject c = cList.getJSONObject(i);
		        
		        Log.d(LOG_TAG, "i = " + i + " , name=" + c.getString("patientFullName") + ", time="
		        		+ c.getString("expectedTime"));
		        
		        HashMap<String, String> map = new HashMap<String, String>();
		        
                // adding each child node to HashMap key => value
                map.put("id", c.getString("id"));
                map.put("patientFullName", c.getString("patientFullName"));
                map.put("expectedTime", c.getString("expectedTime"));
                map.put("status", c.getString("status"));
                
                agendaList.add(map);
                
                
			}
			
			//
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    
    @Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
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
    
    
    //LISTENERs
  	private OnItemClickListener lstAgendaListener = new AdapterView.OnItemClickListener() {
  		public void onItemClick(AdapterView<?> list, View view, int position, long id) {
  			
  			final String agendaId = ((TextView) view.findViewById(R.id.txtId)).getText().toString();
  			Intent instanceViewIntent = new Intent(AgendaActivity.this, ConsultationActivity.class);
  			instanceViewIntent.putExtra("agendaId", agendaId);

  			startActivity(instanceViewIntent);
  		}
  	};
}
