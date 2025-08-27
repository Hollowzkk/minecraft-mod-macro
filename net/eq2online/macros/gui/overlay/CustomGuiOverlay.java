/*    */ package net.eq2online.macros.gui.overlay;
/*    */ 
/*    */ import bib;
/*    */ import java.awt.Rectangle;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*    */ import net.eq2online.macros.gui.designable.LayoutManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomGuiOverlay
/*    */   extends Overlay
/*    */ {
/*    */   private Rectangle ingameGuiBoundingBox;
/* 17 */   private int updateCounter = 0;
/*    */ 
/*    */   
/*    */   public CustomGuiOverlay(Macros macros, bib minecraft, OverlayHandler handler) {
/* 21 */     super(macros, minecraft, handler);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setScreenSize(int width, int height, int scaleFactor) {
/* 27 */     super.setScreenSize(width, height, scaleFactor);
/* 28 */     this.ingameGuiBoundingBox = new Rectangle(0, 0, width, height);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 34 */     this.updateCounter++;
/* 35 */     super.onTick();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(int mouseX, int mouseY, long tick, float partialTick, boolean clock) {
/* 41 */     this.mc.B.c("custom");
/*    */ 
/*    */     
/* 44 */     String layoutName = this.mc.t.ah.e() ? "scoreboard" : (this.mc.t.ax ? "indebug" : "ingame");
/*    */ 
/*    */     
/* 47 */     LayoutManager.Binding ingameBinding = this.macros.getLayoutManager().getBinding(layoutName);
/*    */     
/* 49 */     if (ingameBinding != null && this.ingameGuiBoundingBox != null && this.mc.m == null && !this.mc.t.av) {
/*    */       
/* 51 */       DesignableGuiLayout guiIngame = ingameBinding.getLayout();
/* 52 */       if (clock)
/*    */       {
/* 54 */         guiIngame.onTick(this.updateCounter);
/*    */       }
/*    */       
/* 57 */       guiIngame.draw(ingameBinding.getBoundingBox(this.ingameGuiBoundingBox), 0, 0, false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\CustomGuiOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */