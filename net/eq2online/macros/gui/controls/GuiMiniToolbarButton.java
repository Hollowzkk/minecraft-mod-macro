/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiMiniToolbarButton
/*     */   extends GuiControlEx
/*     */ {
/*     */   protected int u;
/*     */   protected int v;
/*     */   protected int colour;
/*     */   protected int backColour;
/*     */   public boolean selected = false;
/*     */   private String tooltip;
/*     */   
/*     */   public GuiMiniToolbarButton(bib minecraft, int controlId, int u, int v) {
/*  22 */     super(minecraft, controlId, -100, -100, 18, 12, "");
/*  23 */     this.u = u;
/*  24 */     this.v = v;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMiniToolbarButton setColour(int colour) {
/*  29 */     this.colour = colour;
/*  30 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMiniToolbarButton setBackColour(int backColour) {
/*  35 */     this.backColour = backColour;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMiniToolbarButton setColours(int colour, int backColour) {
/*  41 */     this.colour = colour;
/*  42 */     this.backColour = backColour;
/*  43 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMiniToolbarButton setTooltip(String tooltip) {
/*  48 */     this.tooltip = tooltip;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTooltip() {
/*  54 */     return (this.tooltip != null) ? I18n.get(this.tooltip) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drawControlAt(bib minecraft, int mouseX, int mouseY, float partialTicks, int xPos, int yPos, boolean selected) {
/*  59 */     this.selected = selected;
/*  60 */     return drawControlAt(minecraft, mouseX, mouseY, partialTicks, xPos, yPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drawControlAt(bib minecraft, int mouseX, int mouseY, float partialTicks, int xPos, int yPos) {
/*  65 */     this.h = xPos;
/*  66 */     this.i = yPos;
/*  67 */     drawControl(minecraft, mouseX, mouseY, partialTicks);
/*  68 */     return b(minecraft, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/*  74 */     if (!this.m) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  79 */     int bg = this.backColour;
/*     */     
/*  81 */     if (this.selected) {
/*     */       
/*  83 */       a(this.h - 1, this.i - 1, this.h + this.f + 1, this.i + this.g + 1, this.colour);
/*  84 */       bg |= 0xFF000000;
/*     */     } 
/*     */     
/*  87 */     a(this.h, this.i, this.h + this.f, this.i + this.g, bg);
/*     */     
/*  89 */     minecraft.N().a(ResourceLocations.MAIN);
/*     */     
/*  91 */     if (b(minecraft, mouseX, mouseY)) {
/*     */       
/*  93 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  97 */       float red = (this.colour >> 16 & 0xFF) / 255.0F;
/*  98 */       float blue = (this.colour >> 8 & 0xFF) / 255.0F;
/*  99 */       float green = (this.colour & 0xFF) / 255.0F;
/* 100 */       float alpha = (this.colour >> 24 & 0xFF) / 255.0F;
/* 101 */       GL.glColor4f(red, blue, green, alpha);
/*     */     } 
/*     */     
/* 104 */     GL.glEnableAlphaTest();
/* 105 */     GL.glAlphaFunc(516, 0.01F);
/* 106 */     this.renderer.drawTexturedModalRect(this.h + 3, this.i + 2, this.h + this.f - 3, this.i + this.g - 2, this.u, this.v, this.u + 24, this.v + 16);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiMiniToolbarButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */