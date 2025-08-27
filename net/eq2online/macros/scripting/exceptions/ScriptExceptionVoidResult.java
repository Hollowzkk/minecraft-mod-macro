/*    */ package net.eq2online.macros.scripting.exceptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptExceptionVoidResult
/*    */   extends ScriptException
/*    */ {
/*    */   private static final long serialVersionUID = 3687316087979193782L;
/*    */   
/*    */   public ScriptExceptionVoidResult(String arg0) {
/* 12 */     super("Attempted to assign a void result of " + arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionVoidResult(String arg0, Throwable arg1) {
/* 17 */     super("Attempted to assign a void result of " + arg0, arg1);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\exceptions\ScriptExceptionVoidResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */