/*    */ package net.eq2online.macros.gui.overlay;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.settings.Settings;
/*    */ import net.eq2online.macros.gui.GuiRendererMacros;
/*    */ 
/*    */ public abstract class Overlay
/*    */   extends GuiRendererMacros
/*    */   implements IOverlay {
/*    */   protected final Settings settings;
/*    */   protected final OverlayHandler handler;
/*    */   protected int width;
/*    */   protected int height;
/*    */   protected int scaleFactor;
/*    */   
/*    */   public Overlay(Macros macros, bib minecraft, OverlayHandler handler) {
/* 18 */     super(macros, minecraft);
/*    */     
/* 20 */     this.settings = macros.getSettings();
/* 21 */     this.handler = handler;
/* 22 */     this.e = 500.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setScreenSize(int width, int height, int scaleFactor) {
/* 28 */     this.width = width;
/* 29 */     this.height = height;
/* 30 */     this.scaleFactor = scaleFactor;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAlwaysRendered() {
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   public void onTick() {}
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\Overlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */