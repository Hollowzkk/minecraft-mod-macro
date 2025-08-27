/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventDefinition;
/*     */ 
/*     */ public enum BuiltinEvent
/*     */   implements IMacroEventDefinition {
/*   9 */   onJoinGame("onJoinGame"),
/*  10 */   onChat("onChat"),
/*  11 */   onHealthChange("onHealthChange", "player"),
/*  12 */   onFoodChange("onFoodChange", "player"),
/*  13 */   onArmourChange("onArmourChange", "player"),
/*  14 */   onWorldChange("onWorldChange", "world", 1),
/*  15 */   onModeChange("onModeChange", "player"),
/*  16 */   onInventorySlotChange("onInventorySlotChange", "local", 11),
/*  17 */   onOxygenChange("onOxygenChange", "player"),
/*  18 */   onXPChange("onXPChange", "stats"),
/*  19 */   onLevelChange("onLevelChange", "stats"),
/*  20 */   onItemDurabilityChange("onItemDurabilityChange", "player"),
/*  21 */   onWeatherChange("onWeatherChange", "world", 1),
/*  22 */   onPickupItem("onPickupItem", "player"),
/*  23 */   onPlayerJoined("onPlayerJoined", "world", 30),
/*  24 */   onShowGui("onShowGui", "local", 5),
/*  25 */   onArmourDurabilityChange("onArmourDurabilityChange", "player"),
/*  26 */   onAutoCraftingComplete("onAutoCraftingComplete", "local"),
/*  27 */   onConfigChange("onConfigChange", "local"),
/*  28 */   onFilterableChat("onFilterableChat")
/*     */   {
/*     */     
/*     */     public boolean isEnabled(Macros macros)
/*     */     {
/*  33 */       return (macros.getSettings()).showFilterableChat;
/*     */     }
/*     */   },
/*  36 */   onSendChatMessage("onSendChatMessage");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int PRIORITY_MIN = 1;
/*     */ 
/*     */   
/*     */   private static final int PRIORITY_LOW = 5;
/*     */ 
/*     */   
/*     */   private static final int PRIORITY_DEFAULT = 10;
/*     */ 
/*     */   
/*     */   private static final int PRIORITY_ELEVATED = 11;
/*     */ 
/*     */   
/*     */   private static final int PRIORITY_HIGH = 30;
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */   
/*     */   private final String permissionGroup;
/*     */ 
/*     */   
/*     */   private final int defaultDispatchPriority;
/*     */ 
/*     */   
/*     */   private IMacroEvent event;
/*     */ 
/*     */ 
/*     */   
/*     */   BuiltinEvent(String name, String permissionGroup, int defaultDispatchPriority) {
/*  69 */     this.name = name;
/*  70 */     this.permissionGroup = permissionGroup;
/*  71 */     this.defaultDispatchPriority = defaultDispatchPriority;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  77 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  83 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/*  89 */     return this.permissionGroup;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultDispatchPriority() {
/*  94 */     return this.defaultDispatchPriority;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(Macros macros) {
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(IMacroEvent event) {
/* 104 */     this.event = event;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMacroEvent getEvent() {
/* 109 */     return this.event;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\BuiltinEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */