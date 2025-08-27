/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionPut
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPut(ScriptContext context) {
/* 14 */     super(context, "put");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 20 */     if (params.length > 0) {
/*    */       
/* 22 */       String arrayName = provider.expand(macro, params[0], false);
/* 23 */       String variableValue = (params.length > 1) ? provider.expand(macro, params[1], false) : null;
/*    */       
/* 25 */       provider.putValueToArray(macro, arrayName, variableValue);
/*    */     } 
/*    */     
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionPut.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */