/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiControl;
/*    */ import net.eq2online.macros.gui.GuiDialogBox;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ 
/*    */ public class GuiDialogBoxYesNoCancel
/*    */   extends GuiDialogBoxConfirm<Object>
/*    */ {
/*    */   protected GuiControl btnNo;
/*    */   
/*    */   public GuiDialogBoxYesNoCancel(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String line1, String line2, int id, Object metaData) {
/* 15 */     super(minecraft, parentScreen, windowTitle, line1, line2, id, metaData);
/*    */   }
/*    */ 
/*    */   
/*    */   public GuiDialogBoxYesNoCancel(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String line1, String line2, int id) {
/* 20 */     super(minecraft, parentScreen, windowTitle, line1, line2, id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 29 */     this.btnCancel.setXPosition(this.dialogX + 2);
/* 30 */     this.btnNo = new GuiControl(-3, this.dialogX + this.dialogWidth - 124, this.dialogY + this.dialogHeight - 22, 60, 20, I18n.get("gui.no"));
/* 31 */     addControl(this.btnNo);
/* 32 */     this.btnOk.j = I18n.get("gui.yes");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiControl control) {
/* 42 */     if (control.k == this.btnOk.k) {
/*    */       
/* 44 */       this.dialogResult = GuiDialogBox.DialogResult.YES;
/* 45 */       closeDialog();
/*    */     }
/* 47 */     else if (control.k == this.btnNo.k) {
/*    */       
/* 49 */       this.dialogResult = GuiDialogBox.DialogResult.NO;
/* 50 */       closeDialog();
/*    */     }
/* 52 */     else if (control.k == this.btnCancel.k) {
/*    */       
/* 54 */       this.dialogResult = GuiDialogBox.DialogResult.CANCEL;
/* 55 */       closeDialog();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 66 */     if (keyChar == 'y' || keyChar == 'Y') actionPerformed(this.btnOk); 
/* 67 */     if (keyChar == 'n' || keyChar == 'N') actionPerformed(this.btnNo); 
/* 68 */     if (keyChar == 'c' || keyChar == 'C') actionPerformed(this.btnCancel); 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxYesNoCancel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */