/*     */ package net.eq2online.macros.gui;
/*     */ 
/*     */ import bib;
/*     */ import java.nio.DoubleBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiControlEx
/*     */   extends GuiControl
/*     */ {
/*     */   public int updateCounter;
/*     */   protected bib mc;
/*     */   protected boolean actionPerformed;
/*     */   protected boolean doubleClicked;
/*     */   protected DoubleBuffer doubleBuffer;
/*     */   protected final GuiRenderer renderer;
/*     */   
/*     */   public enum HandledState
/*     */   {
/*  25 */     NONE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     HANDLED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     ACTION_PERFORMED;
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
/*     */   public GuiControlEx(bib minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, String displayText) {
/*  81 */     super(controlId, xPos, yPos, controlWidth, controlHeight, displayText);
/*  82 */     this.mc = minecraft;
/*  83 */     this.renderer = new GuiRenderer(minecraft);
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
/*     */   public final void a(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/*  97 */     drawControl(minecraft, mouseX, mouseY, partialTicks);
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
/*     */   protected abstract void drawControl(bib parambib, int paramInt1, int paramInt2, float paramFloat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActionPerformed() {
/* 119 */     return this.actionPerformed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDoubleClicked(boolean resetDoubleClicked) {
/* 129 */     boolean result = this.doubleClicked;
/* 130 */     if (resetDoubleClicked) this.doubleClicked = false; 
/* 131 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\GuiControlEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */