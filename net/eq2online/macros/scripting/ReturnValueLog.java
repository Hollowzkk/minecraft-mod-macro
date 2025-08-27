/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
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
/*    */ 
/*    */ 
/*    */ public class ReturnValueLog
/*    */   implements IReturnValue
/*    */ {
/*    */   public static final String CLEAR = "";
/*    */   private final String message;
/*    */   
/*    */   public ReturnValueLog(String message) {
/* 24 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isVoid() {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getBoolean() {
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInteger() {
/* 51 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getString() {
/* 60 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalMessage() {
/* 69 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRemoteMessage() {
/* 78 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ReturnValueLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */