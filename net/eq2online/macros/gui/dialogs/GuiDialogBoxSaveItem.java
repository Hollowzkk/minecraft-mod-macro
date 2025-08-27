/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bib;
/*     */ import bje;
/*     */ import blk;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
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
/*     */ public class GuiDialogBoxSaveItem
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private bje txtFileName;
/*     */   private String itemName;
/*     */   private String allowedcharacters;
/*     */   public DesignableGuiLayout layout;
/*     */   
/*     */   public GuiDialogBoxSaveItem(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String prompt) {
/*  32 */     super(minecraft, (blk)parentScreen, 320, 100, windowTitle);
/*     */     
/*  34 */     this.prompt = prompt;
/*  35 */     this.allowedcharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  41 */     this.txtFileName.a();
/*  42 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/*  49 */     this.itemName = this.txtFileName.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  55 */     this.txtFileName.a(mouseX, mouseY, button);
/*  56 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/*  62 */     this.txtFileName = new bje(0, this.q, this.dialogX + 10, this.dialogY + 36, this.dialogWidth - 20, 16);
/*  63 */     this.txtFileName.f(255);
/*  64 */     this.txtFileName.b(true);
/*  65 */     this.txtFileName.d(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/*  71 */     if (keyCode == 14 || keyCode == 211 || keyCode == 199 || keyCode == 207 || keyCode == 203 || keyCode == 205 || this.allowedcharacters
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       .indexOf(keyChar) >= 0)
/*     */     {
/*  79 */       this.txtFileName.a(keyChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/*  87 */     return (this.txtFileName.b().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/*  94 */     c(this.q, this.prompt, this.dialogX + 10, this.dialogY + 16, -22016);
/*  95 */     this.txtFileName.g();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 101 */     return this.itemName;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxSaveItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */