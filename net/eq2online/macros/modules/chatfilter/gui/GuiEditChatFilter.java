/*     */ package net.eq2online.macros.modules.chatfilter.gui;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.screens.GuiEditTextString;
/*     */ import net.eq2online.macros.modules.chatfilter.ChatFilterManager;
/*     */ import net.eq2online.macros.modules.chatfilter.ChatFilterTemplate;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiEditChatFilter
/*     */   extends GuiEditTextString
/*     */ {
/*     */   private final ChatFilterTemplate template;
/*     */   private boolean global;
/*     */   private GuiCheckBox chkGlobal;
/*     */   
/*     */   public GuiEditChatFilter(Macros macros, bib mc, GuiScreenEx parent) {
/*  24 */     super(macros, mc, parent, getTemplateText(macros), "Chat Filter", ScriptContext.CHATFILTER);
/*     */     
/*  26 */     this.template = ChatFilterManager.getInstance().getTemplate(this.macros.getActiveConfig(), true);
/*  27 */     this.global = this.template.global;
/*  28 */     this.title = I18n.get("editor.editing", new Object[] { "<Chat Filter>" });
/*  29 */     this.bannerColour = 4259648;
/*  30 */     this.bannerCentred = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  39 */     super.b();
/*     */     
/*  41 */     this.n.add(this.chkGlobal = new GuiCheckBox(this.j, 33, 6, this.m - 24, I18n.get("macro.option.global"), this.global));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getTemplateText(Macros macros) {
/*  46 */     ChatFilterTemplate template = ChatFilterManager.getInstance().getTemplate(macros.getActiveConfig(), true);
/*  47 */     return unfoldTemplate(template.getKeyDownMacro());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String unfoldTemplate(String template) {
/*  52 */     return template.replaceAll("\\x82", "\r\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private static String foldTemplate(String template) {
/*  57 */     return template.replaceAll("\\r?\\n", "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja guibutton) {
/*  67 */     if (guibutton != null) {
/*     */       
/*  69 */       if (guibutton.k == 1) {
/*     */         
/*  71 */         String newText = this.textEditor.getText();
/*  72 */         this.template.setKeyDownMacro(foldTemplate(newText));
/*  73 */         this.template.global = this.global;
/*  74 */         ChatFilterManager.getInstance().save();
/*     */       } 
/*     */       
/*  77 */       if (guibutton.k == this.chkGlobal.k)
/*     */       {
/*  79 */         this.global = this.chkGlobal.checked;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  84 */     super.a(guibutton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/*  93 */     if (this.textEditor != null) {
/*     */       
/*  95 */       mouseWheelDelta /= 120;
/*     */       
/*  97 */       while (mouseWheelDelta > 0) {
/*     */         
/*  99 */         this.textEditor.scroll(-1);
/* 100 */         mouseWheelDelta--;
/*     */       } 
/*     */       
/* 103 */       while (mouseWheelDelta < 0) {
/*     */         
/* 105 */         this.textEditor.scroll(1);
/* 106 */         mouseWheelDelta++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 118 */     this.banner = I18n.get("macro.currentconfig", new Object[] { this.global ? ("§e" + I18n.get("macro.config.global")) : this.macros.getActiveConfigName() });
/*     */     
/* 120 */     super.a(mouseX, mouseY, f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHeaderClick() {
/* 126 */     this.global = !this.global;
/* 127 */     this.chkGlobal.checked = this.global;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\modules\chatfilter\gui\GuiEditChatFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */