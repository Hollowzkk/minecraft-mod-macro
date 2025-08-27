/*    */ package net.eq2online.macros.compatibility;
/*    */ 
/*    */ import net.eq2online.macros.interfaces.ILocalisationProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class I18n
/*    */ {
/*    */   private static ILocalisationProvider provider;
/*    */   
/*    */   public static void setProvider(ILocalisationProvider provider) {
/* 14 */     I18n.provider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getRaw(String key) {
/* 19 */     return (provider != null) ? provider.getRaw(key) : key;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String get(String key) {
/* 24 */     return (provider != null) ? provider.get(key) : key;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String get(String key, Object... params) {
/* 29 */     return (provider != null) ? provider.get(key, params) : key;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\compatibility\I18n.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */