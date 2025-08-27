/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bib;
/*     */ import bkp;
/*     */ import bkq;
/*     */ import blk;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ 
/*     */ 
/*     */ public abstract class MacroParamGenericEditableList<TItem>
/*     */   extends MacroParam<TItem>
/*     */ {
/*     */   protected MacroParamGenericEditableList(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<TItem> provider) {
/*  21 */     super(macros, mc, type, target, params, provider);
/*     */     
/*  23 */     this.enableTextField = Boolean.valueOf(true);
/*  24 */     setParameterValue(params.getParameterValueFromStore(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/*  33 */     this.itemListBox = this.macros.getListProvider().getListBox(this.type);
/*  34 */     this.itemListBox.setSizeAndPosition(2, 2, 180, height - 40, 20, true);
/*  35 */     this.itemListBox.setSelectedItemIndex(0);
/*  36 */     this.itemListBox.selectData(getParameterValue());
/*  37 */     this.itemListBox.scrollToCentre();
/*     */     
/*  39 */     if (this.itemListBox.getSelectedItem() != null && this.itemListBox.getSelectedItem().getId() != -1)
/*     */     {
/*  41 */       setParameterValue(this.itemListBox.getSelectedItem().getText());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoPopulateSupported() {
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editItem(GuiEditListEntry<TItem> gui, String editedText, String displayName, int editedIconID, IListEntry<TItem> editedObject) {
/*  63 */     editedObject.setIconId(editedIconID);
/*  64 */     editedObject.setText(editedText);
/*     */     
/*  66 */     this.itemListBox.save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam<TItem> guiMacroParam) {
/*  76 */     IListEntry<TItem> selectedItem = this.itemListBox.getSelectedItem();
/*     */ 
/*     */     
/*  79 */     if (selectedItem.getId() == -1) {
/*     */       
/*  81 */       this.mc.a((blk)new GuiEditListEntry(this.macros, this.mc, guiMacroParam, this, null));
/*     */       
/*     */       return;
/*     */     } 
/*  85 */     IListEntry.CustomAction customAction = selectedItem.getCustomAction(true);
/*     */     
/*  87 */     if (customAction == IListEntry.CustomAction.DELETE) {
/*     */ 
/*     */       
/*  90 */       this.mc.a((blk)new bkq((bkp)guiMacroParam, I18n.get("param.action.confirmdelete"), selectedItem.getText(), 
/*  91 */             I18n.get("gui.yes"), I18n.get("gui.no"), 0));
/*     */     }
/*  93 */     else if (customAction == IListEntry.CustomAction.EDIT) {
/*     */       
/*  95 */       this.mc.a((blk)new GuiEditListEntry(this.macros, this.mc, guiMacroParam, this, selectedItem));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 100 */       handleListItemClick(selectedItem);
/*     */ 
/*     */       
/* 103 */       if (this.itemListBox.isDoubleClicked(true))
/*     */       {
/* 105 */         guiMacroParam.apply();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleListItemClick(IListEntry<TItem> selectedItem) {
/* 115 */     setParameterValue((String)selectedItem.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTextFieldTextChanged() {
/* 121 */     this.itemListBox.selectData(getParameterValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamGenericEditableList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */