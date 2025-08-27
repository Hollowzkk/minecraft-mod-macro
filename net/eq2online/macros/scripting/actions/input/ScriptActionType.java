/*    */ package net.eq2online.macros.scripting.actions.input;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionType
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionType(ScriptContext context) {
/* 14 */     super(context, "type");
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
/* 44 */     if (params.length == 0) return null;
/*    */     
/* 46 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 48 */     for (String param : params)
/*    */     {
/* 50 */       sb.append(" ").append(param);
/*    */     }
/*    */     
/* 53 */     provider.actionPumpCharacters(provider.expand(macro, sb.toString().substring(1), false));
/*    */     
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */