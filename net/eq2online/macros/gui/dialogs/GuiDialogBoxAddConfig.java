/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bib;
/*     */ import bje;
/*     */ import blk;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AllowedCharacters;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiDialogBoxAddConfig
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private final Macros macros;
/*     */   private bje txtNewConfigName;
/*     */   private GuiCheckBox chkCopySettings;
/*     */   private String newConfigName;
/*     */   public boolean copySettings;
/*     */   
/*     */   public GuiDialogBoxAddConfig(Macros macros, bib minecraft, GuiScreenEx parentScreen) {
/*  54 */     super(minecraft, (blk)parentScreen, 320, 142, I18n.get("options.newconfig.title"));
/*  55 */     this.macros = macros;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNewConfigName() {
/*  60 */     return this.newConfigName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  66 */     this.txtNewConfigName.a();
/*     */     
/*  68 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiControl control) {
/*  74 */     if (control.k == 0) {
/*     */       
/*  76 */       if (this.j.E()) {
/*     */ 
/*     */         
/*  79 */         this.txtNewConfigName.a("Single Player");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  84 */         this.txtNewConfigName.a(this.macros.getLastServerName());
/*     */       } 
/*     */       
/*  87 */       this.txtNewConfigName.b(true);
/*     */     } 
/*     */     
/*  90 */     super.actionPerformed(control);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/*  97 */     this.newConfigName = this.txtNewConfigName.b();
/*  98 */     this.copySettings = this.chkCopySettings.checked;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 104 */     this.txtNewConfigName.a(mouseX, mouseY, button);
/* 105 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/* 111 */     this.txtNewConfigName = new bje(0, this.q, this.dialogX + 10, this.dialogY + 36, this.dialogWidth - 20, 16);
/* 112 */     this.txtNewConfigName.f(32);
/* 113 */     this.txtNewConfigName.b(true);
/*     */     
/* 115 */     this.chkCopySettings = new GuiCheckBox(this.j, 1, this.dialogX + 8, this.dialogY + 90, I18n.get("options.newconfig.option"), false);
/*     */     
/* 117 */     addControl(new GuiControl(0, this.dialogX + 8, this.dialogY + 60, 160, 20, I18n.get("options.newconfig.prompt2")));
/* 118 */     addControl((GuiControl)this.chkCopySettings);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 124 */     if (keyCode == 14 || AllowedCharacters.CHARACTERS.indexOf(keyChar) >= 0)
/*     */     {
/* 126 */       this.txtNewConfigName.a(keyChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 134 */     return (this.txtNewConfigName.b().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 141 */     c(this.q, I18n.get("options.newconfig.prompt"), this.dialogX + 10, this.dialogY + 16, -22016);
/* 142 */     this.txtNewConfigName.g();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxAddConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */