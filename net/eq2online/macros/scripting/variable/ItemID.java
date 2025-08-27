/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import ain;
/*     */ import aip;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.eq2online.util.Game;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemID
/*     */ {
/*     */   public final String identifier;
/*     */   public final int damage;
/*     */   public final boolean hasDamage;
/*     */   public final ain item;
/*     */   public final String name;
/*     */   
/*     */   public ItemID(String itemIdString) {
/*  23 */     if (itemIdString.matches("^[a-z0-9_]+:\\d{1,5}$")) {
/*     */       
/*  25 */       String[] idStringParts = itemIdString.split(":");
/*  26 */       this.identifier = idStringParts[0];
/*  27 */       this.damage = ScriptCore.tryParseInt(idStringParts[1], 0);
/*  28 */       this.hasDamage = true;
/*     */     }
/*     */     else {
/*     */       
/*  32 */       this.identifier = itemIdString;
/*  33 */       this.damage = -1;
/*  34 */       this.hasDamage = false;
/*     */     } 
/*     */     
/*  37 */     this.item = Game.getItem(new nf(this.identifier));
/*  38 */     this.name = Game.getItemName(this.item);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemID(String itemIdString, int damage) {
/*  43 */     if (itemIdString.contains(":")) throw new RuntimeException("Debug? Why are you here with [" + itemIdString + "] and [" + damage + "]?"); 
/*  44 */     this.identifier = itemIdString;
/*  45 */     this.damage = damage;
/*  46 */     this.hasDamage = (damage > 0);
/*  47 */     this.item = Game.getItem(new nf(this.identifier));
/*  48 */     this.name = Game.getItemName(this.item);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  53 */     return (this.item != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasValidDamage() {
/*  58 */     return (this.damage >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public aip toItemStack(int stackSize) {
/*  63 */     return new aip(this.item, stackSize, (this.damage > -1) ? this.damage : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDamage() {
/*  68 */     return String.valueOf((this.damage >= 0) ? this.damage : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  74 */     return String.format("%s:%s", new Object[] { (this.identifier == null) ? Game.getItemName(null) : this.identifier, Integer.valueOf(Math.max(0, this.damage)) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/*  80 */     if (other == null) return false; 
/*  81 */     if (other instanceof ItemID) {
/*     */       
/*  83 */       ItemID otherItem = (ItemID)other;
/*  84 */       return (otherItem.item == this.item);
/*     */     } 
/*  86 */     if (other instanceof ain)
/*     */     {
/*  88 */       return ((ain)other == this.item);
/*     */     }
/*  90 */     if (other instanceof aip) {
/*     */       
/*  92 */       aip itemStack = (aip)other;
/*  93 */       return aip.b(itemStack, toItemStack(itemStack.E()));
/*     */     } 
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return super.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\ItemID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */