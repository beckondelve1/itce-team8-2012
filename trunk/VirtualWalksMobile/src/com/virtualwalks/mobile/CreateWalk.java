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

/**
 * The Activity that displays a form to the user to fill in necessary walk
 * information.
 */
public class CreateWalk extends Activity {
	/** Text field for the walk name */
	private EditText walkNameField;
	/** Text field for the walk location */
	private EditText locFieldField;
	/** Text field for the walk city */
	private EditText walkCityField;
	/** Text field for the walk county */
	private EditText walkCountyField;
	/** Button to start broadcasting */
	private Button broadcastButton;

	/** Local IP field of watcher's PC(debugging purposes) */
	EditText ipField;

	/**
	 * Initializes the text fields in the create walk activity and makes
	 * connection to the web-site on broadcast button click.
	 * 
	 * There are mainly four input text fields for the broadcaster to fill in:
	 * Walk Name, Location(more specific), City and County
	 * 
	 * Also, there is the IP number field which is currently used for debugging
	 * purposes since the application is still only able to stream media over
	 * LAN.
	 * 
	 * When the user taps Broadcast button a VWHttpClient instance is used to
	 * post these parameters to the VirtualWalks web-site(android.jsp
	 * specifically) and the user gets redirected to the Broadcast Activity.
	 * 
	 * @param savedInstanceState
	 *            Used to resume paused Activity state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createwalk);

		ipField = (EditText) findViewById(R.id.cw_ipField);
		ipField.setText("192.168.2.2");

		if (VWSession.userName.contentEquals("")) {
			startActivity(new Intent(CreateWalk.this, Main.class));
		}

		walkNameField = (EditText) findViewById(R.id.cw_nameField);
		locFieldField = (EditText) findViewById(R.id.cw_locField);
		walkCityField = (EditText) findViewById(R.id.cw_cityField);
		walkCountyField = (EditText) findViewById(R.id.cw_countyField);
		broadcastButton = (Button) findViewById(R.id.cw_broadcastButton);

		broadcastButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO send message to server

				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("action", "create"));

				postParameters.add(new BasicNameValuePair("username",
						VWSession.userName));

				postParameters.add(new BasicNameValuePair("name", walkNameField
						.getText().toString()));

				postParameters.add(new BasicNameValuePair("location",
						locFieldField.getText().toString()));

				postParameters.add(new BasicNameValuePair("city", walkCityField
						.getText().toString()));

				postParameters.add(new BasicNameValuePair("county",
						walkCountyField.getText().toString()));

				String response = null;

				try {

					response = VWHttpClient.executeHttpPost(
							VWSession.androidServer, postParameters);

					String res = response.toString();

					res = res.replaceAll("\\s+", "");

					if (res.equals("1")) {
						Intent intent = new Intent(CreateWalk.this,
								Broadcast.class);
						intent.putExtra("ip", ipField.getText().toString());
						startActivity(intent);
					} else
						;

				} catch (Exception e) {

				}
			}
		});
	}
}
