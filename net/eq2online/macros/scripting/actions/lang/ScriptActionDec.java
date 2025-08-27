/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionDec
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionDec(ScriptContext context) {
/* 15 */     super(context, "dec");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     int increment = 1;
/* 22 */     String counter = (params.length > 0) ? provider.expand(macro, params[0], false) : "counter";
/*    */     
/* 24 */     if (params.length > 1)
/*    */     {
/* 26 */       increment = ScriptCore.tryParseInt(provider.expand(macro, params[1], false), 1);
/*    */     }
/*    */     
/* 29 */     provider.incrementCounterVariable(macro, counter, -1 * increment);
/*    */     
/* 31 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionDec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */