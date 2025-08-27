/*     */ package net.eq2online.macros.gui;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiDialogBoxBrowse<TItem>
/*     */   extends GuiDialogBox
/*     */ {
/*     */   protected final GuiListBox<TItem> listBox;
/*     */   
/*     */   public GuiDialogBoxBrowse(bib minecraft, blk parentScreen, int width, int height, String windowTitle) {
/*  17 */     super(minecraft, parentScreen, width, height, windowTitle);
/*  18 */     this.listBox = new GuiListBox(this.j, 1, this.dialogX + 4, this.dialogY + 4, width - 8, height - 29, 20, true, false, false);
/*  19 */     this.movable = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/*  25 */     IListEntry<TItem> selectedItem = this.listBox.getSelectedItem();
/*  26 */     return (selectedItem != null) ? selectedItem.getData() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initDialog() {
/*  32 */     this.listBox.setSizeAndPosition(this.dialogX + 4, this.dialogY + 4, this.dialogWidth - 8, this.dialogHeight - 29);
/*  33 */     addControl((GuiControl)this.listBox);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiControl control) {
/*  39 */     if (control == this.listBox)
/*     */     {
/*  41 */       if (this.listBox.isDoubleClicked(true))
/*     */       {
/*  43 */         dialogSubmit();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/*  51 */     GuiControlEx.HandledState action = this.listBox.listBoxKeyTyped(keyChar, keyCode);
/*  52 */     if (action == GuiControlEx.HandledState.ACTION_PERFORMED) {
/*     */       
/*  54 */       actionPerformed((GuiControl)this.listBox);
/*     */     }
/*  56 */     else if (action == GuiControlEx.HandledState.HANDLED) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  61 */     super.dialogKeyTyped(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  67 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {
/*  89 */     mouseWheelDelta /= 120;
/*     */     
/*  91 */     while (mouseWheelDelta > 0) {
/*     */       
/*  93 */       this.listBox.up();
/*  94 */       actionPerformed((GuiControl)this.listBox);
/*  95 */       mouseWheelDelta--;
/*     */     } 
/*     */     
/*  98 */     while (mouseWheelDelta < 0) {
/*     */       
/* 100 */       this.listBox.down();
/* 101 */       actionPerformed((GuiControl)this.listBox);
/* 102 */       mouseWheelDelta++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\GuiDialogBoxBrowse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */