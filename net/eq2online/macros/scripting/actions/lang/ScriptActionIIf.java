/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.ReturnValueChat;
/*    */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionIIf
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionIIf(ScriptContext context) {
/* 16 */     super(context, "iif");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     if (params.length > 1) {
/*    */       
/* 24 */       IExpressionEvaluator evaluator = provider.getExpressionEvaluator(macro, provider.expand(macro, params[0], true));
/*    */       
/* 26 */       if (evaluator.evaluate() != 0)
/*    */       {
/* 28 */         return (IReturnValue)new ReturnValueChat(provider.expand(macro, params[1], false));
/*    */       }
/* 30 */       if (params.length > 2)
/*    */       {
/* 32 */         return (IReturnValue)new ReturnValueChat(provider.expand(macro, params[2], false));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 54 */     return "chat";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIIf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */