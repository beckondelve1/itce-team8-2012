package postech.itce.team8;

import postech.itce.team8.util.AudioRecorder;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterVoiceActivity extends Activity {
	private static final String LOG_TAG = "RegisterVoiceActivity";
	//UI controls
	private Button btnRecord;
	private Button btnStop;
	private Button btnPlay;
	private Button btnNext;
	
	private Button btnFinish;
	private Button btnCancel;
	//audio recorder
	private AudioRecorder audioRecorder;
	//file uploader
	
	
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
        
        Bundle savedBasicInfo = this.getIntent().getExtras();
        //debug
        Log.i(LOG_TAG, "fullName=" + savedBasicInfo.get("fullName"));
        Log.i(LOG_TAG, "userName=" + savedBasicInfo.get("userName"));
        Log.i(LOG_TAG, "password=" + savedBasicInfo.get("password"));
        
        //
        
        
        //btnRecord
        btnRecord.setOnClickListener(btnRecordHandler);
        
        //btnStop
        btnStop.setOnClickListener(btnStopHandler);
        
        //btnPlay
        
        //btnNext
        
        
        //btnFinish
        btnFinish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO check input fields
				
				
				
				
			}
		});
        
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
    View.OnClickListener btnRecordHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.i(LOG_TAG, "Start Recording");
			enableButtons(true);
			
			audioRecorder = new AudioRecorder();
			audioRecorder.startRecording();
		}
	};
	
	View.OnClickListener btnStopHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			enableButtons(false);
			
			audioRecorder.stopRecording();
			Log.i(LOG_TAG, "Finish recording, filename=" + audioRecorder.getSavedFilename());
			audioRecorder = null;
		}
	};
}
