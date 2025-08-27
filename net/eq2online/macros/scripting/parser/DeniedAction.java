/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import net.eq2online.console.Log;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.ReturnValueLog;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ISettings;
/*    */ 
/*    */ public class DeniedAction
/*    */   extends ScriptAction
/*    */ {
/*    */   public DeniedAction(ScriptContext context) {
/* 16 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*    */     ReturnValueLog returnValueLog;
/* 22 */     IReturnValue retVal = null;
/* 23 */     ISettings settings = provider.getSettings();
/* 24 */     if (settings.generatePermissionsWarnings())
/*    */     {
/* 26 */       returnValueLog = new ReturnValueLog("§c" + I18n.get("script.error.denied", new Object[] { rawParams.toUpperCase() }));
/*    */     }
/*    */     
/* 29 */     if (settings.isDebugEnabled())
/*    */     {
/* 31 */       Log.info("Script action {0} was denied by the server", new Object[] { rawParams.toUpperCase() });
/*    */     }
/*    */     
/* 34 */     return (IReturnValue)returnValueLog;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\DeniedAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */