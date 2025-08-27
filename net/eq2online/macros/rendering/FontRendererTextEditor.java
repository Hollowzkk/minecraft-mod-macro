/*     */ package net.eq2online.macros.rendering;
/*     */ 
/*     */ import bia;
/*     */ import bib;
/*     */ import bip;
/*     */ import buk;
/*     */ import bve;
/*     */ import cdt;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.eq2online.macros.compatibility.AllowedCharacters;
/*     */ import net.eq2online.util.GlColour;
/*     */ import nf;
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
/*     */ 
/*     */ 
/*     */ public class FontRendererTextEditor
/*     */   extends bip
/*     */ {
/*     */   private static final int DISPLAY_LIST_ALLOCATION = 264;
/*     */   private static final int GLYPH_COUNT = 256;
/*     */   private static final int LIST_SEL = 256;
/*     */   private static final int LIST_SELCOL = 257;
/*     */   private static final int LIST_ERRORCOL = 258;
/*     */   private static final int LIST_NORMALCOL = 259;
/*     */   private static final int LIST_CCCOL = 260;
/*     */   private static final int LIST_HICOL = 261;
/*     */   private static final int LIST_HICOL2 = 262;
/*     */   private static final int LIST_PARAMCOL = 263;
/*     */   private static final int LIST_QUOTECOL = 264;
/*  80 */   public static final Map<String, GlColour> DEFAULT_COLOURS = (Map<String, GlColour>)ImmutableMap.builder()
/*  81 */     .put("sel", new GlColour(0.5F, 0.5F, 1.0F))
/*  82 */     .put("error", new GlColour(0.4F, 0.2F, 0.0F))
/*  83 */     .put("cc", new GlColour(0.0F, 0.5F, 0.0F))
/*  84 */     .put("hi", new GlColour(0.5F, 0.0F, 0.0F))
/*  85 */     .put("hi2", new GlColour(0.0F, 0.0F, 0.5F))
/*  86 */     .put("param", new GlColour(0.5F, 0.0F, 0.5F))
/*  87 */     .put("quote", new GlColour(0.0F, 0.3F, 0.3F))
/*  88 */     .build();
/*     */   
/*  90 */   public static final Map<String, GlColour> DARK_COLOURS = (Map<String, GlColour>)ImmutableMap.builder()
/*  91 */     .put("sel", new GlColour(1.0F, 0.47F, 1.0F))
/*  92 */     .put("error", new GlColour(0.4F, 0.2F, 0.0F))
/*  93 */     .put("cc", new GlColour(0.0F, 0.6F, 0.3F))
/*  94 */     .put("hi", new GlColour(0.8F, 0.15F, 0.0F))
/*  95 */     .put("hi2", new GlColour(0.8F, 0.6F, 0.0F))
/*  96 */     .put("param", new GlColour(0.8F, 0.0F, 0.8F))
/*  97 */     .put("quote", new GlColour(0.0F, 0.6F, 0.6F))
/*  98 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int fixedWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IntBuffer buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fontDisplayLists;
/*     */ 
/*     */ 
/*     */   
/*     */   private String highLightSymbols;
/*     */ 
/*     */ 
/*     */   
/*     */   public int fontTextureName;
/*     */ 
/*     */ 
/*     */   
/*     */   private nf fontTexture;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRendererTextEditor(bib minecraft, nf fontTexture, int fixedWidth) {
/* 131 */     super(minecraft.t, fontTexture, minecraft.N(), false); BufferedImage bufferedimage; this.fixedWidth = 7; this.highLightSymbols = "+=-/&%$#|*@'{}[]()";
/*     */     this.fontTextureName = 0;
/* 133 */     this.fixedWidth = fixedWidth;
/*     */ 
/*     */     
/* 136 */     int[] charWidth = new int[256];
/* 137 */     this.fontTexture = fontTexture;
/* 138 */     this.fontTextureName = 0;
/*     */ 
/*     */     
/* 141 */     this.buffer = bia.f(1024);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 147 */       bufferedimage = ImageIO.read(minecraft.O().a(this.fontTexture).b());
/*     */     }
/* 149 */     catch (IOException ioexception) {
/*     */       
/* 151 */       throw new RuntimeException(ioexception);
/*     */     } 
/*     */     
/* 154 */     this.fontTextureName = cdt.a();
/* 155 */     cdt.a(this.fontTextureName, bufferedimage, false, false);
/* 156 */     this.fontDisplayLists = bia.a(264);
/* 157 */     bve tessellator = bve.a();
/* 158 */     buk buf = tessellator.c();
/*     */ 
/*     */     
/* 161 */     for (int glyphIndex = 0; glyphIndex < 256; glyphIndex++) {
/*     */ 
/*     */       
/* 164 */       charWidth[glyphIndex] = fixedWidth;
/*     */ 
/*     */       
/* 167 */       GL.glNewList(this.fontDisplayLists + glyphIndex, 4864);
/*     */ 
/*     */       
/* 170 */       int glyphUCoordinate = glyphIndex % 16 * 8;
/* 171 */       int glyphVCoordinate = glyphIndex / 16 * 8;
/*     */ 
/*     */       
/* 174 */       float f = 7.99F, f1 = 0.0F, f2 = 0.0F;
/*     */ 
/*     */       
/* 177 */       buf.a(7, GL.VF_POSITION_TEX);
/* 178 */       buf.b(0.0D, (0.0F + f), 0.0D).a((glyphUCoordinate / 128.0F + f1), ((glyphVCoordinate + f) / 128.0F + f2)).d();
/* 179 */       buf.b((0.0F + f), (0.0F + f), 0.0D).a(((glyphUCoordinate + f) / 128.0F + f1), ((glyphVCoordinate + f) / 128.0F + f2)).d();
/* 180 */       buf.b((0.0F + f), 0.0D, 0.0D).a(((glyphUCoordinate + f) / 128.0F + f1), (glyphVCoordinate / 128.0F + f2)).d();
/* 181 */       buf.b(0.0D, 0.0D, 0.0D).a((glyphUCoordinate / 128.0F + f1), (glyphVCoordinate / 128.0F + f2)).d();
/* 182 */       tessellator.b();
/*     */ 
/*     */       
/* 185 */       GL.glTranslatef(charWidth[glyphIndex], 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */       
/* 189 */       GL.glEndList();
/*     */     } 
/*     */     
/* 192 */     GL.glNewList(this.fontDisplayLists + 256, 4864);
/*     */     
/* 194 */     GL.glColor4f(0.35F, 0.35F, 0.35F, 1.0F);
/* 195 */     GL.glDisableTexture2D();
/* 196 */     buf.a(7, GL.VF_POSITION);
/* 197 */     buf.b(-1.0D, 9.0D, 0.0D).d();
/* 198 */     buf.b((0.0F + fixedWidth), 9.0D, 0.0D).d();
/* 199 */     buf.b((0.0F + fixedWidth), -1.0D, 0.0D).d();
/* 200 */     buf.b(-1.0D, -1.0D, 0.0D).d();
/* 201 */     tessellator.b();
/* 202 */     GL.glEnableTexture2D();
/* 203 */     GL.glColor3f(0.6F, 0.58F, 0.6F);
/*     */     
/* 205 */     GL.glEndList();
/*     */     
/* 207 */     setColours(DEFAULT_COLOURS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultColours() {
/* 212 */     setColours(DEFAULT_COLOURS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDarkColours() {
/* 217 */     setColours(DARK_COLOURS);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setColours(Map<String, GlColour> colours) {
/* 222 */     GL.glNewList(this.fontDisplayLists + 257, 4864); ((GlColour)colours.get("sel")).apply(); GL.glEndList();
/* 223 */     GL.glNewList(this.fontDisplayLists + 258, 4864); ((GlColour)colours.get("error")).apply(); GL.glEndList();
/* 224 */     GL.glNewList(this.fontDisplayLists + 260, 4864); ((GlColour)colours.get("cc")).apply(); GL.glEndList();
/* 225 */     GL.glNewList(this.fontDisplayLists + 261, 4864); ((GlColour)colours.get("hi")).apply(); GL.glEndList();
/* 226 */     GL.glNewList(this.fontDisplayLists + 262, 4864); ((GlColour)colours.get("hi2")).apply(); GL.glEndList();
/* 227 */     GL.glNewList(this.fontDisplayLists + 263, 4864); ((GlColour)colours.get("param")).apply(); GL.glEndList();
/* 228 */     GL.glNewList(this.fontDisplayLists + 264, 4864); ((GlColour)colours.get("quote")).apply(); GL.glEndList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void release() {
/* 233 */     bia.a(this.fontDisplayLists, 264);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(String displayText, int xPos, int yPos, int colour) {
/* 239 */     renderFixedWidthString(displayText, xPos, yPos, colour, false, 0, 32768);
/* 240 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderFixedWidthString(String displayText, int xPos, int yPos, int colour, boolean doHighlight, int startChar, int displayChars) {
/* 246 */     if (displayText == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 252 */     GL.glBindTexture2D(this.fontTextureName);
/*     */ 
/*     */     
/* 255 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/* 256 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/* 257 */     float blue = (colour & 0xFF) / 255.0F;
/* 258 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*     */ 
/*     */     
/* 261 */     if (alpha == 0.0F) alpha = 1.0F;
/*     */ 
/*     */     
/* 264 */     GL.glNewList(this.fontDisplayLists + 259, 4864);
/* 265 */     GL.glColor3f(red, green, blue);
/* 266 */     GL.glEndList();
/*     */ 
/*     */     
/* 269 */     GL.glColor4f(0.01F, 0.01F, 0.01F, 0.0F);
/* 270 */     GL.glColor4f(red, green, blue, alpha);
/*     */ 
/*     */     
/* 273 */     this.buffer.clear();
/*     */ 
/*     */     
/* 276 */     GL.glPushMatrix();
/* 277 */     GL.glTranslatef(xPos, yPos, 0.0F);
/*     */     
/* 279 */     boolean highlight = false, quote = false, unquote = false, param = false;
/* 280 */     boolean resetColour = false;
/* 281 */     int realPos = 0;
/* 282 */     int previousCodePoint = -1;
/*     */ 
/*     */     
/* 285 */     for (int charIndex = 0; charIndex < displayText.length(); charIndex++) {
/*     */       
/* 287 */       if (charIndex < displayText.length()) {
/*     */         
/* 289 */         int codePoint = displayText.charAt(charIndex);
/*     */         
/* 291 */         if (codePoint == 65535) {
/*     */           
/* 293 */           highlight = true;
/*     */           continue;
/*     */         } 
/* 296 */         if (codePoint == 65534) {
/*     */           
/* 298 */           highlight = false;
/*     */           
/*     */           continue;
/*     */         } 
/* 302 */         if (codePoint == 65531) {
/*     */           
/* 304 */           param = true;
/*     */           continue;
/*     */         } 
/* 307 */         if (codePoint == 65530) {
/*     */           
/* 309 */           param = false;
/*     */           
/*     */           continue;
/*     */         } 
/* 313 */         if (codePoint == 65533) {
/*     */           
/* 315 */           if (doHighlight)
/*     */           {
/* 317 */             addToBufferAndFlip(this.fontDisplayLists + 261);
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/* 322 */         if (codePoint == 65532) {
/*     */           
/* 324 */           if (doHighlight)
/*     */           {
/* 326 */             addToBufferAndFlip(this.fontDisplayLists + 259);
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/* 331 */         if (doHighlight && codePoint == 34 && previousCodePoint != 92) {
/*     */           
/* 333 */           quote = !quote;
/* 334 */           unquote = !quote;
/*     */         } 
/*     */         
/* 337 */         if (realPos >= startChar && realPos < startChar + displayChars) {
/*     */           
/* 339 */           int validCodePointIndex = AllowedCharacters.CHARACTERS.indexOf(codePoint);
/*     */           
/* 341 */           if (highlight)
/*     */           {
/* 343 */             addToBufferAndFlip(this.fontDisplayLists + 256);
/*     */           }
/*     */           
/* 346 */           if (this.highLightSymbols.indexOf((char)codePoint) > -1 && doHighlight && !param) {
/*     */             
/* 348 */             addToBufferAndFlip(this.fontDisplayLists + 262);
/* 349 */             resetColour = true;
/*     */           }
/* 351 */           else if ((quote || unquote) && !param) {
/*     */             
/* 353 */             addToBufferAndFlip(this.fontDisplayLists + 264);
/* 354 */             resetColour = true;
/*     */           }
/* 356 */           else if (param) {
/*     */             
/* 358 */             addToBufferAndFlip(this.fontDisplayLists + 263);
/* 359 */             resetColour = true;
/*     */           } 
/*     */           
/* 362 */           if (validCodePointIndex >= 0) {
/*     */ 
/*     */             
/* 365 */             addToBufferAndFlip(this.fontDisplayLists + validCodePointIndex + 32);
/*     */           }
/* 367 */           else if (codePoint >= 0) {
/*     */ 
/*     */             
/* 370 */             if (codePoint == 8 || codePoint == 0 || codePoint > 255) codePoint = 63;
/*     */ 
/*     */             
/* 373 */             addToBufferAndFlip(this.fontDisplayLists + ((codePoint == 167) ? 260 : 258));
/*     */ 
/*     */             
/* 376 */             addToBufferAndFlip(this.fontDisplayLists + codePoint);
/* 377 */             resetColour = true;
/*     */             
/* 379 */             if (codePoint == 167 && charIndex + 1 < displayText.length() && "0123456789abcdefklmnor"
/* 380 */               .indexOf(displayText.charAt(charIndex + 1)) > -1) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 386 */           if (highlight || resetColour || unquote) {
/*     */             
/* 388 */             addToBufferAndFlip(this.fontDisplayLists + 259);
/* 389 */             resetColour = false;
/* 390 */             unquote = false;
/*     */           } 
/*     */         } 
/*     */         
/* 394 */         realPos++;
/* 395 */         previousCodePoint = codePoint;
/*     */       } 
/*     */       continue;
/*     */     } 
/* 399 */     this.buffer.flip();
/* 400 */     GL.glCallLists(this.buffer);
/* 401 */     GL.glColor4f(0.01F, 0.01F, 0.01F, 0.0F);
/* 402 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToBufferAndFlip(int value) {
/* 407 */     this.buffer.put(value);
/* 408 */     if (this.buffer.remaining() == 0) {
/*     */       
/* 410 */       this.buffer.flip();
/* 411 */       GL.glCallLists(this.buffer);
/* 412 */       this.buffer.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFixedStringWidth(String stringToMeasure) {
/* 423 */     if (stringToMeasure == null)
/*     */     {
/* 425 */       return 0;
/*     */     }
/*     */     
/* 428 */     return stringToMeasure.length() * this.fixedWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFixedCharWidth() {
/* 438 */     return this.fixedWidth;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\rendering\FontRendererTextEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */