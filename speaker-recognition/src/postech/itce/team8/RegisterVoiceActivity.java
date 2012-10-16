package postech.itce.team8;

import postech.itce.team8.util.AudioRecorder;
import postech.itce.team8.util.FileUpload;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterVoiceActivity extends Activity {
	private static final String LOG_TAG = "RegisterVoiceActivity";
	
	//private static final String UPLOAD_URL = "http://141.223.83.139:8080/itce600_server/UploadServlet";	
	private static final String UPLOAD_URL = "http://141.223.83.139:8080/itce600-team8/doctor/executeUpload.do";
	
	//UI controls
	private Button btnRecord;
	private Button btnStop;
	private Button btnPlay;
	private Button btnNext;
	
	private Button btnFinish;
	private Button btnCancel;
	
	private TextView lblSampleSentences;
	private TextView lblSampleSentence;
	//audio recorder
	private AudioRecorder audioRecorder;
	//file uploader
	
	//sample sentences
	private int currentSentence;
	private String[] sampleSentences;
	private String audioRecorderFolder;
	private int numberOfUploaded = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_voice);
        //
        btnRecord = (Button)findViewById(R.id.btnRecord);
        btnStop = (Button)findViewById(R.id.btnStop);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnNext = (Button)findViewById(R.id.btnNext);
        
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        
        lblSampleSentences = (TextView)findViewById(R.id.lblSampleSentences);
        lblSampleSentence = (TextView)findViewById(R.id.lblSampleSentence);
        
        Bundle savedBasicInfo = this.getIntent().getExtras();
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
	
	private View.OnClickListener btnStopHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			enableButtons(false);
			
			audioRecorder.stopRecording(Integer.toString(currentSentence));
			
			Log.i(LOG_TAG, "Finish recording, filename=" + audioRecorder.getSavedFilename());
			audioRecorder = null;
		}
	};
	
	private View.OnClickListener btnPlayHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
	
	private View.OnClickListener btnNextHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			currentSentence++;
			
			lblSampleSentences.setText(getString(R.string.lblSampleSentences) +" ["+ 
					Integer.toString(currentSentence+1)+"/" + sampleSentences.length + "]");
			
			lblSampleSentence.setText(sampleSentences[currentSentence]);
		}
	};
	
	//
	private View.OnClickListener btnFinishHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.i(LOG_TAG, "Prepare to upload audio files...");
			
			numberOfUploaded = 0;
			//new UploadFileTask().execute(audioRecorderFolder, UPLOAD_URL);
			new UploadFileTask().execute("/sdcard/AudioRecorder", UPLOAD_URL);
		}
	};
	
	//CLASSes
	private class UploadFileTask extends AsyncTask<String, Integer, Long>{

		@Override
		protected Long doInBackground(String... params) {
			//upload all files: 0.wav,1.wav,....
			for (int i = 0; i <= currentSentence; i++){
				String fileName = Integer.toString(i) + ".wav";
				
				FileUpload.doFileUpload(params[0]+"/"+fileName,fileName,params[1]);	//selectedPath, fileName, urlString
				numberOfUploaded++;
			}
			
			return Long.valueOf(numberOfUploaded);
		}
		
		protected void onPostExecute(Long result) {
			Toast toast = Toast.makeText(getApplicationContext(), "Uploaded " + result + " file(s)", 
					Toast.LENGTH_SHORT);
			toast.show();
	    }
		
	}
}




