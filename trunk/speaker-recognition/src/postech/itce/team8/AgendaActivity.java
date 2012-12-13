package postech.itce.team8;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AgendaActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_agenda, menu);
        return true;
    }
}
