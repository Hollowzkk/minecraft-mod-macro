/*    */ package net.eq2online.macros.compatibility;
/*    */ 
/*    */ import bja;
/*    */ import blg;
/*    */ import java.io.IOException;
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
/*    */ public class DelegateDisconnect
/*    */   extends blg
/*    */ {
/*    */   public void a(int mouseX, int mouseY, float partialTick) {
/* 22 */     super.a(mouseX, mouseY, partialTick);
/*    */     
/*    */     try {
/* 25 */       a(new bja(1, 0, 0, ""));
/*    */     }
/* 27 */     catch (IOException iOException) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\compatibility\DelegateDisconnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */