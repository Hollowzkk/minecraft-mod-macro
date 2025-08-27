/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IParameterProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.variable.VariableCache;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroExecVariableProvider
/*     */   extends VariableCache
/*     */   implements IParameterProvider
/*     */ {
/*     */   private final List<Object> parameters;
/*     */   
/*     */   public MacroExecVariableProvider(String[] parameters, int ignore, IScriptActionProvider provider, IMacro macro) {
/*  34 */     this.parameters = new ArrayList(parameters.length - ignore);
/*     */     
/*  36 */     for (int paramIndex = ignore; paramIndex < parameters.length; paramIndex++) {
/*     */       
/*  38 */       String parameter = provider.expand(macro, parameters[paramIndex], false);
/*  39 */       if (parameter.matches("^\\-?[0-9\\.]+$")) {
/*     */         
/*  41 */         Integer paramValue = Integer.valueOf(Integer.parseInt(parameter));
/*  42 */         this.parameters.add(paramValue);
/*     */       }
/*  44 */       else if ("true".equalsIgnoreCase(parameter)) {
/*     */         
/*  46 */         this.parameters.add(Boolean.TRUE);
/*     */       }
/*  48 */       else if ("false".equalsIgnoreCase(parameter)) {
/*     */         
/*  50 */         this.parameters.add(Boolean.FALSE);
/*     */       }
/*     */       else {
/*     */         
/*  54 */         this.parameters.add(parameter);
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     storeParametersAsVariables();
/*     */   }
/*     */ 
/*     */   
/*     */   private void storeParametersAsVariables() {
/*  63 */     int variableIndex = 1;
/*     */     
/*  65 */     for (Object parameter : this.parameters) {
/*     */       
/*  67 */       if (parameter instanceof Integer) {
/*     */         
/*  69 */         storeVariable(String.format("#var%d", new Object[] { Integer.valueOf(variableIndex++) }), ((Integer)parameter).intValue()); continue;
/*     */       } 
/*  71 */       if (parameter instanceof Boolean) {
/*     */         
/*  73 */         storeVariable(String.format("var%d", new Object[] { Integer.valueOf(variableIndex++) }), ((Boolean)parameter).booleanValue());
/*     */         
/*     */         continue;
/*     */       } 
/*  77 */       storeVariable(String.format("&var%d", new Object[] { Integer.valueOf(variableIndex++) }), parameter.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/*  85 */     return getCachedValue(variableName);
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
/*     */   public String provideParameters(String macro) {
/*  98 */     if (this.parameters != null) {
/*     */       
/* 100 */       int variableIndex = 1;
/*     */       
/* 102 */       for (Object parameter : this.parameters)
/*     */       {
/* 104 */         macro = macro.replaceAll("\\x24\\x24\\[" + variableIndex++ + "\\]", parameter.toString());
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return macro.replaceAll("\\x24\\x24\\[[0-9]+\\]", "");
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroExecVariableProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */