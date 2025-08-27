/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.console.Log;
/*    */ import net.eq2online.macros.scripting.ReturnValueLog;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.util.Util;
/*    */ import rp;
/*    */ 
/*    */ public class ScriptActionLog
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionLog(ScriptContext context) {
/* 18 */     super(context, "log");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     if (params.length == 0)
/*    */     {
/* 26 */       return null;
/*    */     }
/*    */     
/* 29 */     String logMessage = Util.convertAmpCodes(provider.expand(macro, rawParams, false));
/* 30 */     Log.info("[LOG] {0}", new Object[] { rp.a(logMessage) });
/* 31 */     return (IReturnValue)new ReturnValueLog("§b" + logMessage);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */