package net.biyee.onvifer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import net.biyee.android.BoolClass;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.RTSPoverHTTPDecoderRunnable;
import net.biyee.android.ONVIF.StreamInfo;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class PlayVideoActivity extends Activity
  implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback
{
  String _sURLFileName = "";
  BoolClass bDisposed = new BoolClass(false);
  BoolClass bMuted = new BoolClass(false);
  BoolClass bPaused = new BoolClass(false);
  BoolClass bTimedout = new BoolClass(false);
  float fVolumeLeft = 1.0F;
  float fVolumeRight = 1.0F;
  float fXdown;
  float fXup;
  float fYdown;
  float fYup;
  private SurfaceHolder holder;
  long lTimeActionBar = 9223372036854775807L;
  private MediaPlayer mMediaPlayer;
  private SurfaceViewBiyee mPreview;
  ONVIFDevice od;
  Profiles p;
  ProgressDialog pd;
  String sCodec;
  private String sProfileToken;
  String sTransportProtocol = "HTTP";
  String sURLVideo = "";
  StreamInfo si;
  ImageView.ScaleType stStretechMode = ImageView.ScaleType.CENTER_CROP;
  PowerManager.WakeLock wl;

  public void initialize(String paramString)
  {
    findViewById(2131296322).setVisibility(0);
    Button localButton = (Button)findViewById(2131296323);
    if ("H.264".equals(this.sCodec))
    {
      findViewById(2131296325).setVisibility(0);
      localButton.setText(2131099689);
      if (this.sProfileToken != null)
        break label118;
      localButton.setOnClickListener(new PlayVideoActivity.7(this, paramString));
    }
    while (true)
    {
      findViewById(2131296324).setVisibility(8);
      new PlayVideoActivity.8(this, paramString, localButton).execute(new Void[0]);
      return;
      findViewById(2131296325).setVisibility(8);
      localButton.setText(2131099688);
      break;
      label118: localButton.setVisibility(8);
    }
  }

  public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    TextView localTextView = (TextView)findViewById(2131296312);
    if ((paramInt < 100) && (paramInt != 0))
    {
      localTextView.setVisibility(0);
      localTextView.setText("Buffering: " + paramInt + "%");
    }
    while (true)
    {
      return;
      localTextView.setVisibility(8);
    }
  }

  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    try
    {
      setContentView(2130903049);
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          this.mPreview = ((SurfaceViewBiyee)findViewById(2131296309));
          this.holder = this.mPreview.getHolder();
          this.holder.addCallback(this);
          ImageButton localImageButton1 = (ImageButton)findViewById(2131296320);
          localImageButton1.setOnClickListener(new PlayVideoActivity.1(this, localImageButton1));
          ImageButton localImageButton2 = (ImageButton)findViewById(2131296319);
          localImageButton2.setOnClickListener(new PlayVideoActivity.2(this, localImageButton2));
          ImageButton localImageButton3 = (ImageButton)findViewById(2131296321);
          localImageButton3.setOnClickListener(new PlayVideoActivity.3(this, localImageButton3));
          ((Button)findViewById(2131296314)).setOnClickListener(new PlayVideoActivity.4(this));
          ((Button)findViewById(2131296316)).setOnClickListener(new PlayVideoActivity.5(this));
          this.pd = new ProgressDialog(this);
          this.pd.setMessage("Please wait...");
          this.pd.setProgressStyle(0);
          this.pd.show();
          try
          {
            String[] arrayOfString = getIntent().getExtras().getString("param").split(",");
            String str = arrayOfString[0].trim();
            this.sCodec = arrayOfString[1].trim();
            if (arrayOfString.length >= 3)
              this.sTransportProtocol = arrayOfString[2].trim();
            if (arrayOfString.length >= 4)
              this.sProfileToken = arrayOfString[3].trim();
            initialize(str);
            new PlayVideoActivity.6(this).execute(new Void[0]);
            return;
            localException1 = localException1;
            Log.d("setContentView", localException1.getMessage());
          }
          catch (Exception localException3)
          {
            while (true)
              utility.logMessageAsync(this, "Error in onCreate of PlayVideoActivity:  " + localException3.getMessage());
          }
        }
      }
      catch (Exception localException2)
      {
        while (true)
          utility.logMessageAsync(this, "Error in onCreate of PlayVideoActivity:  " + localException2.getMessage());
      }
    }
  }

  protected void onPause()
  {
    if (this.pd != null)
      this.pd.cancel();
    if (this.wl != null)
      this.wl.release();
    if (this.mMediaPlayer != null)
    {
      this.mMediaPlayer.stop();
      this.mMediaPlayer.release();
    }
    this.bDisposed.bValue = true;
    super.onPause();
  }

  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    findViewById(2131296322).setVisibility(8);
    this.pd.dismiss();
    findViewById(2131296325).setVisibility(8);
    this.mMediaPlayer.start();
  }

  protected void onRestart()
  {
    super.onRestart();
    finish();
  }

  protected void onResume()
  {
    this.bDisposed.bValue = false;
    this.wl = ((PowerManager)getSystemService("power")).newWakeLock(6, "Onvifer");
    this.wl.acquire();
    if (this.sURLVideo.length() > 0)
      playVideo();
    super.onResume();
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.lTimeActionBar = (5000L + System.currentTimeMillis());
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return super.onTouchEvent(paramMotionEvent);
      this.fXdown = paramMotionEvent.getX();
      this.fYdown = paramMotionEvent.getY();
      continue;
      this.fXup = paramMotionEvent.getX();
      this.fYup = paramMotionEvent.getY();
      float f1 = this.fXup - this.fXdown;
      float f2 = this.fYup - this.fYdown;
      if (Math.sqrt(f1 * f1 + f2 * f2) <= 3.0D)
        continue;
      utilityONVIF.PanTilt(this, this.od, this.p, -f1 / findViewById(2131296308).getWidth(), f2 / findViewById(2131296308).getHeight());
    }
  }

  public void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    setSurfaceSize();
  }

  public void playVideo()
  {
    try
    {
      new Handler().postDelayed(new PlayVideoActivity.9(this), 5000L);
      this.pd.show();
      this.pd.setMessage("Streaming URI has been retrieved.  Retrieving video...");
      if ("HTTP".equals(this.sTransportProtocol))
      {
        findViewById(2131296309).setVisibility(8);
        findViewById(2131296324).setVisibility(8);
        ImageView localImageView = (ImageView)findViewById(2131296270);
        localImageView.setVisibility(0);
        new Thread(new RTSPoverHTTPDecoderRunnable(this, this.si, this.sCodec, localImageView, this.pd, this.bDisposed, this.bTimedout, this.bPaused, this.bMuted)).start();
      }
      while (true)
      {
        this.lTimeActionBar = (8000L + System.currentTimeMillis());
        new Thread(new PlayVideoActivity.10(this)).start();
        break;
        findViewById(2131296270).setVisibility(8);
        findViewById(2131296325).setVisibility(0);
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setDataSource(utilityONVIF.GetCorrectedONVIFUrl(this.si.sAddress, this.si.sStreamURL));
        try
        {
          this.mMediaPlayer.setDisplay(this.holder);
          this.mMediaPlayer.prepareAsync();
          this.mMediaPlayer.setOnBufferingUpdateListener(this);
          this.mMediaPlayer.setOnCompletionListener(this);
          this.mMediaPlayer.setOnPreparedListener(this);
          this.mMediaPlayer.setOnVideoSizeChangedListener(this);
          this.mMediaPlayer.setAudioStreamType(3);
        }
        catch (Exception localException2)
        {
        }
      }
    }
    catch (Exception localException1)
    {
      Log.d("playVideo()", localException1.getMessage());
    }
  }

  public void setSurfaceSize()
  {
    SurfaceView localSurfaceView;
    int j;
    float f;
    if ((this.mMediaPlayer != null) && (this.mMediaPlayer.getVideoWidth() != 0) && (this.mMediaPlayer.getVideoHeight() != 0))
    {
      localSurfaceView = (SurfaceView)findViewById(2131296309);
      int i = ((View)localSurfaceView.getParent()).getWidth();
      j = ((View)localSurfaceView.getParent()).getHeight() - findViewById(2131296318).getHeight();
      f = i / this.mMediaPlayer.getVideoWidth();
      switch ($SWITCH_TABLE$android$widget$ImageView$ScaleType()[this.stStretechMode.ordinal()])
      {
      default:
      case 2:
      case 3:
      }
    }
    while (true)
    {
      ViewGroup.LayoutParams localLayoutParams = localSurfaceView.getLayoutParams();
      localLayoutParams.width = (int)(f * this.mMediaPlayer.getVideoWidth());
      localLayoutParams.height = (int)(f * this.mMediaPlayer.getVideoHeight());
      localLayoutParams.height = Math.min(localLayoutParams.height, ((View)localSurfaceView.getParent()).getHeight());
      localSurfaceView.setLayoutParams(localLayoutParams);
      this.holder.setFixedSize(localLayoutParams.width, localLayoutParams.height);
      return;
      f = Math.max(f, j / this.mMediaPlayer.getVideoHeight());
      continue;
      f = Math.min(f, j / this.mMediaPlayer.getVideoHeight());
    }
  }

  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    setSurfaceSize();
  }

  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.PlayVideoActivity
 * JD-Core Version:    0.6.0
 */