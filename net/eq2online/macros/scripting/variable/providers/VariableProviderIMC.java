/*    */ package net.eq2online.macros.scripting.variable.providers;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.console.Log;
/*    */ import net.eq2online.macros.scripting.variable.VariableCache;
/*    */ 
/*    */ public class VariableProviderIMC
/*    */   extends VariableCache
/*    */ {
/* 10 */   private static final Pattern PATTERN_VARNAME = Pattern.compile("^[A-Z][A-Z_]*[A-Z]$");
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 15 */     return getCachedValue(variableName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVariable(String name, Object value) {
/* 20 */     if (name != null && PATTERN_VARNAME.matcher(name).matches()) {
/*    */       
/* 22 */       if (value instanceof Integer)
/*    */       {
/* 24 */         storeVariable(name, ((Integer)value).intValue());
/*    */       }
/* 26 */       else if (value instanceof Boolean)
/*    */       {
/* 28 */         storeVariable(name, ((Boolean)value).booleanValue());
/*    */       }
/* 30 */       else if (value instanceof String)
/*    */       {
/* 32 */         storeVariable(name, (String)value);
/*    */       }
/*    */       else
/*    */       {
/* 36 */         Log.info("Not storing unknown value for IMC variable {0}", new Object[] { name });
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 41 */       Log.info("Invalid environment variable specified by IMC provider: {0}", new Object[] { name });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderIMC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */