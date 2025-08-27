package org.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

public interface Encoder {
  int encode(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, OutputStream paramOutputStream) throws IOException;
  
  int decode(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, OutputStream paramOutputStream) throws IOException;
  
  int decode(String paramString, OutputStream paramOutputStream) throws IOException;
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\org\bouncycastl\\util\encoders\Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */