/*     */ package net.eq2online.macros.struct;
/*     */ 
/*     */ import ain;
/*     */ import aip;
/*     */ import bhz;
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.util.Game;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemInfo
/*     */   implements IListEntry<Integer>
/*     */ {
/*     */   public static class Settings
/*     */     implements ISettingsObserver
/*     */   {
/*  35 */     protected Map<Integer, List<Integer>> includeItems = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/*     */     public void onClearSettings() {
/*  40 */       this.includeItems.clear();
/*  41 */       this.includeItems.put(Integer.valueOf(80), null);
/*     */       
/*  43 */       List<Integer> leafOverrides = new ArrayList<>();
/*  44 */       leafOverrides.add(Integer.valueOf(2));
/*     */       
/*  46 */       this.includeItems.put(Integer.valueOf(18), leafOverrides);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onLoadSettings(ISettingsStore settings) {
/*  52 */       String serialisedOverrides = settings.getSetting("list.items.include", "{18:2}{80}");
/*  53 */       Pattern overridePattern = Pattern.compile("\\{([0-9]+)(:([0-9]+))?\\}");
/*  54 */       Matcher overridePatternMatcher = overridePattern.matcher(serialisedOverrides);
/*     */       
/*  56 */       this.includeItems.clear();
/*     */       
/*  58 */       while (overridePatternMatcher.find()) {
/*     */         
/*  60 */         Integer itemId = Integer.valueOf(Integer.parseInt(overridePatternMatcher.group(1)));
/*     */         
/*  62 */         if (overridePatternMatcher.group(3) == null) {
/*     */           
/*  64 */           if (!this.includeItems.containsKey(itemId))
/*     */           {
/*  66 */             this.includeItems.put(itemId, null);
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*  71 */         Integer itemDamage = Integer.valueOf(Integer.parseInt(overridePatternMatcher.group(3)));
/*     */         
/*  73 */         if (this.includeItems.get(itemId) == null)
/*     */         {
/*  75 */           this.includeItems.put(itemId, new ArrayList<>());
/*     */         }
/*     */         
/*  78 */         if (!((List)this.includeItems.get(itemId)).contains(itemDamage))
/*     */         {
/*  80 */           ((List<Integer>)this.includeItems.get(itemId)).add(itemDamage);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onSaveSettings(ISettingsStore settings) {
/*  89 */       StringBuilder serialisedOverrides = new StringBuilder();
/*     */       
/*  91 */       for (Map.Entry<Integer, List<Integer>> override : this.includeItems.entrySet()) {
/*     */         
/*  93 */         if (override.getValue() == null) {
/*     */           
/*  95 */           serialisedOverrides.append("{").append(override.getKey()).append("}");
/*     */           
/*     */           continue;
/*     */         } 
/*  99 */         for (Integer damageValue : override.getValue())
/*     */         {
/* 101 */           serialisedOverrides.append("{").append(override.getKey()).append(":").append(damageValue).append("}");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 106 */       settings.setSetting("list.items.include", serialisedOverrides.toString());
/* 107 */       settings.setSettingComment("list.items.include", "Item ID/damage values to include in the items list that aren't automatically enumerated, use {id} for base items and {id:damage} for specific damage values");
/*     */     }
/*     */   }
/*     */   
/* 111 */   public static final Settings SETTINGS = new Settings();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ain item;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String itemIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int damageValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected aip itemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemInfo(int id, ain item, int damage, aip stack) {
/* 161 */     this.id = id;
/* 162 */     this.item = item;
/* 163 */     this.itemIdentifier = Game.getItemName(item);
/* 164 */     this.name = (item == null) ? "Air" : item.j(stack);
/*     */     
/* 166 */     this.damageValue = damage;
/* 167 */     this.itemStack = stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIcon() {
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public nf getIconTexture() {
/* 188 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getIcon() {
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamage() {
/* 204 */     return this.damageValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public aip getItemStack() {
/* 213 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean compareTo(ItemInfo other) {
/* 224 */     if (SETTINGS.includeItems.containsKey(other.itemIdentifier)) {
/*     */       
/* 226 */       List<Integer> damageValues = SETTINGS.includeItems.get(other.itemIdentifier);
/*     */       
/* 228 */       if (damageValues == null && other.damageValue == 0) return false;
/*     */       
/* 230 */       if (damageValues != null)
/*     */       {
/* 232 */         for (Integer damageValue : damageValues) {
/*     */           
/* 234 */           if (damageValue.equals(Integer.valueOf(other.damageValue)))
/*     */           {
/* 236 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 242 */     return other.getItemStack().a(this.itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 252 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 258 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 269 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 278 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIdentifier() {
/* 284 */     return this.itemIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getData() {
/* 293 */     return Integer.valueOf(this.damageValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListEntry.CustomAction getCustomAction(boolean bClear) {
/* 302 */     return IListEntry.CustomAction.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIconId(int newIconId) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String newText) {
/* 313 */     this.name = newText;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayName(String newDisplayName) {
/* 319 */     this.name = newDisplayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int newId) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(Integer newData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderIcon(bib minecraft, int xPosition, int yPosition) {
/*     */     try {
/* 337 */       GL.glDepthFunc(515);
/* 338 */       bhz.c();
/* 339 */       minecraft.ad().a(this.itemStack, xPosition, yPosition);
/* 340 */       bhz.a();
/*     */     }
/* 342 */     catch (Exception ex) {
/*     */       
/* 344 */       System.err.printf("Rendering error %s:%s\n", new Object[] { getName(), Integer.valueOf(getDamage()) });
/*     */     } 
/* 346 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateName() {
/*     */     try {
/* 356 */       this.name = this.itemStack.r() + " (" + this.itemIdentifier;
/*     */       
/* 358 */       if (this.damageValue > 0)
/*     */       {
/* 360 */         this.name += ":" + this.damageValue;
/*     */       }
/*     */       
/* 363 */       this.name += ")";
/*     */     }
/* 365 */     catch (Exception ex) {
/*     */       
/* 367 */       this.name = this.itemStack.getClass().getSimpleName();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\struct\ItemInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */