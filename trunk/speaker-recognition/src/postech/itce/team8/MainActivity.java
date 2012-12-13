package postech.itce.team8;

import postech.itce.team8.util.Constants;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String LOG_TAG = "MainActivity";
	//
	private Button btnRegister;
	private Button btnLogin;
	private Button btnAgenda;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnAgenda = (Button)findViewById(R.id.btnAgenda);
        //
        btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, RegisterBasicInfoActivity.class));
				
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
}
