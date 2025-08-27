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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionPop
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPop(ScriptContext context) {
/* 22 */     super(context, "pop");
/*    */   }
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*    */     ReturnValue returnValue;
/* 28 */     IReturnValue retVal = null;
/*    */     
/* 30 */     if (params.length > 0) {
/*    */       
/* 32 */       String arrayName = provider.expand(macro, params[0], false);
/* 33 */       String poppedValue = provider.popValueFromArray(macro, arrayName);
/*    */       
/* 35 */       returnValue = new ReturnValue(poppedValue);
/*    */       
/* 37 */       if (params.length > 1) {
/*    */         
/* 39 */         String variableName = provider.expand(macro, params[1], false).toLowerCase();
/* 40 */         provider.setVariable(macro, variableName, (poppedValue != null) ? poppedValue : "");
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     return (IReturnValue)returnValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionPop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */