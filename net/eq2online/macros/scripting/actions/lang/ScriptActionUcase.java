/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionUcase
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionUcase(ScriptContext context) {
/* 15 */     super(context, "ucase");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 23 */     if (params.length > 0) {
/*    */       
/* 25 */       String upperCase = provider.expand(macro, params[0], false).toUpperCase();
/* 26 */       retVal.setString(upperCase);
/*    */       
/* 28 */       if (params.length > 1) {
/*    */         
/* 30 */         String variableName = provider.expand(macro, params[1], false).toLowerCase();
/* 31 */         provider.setVariable(macro, variableName, upperCase);
/*    */       } 
/*    */     } 
/*    */     
/* 35 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionUcase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */