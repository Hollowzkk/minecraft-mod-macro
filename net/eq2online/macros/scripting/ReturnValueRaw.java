/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ import hh;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ 
/*    */ public class ReturnValueRaw
/*    */   implements IReturnValue
/*    */ {
/*    */   private final hh text;
/*    */   
/*    */   public ReturnValueRaw(hh text) {
/* 12 */     this.text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isVoid() {
/* 18 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getBoolean() {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInteger() {
/* 30 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getString() {
/* 36 */     return this.text.d();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalMessage() {
/* 42 */     return this.text.d();
/*    */   }
/*    */ 
/*    */   
/*    */   public hh getRawMessage() {
/* 47 */     return this.text;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRemoteMessage() {
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ReturnValueRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */