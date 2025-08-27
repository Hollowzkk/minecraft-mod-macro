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
/*    */ public class ScriptActionArraySize
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionArraySize(ScriptContext context) {
/* 15 */     super(context, "arraysize");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     ReturnValue retVal = new ReturnValue(0);
/*    */     
/* 23 */     if (params.length > 0) {
/*    */       
/* 25 */       String arrayName = provider.expand(macro, params[0], false);
/* 26 */       int arraySize = provider.getArraySize(macro, arrayName);
/* 27 */       retVal.setInt(arraySize);
/*    */       
/* 29 */       if (params.length > 1) {
/*    */         
/* 31 */         String variableName = provider.expand(macro, params[1], false).toLowerCase();
/* 32 */         provider.setVariable(macro, variableName, arraySize);
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionArraySize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */