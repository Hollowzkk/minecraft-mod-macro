/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionAssign
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionAssign(ScriptContext context) {
/* 16 */     super(context, "assign");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     String variableName = (params.length > 0) ? provider.expand(macro, params[0], false) : "flag";
/* 23 */     String variableValue = (params.length > 1) ? params[1] : "";
/*    */     
/* 25 */     if (variableValue != null)
/*    */     {
/* 27 */       if (variableName.startsWith("&") || variableName.startsWith("@&")) {
/*    */         
/* 29 */         variableValue = provider.expand(macro, variableValue, false);
/*    */       }
/*    */       else {
/*    */         
/* 33 */         variableValue = String.valueOf(provider.getExpressionEvaluator(macro, provider.expand(macro, variableValue, true)).evaluate());
/*    */       } 
/*    */     }
/*    */     
/* 37 */     if (Variable.couldBeArraySpecifier(variableName) && Variable.getValidVariableOrArraySpecifier(variableName) != null) {
/*    */       
/* 39 */       provider.pushValueToArray(macro, Variable.getValidVariableOrArraySpecifier(variableName), variableValue);
/*    */     }
/*    */     else {
/*    */       
/* 43 */       provider.setVariable(macro, variableName, variableValue);
/*    */     } 
/*    */     
/* 46 */     return (IReturnValue)new ReturnValue(variableValue);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionAssign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */