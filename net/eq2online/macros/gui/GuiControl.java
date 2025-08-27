/*     */ package net.eq2online.macros.gui;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import bja;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
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
/*     */ public class GuiControl
/*     */   extends bja
/*     */ {
/*  25 */   protected static GuiRenderer sharedRenderer = new GuiRenderer(bib.z());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   protected static float texMapScale = 0.00390625F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object tag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public int iconIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int iconU;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int iconV;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControl(int id, int xPosition, int yPosition, String displayText) {
/*  58 */     super(id, xPosition, yPosition, displayText);
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
/*     */   public GuiControl(int id, int xPosition, int yPosition, String displayText, int iconIndex) {
/*  72 */     super(id, xPosition, yPosition, displayText);
/*     */     
/*  74 */     setIconIndex(iconIndex);
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
/*     */   public GuiControl(int id, int xPosition, int yPosition, int controlWidth, int controlHeight, String displayText) {
/*  89 */     super(id, xPosition, yPosition, controlWidth, controlHeight, displayText);
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
/*     */   public GuiControl(int id, int xPosition, int yPosition, int controlWidth, int controlHeight, String displayText, int iconIndex) {
/* 105 */     super(id, xPosition, yPosition, controlWidth, controlHeight, displayText);
/*     */     
/* 107 */     setIconIndex(iconIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIconIndex(int iconIndex) {
/* 116 */     this.iconIndex = iconIndex;
/*     */     
/* 118 */     if (iconIndex > -1) {
/*     */       
/* 120 */       this.iconU = 80 + iconIndex % 4 * 12;
/* 121 */       this.iconV = iconIndex / 4 * 12;
/*     */     }
/*     */     else {
/*     */       
/* 125 */       this.iconU = 0;
/* 126 */       this.iconV = 0;
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
/*     */   protected void drawIcon(nf texture, Icon icon, int x, int y, int x2, int y2) {
/* 139 */     sharedRenderer.drawIcon(texture, icon, x, y, x2, y2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getWidth() {
/* 144 */     return this.f;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getHeight() {
/* 149 */     return this.g;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getXPosition() {
/* 154 */     return this.h;
/*     */   }
/*     */ 
/*     */   
/*     */   public final GuiControl setXPosition(int newXPosition) {
/* 159 */     this.h = newXPosition;
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getYPosition() {
/* 165 */     return this.i;
/*     */   }
/*     */ 
/*     */   
/*     */   public final GuiControl setYPosition(int newYPosition) {
/* 170 */     this.i = newYPosition;
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiControl setPosition(int newXPosition, int newYPosition) {
/* 176 */     this.h = newXPosition;
/* 177 */     this.i = newYPosition;
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isEnabled() {
/* 183 */     return this.l;
/*     */   }
/*     */ 
/*     */   
/*     */   public final GuiControl setEnabled(boolean newEnabled) {
/* 188 */     this.l = newEnabled;
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isVisible() {
/* 194 */     return this.m;
/*     */   }
/*     */ 
/*     */   
/*     */   public final GuiControl setVisible(boolean newVisible) {
/* 199 */     this.m = newVisible;
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 210 */     if (!this.m) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 215 */     bip fontrenderer = minecraft.k;
/*     */     
/* 217 */     minecraft.N().a(ResourceLocations.MAIN);
/* 218 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 220 */     int xPos = this.h;
/* 221 */     boolean mouseOver = (mouseX >= xPos && mouseY >= this.i && mouseX < xPos + this.f && mouseY < this.i + this.g);
/* 222 */     int hoverState = a(mouseOver);
/* 223 */     sharedRenderer.drawTexturedModalRect(xPos, this.i, 0, 64 + hoverState * 20, this.f / 2, this.g, 0.0078125F);
/* 224 */     sharedRenderer.drawTexturedModalRect(xPos + this.f / 2, this.i, 128 - this.f / 2, 64 + hoverState * 20, this.f / 2, this.g, 0.0078125F);
/*     */     
/* 226 */     a(minecraft, mouseX, mouseY);
/*     */     
/* 228 */     int innerWidth = this.f;
/*     */     
/* 230 */     if (this.iconIndex > -1) {
/*     */       
/* 232 */       GL.glEnableBlend();
/* 233 */       sharedRenderer.drawTexturedModalRect(xPos + 5, this.i + 4, this.iconU, this.iconV, 12, 12, 0.0078125F);
/* 234 */       GL.glDisableBlend();
/* 235 */       xPos += 16;
/* 236 */       innerWidth -= 16;
/*     */     } 
/*     */     
/* 239 */     int textColour = this.l ? (mouseOver ? 11206655 : 14737632) : -6250336;
/* 240 */     a(fontrenderer, this.j, xPos + innerWidth / 2, this.i + (this.g - 8) / 2, textColour);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean rightClicked(bib minecraft, int mouseX, int mouseY, boolean buttonState) {
/* 245 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(bib minecraft, int mouseX, int mouseY) {
/* 250 */     return (this.l && this.m && mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\GuiControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */