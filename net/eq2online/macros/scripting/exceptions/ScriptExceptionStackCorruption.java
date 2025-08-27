/*    */ package net.eq2online.macros.scripting.exceptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptExceptionStackCorruption
/*    */   extends ScriptException
/*    */ {
/*    */   private static final long serialVersionUID = -3613874587143317694L;
/*    */   
/*    */   public ScriptExceptionStackCorruption() {
/* 12 */     super("Stack corruption in script");
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionStackCorruption(String arg0) {
/* 17 */     super(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionStackCorruption(Throwable arg0) {
/* 22 */     super(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionStackCorruption(String arg0, Throwable arg1) {
/* 27 */     super(arg0, arg1);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\exceptions\ScriptExceptionStackCorruption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */