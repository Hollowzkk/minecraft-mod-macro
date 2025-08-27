/*    */ package net.eq2online.macros.scripting.repl;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.IErrorLogger;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptParser;
/*    */ 
/*    */ 
/*    */ 
/*    */ class ReplParser
/*    */   extends ScriptParser
/*    */ {
/*    */   private final IErrorLogger errorLogger;
/*    */   
/*    */   ReplParser(ScriptContext context, IErrorLogger errorLogger) {
/* 19 */     super(context);
/* 20 */     this.errorLogger = errorLogger;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<IMacroAction> parseScript(IMacroActionProcessor actionProcessor, String script) {
/* 26 */     List<IMacroAction> actions = super.parseScript(actionProcessor, script);
/*    */     
/* 28 */     for (IMacroAction action : actions) {
/*    */       
/* 30 */       if (action.getAction() != null && action.getAction() instanceof net.eq2online.macros.scripting.parser.UnrecognisedAction)
/*    */       {
/* 32 */         this.errorLogger.logError(I18n.get("repl.error.unrecognised", new Object[] { action.getAction().getName() }));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     if (actions.size() == 0 && script.length() > 0) {
/*    */       
/* 38 */       String actionName = script.trim().toUpperCase();
/* 39 */       if (getContext().getCore().getAction(actionName) != null)
/*    */       {
/* 41 */         this.errorLogger.logError(actionName);
/*    */       }
/*    */     } 
/*    */     
/* 45 */     return actions;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onError(String scriptEntry) {
/* 51 */     this.errorLogger.logError(I18n.get("repl.error.action", new Object[] { scriptEntry }));
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\ReplParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */