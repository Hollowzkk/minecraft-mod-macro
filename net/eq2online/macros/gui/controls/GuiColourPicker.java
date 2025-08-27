/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import bje;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Color;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.res.ResourceLocations;
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
/*     */ public class GuiColourPicker
/*     */   extends GuiControlEx
/*     */ {
/*     */   private static final int H = 0;
/*     */   private static final int S = 1;
/*     */   private static final int B = 2;
/*     */   private float[] hsb;
/*     */   private int rgb;
/*     */   private int opacity;
/*     */   private bje txtRed;
/*     */   private bje txtGreen;
/*     */   private bje txtBlue;
/*     */   private bje txtAlpha;
/*     */   private GuiControl btnOk;
/*     */   private GuiControl btnCancel;
/*     */   private boolean draggingHS;
/*     */   private boolean draggingB;
/*     */   private boolean draggingA;
/*     */   private Rectangle rectHSArea;
/*     */   private Rectangle rectBArea;
/*     */   private Rectangle rectAArea;
/*  72 */   private GuiDialogBox.DialogResult result = GuiDialogBox.DialogResult.NONE;
/*     */   
/*     */   private bip fontRenderer;
/*     */ 
/*     */   
/*     */   public GuiColourPicker(bib minecraft, int controlId, int xPos, int yPos, int initialColour, String displayText) {
/*  78 */     super(minecraft, controlId, xPos, yPos, 231, 173, displayText);
/*     */     
/*  80 */     Color colour = new Color(initialColour);
/*  81 */     this.hsb = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), null);
/*  82 */     this.opacity = initialColour & 0xFF000000;
/*  83 */     if (this.opacity == 16777216) this.opacity = 0;
/*     */     
/*  85 */     this.fontRenderer = minecraft.k;
/*  86 */     this.txtRed = new bje(0, this.fontRenderer, this.h + 188, this.i + 10, 32, 16);
/*  87 */     this.txtGreen = new bje(1, this.fontRenderer, this.h + 188, this.i + 30, 32, 16);
/*  88 */     this.txtBlue = new bje(2, this.fontRenderer, this.h + 188, this.i + 50, 32, 16);
/*  89 */     this.txtAlpha = new bje(3, this.fontRenderer, this.h + 188, this.i + 70, 32, 16);
/*     */     
/*  91 */     this.txtRed.f(3);
/*  92 */     this.txtGreen.f(3);
/*  93 */     this.txtBlue.f(3);
/*  94 */     this.txtAlpha.f(3);
/*     */     
/*  96 */     this.rectHSArea = new Rectangle(this.h + 10, this.i + 10, 128, 128);
/*  97 */     this.rectBArea = new Rectangle(this.h + 143, this.i + 10, 15, 128);
/*  98 */     this.rectAArea = new Rectangle(this.h + 163, this.i + 10, 15, 128);
/*     */     
/* 100 */     this.btnOk = new GuiControl(0, this.h + 9, this.i + 145, 55, 20, I18n.get("gui.ok"), 7);
/* 101 */     this.btnCancel = new GuiControl(1, this.h + 70, this.i + 145, 65, 20, I18n.get("gui.cancel"), 1);
/*     */     
/* 103 */     updateColour();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDialogBox.DialogResult getDialogResult() {
/* 108 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColour() {
/* 113 */     int opacity = (this.opacity == 0) ? 16777216 : this.opacity;
/* 114 */     int rgb = opacity | 0xFFFFFF & Color.HSBtoRGB(this.hsb[0], this.hsb[1], this.hsb[2]);
/* 115 */     return rgb;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 121 */     a(minecraft, mouseX, mouseY);
/*     */ 
/*     */     
/* 124 */     int hPos = this.h + 10 + (int)(128.0F * this.hsb[0]);
/* 125 */     int sPos = this.i + 10 + 128 - (int)(128.0F * this.hsb[1]);
/* 126 */     int bPos = this.i + 10 + 128 - (int)(128.0F * this.hsb[2]);
/* 127 */     int aPos = this.i + 10 + (256 - (this.opacity >> 24 & 0xFF)) / 2;
/*     */ 
/*     */     
/* 130 */     int brightness = Color.HSBtoRGB(this.hsb[0], this.hsb[1], 1.0F) | 0xFF000000;
/*     */ 
/*     */     
/* 133 */     a(this.h, this.i, this.h + this.f, this.i + this.g, -1442840576);
/* 134 */     a(this.h + 9, this.i + 9, this.h + 139, this.i + 139, -6250336);
/* 135 */     a(this.h + 142, this.i + 9, this.h + 159, this.i + 139, -6250336);
/* 136 */     a(this.h + 162, this.i + 9, this.h + 179, this.i + 139, -6250336);
/* 137 */     a(this.h + 187, this.i + 105, this.h + 221, this.i + 139, -6250336);
/*     */ 
/*     */     
/* 140 */     minecraft.N().a(ResourceLocations.COLOURPICKER_PICKER);
/* 141 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 142 */     this.renderer.drawTexturedModalRect(this.h + 10, this.i + 10, this.h + 138, this.i + 138, 0, 0, 256, 256);
/* 143 */     this.renderer.drawCrossHair(hPos, sPos, 5, 1, -16777216);
/*     */ 
/*     */     
/* 146 */     a(this.h + 143, this.i + 10, this.h + 158, this.i + 138, brightness, -16777216);
/* 147 */     this.renderer.drawRotText("Luminosity", this.h + 150, this.i + 74, -16777216, false);
/* 148 */     a(this.h + 142, bPos - 1, this.h + 159, bPos + 1, -1);
/*     */ 
/*     */     
/* 151 */     a(this.h + 163, this.i + 10, this.h + 178, this.i + 138, -1, -16777216);
/* 152 */     this.renderer.drawRotText("Opacity", this.h + 170, this.i + 74, -16777216, false);
/* 153 */     a(this.h + 162, aPos - 1, this.h + 179, aPos + 1, -1);
/*     */ 
/*     */     
/* 156 */     minecraft.N().a(ResourceLocations.COLOURPICKER_CHECKER);
/* 157 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 158 */     this.renderer.drawTexturedModalRect(this.h + 188, this.i + 106, this.h + 220, this.i + 138, 0, 0, 1024, 1024);
/* 159 */     a(this.h + 188, this.i + 106, this.h + 220, this.i + 138, this.rgb);
/*     */ 
/*     */     
/* 162 */     this.txtRed.g();
/* 163 */     this.txtGreen.g();
/* 164 */     this.txtBlue.g();
/* 165 */     this.txtAlpha.g();
/*     */     
/* 167 */     this.btnOk.a(minecraft, mouseX, mouseY, partialTicks);
/* 168 */     this.btnCancel.a(minecraft, mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCursorCounter() {
/* 173 */     this.txtRed.a();
/* 174 */     this.txtGreen.a();
/* 175 */     this.txtBlue.a();
/* 176 */     this.txtAlpha.a();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateColour() {
/* 181 */     this.rgb = this.opacity | 0xFFFFFF & Color.HSBtoRGB(this.hsb[0], this.hsb[1], this.hsb[2]);
/* 182 */     this.txtRed.a(String.valueOf(this.rgb >> 16 & 0xFF));
/* 183 */     this.txtGreen.a(String.valueOf(this.rgb >> 8 & 0xFF));
/* 184 */     this.txtBlue.a(String.valueOf(this.rgb & 0xFF));
/* 185 */     this.txtAlpha.a(String.valueOf(this.opacity >> 24 & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateColourFromTextEntry() {
/* 190 */     int currentRed = this.rgb >> 16 & 0xFF;
/* 191 */     int currentGreen = this.rgb >> 8 & 0xFF;
/* 192 */     int currentBlue = this.rgb & 0xFF;
/* 193 */     int currentOpacity = this.opacity >> 24 & 0xFF;
/*     */     
/* 195 */     currentRed = (int)clamp(tryParseInt(this.txtRed.b(), currentRed), 0.0F, 255.0F);
/* 196 */     currentGreen = (int)clamp(tryParseInt(this.txtGreen.b(), currentGreen), 0.0F, 255.0F);
/* 197 */     currentBlue = (int)clamp(tryParseInt(this.txtBlue.b(), currentBlue), 0.0F, 255.0F);
/* 198 */     currentOpacity = (int)clamp(tryParseInt(this.txtAlpha.b(), currentOpacity), 0.0F, 255.0F);
/*     */     
/* 200 */     this.hsb = Color.RGBtoHSB(currentRed, currentGreen, currentBlue, null);
/* 201 */     this.opacity = currentOpacity << 24 & 0xFF000000;
/* 202 */     updateColour();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int tryParseInt(String text, int defaultValue) {
/*     */     try {
/* 209 */       return Integer.parseInt(text);
/*     */     }
/* 211 */     catch (Exception ex) {
/*     */       
/* 213 */       return "".equals(text) ? 0 : defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bib minecraft, int mouseX, int mouseY) {
/* 224 */     super.a(minecraft, mouseX, mouseY);
/*     */     
/* 226 */     if (this.draggingHS) {
/*     */       
/* 228 */       this.hsb[0] = clamp((mouseX - this.h - 10), 0.0F, 128.0F) / 128.0F;
/* 229 */       this.hsb[1] = (128.0F - clamp((mouseY - this.i - 10), 0.0F, 128.0F)) / 128.0F;
/* 230 */       updateColour();
/*     */     } 
/*     */     
/* 233 */     if (this.draggingB) {
/*     */       
/* 235 */       this.hsb[2] = (128.0F - clamp((mouseY - this.i - 10), 0.0F, 128.0F)) / 128.0F;
/* 236 */       updateColour();
/*     */     } 
/*     */     
/* 239 */     if (this.draggingA) {
/*     */       
/* 241 */       this.opacity = (mouseY - this.i < 11) ? -16777216 : (128 - (int)clamp((mouseY - this.i - 10), 0.0F, 128.0F) << 25 & 0xFF000000);
/* 242 */       updateColour();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 253 */     if (super.b(minecraft, mouseX, mouseY)) {
/*     */ 
/*     */       
/* 256 */       if (this.btnOk.b(minecraft, mouseX, mouseY))
/*     */       {
/* 258 */         this.result = GuiDialogBox.DialogResult.OK;
/*     */       }
/*     */       
/* 261 */       if (this.btnCancel.b(minecraft, mouseX, mouseY))
/*     */       {
/* 263 */         this.result = GuiDialogBox.DialogResult.CANCEL;
/*     */       }
/*     */       
/* 266 */       if (this.rectHSArea.contains(mouseX, mouseY))
/*     */       {
/* 268 */         this.draggingHS = true;
/*     */       }
/*     */       
/* 271 */       if (this.rectBArea.contains(mouseX, mouseY))
/*     */       {
/* 273 */         this.draggingB = true;
/*     */       }
/*     */       
/* 276 */       if (this.rectAArea.contains(mouseX, mouseY))
/*     */       {
/* 278 */         this.draggingA = true;
/*     */       }
/*     */       
/* 281 */       this.txtRed.a(mouseX, mouseY, 0);
/* 282 */       this.txtGreen.a(mouseX, mouseY, 0);
/* 283 */       this.txtBlue.a(mouseX, mouseY, 0);
/* 284 */       this.txtAlpha.a(mouseX, mouseY, 0);
/*     */       
/* 286 */       return true;
/*     */     } 
/* 288 */     if (this.l)
/*     */     {
/* 290 */       this.result = GuiDialogBox.DialogResult.CANCEL;
/*     */     }
/*     */     
/* 293 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 302 */     this.draggingHS = false;
/* 303 */     this.draggingB = false;
/* 304 */     this.draggingA = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean textBoxKeyTyped(char keyChar, int keyCode) {
/* 309 */     this.txtRed.a(keyChar, keyCode);
/* 310 */     this.txtGreen.a(keyChar, keyCode);
/* 311 */     this.txtBlue.a(keyChar, keyCode);
/* 312 */     this.txtAlpha.a(keyChar, keyCode);
/* 313 */     updateColourFromTextEntry();
/*     */     
/* 315 */     if (keyCode == 15)
/*     */     {
/* 317 */       if (this.txtRed.m()) {
/*     */         
/* 319 */         this.txtRed.b(false);
/* 320 */         this.txtGreen.b(true);
/* 321 */         this.txtBlue.b(false);
/* 322 */         this.txtAlpha.b(false);
/*     */       }
/* 324 */       else if (this.txtGreen.m()) {
/*     */         
/* 326 */         this.txtRed.b(false);
/* 327 */         this.txtGreen.b(false);
/* 328 */         this.txtBlue.b(true);
/* 329 */         this.txtAlpha.b(false);
/*     */       }
/* 331 */       else if (this.txtBlue.m()) {
/*     */         
/* 333 */         this.txtRed.b(false);
/* 334 */         this.txtGreen.b(false);
/* 335 */         this.txtBlue.b(false);
/* 336 */         this.txtAlpha.b(true);
/*     */       }
/*     */       else {
/*     */         
/* 340 */         this.txtRed.b(true);
/* 341 */         this.txtGreen.b(false);
/* 342 */         this.txtBlue.b(false);
/* 343 */         this.txtAlpha.b(false);
/*     */       } 
/*     */     }
/*     */     
/* 347 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float clamp(float value, float min, float max) {
/* 352 */     return Math.min(Math.max(value, min), max);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiColourPicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */