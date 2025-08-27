/*    */ package net.eq2online.macros.compatibility;
/*    */ 
/*    */ import bib;
/*    */ import ceo;
/*    */ import cep;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AllowedCharacters
/*    */ {
/* 17 */   public static final String CHARACTERS = build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public static final char[] SPECIAL = new char[] { '/', '\n', '\r', '\t', Character.MIN_VALUE, '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String build() {
/* 31 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 33 */     BufferedReader reader = null;
/*    */     
/*    */     try {
/* 36 */       cep resourceManager = bib.z().O();
/* 37 */       ceo fontResource = resourceManager.a(ResourceLocations.FONT);
/*    */       
/* 39 */       reader = new BufferedReader(new InputStreamReader(fontResource.b()));
/* 40 */       String line = "";
/* 41 */       while ((line = reader.readLine()) != null)
/*    */       {
/* 43 */         if (!line.startsWith("#"))
/*    */         {
/* 45 */           sb.append(line);
/*    */         }
/*    */       }
/*    */     
/* 49 */     } catch (Exception exception) {
/*    */ 
/*    */     
/*    */     } finally {
/*    */       
/* 54 */       if (reader != null) {
/*    */         
/*    */         try {
/*    */           
/* 58 */           reader.close();
/*    */         }
/* 60 */         catch (IOException iOException) {}
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 66 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public static final boolean isAllowed(char c) {
/* 71 */     return (c != '§' && (CHARACTERS.indexOf(c) >= 0 || c > ' '));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String filerAllowedCharacters(String text) {
/* 80 */     StringBuilder sb = new StringBuilder();
/* 81 */     char[] chars = text.toCharArray();
/* 82 */     for (int index = 0; index < chars.length; index++) {
/*    */       
/* 84 */       char c = chars[index];
/* 85 */       if (isAllowed(c))
/*    */       {
/* 87 */         sb.append(c);
/*    */       }
/*    */     } 
/*    */     
/* 91 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\compatibility\AllowedCharacters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */