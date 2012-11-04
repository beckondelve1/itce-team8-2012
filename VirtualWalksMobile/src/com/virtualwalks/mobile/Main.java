package com.virtualwalks.mobile;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The main Activity that displays the login page to the user.
 * 
 * @author VirtualWalks Mobile Team
 * @version 1.0
 */
public class Main extends Activity {
	/** Button to attempt log in to the application */
	private Button login;
	/** Text field to enter user name for log in */
	private EditText userName;
	/** Text field to enter password for log in */
	private EditText password;

	/**
	 * Initializes the text fields in the main(login) activity and checks
	 * authorization with the web-site on login button click. The way
	 * authorization check works is the client sends an HTTP post request to
	 * "android.jsp" at server side with client user name and password. The
	 * server responds shortly with either 1 or 0(1 if successful). If
	 * authorization is valid, the user is redirected to Welcome Activity.
	 * 
	 * @param savedInstanceState
	 *            Used to resume paused activity state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		login = (Button) findViewById(R.id.loginButton);
		final TextView tv = (TextView) findViewById(R.id.loginResultText);
		userName = (EditText) findViewById(R.id.loginUserNameField);
		password = (EditText) findViewById(R.id.loginPasswordField);

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("action", "login"));

				postParameters.add(new BasicNameValuePair("user", userName
						.getText().toString()));

				postParameters.add(new BasicNameValuePair("password", password
						.getText().toString()));

				String response = null;

				try {

					response = VWHttpClient.executeHttpPost(
							VWSession.androidServer, postParameters);

					String res = response.toString();

					res = res.replaceAll("\\s+", "");

					// login succeeded
					if (res.equals("1")) {
						tv.setText("Login successful :)");

						Intent intent = new Intent(Main.this, Welcome.class);

						VWSession.userName = userName.getText().toString();

						startActivity(intent);
					} else
						// login failed
						tv.setText("Login unsuccessful :(");

				} catch (Exception e) {

					tv.setText("Connection error!");
				}
			}
		});
	}
}