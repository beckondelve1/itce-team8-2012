package postech.itce.teleconsultation;

import java.io.IOException;

import net.majorkernelpanic.networking.RtspServer;
import net.majorkernelpanic.networking.Session;
import net.majorkernelpanic.spydroid.*;
import net.majorkernelpanic.streaming.video.H264Stream;
import net.majorkernelpanic.streaming.video.VideoQuality;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CameraStreamingServer implements OnSharedPreferenceChangeListener {
	public static final String TAG = "CameraStreamingServer";

	private CustomHttpServer httpServer = null;
	private RtspServer rtspServer = null;
	private SurfaceHolder holder;
	private SurfaceView camera;
	private Context context;
	private SharedPreferences settings;
	private boolean streaming = false;
	
	public CameraStreamingServer(SurfaceView camview, Context ctx, SharedPreferences defaultpref) {
		camera = camview;
		context = ctx;
		settings = defaultpref;
		
		H264Stream.setPreferences(settings);
		settings.registerOnSharedPreferenceChangeListener(this);

		camera.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder = camera.getHolder();

		Session.setSurfaceHolder(holder);
		Session.setHandler(handler);
		Session.setDefaultAudioEncoder(settings.getBoolean("stream_audio", true)?Integer.parseInt(settings.getString("audio_encoder", "3")):0);
		Session.setDefaultVideoEncoder(settings.getBoolean("stream_video", true)?Integer.parseInt(settings.getString("video_encoder", "1")):0);
		Session.setDefaultVideoQuality(new VideoQuality(settings.getInt("video_resX", 0), 
				settings.getInt("video_resY", 0), 
				Integer.parseInt(settings.getString("video_framerate", "0")), 
				Integer.parseInt(settings.getString("video_bitrate", "0"))*1000));
	}

	public void start() {
		rtspServer = new RtspServer(8086, handler);
		httpServer = new CustomHttpServer(8080, context, handler);
	}

	// The Handler that gets information back from the RtspServer and Session
	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) { 
			switch (msg.what) {
			case RtspServer.MESSAGE_LOG:
				log((String)msg.obj);
				break;
			case RtspServer.MESSAGE_ERROR:
				log((String)msg.obj);
				break;
			case Session.MESSAGE_START:
				streaming = true;
				break;
			case Session.MESSAGE_STOP:
				streaming = false;
				break;
			case Session.MESSAGE_ERROR:
				log((String)msg.obj);
				break;
			}
		}
	};

	public void log(String s) {
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}

	// Save preferences when modified in the OptionsActivit
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("video_resX")) {
			Session.defaultVideoQuality.resX = sharedPreferences.getInt("video_resX", 0);
		}
		else if (key.equals("video_resY"))  {
			Session.defaultVideoQuality.resY = sharedPreferences.getInt("video_resY", 0);
		}
		else if (key.equals("video_framerate")) {
			Session.defaultVideoQuality.frameRate = Integer.parseInt(sharedPreferences.getString("video_framerate", "0"));
		}
		else if (key.equals("video_bitrate")) {
			Session.defaultVideoQuality.bitRate = Integer.parseInt(sharedPreferences.getString("video_bitrate", "0"))*1000;
		}
		else if (key.equals("stream_audio") || key.equals("audio_encoder")) { 
			Session.setDefaultAudioEncoder(sharedPreferences.getBoolean("stream_audio", true)?Integer.parseInt(sharedPreferences.getString("audio_encoder", "3")):0);
		}
		else if (key.equals("stream_video") || key.equals("video_encoder")) {
			Session.setDefaultVideoEncoder(sharedPreferences.getBoolean("stream_video", true)?Integer.parseInt(sharedPreferences.getString("video_encoder", "2")):0);
		}
		else if (key.equals("enable_http")) {
			if (sharedPreferences.getBoolean("enable_http", true)) {
				if (httpServer == null) httpServer = new CustomHttpServer(8080, context, handler);
			} else {
				if (httpServer != null) httpServer = null;
			}
		}
		else if (key.equals("enable_rtsp")) {
			if (sharedPreferences.getBoolean("enable_rtsp", true)) {
				if (rtspServer == null) rtspServer = new RtspServer(8086, handler);
			} else {
				if (rtspServer != null) rtspServer = null;
			}
		}	
	}

	public void stop() {
		if (httpServer != null) httpServer.stop();
		if (rtspServer != null) rtspServer.stop();
	}
	
	public void pause() {
		if (rtspServer != null) rtspServer.stop();
		CustomHttpServer.setScreenState(false);
	}

	public void restart() {
		if (rtspServer != null) {
			try {
				rtspServer.start();
			} catch (IOException e) {
				log("RtspServer could not be started : "+(e.getMessage()!=null?e.getMessage():"Unknown error"));
			}
		}

		if (httpServer != null) {
			CustomHttpServer.setScreenState(true);
			try {
				httpServer.start();
			} catch (IOException e) {
				log("HttpServer could not be started : "+(e.getMessage()!=null?e.getMessage():"Unknown error"));
			}
		}
	}
}
