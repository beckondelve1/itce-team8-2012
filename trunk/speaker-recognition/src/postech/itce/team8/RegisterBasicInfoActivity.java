package postech.itce.team8;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterBasicInfoActivity extends Activity{

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
	//
	AlertDialog alertDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_basic_info);
        //
        txtFullName = (TextView)findViewById(R.id.txtFullName);
        txtFullName.requestFocus();
        txtUserName = (TextView)findViewById(R.id.txtUserName);
        txtPassword = (TextView)findViewById(R.id.txtPassword);
        
        btnNext = (Button)findViewById(R.id.btnNext);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        
        //
        alertDialog = new AlertDialog.Builder(RegisterBasicInfoActivity.this).create();
        
        
        //
        btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO check input fields
				fullName = txtFullName.getText().toString().trim();
				userName = txtUserName.getText().toString().trim();
				password = txtPassword.getText().toString().trim();
				
				Bundle savedBasicInfo = new Bundle();
				savedBasicInfo.putString("fullName", fullName);
				savedBasicInfo.putString("userName", userName);
				savedBasicInfo.putString("password", password);
				
				if (validateTextFields(fullName, userName, password)){
				
					Intent intent = new Intent(RegisterBasicInfoActivity.this, RegisterVoiceActivity.class);
					intent.putExtras(savedBasicInfo);
					//
					startActivity(intent);
				}
				
			}
		});
        
        btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(RegisterBasicInfoActivity.this, MainActivity.class));
			}
		});
        
        //
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register_basic_info, menu);
        return true;
    }

    
    private void showAlertDialog(TextView textView, String fieldName){
    	alertDialog.setTitle("Error");
    	alertDialog.setMessage(fieldName + " is empty !");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alertDialog.show();
    }
    
	//
    private boolean validateTextFields(String fullName, String userName, String password){
    	if (fullName.length() == 0){
    		showAlertDialog(txtFullName, "Full Name");
    		txtFullName.setText("");
    		txtFullName.requestFocus();
    		return false;
    	}
    		
    	if (userName.length() == 0){
    		showAlertDialog(txtUserName, "User Name");
    		txtUserName.setText("");
    		txtUserName.requestFocus();
    		return false;
    	}
    	
    	if (password.length() == 0){
    		showAlertDialog(txtPassword, "Password");
    		txtPassword.setText("");
    		txtPassword.requestFocus();
    		return false;
    	}
    	
    	//check userName exist or not
    	
    	
    	
    	return true;
    }
}
