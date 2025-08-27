/*     */ package net.eq2online.macros.core.settings;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
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
/*     */ public abstract class SettingsBase
/*     */   implements ISettingsObserver
/*     */ {
/*     */   static class SettingField
/*     */   {
/*     */     private final Field field;
/*     */     private final Class<?> type;
/*     */     private final String key;
/*     */     private final String comment;
/*     */     private final Object defaultValue;
/*     */     private final int min;
/*     */     private final int max;
/*     */     
/*     */     SettingField(Object instance, Field field, Setting annotation) {
/*  52 */       this.field = field;
/*  53 */       this.field.setAccessible(true);
/*  54 */       this.type = this.field.getType();
/*  55 */       this.key = annotation.value();
/*  56 */       this.defaultValue = getValue(instance);
/*     */       
/*  58 */       if (this.defaultValue == null)
/*     */       {
/*  60 */         throw new RuntimeException("Error fetching default value for Macros setting " + this.key);
/*     */       }
/*     */       
/*  63 */       Comment comment = field.<Comment>getAnnotation(Comment.class);
/*  64 */       this.comment = (comment != null) ? comment.value() : null;
/*     */       
/*  66 */       Range range = field.<Range>getAnnotation(Range.class);
/*  67 */       this.min = (range != null) ? range.min() : 0;
/*  68 */       this.max = (range != null) ? range.max() : this.min;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/*  76 */       return this.field.getName();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getKey() {
/*  84 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getComment() {
/*  92 */       return this.comment;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset(Object instance) {
/* 100 */       setValue(instance, getDefaultValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setValue(Object instance, Object value) {
/*     */       try {
/* 113 */         this.field.set(instance, value);
/*     */       }
/* 115 */       catch (Exception ex) {
/*     */         
/* 117 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getValue(Object instance) {
/*     */       try {
/* 131 */         return this.field.get(instance);
/*     */       }
/* 133 */       catch (Exception ex) {
/*     */         
/* 135 */         ex.printStackTrace();
/* 136 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDefaultValue() {
/* 145 */       return this.defaultValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void load(Object instance, ISettingsStore settings) {
/*     */       try {
/* 158 */         loadValue(instance, settings);
/*     */       }
/* 160 */       catch (Exception ex) {
/*     */         
/* 162 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void loadValue(Object instance, ISettingsStore settings) throws IllegalAccessException {
/* 169 */       if (String.class.isAssignableFrom(this.type)) {
/*     */         
/* 171 */         String def = (String)this.defaultValue;
/* 172 */         this.field.set(instance, settings.getSetting(this.key, def));
/*     */       }
/* 174 */       else if (int.class.isAssignableFrom(this.type)) {
/*     */         
/* 176 */         int def = ((Integer)this.defaultValue).intValue();
/* 177 */         if (this.max > this.min)
/*     */         {
/* 179 */           this.field.set(instance, Integer.valueOf(settings.getSetting(this.key, def, this.min, this.max)));
/*     */         }
/*     */         else
/*     */         {
/* 183 */           this.field.set(instance, Integer.valueOf(settings.getSetting(this.key, def)));
/*     */         }
/*     */       
/* 186 */       } else if (boolean.class.isAssignableFrom(this.type)) {
/*     */         
/* 188 */         boolean def = ((Boolean)this.defaultValue).booleanValue();
/* 189 */         this.field.set(instance, Boolean.valueOf(settings.getSetting(this.key, def)));
/*     */       }
/* 191 */       else if (Enum.class.isAssignableFrom(this.type)) {
/*     */         
/* 193 */         Enum def = (Enum)this.defaultValue;
/* 194 */         this.field.set(instance, settings.getSetting(this.key, def));
/*     */       }
/*     */       else {
/*     */         
/* 198 */         throw new RuntimeException("Unsupported type " + this.type + " encountered during load of setting " + this.key);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void save(Object instance, ISettingsStore settings) {
/*     */       try {
/* 212 */         saveValue(instance, settings);
/*     */       }
/* 214 */       catch (Exception ex) {
/*     */         
/* 216 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void saveValue(Object instance, ISettingsStore settings) throws IllegalAccessException {
/* 223 */       if (String.class.isAssignableFrom(this.type)) {
/*     */         
/* 225 */         String value = (String)this.field.get(instance);
/* 226 */         settings.setSetting(this.key, value);
/*     */       }
/* 228 */       else if (int.class.isAssignableFrom(this.type)) {
/*     */         
/* 230 */         int value = ((Integer)this.field.get(instance)).intValue();
/* 231 */         settings.setSetting(this.key, value);
/*     */       }
/* 233 */       else if (boolean.class.isAssignableFrom(this.type)) {
/*     */         
/* 235 */         boolean value = ((Boolean)this.field.get(instance)).booleanValue();
/* 236 */         settings.setSetting(this.key, value);
/*     */       }
/* 238 */       else if (Enum.class.isAssignableFrom(this.type)) {
/*     */         
/* 240 */         Enum value = (Enum)this.field.get(instance);
/* 241 */         settings.setSetting(this.key, value);
/*     */       }
/*     */       else {
/*     */         
/* 245 */         throw new RuntimeException("Unsupported type " + this.type + " encountered during save of setting " + this.key);
/*     */       } 
/*     */       
/* 248 */       if (this.comment != null)
/*     */       {
/* 250 */         settings.setSettingComment(this.key, this.comment);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   private final Map<String, SettingField> settings = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 263 */   private final Map<String, SettingField> byName = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/* 270 */     for (Field field : getClass().getDeclaredFields()) {
/*     */       
/* 272 */       Setting annotation = field.<Setting>getAnnotation(Setting.class);
/* 273 */       if (annotation != null) {
/*     */         
/* 275 */         SettingField setting = new SettingField(this, field, annotation);
/* 276 */         this.settings.put(setting.getKey(), setting);
/* 277 */         this.byName.put(setting.getName(), setting);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {
/* 288 */     for (SettingField setting : this.settings.values())
/*     */     {
/* 290 */       setting.reset(this);
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
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 302 */     for (SettingField setting : this.settings.values())
/*     */     {
/* 304 */       setting.load(this, settings);
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
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 316 */     for (SettingField setting : this.settings.values())
/*     */     {
/* 318 */       setting.save(this, settings);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected final SettingField get(String key) {
/* 324 */     return this.settings.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final SettingField getByName(String name) {
/* 329 */     return this.byName.get(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\settings\SettingsBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */