package postech.itce.team8;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ConsultationActivity extends Activity {

	private static final String LOG_TAG = "ConsultationActivity";
	//
	private TextView txtContent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        //
        txtContent = (TextView) findViewById(R.id.txtContent);
        
        Bundle extras = this.getIntent().getExtras();
        String agendaId = extras.getString("agendaId");
        
        txtContent.setText("agendaId = " + agendaId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_consultation, menu);
        return true;
    }
}
