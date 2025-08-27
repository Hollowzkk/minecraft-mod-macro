/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bib;
/*    */ import blk;
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.gui.GuiDialogBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiDialogBoxErrorList
/*    */   extends GuiDialogBox
/*    */ {
/*    */   protected String[] errorList;
/*    */   protected String line1;
/*    */   
/*    */   public GuiDialogBoxErrorList(bib minecraft, blk parentScreen, List<String> errorList, String line, String windowTitle) {
/* 34 */     super(minecraft, parentScreen, 370, 55 + errorList.size() * 10, windowTitle);
/*    */     
/* 36 */     this.errorList = errorList.<String>toArray(new String[0]);
/* 37 */     this.line1 = line;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 46 */     this.btnCancel.setVisible(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSubmit() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validateDialog() {
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 70 */     this.rowPos = this.dialogY + 9;
/* 71 */     drawSpacedString(this.line1, this.dialogX + 9, -22016);
/*    */     
/* 73 */     for (int i = 0; i < this.errorList.length; i++)
/*    */     {
/* 75 */       drawSpacedString(this.errorList[i], this.dialogX + 15, -43691);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxErrorList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */