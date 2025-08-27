/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiTextEditor;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ public class GuiEditTextString
/*     */   extends GuiEditTextBase
/*     */ {
/*     */   protected String string;
/*     */   
/*     */   public GuiEditTextString(Macros macros, bib minecraft, GuiScreenEx parent, String string, String macroName, ScriptContext context) {
/*  22 */     super(macros, minecraft, parent, context);
/*  23 */     this.string = string;
/*  24 */     this.title = I18n.get("macro.edit.title");
/*  25 */     this.bgBottomMargin = 28;
/*  26 */     this.banner = I18n.get("editor.editing", new Object[] { "<" + macroName + ">" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  35 */     Settings settings = this.macros.getSettings();
/*     */     
/*  37 */     Keyboard.enableRepeatEvents(true);
/*     */     
/*  39 */     clearControlList();
/*     */ 
/*     */     
/*  42 */     if (this.textEditor == null) {
/*     */       
/*  44 */       this.textEditor = new GuiTextEditor(this.j, 0, 7, 27, this.l - 12, this.m - 60, this, settings.showTextEditorSyntax, this.context, settings.showTextEditorHelp);
/*     */       
/*  46 */       this.textEditor.setText(this.string);
/*     */     }
/*     */     else {
/*     */       
/*  50 */       this.textEditor.setSizeAndPosition(7, 27, this.l - 12, this.m - 60);
/*     */     } 
/*     */     
/*  53 */     this.textEditor.setUseDarkColours(settings.useDarkEditorColours);
/*     */     
/*  55 */     addControl((GuiControl)this.textEditor);
/*  56 */     addControl(new GuiControl(1, this.l - 64, this.m - 24, 60, 20, I18n.get("gui.ok")));
/*  57 */     addControl(new GuiControl(2, this.l - 128, this.m - 24, 60, 20, I18n.get("gui.cancel")));
/*     */     
/*  59 */     addControl((GuiControl)(this.chkShowHelp = new GuiCheckBox(this.j, 55, 175, this.m - 24, I18n.get("editor.option.help"), settings.showTextEditorHelp)));
/*     */ 
/*     */     
/*  62 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja guibutton) {
/*  73 */     if (guibutton != null) {
/*     */ 
/*     */       
/*  76 */       if (guibutton.k == 1)
/*     */       {
/*     */         
/*  79 */         close();
/*     */       }
/*     */ 
/*     */       
/*  83 */       if (guibutton.k == 1 || guibutton.k == 2)
/*     */       {
/*  85 */         close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/*  96 */     if (keyCode == 1) {
/*     */       
/*  98 */       close();
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     if (this.textEditor != null)
/*     */     {
/* 104 */       this.textEditor.keyTyped(keyChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 114 */     if (this.j.f == null)
/*     */     {
/* 116 */       c();
/*     */     }
/*     */     
/* 119 */     drawScreenWithEnabledState(mouseX, mouseY, f, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTicks, boolean enabled) {
/* 125 */     a(2, this.m - 26, this.l - 2, this.m - 2, this.backColour);
/*     */     
/* 127 */     super.a(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void close() {
/* 136 */     if (this.textEditor != null) {
/*     */       
/* 138 */       (this.macros.getSettings()).showTextEditorSyntax = this.textEditor.isHighlighting();
/* 139 */       (this.macros.getSettings()).showTextEditorHelp = this.textEditor.isHelpVisible();
/*     */     } 
/*     */     
/* 142 */     if (this.parent != null) {
/*     */       
/* 144 */       this.parent.onFinishEditingTextFile(this, null);
/*     */       
/*     */       return;
/*     */     } 
/* 148 */     this.j.a(null);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiEditTextString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */