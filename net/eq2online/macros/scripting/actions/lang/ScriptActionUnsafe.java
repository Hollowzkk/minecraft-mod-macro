/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionUnsafe
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionUnsafe(ScriptContext context) {
/* 15 */     super(context, "unsafe");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConditionalOperator() {
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExpectedPopCommands() {
/* 33 */     return I18n.get("script.error.stackhint", new Object[] { this, "ENDUNSAFE" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 39 */     int maxActions = 100;
/*    */     
/* 41 */     if (params.length > 0)
/*    */     {
/* 43 */       maxActions = Math.min(Math.max(ScriptCore.tryParseInt(params[0], 100), 0), 10000);
/*    */     }
/*    */     
/* 46 */     provider.actionBeginUnsafeBlock(macro, instance, maxActions);
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeStackPop(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroAction popAction) {
/* 54 */     provider.actionEndUnsafeBlock(macro, instance);
/*    */     
/* 56 */     return super.executeStackPop(provider, macro, instance, rawParams, params, popAction);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionUnsafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */