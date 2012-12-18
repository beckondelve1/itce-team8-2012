package postech.itce.team8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
	private static final int ID_DIALOG_VERIFYING = 1;
	
	//
	private static final int RECORDER_CHANNELS = 1;
	private static final int RECORDER_BPP = 16;
	private static final int RECORDER_SAMPLERATE = 8000;	//44100;
	//
	private static final double VOICE_CAPTCHA_THRESHHOLD = 0.5;	 
	
	//
	final Context context = this;

	SharedPreferences settings;

	//
	private Handler mHandler;
	//audio recorder
	private AudioRecorder audioRecorder;
	private String audioRecorderFolder;
	
	//
	private SpeechRecognizer recognizer;
	private Intent intent;
	private byte[] audioData = new byte[0];
	
	//
	private String lastLoginId;
	
	private List<String> sampleSentences = new ArrayList<String>();
	
	// UI controls
	private Spinner spRecentUsers;
	private TextView lblCaptcha;
	private Button btnStart;
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
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStop = (Button) findViewById(R.id.btnStop);
		btnStop.setEnabled(false);
		
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
		btnStart.setOnClickListener(btnStartHandler);
        btnStop.setOnClickListener(btnStopHandler);
        
        //always call...
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String serverAddress = sharedPrefs.getString("server_addr", "NULL");
		Log.i(LOG_TAG, "server_addr=" + serverAddress);
		Constants.updateURLs(serverAddress);
        
		//--------- SPEECH RECOGNITION -----------//
		//from: http://stackoverflow.com/questions/5913773/speech-to-text-on-android/5915010#5915010
        audioData = new byte[0];
        
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "postech.itce.voicecaptcha");

        recognizer = SpeechRecognizer
                .createSpeechRecognizer(this.getApplicationContext());
        
        recognizer.setRecognitionListener(listener);
        
        //
        btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				recognizer.startListening(intent);
		        Log.d(LOG_TAG, "recognizer started");

		        btnStart.setEnabled(false);
		        btnStop.setEnabled(true);
			}
		});
        
        btnStop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				recognizer.stopListening();
		        
		        Log.d(LOG_TAG, "recognizer stopped");
				
		        btnStart.setEnabled(true);
		        btnStop.setEnabled(false);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	//
	private void selectSampleSentences() throws Exception{
		InputStream is = getApplicationContext().getResources().getAssets().open("sample.txt");
		BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF8"));
        String line = r.readLine();
        if(line != null) {
            sampleSentences.add(line);
            line = r.readLine();
            while(line != null) {
            	sampleSentences.add(line);
                line = r.readLine();
            }
        }
        //debug
        Log.i(LOG_TAG, "Sample sentences:");
        for (String sample:sampleSentences)
        	Log.i(LOG_TAG, sample);
        
        //
		if (sampleSentences.size() > 0){
			Random random = new Random();
			int idx = random.nextInt(sampleSentences.size());
			
			lblCaptcha.setText(sampleSentences.get(idx));
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		//set random string for lblCaptcha
		try {
			selectSampleSentences();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
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
										//update spinner
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
    private View.OnClickListener btnStartHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			recognizer.startListening(intent);
	        Log.d(LOG_TAG, "recognizer started");

	        btnStart.setEnabled(false);
	        btnStop.setEnabled(true);
		}
	};
	
	//btnStop
	private View.OnClickListener btnStopHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			recognizer.stopListening();
	        
	        Log.d(LOG_TAG, "recognizer stopped");
			
	        btnStart.setEnabled(true);
	        btnStop.setEnabled(false);
			
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
	
	
	//
    private String findLongestCommonString(String str1, String str2){
    	str1 = str1.trim().toLowerCase();
		str2 = str2.trim().toLowerCase();
		
		int m = str1.length();
		int n = str2.length();
		
		//dynamic programming
		//1. init
		int[][] d = new int[m+1][n+1];
		for (int i = 0; i < m+1; i++)
			for (int j = 0; j < n+1; j++)
				d[i][j] = 0;
		
		//2. find d
		for (int i = 1; i < m+1; i++)
			for (int j = 1; j < n+1; j++)
				if (str1.charAt(i-1) == str2.charAt(j-1)) 
					d[i][j] = d[i-1][j-1] + 1;
				else
					d[i][j] = Math.max(d[i-1][j-1], Math.max(d[i-1][j], d[i][j-1]));
		//3. back track
		int i = m;
		int j = n;
		String result = "";
		while (i > 0 & j > 0){
			if (d[i][j] == d[i-1][j-1] + 1 && str1.charAt(i-1) == str2.charAt(j-1)){
				result = str1.charAt(i-1) + result;
				i--;
				j--;
			}else if (d[i][j] == d[i-1][j-1]){
				i--;
				j--;
			}else if (d[i][j] == d[i][j-1]){
				j--;
			}else if (d[i][j] == d[i-1][j]){
				i--;
			}
				
		}
		
		return result;
    }
    
    //
    private List<String> findBestMatching(String sample, ArrayList<String> voiceResults){
    	String bestMatch = "";
    	String commonString = "";
    	
    	for (String candidate:voiceResults){
    		String result = findLongestCommonString(sample, candidate);
    		if (commonString.length() < result.length()){
    			commonString = result;
    			bestMatch = candidate;
    		}
    			
    	}
    	
    	return Arrays.asList(commonString, bestMatch) ;
    }
    
    //
    private void checkCaptchaThreshold(String sample, String commonString){
    	double captchaRatio = commonString.length()/sample.length();
    	Log.i(LOG_TAG, "captchaRatio=" + captchaRatio);
    	
        if (captchaRatio >= VOICE_CAPTCHA_THRESHHOLD){
        	
        	Toast toast = Toast.makeText(getApplicationContext(), "Voice CAPTCHA: successful", 
					Toast.LENGTH_SHORT);
        	toast.show();
        	
        	//1. upload files
			showDialog(ID_DIALOG_UPLOADING);
			startUploadTempFile();
			
        }else{
        	Toast toast = Toast.makeText(getApplicationContext(), "Voice CAPTCHA: failed", 
					Toast.LENGTH_SHORT);
        	toast.show();
        }
    }
    
    //
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onResults(Bundle results) {
            ArrayList<String> voiceResults = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (voiceResults == null) {
                Log.e(LOG_TAG, "No voice results");
            } else {
                Log.d(LOG_TAG, "Printing matches: ");
                String result = "Result: \n";
                for (String match : voiceResults) {
                    Log.d(LOG_TAG, match);
                    result += match + "\n";
                }
                
                //
                String sample = lblCaptcha.getText().toString();
                List<String> matchPair = findBestMatching(sample, voiceResults);
                
                Log.i(LOG_TAG, "commonString=" + matchPair.get(0) + ":bestmatch=" + matchPair.get(1));
                
                //check CAPTCHA threshold
                checkCaptchaThreshold(sample, matchPair.get(0));
                
                
            }
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d(LOG_TAG, "Ready for speech");
        }

        @Override
        public void onError(int error) {
            Log.d(LOG_TAG,
                    "Error listening for speech: " + error);
            //
            audioData = new byte[0];
            //
            btnStart.setEnabled(true);
	        btnStop.setEnabled(false);
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(LOG_TAG, "Speech starting");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        	Log.d(LOG_TAG, "onBufferReceived: len = " + buffer.length);
        	
        	//append buffer[] to audioData[]
        	byte[] temp = new byte[buffer.length + audioData.length];
        	System.arraycopy(audioData, 0, temp, 0, audioData.length);
        	System.arraycopy(buffer, 0, temp, audioData.length, buffer.length);
        	audioData = temp;
        }

        @Override
        public void onEndOfSpeech() {
        	Log.d(LOG_TAG, "Speech ending: audioData.len = " + audioData.length);
        	//
//        	writeRawFile(audioData, AudioRecorder.getAudioRecorderFolder() + "/speech.raw");
//        	Log.d(LOG_TAG, "audio data written to RAW file");
        	//
        	writeWaveFile(audioData, AudioRecorder.getAudioRecorderFolder() + "/temp.wav");
        	Log.d(LOG_TAG, "audio data written to Wave file");
        	
        	//reset
        	audioData = new byte[0];
        	//
        	btnStart.setEnabled(true);
	        btnStop.setEnabled(false);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // TODO Auto-generated method stub

        }
    };
    
    //AUDIO
    private void writeRawFile(byte[] data,String outFilename){
    	FileOutputStream out = null;
    	try {
			out = new FileOutputStream(outFilename);
			out.write(data);
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void writeWaveFile(byte[] data,String outFilename){
		FileOutputStream out = null;
		long totalAudioLen = 0;
		long totalDataLen = totalAudioLen + 36;
		long longSampleRate = RECORDER_SAMPLERATE;
		int channels = RECORDER_CHANNELS;
		long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels/8;
		
		try {
			out = new FileOutputStream(outFilename);
			totalAudioLen = data.length;
			totalDataLen = totalAudioLen + 36;
			
			Log.i(LOG_TAG, "File size: " + totalDataLen);
			
			WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
					longSampleRate, channels, byteRate);
			
			out.write(data);
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void WriteWaveFileHeader(
			FileOutputStream out, long totalAudioLen,
			long totalDataLen, long longSampleRate, int channels,
			long byteRate) throws IOException {
		
		byte[] header = new byte[44];
		
		header[0] = 'R';  // RIFF/WAVE header
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f';  // 'fmt ' chunk
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1;  // format = 1
		header[21] = 0;
		header[22] = (byte) channels;
		header[23] = 0;
		header[24] = (byte) (longSampleRate & 0xff);
		header[25] = (byte) ((longSampleRate >> 8) & 0xff);
		header[26] = (byte) ((longSampleRate >> 16) & 0xff);
		header[27] = (byte) ((longSampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (2 * 16 / 8);  // block align
		header[33] = 0;
		header[34] = RECORDER_BPP;  // bits per sample
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (totalAudioLen & 0xff);
		header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
		header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
		header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

		out.write(header, 0, 44);
	}
}
