package net.biyee.onvifer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.biyee.android.BoolClass;
import net.biyee.android.ICallback;
import net.biyee.android.ONVIF.DeviceInfo;
import net.biyee.android.ONVIF.ListDevice;
import net.biyee.android.ONVIF.ONVIFDevice;
import net.biyee.android.ONVIF.RetrieveIPSecurityDeviceInfoAsync;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

public class OnviferActivity extends Activity
  implements ICallback
{
  public static final int DEVICE_TILE_SIZE = 120;
  public BoolClass bDisposed = new BoolClass(false);
  int iDeviceListPositionForContextMenu;
  ListDevice listDevices;
  ConcurrentLinkedQueue<DeviceInfo> listDevicesForSnapshotUpdating = new ConcurrentLinkedQueue();
  ProgressDialog pd;
  PowerManager.WakeLock wl;

  private void createSnapshotUpdateList()
  {
    Iterator localIterator = this.listDevices.listDevices.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      DeviceInfo localDeviceInfo = (DeviceInfo)localIterator.next();
      try
      {
        this.listDevicesForSnapshotUpdating.add(localDeviceInfo);
      }
      catch (Exception localException)
      {
        Log.d("Onvifer", localException.getMessage());
      }
    }
  }

  private void populateGridViewSnapshots()
  {
    new OnviferActivity.10(this).start();
  }

  // ERROR //
  private net.biyee.android.ONVIF.SnapshotInfo retrieveSnapshotInfo(String paramString1, int paramInt, net.biyee.android.ONVIF.SnapshotInfo paramSnapshotInfo, String paramString2, File paramFile)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 104	net/biyee/android/ONVIF/utilityONVIF:getONVIFDevice	(Landroid/content/Context;Ljava/lang/String;)Lnet/biyee/android/ONVIF/ONVIFDevice;
    //   5: astore 6
    //   7: aload 6
    //   9: getfield 109	net/biyee/android/ONVIF/ONVIFDevice:listProfiles	Ljava/util/List;
    //   12: ifnonnull +29 -> 41
    //   15: aload_0
    //   16: new 111	java/lang/StringBuilder
    //   19: dup
    //   20: ldc 113
    //   22: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   25: aload 6
    //   27: getfield 120	net/biyee/android/ONVIF/ONVIFDevice:sName	Ljava/lang/String;
    //   30: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   36: invokestatic 133	net/biyee/android/utility:ShowMessage	(Landroid/app/Activity;Ljava/lang/String;)V
    //   39: aload_3
    //   40: areturn
    //   41: iload_2
    //   42: aload 6
    //   44: getfield 109	net/biyee/android/ONVIF/ONVIFDevice:listProfiles	Ljava/util/List;
    //   47: invokestatic 137	net/biyee/android/ONVIF/utilityONVIF:findOptimalProfile	(ILjava/util/List;)Lnet/biyee/android/ONVIF/Profiles;
    //   50: astore 8
    //   52: new 139	net/biyee/android/ONVIF/SnapshotInfo
    //   55: dup
    //   56: invokespecial 140	net/biyee/android/ONVIF/SnapshotInfo:<init>	()V
    //   59: astore 9
    //   61: aload 9
    //   63: aload_1
    //   64: putfield 143	net/biyee/android/ONVIF/SnapshotInfo:sUID	Ljava/lang/String;
    //   67: aload 9
    //   69: aload 6
    //   71: getfield 146	net/biyee/android/ONVIF/ONVIFDevice:sUserName	Ljava/lang/String;
    //   74: putfield 147	net/biyee/android/ONVIF/SnapshotInfo:sUserName	Ljava/lang/String;
    //   77: aload 9
    //   79: aload 6
    //   81: getfield 150	net/biyee/android/ONVIF/ONVIFDevice:sPassword	Ljava/lang/String;
    //   84: putfield 151	net/biyee/android/ONVIF/SnapshotInfo:sPassword	Ljava/lang/String;
    //   87: aload 9
    //   89: aload 6
    //   91: aload 8
    //   93: invokestatic 155	net/biyee/android/ONVIF/utilityONVIF:getURLSnapshot	(Lnet/biyee/android/ONVIF/ONVIFDevice;Lnet/biyee/android/ONVIF/Profiles;)Ljava/lang/String;
    //   96: putfield 158	net/biyee/android/ONVIF/SnapshotInfo:sSnapshotURL	Ljava/lang/String;
    //   99: new 160	java/io/File
    //   102: dup
    //   103: aload 5
    //   105: aload 4
    //   107: invokespecial 163	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   110: astore 11
    //   112: new 165	org/simpleframework/xml/core/Persister
    //   115: dup
    //   116: invokespecial 166	org/simpleframework/xml/core/Persister:<init>	()V
    //   119: aload 9
    //   121: aload 11
    //   123: invokeinterface 172 3 0
    //   128: aload 9
    //   130: astore_3
    //   131: goto -92 -> 39
    //   134: astore 7
    //   136: goto -97 -> 39
    //   139: astore 10
    //   141: aload 9
    //   143: astore_3
    //   144: goto -105 -> 39
    //
    // Exception table:
    //   from	to	target	type
    //   41	61	134	java/lang/Exception
    //   61	128	139	java/lang/Exception
  }

  public void callback(Object paramObject)
  {
    if (!this.bDisposed.bValue)
      try
      {
        ONVIFDevice localONVIFDevice1 = (ONVIFDevice)paramObject;
        if ((localONVIFDevice1.sError == null) || ("".equals(localONVIFDevice1.sError)))
        {
          ONVIFDevice localONVIFDevice2 = utilityONVIF.getONVIFDevice(this, ((DeviceInfo)utilityONVIF.getListDevice(this).listDevices.get(this.iDeviceListPositionForContextMenu)).uid);
          localONVIFDevice1.uid = localONVIFDevice2.uid;
          localONVIFDevice1.sAddress = localONVIFDevice2.sAddress;
          localONVIFDevice1.sName = localONVIFDevice2.sName;
          localONVIFDevice1.sUserName = localONVIFDevice2.sUserName;
          localONVIFDevice1.sPassword = localONVIFDevice2.sPassword;
          utilityONVIF.saveDeviceInfo(this, localONVIFDevice1);
          updateSnapshot(localONVIFDevice1.uid);
          if (this.pd != null)
            this.pd.dismiss();
          ((GridView)findViewById(2131296258)).invalidateViews();
          populateGridViewSnapshots();
        }
        else
        {
          utility.ShowMessage(this, "Error in updating the device: " + localONVIFDevice1.sError);
          if (this.pd != null)
            this.pd.dismiss();
        }
      }
      catch (Exception localException)
      {
        utility.logMessageAsync(this, "Error in callback() for updating a device: " + localException.getMessage());
      }
  }

  void cleanDir(String paramString)
  {
    int i = 0;
    File[] arrayOfFile = getDir(paramString, 0).listFiles();
    int j = arrayOfFile.length;
    if (i >= j)
      return;
    File localFile = arrayOfFile[i];
    int k = 0;
    Iterator localIterator = this.listDevices.listDevices.iterator();
    label45: if (!localIterator.hasNext());
    while (true)
    {
      if (k == 0)
        localFile.delete();
      i++;
      break;
      DeviceInfo localDeviceInfo = (DeviceInfo)localIterator.next();
      if (!localFile.getName().contains(localDeviceInfo.uid))
        break label45;
      k = 1;
    }
  }

  public void goToAppPage()
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    PackageManager localPackageManager = getPackageManager();
    try
    {
      if (localPackageManager.getPackageInfo(getPackageName(), 0).versionCode % 2 == 0)
        localIntent.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=net.biyee.onvifer"));
      while (true)
      {
        startActivity(localIntent);
        break;
        localIntent.setData(Uri.parse("market://details?id=net.biyee.onvifer"));
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
  }

  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
    case 2131296330:
    case 2131296331:
    case 2131296329:
    }
    while (true)
    {
      return super.onContextItemSelected(paramMenuItem);
      utility.showConfirmationDialog(this, "Are you sure?", new OnviferActivity.11(this));
      continue;
      ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(this, ((DeviceInfo)this.listDevices.listDevices.get(this.iDeviceListPositionForContextMenu)).uid);
      this.pd = new ProgressDialog(this);
      this.pd.setProgressStyle(0);
      this.pd.setMessage("Please wait...");
      this.pd.setProgressStyle(0);
      this.pd.show();
      RetrieveIPSecurityDeviceInfoAsync localRetrieveIPSecurityDeviceInfoAsync = new RetrieveIPSecurityDeviceInfoAsync(this, this.pd, getApplicationContext());
      String[] arrayOfString = new String[3];
      arrayOfString[0] = localONVIFDevice.sAddress;
      arrayOfString[1] = localONVIFDevice.sUserName;
      arrayOfString[2] = localONVIFDevice.sPassword;
      localRetrieveIPSecurityDeviceInfoAsync.execute(arrayOfString);
      continue;
      Intent localIntent = new Intent(this, ExploreActivity.class);
      localIntent.putExtra("uid", ((DeviceInfo)utilityONVIF.getListDevice(this).listDevices.get(this.iDeviceListPositionForContextMenu)).uid);
      startActivity(localIntent);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903047);
    try
    {
      GridView localGridView = (GridView)findViewById(2131296258);
      localGridView.setAdapter(new DeviceTileAdapter(this));
      localGridView.setOnItemClickListener(new OnviferActivity.1(this));
      registerForContextMenu(localGridView);
      ImageButton localImageButton = (ImageButton)findViewById(2131296283);
      localImageButton.setFocusable(true);
      localImageButton.setFocusableInTouchMode(true);
      localImageButton.requestFocus();
      localImageButton.setOnClickListener(new OnviferActivity.2(this));
      ((ImageButton)findViewById(2131296284)).setOnClickListener(new OnviferActivity.3(this));
      ((ImageButton)findViewById(2131296285)).setOnClickListener(new OnviferActivity.4(this));
      ((ImageButton)findViewById(2131296286)).setOnClickListener(new OnviferActivity.5(this));
      new OnviferActivity.6(this).execute(new Void[0]);
      ((Button)findViewById(2131296281)).setOnClickListener(new OnviferActivity.7(this));
      PackageInfo localPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      setTitle("Onvifer " + localPackageInfo.versionName);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        utility.logMessageAsync(this, "Error in onCreate() of OnviferActivity: " + localException.getMessage());
    }
  }

  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
    getMenuInflater().inflate(2131230720, paramContextMenu);
    this.iDeviceListPositionForContextMenu = ((AdapterView.AdapterContextMenuInfo)paramContextMenuInfo).position;
  }

  protected void onPause()
  {
    this.bDisposed.bValue = true;
    if (this.wl != null)
      this.wl.release();
    super.onPause();
  }

  protected void onResume()
  {
    super.onResume();
    this.bDisposed.bValue = false;
    this.wl = ((PowerManager)getSystemService("power")).newWakeLock(6, "Onvifer");
    this.wl.acquire();
    this.listDevices = utilityONVIF.getListDevice(this);
    utility.logMessageAsync(this, "Entering the main page with device list size of: " + this.listDevices.listDevices.size());
    ((GridView)findViewById(2131296258)).invalidateViews();
    if (this.listDevices.listDevices.size() > 0)
    {
      findViewById(2131296257).setVisibility(8);
      createSnapshotUpdateList();
      populateGridViewSnapshots();
      new OnviferActivity.8(this).start();
      findViewById(2131296280).setVisibility(8);
      if (this.listDevices.listDevices.size() >= 5)
        break label218;
      if (this.listDevices.listDevices.size() > 0)
        findViewById(2131296280).setVisibility(0);
      new OnviferActivity.9(this).start();
    }
    while (true)
    {
      return;
      findViewById(2131296257).setVisibility(0);
      break;
      label218: findViewById(2131296269).setVisibility(8);
    }
  }

  // ERROR //
  public android.graphics.Bitmap updateSnapshot(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: getfield 32	net/biyee/onvifer/OnviferActivity:bDisposed	Lnet/biyee/android/BoolClass;
    //   6: getfield 178	net/biyee/android/BoolClass:bValue	Z
    //   9: ifne +180 -> 189
    //   12: aconst_null
    //   13: astore_3
    //   14: new 111	java/lang/StringBuilder
    //   17: dup
    //   18: aload_1
    //   19: invokestatic 521	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   22: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   25: ldc_w 523
    //   28: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: sipush 14400
    //   34: invokevirtual 497	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   37: ldc_w 525
    //   40: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   46: astore 4
    //   48: aload_0
    //   49: ldc_w 527
    //   52: iconst_0
    //   53: invokevirtual 243	net/biyee/onvifer/OnviferActivity:getDir	(Ljava/lang/String;I)Ljava/io/File;
    //   56: astore 5
    //   58: new 160	java/io/File
    //   61: dup
    //   62: aload 5
    //   64: aload 4
    //   66: invokespecial 163	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   69: astore 6
    //   71: new 165	org/simpleframework/xml/core/Persister
    //   74: dup
    //   75: invokespecial 166	org/simpleframework/xml/core/Persister:<init>	()V
    //   78: ldc 139
    //   80: aload 6
    //   82: invokeinterface 531 3 0
    //   87: checkcast 139	net/biyee/android/ONVIF/SnapshotInfo
    //   90: astore_3
    //   91: aload_3
    //   92: ifnull +97 -> 189
    //   95: aload_3
    //   96: getfield 158	net/biyee/android/ONVIF/SnapshotInfo:sSnapshotURL	Ljava/lang/String;
    //   99: aload_3
    //   100: getfield 147	net/biyee/android/ONVIF/SnapshotInfo:sUserName	Ljava/lang/String;
    //   103: aload_3
    //   104: getfield 151	net/biyee/android/ONVIF/SnapshotInfo:sPassword	Ljava/lang/String;
    //   107: invokestatic 535	net/biyee/android/utility:loadBitmap	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   110: astore_2
    //   111: aload_2
    //   112: ifnull +77 -> 189
    //   115: new 160	java/io/File
    //   118: dup
    //   119: aload_0
    //   120: ldc_w 537
    //   123: iconst_0
    //   124: invokevirtual 243	net/biyee/onvifer/OnviferActivity:getDir	(Ljava/lang/String;I)Ljava/io/File;
    //   127: new 111	java/lang/StringBuilder
    //   130: dup
    //   131: aload_3
    //   132: getfield 143	net/biyee/android/ONVIF/SnapshotInfo:sUID	Ljava/lang/String;
    //   135: invokestatic 521	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   138: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   141: ldc_w 539
    //   144: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   150: invokespecial 163	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   153: astore 8
    //   155: getstatic 545	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   158: astore 10
    //   160: new 547	java/io/FileOutputStream
    //   163: dup
    //   164: aload 8
    //   166: invokespecial 550	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   169: astore 11
    //   171: aload_2
    //   172: aload 10
    //   174: bipush 100
    //   176: new 552	java/io/BufferedOutputStream
    //   179: dup
    //   180: aload 11
    //   182: invokespecial 555	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   185: invokevirtual 561	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   188: pop
    //   189: aload_2
    //   190: areturn
    //   191: astore 21
    //   193: aload_0
    //   194: aload_1
    //   195: sipush 14400
    //   198: aconst_null
    //   199: aload 4
    //   201: aload 5
    //   203: invokespecial 563	net/biyee/onvifer/OnviferActivity:retrieveSnapshotInfo	(Ljava/lang/String;ILnet/biyee/android/ONVIF/SnapshotInfo;Ljava/lang/String;Ljava/io/File;)Lnet/biyee/android/ONVIF/SnapshotInfo;
    //   206: astore_3
    //   207: goto -116 -> 91
    //   210: astore 13
    //   212: new 111	java/lang/StringBuilder
    //   215: dup
    //   216: invokespecial 564	java/lang/StringBuilder:<init>	()V
    //   219: astore 14
    //   221: new 160	java/io/File
    //   224: dup
    //   225: aload 5
    //   227: aload 4
    //   229: invokespecial 163	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   232: astore 15
    //   234: new 566	java/io/BufferedReader
    //   237: dup
    //   238: new 568	java/io/FileReader
    //   241: dup
    //   242: aload 15
    //   244: invokespecial 569	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   247: invokespecial 572	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   250: astore 16
    //   252: aload 16
    //   254: invokevirtual 575	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   257: astore 18
    //   259: aload 18
    //   261: ifnonnull +42 -> 303
    //   264: aload_0
    //   265: new 111	java/lang/StringBuilder
    //   268: dup
    //   269: ldc_w 577
    //   272: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   275: aload 14
    //   277: invokevirtual 580	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   280: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   283: invokestatic 238	net/biyee/android/utility:logMessageAsync	(Landroid/content/Context;Ljava/lang/String;)V
    //   286: aload_0
    //   287: aload_1
    //   288: sipush 14400
    //   291: aconst_null
    //   292: aload 4
    //   294: aload 5
    //   296: invokespecial 563	net/biyee/onvifer/OnviferActivity:retrieveSnapshotInfo	(Ljava/lang/String;ILnet/biyee/android/ONVIF/SnapshotInfo;Ljava/lang/String;Ljava/io/File;)Lnet/biyee/android/ONVIF/SnapshotInfo;
    //   299: astore_3
    //   300: goto -209 -> 91
    //   303: aload 14
    //   305: aload 18
    //   307: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: pop
    //   311: aload 14
    //   313: ldc_w 582
    //   316: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   319: pop
    //   320: goto -68 -> 252
    //   323: astore 17
    //   325: aload_0
    //   326: new 111	java/lang/StringBuilder
    //   329: dup
    //   330: ldc_w 584
    //   333: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   336: aload 17
    //   338: invokevirtual 83	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   341: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   344: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   347: invokestatic 238	net/biyee/android/utility:logMessageAsync	(Landroid/content/Context;Ljava/lang/String;)V
    //   350: goto -259 -> 91
    //   353: astore 7
    //   355: aload_0
    //   356: new 111	java/lang/StringBuilder
    //   359: dup
    //   360: ldc_w 586
    //   363: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   366: aload 7
    //   368: invokevirtual 83	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   371: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   374: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   377: invokestatic 238	net/biyee/android/utility:logMessageAsync	(Landroid/content/Context;Ljava/lang/String;)V
    //   380: goto -289 -> 91
    //   383: astore 9
    //   385: aload_0
    //   386: new 111	java/lang/StringBuilder
    //   389: dup
    //   390: ldc_w 588
    //   393: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   396: aload 9
    //   398: invokevirtual 589	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   401: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   404: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   407: invokestatic 238	net/biyee/android/utility:logMessageAsync	(Landroid/content/Context;Ljava/lang/String;)V
    //   410: goto -221 -> 189
    //
    // Exception table:
    //   from	to	target	type
    //   58	91	191	java/io/FileNotFoundException
    //   58	91	210	org/simpleframework/xml/stream/NodeException
    //   234	320	323	java/lang/Exception
    //   58	91	353	java/lang/Exception
    //   115	189	383	java/io/FileNotFoundException
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.OnviferActivity
 * JD-Core Version:    0.6.0
 */