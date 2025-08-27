/*     */ package net.eq2online.macros.rendering;
/*     */ 
/*     */ import bia;
/*     */ import bid;
/*     */ import bip;
/*     */ import buk;
/*     */ import bve;
/*     */ import cdr;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Random;
/*     */ import net.eq2online.macros.compatibility.AllowedCharacters;
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
/*     */ public final class FontRendererLegacy
/*     */   extends bip
/*     */ {
/*     */   private static FontRendererLegacy instance;
/*  31 */   protected int[] d = new int[] { 1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9, 8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9, 9, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6, 8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9, 8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lineHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int fontDisplayLists;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IntBuffer buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Random rng;
/*     */ 
/*     */ 
/*     */   
/*     */   private cdr renderEngine;
/*     */ 
/*     */ 
/*     */   
/*     */   private nf fontTexture;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FontRendererLegacy(bid gamesettings, nf fontTexture, cdr renderEngine) {
/*  65 */     super(gamesettings, fontTexture, renderEngine, false);
/*     */     
/*  67 */     this.fontTexture = fontTexture;
/*  68 */     this.renderEngine = renderEngine;
/*  69 */     this.lineHeight = 8;
/*  70 */     this.buffer = bia.f(1024);
/*  71 */     this.rng = new Random();
/*     */     
/*  73 */     this.fontDisplayLists = bia.a(288);
/*     */     
/*  75 */     bve tessellator = bve.a();
/*  76 */     buk buf = tessellator.c();
/*     */     
/*  78 */     for (int index = 0; index < 256; index++) {
/*     */       
/*  80 */       GL.glNewList(this.fontDisplayLists + index, 4864);
/*  81 */       GL.glEnableBlend();
/*  82 */       GL.glBlendFunc(770, 771);
/*     */       
/*  84 */       buf.a(7, GL.VF_POSITION_TEX);
/*     */       
/*  86 */       int xPos = index % 16 * 8;
/*  87 */       int yPos = index / 16 * 8;
/*     */       
/*  89 */       float f = 7.99F;
/*  90 */       float f1 = 0.0F;
/*  91 */       float f2 = 0.0F;
/*     */       
/*  93 */       buf.b(0.0D, (0.0F + f), 0.0D).a((xPos / 128.0F + f1), ((yPos + f) / 128.0F + f2)).d();
/*  94 */       buf.b((0.0F + f), (0.0F + f), 0.0D).a(((xPos + f) / 128.0F + f1), ((yPos + f) / 128.0F + f2)).d();
/*  95 */       buf.b((0.0F + f), 0.0D, 0.0D).a(((xPos + f) / 128.0F + f1), (yPos / 128.0F + f2)).d();
/*  96 */       buf.b(0.0D, 0.0D, 0.0D).a((xPos / 128.0F + f1), (yPos / 128.0F + f2)).d();
/*  97 */       tessellator.b();
/*     */       
/*  99 */       GL.glTranslatef(this.d[index], 0.0F, 0.0F);
/* 100 */       GL.glDisableBlend();
/* 101 */       GL.glEndList();
/*     */     } 
/*     */     
/* 104 */     for (int colourCode = 0; colourCode < 32; colourCode++) {
/*     */       
/* 106 */       int i2 = (colourCode >> 3 & 0x1) * 85;
/* 107 */       int red = (colourCode >> 2 & 0x1) * 170 + i2;
/* 108 */       int green = (colourCode >> 1 & 0x1) * 170 + i2;
/* 109 */       int blue = (colourCode >> 0 & 0x1) * 170 + i2;
/*     */       
/* 111 */       if (colourCode == 6)
/*     */       {
/* 113 */         red += 85;
/*     */       }
/*     */       
/* 116 */       boolean bright = (colourCode >= 16);
/*     */       
/* 118 */       if (bright) {
/*     */         
/* 120 */         red /= 4;
/* 121 */         green /= 4;
/* 122 */         blue /= 4;
/*     */       } 
/*     */       
/* 125 */       GL.glNewList(this.fontDisplayLists + 256 + colourCode, 4864);
/* 126 */       GL.glColor3f(red / 255.0F, green / 255.0F, blue / 255.0F);
/* 127 */       GL.glEndList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(String string, float xPos, float yPos, int colour) {
/* 134 */     b(string, xPos + 1.0F, yPos + 1.0F, colour, true);
/* 135 */     a(string, xPos, yPos, colour, false);
/* 136 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(String string, float xPos, float yPos, int colour, boolean shadow) {
/* 142 */     return b(string, xPos, yPos, colour, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int b(String string, float xPos, float yPos, int colour, boolean shadow) {
/* 147 */     if (string == null)
/*     */     {
/* 149 */       return 0;
/*     */     }
/* 151 */     boolean flag1 = false;
/* 152 */     if (shadow) {
/*     */       
/* 154 */       int l = colour & 0xFF000000;
/* 155 */       colour = (colour & 0xFCFCFC) >> 2;
/* 156 */       colour += l;
/*     */     } 
/* 158 */     this.renderEngine.a(this.fontTexture);
/* 159 */     float f = (colour >> 16 & 0xFF) / 255.0F;
/* 160 */     float f1 = (colour >> 8 & 0xFF) / 255.0F;
/* 161 */     float f2 = (colour & 0xFF) / 255.0F;
/* 162 */     float f3 = (colour >> 24 & 0xFF) / 255.0F;
/* 163 */     if (f3 == 0.0F)
/*     */     {
/* 165 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 168 */     GL.glColor4f(f, f1, f2, f3);
/*     */     
/* 170 */     this.buffer.clear();
/*     */     
/* 172 */     GL.glPushMatrix();
/* 173 */     GL.glTranslatef(xPos, yPos, 0.0F);
/*     */     
/* 175 */     for (int i1 = 0; i1 < string.length(); i1++) {
/*     */       
/* 177 */       for (; string.length() > i1 + 1 && string.charAt(i1) == '§'; i1 += 2) {
/*     */         
/* 179 */         char c = string.toLowerCase().charAt(i1 + 1);
/* 180 */         if (c == 'k') {
/*     */           
/* 182 */           flag1 = true;
/*     */         } else {
/*     */           
/* 185 */           flag1 = false;
/* 186 */           int k1 = "0123456789abcdef".indexOf(c);
/* 187 */           if (k1 < 0 || k1 > 15)
/*     */           {
/* 189 */             k1 = 15;
/*     */           }
/* 191 */           this.buffer.put(this.fontDisplayLists + 256 + k1 + (shadow ? 16 : 0));
/* 192 */           if (this.buffer.remaining() == 0) {
/*     */             
/* 194 */             this.buffer.flip();
/* 195 */             GL.glCallLists(this.buffer);
/* 196 */             this.buffer.clear();
/*     */           } 
/*     */         } 
/*     */       } 
/* 200 */       if (i1 < string.length()) {
/*     */         
/* 202 */         int j1 = AllowedCharacters.CHARACTERS.indexOf(string.charAt(i1));
/* 203 */         if (j1 >= 0)
/*     */         {
/* 205 */           if (flag1) {
/*     */             
/* 207 */             int l1 = 0;
/*     */             
/*     */             do {
/* 210 */               l1 = this.rng.nextInt(AllowedCharacters.CHARACTERS.length());
/*     */             }
/* 212 */             while (this.d[j1 + 32] != this.d[l1 + 32]);
/*     */             
/* 214 */             this.buffer.put(this.fontDisplayLists + 256 + this.rng.nextInt(2) + 8 + (shadow ? 16 : 0));
/* 215 */             this.buffer.put(this.fontDisplayLists + l1 + 32);
/*     */           }
/*     */           else {
/*     */             
/* 219 */             this.buffer.put(this.fontDisplayLists + j1 + 32);
/*     */           } 
/*     */         }
/*     */       } 
/* 223 */       if (this.buffer.remaining() == 0) {
/*     */         
/* 225 */         this.buffer.flip();
/* 226 */         GL.glCallLists(this.buffer);
/* 227 */         this.buffer.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     this.buffer.flip();
/* 232 */     GL.glCallLists(this.buffer);
/* 233 */     GL.glPopMatrix();
/* 234 */     GL.glDisableBlend();
/*     */     
/* 236 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(String s) {
/* 242 */     if (s == null)
/*     */     {
/* 244 */       return 0;
/*     */     }
/* 246 */     int i = 0;
/* 247 */     for (int j = 0; j < s.length(); j++) {
/*     */       
/* 249 */       if (s.charAt(j) == '§') {
/*     */         
/* 251 */         j++;
/*     */       } else {
/*     */         
/* 254 */         int k = AllowedCharacters.CHARACTERS.indexOf(s.charAt(j));
/* 255 */         if (k >= 0)
/*     */         {
/* 257 */           i += this.d[k + 32];
/*     */         }
/*     */       } 
/*     */     } 
/* 261 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(char character) {
/* 267 */     if (character < 'Ā')
/*     */     {
/* 269 */       return this.d[character];
/*     */     }
/*     */     
/* 272 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FontRendererLegacy createInstance(bid gamesettings, nf fontTexture, cdr renderEngine) {
/* 277 */     return instance = new FontRendererLegacy(gamesettings, fontTexture, renderEngine);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FontRendererLegacy getInstance() {
/* 282 */     return instance;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\rendering\FontRendererLegacy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */