package net.biyee.android.ONVIF;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioTrack;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.biyee.android.BoolClass;
import net.biyee.android.utility;
import org.jcodec.codecs.h264.H264Decoder;
import org.jcodec.codecs.h264.annexb.AnnexBDemuxer;

public class RTSPoverHTTPDecoder extends AsyncTask<String, Bitmap, String>
{
  public static UUID uid = UUID.randomUUID();
  String _PathComplete;
  boolean _bIDR = false;
  boolean _bJPEGErrorReported;
  int _iNALCount = 0;
  String _sNonce;
  String _sRealm;
  public Activity activity;
  BoolClass bDisposed;
  boolean bH264Initialized = false;
  private BoolClass bMuted;
  BoolClass bPaused;
  BoolClass bTimedout;
  boolean bVideoPlayStarted = false;
  byte byteAudioRTPCPChannel = -1;
  byte byteAudioRTPChannel = -1;
  byte byteVideoRTPCPChannel = -1;
  byte byteVideoRTPChannel = -1;
  byte[] chm_ac_codelens;
  byte[] chm_ac_symbols;
  byte[] chm_dc_codelens;
  byte[] chm_dc_symbols;
  float fFrameRate = 0.0F;
  private int iAudioPayloadtype;
  private int iAudioSetupSeq;
  private int iClockRate;
  int iDisplayedFrames = 0;
  int iIndexPacketStart = 0;
  int iJPEGSizeMax;
  int iPort;
  int iProcessedFrames = 0;
  private int iSequencePre = 0;
  int iVideoPayloadtype;
  private int iVideoSetupSeq;
  int icSeq = 0;
  ImageView iv = null;
  long lProfiledTime = 0L;
  long lTimeStartMS;
  long liTimeLastData = 9223372036854775807L;
  ArrayList<ArrayList<Byte>> listQuantizationTables = new ArrayList();
  ArrayList<Byte> listSample = new ArrayList();
  byte[] lum_ac_codelens;
  byte[] lum_ac_symbols;
  byte[] lum_dc_codelens;
  byte[] lum_dc_symbols;
  OutputStream osPost;
  private String packetization_mode;
  Pattern patternAuthentication;
  Pattern patternDescribe;
  Pattern patternOptions;
  Pattern patternPlay;
  ProgressDialog pd;
  private String profile_level_id;
  Queue<Byte> qH264Stream = new ArrayBlockingQueue(196608);
  private String sAudioControlURL;
  private String sEncodingName;
  String sPlayURL;
  String sProgress = "";
  private String sSession;
  String sURL;
  private String sVideoControlURL;
  StreamInfo si;
  private String sprop_parameter_sets;
  AudioTrack track;
  int uireceivedPacketCount = 0;
  long ulFrameTime = 0L;
  long ulFrameTimeRe = 0L;
  Uri uri;

  public RTSPoverHTTPDecoder(Activity paramActivity, StreamInfo paramStreamInfo, ImageView paramImageView, ProgressDialog paramProgressDialog, BoolClass paramBoolClass1, BoolClass paramBoolClass2, BoolClass paramBoolClass3, BoolClass paramBoolClass4)
  {
    byte[] arrayOfByte1 = new byte[16];
    arrayOfByte1[1] = 1;
    arrayOfByte1[2] = 5;
    arrayOfByte1[3] = 1;
    arrayOfByte1[4] = 1;
    arrayOfByte1[5] = 1;
    arrayOfByte1[6] = 1;
    arrayOfByte1[7] = 1;
    arrayOfByte1[8] = 1;
    this.lum_dc_codelens = arrayOfByte1;
    byte[] arrayOfByte2 = new byte[12];
    arrayOfByte2[1] = 1;
    arrayOfByte2[2] = 2;
    arrayOfByte2[3] = 3;
    arrayOfByte2[4] = 4;
    arrayOfByte2[5] = 5;
    arrayOfByte2[6] = 6;
    arrayOfByte2[7] = 7;
    arrayOfByte2[8] = 8;
    arrayOfByte2[9] = 9;
    arrayOfByte2[10] = 10;
    arrayOfByte2[11] = 11;
    this.lum_dc_symbols = arrayOfByte2;
    byte[] arrayOfByte3 = new byte[16];
    arrayOfByte3[1] = 2;
    arrayOfByte3[2] = 1;
    arrayOfByte3[3] = 3;
    arrayOfByte3[4] = 3;
    arrayOfByte3[5] = 2;
    arrayOfByte3[6] = 4;
    arrayOfByte3[7] = 3;
    arrayOfByte3[8] = 5;
    arrayOfByte3[9] = 5;
    arrayOfByte3[10] = 4;
    arrayOfByte3[11] = 4;
    arrayOfByte3[14] = 1;
    arrayOfByte3[15] = 125;
    this.lum_ac_codelens = arrayOfByte3;
    byte[] arrayOfByte4 = new byte['¢'];
    arrayOfByte4[0] = 1;
    arrayOfByte4[1] = 2;
    arrayOfByte4[2] = 3;
    arrayOfByte4[4] = 4;
    arrayOfByte4[5] = 17;
    arrayOfByte4[6] = 5;
    arrayOfByte4[7] = 18;
    arrayOfByte4[8] = 33;
    arrayOfByte4[9] = 49;
    arrayOfByte4[10] = 65;
    arrayOfByte4[11] = 6;
    arrayOfByte4[12] = 19;
    arrayOfByte4[13] = 81;
    arrayOfByte4[14] = 97;
    arrayOfByte4[15] = 7;
    arrayOfByte4[16] = 34;
    arrayOfByte4[17] = 113;
    arrayOfByte4[18] = 20;
    arrayOfByte4[19] = 50;
    arrayOfByte4[20] = -127;
    arrayOfByte4[21] = -111;
    arrayOfByte4[22] = -95;
    arrayOfByte4[23] = 8;
    arrayOfByte4[24] = 35;
    arrayOfByte4[25] = 66;
    arrayOfByte4[26] = -79;
    arrayOfByte4[27] = -63;
    arrayOfByte4[28] = 21;
    arrayOfByte4[29] = 82;
    arrayOfByte4[30] = -47;
    arrayOfByte4[31] = -16;
    arrayOfByte4[32] = 36;
    arrayOfByte4[33] = 51;
    arrayOfByte4[34] = 98;
    arrayOfByte4[35] = 114;
    arrayOfByte4[36] = -126;
    arrayOfByte4[37] = 9;
    arrayOfByte4[38] = 10;
    arrayOfByte4[39] = 22;
    arrayOfByte4[40] = 23;
    arrayOfByte4[41] = 24;
    arrayOfByte4[42] = 25;
    arrayOfByte4[43] = 26;
    arrayOfByte4[44] = 37;
    arrayOfByte4[45] = 38;
    arrayOfByte4[46] = 39;
    arrayOfByte4[47] = 40;
    arrayOfByte4[48] = 41;
    arrayOfByte4[49] = 42;
    arrayOfByte4[50] = 52;
    arrayOfByte4[51] = 53;
    arrayOfByte4[52] = 54;
    arrayOfByte4[53] = 55;
    arrayOfByte4[54] = 56;
    arrayOfByte4[55] = 57;
    arrayOfByte4[56] = 58;
    arrayOfByte4[57] = 67;
    arrayOfByte4[58] = 68;
    arrayOfByte4[59] = 69;
    arrayOfByte4[60] = 70;
    arrayOfByte4[61] = 71;
    arrayOfByte4[62] = 72;
    arrayOfByte4[63] = 73;
    arrayOfByte4[64] = 74;
    arrayOfByte4[65] = 83;
    arrayOfByte4[66] = 84;
    arrayOfByte4[67] = 85;
    arrayOfByte4[68] = 86;
    arrayOfByte4[69] = 87;
    arrayOfByte4[70] = 88;
    arrayOfByte4[71] = 89;
    arrayOfByte4[72] = 90;
    arrayOfByte4[73] = 99;
    arrayOfByte4[74] = 100;
    arrayOfByte4[75] = 101;
    arrayOfByte4[76] = 102;
    arrayOfByte4[77] = 103;
    arrayOfByte4[78] = 104;
    arrayOfByte4[79] = 105;
    arrayOfByte4[80] = 106;
    arrayOfByte4[81] = 115;
    arrayOfByte4[82] = 116;
    arrayOfByte4[83] = 117;
    arrayOfByte4[84] = 118;
    arrayOfByte4[85] = 119;
    arrayOfByte4[86] = 120;
    arrayOfByte4[87] = 121;
    arrayOfByte4[88] = 122;
    arrayOfByte4[89] = -125;
    arrayOfByte4[90] = -124;
    arrayOfByte4[91] = -123;
    arrayOfByte4[92] = -122;
    arrayOfByte4[93] = -121;
    arrayOfByte4[94] = -120;
    arrayOfByte4[95] = -119;
    arrayOfByte4[96] = -118;
    arrayOfByte4[97] = -110;
    arrayOfByte4[98] = -109;
    arrayOfByte4[99] = -108;
    arrayOfByte4[100] = -107;
    arrayOfByte4[101] = -106;
    arrayOfByte4[102] = -105;
    arrayOfByte4[103] = -104;
    arrayOfByte4[104] = -103;
    arrayOfByte4[105] = -102;
    arrayOfByte4[106] = -94;
    arrayOfByte4[107] = -93;
    arrayOfByte4[108] = -92;
    arrayOfByte4[109] = -91;
    arrayOfByte4[110] = -90;
    arrayOfByte4[111] = -89;
    arrayOfByte4[112] = -88;
    arrayOfByte4[113] = -87;
    arrayOfByte4[114] = -86;
    arrayOfByte4[115] = -78;
    arrayOfByte4[116] = -77;
    arrayOfByte4[117] = -76;
    arrayOfByte4[118] = -75;
    arrayOfByte4[119] = -74;
    arrayOfByte4[120] = -73;
    arrayOfByte4[121] = -72;
    arrayOfByte4[122] = -71;
    arrayOfByte4[123] = -70;
    arrayOfByte4[124] = -62;
    arrayOfByte4[125] = -61;
    arrayOfByte4[126] = -60;
    arrayOfByte4[127] = -59;
    arrayOfByte4[''] = -58;
    arrayOfByte4[''] = -57;
    arrayOfByte4[''] = -56;
    arrayOfByte4[''] = -55;
    arrayOfByte4[''] = -54;
    arrayOfByte4[''] = -46;
    arrayOfByte4[''] = -45;
    arrayOfByte4[''] = -44;
    arrayOfByte4[''] = -43;
    arrayOfByte4[''] = -42;
    arrayOfByte4[''] = -41;
    arrayOfByte4[''] = -40;
    arrayOfByte4[''] = -39;
    arrayOfByte4[''] = -38;
    arrayOfByte4[''] = -31;
    arrayOfByte4[''] = -30;
    arrayOfByte4[''] = -29;
    arrayOfByte4[''] = -28;
    arrayOfByte4[''] = -27;
    arrayOfByte4[''] = -26;
    arrayOfByte4[''] = -25;
    arrayOfByte4[''] = -24;
    arrayOfByte4[''] = -23;
    arrayOfByte4[''] = -22;
    arrayOfByte4[''] = -15;
    arrayOfByte4[''] = -14;
    arrayOfByte4[''] = -13;
    arrayOfByte4[''] = -12;
    arrayOfByte4[''] = -11;
    arrayOfByte4[''] = -10;
    arrayOfByte4[''] = -9;
    arrayOfByte4[''] = -8;
    arrayOfByte4[' '] = -7;
    arrayOfByte4['¡'] = -6;
    this.lum_ac_symbols = arrayOfByte4;
    byte[] arrayOfByte5 = new byte[16];
    arrayOfByte5[1] = 3;
    arrayOfByte5[2] = 1;
    arrayOfByte5[3] = 1;
    arrayOfByte5[4] = 1;
    arrayOfByte5[5] = 1;
    arrayOfByte5[6] = 1;
    arrayOfByte5[7] = 1;
    arrayOfByte5[8] = 1;
    arrayOfByte5[9] = 1;
    arrayOfByte5[10] = 1;
    this.chm_dc_codelens = arrayOfByte5;
    byte[] arrayOfByte6 = new byte[12];
    arrayOfByte6[1] = 1;
    arrayOfByte6[2] = 2;
    arrayOfByte6[3] = 3;
    arrayOfByte6[4] = 4;
    arrayOfByte6[5] = 5;
    arrayOfByte6[6] = 6;
    arrayOfByte6[7] = 7;
    arrayOfByte6[8] = 8;
    arrayOfByte6[9] = 9;
    arrayOfByte6[10] = 10;
    arrayOfByte6[11] = 11;
    this.chm_dc_symbols = arrayOfByte6;
    byte[] arrayOfByte7 = new byte[16];
    arrayOfByte7[1] = 2;
    arrayOfByte7[2] = 1;
    arrayOfByte7[3] = 2;
    arrayOfByte7[4] = 4;
    arrayOfByte7[5] = 4;
    arrayOfByte7[6] = 3;
    arrayOfByte7[7] = 4;
    arrayOfByte7[8] = 7;
    arrayOfByte7[9] = 5;
    arrayOfByte7[10] = 4;
    arrayOfByte7[11] = 4;
    arrayOfByte7[13] = 1;
    arrayOfByte7[14] = 2;
    arrayOfByte7[15] = 119;
    this.chm_ac_codelens = arrayOfByte7;
    byte[] arrayOfByte8 = new byte['¢'];
    arrayOfByte8[1] = 1;
    arrayOfByte8[2] = 2;
    arrayOfByte8[3] = 3;
    arrayOfByte8[4] = 17;
    arrayOfByte8[5] = 4;
    arrayOfByte8[6] = 5;
    arrayOfByte8[7] = 33;
    arrayOfByte8[8] = 49;
    arrayOfByte8[9] = 6;
    arrayOfByte8[10] = 18;
    arrayOfByte8[11] = 65;
    arrayOfByte8[12] = 81;
    arrayOfByte8[13] = 7;
    arrayOfByte8[14] = 97;
    arrayOfByte8[15] = 113;
    arrayOfByte8[16] = 19;
    arrayOfByte8[17] = 34;
    arrayOfByte8[18] = 50;
    arrayOfByte8[19] = -127;
    arrayOfByte8[20] = 8;
    arrayOfByte8[21] = 20;
    arrayOfByte8[22] = 66;
    arrayOfByte8[23] = -111;
    arrayOfByte8[24] = -95;
    arrayOfByte8[25] = -79;
    arrayOfByte8[26] = -63;
    arrayOfByte8[27] = 9;
    arrayOfByte8[28] = 35;
    arrayOfByte8[29] = 51;
    arrayOfByte8[30] = 82;
    arrayOfByte8[31] = -16;
    arrayOfByte8[32] = 21;
    arrayOfByte8[33] = 98;
    arrayOfByte8[34] = 114;
    arrayOfByte8[35] = -47;
    arrayOfByte8[36] = 10;
    arrayOfByte8[37] = 22;
    arrayOfByte8[38] = 36;
    arrayOfByte8[39] = 52;
    arrayOfByte8[40] = -31;
    arrayOfByte8[41] = 37;
    arrayOfByte8[42] = -15;
    arrayOfByte8[43] = 23;
    arrayOfByte8[44] = 24;
    arrayOfByte8[45] = 25;
    arrayOfByte8[46] = 26;
    arrayOfByte8[47] = 38;
    arrayOfByte8[48] = 39;
    arrayOfByte8[49] = 40;
    arrayOfByte8[50] = 41;
    arrayOfByte8[51] = 42;
    arrayOfByte8[52] = 53;
    arrayOfByte8[53] = 54;
    arrayOfByte8[54] = 55;
    arrayOfByte8[55] = 56;
    arrayOfByte8[56] = 57;
    arrayOfByte8[57] = 58;
    arrayOfByte8[58] = 67;
    arrayOfByte8[59] = 68;
    arrayOfByte8[60] = 69;
    arrayOfByte8[61] = 70;
    arrayOfByte8[62] = 71;
    arrayOfByte8[63] = 72;
    arrayOfByte8[64] = 73;
    arrayOfByte8[65] = 74;
    arrayOfByte8[66] = 83;
    arrayOfByte8[67] = 84;
    arrayOfByte8[68] = 85;
    arrayOfByte8[69] = 86;
    arrayOfByte8[70] = 87;
    arrayOfByte8[71] = 88;
    arrayOfByte8[72] = 89;
    arrayOfByte8[73] = 90;
    arrayOfByte8[74] = 99;
    arrayOfByte8[75] = 100;
    arrayOfByte8[76] = 101;
    arrayOfByte8[77] = 102;
    arrayOfByte8[78] = 103;
    arrayOfByte8[79] = 104;
    arrayOfByte8[80] = 105;
    arrayOfByte8[81] = 106;
    arrayOfByte8[82] = 115;
    arrayOfByte8[83] = 116;
    arrayOfByte8[84] = 117;
    arrayOfByte8[85] = 118;
    arrayOfByte8[86] = 119;
    arrayOfByte8[87] = 120;
    arrayOfByte8[88] = 121;
    arrayOfByte8[89] = 122;
    arrayOfByte8[90] = -126;
    arrayOfByte8[91] = -125;
    arrayOfByte8[92] = -124;
    arrayOfByte8[93] = -123;
    arrayOfByte8[94] = -122;
    arrayOfByte8[95] = -121;
    arrayOfByte8[96] = -120;
    arrayOfByte8[97] = -119;
    arrayOfByte8[98] = -118;
    arrayOfByte8[99] = -110;
    arrayOfByte8[100] = -109;
    arrayOfByte8[101] = -108;
    arrayOfByte8[102] = -107;
    arrayOfByte8[103] = -106;
    arrayOfByte8[104] = -105;
    arrayOfByte8[105] = -104;
    arrayOfByte8[106] = -103;
    arrayOfByte8[107] = -102;
    arrayOfByte8[108] = -94;
    arrayOfByte8[109] = -93;
    arrayOfByte8[110] = -92;
    arrayOfByte8[111] = -91;
    arrayOfByte8[112] = -90;
    arrayOfByte8[113] = -89;
    arrayOfByte8[114] = -88;
    arrayOfByte8[115] = -87;
    arrayOfByte8[116] = -86;
    arrayOfByte8[117] = -78;
    arrayOfByte8[118] = -77;
    arrayOfByte8[119] = -76;
    arrayOfByte8[120] = -75;
    arrayOfByte8[121] = -74;
    arrayOfByte8[122] = -73;
    arrayOfByte8[123] = -72;
    arrayOfByte8[124] = -71;
    arrayOfByte8[125] = -70;
    arrayOfByte8[126] = -62;
    arrayOfByte8[127] = -61;
    arrayOfByte8[''] = -60;
    arrayOfByte8[''] = -59;
    arrayOfByte8[''] = -58;
    arrayOfByte8[''] = -57;
    arrayOfByte8[''] = -56;
    arrayOfByte8[''] = -55;
    arrayOfByte8[''] = -54;
    arrayOfByte8[''] = -46;
    arrayOfByte8[''] = -45;
    arrayOfByte8[''] = -44;
    arrayOfByte8[''] = -43;
    arrayOfByte8[''] = -42;
    arrayOfByte8[''] = -41;
    arrayOfByte8[''] = -40;
    arrayOfByte8[''] = -39;
    arrayOfByte8[''] = -38;
    arrayOfByte8[''] = -30;
    arrayOfByte8[''] = -29;
    arrayOfByte8[''] = -28;
    arrayOfByte8[''] = -27;
    arrayOfByte8[''] = -26;
    arrayOfByte8[''] = -25;
    arrayOfByte8[''] = -24;
    arrayOfByte8[''] = -23;
    arrayOfByte8[''] = -22;
    arrayOfByte8[''] = -14;
    arrayOfByte8[''] = -13;
    arrayOfByte8[''] = -12;
    arrayOfByte8[''] = -11;
    arrayOfByte8[''] = -10;
    arrayOfByte8[''] = -9;
    arrayOfByte8[''] = -8;
    arrayOfByte8[' '] = -7;
    arrayOfByte8['¡'] = -6;
    this.chm_ac_symbols = arrayOfByte8;
    this.iJPEGSizeMax = 1024;
    this._bJPEGErrorReported = false;
    this.patternOptions = Pattern.compile("^RTSP/1.0 200 OK.*?Public.+?\\r\\n$", 40);
    this.patternDescribe = Pattern.compile("RTSP/1.0 200 OK.*?^Content-Type:.*?application/sdp.+?\\r\\n$.+?\\r\\n$", 42);
    this.patternPlay = Pattern.compile("^RTSP/1.0 200 OK.*?^RTP-Info:.+?\\r\\n$", 40);
    this.patternAuthentication = Pattern.compile("WWW-Authenticate:.*?Digest realm.*?=.*?\"(.+?)\".+?nonce.*?=.*?\"(.+?)\"", 40);
    this.iVideoPayloadtype = 255;
    this.iv = paramImageView;
    this.activity = paramActivity;
    this.pd = paramProgressDialog;
    this.bDisposed = paramBoolClass1;
    this.lTimeStartMS = new Date().getTime();
    this.bTimedout = paramBoolClass2;
    this.si = paramStreamInfo;
    this.bPaused = paramBoolClass3;
    this.bMuted = paramBoolClass4;
  }

  private void MakeHuffmanHeader(ArrayList<Byte> paramArrayList, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
  {
    paramArrayList.add(Byte.valueOf(-1));
    paramArrayList.add(Byte.valueOf(-60));
    paramArrayList.add(Byte.valueOf(0));
    paramArrayList.add(Byte.valueOf((byte)(3 + paramArrayOfByte1.length + paramArrayOfByte2.length)));
    paramArrayList.add(Byte.valueOf((byte)(paramInt1 | paramInt2 << 4)));
    int i = 0;
    if (i >= paramArrayOfByte1.length);
    for (int j = 0; ; j++)
    {
      if (j >= paramArrayOfByte2.length)
      {
        return;
        paramArrayList.add(Byte.valueOf(paramArrayOfByte1[i]));
        i++;
        break;
      }
      paramArrayList.add(Byte.valueOf(paramArrayOfByte2[j]));
    }
  }

  private void ProcessAudioPayload(byte[] paramArrayOfByte, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    short[] arrayOfShort;
    int i;
    int j;
    int i4;
    if (this.sEncodingName == "G.711")
    {
      arrayOfShort = new short[paramArrayOfByte.length - paramInt2];
      i = 0;
      j = paramInt2;
      if (j < paramArrayOfByte.length)
        break label88;
      if ((this.track != null) && (!this.bMuted.bValue))
      {
        i4 = this.track.write(arrayOfShort, 0, arrayOfShort.length);
        if (i4 <= 0)
          break label192;
        if (this.pd.isShowing())
          this.pd.dismiss();
      }
    }
    while (true)
    {
      return;
      label88: int k = (byte)(0xFFFFFFFF ^ paramArrayOfByte[j]);
      int m = utility.GetBigEndianBits(k, 1, 3);
      if ((k & 0x80) > 0);
      for (int n = 1; ; n = 0)
      {
        int i1 = (short)(1 << m + 5) + (short)(utility.GetBigEndianBits(k, 4, 4) << m + 1);
        if (n != 0)
          i1 *= -1;
        int i2 = 4 * (i1 - 32);
        int i3 = i + 1;
        arrayOfShort[i] = i2;
        j++;
        i = i3;
        break;
      }
      label192: utility.logd("AudioTrack write error:", String.valueOf(i4));
    }
  }

  private boolean ProcessData(ArrayList<Byte> paramArrayList, boolean paramBoolean)
  {
    boolean bool = false;
    PackeType localPackeType = PackeType.VideoRTPCP;
    int i = 0;
    label853: 
    do
    {
      while (true)
      {
        byte[] arrayOfByte2;
        int i6;
        try
        {
          int j = utility.listIndexOf(paramArrayList, utility.ToByteArray(("$" + (char)this.byteVideoRTPChannel).getBytes()));
          this.iIndexPacketStart = j;
          if (j < 0)
            continue;
          localPackeType = PackeType.VideoRTP;
          i = 1;
          if (i == 0)
            break;
          if (paramArrayList.size() <= 2 + (2 + this.iIndexPacketStart))
            break label859;
          int i4 = (utility.ib(((Byte)paramArrayList.get(2 + this.iIndexPacketStart)).byteValue()) << 8) + utility.ib(((Byte)paramArrayList.get(3 + this.iIndexPacketStart)).byteValue());
          if (this.iIndexPacketStart <= 0)
            continue;
          ArrayList localArrayList = new ArrayList();
          int i5 = 0;
          if (i5 < this.iIndexPacketStart)
            continue;
          paramArrayList.subList(0, this.iIndexPacketStart).clear();
          bool = true;
          break label853;
          int k = utility.listIndexOf(paramArrayList, utility.ToByteArray(("$" + (char)this.byteVideoRTPCPChannel).getBytes()));
          this.iIndexPacketStart = k;
          if (k < 0)
            continue;
          localPackeType = PackeType.VideoRTPCP;
          i = 1;
          continue;
          int m = utility.listIndexOf(paramArrayList, utility.ToByteArray(("$" + (char)this.byteAudioRTPChannel).getBytes()));
          this.iIndexPacketStart = m;
          if (m < 0)
            continue;
          localPackeType = PackeType.AudioRTP;
          i = 1;
          continue;
          int n = utility.listIndexOf(paramArrayList, utility.ToByteArray(("$" + (char)this.byteAudioRTPCPChannel).getBytes()));
          this.iIndexPacketStart = n;
          if (n < 0)
            continue;
          localPackeType = PackeType.AudioRTPCP;
          i = 1;
          continue;
          localArrayList.add((Byte)paramArrayList.get(i5));
          i5++;
          continue;
          bool = ProcessData(localArrayList, bool);
          break label853;
          if (paramArrayList.size() < i4 + (2 + (2 + this.iIndexPacketStart)))
            break label859;
          arrayOfByte2 = new byte[i4];
          i6 = 0;
          if (i6 >= i4)
          {
            paramArrayList.subList(0, i4 + 4).clear();
            if (paramArrayList.size() <= 0)
              continue;
            bool = true;
            if (i4 <= 12)
              break label859;
            Process_RTP_RTCP_acket(localPackeType, arrayOfByte2);
          }
        }
        catch (Exception localException)
        {
          utility.logMessageAsync(this.activity, "Error in ProcessData of RTSPoverHTTPDecoder(): " + localException.getMessage());
        }
        int i7 = i6 + 4;
        arrayOfByte2[i6] = ((Byte)paramArrayList.get(i7)).byteValue();
        i6++;
      }
      int i1 = 2 + utility.listLastIndexOf(paramArrayList, utility.ToByteArray("\r\n".getBytes()));
      byte[] arrayOfByte1 = new byte[i1];
      String str;
      Pattern localPattern2;
      for (int i2 = 0; ; i2++)
      {
        if (i2 >= i1)
        {
          str = new String(arrayOfByte1);
          Pattern localPattern1 = Pattern.compile(".+?/1.0 200 OK.+?application/x-rtsp-tunnelled.*?\\r\\n\\r\\n", 32);
          localPattern2 = Pattern.compile("(RTSP/1.0 200 OK.+?\r\n\r\n)|(RTSP/1.0.+?WWW-Authenticate.+?\r\n\r\n)", 32);
          Matcher localMatcher1 = localPattern1.matcher(str);
          if (!localMatcher1.find())
            break;
          localMatcher1.find(0);
          paramArrayList.subList(0, localMatcher1.start() + localMatcher1.group().length()).clear();
          if (paramArrayList.size() > 0)
            bool = true;
          this.sProgress = "Communication channels have been established. Requesting video source information...";
          Bitmap[] arrayOfBitmap = new Bitmap[1];
          arrayOfBitmap[0] = null;
          publishProgress(arrayOfBitmap);
          break label859;
        }
        arrayOfByte1[i2] = ((Byte)paramArrayList.get(i2)).byteValue();
      }
      Matcher localMatcher2 = localPattern2.matcher(str);
      if (!localMatcher2.find())
        break;
      localMatcher2.find(0);
      if (localMatcher2.group().contains("Content-Length:"))
      {
        Matcher localMatcher3 = Pattern.compile("RTSP/1.0 200 OK.+?^Content-Length:.+?\r\n$.+?\r\n$", 40).matcher(str);
        if (localMatcher3.find())
        {
          localMatcher3.find(0);
          ProcessRTSPResponse(localMatcher3.group());
          paramArrayList.subList(0, localMatcher3.start() + localMatcher3.group().length()).clear();
          if (paramArrayList.size() <= 0)
            break;
          bool = true;
          break;
        }
        utility.logMessageAsync(this.activity, "Unknown RTSP commmand");
        break;
      }
      ProcessRTSPResponse(localMatcher2.group());
      paramArrayList.subList(0, localMatcher2.start() + localMatcher2.group().length()).clear();
      int i3 = paramArrayList.size();
      if (i3 <= 0)
        break;
      bool = true;
      break;
    }
    while (bool);
    bool = true;
    label859: return bool;
  }

  private void ProcessH264Payload(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws Exception
  {
    byte[] arrayOfByte1;
    byte[] arrayOfByte2;
    int i9;
    int i11;
    label79: int i;
    int j;
    int k;
    int m;
    int n;
    label197: int i1;
    label232: int i4;
    if (!this.bH264Initialized)
    {
      String[] arrayOfString = this.sprop_parameter_sets.trim().split(",");
      arrayOfByte1 = org.kobjects.base64.Base64.decode(arrayOfString[0]);
      arrayOfByte2 = org.kobjects.base64.Base64.decode(arrayOfString[1]);
      addH264StartPrefix(this.qH264Stream);
      int i8 = arrayOfByte1.length;
      i9 = 0;
      if (i9 >= i8)
      {
        addH264StartPrefix(this.qH264Stream);
        int i10 = arrayOfByte2.length;
        i11 = 0;
        if (i11 < i10)
          break label316;
        2 local2 = new Runnable()
        {
          public void run()
          {
            RTSPoverHTTPDecoder.InputStreamH264 localInputStreamH264 = new RTSPoverHTTPDecoder.InputStreamH264(RTSPoverHTTPDecoder.this, RTSPoverHTTPDecoder.this.qH264Stream);
            try
            {
              H264Decoder localH264Decoder = new H264Decoder(new AnnexBDemuxer(localInputStreamH264));
              while (true)
              {
                if (RTSPoverHTTPDecoder.this.bDisposed.bValue)
                  return;
                if (localH264Decoder.nextPicture() == null)
                  break;
                Log.d("ProcessH264Payload", "A picture has been decoded");
              }
            }
            catch (IOException localIOException)
            {
              while (true)
              {
                Log.d("ProcessH264Payload", localIOException.getMessage());
                break;
                Log.d("ProcessH264Payload", "A null picture has been decoded");
                Thread.sleep(50L);
              }
            }
            catch (InterruptedException localInterruptedException)
            {
              Log.d("ProcessH264Payload Thread.sleep()", localInterruptedException.getMessage());
            }
          }
        };
        new Thread(local2).start();
        this.bH264Initialized = true;
      }
    }
    else
    {
      i = paramInt2 + 1;
      j = paramArrayOfByte[paramInt2];
      k = utility.GetBigEndianBits(j, 3, 5);
      m = 0;
      n = 0;
      switch (k)
      {
      default:
        if (k < 24)
          if ((k == 1) || (k == 5) || (k == 13))
            if (k == 5)
            {
              this._bIDR = true;
              if (n == 0)
              {
                addH264StartPrefix(this.qH264Stream);
                this.qH264Stream.offer(Byte.valueOf(j));
              }
              m = 1;
              i1 = i;
              if (n != 0)
                break;
              i4 = 0;
            }
      case 28:
      }
    }
    label316: label370: int i6;
    for (int i5 = i1; ; i5 = i6)
    {
      if (i4 >= paramInt3 - (i5 - paramInt2))
      {
        this._iNALCount = (1 + this._iNALCount);
        utility.logd("NAL Count", String.valueOf(this._iNALCount));
        if (m != 0);
        return;
        byte b2 = arrayOfByte1[i9];
        this.qH264Stream.offer(Byte.valueOf(b2));
        i9++;
        break;
        byte b3 = arrayOfByte2[i11];
        this.qH264Stream.offer(Byte.valueOf(b3));
        i11++;
        break label79;
        i1 = i + 1;
        byte b1 = paramArrayOfByte[i];
        int i2;
        int i3;
        if (utility.GetBigEndianBits(b1, 0, 1) == 1)
        {
          i2 = 1;
          if (utility.GetBigEndianBits(b1, 1, 1) != 1)
            break label472;
          i3 = 1;
          label384: if (utility.GetBigEndianBits(b1, 3, 5) != 5)
            break label478;
          this._bIDR = true;
          label400: if (i2 == 0)
            break label486;
          addH264StartPrefix(this.qH264Stream);
          int i7 = j & 0xE0 | b1 & 0x1F;
          this.qH264Stream.offer(Byte.valueOf(i7));
        }
        while (true)
        {
          if (utility.GetBigEndianBits(b1, 2, 1) != 1)
            break label495;
          utility.ShowMessage(this.activity, "The reserved R bit of FU-A packet's FU header is not zero");
          break;
          i2 = 0;
          break label370;
          label472: i3 = 0;
          break label384;
          label478: this._bIDR = false;
          break label400;
          label486: if (i3 == 0)
            continue;
          m = 1;
        }
        label495: break label232;
        this._bIDR = false;
        break label197;
        if (k == 6)
        {
          n = 1;
          break label197;
        }
        if ((k == 7) || (k == 8))
          break label197;
        throw new Exception("Unhandled H.264 payload type: " + String.valueOf(k));
        if (k >= 30)
          throw new Exception("Unhandled undefined H.264 payload type: " + String.valueOf(k));
        throw new Exception("This type in H.264 payload in a TCP packet (see Table 1 of RTP Payload Format for H.264 Video)  is not supported: " + String.valueOf(k));
      }
      Queue localQueue = this.qH264Stream;
      i6 = i5 + 1;
      localQueue.offer(Byte.valueOf(paramArrayOfByte[i5]));
      i4++;
    }
  }

  private void ProcessJPEGPayload(byte[] paramArrayOfByte, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    int i = paramInt2 + 1;
    paramArrayOfByte[paramInt2];
    int j = i + 1;
    int k = utility.ib(paramArrayOfByte[i]) << 16;
    int m = j + 1;
    int n = k + (utility.ib(paramArrayOfByte[j]) << 8);
    int i1 = m + 1;
    int i2 = n + utility.ib(paramArrayOfByte[m]);
    int i3 = i1 + 1;
    int i4 = paramArrayOfByte[i1];
    int i5 = i3 + 1;
    int i6 = utility.ib(paramArrayOfByte[i3]);
    int i7 = i5 + 1;
    int i8 = paramArrayOfByte[i5];
    int i9 = i7 + 1;
    int i10 = paramArrayOfByte[i7];
    if ((i4 != 0) && (i4 != 1));
    while (true)
    {
      return;
      label195: int i21;
      int i24;
      int i15;
      label752: int i13;
      label1106: byte[] arrayOfByte;
      int i11;
      BitmapFactory.Options localOptions;
      if (i2 == 0)
      {
        if (i6 >= 128)
        {
          Iterator localIterator2 = this.listQuantizationTables.iterator();
          int i19;
          while (true)
          {
            if (!localIterator2.hasNext())
            {
              i19 = i9 + 1;
              if (paramArrayOfByte[i9] == 0)
                break label195;
              break;
            }
            ((ArrayList)localIterator2.next()).clear();
          }
          int i20 = i19 + 1;
          i21 = utility.ib(paramArrayOfByte[i19]);
          int i22 = i20 + 1;
          int i23 = utility.ib(paramArrayOfByte[i20]) << 8;
          i9 = i22 + 1;
          if (i23 + utility.ib(paramArrayOfByte[i22]) != 128)
            continue;
          i24 = 0;
          int i25 = this.listQuantizationTables.size();
          if (i24 < i25);
        }
        else
        {
          this.listSample.clear();
          this.listSample.ensureCapacity(this.iJPEGSizeMax);
          this.listSample.add(Byte.valueOf(-1));
          this.listSample.add(Byte.valueOf(-40));
          this.listSample.add(Byte.valueOf(-1));
          this.listSample.add(Byte.valueOf(-32));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(16));
          this.listSample.add(Byte.valueOf(74));
          this.listSample.add(Byte.valueOf(70));
          this.listSample.add(Byte.valueOf(73));
          this.listSample.add(Byte.valueOf(70));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(1));
          this.listSample.add(Byte.valueOf(1));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(1));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(1));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(0));
          i15 = 0;
          int i16 = this.listQuantizationTables.size();
          if (i15 < i16)
            break label1451;
          int i17 = i8 * 8;
          int i18 = i10 * 8;
          this.listSample.add(Byte.valueOf(-1));
          this.listSample.add(Byte.valueOf(-64));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(17));
          this.listSample.add(Byte.valueOf(8));
          this.listSample.add(Byte.valueOf((byte)(i18 >> 8)));
          this.listSample.add(Byte.valueOf((byte)(i18 & 0xFF)));
          this.listSample.add(Byte.valueOf((byte)(i17 >> 8)));
          this.listSample.add(Byte.valueOf((byte)(i17 & 0xFF)));
          this.listSample.add(Byte.valueOf(3));
          this.listSample.add(Byte.valueOf(1));
        }
      }
      else
        switch (i4)
        {
        default:
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(2));
          this.listSample.add(Byte.valueOf(17));
          this.listSample.add(Byte.valueOf(1));
          this.listSample.add(Byte.valueOf(3));
          this.listSample.add(Byte.valueOf(17));
          this.listSample.add(Byte.valueOf(1));
          MakeHuffmanHeader(this.listSample, this.lum_dc_codelens, this.lum_dc_symbols, 0, 0);
          MakeHuffmanHeader(this.listSample, this.lum_ac_codelens, this.lum_ac_symbols, 0, 1);
          MakeHuffmanHeader(this.listSample, this.chm_dc_codelens, this.chm_dc_symbols, 1, 0);
          MakeHuffmanHeader(this.listSample, this.chm_ac_codelens, this.chm_ac_symbols, 1, 1);
          this.listSample.add(Byte.valueOf(-1));
          this.listSample.add(Byte.valueOf(-38));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(12));
          this.listSample.add(Byte.valueOf(3));
          this.listSample.add(Byte.valueOf(1));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(2));
          this.listSample.add(Byte.valueOf(17));
          this.listSample.add(Byte.valueOf(3));
          this.listSample.add(Byte.valueOf(17));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf(63));
          this.listSample.add(Byte.valueOf(0));
          long l1 = System.currentTimeMillis();
          if (this.listSample.size() < 1000000)
          {
            i13 = i9;
            int i14 = paramArrayOfByte.length;
            if (i13 < i14);
          }
          else
          {
            utility.logd("Onvifer", "Add data to listSample time (ms): " + (System.currentTimeMillis() - l1));
            if (!paramBoolean)
              continue;
            arrayOfByte = new byte[this.listSample.size()];
            i11 = 0;
            int i12 = this.listSample.size();
            if (i11 < i12)
              break label1649;
            this.iJPEGSizeMax = Math.max(this.iJPEGSizeMax, this.listSample.size());
            localOptions = new BitmapFactory.Options();
          }
        case 0:
        case 1:
        }
      try
      {
        this.iProcessedFrames = (1 + this.iProcessedFrames);
        long l2 = System.currentTimeMillis();
        Bitmap localBitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
        utility.logd("BitmapFactory.decodeByteArray (ms)", " " + (System.currentTimeMillis() - l2));
        Bitmap[] arrayOfBitmap3 = new Bitmap[1];
        arrayOfBitmap3[0] = localBitmap;
        publishProgress(arrayOfBitmap3);
        this.ulFrameTime = (paramInt1 * 1000 / 90000);
        this.uireceivedPacketCount = (1 + this.uireceivedPacketCount);
        if (this.uireceivedPacketCount % 5 != 0)
          continue;
        if (this.ulFrameTimeRe != 0L)
        {
          f = (float)(5000L / (this.ulFrameTime - this.ulFrameTimeRe));
          if (this.fFrameRate == 0.0F)
            this.fFrameRate = f;
        }
        else
        {
          this.ulFrameTimeRe = this.ulFrameTime;
          continue;
          int i26 = 64;
          if ((0x1 & i21 >> i24) == 1)
            i26 = 128;
          int i27 = 0;
          int i29;
          for (int i28 = i9; ; i28 = i29)
          {
            if (i27 >= i26)
            {
              i24++;
              i9 = i28;
              break;
            }
            ArrayList localArrayList = (ArrayList)this.listQuantizationTables.get(i24);
            i29 = i28 + 1;
            localArrayList.add(Byte.valueOf(paramArrayOfByte[i28]));
            i27++;
          }
          label1451: this.listSample.add(Byte.valueOf(-1));
          this.listSample.add(Byte.valueOf(-37));
          this.listSample.add(Byte.valueOf(0));
          this.listSample.add(Byte.valueOf((byte)(3 + ((ArrayList)this.listQuantizationTables.get(i15)).size())));
          this.listSample.add(Byte.valueOf((byte)i15));
          Iterator localIterator1 = ((ArrayList)this.listQuantizationTables.get(i15)).iterator();
          while (true)
          {
            if (!localIterator1.hasNext())
            {
              i15++;
              break;
            }
            byte b = ((Byte)localIterator1.next()).byteValue();
            this.listSample.add(Byte.valueOf(b));
          }
          this.listSample.add(Byte.valueOf(33));
          break label752;
          this.listSample.add(Byte.valueOf(34));
          break label752;
          this.listSample.add(Byte.valueOf(paramArrayOfByte[i13]));
          i13++;
          break label1106;
          label1649: arrayOfByte[i11] = ((Byte)this.listSample.get(i11)).byteValue();
          i11++;
        }
      }
      catch (Exception localException)
      {
        while (this._bJPEGErrorReported);
        this._bJPEGErrorReported = true;
        if (localException.getMessage() == null);
        for (String str2 = "Unknown"; ; str2 = localException.getMessage())
        {
          utility.logMessageAsync(this.activity, "JPEG Decoding error: " + str2);
          utility.logd("BitmapFactory.decodeByteArray Exception: ", str2);
          this.sProgress = "The streamed JPEG image cannot be decoded.";
          Bitmap[] arrayOfBitmap2 = new Bitmap[1];
          arrayOfBitmap2[0] = null;
          publishProgress(arrayOfBitmap2);
          break;
        }
      }
      catch (Error localError)
      {
        while (true)
        {
          float f;
          if (this._bJPEGErrorReported)
            continue;
          this.pd.dismiss();
          this._bJPEGErrorReported = true;
          if (localError.getMessage() == null);
          for (String str1 = "Unknown"; ; str1 = localError.getMessage())
          {
            utility.logMessageAsync(this.activity, "JPEG Decoding error: " + str1);
            utility.logd("BitmapFactory.decodeByteArray Exception: ", str1);
            this.sProgress = "The streamed JPEG image cannot be decoded.";
            Bitmap[] arrayOfBitmap1 = new Bitmap[1];
            arrayOfBitmap1[0] = null;
            publishProgress(arrayOfBitmap1);
            break;
          }
          this.fFrameRate = (0.95F * this.fFrameRate + 0.05F * f);
        }
      }
    }
  }

  private void ProcessRTPPacket(byte[] paramArrayOfByte)
  {
    utility.GetBigEndianBits(paramArrayOfByte[0], 0, 2);
    int i;
    int j;
    label36: boolean bool;
    label64: int m;
    int n;
    ArrayList localArrayList;
    int i1;
    int i2;
    label192: int i14;
    int i15;
    if (utility.GetBigEndianBits(paramArrayOfByte[0], 2, 1) > 0)
    {
      i = 1;
      if (utility.GetBigEndianBits(paramArrayOfByte[0], 3, 1) <= 0)
        break label349;
      j = 1;
      int k = 0xFF & utility.GetBigEndianBits(paramArrayOfByte[0], 4, 4);
      if (utility.GetBigEndianBits(paramArrayOfByte[1], 0, 1) <= 0)
        break label355;
      bool = true;
      m = utility.GetBigEndianBits(paramArrayOfByte[1], 1, 7);
      this.iSequencePre = ((utility.ib(paramArrayOfByte[2]) << 8) + utility.ib(paramArrayOfByte[3]));
      n = (utility.ib(paramArrayOfByte[4]) << 24) + (utility.ib(paramArrayOfByte[5]) << 16) + (utility.ib(paramArrayOfByte[6]) << 8) + utility.ib(paramArrayOfByte[7]);
      ((utility.ib(paramArrayOfByte[8]) << 24) + (utility.ib(paramArrayOfByte[9]) << 16) + (utility.ib(paramArrayOfByte[10]) << 8) + utility.ib(paramArrayOfByte[11]));
      localArrayList = new ArrayList();
      i1 = 0;
      i2 = 12;
      if (i1 < k)
        break label361;
      if (j == 0)
        break label582;
      int i11 = i2 + 1;
      int i12 = utility.ib(paramArrayOfByte[i2]) << 8;
      int i13 = i11 + 1;
      (i12 + utility.ib(paramArrayOfByte[i11]));
      i14 = i13 + 1;
      i15 = utility.ib(paramArrayOfByte[i13]) << 8;
    }
    label582: for (int i9 = i14 + 1 + 4 * (i15 + utility.ib(paramArrayOfByte[i14])); ; i9 = i2)
    {
      int i10 = paramArrayOfByte.length - i9;
      if (i != 0)
        i10 -= paramArrayOfByte[(-1 + paramArrayOfByte.length)];
      if (m == this.iVideoPayloadtype)
        switch (this.iVideoPayloadtype)
        {
        default:
        case 26:
        }
      while (true)
      {
        try
        {
          ProcessH264Payload(paramArrayOfByte, n, i9, i10);
          return;
          i = 0;
          break;
          label349: j = 0;
          break label36;
          label355: bool = false;
          break label64;
          label361: int i3 = i2 + 1;
          int i4 = utility.ib(paramArrayOfByte[i2]) << 24;
          int i5 = i3 + 1;
          int i6 = i4 + (utility.ib(paramArrayOfByte[i3]) << 16);
          int i7 = i5 + 1;
          int i8 = i6 + (utility.ib(paramArrayOfByte[i5]) << 8);
          i2 = i7 + 1;
          localArrayList.add(Integer.valueOf(i8 + utility.ib(paramArrayOfByte[i7])));
          i1 += 1;
          break label192;
          long l = System.currentTimeMillis();
          ProcessJPEGPayload(paramArrayOfByte, bool, n, i9);
          this.lProfiledTime += System.currentTimeMillis() - l;
          utility.logd("Onvifer", "ProcessJPEGPayload() tottal time (ms): " + this.lProfiledTime);
          utility.logd("Onvifer", "ProcessJPEGPayload() time (ms): " + (System.currentTimeMillis() - l));
          continue;
        }
        catch (Exception localException)
        {
          utility.logd("ProcessH264Payload", localException.getMessage());
          continue;
        }
        if (m != this.iAudioPayloadtype)
          continue;
        ProcessAudioPayload(paramArrayOfByte, bool, n, i9);
      }
    }
  }

  private void ProcessRTSPResponse(String paramString)
  {
    Matcher localMatcher1 = this.patternOptions.matcher(paramString);
    if (localMatcher1.find())
    {
      localMatcher1.find(0);
      this.sProgress = "Requested options data have been received. Requesting media information...";
      Bitmap[] arrayOfBitmap5 = new Bitmap[1];
      arrayOfBitmap5[0] = null;
      publishProgress(arrayOfBitmap5);
      if (!localMatcher1.group().contains("DESCRIBE"))
        utility.logMessageAsync(this.activity, "DESCRIBE is not a part of OPTIONS");
    }
    while (true)
    {
      return;
      Matcher localMatcher2 = this.patternAuthentication.matcher(paramString);
      if (localMatcher2.find())
      {
        this.sProgress = "Authentication request has been received.  Requesting media information again...";
        Bitmap[] arrayOfBitmap4 = new Bitmap[1];
        arrayOfBitmap4[0] = null;
        publishProgress(arrayOfBitmap4);
        localMatcher2.find(0);
        this._sRealm = utility.getParamValue(localMatcher2.group(0), "realm");
        this._sNonce = utility.getParamValue(localMatcher2.group(0), "nonce");
        StringBuilder localStringBuilder4 = new StringBuilder(String.valueOf("DESCRIBE " + this.sURL + " RTSP/1.0")).append("\r\nCSeq: ");
        int i1 = this.icSeq;
        this.icSeq = (i1 + 1);
        SendRTSPCommand(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(localStringBuilder4.append(String.valueOf(i1)).toString())).append("\r\nAccept: application/sdp").toString())).append("\r\nBandwidth: 512000").toString() + "\r\nAccept-Language: en-US", "DESCRIBE");
        continue;
      }
      if (this.patternDescribe.matcher(paramString).find())
      {
        this.sProgress = "Requested media description has been received. Setting up media...";
        Bitmap[] arrayOfBitmap3 = new Bitmap[1];
        arrayOfBitmap3[0] = null;
        publishProgress(arrayOfBitmap3);
        Matcher localMatcher6 = Pattern.compile("RTSP/1.0 200 OK.*?^Content-Base:(.+?)$.+?\\r\\n$.+?\\r\\n$", 40).matcher(paramString);
        label361: Matcher localMatcher11;
        if (localMatcher6.find())
        {
          localMatcher6.find(0);
          this.sPlayURL = localMatcher6.group(1);
          Matcher localMatcher7 = Pattern.compile("m=video\\s*?(\\d+?)\\s+?(\\S+?)\\s+?(.+?)$", 32).matcher(paramString);
          if (!localMatcher7.find())
            break label1332;
          localMatcher7.find(0);
          String str2 = localMatcher7.group();
          localMatcher11 = Pattern.compile("m=video\\s*?(\\d+?)\\s+?(\\S+?)\\s+?(.+?)$", 40).matcher(paramString);
          if (!localMatcher11.find())
            break label1319;
          localMatcher11.find(0);
          if (!localMatcher11.group(2).endsWith("RTP/AVP"))
            break label1287;
          String str3 = localMatcher11.group(3).trim();
          this.iVideoPayloadtype = Integer.parseInt(str3);
          switch (this.iVideoPayloadtype)
          {
          default:
            Matcher localMatcher13 = Pattern.compile("^a=rtpmap:\\s*?" + str3 + "\\s+?H264/90000.+?a=fmtp:" + str3 + "(.+?\\r?\\n?$)", 40).matcher(str2);
            if (!localMatcher13.find())
              break label1274;
            localMatcher13.find(0);
            String str4 = localMatcher13.group(1);
            Matcher localMatcher14 = Pattern.compile("packetization-mode=(\\d+)", 40).matcher(str4);
            if (localMatcher14.find())
            {
              localMatcher14.find(0);
              this.packetization_mode = localMatcher14.group(1);
            }
            Matcher localMatcher15 = Pattern.compile("profile-level-id=(.+?)(;|\\r?\\n?$)", 40).matcher(str4);
            if (localMatcher15.find())
            {
              localMatcher15.find(0);
              this.profile_level_id = localMatcher15.group(1);
            }
            Matcher localMatcher16 = Pattern.compile("sprop-parameter-sets=(.+?)(;|\r?\n?$)", 40).matcher(str4);
            if (!localMatcher16.find())
              break;
            localMatcher16.find(0);
            this.sprop_parameter_sets = localMatcher16.group(1);
          case 26:
          }
          label674: Matcher localMatcher12 = Pattern.compile("^m=video(.+?\\r?\\n?$).+?^a=control:(.+?)\\r?\\n?$", 40).matcher(str2);
          if (localMatcher12.find())
          {
            localMatcher12.find(0);
            this.sVideoControlURL = localMatcher12.group(2);
            if (!this.sVideoControlURL.contains("rtsp://"))
              this.sVideoControlURL = ("rtsp://" + this.uri.getHost() + "/" + this.sVideoControlURL);
            this.iVideoSetupSeq = this.icSeq;
            StringBuilder localStringBuilder3 = new StringBuilder(String.valueOf(new StringBuilder("SETUP ").append(this.sVideoControlURL).toString() + " RTSP/1.0\r\nCSeq: "));
            int n = this.icSeq;
            this.icSeq = (n + 1);
            SendRTSPCommand(localStringBuilder3.append(String.valueOf(n)).append("\r\nTransport: RTP/AVP/TCP;unicast").toString() + "\r\nBlocksize:3000", "SETUP");
          }
        }
        String str1;
        while (true)
        {
          Matcher localMatcher8 = Pattern.compile("m=audio.+?RTP/AVP.+?($|(m=))", 32).matcher(paramString);
          if (!localMatcher8.find())
            break;
          localMatcher8.find(0);
          str1 = localMatcher8.group();
          Matcher localMatcher9 = Pattern.compile("m=audio\\s*?(\\d+?)\\s+?(\\S+?)\\s+?(.+?)$", 40).matcher(str1);
          if (!localMatcher9.find())
            break label1387;
          localMatcher9.find(0);
          if (!localMatcher9.group(2).trim().equals("RTP/AVP"))
            break label1359;
          this.iAudioPayloadtype = Integer.parseInt(localMatcher9.group(3));
          switch (this.iAudioPayloadtype)
          {
          default:
            break;
          case 0:
            this.iClockRate = 8000;
            this.sEncodingName = "G.711";
            this.track = new AudioTrack(3, this.iClockRate, 4, 2, 3 * (2 * this.iClockRate), 1);
            this.track.play();
            Matcher localMatcher10 = Pattern.compile("^a=control:(.+?)\\r?\\n?$", 40).matcher(str1);
            if (!localMatcher10.find())
              break;
            localMatcher10.find(0);
            this.sAudioControlURL = localMatcher10.group(1);
            if (!this.sAudioControlURL.contains("rtsp://"))
              this.sAudioControlURL = (this.sPlayURL + this.sAudioControlURL);
            this.iAudioSetupSeq = this.icSeq;
            StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf("SETUP " + this.sAudioControlURL + " RTSP/1.0")).append("\r\nCSeq: ");
            int m = this.icSeq;
            this.icSeq = (m + 1);
            SendRTSPCommand(new StringBuilder(String.valueOf(localStringBuilder2.append(String.valueOf(m)).toString())).append("\r\nTransport: RTP/AVP/TCP;unicast").toString() + "\r\nBlocksize:3000", "SETUP");
            break;
            this.sPlayURL = this.sURL;
            break label361;
            label1274: utility.logMessageAsync(this.activity, "This device has a compatibility issue.  H.264 format information is not in the response to command DESCRIBE. ");
            break label674;
            label1287: utility.logMessageAsync(this.activity, "This device has a compatibility issue.  SDP m=video Transport is not RTP/AVP.  It is " + localMatcher11.group(2));
            continue;
            label1319: utility.logMessageAsync(this.activity, "This device has a compatibility issue.  SDP m=video does not have the correct set of parameters.");
            continue;
            label1332: utility.logMessageAsync(this.activity, "Unable to retrieve the video stream information from the response: " + paramString);
          }
        }
        label1359: utility.logMessageAsync(this.activity, "This device has a compatibility issue.  SDP m=audio Transport is not RTP/AVP : " + str1);
        continue;
        label1387: utility.logMessageAsync(this.activity, "This device has a compatibility issue.  SDP m=audio does not have the correct set of parameters." + str1);
        continue;
      }
      if ((paramString.contains("Transport:")) && (paramString.contains("Session:")))
      {
        Matcher localMatcher3 = Pattern.compile("interleaved=(.+?)(;|(\\r?\\n?$))", 40).matcher(paramString);
        if (!localMatcher3.find())
          continue;
        localMatcher3.find(0);
        String[] arrayOfString = localMatcher3.group(1).split("-");
        Matcher localMatcher4 = Pattern.compile("^CSeq:.*?(\\d+?)\\r?\\n?$.+?", 40).matcher(paramString);
        if (!localMatcher4.find())
          continue;
        localMatcher4.find(0);
        int i = Integer.parseInt(localMatcher4.group(1));
        int j = 0;
        if (arrayOfString.length == 2)
          if (i == this.iVideoSetupSeq)
          {
            this.byteVideoRTPChannel = Byte.parseByte(arrayOfString[0]);
            this.byteVideoRTPCPChannel = Byte.parseByte(arrayOfString[1]);
            j = 1;
          }
        while (true)
        {
          Pattern localPattern = Pattern.compile("^Session:(.+?)(;|(\\r?\\n?$))", 40);
          if (j == 0)
            break;
          Matcher localMatcher5 = localPattern.matcher(paramString);
          if (!localMatcher5.find())
            break;
          localMatcher5.find(0);
          this.sSession = localMatcher5.group(1);
          StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf("PLAY " + this.sPlayURL + " RTSP/1.0")).append("\r\nCSeq: ");
          int k = this.icSeq;
          this.icSeq = (k + 1);
          SendRTSPCommand(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(localStringBuilder1.append(String.valueOf(k)).toString())).append("\r\nRange: npt=0.000000-").toString())).append("\r\nx-prebuffer: maxtime=2.000000").toString() + "\r\nSession: " + this.sSession, "PLAY");
          this.sProgress = "Requesting media play...";
          Bitmap[] arrayOfBitmap2 = new Bitmap[1];
          arrayOfBitmap2[0] = null;
          publishProgress(arrayOfBitmap2);
          break;
          if (i == this.iAudioSetupSeq)
          {
            this.byteAudioRTPChannel = Byte.parseByte(arrayOfString[0]);
            this.byteAudioRTPCPChannel = Byte.parseByte(arrayOfString[1]);
            j = 1;
            continue;
          }
          j = 0;
          utility.logMessageAsync(this.activity, "This device has a compatibility issue.  CSeq of SETUP response does not have a corresponding value.");
          continue;
          utility.logMessageAsync(this.activity, "ProcessData() in response to SETUP, interleaved = : " + localMatcher4.group(2));
        }
      }
      if (!this.patternPlay.matcher(paramString).find())
        continue;
      this.sProgress = "Media streaming has started";
      Bitmap[] arrayOfBitmap1 = new Bitmap[1];
      arrayOfBitmap1[0] = null;
      publishProgress(arrayOfBitmap1);
    }
  }

  private void Process_RTP_RTCP_acket(PackeType paramPackeType, byte[] paramArrayOfByte)
  {
    try
    {
      switch ($SWITCH_TABLE$net$biyee$android$ONVIF$RTSPoverHTTPDecoder$PackeType()[paramPackeType.ordinal()])
      {
      default:
        throw new Exception("Unknown packet type");
      case 4:
      case 1:
      case 3:
      case 2:
      }
    }
    catch (Exception localException)
    {
      utility.logMessageAsync(this.activity, "ProcessData()  Error:" + localException.getMessage());
      if (localException.getMessage().contains("IndexOutOfRangeException"))
        localException.getMessage();
    }
    while (true)
    {
      return;
      ProcessRTPPacket(paramArrayOfByte);
    }
    utility.GetBigEndianBits(paramArrayOfByte[0], 0, 2);
    if (utility.GetBigEndianBits(paramArrayOfByte[0], 2, 1) > 0);
    while (true)
    {
      int i = utility.GetBigEndianBits(paramArrayOfByte[0], 3, 5);
      switch (utility.ib(paramArrayOfByte[1]))
      {
      case 6:
      case 44:
      case 191:
      case 202:
      case 203:
      case 204:
      case 239:
      default:
        throw new Exception("Unknown RTPCP packet type");
      case 200:
        int i48 = 2 + 1;
        int i49 = utility.ib(paramArrayOfByte[2]) << 8;
        int i50 = i48 + 1;
        (i49 + utility.ib(paramArrayOfByte[i48]));
        int i51 = i50 + 1;
        int i52 = utility.ib(paramArrayOfByte[i50]) << 24;
        int i53 = i51 + 1;
        int i54 = i52 + (utility.ib(paramArrayOfByte[i51]) << 16);
        int i55 = i53 + 1;
        int i56 = i54 + (utility.ib(paramArrayOfByte[i53]) << 8);
        int i57 = i55 + 1;
        (i56 + utility.ib(paramArrayOfByte[i55]));
        int i58 = i57 + 1;
        int i59 = utility.ib(paramArrayOfByte[i57]) << 24;
        int i60 = i58 + 1;
        int i61 = i59 + (utility.ib(paramArrayOfByte[i58]) << 16);
        int i62 = i60 + 1;
        int i63 = i61 + (utility.ib(paramArrayOfByte[i60]) << 8);
        int i64 = i62 + 1;
        double d = i63 + utility.ib(paramArrayOfByte[i62]);
        int i65 = i64 + 1;
        int i66 = utility.ib(paramArrayOfByte[i64]) << 24;
        int i67 = i65 + 1;
        int i68 = i66 + (utility.ib(paramArrayOfByte[i65]) << 16);
        int i69 = i67 + 1;
        int i70 = i68 + (utility.ib(paramArrayOfByte[i67]) << 8);
        int i71 = i69 + 1;
        (d + 1.0D * (i70 + utility.ib(paramArrayOfByte[i69])) / 2147483647.0D);
        int i72 = i71 + 1;
        int i73 = utility.ib(paramArrayOfByte[i71]) << 24;
        int i74 = i72 + 1;
        int i75 = i73 + (utility.ib(paramArrayOfByte[i72]) << 16);
        int i76 = i74 + 1;
        int i77 = i75 + (utility.ib(paramArrayOfByte[i74]) << 8);
        int i78 = i76 + 1;
        (i77 + utility.ib(paramArrayOfByte[i76]));
        int i79 = i78 + 1;
        int i80 = utility.ib(paramArrayOfByte[i78]) << 24;
        int i81 = i79 + 1;
        int i82 = i80 + (utility.ib(paramArrayOfByte[i79]) << 16);
        int i83 = i81 + 1;
        int i84 = i82 + (utility.ib(paramArrayOfByte[i81]) << 8);
        int i85 = i83 + 1;
        (i84 + utility.ib(paramArrayOfByte[i83]));
        int i86 = i85 + 1;
        int i87 = utility.ib(paramArrayOfByte[i85]) << 24;
        int i88 = i86 + 1;
        int i89 = i87 + (utility.ib(paramArrayOfByte[i86]) << 16);
        int i90 = i88 + 1;
        int i91 = i89 + (utility.ib(paramArrayOfByte[i88]) << 8);
        int i92 = i90 + 1;
        (i91 + utility.ib(paramArrayOfByte[i90]));
        int i93 = 0;
        int i94 = i92;
        while (i93 < i)
        {
          int i95 = i94 + 1;
          int i96 = utility.ib(paramArrayOfByte[i94]) << 24;
          int i97 = i95 + 1;
          int i98 = i96 + (utility.ib(paramArrayOfByte[i95]) << 16);
          int i99 = i97 + 1;
          int i100 = i98 + (utility.ib(paramArrayOfByte[i97]) << 8);
          int i101 = i99 + 1;
          (i100 + utility.ib(paramArrayOfByte[i99]));
          int i102 = i101 + 1;
          paramArrayOfByte[i101];
          int i103 = i102 + 1;
          int i104 = utility.ib(paramArrayOfByte[i102]) << 16;
          int i105 = i103 + 1;
          int i106 = i104 + (utility.ib(paramArrayOfByte[i103]) << 8);
          int i107 = i105 + 1;
          (i106 + utility.ib(paramArrayOfByte[i105]));
          int i108 = i107 + 1;
          int i109 = utility.ib(paramArrayOfByte[i107]) << 8;
          int i110 = i108 + 1;
          (i109 + utility.ib(paramArrayOfByte[i108]));
          int i111 = i110 + 1;
          int i112 = utility.ib(paramArrayOfByte[i110]) << 8;
          int i113 = i111 + 1;
          (i112 + utility.ib(paramArrayOfByte[i111]));
          int i114 = i113 + 1;
          int i115 = utility.ib(paramArrayOfByte[i113]) << 24;
          int i116 = i114 + 1;
          int i117 = i115 + (utility.ib(paramArrayOfByte[i114]) << 16);
          int i118 = i116 + 1;
          int i119 = i117 + (utility.ib(paramArrayOfByte[i116]) << 8);
          int i120 = i118 + 1;
          (i119 + utility.ib(paramArrayOfByte[i118]));
          int i121 = i120 + 1;
          int i122 = utility.ib(paramArrayOfByte[i120]) << 8;
          int i123 = i121 + 1;
          float f2 = i122 + utility.ib(paramArrayOfByte[i121]);
          int i124 = i123 + 1;
          int i125 = utility.ib(paramArrayOfByte[i123]) << 8;
          int i126 = i124 + 1;
          (f2 + 1.0F * (i125 + utility.ib(paramArrayOfByte[i124])) / -32768.0F);
          int i127 = i126 + 1;
          int i128 = utility.ib(paramArrayOfByte[i126]) << 24;
          int i129 = i127 + 1;
          int i130 = i128 + (utility.ib(paramArrayOfByte[i127]) << 16);
          int i131 = i129 + 1;
          int i132 = i130 + (utility.ib(paramArrayOfByte[i129]) << 8);
          i94 = i131 + 1;
          (i132 + utility.ib(paramArrayOfByte[i131]));
          i93 += 1;
        }
      case 201:
      }
      int j = 2 + 1;
      int k = utility.ib(paramArrayOfByte[2]) << 8;
      int m = j + 1;
      (k + utility.ib(paramArrayOfByte[j]));
      int n = m + 1;
      int i1 = utility.ib(paramArrayOfByte[m]) << 24;
      int i2 = n + 1;
      int i3 = i1 + (utility.ib(paramArrayOfByte[n]) << 16);
      int i4 = i2 + 1;
      int i5 = i3 + (utility.ib(paramArrayOfByte[i2]) << 8);
      int i6 = i4 + 1;
      (i5 + utility.ib(paramArrayOfByte[i4]));
      int i7 = 0;
      int i8 = i6;
      while (i7 < i)
      {
        int i9 = i8 + 1;
        int i10 = utility.ib(paramArrayOfByte[i8]) << 24;
        int i11 = i9 + 1;
        int i12 = i10 + (utility.ib(paramArrayOfByte[i9]) << 16);
        int i13 = i11 + 1;
        int i14 = i12 + (utility.ib(paramArrayOfByte[i11]) << 8);
        int i15 = i13 + 1;
        (i14 + utility.ib(paramArrayOfByte[i13]));
        int i16 = i15 + 1;
        paramArrayOfByte[i15];
        int i17 = i16 + 1;
        int i18 = utility.ib(paramArrayOfByte[i16]) << 16;
        int i19 = i17 + 1;
        int i20 = i18 + (utility.ib(paramArrayOfByte[i17]) << 8);
        int i21 = i19 + 1;
        (i20 + utility.ib(paramArrayOfByte[i19]));
        int i22 = i21 + 1;
        int i23 = utility.ib(paramArrayOfByte[i21]) << 8;
        int i24 = i22 + 1;
        (i23 + utility.ib(paramArrayOfByte[i22]));
        int i25 = i24 + 1;
        int i26 = utility.ib(paramArrayOfByte[i24]) << 8;
        int i27 = i25 + 1;
        (i26 + utility.ib(paramArrayOfByte[i25]));
        int i28 = i27 + 1;
        int i29 = utility.ib(paramArrayOfByte[i27]) << 24;
        int i30 = i28 + 1;
        int i31 = i29 + (utility.ib(paramArrayOfByte[i28]) << 16);
        int i32 = i30 + 1;
        int i33 = i31 + (utility.ib(paramArrayOfByte[i30]) << 8);
        int i34 = i32 + 1;
        (i33 + utility.ib(paramArrayOfByte[i32]));
        int i35 = i34 + 1;
        int i36 = utility.ib(paramArrayOfByte[i34]) << 8;
        int i37 = i35 + 1;
        float f1 = i36 + utility.ib(paramArrayOfByte[i35]);
        int i38 = i37 + 1;
        int i39 = utility.ib(paramArrayOfByte[i37]) << 8;
        int i40 = i38 + 1;
        (f1 + 1.0F * (i39 + utility.ib(paramArrayOfByte[i38])) / 32767.0F);
        int i41 = i40 + 1;
        int i42 = utility.ib(paramArrayOfByte[i40]) << 24;
        int i43 = i41 + 1;
        int i44 = i42 + (utility.ib(paramArrayOfByte[i41]) << 16);
        int i45 = i43 + 1;
        int i46 = i44 + (utility.ib(paramArrayOfByte[i43]) << 8);
        i8 = i45 + 1;
        int i47 = utility.ib(paramArrayOfByte[i45]);
        (i46 + i47);
        i7 += 1;
      }
      break;
    }
  }

  private void SendRTSPCommand(String paramString1, String paramString2)
  {
    String str = paramString1 + "\r\nUser-Agent:BST";
    if (this._sRealm != null)
      str = str + utility.DigestAuthHeader(this.si.sUserName, this.si.sPassword, this._sRealm, this._sNonce, this._PathComplete, paramString2);
    byte[] arrayOfByte = android.util.Base64.encodeToString((str + "\r\n\r\n").getBytes(), 2).getBytes();
    try
    {
      this.osPost.write(arrayOfByte);
      this.osPost.flush();
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        utility.logMessageAsync(this.activity, "Error in SendRTSPCommand of RTSPoverHTTPDecoder(): " + localIOException.getMessage());
    }
  }

  public void addH264StartPrefix(Queue<Byte> paramQueue)
  {
    paramQueue.offer(Byte.valueOf(0));
    paramQueue.offer(Byte.valueOf(0));
    paramQueue.offer(Byte.valueOf(0));
    paramQueue.offer(Byte.valueOf(1));
  }

  protected String doInBackground(String[] paramArrayOfString)
  {
    this.listQuantizationTables.add(new ArrayList());
    this.listQuantizationTables.add(new ArrayList());
    this.sURL = this.si.sStreamURL.replace("http:", "rtsp:");
    this.uri = Uri.parse(this.sURL);
    if (this.uri.getQuery() == null)
      this._PathComplete = this.uri.getPath();
    byte[] arrayOfByte;
    ArrayList localArrayList;
    int i;
    while (true)
    {
      if (this.uri.getPort() >= 0)
      {
        this.iPort = this.uri.getPort();
        this.icSeq = 1;
      }
      try
      {
        this.sProgress = "Establishing communication channels...";
        Bitmap[] arrayOfBitmap = new Bitmap[1];
        arrayOfBitmap[0] = null;
        publishProgress(arrayOfBitmap);
        Socket localSocket = new Socket(this.uri.getHost(), this.iPort);
        localSocket.setSoTimeout(5000);
        OutputStream localOutputStream = localSocket.getOutputStream();
        String str1 = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder("GET ").append(this._PathComplete).append(" HTTP/1.1\r\n").toString())).append("x-sessioncookie: ").append(uid.toString()).append("\r\n").toString())).append("Pragma: no-cache \r\n").toString())).append("Cache-Control: no-cache \r\n").toString())).append("Content-Length: 32767 \r\n").toString() + "Accept: application/x-rtsp-tunnelled \r\n";
        if ((this.si.sUserName != null) && (this.si.sPassword != null))
        {
          String str5 = android.util.Base64.encodeToString((this.si.sUserName + ":" + this.si.sPassword).getBytes(), 2);
          str1 = str1 + "Authorization: Basic " + str5;
        }
        localOutputStream.write((str1 + "\r\n\r\n").getBytes());
        localInputStream = localSocket.getInputStream();
        try
        {
          this.osPost = new Socket(this.uri.getHost(), this.iPort).getOutputStream();
          String str2 = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder("POST ").append(this._PathComplete).append(" HTTP/1.1\r\n").toString())).append("x-sessioncookie: ").append(uid.toString()).append("\r\n").toString())).append("Content-Type: application/x-rtsp-tunnelled").toString())).append("Pragma: no-cache\r\n").toString())).append("Cache-Control: no-cache\r\n").toString())).append("Content-Length: 32767\r\n").toString() + "Expires: Thurs, 08 Sept 1994 16:00:00 GMT\r\n";
          if ((this.si.sUserName != null) && (this.si.sPassword != null))
          {
            String str4 = android.util.Base64.encodeToString((this.si.sUserName + ":" + this.si.sPassword).getBytes(), 2);
            str2 = str2 + "Authorization: Basic " + str4;
          }
          String str3 = str2 + "\r\n\r\n";
          this.osPost.write(str3.getBytes());
          StringBuilder localStringBuilder = new StringBuilder(String.valueOf("DESCRIBE " + this.sURL + " RTSP/1.0")).append("\r\nCSeq: ");
          int k = this.icSeq;
          this.icSeq = (k + 1);
          SendRTSPCommand(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(localStringBuilder.append(String.valueOf(k)).toString())).append("\r\nAccept: application/sdp").toString())).append("\r\nBandwidth: 512000").toString() + "\r\nAccept-Language: en-US", "DESCRIBE");
          arrayOfByte = new byte[65536];
          localArrayList = new ArrayList();
          if (this.bDisposed != null)
          {
            boolean bool1 = this.bDisposed.bValue;
            if (!bool1);
          }
          else
          {
            return null;
            this._PathComplete = (this.uri.getPath() + "?" + this.uri.getQuery());
            continue;
            this.iPort = 80;
          }
        }
        catch (Exception localException2)
        {
          while (true)
            utility.logMessageAsync(this.activity, "Error in creating the POST channel in RTSPoverHTTPDecoder(): " + localException2.getMessage());
        }
      }
      catch (SocketTimeoutException localSocketTimeoutException)
      {
        while (true)
        {
          InputStream localInputStream;
          this.bTimedout.bValue = true;
          continue;
          i = localInputStream.read(arrayOfByte);
          if (i != -1)
            break label1100;
          if (SystemClock.elapsedRealtime() - this.liTimeLastData <= 10000L)
            break;
          this.bTimedout.bValue = true;
        }
      }
      catch (Exception localException1)
      {
        while (true)
        {
          utility.logMessageAsync(this.activity, "Error in the thread of Get channle: " + localException1.getMessage());
          continue;
          Thread.sleep(100L);
        }
      }
    }
    label1100: int j;
    if (localArrayList.size() < 1048576)
    {
      localArrayList.ensureCapacity(i + localArrayList.size());
      j = 0;
    }
    while (true)
    {
      if (!bool2)
      {
        this.liTimeLastData = SystemClock.elapsedRealtime();
        if (i >= 1000)
          break;
        Thread.sleep(100L);
        break;
      }
      do
      {
        localArrayList.add(Byte.valueOf(arrayOfByte[j]));
        j++;
        continue;
        boolean bool3 = ProcessData(localArrayList, bool2);
        bool2 = bool3;
        break;
      }
      while (j < i);
      boolean bool2 = true;
    }
  }

  protected void onPostExecute(String paramString)
  {
    super.onPostExecute(paramString);
    if (this.track != null)
      this.track.stop();
    if (this.bTimedout.bValue)
    {
      this.pd.setMessage("There seems to be a problem  in getting the normal JPEG stream.  Backup mode is starting...");
      new AsyncTask()
      {
        protected String doInBackground(Void[] paramArrayOfVoid)
        {
          try
          {
            ONVIFDevice localONVIFDevice = utilityONVIF.getONVIFDevice(RTSPoverHTTPDecoder.this.activity, RTSPoverHTTPDecoder.this.si.sUID);
            String str = utilityONVIF.getURLSnapshot(localONVIFDevice, utilityONVIF.findProfile(RTSPoverHTTPDecoder.this.si.sProfileToken, localONVIFDevice.listProfiles));
            while (!RTSPoverHTTPDecoder.this.bDisposed.bValue)
            {
              Bitmap localBitmap = utility.loadBitmap(str, localONVIFDevice.sUserName, localONVIFDevice.sPassword);
              Bitmap[] arrayOfBitmap = new Bitmap[1];
              arrayOfBitmap[0] = localBitmap;
              publishProgress(arrayOfBitmap);
            }
          }
          catch (URISyntaxException localURISyntaxException)
          {
          }
          catch (Exception localException)
          {
            Log.d("LoadBitmap", localException.getMessage());
          }
          return null;
        }

        protected void onProgressUpdate(Bitmap[] paramArrayOfBitmap)
        {
          RTSPoverHTTPDecoder.this.iv.setImageBitmap(paramArrayOfBitmap[0]);
          if (RTSPoverHTTPDecoder.this.pd.isShowing())
            RTSPoverHTTPDecoder.this.pd.dismiss();
          super.onProgressUpdate(paramArrayOfBitmap);
        }
      }
      .execute(new Void[0]);
    }
    while (true)
    {
      return;
      this.pd.dismiss();
      if (paramString == null)
        continue;
      utility.ShowMessage(this.activity, paramString);
    }
  }

  protected void onProgressUpdate(Bitmap[] paramArrayOfBitmap)
  {
    super.onProgressUpdate(paramArrayOfBitmap);
    if ((paramArrayOfBitmap[0] != null) && (!this.bPaused.bValue))
    {
      this.iv.setImageBitmap(paramArrayOfBitmap[0]);
      if (this.pd.isShowing())
        this.pd.dismiss();
    }
    if (!this.sProgress.equals(""))
      this.pd.setMessage(this.sProgress);
  }

  class InputStreamH264 extends InputStream
  {
    Queue<Byte> qStream;

    public InputStreamH264()
    {
      Object localObject;
      this.qStream = localObject;
    }

    public int read()
      throws IOException
    {
      Byte localByte = (Byte)this.qStream.poll();
      while (true)
      {
        if ((localByte != null) || (RTSPoverHTTPDecoder.this.bDisposed.bValue));
        try
        {
          Thread.sleep(100000L);
          return 0xFF & localByte.byteValue();
          try
          {
            Thread.sleep(300L);
            localByte = (Byte)this.qStream.poll();
          }
          catch (InterruptedException localInterruptedException2)
          {
            localByte = Byte.valueOf(-1);
          }
        }
        catch (InterruptedException localInterruptedException1)
        {
          while (true)
            localInterruptedException1.printStackTrace();
        }
      }
    }
  }

  static enum PackeType
  {
    static
    {
      AudioRTP = new PackeType("AudioRTP", 2);
      AudioRTPCP = new PackeType("AudioRTPCP", 3);
      PackeType[] arrayOfPackeType = new PackeType[4];
      arrayOfPackeType[0] = VideoRTP;
      arrayOfPackeType[1] = VideoRTPCP;
      arrayOfPackeType[2] = AudioRTP;
      arrayOfPackeType[3] = AudioRTPCP;
      ENUM$VALUES = arrayOfPackeType;
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.RTSPoverHTTPDecoder
 * JD-Core Version:    0.6.0
 */