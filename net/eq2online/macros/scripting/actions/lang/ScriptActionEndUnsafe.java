/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionEndUnsafe
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionEndUnsafe(ScriptContext context) {
/* 11 */     super(context, "endunsafe");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 17 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStackPopOperator() {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesConditionalOperator(IScriptAction action) {
/* 29 */     return action instanceof ScriptActionUnsafe;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionEndUnsafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */