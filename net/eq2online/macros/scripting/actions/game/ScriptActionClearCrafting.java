/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionClearCrafting
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionClearCrafting(ScriptContext context) {
/* 14 */     super(context, "clearcrafting");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 20 */     return "craft";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 32 */     provider.actionClearCrafting();
/* 33 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionClearCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */