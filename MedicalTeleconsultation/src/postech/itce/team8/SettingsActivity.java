package postech.itce.team8;

import postech.itce.team8.util.Constants;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	private static final String LOG_TAG = "SettingsActivity";
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
//		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//		Log.d(LOG_TAG, sharedPrefs.getString("server_addr", "NULL"));
//		
//		//
//		Constants.updateURLs(sharedPrefs.getString("server_addr", "NULL"));
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key) {
		Log.i(LOG_TAG, "updating settings...");
		//if (key.equals("server_addr")){
			Log.d(LOG_TAG, sharedPrefs.getString("server_addr", "NULL"));
		
			//
			Constants.updateURLs(sharedPrefs.getString("server_addr", "NULL"));
		//}
		
	}
	
	
}
