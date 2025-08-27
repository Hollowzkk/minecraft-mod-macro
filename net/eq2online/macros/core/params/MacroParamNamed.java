/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.providers.MacroParamProviderNamed;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamNamed
/*    */   extends MacroParamStandard<String>
/*    */ {
/*    */   protected String namedParamName;
/*    */   
/*    */   public MacroParamNamed(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProviderNamed provider) {
/* 20 */     super(macros, mc, type, target, params, (MacroParamProvider<String>)provider);
/*    */     
/* 22 */     this.namedParamName = provider.getNextNamedVar();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 31 */     if (!"var".equals(this.namedParamName)) {
/*    */       
/* 33 */       if (this.target.getParamStore() != null)
/*    */       {
/* 35 */         this.target.getParamStore().setStoredParam(this.type, 0, this.namedParamName, getParameterValue());
/*    */       }
/*    */       
/* 38 */       this.target.setTargetString(this.target.getTargetString().replaceAll("\\$\\$\\[" + this.namedParamName + "\\]", 
/* 39 */             Macro.escapeReplacement(getParameterValue())));
/* 40 */       this.params.removeNamedVar(this.namedParamName);
/* 41 */       this.target.recompile();
/*    */     } 
/*    */     
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptMessage() {
/* 53 */     return MacroParam.getLocalisedString("param.prompt.named", new String[] { this.target.getDisplayName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptPrefix() {
/* 62 */     return this.namedParamName;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamNamed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */