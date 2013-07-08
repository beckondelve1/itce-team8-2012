package postech.itce.team8;

import java.io.InputStream;
import java.security.KeyStore;

import postech.itce.team8.util.AdditionalKeyStoresSSLSocketFactory;
import android.app.Application;
import android.content.Context;

public class App extends Application {
	
	public static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		//
		context = getApplicationContext();
	}

	//createAdditionalCertsSSLSocketFactory
	public org.apache.http.conn.ssl.SSLSocketFactory createAdditionalCertsSSLSocketFactory() {
	    try {
	        final KeyStore ks = KeyStore.getInstance("BKS");

	        // the bks file we generated above
	        final InputStream in = getApplicationContext().getResources().openRawResource( R.raw.hiepnh_tomcat);  
	        try {
	            // don't forget to put the password used above in strings.xml/mystore_password
	            //ks.load(in, context.getString( R.string.mystore_password ).toCharArray());
	        	ks.load(in, "asdfgh".toCharArray());
	        } finally {
	            in.close();
	        }

	        return new AdditionalKeyStoresSSLSocketFactory(ks);

	    } catch( Exception e ) {
	        throw new RuntimeException(e);
	    }
	}
}
