/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public class MacroParamUser
/*    */   extends MacroParamFriend
/*    */ {
/*    */   public MacroParamUser(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<String> provider) {
/* 15 */     super(macros, mc, type, target, params, provider);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 24 */     if (this.target.getParamStore() != null)
/*    */     {
/* 26 */       this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*    */     }
/*    */     
/* 29 */     if (shouldReplaceFirstOccurrenceOnly()) {
/*    */       
/* 31 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceFirst(getParameterValueEscaped()));
/*    */     }
/*    */     else {
/*    */       
/* 35 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*    */     } 
/*    */     
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldReplaceFirstOccurrenceOnly() {
/* 48 */     return (this.replaceFirstOccurrenceOnly || this.settings.getCompilerFlagUser());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAutoPopulateSupported() {
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptMessage() {
/* 67 */     return MacroParam.getLocalisedString("param.prompt.person", new String[] { I18n.get("param.prompt.user"), this.target.getDisplayName() });
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */