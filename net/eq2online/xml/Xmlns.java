/*    */ package net.eq2online.xml;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.NamespaceContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Xmlns
/*    */   implements NamespaceContext
/*    */ {
/* 17 */   private Map<String, String> prefixes = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void addPrefix(String prefix, String namespaceURI) {
/* 21 */     this.prefixes.put(prefix, namespaceURI);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 26 */     this.prefixes.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<String> getPrefixes(String namespaceURI) {
/* 32 */     return this.prefixes.keySet().iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPrefix(String namespaceURI) {
/* 38 */     for (Map.Entry<String, String> prefix : this.prefixes.entrySet()) {
/*    */       
/* 40 */       if (((String)prefix.getValue()).equals(namespaceURI)) {
/* 41 */         return prefix.getKey();
/*    */       }
/*    */     } 
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNamespaceURI(String prefix) {
/* 50 */     return this.prefixes.get(prefix);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\xml\Xmlns.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */