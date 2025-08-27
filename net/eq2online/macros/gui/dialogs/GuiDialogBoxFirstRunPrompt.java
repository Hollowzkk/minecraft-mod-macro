/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bib;
/*    */ import ble;
/*    */ import blk;
/*    */ import bme;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.core.handler.ScreenTransformHandler;
/*    */ import net.eq2online.macros.gui.GuiDialogBox;
/*    */ 
/*    */ public class GuiDialogBoxFirstRunPrompt
/*    */   extends GuiDialogBox
/*    */ {
/*    */   private final ScreenTransformHandler handler;
/*    */   
/*    */   public GuiDialogBoxFirstRunPrompt(bib minecraft, blk parentScreen, ScreenTransformHandler handler) {
/* 18 */     super(minecraft, parentScreen, 340, 150, I18n.get("firstrun.title"));
/* 19 */     this.handler = handler;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 25 */     this.btnOk.j = I18n.get("firstrun.yes");
/* 26 */     this.btnCancel.j = I18n.get("firstrun.no");
/*    */     
/* 28 */     this.btnCancel.setXPosition(this.dialogX + this.dialogWidth - 62);
/* 29 */     this.btnOk.setXPosition(this.dialogX + this.dialogWidth - 186);
/* 30 */     this.btnOk.a(120);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSubmit() {
/* 36 */     this.handler.enableScrollToButtons();
/* 37 */     setParent((blk)new bme((blk)new ble(this.parent.getDelegate(), this.j.t), this.j.t));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validateDialog() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 52 */     this.rowPos = this.dialogY + 9;
/*    */     
/* 54 */     for (int i = 1; !I18n.get("firstrun.line" + i).startsWith("firstrun."); i++)
/*    */     {
/* 56 */       drawSpacedString(I18n.get("firstrun.line" + i), this.dialogX + 9, -22016);
/*    */     }
/*    */     
/* 59 */     c(this.q, "v" + MacroModCore.getVersion(), this.dialogX + 10, this.dialogY + this.dialogHeight - 16, -10461088);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxFirstRunPrompt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */