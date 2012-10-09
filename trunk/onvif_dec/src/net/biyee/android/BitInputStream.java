package net.biyee.android;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class BitInputStream
{
  private int iBuffer;
  private InputStream iIs;
  private int iNextBit = 8;

  public BitInputStream(InputStream paramInputStream)
  {
    this.iIs = paramInputStream;
  }

  public void close()
    throws IOException
  {
    this.iIs.close();
    this.iIs = null;
  }

  public int readBitBigEndian()
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.iIs == null)
        throw new IOException("Already closed");
    }
    finally
    {
      monitorexit;
    }
    if (this.iNextBit == 8)
    {
      this.iBuffer = this.iIs.read();
      if (this.iBuffer == -1)
        throw new EOFException();
      this.iNextBit = 0;
    }
    int i = this.iBuffer & 1 << 7 - this.iNextBit;
    this.iNextBit = (1 + this.iNextBit);
    if (i == 0);
    for (int j = 0; ; j = 1)
    {
      monitorexit;
      return j;
    }
  }

  public int readBitLittleEndian()
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.iIs == null)
        throw new IOException("Already closed");
    }
    finally
    {
      monitorexit;
    }
    if (this.iNextBit == 8)
    {
      this.iBuffer = this.iIs.read();
      if (this.iBuffer == -1)
        throw new EOFException();
      this.iNextBit = 0;
    }
    int i = this.iBuffer & 1 << this.iNextBit;
    this.iNextBit = (1 + this.iNextBit);
    if (i == 0);
    for (int j = 0; ; j = 1)
    {
      monitorexit;
      return j;
    }
  }

  public int readBitsBigEndian(short paramShort)
    throws IOException
  {
    monitorenter;
    int i = 0;
    int j = paramShort - 1;
    while (true)
    {
      if (j < 0)
      {
        monitorexit;
        return i;
      }
      try
      {
        int k = readBitBigEndian();
        i |= k << j;
        j -= 1;
      }
      finally
      {
        monitorexit;
      }
    }
  }

  public int readBitsLittleEndian(short paramShort)
    throws IOException
  {
    monitorenter;
    int i = 0;
    int j = paramShort - 1;
    while (true)
    {
      if (j < 0)
      {
        monitorexit;
        return i;
      }
      try
      {
        int k = readBitLittleEndian();
        i |= k << j;
        j -= 1;
      }
      finally
      {
        monitorexit;
      }
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.BitInputStream
 * JD-Core Version:    0.6.0
 */