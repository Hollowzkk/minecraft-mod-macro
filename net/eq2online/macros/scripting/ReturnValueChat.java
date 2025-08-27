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
/*    */ public class ReturnValueChat
/*    */   implements IReturnValue
/*    */ {
/*    */   private final String message;
/*    */   
/*    */   public ReturnValueChat(String message) {
/* 22 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isVoid() {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getBoolean() {
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInteger() {
/* 49 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getString() {
/* 58 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalMessage() {
/* 67 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRemoteMessage() {
/* 76 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ReturnValueChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */