package postech.itce.team8;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import postech.itce.team8.util.AeSimpleMD5;
import postech.itce.team8.util.Constants;
import postech.itce.team8.util.HttpPostRequester;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String LOG_TAG = "MainActivity";
	//
	private static final int ID_DIALOG_LOGGING_PASSWORD = 0;
	//
	private Handler mHandler;
	//
	private Button btnRegister;
	private Button btnLogin;
	private Button btnAgenda;
	//
	private Context context;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        context = getApplicationContext();
        //
        mHandler = new Handler();
        //
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnAgenda = (Button)findViewById(R.id.btnAgenda);
        //
        btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//startActivity(new Intent(MainActivity.this, RegisterBasicInfoActivity.class));
				
				//new - password-based login
				LayoutInflater factory = LayoutInflater.from(MainActivity.this);            
		        final View textEntryView = factory.inflate(R.layout.dialog_user_pass, null);

		        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this); 

		        alert.setTitle("Please Login"); 
		        alert.setMessage("Enter your name and password"); 
		        // Set an EditText view to get user input  
		        alert.setView(textEntryView); 

		        final EditText input1 = (EditText) textEntryView.findViewById(R.id.txtUserName);
		        final EditText input2 = (EditText) textEntryView.findViewById(R.id.txtPassword);

		        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() { 
		        public void onClick(DialogInterface dialog, int whichButton) { 
		            String userName = input1.getText().toString();
		            String password = input2.getText().toString();
		            String hashedPassword = "";
		            try {
		            	hashedPassword = AeSimpleMD5.MD5(password);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
		            		
		            //
		            showDialog(ID_DIALOG_LOGGING_PASSWORD);
		            startVerifyingUsernamePassword(userName, hashedPassword);

		        } 
		        }); 

		        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { 
		          public void onClick(DialogInterface dialog, int whichButton) { 
		            // Canceled. 
		          } 
		        }); 

		        alert.show(); 
				
				
			}
		});
        
        //
        btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				
			}
		});
        
        //
        btnAgenda.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, AgendaActivity.class));
				
			}
		});
        
        //always call before any subsequent activities
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String serverAddress = sharedPrefs.getString("server_addr", "NULL");
		Log.i(LOG_TAG, "first call, server_addr=" + serverAddress);
		Constants.updateURLs(serverAddress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
    
    private final Runnable loginPasswordSuccess = new Runnable() {
		@Override
		public void run(){
			Toast toast = Toast.makeText(getApplicationContext(), "Login successful", 
					Toast.LENGTH_SHORT);
			toast.show();
		}
	};
	
	private final Runnable loginPasswordFailure = new Runnable() {
		@Override
		public void run(){
			Toast toast = Toast.makeText(getApplicationContext(), "Login failed", 
					Toast.LENGTH_SHORT);
			toast.show();
		}
	};
    
    private void startVerifyingUsernamePassword(final String userName, final String password){
		Thread t = new Thread() {
			@Override
			public void run() {
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", userName);
				map.put("password", password);
				
				//debug
				//App app = (App)getApplicationContext();
				//App app = (App)getApplication();
				//App app = (App)context;
				App app = (App)App.context;
				
				String responseStr = HttpPostRequester.postSecureData(Constants.LOGIN_PASSWORD_URL, map,
						app);
				
				Log.i(LOG_TAG, "responseStr="+responseStr);
				
				
				// Check responseStr: SUCCESS or FAILURE
				if (responseStr.equals("loginPassword: SUCCESS")){
					dismissDialog(ID_DIALOG_LOGGING_PASSWORD);
					mHandler.post(loginPasswordSuccess);
					//
					Bundle savedBasicInfo = new Bundle();
					savedBasicInfo.putString("userName", userName);
					Intent intent = new Intent(MainActivity.this, RegisterVoiceActivity.class);
					intent.putExtras(savedBasicInfo);
					//
					startActivity(intent);
					
				}else{
					mHandler.post(loginPasswordFailure);
				}
				
			}
		};
		t.start();
    }
    
    @Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		if(id == ID_DIALOG_LOGGING_PASSWORD){
			ProgressDialog uploadingDialog = new ProgressDialog(this);
			uploadingDialog.setMessage("Verifying username/password...");
			uploadingDialog.setIndeterminate(true);
			uploadingDialog.setCancelable(true);
			return uploadingDialog;
			
		}
		
		//
		return super.onCreateDialog(id);
	}
}
