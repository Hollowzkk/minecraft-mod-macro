/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ import net.eq2online.macros.interfaces.IRefreshable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamFixedList<TItem>
/*    */   extends MacroParam<TItem>
/*    */ {
/*    */   public MacroParamFixedList(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<TItem> provider) {
/* 17 */     super(macros, mc, type, target, params, provider);
/*    */     
/* 19 */     this.enableTextField = Boolean.valueOf(false);
/* 20 */     setParameterValue(params.getParameterValueFromStore(provider));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptMessage() {
/* 26 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean apply() {
/* 32 */     updateValue();
/* 33 */     return super.apply();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateValue() {
/* 41 */     String selectedValue = this.itemListBox.getSelectedItem().getText();
/* 42 */     setParameterValue(selectedValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 51 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*    */     {
/* 53 */       this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*    */     }
/*    */     
/* 56 */     this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initListBox(int width, int height) {
/* 63 */     this.itemListBox = this.macros.getListProvider().getListBox(this.type);
/* 64 */     if (this.itemListBox instanceof IRefreshable)
/*    */     {
/* 66 */       ((IRefreshable)this.itemListBox).refresh();
/*    */     }
/* 68 */     this.itemListBox.setSizeAndPosition(2, 2, 360, height - 40, 20, true);
/* 69 */     this.itemListBox.scrollToCentre();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setupListBox(int width, int height) {
/* 75 */     this.itemListBox.setSize(360, height - 4);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean addItem(GuiEditListEntry<TItem> gui, String newItem, String displayName, int iconID, TItem newItemData) {
/* 81 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamFixedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */