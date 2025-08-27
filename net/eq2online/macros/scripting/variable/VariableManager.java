/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import bib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.IArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.ICounterProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroEngine;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ITickableVariableProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableListener;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProviderShared;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import rl;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VariableManager
/*     */   implements IScriptActionProvider
/*     */ {
/*     */   protected final bib mc;
/*     */   protected IVariableProviderShared sharedVariableProvider;
/*  32 */   private List<IVariableProvider> variableProviders = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private List<IVariableListener> variableListeners = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private List<IArrayProvider> arrayProviders = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public VariableManager(bib minecraft) {
/*  46 */     this.mc = minecraft;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  52 */     for (IVariableProvider variableProvider : this.variableProviders) {
/*     */       
/*  54 */       if (variableProvider instanceof ITickableVariableProvider)
/*     */       {
/*  56 */         ((ITickableVariableProvider)variableProvider).onTick();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IVariableProviderShared getSharedVariableProvider() {
/*  68 */     return this.sharedVariableProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableProvider(IVariableProvider variableProvider) {
/*  79 */     if (!this.variableProviders.contains(variableProvider)) {
/*     */       
/*  81 */       this.variableProviders.add(variableProvider);
/*     */       
/*  83 */       if (variableProvider instanceof IArrayProvider && variableProvider != this.sharedVariableProvider)
/*     */       {
/*  85 */         this.arrayProviders.add((IArrayProvider)variableProvider);
/*     */       }
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
/*     */   public void unregisterVariableProvider(IVariableProvider variableProvider) {
/*  98 */     this.variableProviders.remove(variableProvider);
/*  99 */     this.arrayProviders.remove(variableProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariableProviders(boolean clock) {
/* 109 */     rl mcProfiler = this.mc.B;
/* 110 */     IVariableProvider faultingProvider = null;
/*     */     
/* 112 */     mcProfiler.a("VariableManager");
/*     */ 
/*     */     
/*     */     try {
/* 116 */       for (IVariableProvider variableProvider : this.variableProviders)
/*     */       {
/* 118 */         faultingProvider = variableProvider;
/* 119 */         mcProfiler.c(variableProvider.getClass().getSimpleName());
/* 120 */         variableProvider.updateVariables(clock);
/*     */       }
/*     */     
/* 123 */     } catch (Exception ex) {
/*     */       
/* 125 */       Log.printStackTrace(ex);
/*     */       
/* 127 */       if (faultingProvider != null) {
/*     */         
/* 129 */         this.variableProviders.remove(faultingProvider);
/* 130 */         actionAddChatMessage("§4[MACROS] Critical error, removing variable provider " + faultingProvider.getClass().getSimpleName());
/* 131 */         actionAddChatMessage("§4[MACROS] Error was: " + ex.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     mcProfiler.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IVariableProvider getProviderForVariable(String variableName) {
/* 145 */     updateVariableProviders(false);
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
/* 157 */     for (IVariableProvider variableProvider : this.variableProviders) {
/*     */       
/* 159 */       if (variableProvider.getVariable(variableName) != null)
/*     */       {
/* 161 */         return variableProvider;
/*     */       }
/*     */     } 
/*     */     
/* 165 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableListener(IVariableListener variableListener) {
/* 176 */     if (!this.variableListeners.contains(variableListener))
/*     */     {
/* 178 */       this.variableListeners.add(variableListener);
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
/*     */   public void unregisterVariableListener(IVariableListener variableListener) {
/* 190 */     this.variableListeners.remove(variableListener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String expand(IMacro macro, String text, boolean quoteStrings) {
/* 196 */     return (new VariableExpander(this, macro, text, quoteStrings)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getEnvironmentVariables() {
/* 202 */     Set<String> env = new TreeSet<>();
/*     */     
/* 204 */     env.add("CONFIG");
/* 205 */     env.add("KEYID");
/* 206 */     env.add("KEYNAME");
/*     */     
/* 208 */     for (IVariableProvider variableProvider : this.variableProviders)
/*     */     {
/* 210 */       env.addAll(variableProvider.getVariables());
/*     */     }
/*     */     
/* 213 */     return env;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName, IVariableProvider inContextProvider) {
/* 224 */     IVariableProvider faultingProvider = null;
/*     */ 
/*     */     
/*     */     try {
/* 228 */       if (inContextProvider != null)
/*     */       {
/* 230 */         Object variableValue = inContextProvider.getVariable(variableName);
/* 231 */         if (variableValue != null)
/*     */         {
/* 233 */           return variableValue;
/*     */         }
/*     */       }
/*     */     
/* 237 */     } catch (Exception ex) {
/*     */       
/* 239 */       Log.printStackTrace(ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 244 */       for (IVariableProvider variableProvider : this.variableProviders)
/*     */       {
/* 246 */         faultingProvider = variableProvider;
/*     */         
/* 248 */         Object variableValue = variableProvider.getVariable(variableName);
/* 249 */         if (variableValue != null)
/*     */         {
/* 251 */           return variableValue;
/*     */         }
/*     */       }
/*     */     
/* 255 */     } catch (Exception ex) {
/*     */       
/* 257 */       Log.printStackTrace(ex);
/*     */       
/* 259 */       if (faultingProvider != null) {
/*     */         
/* 261 */         this.variableProviders.remove(faultingProvider);
/* 262 */         actionAddChatMessage("§4[MACROS] Critical error, removing variable provider " + faultingProvider.getClass().getSimpleName());
/* 263 */         actionAddChatMessage("§4[MACROS] Error was: " + ex.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName, IMacro macro) {
/* 278 */     if (macro != null) {
/*     */       
/* 280 */       if ("KEYID".equals(variableName)) return String.valueOf(macro.getID()); 
/* 281 */       if ("KEYNAME".equals(variableName)) return getMacroEngine().getMacroNameForId(macro.getID());
/*     */     
/*     */     } 
/* 284 */     IMacroEngine macros = getMacroEngine();
/*     */     
/* 286 */     if (macros != null)
/*     */     {
/* 288 */       if ("CONFIG".equals(variableName)) return macros.getActiveConfigName();
/*     */     
/*     */     }
/* 291 */     return getVariable(variableName, (macro != null) ? (IVariableProvider)macro : (IVariableProvider)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFlagValue(IMacro macro, String flagName) {
/* 302 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, flagName);
/*     */     
/* 304 */     if (var != null) {
/*     */       String stringValue;
/* 306 */       switch (var.getType()) {
/*     */         
/*     */         case FLAG:
/* 309 */           return var.getFlag();
/*     */         
/*     */         case COUNTER:
/* 312 */           return (var.getCounter() == 1);
/*     */         
/*     */         case STRING:
/* 315 */           stringValue = var.getString();
/* 316 */           return ("1".equals(stringValue) || "true".equalsIgnoreCase(stringValue));
/*     */       } 
/*     */       
/* 319 */       throw new IllegalStateException("Unrecognised variable type: " + var.getType());
/*     */     } 
/*     */ 
/*     */     
/* 323 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlagVariable(IMacro macro, String flagName, boolean value) {
/* 334 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, flagName);
/* 335 */     if (var != null) {
/*     */       
/* 337 */       macro.markDirty();
/*     */       
/* 339 */       switch (var.getType()) {
/*     */         
/*     */         case FLAG:
/* 342 */           var.setFlag(value);
/*     */           return;
/*     */         
/*     */         case COUNTER:
/* 346 */           var.setCounter(value ? 1 : 0);
/*     */           return;
/*     */         
/*     */         case STRING:
/* 350 */           var.setString(value ? "true" : "false");
/*     */           return;
/*     */       } 
/*     */       
/* 354 */       throw new IllegalStateException("Unrecognised variable type: " + var.getType());
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
/*     */   public void setVariable(IMacro macro, String variableName, String variableValue, int intValue, boolean boolValue) {
/* 367 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, variableName);
/* 368 */     if (var != null) {
/*     */       
/* 370 */       if (macro != null)
/*     */       {
/* 372 */         macro.markDirty();
/*     */       }
/*     */       
/* 375 */       switch (var.getType()) {
/*     */         
/*     */         case FLAG:
/* 378 */           var.setFlag(boolValue);
/*     */           return;
/*     */         
/*     */         case COUNTER:
/* 382 */           var.setCounter(intValue);
/*     */           return;
/*     */         
/*     */         case STRING:
/* 386 */           var.setString(variableValue);
/*     */           return;
/*     */       } 
/*     */       
/* 390 */       throw new IllegalStateException("Unrecognised variable type: " + var.getType());
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
/*     */   public void setVariable(IMacro macro, String variableName, IReturnValue returnValue) {
/* 403 */     setVariable(macro, variableName, returnValue.getString(), returnValue.getInteger(), returnValue.getBoolean());
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
/*     */   public void setVariable(IMacro macro, String variableName, String variableValue) {
/* 417 */     int intValue = ScriptCore.tryParseInt(variableValue, 0);
/* 418 */     boolean boolValue = ScriptCore.parseBoolean(variableValue, intValue);
/*     */ 
/*     */     
/* 421 */     if (variableValue == null) variableValue = "";
/*     */     
/* 423 */     setVariable(macro, variableName, variableValue, intValue, boolValue);
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
/*     */   public void setVariable(IMacro macro, String variableName, int variableValue) {
/* 435 */     if (variableName.length() > 0)
/*     */     {
/* 437 */       setVariable(macro, variableName, String.valueOf(variableValue), variableValue, (variableValue != 0));
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
/*     */   public void unsetVariable(IMacro macro, String variableName) {
/* 449 */     if (Variable.couldBeArraySpecifier(variableName)) {
/*     */       
/* 451 */       String arrayName = Variable.getValidVariableOrArraySpecifier(variableName);
/*     */       
/* 453 */       if (arrayName != null) {
/*     */         
/* 455 */         clearArray(macro, arrayName);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 460 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, variableName);
/* 461 */     if (var != null) {
/*     */       
/* 463 */       macro.markDirty();
/* 464 */       switch (var.getType()) {
/*     */         
/*     */         case FLAG:
/* 467 */           var.unSetFlag();
/*     */           return;
/*     */         
/*     */         case COUNTER:
/* 471 */           var.unSetCounter();
/*     */           return;
/*     */         
/*     */         case STRING:
/* 475 */           var.unSetString();
/*     */           return;
/*     */       } 
/*     */       
/* 479 */       throw new IllegalStateException("Unrecognised variable type: " + var.getType());
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
/*     */   public void incrementCounterVariable(IMacro macro, String counterName, int increment) {
/* 492 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, counterName);
/* 493 */     if (var != null && var.getType() == Variable.Type.COUNTER) {
/*     */       
/* 495 */       macro.markDirty();
/* 496 */       ICounterProvider counterProvider = var.getCounterProvider();
/* 497 */       if (counterProvider != null) counterProvider.incrementCounter(var.getVariableName(), var.getArrayOffset(), increment);
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
/*     */   public void pushValueToArray(IMacro macro, String arrayName, String variableValue) {
/* 509 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 510 */     if (var != null) {
/*     */       
/* 512 */       if (!var.isShared()) macro.markDirty(); 
/* 513 */       var.arrayPush(variableValue);
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
/*     */   public void putValueToArray(IMacro macro, String arrayName, String variableValue) {
/* 525 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 526 */     if (var != null) {
/*     */       
/* 528 */       if (!var.isShared()) macro.markDirty(); 
/* 529 */       var.arrayPut(variableValue);
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
/*     */   public String popValueFromArray(IMacro macro, String arrayName) {
/* 541 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 542 */     if (var != null) {
/*     */       
/* 544 */       if (!var.isShared()) macro.markDirty(); 
/* 545 */       return var.arrayPop();
/*     */     } 
/*     */     
/* 548 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArrayIndexOf(IMacro macro, String arrayName, String search, boolean caseSensitive) {
/* 559 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 560 */     if (var != null)
/*     */     {
/* 562 */       return var.arrayIndexOf(search, caseSensitive);
/*     */     }
/*     */     
/* 565 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearArray(IMacro macro, String arrayName) {
/* 575 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 576 */     if (var != null) {
/*     */       
/* 578 */       if (!var.isShared()) macro.markDirty(); 
/* 579 */       var.arrayClear();
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
/*     */   public Object getArrayElement(IMacro macro, String arrayName, int offset) {
/* 591 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 592 */     if (var != null)
/*     */     {
/* 594 */       return var.arrayGetValue(offset);
/*     */     }
/*     */     
/* 597 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArraySize(IMacro macro, String arrayName) {
/* 608 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 609 */     if (var != null)
/*     */     {
/* 611 */       return var.arrayGetMaxIndex() + 1;
/*     */     }
/*     */     
/* 614 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getArrayExists(IMacro macro, String arrayName) {
/* 625 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 626 */     if (var != null)
/*     */     {
/* 628 */       return var.arrayExists();
/*     */     }
/*     */     
/* 631 */     return false;
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
/*     */   protected String sanitiseArrayVariableName(IMacro macro, String arrayName) {
/* 643 */     if (Variable.isValidVariableName(arrayName)) {
/*     */       
/* 645 */       Matcher arrayIndexMatcher = Variable.arrayVariablePattern.matcher(arrayName);
/* 646 */       return arrayIndexMatcher.find() ? arrayName.substring(0, arrayName.indexOf('[')) : arrayName;
/*     */     } 
/*     */     
/* 649 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSoundResourceNamespace() {
/* 655 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\VariableManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */