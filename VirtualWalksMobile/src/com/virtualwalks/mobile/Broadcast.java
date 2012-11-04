package com.virtualwalks.mobile;

import java.io.IOException;

import net.mkp.spydroid.CameraStreamer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * The activity that is displayed when clicked on the broadcast button.
 * 
 * The activity also implements SurfaceHolder.Callback interface to receive
 * changes to the surface.
 * 
 * The activity's UI basically consists of a SurfaceView object to display the
 * camera's view to the user.
 * 
 * The activity makes use of CameraStreamer class from the SpyDroid project to
 * stream video across LAN.
 */
public class Broadcast extends Activity implements SurfaceHolder.Callback {
	/** The variable that handles video stream over LAN */
	private CameraStreamer streamer = null;
	/** Used to prevent the phone from sleeping */
	private PowerManager.WakeLock wl;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.broadcast);
	}

	/**
	 * PowerManager's WakeLock is used in order to prevent the phone from
	 * sleeping and streamer is initialized.
	 */
	public void onStart() {
		super.onStart();

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
				"VirtualWalksWakeLock");

		SurfaceView cv = (SurfaceView) findViewById(R.id.cameraview);
		SurfaceHolder sh = cv.getHolder();

		sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		sh.addCallback(this);

		streamer = new CameraStreamer();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	/**
	 * When the surface is created streamer's setup function is called with the
	 * SurfaceHolder and the local IP of the watcher's PC.
	 * 
	 * @param holder
	 *            Android calls this function with an instance of SurfaceHolder
	 *            when the surface is first created.
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		String ip = getIntent().getStringExtra("ip");

		// Ensures that the screen stays on
		wl.acquire();

		try {
			streamer.setup(holder, ip);
		} catch (IOException e) {
			// Catch error if any and display message
		}
		streamer.start();
	}

	/**
	 * WakeLock is released and the video stream is stopped.
	 * 
	 * @param holder
	 *            Android calls this function with an instance of SurfaceHolder
	 *            when the surface is destroyed.
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		wl.release();
		streamer.stop();
	}

}
