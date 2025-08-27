/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionSprint
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSprint(ScriptContext context) {
/* 14 */     super(context, "sprint");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 38 */     return "input";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 44 */     boolean sprint = true;
/*    */     
/* 46 */     if (params.length > 0)
/*    */     {
/* 48 */       if (params[0].equals("0") || params[0].equalsIgnoreCase("off"))
/*    */       {
/* 50 */         sprint = false;
/*    */       }
/*    */     }
/*    */     
/* 54 */     provider.actionSetSprinting(sprint);
/*    */     
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionSprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */