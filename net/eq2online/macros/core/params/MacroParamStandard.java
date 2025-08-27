/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public class MacroParamStandard<TItem>
/*    */   extends MacroParam<TItem>
/*    */ {
/*    */   public MacroParamStandard(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<TItem> provider) {
/* 15 */     super(macros, mc, type, target, params, provider);
/*    */     
/* 17 */     this.enableTextField = Boolean.valueOf(true);
/* 18 */     setParameterValue(params.getParameterValueFromStore(provider));
/*    */     
/* 20 */     if (target.getIteration() > 1)
/*    */     {
/* 22 */       setParameterValue("");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 32 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*    */     {
/* 34 */       this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*    */     }
/*    */     
/* 37 */     this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(
/* 38 */           Macro.escapeReplacement(getParameterValue())));
/*    */     
/* 40 */     this.target.compile();
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamStandard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */