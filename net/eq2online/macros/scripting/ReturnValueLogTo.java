/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ public class ReturnValueLogTo
/*    */   extends ReturnValueLog
/*    */ {
/*    */   private final String target;
/*    */   
/*    */   public ReturnValueLogTo(String message, String target) {
/*  9 */     super(message);
/* 10 */     this.target = target;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTarget() {
/* 15 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ReturnValueLogTo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */