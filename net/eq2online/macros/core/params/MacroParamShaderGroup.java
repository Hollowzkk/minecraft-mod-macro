/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public class MacroParamShaderGroup
/*    */   extends MacroParamFixedList<String>
/*    */ {
/*    */   public MacroParamShaderGroup(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<String> provider) {
/* 14 */     super(macros, mc, type, target, params, provider);
/*    */     
/* 16 */     this.enableTextField = Boolean.valueOf(false);
/* 17 */     setParameterValue(params.getParameterValueFromStore(provider));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateValue() {
/* 23 */     String selectedValue = (String)this.itemListBox.getSelectedItem().getData();
/* 24 */     setParameterValue(selectedValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 33 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*    */     {
/* 35 */       this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*    */     }
/*    */     
/* 38 */     this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamShaderGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */