/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bib;
/*    */ import blk;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.gui.GuiDialogBox;
/*    */ 
/*    */ public class GuiDialogBoxBetaExpired
/*    */   extends GuiDialogBox
/*    */ {
/*    */   private final String version;
/*    */   
/*    */   public GuiDialogBoxBetaExpired(bib minecraft, blk parentScreen, String version) {
/* 15 */     super(minecraft, parentScreen, 340, 130, I18n.get("betaexpired.title"));
/* 16 */     this.version = version;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 22 */     this.btnCancel.m = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSubmit() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validateDialog() {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawDialog(int mouseX, int mouseY, float partialTicks) {
/* 43 */     this.rowPos = this.dialogY + 9;
/*    */     
/* 45 */     for (int i = 1; !I18n.get("betaexpired.line" + i).startsWith("betaexpired."); i++) {
/*    */       
/* 47 */       drawSpacedString(I18n.get("betaexpired.line" + i, new Object[] { this.version }), this.dialogX + 9, -22016);
/*    */     } 
/*    */     
/* 50 */     c(this.q, "v" + MacroModCore.getVersion(), this.dialogX + 10, this.dialogY + this.dialogHeight - 16, -10461088);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxBetaExpired.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */