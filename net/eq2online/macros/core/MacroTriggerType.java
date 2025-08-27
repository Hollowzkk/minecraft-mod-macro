/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import net.eq2online.macros.event.MacroEventManager;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ public enum MacroTriggerType
/*     */ {
/*  19 */   KEY(true, true, 0, 254)
/*     */   {
/*     */     
/*     */     public String getName(Macros macros, int id)
/*     */     {
/*  24 */       if (!supportsId(id))
/*     */       {
/*  26 */         return super.getName(macros, id);
/*     */       }
/*     */       
/*  29 */       if (id == 248) return "MWHEELUP"; 
/*  30 */       if (id == 249) return "MWHEELDOWN"; 
/*  31 */       if (id == 250) return "LMOUSE"; 
/*  32 */       if (id == 251) return "RMOUSE"; 
/*  33 */       if (id == 252) return "MIDDLEMOUSE"; 
/*  34 */       if (id == 253 || id == -97) return "MOUSE3"; 
/*  35 */       if (id == 254 || id == -96) return "MOUSE4"; 
/*  36 */       if (id == 240 || id == -95) return "MOUSE5"; 
/*  37 */       if (id == 241 || id == -94) return "MOUSE6"; 
/*  38 */       if (id == 242 || id == -93) return "MOUSE7"; 
/*  39 */       if (id == 243 || id == -92) return "MOUSE8"; 
/*  40 */       if (id == 244 || id == -91) return "MOUSE9"; 
/*  41 */       if (id == 245 || id == -90) return "MOUSE10";
/*     */       
/*  43 */       String keyName = Keyboard.getKeyName(id);
/*  44 */       return (keyName != null) ? keyName : String.valueOf(id);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   CONTROL(true, true, 256, 999, 3000, 9999)
/*     */   {
/*     */     
/*     */     public String getName(Macros macros, int id)
/*     */     {
/*  56 */       if (!supportsId(id))
/*     */       {
/*  58 */         return super.getName(macros, id);
/*     */       }
/*     */       
/*  61 */       return macros.getLayoutManager().getControls().getControlName(id);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   EVENT(true, false, 1000, 1999)
/*     */   {
/*     */     
/*     */     public String getName(Macros macros, int id)
/*     */     {
/*  73 */       if (!supportsId(id))
/*     */       {
/*  75 */         return super.getName(macros, id);
/*     */       }
/*     */       
/*  78 */       MacroEventManager eventManger = macros.getEventManager();
/*  79 */       IMacroEvent event = eventManger.getEvent(id);
/*     */       
/*  81 */       if (event != null)
/*     */       {
/*  83 */         return event.getName();
/*     */       }
/*     */       
/*  86 */       return "onEventId" + getRelativeId(id);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   NONE(false, false, 2000, 2999)
/*     */   {
/*     */ 
/*     */ 
/*     */     
/*  99 */     private int nextFreeIndex = getMinId();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     private String[] macroNames = new String[getMaxId() + 1];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName(Macros macros, int id) {
/* 110 */       if (!supportsId(id))
/*     */       {
/* 112 */         return super.getName(macros, id);
/*     */       }
/*     */       
/* 115 */       if (this.macroNames[id] != null)
/*     */       {
/* 117 */         return this.macroNames[id].toUpperCase();
/*     */       }
/*     */       
/* 120 */       return "MACRO" + (getRelativeId(id) + 1);
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
/*     */     
/*     */     public int getIndexForName(Macros macros, String macroName) {
/* 133 */       if (macroName == null) return 0;
/*     */       
/* 135 */       for (int code = getMinId(); code <= getMaxId(); code++) {
/*     */         
/* 137 */         if (getName(macros, code).equalsIgnoreCase(macroName))
/*     */         {
/* 139 */           return code;
/*     */         }
/*     */       } 
/*     */       
/* 143 */       return 0;
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
/*     */     
/*     */     public int getNextFreeIndex(Macros macros, String macroName) {
/* 156 */       int existingIndex = getIndexForName(macros, macroName);
/*     */       
/* 158 */       if (existingIndex > 0)
/*     */       {
/* 160 */         return existingIndex;
/*     */       }
/*     */       
/* 163 */       if (this.nextFreeIndex >= getMaxId())
/*     */       {
/* 165 */         this.nextFreeIndex = getMinId();
/*     */       }
/*     */       
/* 168 */       this.macroNames[this.nextFreeIndex] = macroName;
/* 169 */       return this.nextFreeIndex++;
/*     */     }
/*     */   };
/*     */ 
/*     */   
/*     */   public static final int MAX_TEMPLATES = 10000;
/*     */   
/*     */   private final boolean save;
/*     */   
/*     */   private final boolean supportsParams;
/*     */   
/*     */   private final int minId;
/*     */   
/*     */   private final int maxId;
/*     */   private final boolean extendedId;
/*     */   private final int minExtId;
/*     */   private final int maxExtId;
/*     */   
/*     */   MacroTriggerType(boolean save, boolean supportsParams, int minId, int maxId) {
/* 188 */     this.save = save;
/* 189 */     this.supportsParams = supportsParams;
/* 190 */     this.minId = minId;
/* 191 */     this.maxId = maxId;
/* 192 */     this.minExtId = 9999;
/* 193 */     this.maxExtId = 10000;
/* 194 */     this.extendedId = false;
/*     */   }
/*     */ 
/*     */   
/*     */   MacroTriggerType(boolean save, boolean supportsParams, int minId, int maxId, int minExtId, int maxExtId) {
/* 199 */     this.save = save;
/* 200 */     this.supportsParams = supportsParams;
/* 201 */     this.minId = minId;
/* 202 */     this.maxId = maxId;
/* 203 */     this.minExtId = minExtId;
/* 204 */     this.maxExtId = maxExtId;
/* 205 */     this.extendedId = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsParams() {
/* 210 */     return this.supportsParams;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinId() {
/* 215 */     return this.minId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxId() {
/* 220 */     return this.maxId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsExtendedId() {
/* 225 */     return this.extendedId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinExtId() {
/* 230 */     return this.minExtId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxExtId() {
/* 235 */     return this.maxExtId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAbsoluteMaxId() {
/* 240 */     return this.extendedId ? this.maxExtId : this.maxId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsId(int id) {
/* 245 */     return ((id >= this.minId && id <= this.maxId) || (this.extendedId && id >= this.minExtId && id <= this.maxExtId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRelativeId(int id) {
/* 250 */     return id - this.minId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(Macros macros, int id) {
/* 255 */     if (id < 0) {
/*     */       
/* 257 */       if (id == -100) return "LMOUSE"; 
/* 258 */       if (id == -99) return "RMOUSE"; 
/* 259 */       if (id == -98) return "MIDDLEMOUSE";
/*     */     
/*     */     } 
/* 262 */     return "UNKNOWN";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndexForName(Macros macros, String macroName) {
/* 267 */     throw new UnsupportedOperationException("Cannot lookup macro name for type " + name());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextFreeIndex(Macros macros, String macroName) {
/* 272 */     throw new UnsupportedOperationException("Cannot get free index for type " + name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MacroTriggerType fromId(int id) {
/* 283 */     for (MacroTriggerType type : values()) {
/*     */       
/* 285 */       if (type.supportsId(id))
/*     */       {
/* 287 */         return type;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     return NONE;
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
/*     */   public static boolean validate(int id, MacroTriggerType type) {
/* 303 */     return fromId(id).equals(type);
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
/*     */   public static String getMacroNameWithPrefix(Macros macros, int mappingId) {
/* 315 */     MacroTriggerType macroType = fromId(mappingId);
/* 316 */     String prefix = macroType.toString().toUpperCase() + "_";
/* 317 */     return prefix + macroType.getName(macros, mappingId).toUpperCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMacroName(Macros macros, int mappingId) {
/* 328 */     return fromId(mappingId).getName(macros, mappingId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getMacroShouldBeSaved(Macros macros, int mappingId) {
/* 333 */     MacroTriggerType type = fromId(mappingId);
/*     */     
/* 335 */     if (type == CONTROL)
/*     */     {
/* 337 */       return (macros.getLayoutManager().getControls().getControl(mappingId) != null);
/*     */     }
/*     */     
/* 340 */     return type.save;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroTriggerType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */