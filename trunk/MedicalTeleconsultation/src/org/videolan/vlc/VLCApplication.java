/*****************************************************************************
 * VLCApplication.java
 *****************************************************************************
 * Copyright © 2010-2012 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/
package org.videolan.vlc;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Locale;

import postech.itce.team8.util.AdditionalKeyStoresSSLSocketFactory;
import postech.itce.teleconsultation.R;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

// hiepnh - merged with postech.itce.team8.App

public class VLCApplication extends Application {
    public final static String TAG = "VLC/VLCApplication";
    private static VLCApplication instance;

    public static Context context;
    
    @Override
    public void onCreate() {
        super.onCreate();

        // Are we using advanced debugging - locale?
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String p = pref.getString("set_locale", "");
        if (p != null && !p.equals("")) {
            Locale locale;
            // workaround due to region code
            if(p.startsWith("zh")) {
                locale = Locale.CHINA;
            } else {
                locale = new Locale(p);
            }
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }

        instance = this;
        
        // hiepnh
		context = getApplicationContext();
    }

    /**
     * Called when the overall system is running low on memory
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.w(TAG, "System is running low on memory");

        BitmapCache.getInstance().clear();
    }

    /**
     * @return the main context of the Application
     */
    public static Context getAppContext()
    {
        return instance;
    }

    /**
     * @return the main resources from the Application
     */
    public static Resources getAppResources()
    {
        return instance.getResources();
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
