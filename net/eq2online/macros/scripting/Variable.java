/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.api.IArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.ICounterProvider;
/*     */ import net.eq2online.macros.scripting.api.IFlagProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMutableArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.IStringProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Variable
/*     */ {
/*     */   public static final String PREFIX_SHARED = "@";
/*     */   public static final String PREFIX_STRING = "&";
/*     */   public static final String PREFIX_INT = "#";
/*     */   public static final String PREFIX_BOOL = "";
/*     */   public static final String SUFFIX_ARRAY = "[]";
/*     */   public static final String PREFIX_TYPES = "#&";
/*     */   
/*     */   public enum Type
/*     */   {
/*  32 */     FLAG,
/*  33 */     COUNTER,
/*  34 */     STRING;
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
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final Pattern variableNamePattern = Pattern.compile("^(@?)([#&]?)([a-z~]([a-z0-9_\\-]*))(\\[([0-9]{1,5})\\])?$", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final Pattern arrayVariablePattern = Pattern.compile("\\[([0-9]{1,5})\\]$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IArrayProvider arrayProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMacro macro;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IVariableProvider variableProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Type type;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isShared;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String prefix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String variableName;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String qualifiedName;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int arrayOffset;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Variable(IVariableProvider variableProvider, List<IArrayProvider> arrayProviders, IMacro macro, String sharedPrefix, String typePrefix, String variableName, String arrayFormat, String arrayIndex, boolean array) {
/* 103 */     this.variableProvider = variableProvider;
/* 104 */     this.macro = macro;
/* 105 */     this.isShared = sharedPrefix.equals("@");
/* 106 */     this.prefix = typePrefix;
/* 107 */     this.variableName = variableName;
/* 108 */     this.qualifiedName = typePrefix + variableName;
/* 109 */     this.type = initType();
/* 110 */     this.arrayProvider = initArrayProvider(arrayProviders, arrayFormat, array);
/* 111 */     this.arrayOffset = initArrayOffset(arrayFormat, arrayIndex, array);
/*     */   }
/*     */ 
/*     */   
/*     */   private Type initType() {
/* 116 */     if (this.prefix.equals("#"))
/*     */     {
/* 118 */       return Type.COUNTER;
/*     */     }
/* 120 */     if (this.prefix.equals("&"))
/*     */     {
/* 122 */       return Type.STRING;
/*     */     }
/* 124 */     return Type.FLAG;
/*     */   }
/*     */ 
/*     */   
/*     */   private IArrayProvider initArrayProvider(List<IArrayProvider> arrayProviders, String arrayFormat, boolean array) {
/* 129 */     IArrayProvider iArrayProvider = null;
/* 130 */     if (arrayFormat != null || array) {
/*     */       IMutableArrayProvider iMutableArrayProvider;
/* 132 */       if (this.isShared) {
/*     */         
/* 134 */         if (this.variableProvider instanceof IArrayProvider)
/*     */         {
/* 136 */           iArrayProvider = (IArrayProvider)this.variableProvider;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 141 */         iMutableArrayProvider = this.macro.getArrayProvider();
/*     */       } 
/*     */       
/* 144 */       if (iMutableArrayProvider == null || !iMutableArrayProvider.checkArrayExists(this.qualifiedName))
/*     */       {
/* 146 */         for (IArrayProvider otherProvider : arrayProviders) {
/*     */           
/* 148 */           if (otherProvider.checkArrayExists(this.qualifiedName)) {
/*     */             
/* 150 */             iArrayProvider = otherProvider;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 156 */     return iArrayProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   private int initArrayOffset(String arrayFormat, String arrayIndex, boolean array) {
/* 161 */     if (arrayFormat != null && arrayIndex != null)
/*     */     {
/* 163 */       return Math.max(0, Integer.parseInt(arrayIndex));
/*     */     }
/* 165 */     if (array)
/*     */     {
/* 167 */       return 0;
/*     */     }
/*     */     
/* 170 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 176 */     return String.format("Variable(%s)[%s] %s", new Object[] { this.qualifiedName, Integer.valueOf(this.arrayOffset), this.arrayProvider });
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 181 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShared() {
/* 186 */     return this.isShared;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 191 */     return this.prefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVariableName() {
/* 196 */     return this.variableName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQualifiedName() {
/* 201 */     return this.qualifiedName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getArrayOffset() {
/* 206 */     return this.arrayOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArray() {
/* 214 */     return (this.arrayProvider != null && this.arrayOffset > -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICounterProvider getCounterProvider() {
/* 222 */     return this.isShared ? ((this.variableProvider instanceof ICounterProvider) ? (ICounterProvider)this.variableProvider : null) : this.macro.getCounterProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IFlagProvider getFlagProvider() {
/* 230 */     return this.isShared ? ((this.variableProvider instanceof IFlagProvider) ? (IFlagProvider)this.variableProvider : null) : this.macro.getFlagProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IStringProvider getStringProvider() {
/* 238 */     return this.isShared ? ((this.variableProvider instanceof IStringProvider) ? (IStringProvider)this.variableProvider : null) : this.macro.getStringProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFlag() {
/* 246 */     if (this.type != Type.FLAG) return false; 
/* 247 */     IFlagProvider flagProvider = getFlagProvider();
/* 248 */     return (flagProvider != null) ? flagProvider.getFlag(this.variableName, this.arrayOffset) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCounter() {
/* 256 */     if (this.type != Type.COUNTER) return 0; 
/* 257 */     ICounterProvider counterProvider = getCounterProvider();
/* 258 */     return (counterProvider != null) ? counterProvider.getCounter(this.variableName, this.arrayOffset) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString() {
/* 266 */     if (this.type != Type.STRING) return ""; 
/* 267 */     IStringProvider stringProvider = getStringProvider();
/* 268 */     return (stringProvider != null) ? stringProvider.getString(this.variableName, this.arrayOffset) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(boolean newValue) {
/* 276 */     if (this.type == Type.FLAG) {
/*     */       
/* 278 */       IFlagProvider flagProvider = getFlagProvider();
/* 279 */       if (flagProvider != null) flagProvider.setFlag(this.variableName, this.arrayOffset, newValue);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCounter(int newValue) {
/* 288 */     if (this.type == Type.COUNTER) {
/*     */       
/* 290 */       ICounterProvider counterProvider = getCounterProvider();
/* 291 */       if (counterProvider != null) counterProvider.setCounter(this.variableName, this.arrayOffset, newValue);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String newValue) {
/* 300 */     if (this.type == Type.STRING) {
/*     */       
/* 302 */       IStringProvider stringProvider = getStringProvider();
/* 303 */       if (stringProvider != null) stringProvider.setString(this.variableName, this.arrayOffset, newValue);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unSetFlag() {
/* 312 */     if (this.type == Type.FLAG) {
/*     */       
/* 314 */       IFlagProvider flagProvider = getFlagProvider();
/* 315 */       if (flagProvider != null) flagProvider.unsetFlag(this.variableName, this.arrayOffset);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unSetCounter() {
/* 324 */     if (this.type == Type.COUNTER) {
/*     */       
/* 326 */       ICounterProvider counterProvider = getCounterProvider();
/* 327 */       if (counterProvider != null) counterProvider.unsetCounter(this.variableName, this.arrayOffset);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unSetString() {
/* 336 */     if (this.type == Type.STRING) {
/*     */       
/* 338 */       IStringProvider stringProvider = getStringProvider();
/* 339 */       if (stringProvider != null) stringProvider.unsetString(this.variableName, this.arrayOffset);
/*     */     
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
/*     */   
/*     */   public static Variable getVariable(IVariableProvider variableProvider, List<IArrayProvider> arrayProviders, IMacro macro, String variableName) {
/* 353 */     return getVariable(variableProvider, arrayProviders, macro, variableName, false);
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
/*     */   public static Variable getArrayVariable(IVariableProvider variableProvider, List<IArrayProvider> arrayProviders, IMacro macro, String variableName) {
/* 366 */     return getVariable(variableProvider, arrayProviders, macro, variableName, true);
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
/*     */   private static Variable getVariable(IVariableProvider variableProvider, List<IArrayProvider> arrayProviders, IMacro macro, String variableName, boolean assumeArray) {
/* 378 */     Matcher var = variableNamePattern.matcher(variableName);
/*     */     
/* 380 */     if (var.matches())
/*     */     {
/* 382 */       return new Variable(variableProvider, arrayProviders, macro, var.group(1), var.group(2), var.group(3), var.group(5), var.group(6), assumeArray);
/*     */     }
/* 384 */     if (assumeArray && couldBeArraySpecifier(variableName)) {
/*     */       
/* 386 */       var = variableNamePattern.matcher(getValidVariableOrArraySpecifier(variableName));
/*     */       
/* 388 */       if (var.matches())
/*     */       {
/* 390 */         return new Variable(variableProvider, arrayProviders, macro, var.group(1), var.group(2), var.group(3), var.group(5), var.group(6), assumeArray);
/*     */       }
/*     */     } 
/*     */     
/* 394 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean isValidVariableName(String variableName) {
/* 405 */     return variableNamePattern.matcher(variableName).matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean isValidScalarVariableName(String variableName) {
/* 416 */     Matcher matcher = variableNamePattern.matcher(variableName);
/* 417 */     return (matcher.matches() && matcher.group(5) == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidVariableOrArraySpecifier(String variableName) {
/* 422 */     String name = getValidVariableOrArraySpecifier(variableName);
/* 423 */     if (name != null) return true;
/*     */     
/* 425 */     String expandedVariableName = (new VariableExpander(null, null, variableName, false, "var")).toString();
/* 426 */     return (getValidVariableOrArraySpecifier(expandedVariableName) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getValidVariableOrArraySpecifier(String variableName) {
/* 431 */     if (isValidVariableName(variableName)) return variableName; 
/* 432 */     if (couldBeArraySpecifier(variableName) && isValidVariableName(variableName.substring(0, variableName.length() - 2)))
/*     */     {
/* 434 */       return variableName.substring(0, variableName.length() - 2);
/*     */     }
/*     */     
/* 437 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean couldBeArraySpecifier(String variableName) {
/* 446 */     return (variableName != null && variableName.endsWith("[]") && variableName.length() > 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean couldBeString(String variableName) {
/* 457 */     if (variableName != null && variableName.startsWith("@")) variableName = variableName.substring(1); 
/* 458 */     return (variableName != null && variableName.startsWith("&"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean couldBeInt(String variableName) {
/* 467 */     if (variableName != null && variableName.startsWith("@")) variableName = variableName.substring(1); 
/* 468 */     return (variableName != null && variableName.startsWith("#"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean couldBeBoolean(String variableName) {
/* 473 */     if (variableName != null && variableName.startsWith("@")) variableName = variableName.substring(1); 
/* 474 */     return (variableName != null && variableName.matches("^[a-z]([a-z0-9_\\-]*)$"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayPush(String value) {
/* 479 */     if (this.arrayProvider instanceof IMutableArrayProvider)
/*     */     {
/* 481 */       ((IMutableArrayProvider)this.arrayProvider).push(this.qualifiedName, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayPut(String value) {
/* 487 */     if (this.arrayProvider instanceof IMutableArrayProvider)
/*     */     {
/* 489 */       ((IMutableArrayProvider)this.arrayProvider).put(this.qualifiedName, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String arrayPop() {
/* 495 */     if (this.arrayProvider instanceof IMutableArrayProvider)
/*     */     {
/* 497 */       return ((IMutableArrayProvider)this.arrayProvider).pop(this.qualifiedName);
/*     */     }
/*     */     
/* 500 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayIndexOf(String search, boolean caseSensitive) {
/* 505 */     return (this.arrayProvider != null) ? this.arrayProvider.indexOf(this.qualifiedName, search, caseSensitive) : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayClear() {
/* 510 */     if (this.arrayProvider instanceof IMutableArrayProvider)
/*     */     {
/* 512 */       ((IMutableArrayProvider)this.arrayProvider).clear(this.qualifiedName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object arrayGetValue(int offset) {
/* 518 */     return (this.arrayProvider != null) ? this.arrayProvider.getArrayVariableValue(this.qualifiedName, offset) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayGetMaxIndex() {
/* 523 */     return (this.arrayProvider != null) ? this.arrayProvider.getMaxArrayIndex(this.qualifiedName) : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean arrayExists() {
/* 528 */     return (this.arrayProvider != null) ? this.arrayProvider.checkArrayExists(this.qualifiedName) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\Variable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */