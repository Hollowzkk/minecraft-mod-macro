/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionIndexOf
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionIndexOf(ScriptContext context) {
/* 16 */     super(context, "indexof");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     ReturnValue retVal = new ReturnValue(-1);
/*    */     
/* 24 */     if (params.length > 2) {
/*    */       
/* 26 */       boolean caseSensitive = (params.length > 3) ? ScriptCore.parseBoolean(provider.expand(macro, params[3], false)) : false;
/*    */       
/* 28 */       String arrayName = provider.expand(macro, params[0], false);
/* 29 */       String variableName = provider.expand(macro, params[1], false).toLowerCase();
/* 30 */       String searchValue = provider.expand(macro, params[2], false);
/*    */       
/* 32 */       int arrayIndex = provider.getArrayIndexOf(macro, arrayName, searchValue, caseSensitive);
/* 33 */       retVal.setInt(arrayIndex);
/* 34 */       provider.setVariable(macro, variableName, arrayIndex);
/*    */     } 
/*    */     
/* 37 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIndexOf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */