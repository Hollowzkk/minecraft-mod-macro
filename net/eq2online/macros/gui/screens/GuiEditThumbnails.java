/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.controls.GuiScrollBar;
/*     */ import net.eq2online.macros.gui.thumbnail.ThumbnailHandler;
/*     */ import net.eq2online.macros.res.ResourceLocations;
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
/*     */ public class GuiEditThumbnails
/*     */   extends GuiScreenWithHeader
/*     */ {
/*     */   private final blk parentScreen;
/*     */   private final ThumbnailHandler thumbnailHandler;
/*  35 */   private int iconSpacing = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private int totalHeight = 320;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiScrollBar scrollBar;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private static int lastScrollBarValue = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiEditThumbnails(Macros macros, bib minecraft, blk oldScreen, ThumbnailHandler thumbnailHandler) {
/*  60 */     super(minecraft, 0, 0);
/*     */     
/*  62 */     this.parentScreen = oldScreen;
/*  63 */     this.thumbnailHandler = thumbnailHandler;
/*     */     
/*  65 */     this.title = I18n.get("macro.icons.title");
/*  66 */     this.banner = I18n.get("macro.icons.help");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  76 */     int columnWidth = (this.l - 44) / 2;
/*  77 */     int iconsPerColumn = columnWidth / (16 + this.iconSpacing);
/*     */     
/*  79 */     if (iconsPerColumn > 0) {
/*     */       
/*  81 */       this.totalHeight = (256 / iconsPerColumn + 2) * (16 + this.iconSpacing) + 10;
/*     */     }
/*     */     else {
/*     */       
/*  85 */       this.totalHeight = this.m;
/*     */     } 
/*     */     
/*  88 */     this
/*  89 */       .scrollBar = new GuiScrollBar(this.j, 0, this.l - 24, 43, 20, this.m - 50, 0, Math.max(0, this.totalHeight - this.m - 50), GuiScrollBar.ScrollBarOrientation.VERTICAL);
/*  90 */     this.scrollBar.setValue(lastScrollBarValue);
/*  91 */     addControl((GuiControl)this.scrollBar);
/*     */     
/*  93 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/*  99 */     super.a(mouseX, mouseY, f);
/*     */     
/* 101 */     GL.glDisableLighting();
/* 102 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 104 */     int columnWidth = (this.l - 44) / 2;
/*     */     
/* 106 */     c(this.q, I18n.get("macro.icons.town"), 14, 29, -1);
/* 107 */     c(this.q, I18n.get("macro.icons.homes"), 14 + columnWidth + 10, 29, -1);
/*     */     
/* 109 */     GLClippingPlanes.glEnableVerticalClipping(44, this.m - 7);
/*     */     
/* 111 */     mouseY += this.scrollBar.getValue();
/* 112 */     lastScrollBarValue = this.scrollBar.getValue();
/*     */     
/* 114 */     GL.glPushMatrix();
/* 115 */     GL.glTranslatef(0.0F, -this.scrollBar.getValue(), 0.0F);
/*     */     
/* 117 */     int mouseOverIndex1 = drawIcons(ResourceLocations.DYNAMIC_TOWNS, 10, 44, columnWidth, mouseX, mouseY);
/* 118 */     int mouseOverIndex2 = drawIcons(ResourceLocations.DYNAMIC_HOMES, 10 + columnWidth + 10, 44, columnWidth, mouseX, mouseY);
/*     */     
/* 120 */     GLClippingPlanes.glDisableClipping();
/*     */ 
/*     */     
/* 123 */     if (mouseOverIndex1 > -1 && mouseY > 44)
/*     */     {
/* 125 */       drawTooltip("Icon " + (mouseOverIndex1 + 1), mouseX, mouseY, 16777215, -805306368);
/*     */     }
/*     */     
/* 128 */     if (mouseOverIndex2 > -1 && mouseY > 44)
/*     */     {
/* 130 */       drawTooltip("Icon " + (mouseOverIndex2 + 1), mouseX, mouseY, 16777215, -805306368);
/*     */     }
/*     */     
/* 133 */     GL.glPopMatrix();
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
/*     */   protected int drawIcons(nf texture, int left, int top, int columnWidth, int mouseX, int mouseY) {
/* 148 */     this.j.N().a(texture);
/*     */     
/* 150 */     int xPosition = left, yPosition = top;
/* 151 */     int mouseOverIndex = -1;
/*     */     
/* 153 */     for (int iconIndex = 0; iconIndex < 256; iconIndex++) {
/*     */       
/* 155 */       int u = iconIndex % 16 * 16;
/* 156 */       int v = iconIndex / 16 * 16;
/*     */       
/* 158 */       if (mouseX > xPosition - this.iconSpacing && mouseY > yPosition - this.iconSpacing && mouseX < xPosition + 16 && mouseY < yPosition + 16) {
/*     */         
/* 160 */         a(xPosition - 2, yPosition - 2, xPosition + 16 + 2, yPosition + 16 + 2, -1);
/* 161 */         mouseOverIndex = iconIndex;
/*     */       } 
/*     */       
/* 164 */       this.renderer.drawTexturedModalRect(xPosition, yPosition, xPosition + 16, yPosition + 16, u, v, u + 16, v + 16);
/*     */       
/* 166 */       xPosition += 16 + this.iconSpacing;
/*     */       
/* 168 */       if (xPosition > left + columnWidth - 20) {
/*     */         
/* 170 */         xPosition = left;
/* 171 */         yPosition += 17 + this.iconSpacing;
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     return mouseOverIndex;
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
/*     */   protected int getMouseOverIndex(int left, int top, int columnWidth, int mouseX, int mouseY) {
/* 191 */     int xPosition = left, yPosition = top;
/*     */     
/* 193 */     for (int iconIndex = 0; iconIndex < 256; iconIndex++) {
/*     */       
/* 195 */       if (mouseX > xPosition - this.iconSpacing && mouseY > yPosition - this.iconSpacing && mouseX < xPosition + 16 && mouseY < yPosition + 16)
/*     */       {
/* 197 */         return iconIndex;
/*     */       }
/*     */       
/* 200 */       xPosition += 16 + this.iconSpacing;
/*     */       
/* 202 */       if (xPosition > left + columnWidth - 20) {
/*     */         
/* 204 */         xPosition = left;
/* 205 */         yPosition += 17 + this.iconSpacing;
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickedEx(int mouseX, int mouseY, int button) {
/* 215 */     if (button == 0 && mouseY > 44) {
/*     */       
/* 217 */       mouseY += this.scrollBar.getValue();
/*     */       
/* 219 */       int columnWidth = (this.l - 44) / 2;
/* 220 */       int mouseOverTownsIndex = getMouseOverIndex(10, 44, columnWidth, mouseX, mouseY);
/*     */       
/* 222 */       if (mouseOverTownsIndex > -1) {
/*     */         
/* 224 */         this.thumbnailHandler.beginThumbnailCapture(this, ResourceLocations.TOWNS, mouseOverTownsIndex);
/*     */       }
/*     */       else {
/*     */         
/* 228 */         int mouseOverHomesIndex = getMouseOverIndex(10 + columnWidth + 10, 44, columnWidth, mouseX, mouseY);
/*     */         
/* 230 */         if (mouseOverHomesIndex > -1)
/*     */         {
/* 232 */           this.thumbnailHandler.beginThumbnailCapture(this, ResourceLocations.HOMES, mouseOverHomesIndex);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/* 244 */     mouseWheelDelta /= 3;
/* 245 */     this.scrollBar.setValue(this.scrollBar.getValue() - mouseWheelDelta);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 251 */     if (keyCode == 1)
/*     */     {
/* 253 */       this.j.a(this.parentScreen);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 263 */     this.j.a(this.parentScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiEditThumbnails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */