package postech.itce.team8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import postech.itce.team8.util.AudioRecorder;
import postech.itce.team8.util.Constants;
import postech.itce.team8.util.FileUpload;
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
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private static final String LOG_TAG = "LoginActivity";

	//
	private static final int ID_DIALOG_UPLOADING = 0;
	private static final int ID_DIALOG_VERIFYING = 0;
	
	
	//
	final Context context = this;

	SharedPreferences settings;

	//
	private Handler mHandler;
	//audio recorder
	private AudioRecorder audioRecorder;
	private String audioRecorderFolder;
	//
	private String lastLoginId;
	
	// UI controls
	private Spinner spRecentUsers;
	private TextView lblCaptcha;
	private Button btnRecord;
	private Button btnStop;

	//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//
        mHandler = new Handler();
		//
		spRecentUsers = (Spinner) findViewById(R.id.spRecentUsers);
		lblCaptcha = (TextView) findViewById(R.id.lblCaptcha);
		btnRecord = (Button) findViewById(R.id.btnRecord);
		btnStop = (Button) findViewById(R.id.btnStop);

		//
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		String recentUsersListStr = settings.getString("recentUsersList",
				"Other");
		String[] userNameList = recentUsersListStr.split(";");

		List<String> recentUsersList = new ArrayList<String>();
		for (String userName : userNameList)
			recentUsersList.add(userName);

		//
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, recentUsersList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRecentUsers.setAdapter(dataAdapter);
		spRecentUsers.setOnItemSelectedListener(spRecentUsersHandler);

		//
		btnRecord.setOnClickListener(btnRecordHandler);
        btnStop.setOnClickListener(btnStopHandler);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	//
	private void updateRecentUsers(String newUserName) {
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		String recentUsersListStr = settings.getString("recentUsersList",
				"Other");
		String[] userNameList = recentUsersListStr.split(";");

		List<String> recentUsersList = new ArrayList<String>();
		for (String userName : userNameList)
			recentUsersList.add(userName);
		// add newUserName
		recentUsersList.add(0, newUserName);
				
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, recentUsersList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRecentUsers.setAdapter(dataAdapter);		

		String newUsersListStr = "";
		for (String userName : recentUsersList)
			newUsersListStr += userName + ";";

		SharedPreferences.Editor editor = settings.edit();
		editor.putString("recentUsersList", newUsersListStr);
		// Commit the edits!
	    editor.commit();

	}
	
	
	private void enableButton(int id,boolean isEnable){
		((Button)findViewById(id)).setEnabled(isEnable);
	}
	
	private void enableButtons(boolean isRecording) {
		enableButton(R.id.btnRecord,!isRecording);
		enableButton(R.id.btnStop,isRecording);
	}

	// HANDLERs
	AdapterView.OnItemSelectedListener spRecentUsersHandler = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			if (parent.getItemAtPosition(pos).toString().equals("Other")) {
				// get prompts.xml view
				LayoutInflater li = LayoutInflater.from(context);
				View userNamePrompt = li
						.inflate(R.layout.dialog_username, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(userNamePrompt);

				final EditText txtUserName = (EditText) userNamePrompt
						.findViewById(R.id.txtUserName);

				// set dialog message
				alertDialogBuilder
						.setTitle("Input User Name")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// get user input and set it to result
										// edit text
										lblCaptcha.setText(txtUserName
												.getText());

										updateRecentUsers(txtUserName.getText()
												.toString());

									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	};

	//btnRecord
    private View.OnClickListener btnRecordHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.i(LOG_TAG, "Start Recording");
			enableButtons(true);
			
			audioRecorder = new AudioRecorder();
			audioRecorderFolder = audioRecorder.getAudioRecorderFolder();
			
			audioRecorder.startRecording();
		}
	};
	
	//btnStop
	private View.OnClickListener btnStopHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			enableButtons(false);
			
			audioRecorder.stopRecording("temp");
			
			Log.i(LOG_TAG, "Finish recording, filename=" + audioRecorder.getSavedFilename());
			audioRecorder = null;
			
			//upload to server for identification
			Log.i(LOG_TAG, "Prepare to upload audio files...");
			
			String userName = spRecentUsers.getSelectedItem().toString();
			
			//1. upload files
			showDialog(ID_DIALOG_UPLOADING);
			startUploadTempFile();
			
		}
	};
	
	//
	private final Runnable uploadFileResult = new Runnable() {
		@Override
		public void run(){
			Toast toast = Toast.makeText(getApplicationContext(), "Uploaded temp.wav file", 
					Toast.LENGTH_SHORT);
			toast.show();
			
			//2. request voice identification 
			showDialog(ID_DIALOG_VERIFYING);
			startRequestVerifyingAudio();
		}
	};
	
	private final Runnable verifyFileResult = new Runnable() {
		@Override
		public void run(){
			Toast toast = Toast.makeText(getApplicationContext(), "Verified temp.wav file", 
					Toast.LENGTH_SHORT);
			toast.show();
		}
	};
	
	private void startRequestVerifyingAudio(){
		Thread t = new Thread() {
			@Override
			public void run() {
				String userName = spRecentUsers.getSelectedItem().toString();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", userName);
				map.put("loginId", lastLoginId);
				
				//String responseStr = HttpPostRequester.postData(Constants.IDENTIFY_URL, map);
				String responseStr = HttpPostRequester.postSecureData(Constants.IDENTIFY_URL, map,
						(App)getApplicationContext());
				
				Log.i(LOG_TAG, "responseStr="+responseStr);
				
				//
				dismissDialog(ID_DIALOG_VERIFYING);
				mHandler.post(verifyFileResult);
			}
		};
		t.start();
		
		
	}
	
	private void startUploadTempFile(){
    	Thread t = new Thread() {
			@Override
			public void run() {
				//upload all files: 0.wav,1.wav,....
				String userName = spRecentUsers.getSelectedItem().toString();
				String fileName = "temp.wav";
					
				//lastLoginId = FileUpload.doFileUpload("/sdcard/AudioRecorder/"+fileName,fileName, Constants.UPLOAD_URL, userName);	//selectedPath, fileName, urlString
				
				//
				lastLoginId = FileUpload.doSecureFileUpload("/sdcard/AudioRecorder/"+fileName,fileName, 
						Constants.UPLOAD_URL, userName, (App)getApplicationContext());	//selectedPath, fileName, urlString
				
				Log.d(LOG_TAG, "lastLoginId = " + lastLoginId);
				
				//
				dismissDialog(ID_DIALOG_UPLOADING);
				mHandler.post(uploadFileResult);
				
				
			}
		};
		t.start();
    }
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		if(id == ID_DIALOG_UPLOADING){
			ProgressDialog uploadingDialog = new ProgressDialog(this);
			uploadingDialog.setMessage("Uploading login audio file...");
			uploadingDialog.setIndeterminate(true);
			uploadingDialog.setCancelable(true);
			return uploadingDialog;
			
		}
		
		if(id == ID_DIALOG_VERIFYING){
			ProgressDialog verifyingDialog = new ProgressDialog(this);
			verifyingDialog.setMessage("Verifying login audio file...");
			verifyingDialog.setIndeterminate(true);
			verifyingDialog.setCancelable(true);
			return verifyingDialog;
			
		}
	    	
		//
		return super.onCreateDialog(id);
	}
	
}
