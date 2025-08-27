/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionPush
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPush(ScriptContext context) {
/* 19 */     super(context, "push");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 25 */     if (params.length > 0) {
/*    */       
/* 27 */       String arrayName = provider.expand(macro, params[0], false);
/* 28 */       String variableValue = (params.length > 1) ? provider.expand(macro, params[1], false) : null;
/*    */       
/* 30 */       provider.pushValueToArray(macro, arrayName, variableValue);
/*    */     } 
/*    */     
/* 33 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionPush.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */