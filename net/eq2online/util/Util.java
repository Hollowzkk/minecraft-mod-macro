/*     */ package net.eq2online.util;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   public static String convertAmpCodes(String text) {
/*  18 */     return text.replaceAll("(?<!&)&([0-9a-fklmnor])", "§$1").replaceAll("&&", "&");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String stringToHighlightMask(String highlighted) {
/*  29 */     StringBuilder mask = new StringBuilder();
/*  30 */     char colour = 'f';
/*     */     
/*  32 */     for (int i = 0; i < highlighted.length(); i++) {
/*     */       
/*  34 */       char c = highlighted.charAt(i);
/*     */       
/*  36 */       if (c == '§' && i < highlighted.length() - 1) {
/*     */         
/*  38 */         colour = highlighted.charAt(i + 1);
/*  39 */         i++;
/*     */       }
/*     */       else {
/*     */         
/*  43 */         mask.append(colour);
/*     */       } 
/*     */     } 
/*  46 */     return mask.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sanitiseFileName(String fileName, String extension) {
/*  58 */     if (fileName.length() > 0) {
/*     */       
/*  60 */       if (!fileName.toLowerCase().endsWith(extension)) {
/*     */         
/*  62 */         if (fileName.lastIndexOf('.') > 0)
/*     */         {
/*  64 */           fileName = fileName.substring(0, fileName.lastIndexOf('.'));
/*     */         }
/*     */         
/*  67 */         fileName = fileName + extension;
/*     */       } 
/*     */ 
/*     */       
/*  71 */       if (fileName.startsWith("."))
/*     */       {
/*  73 */         fileName = fileName.substring(1);
/*     */       }
/*     */ 
/*     */       
/*  77 */       if (fileName.length() > 4)
/*     */       {
/*  79 */         return fileName;
/*     */       }
/*     */     } 
/*     */     
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parsePositiveInt(String value, int defaultValue) {
/*  96 */     if (Strings.isNullOrEmpty(value))
/*     */     {
/*  98 */       return 0;
/*     */     }
/*     */     
/* 101 */     Matcher numeric = Pattern.compile("^(\\d+)").matcher(value);
/* 102 */     if (numeric.find())
/*     */     {
/* 104 */       value = numeric.group(1);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 109 */       int i = Integer.parseInt(value);
/* 110 */       return i;
/*     */     }
/* 112 */     catch (Exception ex) {
/*     */       
/* 114 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOrdinalSuffix(int value) {
/* 126 */     int teenRemainder = value % 100;
/* 127 */     if (teenRemainder > 9 && teenRemainder < 21) return "th"; 
/* 128 */     switch (value % 10) {
/*     */       case 1:
/* 130 */         return "st";
/* 131 */       case 2: return "nd";
/* 132 */       case 3: return "rd";
/* 133 */     }  return "th";
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2onlin\\util\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */