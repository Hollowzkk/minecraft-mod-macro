/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiDialogBoxConfirm<T>
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private final int id;
/*     */   private String messageText1;
/*     */   private String messageText2;
/*     */   private T metaData;
/*     */   
/*     */   public GuiDialogBoxConfirm(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String line1, String line2, int id, T metaData) {
/*  42 */     this(minecraft, parentScreen, windowTitle, line1, line2, id);
/*  43 */     this.metaData = metaData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxConfirm(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String line1, String line2, int id) {
/*  56 */     super(minecraft, (blk)parentScreen, 320, 80, windowTitle);
/*     */ 
/*     */     
/*  59 */     this.messageText1 = line1;
/*  60 */     this.messageText2 = line2;
/*  61 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  67 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getMetaData() {
/*  77 */     return this.metaData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initDialog() {
/*  83 */     this.btnOk.j = I18n.get("gui.yes");
/*  84 */     this.btnCancel.j = I18n.get("gui.no");
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
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 102 */     a(this.q, this.messageText1, this.dialogX + this.dialogWidth / 2, this.dialogY + 18, -22016);
/* 103 */     a(this.q, this.messageText2, this.dialogX + this.dialogWidth / 2, this.dialogY + 32, -22016);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 112 */     if (keyChar == 'y' || keyChar == 'Y') a((bja)this.btnOk); 
/* 113 */     if (keyChar == 'n' || keyChar == 'N') a((bja)this.btnCancel); 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxConfirm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */