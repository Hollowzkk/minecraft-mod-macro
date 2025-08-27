/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionIf
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionIf(ScriptContext context) {
/* 15 */     super(context, "if");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionIf(ScriptContext context, String actionName) {
/* 20 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExpectedPopCommands() {
/* 26 */     return I18n.get("script.error.stackhint", new Object[] { this, "ELSEIF§c, §dELSE§c or §dENDIF" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConditionalOperator() {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 38 */     String condition = (params.length > 0) ? params[0] : "flag";
/*    */     
/* 40 */     IExpressionEvaluator evaluator = provider.getExpressionEvaluator(macro, provider.expand(macro, condition, true));
/* 41 */     return (evaluator.evaluate() != 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */