package postech.itce.team8;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterVoiceActivity extends Activity {

	//
	
	private Button btnFinish;
	private Button btnCancel;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_voice);
        //
        
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        
        Bundle savedBasicInfo = this.getIntent().getExtras();
        
        //
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
    
    //UPLOAD
    
    
}
