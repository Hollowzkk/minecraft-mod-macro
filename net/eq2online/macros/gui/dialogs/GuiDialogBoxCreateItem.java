/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bib;
/*     */ import bje;
/*     */ import blk;
/*     */ import java.io.IOException;
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
/*     */ public class GuiDialogBoxCreateItem
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private bje txtNewItemName;
/*     */   private bje txtNewItemDisplayName;
/*  24 */   private String newItemName = "", newItemDisplayName = "";
/*     */   
/*  26 */   private String allowedcharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */ 
/*     */   
/*     */   public GuiDialogBoxCreateItem(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String prompt) {
/*  30 */     super(minecraft, (blk)parentScreen, 320, 110, windowTitle);
/*  31 */     this.prompt = prompt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  37 */     this.txtNewItemName.a();
/*  38 */     this.txtNewItemDisplayName.a();
/*     */     
/*  40 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/*  46 */     this.newItemName = this.txtNewItemName.b();
/*  47 */     this.newItemDisplayName = this.txtNewItemDisplayName.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  53 */     this.txtNewItemName.a(mouseX, mouseY, button);
/*  54 */     this.txtNewItemDisplayName.a(mouseX, mouseY, button);
/*  55 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/*  61 */     this.txtNewItemName = new bje(0, this.q, this.dialogX + 80, this.dialogY + 30, this.dialogWidth - 90, 16);
/*  62 */     this.txtNewItemName.f(255);
/*  63 */     this.txtNewItemName.a(this.newItemName);
/*  64 */     this.txtNewItemName.b(true);
/*     */     
/*  66 */     this.txtNewItemDisplayName = new bje(1, this.q, this.dialogX + 80, this.dialogY + 54, this.dialogWidth - 90, 16);
/*  67 */     this.txtNewItemDisplayName.f(255);
/*  68 */     this.txtNewItemDisplayName.a(this.newItemDisplayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/*  74 */     if (keyCode == 14 || keyCode == 211 || keyCode == 199 || keyCode == 207 || keyCode == 203 || keyCode == 205 || this.allowedcharacters
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       .indexOf(keyChar) >= 0) {
/*     */       
/*  82 */       String oldName = this.txtNewItemName.b();
/*     */       
/*  84 */       this.txtNewItemName.a(keyChar, keyCode);
/*  85 */       this.txtNewItemDisplayName.a(keyChar, keyCode);
/*     */       
/*  87 */       this.txtNewItemName.g(14737632);
/*     */       
/*  89 */       if (this.txtNewItemName.b() != oldName)
/*     */       {
/*  91 */         this.txtNewItemDisplayName.a(this.txtNewItemName.b());
/*     */       }
/*     */     }
/*  94 */     else if (keyCode == 15) {
/*     */       
/*  96 */       if (this.txtNewItemName.m()) {
/*     */         
/*  98 */         this.txtNewItemName.b(false);
/*  99 */         this.txtNewItemDisplayName.b(true);
/*     */       }
/*     */       else {
/*     */         
/* 103 */         this.txtNewItemName.b(true);
/* 104 */         this.txtNewItemDisplayName.b(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogSubmissionFailed(String reason) {
/* 112 */     this.txtNewItemName.g(16733525);
/* 113 */     this.txtNewItemName.b(true);
/* 114 */     this.txtNewItemDisplayName.b(false);
/* 115 */     this.prompt = reason;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 121 */     return (this.txtNewItemName.b().length() > 0 && this.txtNewItemDisplayName.b().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 128 */     c(this.q, this.prompt, this.dialogX + 10, this.dialogY + 10, -22016);
/*     */     
/* 130 */     c(this.q, I18n.get("patch.create.label.name"), this.dialogX + 10, this.dialogY + 36, -256);
/* 131 */     this.txtNewItemName.g();
/*     */     
/* 133 */     c(this.q, I18n.get("patch.create.label.display"), this.dialogX + 10, this.dialogY + 60, -256);
/* 134 */     this.txtNewItemDisplayName.g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewItemName() {
/* 142 */     return this.newItemName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNewItemDisplayName() {
/* 147 */     return this.newItemDisplayName;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxCreateItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */