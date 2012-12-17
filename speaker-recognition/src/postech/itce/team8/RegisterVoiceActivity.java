package postech.itce.team8;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import postech.itce.team8.util.AudioRecorder;
import postech.itce.team8.util.Constants;
import postech.itce.team8.util.FileUpload;
import postech.itce.team8.util.HttpPostRequester;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterVoiceActivity extends Activity {
	private static final String LOG_TAG = "RegisterVoiceActivity";
	
	//
	private static final int ID_DIALOG_UPLOADING = 0;
	private static final int ID_DIALOG_ENROLLING = 0;
	
	//private static final String UPLOAD_URL = "http://141.223.83.139:8080/itce600_server/UploadServlet";	
	//119.202.84.55
	//172.168.148.228
	
	//UI controls
	private Button btnRecord;
	private Button btnStop;
	private Button btnPlay;
	private Button btnNext;
	
	private Button btnFinish;
	private Button btnCancel;
	
	private TextView lblSampleSentences;
	private TextView lblSampleSentence;
	//
	private Handler mHandler;
	//audio recorder
	private AudioRecorder audioRecorder;
	//file uploader
	
	//
	Bundle savedBasicInfo;
	
	//sample sentences
	private int currentSentence = 0;
	private String[] sampleSentences;
	private String audioRecorderFolder;
	private int numberOfUploaded = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_voice);
        //
        mHandler = new Handler();
        
        //
        btnRecord = (Button)findViewById(R.id.btnRecord);
        btnStop = (Button)findViewById(R.id.btnStop);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnNext = (Button)findViewById(R.id.btnNext);
        
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        
        lblSampleSentences = (TextView)findViewById(R.id.lblSampleSentences);
        lblSampleSentence = (TextView)findViewById(R.id.lblSampleSentence);
        
        savedBasicInfo = this.getIntent().getExtras();
        //debug
        Log.i(LOG_TAG, "fullName=" + savedBasicInfo.get("fullName"));
        Log.i(LOG_TAG, "userName=" + savedBasicInfo.get("userName"));
        Log.i(LOG_TAG, "password=" + savedBasicInfo.get("password"));
        
        //
        currentSentence = 0;
        sampleSentences = new String[]{getString(R.string.sample_sentence_1),
        		getString(R.string.sample_sentence_2),
        		getString(R.string.sample_sentence_3),
        		getString(R.string.sample_sentence_4),
        		getString(R.string.sample_sentence_5),
        		getString(R.string.sample_sentence_6),
        		getString(R.string.sample_sentence_7)};
        
        lblSampleSentences.setText(getString(R.string.lblSampleSentences) +" [1/"+ 
        		sampleSentences.length + "]");
        
        		//
        btnRecord.setOnClickListener(btnRecordHandler);
        btnStop.setOnClickListener(btnStopHandler);
        btnPlay.setOnClickListener(btnPlayHandler);
        btnNext.setOnClickListener(btnNextHandler);
        
        //btnFinish
        btnFinish.setOnClickListener(btnFinishHandler);
        
        btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(RegisterVoiceActivity.this, MainActivity.class));
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register_voice, menu);
        return true;
    }
    
    //RECORD
    private void enableButton(int id,boolean isEnable){
		((Button)findViewById(id)).setEnabled(isEnable);
	}
    
    private void enableButtons(boolean isRecording) {
		enableButton(R.id.btnRecord,!isRecording);
		enableButton(R.id.btnStop,isRecording);
	}
    
    
    //UPLOAD
    
    
    //HANDLERs
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
			
			audioRecorder.stopRecording(Integer.toString(currentSentence));
			
			Log.i(LOG_TAG, "Finish recording, filename=" + audioRecorder.getSavedFilename());
			audioRecorder = null;
		}
	};
	
	//btnPlay
	private View.OnClickListener btnPlayHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
	
	//btnNext
	private View.OnClickListener btnNextHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			currentSentence++;
			
			lblSampleSentences.setText(getString(R.string.lblSampleSentences) +" ["+ 
					Integer.toString(currentSentence+1)+"/" + sampleSentences.length + "]");
			
			lblSampleSentence.setText(sampleSentences[currentSentence]);
		}
	};
	
	//btnFinish
	private View.OnClickListener btnFinishHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.i(LOG_TAG, "Prepare to upload audio files...");
			
			String userName = savedBasicInfo.get("userName").toString();
			
			//1. upload files
			numberOfUploaded = 0;
			//new UploadFileTask().execute(audioRecorderFolder, UPLOAD_URL);
			//new UploadFileTask().execute("/sdcard/AudioRecorder", UPLOAD_URL, userName);
			
			//another technique
			showDialog(ID_DIALOG_UPLOADING);
			startUploadFiles();
			
		}
	};
	
	//TECHNIQUE 1
	//CLASSes
	private class UploadFileTask extends AsyncTask<String, Void, Long>{

		@Override
		protected Long doInBackground(String... params) {
			//upload all files: 0.wav,1.wav,....
			for (int i = 0; i <= currentSentence; i++){
				String fileName = Integer.toString(i) + ".wav";
				
				//FileUpload.doFileUpload(params[0]+"/"+fileName,fileName, params[1], params[2]);	//selectedPath, fileName, urlString
				FileUpload.doSecureFileUpload(params[0]+"/"+fileName,fileName, params[1], params[2],
						(App)getApplicationContext());	//selectedPath, fileName, urlString
				
				numberOfUploaded++;
			}
			
			return Long.valueOf(numberOfUploaded);
		}
		
		protected void onPostExecute(Long result) {
			Toast toast = Toast.makeText(getApplicationContext(), "Uploaded " + result + " file(s)", 
					Toast.LENGTH_SHORT);
			toast.show();
			
			//2. request voice enrollment
			String userName = savedBasicInfo.get("userName").toString();
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("userName", userName);
			map.put("numberOfFiles", Long.toString(result));
			
			//HttpPostRequester.postData(Constants.ENROLL_URL, map);
			HttpPostRequester.postSecureData(Constants.ENROLL_URL, map, (App)getApplicationContext());	
			
	    }
		
	}
	
	//TECHNIQUE 2
	//
	private final Runnable uploadFilesResult = new Runnable() {
		@Override
		public void run(){
			Toast toast = Toast.makeText(getApplicationContext(), "Uploaded " + (currentSentence+1) + " file(s)", 
					Toast.LENGTH_SHORT);
			toast.show();
			
			//2. request voice enrollment
			showDialog(ID_DIALOG_ENROLLING);
			startRequestEnrollingAudio();
			
			
		}
	};
	
	private final Runnable enrollFilesResult = new Runnable() {
		@Override
		public void run(){
			Toast toast = Toast.makeText(getApplicationContext(), "Enrolled " + (currentSentence+1) + " file(s)", 
					Toast.LENGTH_SHORT);
			toast.show();
		}
	};
	
	private void startRequestEnrollingAudio(){
		Thread t = new Thread() {
			@Override
			public void run() {
				String userName = savedBasicInfo.get("userName").toString();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", userName);
				map.put("numberOfFiles", Long.toString(currentSentence+1));
				
				//HttpPostRequester.postData(Constants.ENROLL_URL, map);
				HttpPostRequester.postSecureData(Constants.ENROLL_URL, map, (App)getApplicationContext());	
				
				//
				dismissDialog(ID_DIALOG_ENROLLING);
				mHandler.post(enrollFilesResult);
			}
		};
		t.start();
		
		
	}
	
	private void startUploadFiles(){
    	Thread t = new Thread() {
			@Override
			public void run() {
				//upload all files: 0.wav,1.wav,....
				String userName = savedBasicInfo.get("userName").toString();
				for (int i = 0; i <= currentSentence; i++){
					String fileName = Integer.toString(i) + ".wav";
					
					//FileUpload.doFileUpload("/sdcard/AudioRecorder/"+fileName,fileName, Constants.UPLOAD_URL, userName);	//selectedPath, fileName, urlString
					FileUpload.doSecureFileUpload("/sdcard/AudioRecorder/"+fileName,fileName, Constants.UPLOAD_URL, userName,
							(App)getApplicationContext());		//selectedPath, fileName, urlString
					numberOfUploaded++;
				}
				
				//delete all files: 0.wav,1.wav,....
				/*
				for (int i = 0; i <= currentSentence; i++){
					String fileName = Integer.toString(i) + ".wav";
					new File("/sdcard/AudioRecorder/"+fileName).delete();
				}
				*/
				
				//
				dismissDialog(ID_DIALOG_UPLOADING);
				mHandler.post(uploadFilesResult);
			}
		};
		t.start();
    }
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		if(id == ID_DIALOG_UPLOADING){
			ProgressDialog loadingDialog = new ProgressDialog(this);
			loadingDialog.setMessage("Uploading audio files...");
			loadingDialog.setIndeterminate(true);
			loadingDialog.setCancelable(true);
			return loadingDialog;
			
		}
		
		if(id == ID_DIALOG_ENROLLING){
			ProgressDialog loadingDialog = new ProgressDialog(this);
			loadingDialog.setMessage("Enrolling user voice...");
			loadingDialog.setIndeterminate(true);
			loadingDialog.setCancelable(true);
			return loadingDialog;
			
		}
	    	
		//
		return super.onCreateDialog(id);
	}
    
}




