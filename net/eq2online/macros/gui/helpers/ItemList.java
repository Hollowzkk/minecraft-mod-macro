/*     */ package net.eq2online.macros.gui.helpers;
/*     */ 
/*     */ import ain;
/*     */ import aip;
/*     */ import fi;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.interfaces.ILocalisationChangeListener;
/*     */ import net.eq2online.macros.interfaces.IRefreshable;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ import net.eq2online.macros.struct.ItemInfo;
/*     */ import net.eq2online.util.Game;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemList
/*     */   implements IRefreshable, ILocalisationChangeListener, Iterable<ItemInfo>
/*     */ {
/*     */   private final Macros macros;
/*  28 */   private final List<ItemInfo> enumeratedItems = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private final Map<String, List<ItemInfo>> mappedEnumeratedItems = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ItemList(Macros macros) {
/*  37 */     this.macros = macros;
/*  38 */     this.macros.getLocalisationHandler().registerListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {
/*     */     try {
/*  47 */       enumerateItems();
/*     */     }
/*  49 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<ItemInfo> iterator() {
/*  55 */     return this.enumeratedItems.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemInfo> getEnumeratedItems() {
/*  60 */     return Collections.unmodifiableList(this.enumeratedItems);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemInfo> getInfoForItem(ItemID itemId) {
/*  71 */     List<ItemInfo> info = itemId.isValid() ? this.mappedEnumeratedItems.get(itemId.identifier) : null;
/*  72 */     return (info != null) ? info : Collections.<ItemInfo>emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   private void enumerateItems() {
/*  77 */     this.enumeratedItems.clear();
/*     */     
/*  79 */     int itemNumber = 0;
/*  80 */     Iterator<ain> iter = ain.g.iterator();
/*     */     
/*  82 */     while (iter.hasNext()) {
/*     */       
/*  84 */       ain theItem = iter.next();
/*  85 */       itemNumber++;
/*     */ 
/*     */       
/*  88 */       if (theItem != null && theItem.a() != null) {
/*     */         
/*     */         try {
/*     */           
/*  92 */           ain currentItem = theItem;
/*     */           
/*  94 */           fi<aip> subItems = fi.a();
/*  95 */           currentItem.a(currentItem.b(), subItems);
/*     */           
/*  97 */           for (aip subItem : subItems) {
/*     */             
/*     */             try
/*     */             {
/* 101 */               ItemInfo newItem = new ItemInfo(itemNumber, theItem, subItem.j(), subItem);
/* 102 */               this.enumeratedItems.add(newItem);
/*     */               
/* 104 */               String id = Game.getItemName(subItem.c());
/* 105 */               List<ItemInfo> mappedItems = this.mappedEnumeratedItems.get(id);
/* 106 */               if (mappedItems == null)
/*     */               {
/* 108 */                 this.mappedEnumeratedItems.put(id, mappedItems = new ArrayList<>());
/*     */               }
/* 110 */               mappedItems.add(newItem);
/*     */             }
/* 112 */             catch (Exception ex)
/*     */             {
/* 114 */               ex.printStackTrace();
/*     */             }
/*     */           
/*     */           } 
/* 118 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */     
/* 122 */     updateItemNames();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLocaleChanged(String currentLanguage) {
/* 128 */     updateItemNames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateItemNames() {
/* 137 */     for (ItemInfo item : this.enumeratedItems) {
/*     */ 
/*     */       
/*     */       try {
/* 141 */         item.updateName();
/*     */       }
/* 143 */       catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\helpers\ItemList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */