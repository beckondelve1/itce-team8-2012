package com.virtualwalks.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends Activity {

	/** To display user's name in the welcome page like "Welcome ....." */
	private TextView userNameField;
	/** Takes the user to CreateWalk activity. */
	private Button createWalk;
	/** Logs out the user from the application. */
	private Button logOut;

	/**
	 * Has the Create New Walk and Log Out buttons
	 * 
	 * If the user taps the Create New Walk Button then he/she is redirected to
	 * CreateWalk Activity.
	 * 
	 * If the user taps the Log Out button he/she gets logged out of the Android
	 * application.
	 * 
	 * @param savedInstanceState
	 *            Used to resume paused activity state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		if (VWSession.userName.contentEquals("")) {
			startActivity(new Intent(Welcome.this, Main.class));
		}

		userNameField = (TextView) findViewById(R.id.welcome_userNameField);
		createWalk = (Button) findViewById(R.id.welcome_createWalkButton);
		logOut = (Button) findViewById(R.id.welcome_logoutButton);

		userNameField.setText(VWSession.userName);

		createWalk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Welcome.this, CreateWalk.class);
				startActivity(intent);
			}
		});

		logOut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				VWSession.userName = "";
				startActivity(new Intent(Welcome.this, Main.class));
			}
		});

	}
}
