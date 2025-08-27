/*    */ package net.eq2online.macros.gui.ext;
/*    */ 
/*    */ import bib;
/*    */ import bja;
/*    */ import bjm;
/*    */ import blk;
/*    */ import bme;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.screens.GuiMacroBind;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BindingButtonEntry
/*    */   implements bjm.a
/*    */ {
/*    */   private bme controlsGui;
/*    */   private final Macros macros;
/*    */   private final bib mc;
/*    */   private final bja bindingButton;
/*    */   
/*    */   public BindingButtonEntry(Macros macros, bib minecraft, bme controlsGui) {
/* 23 */     this.macros = macros;
/* 24 */     this.mc = minecraft;
/* 25 */     this.controlsGui = controlsGui;
/*    */     
/* 27 */     this.bindingButton = new bja(0, 0, 0, 110, 18, I18n.get("gui.bindings.goto"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(int id, int xPosition, int yPosition, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTicks) {
/* 33 */     this.bindingButton.h = xPosition + 105;
/* 34 */     this.bindingButton.i = yPosition;
/*    */     
/* 36 */     this.bindingButton.a(this.mc, mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a(int id, int mouseX, int mouseY, int button, int relX, int relY) {
/* 42 */     if (this.bindingButton.b(this.mc, mouseX, mouseY)) {
/*    */       
/* 44 */       this.mc.a((blk)new GuiMacroBind(this.macros, this.mc, true, false, (this.mc.f == null) ? (blk)this.controlsGui : null));
/* 45 */       return true;
/*    */     } 
/*    */     
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void b(int id, int mouseX, int mouseY, int button, int width, int height) {
/* 54 */     this.bindingButton.a(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public void a(int entryID, int insideLeft, int yPos, float partialTicks) {}
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\ext\BindingButtonEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */