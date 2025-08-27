/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.gui.GuiRendererMacros;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import rp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiPlaybackStatus
/*     */   extends GuiRendererMacros
/*     */ {
/*     */   private static final int LINE_HEIGHT = 10;
/*     */   private static final int INDENT = 12;
/*     */   private final SpamFilter spamFilter;
/*     */   private boolean overlayMode = true;
/*     */   
/*     */   public GuiPlaybackStatus(Macros macros, bib mc, SpamFilter spamFilter) {
/*  26 */     super(macros, mc);
/*  27 */     this.spamFilter = spamFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverlayMode(boolean overlayMode) {
/*  32 */     this.overlayMode = overlayMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOverlayMode() {
/*  37 */     return this.overlayMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawOverlay(Rectangle boundingBox, int mouseX, int mouseY, boolean interactive) {
/*  45 */     if (this.overlayMode)
/*     */     {
/*  47 */       draw(boundingBox, mouseX, mouseY, interactive);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(Rectangle boundingBox, int mouseX, int mouseY, boolean interactive) {
/*  53 */     int line = 0;
/*     */     
/*  55 */     if (this.spamFilter != null)
/*     */     {
/*  57 */       line = drawQueueStatus(boundingBox, mouseX, mouseY, interactive);
/*     */     }
/*     */     
/*  60 */     List<IMacro.IMacroStatus> status = this.macros.getExecutingMacroStatus();
/*  61 */     int remaining = status.size() - 1;
/*  62 */     for (IMacro.IMacroStatus macroStatus : status) {
/*     */       
/*  64 */       int top = boundingBox.y + line++ * 10;
/*  65 */       if ((top + 20 - 2) > boundingBox.getMaxY() && remaining > 0) {
/*     */         
/*  67 */         this.fontRenderer.a(I18n.get("playback.more", new Object[] { Integer.valueOf(remaining + 1) }), (boundingBox.x + (interactive ? 14 : 0)), top, -1);
/*     */         
/*     */         break;
/*     */       } 
/*  71 */       drawStatusLine(boundingBox, top, macroStatus.toString(), mouseX, mouseY, interactive);
/*  72 */       remaining--;
/*     */     } 
/*     */     
/*  75 */     if (line == 0 && !this.overlayMode && interactive) {
/*     */       
/*  77 */       String text = I18n.get("playback.idle");
/*  78 */       this.fontRenderer.a(text, boundingBox.x, boundingBox.y, -8355712);
/*     */     } 
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
/*     */   
/*     */   private int drawQueueStatus(Rectangle boundingBox, int mouseX, int mouseY, boolean interactive) {
/*  95 */     if (this.spamFilter.getHasQueue()) {
/*     */       
/*  97 */       drawStatusLine(boundingBox, boundingBox.y, this.spamFilter.getQueueStatusText(), mouseX, mouseY, interactive);
/*  98 */       return 1;
/*     */     } 
/*     */     
/* 101 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawStatusLine(Rectangle boundingBox, int top, String text, int mouseX, int mouseY, boolean interactive) {
/* 106 */     boolean mouseOver = (mouseX > boundingBox.x && mouseX < boundingBox.x + 12 && mouseY > top - 1 && mouseY < top + 9);
/* 107 */     int textLength = this.fontRenderer.a(text);
/*     */     
/* 109 */     if (interactive) {
/*     */       
/* 111 */       GL.glPushAttrib(1048575);
/*     */       
/* 113 */       if (mouseOver) {
/*     */         
/* 115 */         a(boundingBox.x - 1, top - 3, boundingBox.x + 12 + textLength + 6, top + 11, 1082654720);
/*     */         
/* 117 */         GL.glEnableColorLogic();
/* 118 */         GL.glLogicOp(5388);
/*     */       } 
/*     */       
/* 121 */       this.mc.N().a(ResourceLocations.MAIN);
/* 122 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 124 */       drawTexturedModalRectEx(boundingBox.x, top - 2, boundingBox.x + 12, top + 10, 12, 26, 18, 32, 0);
/*     */       
/* 126 */       GL.glPopAttrib();
/*     */     } 
/*     */     
/* 129 */     if (mouseOver)
/*     */     {
/* 131 */       text = rp.a(text);
/*     */     }
/*     */     
/* 134 */     this.fontRenderer.a(text, (boundingBox.x + (interactive ? 14 : 0)), top, -7798785);
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
/*     */   public boolean mouseClick(Rectangle boundingBox, int mouseX, int mouseY) {
/* 146 */     int offset = 0;
/* 147 */     if (this.spamFilter != null && this.spamFilter.getHasQueue()) {
/*     */       
/* 149 */       if (this.spamFilter.mousePressed(boundingBox.x, boundingBox.y, mouseX, mouseY))
/*     */       {
/* 151 */         return true;
/*     */       }
/* 153 */       offset = -1;
/*     */     } 
/*     */     
/* 156 */     if (mouseX < boundingBox.x || mouseX > boundingBox.x + 12)
/*     */     {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     int index = offset + (mouseY - boundingBox.y) / 10;
/* 162 */     return this.macros.removeRunningMacro(index);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\GuiPlaybackStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */