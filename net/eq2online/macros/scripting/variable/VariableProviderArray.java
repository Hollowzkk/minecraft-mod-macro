/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.ICounterProvider;
/*     */ import net.eq2online.macros.scripting.api.IFlagProvider;
/*     */ import net.eq2online.macros.scripting.api.IMutableArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.IStringProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.eq2online.xml.IArrayStorageBundle;
/*     */ import net.eq2online.xml.PropertiesXMLUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VariableProviderArray
/*     */   implements IMutableArrayProvider, ICounterProvider, IFlagProvider, IStringProvider
/*     */ {
/*  24 */   protected final ArrayStorage<Boolean> flagStore = new ArrayStorage<>("boolean", Boolean.valueOf(false));
/*  25 */   protected final ArrayStorage<Integer> counterStore = new ArrayStorage<>("int", Integer.valueOf(0), "#");
/*  26 */   protected final ArrayStorage<String> stringStore = new ArrayStorage<>("string", "", "&");
/*     */ 
/*     */   
/*     */   private Map<Integer, ?> getArray(String arrayName) {
/*  30 */     return getStorage(arrayName).lookup(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   private ArrayStorage<?> getStorage(String arrayName) {
/*  35 */     return arrayName.startsWith("#") ? this.counterStore : (
/*     */       
/*  37 */       arrayName.startsWith("&") ? this.stringStore : this.flagStore);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxArrayIndex(String arrayName) {
/*  43 */     return getStorage(arrayName).getMaxIndex(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFreeArrayIndex(String arrayName) {
/*  48 */     return getStorage(arrayName).getFreeIndex(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkArrayExists(String arrayName) {
/*  54 */     return getStorage(arrayName).has(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean push(String arrayName, String value) {
/*  60 */     arrayName = arrayName.toLowerCase();
/*  61 */     insert(arrayName, getMaxArrayIndex(arrayName) + 1, value);
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean put(String arrayName, String value) {
/*  68 */     arrayName = arrayName.toLowerCase();
/*  69 */     insert(arrayName, getFreeArrayIndex(arrayName), value);
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(String arrayName, int offset, String value) {
/*  75 */     if (arrayName.startsWith("#")) {
/*     */       
/*  77 */       int intValue = ScriptCore.tryParseInt(value, 0);
/*  78 */       this.counterStore.set(arrayName, offset, Integer.valueOf(intValue));
/*     */     }
/*  80 */     else if (arrayName.startsWith("&")) {
/*     */       
/*  82 */       this.stringStore.set(arrayName, offset, value);
/*     */     }
/*     */     else {
/*     */       
/*  86 */       boolean booleanValue = ("1".equals(value) || "true".equalsIgnoreCase(value));
/*  87 */       this.flagStore.set(arrayName, offset, Boolean.valueOf(booleanValue));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String pop(String arrayName) {
/*  94 */     arrayName = arrayName.toLowerCase();
/*  95 */     int popIndex = getMaxArrayIndex(arrayName);
/*     */     
/*  97 */     if (popIndex > -1) {
/*     */       
/*  99 */       if (arrayName.startsWith("#")) {
/*     */         
/* 101 */         Integer integer = this.counterStore.pop(arrayName, popIndex);
/* 102 */         return (integer != null) ? String.valueOf(integer) : null;
/*     */       } 
/* 104 */       if (arrayName.startsWith("&"))
/*     */       {
/* 106 */         return this.stringStore.pop(arrayName, popIndex);
/*     */       }
/*     */       
/* 109 */       Boolean value = this.flagStore.pop(arrayName, popIndex);
/* 110 */       return (value != null) ? (value.booleanValue() ? "1" : "0") : null;
/*     */     } 
/*     */     
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(String arrayName, String value, boolean caseSensitive) {
/* 119 */     arrayName = arrayName.toLowerCase();
/* 120 */     Map<Integer, ?> map = getArray(arrayName);
/*     */     
/* 122 */     if (map != null) {
/*     */       
/* 124 */       for (Map.Entry<Integer, ?> mapEntry : map.entrySet()) {
/*     */         
/* 126 */         String entryValue = (mapEntry.getValue() instanceof String) ? (String)mapEntry.getValue() : String.valueOf(mapEntry.getValue());
/*     */         
/* 128 */         if ((!caseSensitive && entryValue.equalsIgnoreCase(value)) || entryValue.equals(value))
/*     */         {
/* 130 */           return ((Integer)mapEntry.getKey()).intValue();
/*     */         }
/*     */       } 
/*     */       
/* 134 */       boolean arrayIsString = arrayName.startsWith("&");
/* 135 */       if ((arrayIsString && value.length() == 0) || (!arrayIsString && ("0".equals(value) || "false".equalsIgnoreCase(value))))
/*     */       {
/* 137 */         for (int vid = 0; vid < getMaxArrayIndex(arrayName); vid++) {
/*     */           
/* 139 */           if (map.get(Integer.valueOf(vid)) == null)
/*     */           {
/* 141 */             return vid;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 147 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String arrayName, int offset) {
/* 153 */     getStorage(arrayName).delete(arrayName.toLowerCase(), offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(String arrayName) {
/* 159 */     getStorage(arrayName).remove(arrayName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 165 */     Matcher arrayIndexMatcher = Variable.arrayVariablePattern.matcher(variableName);
/*     */     
/* 167 */     if (arrayIndexMatcher.find()) {
/*     */       
/* 169 */       int arrayIndex = Math.max(0, Integer.parseInt(arrayIndexMatcher.group(1)));
/* 170 */       variableName = variableName.substring(0, variableName.indexOf('['));
/*     */       
/* 172 */       return getArrayVariableValue(variableName, arrayIndex);
/*     */     } 
/*     */     
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 181 */     return addVariables(addVariables(addVariables(new HashSet<>(), this.counterStore), this.stringStore), this.flagStore);
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<String> addVariables(Set<String> variables, ArrayStorage<?> store1) {
/* 186 */     for (String var : store1.getArrayNames())
/*     */     {
/* 188 */       variables.add(store1.getPrefix() + var + "[]");
/*     */     }
/*     */     
/* 191 */     return variables;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getArrayVariableValue(String variableName, int offset) {
/* 197 */     return getStorage(variableName).getValue(variableName, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFlag(String flag, int offset) {
/* 203 */     flag = flag.toLowerCase();
/* 204 */     if (offset < 0) return getFlag(flag); 
/* 205 */     return ((Boolean)this.flagStore.getNotNull(flag, offset)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, int offset, boolean value) {
/* 211 */     flag = flag.toLowerCase();
/* 212 */     if (offset < 0) {
/*     */       
/* 214 */       setFlag(flag, value);
/*     */       
/*     */       return;
/*     */     } 
/* 218 */     this.flagStore.set(flag, offset, Boolean.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, int offset) {
/* 224 */     setFlag(flag, offset, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetFlag(String flag, int offset) {
/* 230 */     setFlag(flag, offset, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCounter(String counter, int offset) {
/* 236 */     counter = counter.toLowerCase();
/* 237 */     if (offset < 0) return getCounter(counter); 
/* 238 */     return ((Integer)this.counterStore.getNotNull(counter, offset)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCounter(String counter, int offset, int value) {
/* 244 */     counter = counter.toLowerCase();
/* 245 */     if (offset < 0) {
/*     */       
/* 247 */       setCounter(counter, value);
/*     */       
/*     */       return;
/*     */     } 
/* 251 */     this.counterStore.set(counter, offset, Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetCounter(String counter, int offset) {
/* 257 */     counter = counter.toLowerCase();
/* 258 */     if (offset < 0) {
/*     */       
/* 260 */       unsetCounter(counter);
/*     */       
/*     */       return;
/*     */     } 
/* 264 */     delete("#" + counter, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCounter(String counter, int offset, int increment) {
/* 270 */     counter = counter.toLowerCase();
/* 271 */     int counterValue = getCounter(counter, offset);
/* 272 */     counterValue += increment;
/* 273 */     setCounter(counter, offset, counterValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementCounter(String counter, int offset, int increment) {
/* 279 */     counter = counter.toLowerCase();
/* 280 */     int counterValue = getCounter(counter, offset);
/* 281 */     counterValue -= increment;
/* 282 */     setCounter(counter, offset, counterValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String stringName, int offset) {
/* 288 */     stringName = stringName.toLowerCase();
/* 289 */     if (offset < 0) return getString(stringName); 
/* 290 */     return this.stringStore.getNotNull(stringName, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String stringName, int offset, String value) {
/* 296 */     stringName = stringName.toLowerCase();
/* 297 */     if (offset < 0) {
/*     */       
/* 299 */       setString(stringName, value);
/*     */       
/*     */       return;
/*     */     } 
/* 303 */     this.stringStore.set(stringName, offset, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetString(String stringName, int offset) {
/* 309 */     stringName = stringName.toLowerCase();
/* 310 */     if (offset < 0) {
/*     */       
/* 312 */       unsetString(stringName);
/*     */       
/*     */       return;
/*     */     } 
/* 316 */     delete("&" + stringName, offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveVariables(Document xml, Element node) {
/* 321 */     this.flagStore.save(xml, node);
/* 322 */     this.counterStore.save(xml, node);
/* 323 */     this.stringStore.save(xml, node);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadVariables(Node node) {
/* 328 */     IArrayStorageBundle arrayStorage = ArrayStorage.getStorageBundle((ArrayStorage<?>[])new ArrayStorage[] { this.flagStore, this.counterStore, this.stringStore });
/* 329 */     PropertiesXMLUtils.importProperties(arrayStorage, node);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\VariableProviderArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */