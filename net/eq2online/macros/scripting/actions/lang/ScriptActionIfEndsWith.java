/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionIfEndsWith
/*    */   extends ScriptActionIf
/*    */ {
/*    */   public ScriptActionIfEndsWith(ScriptContext context) {
/* 12 */     super(context, "ifendswith");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 18 */     if (params.length > 1) {
/*    */       
/* 20 */       String haystack = provider.expand(macro, params[0], false).toLowerCase().trim();
/* 21 */       String needle = provider.expand(macro, params[1], false).toLowerCase().trim();
/*    */       
/* 23 */       return haystack.endsWith(needle);
/*    */     } 
/*    */     
/* 26 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIfEndsWith.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */