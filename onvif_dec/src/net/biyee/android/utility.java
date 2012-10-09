package net.biyee.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Looper;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.impl.auth.DigestScheme;

public class utility
{
  static final boolean LOG;

  public static String DigestAuthHeader(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    String str1 = getMD5Hash(paramString1 + ":" + paramString3 + ":" + paramString2);
    String str2 = getMD5Hash(paramString6 + ":" + paramString5);
    String str3 = getMD5Hash(str1 + ":" + paramString4 + ":" + str2);
    return "\r\nAuthorization: Digest username=\"" + paramString1 + "\", realm=\"" + paramString3 + "\", nonce=\"" + paramString4 + "\"," + " uri=\"" + paramString5 + "\", response=\"" + str3 + "\"";
  }

  public static byte GetBigEndianBits(byte paramByte, int paramInt1, int paramInt2)
  {
    return (0xFF & paramByte << paramInt1) >> 8 - paramInt2;
  }

  public static void ShowMessage(Activity paramActivity, String paramString)
  {
    try
    {
      if (Looper.getMainLooper().equals(Looper.myLooper()))
      {
        AlertDialog localAlertDialog = new AlertDialog.Builder(paramActivity).create();
        localAlertDialog.setCancelable(false);
        localAlertDialog.setMessage(paramString);
        localAlertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            paramDialogInterface.dismiss();
          }
        });
        localAlertDialog.show();
      }
      else
      {
        paramActivity.runOnUiThread(new Runnable(paramActivity, paramString)
        {
          public void run()
          {
            AlertDialog localAlertDialog = new AlertDialog.Builder(utility.this).create();
            localAlertDialog.setCancelable(false);
            localAlertDialog.setMessage(this.val$sMessage);
            localAlertDialog.setButton(-1, "OK", new utility.2.1(this));
            localAlertDialog.show();
          }
        });
      }
    }
    catch (Exception localException)
    {
      logMessageAsync(paramActivity, "Error in ShowMessage() for message: " + paramString + ", Error: " + localException.getMessage());
    }
  }

  public static Byte[] ToByteArray(byte[] paramArrayOfByte)
  {
    Byte[] arrayOfByte = new Byte[paramArrayOfByte.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte.length)
        return arrayOfByte;
      arrayOfByte[i] = Byte.valueOf(paramArrayOfByte[i]);
    }
  }

  public static void addButton(Context paramContext, String paramString1, String paramString2, LinearLayout paramLinearLayout, Class<?> paramClass)
  {
    Button localButton = new Button(paramContext);
    localButton.setText(paramString2);
    if (paramClass != null)
      localButton.setOnClickListener(new View.OnClickListener(paramContext, paramClass, paramString1)
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(utility.this, this.val$cls);
          localIntent.putExtra("param", this.val$sParam);
          utility.this.startActivity(localIntent);
        }
      });
    paramLinearLayout.addView(localButton);
  }

  public static void addTableRow(Context paramContext, TableLayout paramTableLayout, String paramString, Boolean paramBoolean)
  {
    if (paramBoolean != null)
      addTableRow(paramContext, paramTableLayout, paramString, paramBoolean.toString());
  }

  public static void addTableRow(Context paramContext, TableLayout paramTableLayout, String paramString1, String paramString2)
  {
    if (paramString2 != null)
    {
      TableRow localTableRow = new TableRow(paramContext);
      TextView localTextView1 = new TextView(paramContext);
      localTextView1.setPadding(2, 2, 2, 2);
      localTextView1.setText(paramString1);
      localTableRow.addView(localTextView1);
      TextView localTextView2 = new TextView(paramContext);
      localTextView2.setPadding(2, 2, 2, 2);
      localTextView2.setText(paramString2);
      localTableRow.addView(localTextView2);
      paramTableLayout.addView(localTableRow);
    }
  }

  public static <T> int arrayIndexOf(T[] paramArrayOfT1, T[] paramArrayOfT2)
  {
    int i = -1;
    if (paramArrayOfT1.length >= paramArrayOfT2.length);
    label68: label74: for (int j = 0; ; j++)
    {
      if (j > paramArrayOfT1.length - paramArrayOfT2.length)
        return i;
      int k = 1;
      for (int m = 0; ; m++)
      {
        if (m >= paramArrayOfT2.length);
        while (true)
        {
          if (k == 0)
            break label74;
          i = j;
          break;
          if (paramArrayOfT1[(j + m)].equals(paramArrayOfT2[m]))
            break label68;
          k = 0;
        }
      }
    }
  }

  public static <T> int arrayLastIndexOf(T[] paramArrayOfT1, T[] paramArrayOfT2)
  {
    int i = -1;
    if (paramArrayOfT1.length >= paramArrayOfT2.length);
    label67: label73: for (int j = paramArrayOfT1.length - paramArrayOfT2.length; ; j--)
    {
      if (j <= 0)
        return i;
      int k = 1;
      for (int m = 0; ; m++)
      {
        if (m >= paramArrayOfT2.length);
        while (true)
        {
          if (k == 0)
            break label73;
          i = j;
          break;
          if (paramArrayOfT1[(j + m)].equals(paramArrayOfT2[m]))
            break label67;
          k = 0;
        }
      }
    }
  }

  public static String getMD5Hash(String paramString)
  {
    Object localObject = null;
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes(), 0, paramString.length());
      String str = new BigInteger(1, localMessageDigest.digest()).toString(16);
      localObject = str;
      label43: return localObject;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      break label43;
    }
  }

  public static String getParamValue(String paramString1, String paramString2)
  {
    Matcher localMatcher = Pattern.compile(paramString2 + "\\s*?=\\s*?\"?(.+?)\"?(\\s|$|,)", 40).matcher(paramString1);
    if (localMatcher.find());
    for (String str = localMatcher.group(1); ; str = null)
      return str;
  }

  public static int ib(byte paramByte)
  {
    return paramByte & 0xFF;
  }

  public static final byte[] intToByteArray(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = (byte)(paramInt >>> 24);
    arrayOfByte[1] = (byte)(0xFF & paramInt >> 16);
    arrayOfByte[2] = (byte)(0xFF & paramInt >> 8);
    arrayOfByte[3] = (byte)(paramInt & 0xFF);
    return arrayOfByte;
  }

  public static byte[] intToByteArrayLSBFirst(long paramLong)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = (byte)(int)(paramLong & 0xFF);
    arrayOfByte[1] = (byte)(int)(paramLong >>> 8);
    arrayOfByte[2] = (byte)(int)(0xFF & paramLong >> 16);
    arrayOfByte[3] = (byte)(int)(0xFF & paramLong >> 24);
    return arrayOfByte;
  }

  public static <T> int listIndexOf(List<T> paramList, T[] paramArrayOfT)
  {
    int i = -1;
    if (paramList.size() >= paramArrayOfT.length);
    label80: label86: for (int j = 0; ; j++)
    {
      if (j > paramList.size() - paramArrayOfT.length)
        return i;
      int k = 1;
      for (int m = 0; ; m++)
      {
        if (m >= paramArrayOfT.length);
        while (true)
        {
          if (k == 0)
            break label86;
          i = j;
          break;
          if (paramList.get(j + m).equals(paramArrayOfT[m]))
            break label80;
          k = 0;
        }
      }
    }
  }

  public static <T> int listLastIndexOf(List<T> paramList, T[] paramArrayOfT)
  {
    int i = -1;
    if (paramList.size() >= paramArrayOfT.length);
    label79: label85: for (int j = paramList.size() - paramArrayOfT.length; ; j--)
    {
      if (j <= 0)
        return i;
      int k = 1;
      for (int m = 0; ; m++)
      {
        if (m >= paramArrayOfT.length);
        while (true)
        {
          if (k == 0)
            break label85;
          i = j;
          break;
          if (paramList.get(j + m).equals(paramArrayOfT[m]))
            break label79;
          k = 0;
        }
      }
    }
  }

  public static Bitmap loadBitmap(String paramString1, String paramString2, String paramString3)
  {
    Bitmap localBitmap = null;
    String str1;
    byte[] arrayOfByte1;
    if (paramString1 != null)
    {
      str1 = paramString1.trim();
      arrayOfByte1 = new byte[16384];
    }
    try
    {
      URL localURL = new URL(str1);
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
      localHttpURLConnection.setConnectTimeout(5000);
      localHttpURLConnection.setReadTimeout(5000);
      if (paramString2 != null)
      {
        paramString2 = paramString2.trim();
        if (paramString3 == null)
          paramString3 = "";
        paramString3 = paramString3.trim();
        String str13 = Base64.encodeToString((paramString2 + ":" + paramString3).getBytes(), 2);
        String str14 = "Basic " + str13;
        localHttpURLConnection.setRequestProperty("Authorization", str14);
      }
      String str3;
      String str4;
      String str6;
      String str7;
      String str8;
      Object localObject;
      if ((localHttpURLConnection.getResponseCode() == 401) && (localHttpURLConnection.getHeaderFields().get("WWW-Authenticate") != null))
      {
        String str2 = (String)((List)localHttpURLConnection.getHeaderFields().get("WWW-Authenticate")).get(0);
        str3 = getParamValue(str2, "realm");
        str4 = getParamValue(str2, "nonce");
        getParamValue(str2, "stale");
        String str5 = getParamValue(str2, "qop");
        str6 = getMD5Hash(paramString2 + ":" + str3 + ":" + paramString3);
        getMD5Hash("Mufasa:testrealm@host.com:Circle Of Life");
        if ((str5 == null) || ((str5 != null) && (str5.equals("auth"))))
        {
          str7 = localURL.getPath() + "?" + localURL.getQuery();
          str8 = getMD5Hash("GET:" + str7);
          if (str5 != null)
            break label580;
          String str12 = getMD5Hash(str6 + ":" + str4 + ":" + str8);
          localObject = "Digest username=\"" + paramString2 + "\", realm=\"" + str3 + "\", nonce=\"" + str4 + "\", uri=\"" + str7 + "\", response=\"" + str12 + "\"";
          localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
          localHttpURLConnection.setRequestProperty("Authorization", (String)localObject);
        }
      }
      localHttpURLConnection.getContentLength();
      InputStream localInputStream = localHttpURLConnection.getInputStream();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localByteArrayOutputStream);
      while (true)
      {
        try
        {
          int i = localInputStream.read(arrayOfByte1);
          if (i != -1)
            continue;
          localBufferedOutputStream.flush();
          byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
          BitmapFactory.Options localOptions = new BitmapFactory.Options();
          localBitmap = BitmapFactory.decodeByteArray(arrayOfByte2, 0, arrayOfByte2.length, localOptions);
          localInputStream.close();
          localBufferedOutputStream.close();
          return localBitmap;
          label580: DigestScheme.createCnonce();
          String str9 = DigestScheme.createCnonce().substring(16);
          String str10 = getMD5Hash(str6 + ":" + str4 + ":" + "00000001" + ":" + str9 + ":auth:" + str8);
          String str11 = "Digest username=\"" + paramString2 + "\", realm=\"" + str3 + "\", nonce=\"" + str4 + "\", uri=\"" + str7 + "\", cnonce=\"" + str9 + "\",nc=" + "00000001" + ", response=\"" + str10 + "\", qop=\"auth\"";
          localObject = str11;
          break;
          localBufferedOutputStream.write(arrayOfByte1, 0, i);
          continue;
        }
        catch (Exception localException1)
        {
        }
        label768: logd("Onvifer", localException1.getMessage());
      }
    }
    catch (Exception localException2)
    {
      break label768;
    }
  }

  // ERROR //
  protected static void logMessage(Context paramContext, String paramString)
    throws Exception
  {
    // Byte code:
    //   0: new 18	net/biyee/android/utility$JsonMessage
    //   3: dup
    //   4: invokespecial 421	net/biyee/android/utility$JsonMessage:<init>	()V
    //   7: astore_2
    //   8: aload_2
    //   9: aload_1
    //   10: putfield 425	net/biyee/android/utility$JsonMessage:Message	Ljava/lang/String;
    //   13: aload_0
    //   14: invokevirtual 431	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   17: astore 23
    //   19: aload 23
    //   21: aload_0
    //   22: invokevirtual 434	android/content/Context:getPackageName	()Ljava/lang/String;
    //   25: iconst_0
    //   26: invokevirtual 440	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   29: astore 24
    //   31: aload_2
    //   32: aload 23
    //   34: aload 23
    //   36: aload_0
    //   37: invokevirtual 434	android/content/Context:getPackageName	()Ljava/lang/String;
    //   40: iconst_0
    //   41: invokevirtual 444	android/content/pm/PackageManager:getApplicationInfo	(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    //   44: invokevirtual 448	android/content/pm/PackageManager:getApplicationLabel	(Landroid/content/pm/ApplicationInfo;)Ljava/lang/CharSequence;
    //   47: checkcast 31	java/lang/String
    //   50: putfield 451	net/biyee/android/utility$JsonMessage:ApplicationName	Ljava/lang/String;
    //   53: aload_2
    //   54: aload 24
    //   56: getfield 456	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   59: putfield 459	net/biyee/android/utility$JsonMessage:ApplicationVersion	Ljava/lang/String;
    //   62: aload_2
    //   63: invokestatic 465	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   66: invokevirtual 468	java/util/Locale:getDisplayLanguage	()Ljava/lang/String;
    //   69: putfield 471	net/biyee/android/utility$JsonMessage:ApplicationCulture	Ljava/lang/String;
    //   72: aload_0
    //   73: ldc_w 473
    //   76: invokevirtual 477	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   79: checkcast 479	android/app/ActivityManager
    //   82: astore 25
    //   84: new 481	android/app/ActivityManager$MemoryInfo
    //   87: dup
    //   88: invokespecial 482	android/app/ActivityManager$MemoryInfo:<init>	()V
    //   91: astore 26
    //   93: aload 25
    //   95: aload 26
    //   97: invokevirtual 486	android/app/ActivityManager:getMemoryInfo	(Landroid/app/ActivityManager$MemoryInfo;)V
    //   100: aload_2
    //   101: getstatic 491	android/os/Build$VERSION:RELEASE	Ljava/lang/String;
    //   104: putfield 494	net/biyee/android/utility$JsonMessage:DeviceFirmwareVersion	Ljava/lang/String;
    //   107: aload_2
    //   108: getstatic 499	android/os/Build:DEVICE	Ljava/lang/String;
    //   111: putfield 502	net/biyee/android/utility$JsonMessage:DeviceHardwareVersion	Ljava/lang/String;
    //   114: aload_2
    //   115: getstatic 505	android/os/Build:MANUFACTURER	Ljava/lang/String;
    //   118: putfield 508	net/biyee/android/utility$JsonMessage:DeviceManufacturer	Ljava/lang/String;
    //   121: aload_0
    //   122: invokevirtual 512	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   125: invokevirtual 518	android/content/res/Resources:getDisplayMetrics	()Landroid/util/DisplayMetrics;
    //   128: getfield 524	android/util/DisplayMetrics:densityDpi	I
    //   131: lookupswitch	default:+555->686, 120:+238->369, 160:+563->694, 213:+587->718, 240:+571->702, 320:+579->710
    //   181: new 29	java/lang/StringBuilder
    //   184: dup
    //   185: getstatic 527	android/os/Build:MODEL	Ljava/lang/String;
    //   188: invokestatic 35	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   191: invokespecial 38	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   194: ldc_w 529
    //   197: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: aload 27
    //   202: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: ldc_w 531
    //   208: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: invokevirtual 48	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: putfield 534	net/biyee/android/utility$JsonMessage:DeviceName	Ljava/lang/String;
    //   217: aload_2
    //   218: aload 26
    //   220: getfield 538	android/app/ActivityManager$MemoryInfo:availMem	J
    //   223: putfield 541	net/biyee/android/utility$JsonMessage:DeviceTotalMemory	J
    //   226: aload_0
    //   227: aconst_null
    //   228: new 543	android/content/IntentFilter
    //   231: dup
    //   232: ldc_w 545
    //   235: invokespecial 546	android/content/IntentFilter:<init>	(Ljava/lang/String;)V
    //   238: invokevirtual 550	android/content/Context:registerReceiver	(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
    //   241: ldc_w 552
    //   244: bipush 255
    //   246: invokevirtual 558	android/content/Intent:getIntExtra	(Ljava/lang/String;I)I
    //   249: istore 28
    //   251: iload 28
    //   253: iconst_1
    //   254: if_icmpeq +9 -> 263
    //   257: iload 28
    //   259: iconst_2
    //   260: if_icmpne +117 -> 377
    //   263: aload_2
    //   264: ldc_w 560
    //   267: putfield 563	net/biyee/android/utility$JsonMessage:PowerSource	Ljava/lang/String;
    //   270: new 565	com/google/gson/Gson
    //   273: dup
    //   274: invokespecial 566	com/google/gson/Gson:<init>	()V
    //   277: astore 29
    //   279: new 568	org/apache/http/entity/StringEntity
    //   282: dup
    //   283: aload 29
    //   285: aload_2
    //   286: invokevirtual 571	com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   289: invokespecial 572	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   292: astore 30
    //   294: new 574	org/apache/http/impl/client/DefaultHttpClient
    //   297: dup
    //   298: invokespecial 575	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   301: astore 31
    //   303: new 577	org/apache/http/client/methods/HttpPost
    //   306: dup
    //   307: ldc_w 579
    //   310: invokespecial 580	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   313: astore 32
    //   315: aload 32
    //   317: aload 30
    //   319: invokevirtual 584	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   322: aload 32
    //   324: ldc_w 586
    //   327: ldc_w 588
    //   330: invokevirtual 591	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   333: aload 32
    //   335: ldc_w 593
    //   338: ldc_w 588
    //   341: invokevirtual 591	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   344: new 595	org/apache/http/impl/client/BasicResponseHandler
    //   347: dup
    //   348: invokespecial 596	org/apache/http/impl/client/BasicResponseHandler:<init>	()V
    //   351: astore 34
    //   353: aload 31
    //   355: aload 32
    //   357: aload 34
    //   359: invokeinterface 602 3 0
    //   364: checkcast 31	java/lang/String
    //   367: pop
    //   368: return
    //   369: ldc_w 604
    //   372: astore 27
    //   374: goto -194 -> 180
    //   377: aload_2
    //   378: ldc_w 606
    //   381: putfield 563	net/biyee/android/utility$JsonMessage:PowerSource	Ljava/lang/String;
    //   384: goto -114 -> 270
    //   387: astore 13
    //   389: aload 13
    //   391: invokevirtual 609	android/content/pm/PackageManager$NameNotFoundException:printStackTrace	()V
    //   394: new 565	com/google/gson/Gson
    //   397: dup
    //   398: invokespecial 566	com/google/gson/Gson:<init>	()V
    //   401: astore 14
    //   403: new 568	org/apache/http/entity/StringEntity
    //   406: dup
    //   407: aload 14
    //   409: aload_2
    //   410: invokevirtual 571	com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   413: invokespecial 572	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   416: astore 15
    //   418: new 574	org/apache/http/impl/client/DefaultHttpClient
    //   421: dup
    //   422: invokespecial 575	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   425: astore 16
    //   427: new 577	org/apache/http/client/methods/HttpPost
    //   430: dup
    //   431: ldc_w 579
    //   434: invokespecial 580	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   437: astore 17
    //   439: aload 17
    //   441: aload 15
    //   443: invokevirtual 584	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   446: aload 17
    //   448: ldc_w 586
    //   451: ldc_w 588
    //   454: invokevirtual 591	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   457: aload 17
    //   459: ldc_w 593
    //   462: ldc_w 588
    //   465: invokevirtual 591	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   468: new 595	org/apache/http/impl/client/BasicResponseHandler
    //   471: dup
    //   472: invokespecial 596	org/apache/http/impl/client/BasicResponseHandler:<init>	()V
    //   475: astore 19
    //   477: aload 16
    //   479: aload 17
    //   481: aload 19
    //   483: invokeinterface 602 3 0
    //   488: checkcast 31	java/lang/String
    //   491: pop
    //   492: goto -124 -> 368
    //   495: astore 21
    //   497: aload 21
    //   499: invokevirtual 610	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   502: goto -134 -> 368
    //   505: astore 18
    //   507: aload 18
    //   509: invokevirtual 611	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   512: goto -144 -> 368
    //   515: astore 20
    //   517: aload 20
    //   519: invokevirtual 612	java/io/IOException:printStackTrace	()V
    //   522: goto -154 -> 368
    //   525: astore_3
    //   526: new 565	com/google/gson/Gson
    //   529: dup
    //   530: invokespecial 566	com/google/gson/Gson:<init>	()V
    //   533: astore 4
    //   535: new 568	org/apache/http/entity/StringEntity
    //   538: dup
    //   539: aload 4
    //   541: aload_2
    //   542: invokevirtual 571	com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   545: invokespecial 572	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   548: astore 5
    //   550: new 574	org/apache/http/impl/client/DefaultHttpClient
    //   553: dup
    //   554: invokespecial 575	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   557: astore 6
    //   559: new 577	org/apache/http/client/methods/HttpPost
    //   562: dup
    //   563: ldc_w 579
    //   566: invokespecial 580	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   569: astore 7
    //   571: aload 7
    //   573: aload 5
    //   575: invokevirtual 584	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   578: aload 7
    //   580: ldc_w 586
    //   583: ldc_w 588
    //   586: invokevirtual 591	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   589: aload 7
    //   591: ldc_w 593
    //   594: ldc_w 588
    //   597: invokevirtual 591	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   600: new 595	org/apache/http/impl/client/BasicResponseHandler
    //   603: dup
    //   604: invokespecial 596	org/apache/http/impl/client/BasicResponseHandler:<init>	()V
    //   607: astore 9
    //   609: aload 6
    //   611: aload 7
    //   613: aload 9
    //   615: invokeinterface 602 3 0
    //   620: checkcast 31	java/lang/String
    //   623: pop
    //   624: aload_3
    //   625: athrow
    //   626: astore 11
    //   628: aload 11
    //   630: invokevirtual 610	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   633: goto -9 -> 624
    //   636: astore 8
    //   638: aload 8
    //   640: invokevirtual 611	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   643: goto -19 -> 624
    //   646: astore 10
    //   648: aload 10
    //   650: invokevirtual 612	java/io/IOException:printStackTrace	()V
    //   653: goto -29 -> 624
    //   656: astore 36
    //   658: aload 36
    //   660: invokevirtual 610	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   663: goto -295 -> 368
    //   666: astore 33
    //   668: aload 33
    //   670: invokevirtual 611	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   673: goto -305 -> 368
    //   676: astore 35
    //   678: aload 35
    //   680: invokevirtual 612	java/io/IOException:printStackTrace	()V
    //   683: goto -315 -> 368
    //   686: ldc_w 614
    //   689: astore 27
    //   691: goto -511 -> 180
    //   694: ldc_w 616
    //   697: astore 27
    //   699: goto -519 -> 180
    //   702: ldc_w 618
    //   705: astore 27
    //   707: goto -527 -> 180
    //   710: ldc_w 620
    //   713: astore 27
    //   715: goto -535 -> 180
    //   718: ldc_w 622
    //   721: astore 27
    //   723: goto -543 -> 180
    //
    // Exception table:
    //   from	to	target	type
    //   8	270	387	android/content/pm/PackageManager$NameNotFoundException
    //   369	384	387	android/content/pm/PackageManager$NameNotFoundException
    //   477	492	495	org/apache/http/client/ClientProtocolException
    //   403	477	505	java/io/UnsupportedEncodingException
    //   477	492	505	java/io/UnsupportedEncodingException
    //   497	502	505	java/io/UnsupportedEncodingException
    //   517	522	505	java/io/UnsupportedEncodingException
    //   477	492	515	java/io/IOException
    //   8	270	525	finally
    //   369	384	525	finally
    //   389	394	525	finally
    //   609	624	626	org/apache/http/client/ClientProtocolException
    //   535	609	636	java/io/UnsupportedEncodingException
    //   609	624	636	java/io/UnsupportedEncodingException
    //   628	633	636	java/io/UnsupportedEncodingException
    //   648	653	636	java/io/UnsupportedEncodingException
    //   609	624	646	java/io/IOException
    //   353	368	656	org/apache/http/client/ClientProtocolException
    //   279	353	666	java/io/UnsupportedEncodingException
    //   353	368	666	java/io/UnsupportedEncodingException
    //   658	663	666	java/io/UnsupportedEncodingException
    //   678	683	666	java/io/UnsupportedEncodingException
    //   353	368	676	java/io/IOException
  }

  public static void logMessageAsync(Context paramContext, String paramString)
  {
    new Thread(new Runnable(paramContext, paramString)
    {
      public void run()
      {
        try
        {
          utility.logMessage(utility.this, this.val$sMessage);
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }).start();
  }

  public static void logd(String paramString1, String paramString2)
  {
  }

  public static void loge(String paramString1, String paramString2)
  {
  }

  public static void logi(String paramString1, String paramString2)
  {
  }

  public static void logv(String paramString1, String paramString2)
  {
  }

  public static void logw(String paramString1, String paramString2)
  {
  }

  public static long longFromByteArray(byte[] paramArrayOfByte)
  {
    long l = 0L;
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte.length)
        return l;
      l = (l << 8) + (0xFF & paramArrayOfByte[i]);
    }
  }

  public static long reverseIntToLong(int paramInt)
  {
    return longFromByteArray(intToByteArray(Integer.reverseBytes(paramInt)));
  }

  public static void showConfirmationDialog(Context paramContext, String paramString, DialogConfirmation paramDialogConfirmation)
  {
    new AlertDialog.Builder(paramContext).setMessage(paramString).setPositiveButton("Yes", new DialogInterface.OnClickListener(paramDialogConfirmation)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        utility.this.processDialogConfirmationResult(true);
      }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(paramDialogConfirmation)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        utility.this.processDialogConfirmationResult(false);
      }
    }).create().show();
  }

  static class JsonMessage
  {
    public String ApplicationCulture;
    public long ApplicationCurrentMemoryUsage;
    public long ApplicationMemoryUsageLimit;
    public String ApplicationName;
    public long ApplicationPeakMemoryUsage;
    public String ApplicationVersion;
    public String DeviceFirmwareVersion;
    public String DeviceHardwareVersion;
    public String DeviceManufacturer;
    public String DeviceName;
    public long DeviceTotalMemory;
    public boolean IsKeyboardDeployed;
    public boolean IsKeyboardPresent;
    public String Message;
    public String PowerSource;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.utility
 * JD-Core Version:    0.6.0
 */