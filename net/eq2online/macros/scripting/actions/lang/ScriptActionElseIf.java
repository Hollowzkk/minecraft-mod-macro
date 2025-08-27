/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionElseIf
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionElseIf(ScriptContext context) {
/* 16 */     super(context, "elseif");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionElseIf(ScriptContext context, String actionName) {
/* 21 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStackPopOperator() {
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConditionalElseOperator(IScriptAction action) {
/* 33 */     return action instanceof ScriptActionIf;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void executeConditionalElse(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroActionStackEntry top) {
/* 40 */     if (top.getIfFlag()) {
/*    */       
/* 42 */       top.setConditionalFlag(false);
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     if (params.length > 0) {
/*    */       
/* 48 */       IExpressionEvaluator evaluator = provider.getExpressionEvaluator(macro, provider.expand(macro, params[0], true));
/* 49 */       top.setConditionalFlag((evaluator.evaluate() != 0));
/*    */       
/* 51 */       top.setIfFlag(top.getIfFlag() | top.getConditionalFlag());
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     top.setConditionalFlag(!top.getConditionalFlag());
/* 56 */     top.setElseFlag(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionElseIf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */