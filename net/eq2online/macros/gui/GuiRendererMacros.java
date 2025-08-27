/*    */ package net.eq2online.macros.gui;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ 
/*    */ public abstract class GuiRendererMacros
/*    */   extends GuiRenderer
/*    */ {
/*    */   protected final Macros macros;
/*    */   
/*    */   public GuiRendererMacros(Macros macros, bib minecraft) {
/* 12 */     super(minecraft);
/* 13 */     this.macros = macros;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\GuiRendererMacros.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */