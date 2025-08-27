/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import java.util.ArrayList;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ 
/*     */ 
/*     */ public class MacroParamFriend
/*     */   extends MacroParamGenericEditableList<String>
/*     */ {
/*     */   private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";
/*     */   
/*     */   public MacroParamFriend(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<String> provider) {
/*  22 */     super(macros, mc, type, target, params, provider);
/*     */     
/*  24 */     if (!Pattern.matches("^[a-zA-Z0-9_]{2,16}$", getParameterValue())) setParameterValue(""); 
/*  25 */     this.maxParameterLength = 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  34 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*     */     {
/*  36 */       this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*     */     }
/*     */     
/*  39 */     if (shouldReplaceFirstOccurrenceOnly()) {
/*     */       
/*  41 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceFirst(getParameterValueEscaped()));
/*     */     }
/*     */     else {
/*     */       
/*  45 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*     */     } 
/*     */     
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldReplaceFirstOccurrenceOnly() {
/*  58 */     return (this.replaceFirstOccurrenceOnly || this.settings.getCompilerFlagFriend());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstOccurrenceSupported() {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getAllowedCharacters() {
/*  77 */     return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  86 */     return MacroParam.getLocalisedString("param.prompt.person", new String[] { I18n.get("param.prompt.friend"), this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void autoPopulate() {
/*  95 */     if (this.parentScreen != null)
/*     */     {
/*  97 */       this.mc.a((blk)this.parentScreen);
/*     */     }
/*     */     
/* 100 */     if (this.parentScreen != null)
/*     */     {
/* 102 */       this.parentScreen.onAutoPopulateComplete(this, new ArrayList());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry<String> gui, boolean editing) {
/* 113 */     gui.displayText = I18n.get(editing ? "entry.editfriend" : "entry.newfriend");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/* 123 */     textField.minStringLength = 2;
/* 124 */     textField.f(16);
/* 125 */     textField.allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamFriend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */