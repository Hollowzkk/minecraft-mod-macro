/*    */ package net.eq2online.macros.gui.controls;
/*    */ 
/*    */ import bib;
/*    */ import bip;
/*    */ import net.eq2online.macros.gui.GuiControl;
/*    */ 
/*    */ public class GuiLabel
/*    */   extends GuiControl
/*    */ {
/*    */   public int drawColour;
/*    */   
/*    */   public GuiLabel(int id, int xPosition, int yPosition, String displayText, int drawColour) {
/* 13 */     super(id, xPosition, yPosition, displayText);
/* 14 */     this.drawColour = drawColour;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 24 */     if (this.m) {
/*    */       
/* 26 */       bip fontrenderer = minecraft.k;
/* 27 */       c(fontrenderer, this.j, this.h, this.i, this.drawColour);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiLabel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */