package postech.itce.team8;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterBasicInfoActivity extends Activity {

	//
	private TextView txtFullName;
	private TextView txtUserName;
	private TextView txtPassword;
	private Button btnNext;
	private Button btnCancel;
	//
	private String fullName;
	private String userName;
	private String password;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_basic_info);
        //
        txtFullName = (TextView)findViewById(R.id.txtFullName);
        txtUserName = (TextView)findViewById(R.id.txtUserName);
        txtPassword = (TextView)findViewById(R.id.txtPassword);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        //
        btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO check input fields
				fullName = txtFullName.getText().toString();
				userName = txtUserName.getText().toString();
				password = txtPassword.getText().toString();
				
				Bundle savedBasicInfo = new Bundle();
				savedBasicInfo.putString("fullName", fullName);
				savedBasicInfo.putString("userName", userName);
				savedBasicInfo.putString("password", password);
				
				Intent intent = new Intent(RegisterBasicInfoActivity.this, RegisterVoiceActivity.class);
				intent.putExtras(savedBasicInfo);
				//
				startActivity(intent);
				
			}
		});
        
        btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(RegisterBasicInfoActivity.this, MainActivity.class));
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register_basic_info, menu);
        return true;
    }
}
