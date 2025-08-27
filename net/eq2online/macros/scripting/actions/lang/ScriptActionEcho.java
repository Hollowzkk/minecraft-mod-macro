/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.ReturnValueChat;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
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
/*    */ 
/*    */ public class ScriptActionEcho
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionEcho(ScriptContext context) {
/* 25 */     super(context, "echo");
/* 26 */     this.parseVars = false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 32 */     return (IReturnValue)new ReturnValueChat(provider.expand(macro, rawParams, false));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 44 */     return "chat";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionEcho.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */