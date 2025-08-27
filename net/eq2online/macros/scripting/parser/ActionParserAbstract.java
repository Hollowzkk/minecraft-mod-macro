/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import net.eq2online.macros.core.executive.MacroAction;
/*    */ import net.eq2online.macros.scripting.ActionParser;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ 
/*    */ public abstract class ActionParserAbstract
/*    */   extends ActionParser
/*    */ {
/*    */   protected ActionParserAbstract(ScriptContext context) {
/* 12 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final MacroAction getInstance(IMacroActionProcessor actionProcessor, String actionName, String rawParams, String unparsedParams, String[] params, String outVar) {
/* 25 */     IScriptAction scriptAction = this.context.getAction(actionName);
/*    */     
/* 27 */     if (scriptAction != null) {
/*    */       
/* 29 */       if (scriptAction.checkExecutePermission())
/*    */       {
/* 31 */         return new MacroAction(actionProcessor, scriptAction, rawParams, unparsedParams, params, outVar);
/*    */       }
/*    */       
/* 34 */       if (scriptAction.isPermissable())
/*    */       {
/* 36 */         return new MacroAction(actionProcessor, (IScriptAction)new DeniedAction(this.context), actionName, actionName, params, outVar);
/*    */       }
/*    */     } 
/*    */     
/* 40 */     return new MacroAction(actionProcessor, (IScriptAction)new UnrecognisedAction(this.context, actionName), rawParams, unparsedParams, params, outVar);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final MacroAction parse(IMacroActionProcessor actionProcessor, String actionName, String unparsedParams, String outVar) {
/* 53 */     StringBuilder rawParams = new StringBuilder();
/* 54 */     char firstParamQuote = actionName.toLowerCase().matches("^(if|elseif|iif)$") ? Character.MIN_VALUE : '"';
/* 55 */     String[] params = ScriptCore.tokenize(unparsedParams, ',', firstParamQuote, '"', '\\', rawParams);
/*    */     
/* 57 */     return getInstance(actionProcessor, actionName, (rawParams.length() > 0) ? rawParams.substring(1) : "", unparsedParams, params, outVar);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\ActionParserAbstract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */