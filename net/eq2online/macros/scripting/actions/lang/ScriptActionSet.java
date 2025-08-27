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
/*    */ public class ScriptActionSet
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSet(ScriptContext context) {
/* 15 */     super(context, "set");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     String variableName = (params.length > 0) ? provider.expand(macro, params[0], false) : "flag";
/* 22 */     String variableValue = (params.length > 1) ? provider.expand(macro, params[1], false) : null;
/*    */     
/* 24 */     provider.setVariable(macro, variableName, variableValue);
/*    */     
/* 26 */     return (IReturnValue)new ReturnValue(variableValue);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */