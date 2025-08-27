/*     */ package org.bouncycastle.util.encoders;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class Base64Encoder
/*     */   implements Encoder {
/*   8 */   protected final byte[] encodingTable = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  24 */   protected byte padding = 61;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   protected final byte[] decodingTable = new byte[128];
/*     */   
/*     */   protected void initialiseDecodingTable() {
/*     */     int i;
/*  33 */     for (i = 0; i < this.decodingTable.length; i++)
/*     */     {
/*  35 */       this.decodingTable[i] = -1;
/*     */     }
/*     */     
/*  38 */     for (i = 0; i < this.encodingTable.length; i++)
/*     */     {
/*  40 */       this.decodingTable[this.encodingTable[i]] = (byte)i;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Base64Encoder() {
/*  46 */     initialiseDecodingTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
/*  57 */     int b1, b2, b3, d1, d2, modulus = length % 3;
/*  58 */     int dataLength = length - modulus;
/*     */ 
/*     */     
/*  61 */     for (int i = off; i < off + dataLength; i += 3) {
/*     */       
/*  63 */       int a1 = data[i] & 0xFF;
/*  64 */       int a2 = data[i + 1] & 0xFF;
/*  65 */       int a3 = data[i + 2] & 0xFF;
/*     */       
/*  67 */       out.write(this.encodingTable[a1 >>> 2 & 0x3F]);
/*  68 */       out.write(this.encodingTable[(a1 << 4 | a2 >>> 4) & 0x3F]);
/*  69 */       out.write(this.encodingTable[(a2 << 2 | a3 >>> 6) & 0x3F]);
/*  70 */       out.write(this.encodingTable[a3 & 0x3F]);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     switch (modulus) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*  84 */         d1 = data[off + dataLength] & 0xFF;
/*  85 */         b1 = d1 >>> 2 & 0x3F;
/*  86 */         b2 = d1 << 4 & 0x3F;
/*     */         
/*  88 */         out.write(this.encodingTable[b1]);
/*  89 */         out.write(this.encodingTable[b2]);
/*  90 */         out.write(this.padding);
/*  91 */         out.write(this.padding);
/*     */         break;
/*     */       case 2:
/*  94 */         d1 = data[off + dataLength] & 0xFF;
/*  95 */         d2 = data[off + dataLength + 1] & 0xFF;
/*     */         
/*  97 */         b1 = d1 >>> 2 & 0x3F;
/*  98 */         b2 = (d1 << 4 | d2 >>> 4) & 0x3F;
/*  99 */         b3 = d2 << 2 & 0x3F;
/*     */         
/* 101 */         out.write(this.encodingTable[b1]);
/* 102 */         out.write(this.encodingTable[b2]);
/* 103 */         out.write(this.encodingTable[b3]);
/* 104 */         out.write(this.padding);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     return dataLength / 3 * 4 + ((modulus == 0) ? 0 : 4);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean ignore(char c) {
/* 115 */     return (c == '\n' || c == '\r' || c == '\t' || c == ' ');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int decode(byte[] data, int off, int length, OutputStream out) throws IOException {
/* 128 */     int outLen = 0;
/*     */     
/* 130 */     int end = off + length;
/*     */     
/* 132 */     while (end > off) {
/*     */       
/* 134 */       if (!ignore((char)data[end - 1])) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 139 */       end--;
/*     */     } 
/*     */     
/* 142 */     int i = off;
/* 143 */     int finish = end - 4;
/*     */     
/* 145 */     i = nextI(data, i, finish);
/*     */     
/* 147 */     while (i < finish) {
/*     */       
/* 149 */       byte b1 = this.decodingTable[data[i++]];
/*     */       
/* 151 */       i = nextI(data, i, finish);
/*     */       
/* 153 */       byte b2 = this.decodingTable[data[i++]];
/*     */       
/* 155 */       i = nextI(data, i, finish);
/*     */       
/* 157 */       byte b3 = this.decodingTable[data[i++]];
/*     */       
/* 159 */       i = nextI(data, i, finish);
/*     */       
/* 161 */       byte b4 = this.decodingTable[data[i++]];
/*     */       
/* 163 */       if ((b1 | b2 | b3 | b4) < 0)
/*     */       {
/* 165 */         throw new IOException("invalid characters encountered in base64 data");
/*     */       }
/*     */       
/* 168 */       out.write(b1 << 2 | b2 >> 4);
/* 169 */       out.write(b2 << 4 | b3 >> 2);
/* 170 */       out.write(b3 << 6 | b4);
/*     */       
/* 172 */       outLen += 3;
/*     */       
/* 174 */       i = nextI(data, i, finish);
/*     */     } 
/*     */     
/* 177 */     outLen += decodeLastBlock(out, (char)data[end - 4], (char)data[end - 3], (char)data[end - 2], (char)data[end - 1]);
/*     */     
/* 179 */     return outLen;
/*     */   }
/*     */ 
/*     */   
/*     */   private int nextI(byte[] data, int i, int finish) {
/* 184 */     while (i < finish && ignore((char)data[i]))
/*     */     {
/* 186 */       i++;
/*     */     }
/* 188 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int decode(String data, OutputStream out) throws IOException {
/* 201 */     int length = 0;
/*     */     
/* 203 */     int end = data.length();
/*     */     
/* 205 */     while (end > 0) {
/*     */       
/* 207 */       if (!ignore(data.charAt(end - 1))) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 212 */       end--;
/*     */     } 
/*     */     
/* 215 */     int i = 0;
/* 216 */     int finish = end - 4;
/*     */     
/* 218 */     i = nextI(data, i, finish);
/*     */     
/* 220 */     while (i < finish) {
/*     */       
/* 222 */       byte b1 = this.decodingTable[data.charAt(i++)];
/*     */       
/* 224 */       i = nextI(data, i, finish);
/*     */       
/* 226 */       byte b2 = this.decodingTable[data.charAt(i++)];
/*     */       
/* 228 */       i = nextI(data, i, finish);
/*     */       
/* 230 */       byte b3 = this.decodingTable[data.charAt(i++)];
/*     */       
/* 232 */       i = nextI(data, i, finish);
/*     */       
/* 234 */       byte b4 = this.decodingTable[data.charAt(i++)];
/*     */       
/* 236 */       if ((b1 | b2 | b3 | b4) < 0)
/*     */       {
/* 238 */         throw new IOException("invalid characters encountered in base64 data");
/*     */       }
/*     */       
/* 241 */       out.write(b1 << 2 | b2 >> 4);
/* 242 */       out.write(b2 << 4 | b3 >> 2);
/* 243 */       out.write(b3 << 6 | b4);
/*     */       
/* 245 */       length += 3;
/*     */       
/* 247 */       i = nextI(data, i, finish);
/*     */     } 
/*     */     
/* 250 */     length += decodeLastBlock(out, data.charAt(end - 4), data.charAt(end - 3), data.charAt(end - 2), data.charAt(end - 1));
/*     */     
/* 252 */     return length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int decodeLastBlock(OutputStream out, char c1, char c2, char c3, char c4) throws IOException {
/* 259 */     if (c3 == this.padding) {
/*     */       
/* 261 */       byte b5 = this.decodingTable[c1];
/* 262 */       byte b6 = this.decodingTable[c2];
/*     */       
/* 264 */       if ((b5 | b6) < 0)
/*     */       {
/* 266 */         throw new IOException("invalid characters encountered at end of base64 data");
/*     */       }
/*     */       
/* 269 */       out.write(b5 << 2 | b6 >> 4);
/*     */       
/* 271 */       return 1;
/*     */     } 
/* 273 */     if (c4 == this.padding) {
/*     */       
/* 275 */       byte b5 = this.decodingTable[c1];
/* 276 */       byte b6 = this.decodingTable[c2];
/* 277 */       byte b7 = this.decodingTable[c3];
/*     */       
/* 279 */       if ((b5 | b6 | b7) < 0)
/*     */       {
/* 281 */         throw new IOException("invalid characters encountered at end of base64 data");
/*     */       }
/*     */       
/* 284 */       out.write(b5 << 2 | b6 >> 4);
/* 285 */       out.write(b6 << 4 | b7 >> 2);
/*     */       
/* 287 */       return 2;
/*     */     } 
/*     */ 
/*     */     
/* 291 */     byte b1 = this.decodingTable[c1];
/* 292 */     byte b2 = this.decodingTable[c2];
/* 293 */     byte b3 = this.decodingTable[c3];
/* 294 */     byte b4 = this.decodingTable[c4];
/*     */     
/* 296 */     if ((b1 | b2 | b3 | b4) < 0)
/*     */     {
/* 298 */       throw new IOException("invalid characters encountered at end of base64 data");
/*     */     }
/*     */     
/* 301 */     out.write(b1 << 2 | b2 >> 4);
/* 302 */     out.write(b2 << 4 | b3 >> 2);
/* 303 */     out.write(b3 << 6 | b4);
/*     */     
/* 305 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextI(String data, int i, int finish) {
/* 311 */     while (i < finish && ignore(data.charAt(i)))
/*     */     {
/* 313 */       i++;
/*     */     }
/* 315 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\org\bouncycastl\\util\encoders\Base64Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */