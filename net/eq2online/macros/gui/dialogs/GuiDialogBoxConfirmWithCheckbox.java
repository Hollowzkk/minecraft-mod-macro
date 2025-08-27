/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.gui.GuiControl;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GuiDialogBoxConfirmWithCheckbox
/*    */   extends GuiDialogBoxConfirm<Object>
/*    */ {
/*    */   private final String checkboxText;
/*    */   private GuiCheckBox checkbox;
/*    */   
/*    */   public GuiDialogBoxConfirmWithCheckbox(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String line1, String line2, String checkboxText, int id, Object metaData) {
/* 18 */     super(minecraft, parentScreen, windowTitle, line1, line2, id, metaData);
/*    */     
/* 20 */     this.checkboxText = checkboxText;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiDialogBoxConfirmWithCheckbox(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String line1, String line2, String checkboxText, int id) {
/* 26 */     super(minecraft, parentScreen, windowTitle, line1, line2, id);
/*    */     
/* 28 */     this.checkboxText = checkboxText;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 37 */     super.initDialog();
/*    */     
/* 39 */     this.checkbox = new GuiCheckBox(this.j, -4, this.dialogX + 8, this.dialogY + this.dialogHeight - 22, this.checkboxText, false);
/* 40 */     addControl((GuiControl)this.checkbox);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getChecked() {
/* 45 */     return this.checkbox.checked;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 54 */     if (keyChar == 'r' || keyChar == 'R' || keyCode == 57)
/*    */     {
/* 56 */       actionPerformed(this.btnOk);
/*    */     }
/*    */     
/* 59 */     super.dialogKeyTyped(keyChar, keyCode);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxConfirmWithCheckbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */