/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bib;
/*     */ import bkp;
/*     */ import bkq;
/*     */ import blk;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderPreset;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamPreset
/*     */   extends MacroParamListOnly<String>
/*     */ {
/*     */   protected int presetIndex;
/*     */   
/*     */   public MacroParamPreset(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProviderPreset provider) {
/*  28 */     super(macros, mc, type, target, params, (MacroParamProvider<String>)provider);
/*     */     
/*  30 */     this.presetIndex = provider.getNextPresetIndex();
/*     */     
/*  32 */     setParameterValue(params.getParameterValueFromStore((MacroParamProvider)provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  41 */     if (this.presetIndex > -1 && this.presetIndex < 10) {
/*     */       
/*  43 */       if (this.target.getParamStore() != null)
/*     */       {
/*  45 */         this.target.getParamStore().setStoredParam(this.type, this.presetIndex, getParameterValue());
/*     */       }
/*     */       
/*  48 */       Matcher matcher = MacroParamProviderPreset.getMatcher(this.presetIndex, this.target.getTargetString());
/*  49 */       this.target.setTargetString(matcher.replaceAll(getParameterValueEscaped()));
/*  50 */       this.presetIndex = -1;
/*  51 */       this.target.recompile();
/*     */     } 
/*     */     
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/*  63 */     this.itemListBox = this.macros.getListProvider().getListBox(this.type, String.valueOf(this.presetIndex));
/*  64 */     this.itemListBox.setSizeAndPosition(2, 2, width - 4, height - 4, 20, false);
/*  65 */     this.itemListBox.setSelectedItemIndex(0);
/*  66 */     this.itemListBox.selectData(getParameterValue());
/*  67 */     this.itemListBox.scrollToCentre();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupListBox(int width, int height) {
/*  76 */     this.itemListBox.setSize(width - 4, height - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam<String> guiMacroParam) {
/*  86 */     IListEntry<String> selectedItem = this.itemListBox.getSelectedItem();
/*     */ 
/*     */     
/*  89 */     if (selectedItem.getId() == -1) {
/*     */       
/*  91 */       IListEntry<String> newItem = this.itemListBox.createObject("");
/*  92 */       this.itemListBox.addItemAt(newItem, this.itemListBox.getSelectedItemIndex());
/*  93 */       this.itemListBox.selectItem(newItem);
/*  94 */       this.itemListBox.beginEditInPlace();
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     IListEntry.CustomAction customAction = selectedItem.getCustomAction(true);
/*     */     
/* 100 */     if (customAction == IListEntry.CustomAction.DELETE) {
/*     */ 
/*     */       
/* 103 */       this.mc.a((blk)new bkq((bkp)guiMacroParam, I18n.get("param.action.confirmdelete"), selectedItem.getText(), 
/* 104 */             I18n.get("gui.yes"), I18n.get("gui.no"), 0));
/*     */     } else {
/* 106 */       if (customAction == IListEntry.CustomAction.EDIT) {
/*     */         
/* 108 */         this.itemListBox.beginEditInPlace();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 113 */       if (selectedItem.getText().equals(""))
/*     */       {
/* 115 */         this.itemListBox.removeSelectedItem();
/*     */       }
/*     */       
/* 118 */       selectedItem = this.itemListBox.getSelectedItem();
/* 119 */       if (selectedItem != null) setParameterValue(selectedItem.getText());
/*     */     
/*     */     } 
/*     */     
/* 123 */     if (this.itemListBox.isDoubleClicked(true) && selectedItem != null && selectedItem.getId() > -1)
/*     */     {
/* 125 */       guiMacroParam.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.HandledState keyTyped(char keyChar, int keyCode) {
/* 133 */     if (this.itemListBox != null)
/*     */     {
/* 135 */       return this.itemListBox.listBoxKeyTyped(keyChar, keyCode);
/*     */     }
/*     */     
/* 138 */     return GuiControlEx.HandledState.NONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamPreset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */