/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bib;
/*     */ import bje;
/*     */ import blk;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
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
/*     */ 
/*     */ 
/*     */ public class GuiDialogBoxRenameItem
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private bje txtNewItemName;
/*     */   private String originalName;
/*     */   private String newItemName;
/*     */   private String allowedcharacters;
/*     */   public File file;
/*     */   public DesignableGuiLayout layout;
/*     */   
/*     */   public GuiDialogBoxRenameItem(bib minecraft, GuiScreenEx parentScreen, File file) {
/*  38 */     super(minecraft, (blk)parentScreen, 320, 100, I18n.get("editor.rename.file"));
/*     */     
/*  40 */     this.file = file;
/*  41 */     this.prompt = I18n.get("editor.rename.file.prompt");
/*  42 */     this.originalName = this.file.getName();
/*  43 */     this.allowedcharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDialogBoxRenameItem(bib minecraft, GuiScreenEx parentScreen, DesignableGuiLayout layout) {
/*  48 */     super(minecraft, (blk)parentScreen, 320, 100, I18n.get("editor.rename.gui"));
/*     */     
/*  50 */     this.layout = layout;
/*  51 */     this.prompt = I18n.get("editor.rename.gui.prompt", new Object[] { layout.getName() });
/*  52 */     this.originalName = this.layout.getDisplayName();
/*  53 */     this.allowedcharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  60 */     this.txtNewItemName.a();
/*     */     
/*  62 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/*  69 */     this.newItemName = this.txtNewItemName.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  75 */     this.txtNewItemName.a(mouseX, mouseY, button);
/*  76 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/*  82 */     this.txtNewItemName = new bje(0, this.q, this.dialogX + 10, this.dialogY + 36, this.dialogWidth - 20, 16);
/*  83 */     this.txtNewItemName.f(255);
/*  84 */     this.txtNewItemName.a(this.originalName);
/*  85 */     this.txtNewItemName.b(true);
/*  86 */     this.txtNewItemName.d(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/*  92 */     if (keyCode == 14 || keyCode == 211 || keyCode == 199 || keyCode == 207 || keyCode == 203 || keyCode == 205 || this.allowedcharacters
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       .indexOf(keyChar) >= 0)
/*     */     {
/* 100 */       this.txtNewItemName.a(keyChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 108 */     return (this.txtNewItemName.b().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 115 */     c(this.q, this.prompt, this.dialogX + 10, this.dialogY + 16, -22016);
/* 116 */     this.txtNewItemName.g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewItemName() {
/* 124 */     return this.newItemName;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxRenameItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */