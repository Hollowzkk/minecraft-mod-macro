/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bib;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamTown
/*     */   extends MacroParamGenericEditableList<String>
/*     */ {
/*     */   public MacroParamTown(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<String> provider) {
/*  21 */     super(macros, mc, type, target, params, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  30 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*     */     {
/*  32 */       this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*     */     }
/*     */     
/*  35 */     if (shouldReplaceFirstOccurrenceOnly()) {
/*     */       
/*  37 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceFirst(getParameterValueEscaped()));
/*     */     }
/*     */     else {
/*     */       
/*  41 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*     */     } 
/*     */     
/*  44 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldReplaceFirstOccurrenceOnly() {
/*  54 */     return (this.replaceFirstOccurrenceOnly || this.settings.getCompilerFlagTown());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstOccurrenceSupported() {
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getAllowedCharacters() {
/*  73 */     return super.getAllowedCharacters();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  82 */     return MacroParam.getLocalisedString("param.prompt.town", new String[] { this.target.getDisplayName() });
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
/*     */   public void editItem(GuiEditListEntry<String> gui, String editedText, String displayName, int editedIconID, IListEntry<String> editedObject) {
/*  94 */     editedObject.setData(editedText);
/*  95 */     editedObject.setDisplayName(displayName);
/*  96 */     super.editItem(gui, editedText, displayName, editedIconID, editedObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry<String> gui, boolean editing) {
/* 106 */     gui.displayText = I18n.get(editing ? "entry.edittown" : "entry.newtown");
/* 107 */     gui.enableDisplayName = true;
/* 108 */     gui.windowHeight = 198;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/* 118 */     textField.minStringLength = 1;
/* 119 */     textField.f(256);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public nf getIconTexture() {
/* 128 */     return ResourceLocations.DYNAMIC_TOWNS;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamTown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */