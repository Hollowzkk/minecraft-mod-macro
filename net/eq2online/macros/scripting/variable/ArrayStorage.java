/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.eq2online.xml.IArrayStorageBundle;
/*     */ import net.eq2online.xml.PropertiesXMLUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class ArrayStorage<T>
/*     */ {
/*  27 */   final Map<String, Map<Integer, T>> arrays = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   final Map<String, Integer> lengths = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String key;
/*     */ 
/*     */ 
/*     */   
/*     */   private final T defaultValue;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String prefix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayStorage(String key, T defaultValue) {
/*  51 */     this(key, defaultValue, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayStorage(String key, T defaultValue, String prefix) {
/*  56 */     this.key = key;
/*  57 */     this.defaultValue = defaultValue;
/*  58 */     this.prefix = prefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  63 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  68 */     return (this.prefix != null) ? this.prefix : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getArrayNames() {
/*  73 */     return this.arrays.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   void updateArrayLengths() {
/*  78 */     this.lengths.clear();
/*     */     
/*  80 */     for (Map.Entry<String, ? extends Map<Integer, ?>> arrayEntry : this.arrays.entrySet())
/*     */     {
/*  82 */       updateArrayLength(arrayEntry.getKey(), arrayEntry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateArrayLength(String name, Map<Integer, ?> array) {
/*  88 */     int max = -1;
/*  89 */     for (Integer pos : array.keySet())
/*     */     {
/*  91 */       max = Math.max(pos.intValue(), max);
/*     */     }
/*  93 */     this.lengths.put(name, Integer.valueOf(max));
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  98 */     this.arrays.clear();
/*  99 */     this.lengths.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private String trim(String arrayName) {
/* 104 */     return (this.prefix == null) ? arrayName : (arrayName.startsWith(this.prefix) ? arrayName.substring(this.prefix.length()) : arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean has(String arrayName) {
/* 109 */     return has0(trim(arrayName));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean has0(String arrayName) {
/* 114 */     return this.arrays.containsKey(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Integer, T> lookup(String arrayName) {
/* 119 */     return lookup0(trim(arrayName));
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<Integer, T> lookup0(String arrayName) {
/* 124 */     return this.arrays.get(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(String arrayName) {
/* 129 */     remove0(trim(arrayName));
/*     */   }
/*     */ 
/*     */   
/*     */   private void remove0(String arrayName) {
/* 134 */     this.arrays.remove(arrayName);
/* 135 */     this.lengths.remove(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxIndex(String arrayName) {
/* 140 */     return getMaxArrayIndex0(trim(arrayName));
/*     */   }
/*     */ 
/*     */   
/*     */   private int getMaxArrayIndex0(String arrayName) {
/* 145 */     return this.lengths.containsKey(arrayName) ? ((Integer)this.lengths.get(arrayName)).intValue() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFreeIndex(String arrayName) {
/* 150 */     arrayName = trim(arrayName);
/* 151 */     Map<Integer, T> map = lookup0(arrayName);
/* 152 */     if (map == null)
/*     */     {
/* 154 */       return 0;
/*     */     }
/*     */     
/* 157 */     int maxIndex = getMaxArrayIndex0(arrayName);
/* 158 */     int pos = 0;
/* 159 */     for (; pos <= maxIndex; pos++) {
/*     */       
/* 161 */       if (!map.containsKey(Integer.valueOf(pos)))
/*     */       {
/* 163 */         return pos;
/*     */       }
/*     */     } 
/*     */     
/* 167 */     return pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getValue(String arrayName, int arrayIndex) {
/* 175 */     Map<Integer, T> map = lookup(arrayName);
/* 176 */     if (map == null)
/*     */     {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     T value = map.get(Integer.valueOf(arrayIndex));
/* 182 */     return (value != null) ? value : this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getNotNull(String arrayName, int arrayIndex) {
/* 190 */     T value = get(arrayName, arrayIndex);
/* 191 */     return (value != null) ? value : this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(String arrayName, int arrayIndex) {
/* 200 */     Map<Integer, T> map = lookup(arrayName);
/* 201 */     return (map != null) ? map.get(Integer.valueOf(arrayIndex)) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private T get0(String arrayName, int arrayIndex) {
/* 206 */     Map<Integer, T> map = lookup0(arrayName);
/* 207 */     return (map != null) ? map.get(Integer.valueOf(arrayIndex)) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(String arrayName, int offset, T value) {
/* 212 */     set0(trim(arrayName), offset, value);
/*     */   }
/*     */ 
/*     */   
/*     */   private void set0(String arrayName, int offset, T value) {
/* 217 */     Map<Integer, T> map = lookup0(arrayName);
/* 218 */     if (map == null) {
/*     */       
/* 220 */       map = new TreeMap<>();
/* 221 */       this.arrays.put(arrayName, map);
/* 222 */       this.lengths.put(arrayName, Integer.valueOf(0));
/*     */     } 
/*     */     
/* 225 */     map.put(Integer.valueOf(offset), value);
/*     */     
/* 227 */     if (offset > getMaxArrayIndex0(arrayName))
/*     */     {
/* 229 */       this.lengths.put(arrayName, Integer.valueOf(offset));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public T pop(String arrayName, int popIndex) {
/* 235 */     arrayName = trim(arrayName);
/* 236 */     return pop0(arrayName, popIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   private T pop0(String arrayName, int popIndex) {
/* 241 */     Map<Integer, T> array = lookup0(arrayName);
/*     */     
/* 243 */     if (array != null) {
/*     */       
/* 245 */       T valueAt = get0(arrayName, popIndex);
/* 246 */       array.remove(Integer.valueOf(popIndex));
/* 247 */       this.lengths.put(arrayName, Integer.valueOf(popIndex - 1));
/*     */       
/* 249 */       if (popIndex == 0)
/*     */       {
/* 251 */         remove0(arrayName);
/*     */       }
/*     */       
/* 254 */       return valueAt;
/*     */     } 
/*     */     
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete(String arrayName, int offset) {
/* 262 */     delete0(trim(arrayName), offset);
/*     */   }
/*     */ 
/*     */   
/*     */   private void delete0(String arrayName, int offset) {
/* 267 */     Map<Integer, T> array = lookup0(arrayName);
/*     */     
/* 269 */     if (array == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 274 */     if (array.remove(Integer.valueOf(offset)) != null) {
/*     */       
/* 276 */       this.lengths.remove(arrayName);
/*     */       
/* 278 */       if (array.size() == 0) {
/*     */         
/* 280 */         array.remove(arrayName);
/*     */         
/*     */         return;
/*     */       } 
/* 284 */       updateArrayLength(arrayName, array);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IArrayStorageBundle getStorageBundle(ArrayStorage<?>... stores) {
/* 290 */     return new ArrayStorageBundle(stores);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Document xml, Element node) {
/* 296 */     PropertiesXMLUtils.serialiseArrays(this.key, this.arrays, xml, node);
/*     */   }
/*     */   
/*     */   static class ArrayStorageBundle
/*     */     implements IArrayStorageBundle
/*     */   {
/*     */     private final ArrayStorage<?>[] stores;
/* 303 */     private final Map<String, Map<String, Map<Integer, ?>>> arrayStorage = new HashMap<>();
/*     */ 
/*     */     
/*     */     ArrayStorageBundle(ArrayStorage<?>... stores) {
/* 307 */       this.stores = stores;
/*     */       
/* 309 */       for (ArrayStorage<?> store : stores) {
/*     */ 
/*     */         
/* 312 */         Map<String, Map<Integer, ?>> arrays = store.arrays;
/* 313 */         this.arrayStorage.put(store.getKey(), arrays);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> getStorageTypes() {
/* 320 */       return this.arrayStorage.keySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Map<Integer, ?>> getStorage(String storageType) {
/* 326 */       return this.arrayStorage.get(storageType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void preDeserialise() {
/* 332 */       for (ArrayStorage<?> store : this.stores)
/*     */       {
/* 334 */         store.clear();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void preSerialise() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void postDeserialise() {
/* 346 */       for (ArrayStorage<?> store : this.stores)
/*     */       {
/* 348 */         store.updateArrayLengths();
/*     */       }
/*     */     }
/*     */     
/*     */     public void postSerialise() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\ArrayStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */