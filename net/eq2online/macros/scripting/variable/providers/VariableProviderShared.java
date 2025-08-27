/*     */ package net.eq2online.macros.scripting.variable.providers;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IVariableProviderShared;
/*     */ import net.eq2online.macros.scripting.variable.ArrayStorage;
/*     */ import net.eq2online.macros.scripting.variable.VariableProviderArray;
/*     */ import net.eq2online.xml.IArrayStorageBundle;
/*     */ import net.eq2online.xml.PropertiesXMLUtils;
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
/*     */ public class VariableProviderShared
/*     */   extends VariableProviderArray
/*     */   implements IVariableProviderShared
/*     */ {
/*     */   private final Macros macros;
/*  33 */   private final Properties sharedVariables = new Properties();
/*     */ 
/*     */ 
/*     */   
/*     */   private final File propertiesFile;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dirty = false;
/*     */ 
/*     */ 
/*     */   
/*  45 */   private int unSavedTicks = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VariableProviderShared(Macros macros) {
/*  54 */     this.macros = macros;
/*  55 */     this.propertiesFile = this.macros.getFile(".globalvars.xml");
/*  56 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void markDirty() {
/*  61 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void load() {
/*  69 */     this.sharedVariables.clear();
/*     */     
/*  71 */     if (this.propertiesFile != null && this.propertiesFile.exists()) {
/*     */       
/*     */       try {
/*     */         
/*  75 */         BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(this.propertiesFile));
/*  76 */         IArrayStorageBundle arrayStorage = ArrayStorage.getStorageBundle(new ArrayStorage[] { this.flagStore, this.counterStore, this.stringStore });
/*  77 */         this.dirty |= PropertiesXMLUtils.load(this.sharedVariables, arrayStorage, inputStream);
/*  78 */         inputStream.close();
/*     */       }
/*  80 */       catch (IOException ex) {
/*     */         
/*  82 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  86 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void save() {
/*  94 */     if (this.propertiesFile != null) {
/*     */       
/*  96 */       this.dirty = false;
/*     */ 
/*     */       
/*     */       try {
/* 100 */         BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(this.propertiesFile));
/* 101 */         IArrayStorageBundle arrayStorage = ArrayStorage.getStorageBundle(new ArrayStorage[] { this.flagStore, this.counterStore, this.stringStore });
/* 102 */         PropertiesXMLUtils.save(this.sharedVariables, arrayStorage, outputStream, "Shared variables store for mod_Macros", "UTF-8");
/* 103 */         outputStream.close();
/*     */       }
/* 105 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/* 116 */     if (clock) {
/*     */       
/* 118 */       this.unSavedTicks--;
/* 119 */       if (this.dirty && this.unSavedTicks < 0) {
/*     */         
/* 121 */         save();
/* 122 */         this.unSavedTicks = 100;
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
/*     */   public void setSharedVariable(String variableName, String variableValue) {
/* 134 */     this.sharedVariables.setProperty(variableName, variableValue);
/* 135 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSharedVariable(String variableName) {
/* 145 */     return this.sharedVariables.containsKey(variableName) ? this.sharedVariables.getProperty(variableName) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 155 */     if (variableName.startsWith("@") && Variable.isValidVariableName(variableName)) {
/*     */       
/* 157 */       variableName = variableName.substring(1);
/*     */       
/* 159 */       if (this.sharedVariables.containsKey(variableName)) {
/*     */         
/* 161 */         String propertyValue = this.sharedVariables.getProperty(variableName);
/* 162 */         if (propertyValue.matches("^(\\-)?\\d+$")) {
/*     */           
/*     */           try {
/*     */             
/* 166 */             int intValue = Integer.parseInt(propertyValue);
/* 167 */             return Integer.valueOf(intValue);
/*     */           }
/* 169 */           catch (NumberFormatException numberFormatException) {}
/*     */         }
/*     */         
/* 172 */         return propertyValue;
/*     */       } 
/*     */       
/* 175 */       return super.getVariable(variableName);
/*     */     } 
/*     */     
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 188 */     Set<String> variables = super.getVariables();
/*     */     
/* 190 */     for (Object sharedVar : this.sharedVariables.keySet())
/*     */     {
/* 192 */       variables.add("@" + sharedVar.toString());
/*     */     }
/*     */     
/* 195 */     return variables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSharedVariable(String variableName, int defaultValue) {
/*     */     try {
/* 207 */       int value = Integer.parseInt(getSharedVariable(variableName));
/* 208 */       return value;
/*     */     }
/* 210 */     catch (NumberFormatException ex) {
/*     */       
/* 212 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCounter(String counter, int value) {
/* 231 */     setSharedVariable("#" + counter, String.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetCounter(String counter) {
/* 241 */     this.sharedVariables.remove("#" + counter.toLowerCase());
/* 242 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetCounter(String counter, int offset) {
/* 252 */     markDirty();
/* 253 */     super.unsetCounter(counter, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCounter(String counter, int increment) {
/* 263 */     int currentValue = getCounter(counter);
/* 264 */     setSharedVariable("#" + counter, String.valueOf(currentValue + increment));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementCounter(String counter, int decrement) {
/* 274 */     incrementCounter(counter, decrement * -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCounter(String counter) {
/* 284 */     return getSharedVariable("#" + counter, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String stringName) {
/* 294 */     return getSharedVariable("&" + stringName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String stringName, String value) {
/* 304 */     setSharedVariable("&" + stringName.toLowerCase(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetString(String stringName) {
/* 314 */     this.sharedVariables.remove("&" + stringName.toLowerCase());
/* 315 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFlag(String flag) {
/* 325 */     String flagValue = getSharedVariable(flag);
/* 326 */     return ("1".equals(flagValue) || "true".equalsIgnoreCase(flagValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, boolean value) {
/* 336 */     setSharedVariable(flag, value ? "1" : "0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag) {
/* 346 */     setSharedVariable(flag, "1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetFlag(String flag) {
/* 356 */     setSharedVariable(flag, "0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, int offset, boolean value) {
/* 366 */     markDirty();
/* 367 */     super.setFlag(flag, offset, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, int offset) {
/* 377 */     markDirty();
/* 378 */     super.setFlag(flag, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetFlag(String flag, int offset) {
/* 388 */     markDirty();
/* 389 */     super.unsetFlag(flag, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCounter(String counter, int offset, int value) {
/* 399 */     markDirty();
/* 400 */     super.setCounter(counter, offset, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String stringName, int offset, String value) {
/* 410 */     markDirty();
/* 411 */     super.setString(stringName, offset, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetString(String stringName, int offset) {
/* 421 */     markDirty();
/* 422 */     super.unsetString(stringName, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean push(String arrayName, String value) {
/* 432 */     markDirty();
/* 433 */     return super.push(arrayName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String pop(String arrayName) {
/* 443 */     markDirty();
/* 444 */     return super.pop(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String arrayName, int offset) {
/* 454 */     markDirty();
/* 455 */     super.delete(arrayName, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(String arrayName) {
/* 465 */     markDirty();
/* 466 */     super.clear(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCounter(String counter, int offset, int increment) {
/* 476 */     markDirty();
/* 477 */     super.incrementCounter(counter, offset, increment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementCounter(String counter, int offset, int increment) {
/* 487 */     markDirty();
/* 488 */     super.decrementCounter(counter, offset, increment);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderShared.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */