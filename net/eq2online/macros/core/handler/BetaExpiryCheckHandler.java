/*    */ package net.eq2online.macros.core.handler;
/*    */ 
/*    */ import bib;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ import rd;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BetaExpiryCheckHandler
/*    */   extends Thread
/*    */ {
/*    */   private final String version;
/*    */   private volatile boolean expired = false;
/*    */   
/*    */   public BetaExpiryCheckHandler(String version) {
/* 17 */     this.version = version;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 25 */       URL checkURL = new URL("http://eq2.co.uk/expiry.php?ver=" + this.version);
/* 26 */       String response = rd.a(checkURL, new HashMap<>(), true, bib.z().M());
/* 27 */       if ("yes".equals(response.trim())) this.expired = true;
/*    */     
/* 29 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpired() {
/* 34 */     return this.expired;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\handler\BetaExpiryCheckHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */