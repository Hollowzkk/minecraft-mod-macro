/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.core.MacroTriggerType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayoutPanelKeys
/*     */   extends LayoutPanelStandard
/*     */ {
/*     */   public LayoutPanelKeys(Macros macros, bib minecraft, int controlId) {
/*  15 */     super(macros, minecraft, controlId, "keyboard.layout", MacroTriggerType.KEY);
/*     */     
/*  17 */     this.defaultLayout = "{1,20,4}{2,36,24}{3,56,24}{4,76,24}{5,96,24}{6,116,24}{7,136,24}{8,156,24}{9,176,24}{10,196,24}{11,216,24}{12,236,24}{13,256,24}{14,288,24}{15,16,40}{16,44,40}{17,64,40}{18,84,40}{19,104,40}{20,124,40}{21,144,40}{22,164,40}{23,184,40}{24,204,40}{25,224,40}{26,244,40}{27,264,40}{28,292,40}{29,20,88}{30,48,56}{31,68,56}{32,88,56}{33,108,56}{34,128,56}{35,148,56}{36,168,56}{37,188,56}{38,208,56}{39,228,56}{40,268,56}{41,248,56}{42,12,72}{43,36,72}{44,56,72}{45,76,72}{46,96,72}{47,116,72}{48,136,72}{49,156,72}{50,176,72}{51,196,72}{52,216,72}{53,236,72}{54,280,72}{55,368,116}{57,136,88}{58,20,56}{59,48,4}{60,68,4}{61,88,4}{62,108,4}{63,132,4}{64,152,4}{65,172,4}{66,192,4}{67,216,4}{68,240,4}{69,296,116}{70,368,4}{71,296,132}{72,332,132}{73,368,132}{74,404,116}{75,296,148}{76,332,148}{77,368,148}{78,404,132}{79,296,164}{80,332,164}{81,368,164}{82,314,180}{83,368,180}{87,268,4}{88,296,4}{156,404,164}{157,252,88}{181,332,116}{183,332,4}{197,404,4}{199,368,24}{200,368,72}{201,404,24}{203,332,88}{205,404,88}{207,368,40}{208,368,88}{209,404,40}{210,332,24}{211,332,40}{219,56,88}{220,216,88}{248,92,116}{249,92,132}{250,26,108}{251,164,108}{252,168,152}{253,26,152}{254,26,136}";
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
/*     */ 
/*     */   
/*     */   protected void drawSpecial(bib minecraft, int mouseX, int mouseY) {
/*  32 */     this.renderer.setTexMapSize(256);
/*     */     
/*  34 */     int x = this.h + 35;
/*  35 */     int y = this.i + 108;
/*     */     
/*  37 */     int hoverId = (this.hovering != null) ? this.hovering.id : 0;
/*     */     
/*  39 */     minecraft.N().a(ResourceLocations.MOUSE_IMAGE);
/*     */     
/*  41 */     GL.glEnableAlphaTest();
/*  42 */     GL.glAlphaFunc(516, 0.1F);
/*  43 */     GL.glEnableBlend();
/*     */     
/*  45 */     if (hoverId > 0) {
/*     */       
/*  47 */       GL.glColor4f(1.0F, 1.0F, 0.25F, 0.5F);
/*     */       
/*  49 */       switch (hoverId) {
/*     */         
/*     */         case 250:
/*  52 */           drawOffsetRect(x + 28, y, 36, 0, 44, 46, 128);
/*     */           break;
/*     */         
/*     */         case 251:
/*  56 */           drawOffsetRect(x + 66, y, 90, 0, 32, 46, 128);
/*     */           break;
/*     */         
/*     */         case 252:
/*  60 */           drawOffsetRect(x + 62, y, 78, 0, 12, 46, 128);
/*     */           break;
/*     */         
/*     */         case 253:
/*  64 */           GL.glColor4f(1.0F, 1.0F, 0.2F, 0.75F);
/*  65 */           drawOffsetRect(x + 24, y + 32, 8, 64, 16, 32, 128);
/*     */           break;
/*     */         
/*     */         case 254:
/*  69 */           GL.glColor4f(1.0F, 1.0F, 0.2F, 0.75F);
/*  70 */           drawOffsetRect(x + 24, y + 16, 8, 0, 16, 32, 128);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/*  79 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  80 */     drawOffsetRect(x, y, 0, 0, 128, 64, 0);
/*  81 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
/*  82 */     if (hoverId == 253 || hoverId == 254) {
/*     */       
/*  84 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*  85 */       drawOffsetRect(x + 24, y + 16, 24, 32, 12, 64, 128);
/*     */     } 
/*     */     
/*  88 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  89 */     if (hoverId > 0)
/*     */     {
/*  91 */       switch (hoverId) {
/*     */         
/*     */         case 250:
/*  94 */           drawOffsetRect(x, y, 0, 128, 54, 27, 0);
/*     */           break;
/*     */         
/*     */         case 251:
/*  98 */           drawOffsetRect(x + 64, y, 64, 128, 64, 27, 0);
/*     */           break;
/*     */         
/*     */         case 252:
/* 102 */           drawOffsetRect(x + 64, y + 26, 64, 188, 64, 32, 0);
/*     */           break;
/*     */         
/*     */         case 253:
/* 106 */           drawOffsetRect(x, y + 43, 0, 214, 36, 16, 0);
/*     */           break;
/*     */         
/*     */         case 254:
/* 110 */           drawOffsetRect(x, y + 27, 0, 182, 36, 16, 0);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 118 */     GL.glAlphaFunc(516, 0.1F);
/* 119 */     GL.glDisableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawOffsetRect(int x, int y, int u, int v, int width, int height, int uoffset) {
/* 124 */     this.renderer.drawTexturedModalRect(x, y, x + width, y + height, u + uoffset, v, u + uoffset + width, v + height * 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\LayoutPanelKeys.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */