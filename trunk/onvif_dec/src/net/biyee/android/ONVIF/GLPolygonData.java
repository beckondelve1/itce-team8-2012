package net.biyee.android.ONVIF;

import java.nio.FloatBuffer;

public class GLPolygonData
{
  public FloatBuffer fbData;
  public int iMode;
  public int iVertexCount;

  public GLPolygonData(FloatBuffer paramFloatBuffer, int paramInt1, int paramInt2)
  {
    this.fbData = paramFloatBuffer;
    this.iVertexCount = paramInt1;
    this.iMode = paramInt2;
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.GLPolygonData
 * JD-Core Version:    0.6.0
 */