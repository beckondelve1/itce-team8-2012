//http://stackoverflow.com/questions/4559930/speechrecognizer-causes-anr-i-need-help-with-android-speech-api

package postech.itce.voicecaptcha;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String LOG_TAG = "MainActivity";
	//
	private static final int RECORDER_CHANNELS = 1;
	private static final int RECORDER_BPP = 16;
	private static final int RECORDER_SAMPLERATE = 8000;	//44100;
	//
	private byte[] audioData;
	
	private TextView txtResult;
	private TextView txtError;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtError = (TextView) findViewById(R.id.txtError);
        
        //Speech Recognition 
        //from: http://stackoverflow.com/questions/5913773/speech-to-text-on-android/5915010#5915010
        audioData = new byte[0];
        
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "postech.itce.voicecaptcha");

        SpeechRecognizer recognizer = SpeechRecognizer
                .createSpeechRecognizer(this.getApplicationContext());
        RecognitionListener listener = new RecognitionListener() {
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
                    txtResult.setText(result);
                    txtError.setText("Error : NO");
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
                txtError.setText("Error : " + error);
                audioData = new byte[0];
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d(LOG_TAG, "Speech starting");
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
            	Log.d(LOG_TAG, "onBufferReceived: len = " + buffer.length);
            	//
            	byte[] temp = new byte[buffer.length + audioData.length];
            	System.arraycopy(audioData, 0, temp, 0, audioData.length);
            	System.arraycopy(buffer, 0, temp, audioData.length, buffer.length);
            	audioData = temp;
            }

            @Override
            public void onEndOfSpeech() {
            	Log.d(LOG_TAG, "Speech ending: audioData.len = " + audioData.length);
            	//
            	writeRawFile(audioData, "/mnt/sdcard/speech.raw");
            	Log.d(LOG_TAG, "audio data written to RAW file");
            	//
            	writeWaveFile(audioData, "/mnt/sdcard/speech.wav");
            	Log.d(LOG_TAG, "audio data written to Wave file");
            	
            	//
            	audioData = new byte[0];
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
        recognizer.setRecognitionListener(listener);
        recognizer.startListening(intent);
        
        Log.d(LOG_TAG, "recognizer started");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
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
