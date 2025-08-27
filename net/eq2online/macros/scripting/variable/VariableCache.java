/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.api.IArrayProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VariableCache
/*     */   implements IArrayProvider, IVariableStore
/*     */ {
/*  21 */   private static Pattern PATTERN_VARIABLE = Pattern.compile("^([A-Z]+)(\\[([0-9]{1,5})\\])?$");
/*     */   
/*  23 */   private Map<String, Object> variables = new HashMap<>();
/*  24 */   private Map<String, Boolean> booleanVariables = new HashMap<>();
/*  25 */   private Map<String, Integer> intVariables = new HashMap<>();
/*  26 */   private Map<String, String> stringVariables = new HashMap<>();
/*     */   
/*  28 */   private Map<String, String[]> arrayVariables = (Map)new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String variableName, boolean variableValue) {
/*  33 */     this.variables.put(variableName, Boolean.valueOf(variableValue));
/*  34 */     this.booleanVariables.put(variableName, Boolean.valueOf(variableValue));
/*  35 */     this.intVariables.remove(variableName);
/*  36 */     this.stringVariables.remove(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String variableName, int variableValue) {
/*  42 */     this.variables.put(variableName, Integer.valueOf(variableValue));
/*  43 */     this.intVariables.put(variableName, Integer.valueOf(variableValue));
/*  44 */     this.booleanVariables.remove(variableName);
/*  45 */     this.stringVariables.remove(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String variableName, String variableValue) {
/*  51 */     this.variables.put(variableName, variableValue);
/*  52 */     this.stringVariables.put(variableName, variableValue);
/*  53 */     this.booleanVariables.remove(variableName);
/*  54 */     this.intVariables.remove(variableName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCachedVariable(String variableName, String[] variableValue) {
/*  59 */     this.arrayVariables.put(variableName, variableValue);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clearCachedVariables() {
/*  64 */     this.booleanVariables.clear();
/*  65 */     this.intVariables.clear();
/*  66 */     this.stringVariables.clear();
/*  67 */     this.arrayVariables.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean getCachedBooleanValue(String variableName) {
/*  72 */     return ((Boolean)this.booleanVariables.get(variableName)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getCachedIntegerValue(String variableName) {
/*  77 */     return ((Integer)this.intVariables.get(variableName)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getCachedStringValue(String variableName) {
/*  82 */     return this.stringVariables.get(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> T getCachedGenericValue(String variableName) {
/*  88 */     return (T)this.variables.get(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getCachedValue(String variableName) {
/*     */     try {
/*  95 */       Matcher varPatternMatcher = PATTERN_VARIABLE.matcher(variableName);
/*  96 */       if (varPatternMatcher.matches()) {
/*     */         
/*  98 */         String varName = varPatternMatcher.group(1);
/*  99 */         if (this.arrayVariables.containsKey(varName))
/*     */         {
/* 101 */           if (varPatternMatcher.group(3) != null)
/*     */           {
/* 103 */             int arrayIndex = Math.max(0, Integer.parseInt(varPatternMatcher.group(3)));
/* 104 */             String[] values = this.arrayVariables.get(varName);
/* 105 */             return (arrayIndex < values.length) ? values[arrayIndex] : "";
/*     */           }
/*     */         
/*     */         }
/*     */       } 
/* 110 */     } catch (Exception ex) {
/*     */       
/* 112 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 115 */     return this.variables.get(variableName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasCachedValue(String variableName) {
/* 120 */     return this.variables.containsKey(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 126 */     return this.variables.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(String arrayName, String value, boolean caseSensitive) {
/* 132 */     String[] array = this.arrayVariables.get(arrayName);
/* 133 */     if (array != null)
/*     */     {
/* 135 */       for (int index = 0; index < array.length; index++) {
/*     */         
/* 137 */         if ((!caseSensitive && array[index].equalsIgnoreCase(value)) || array[index].equals(value))
/*     */         {
/* 139 */           return index;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 144 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxArrayIndex(String arrayName) {
/* 150 */     String[] array = this.arrayVariables.get(arrayName);
/* 151 */     return (array != null) ? (array.length - 1) : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkArrayExists(String arrayName) {
/* 157 */     return this.arrayVariables.containsKey(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getArrayVariableValue(String arrayName, int offset) {
/* 163 */     String[] array = this.arrayVariables.get(arrayName);
/* 164 */     if (array != null)
/*     */     {
/* 166 */       return (offset > -1 && offset < array.length) ? array[offset] : "";
/*     */     }
/*     */     
/* 169 */     return null;
/*     */   }
/*     */   
/*     */   public void updateVariables(boolean clock) {}
/*     */   
/*     */   public void onInit() {}
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\VariableCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */