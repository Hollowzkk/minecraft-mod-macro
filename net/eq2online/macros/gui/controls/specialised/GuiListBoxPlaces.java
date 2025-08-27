/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bib;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.list.ListEntry;
/*     */ import net.eq2online.macros.gui.list.PlaceListEntry;
/*     */ import net.eq2online.macros.interfaces.IConfigs;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.struct.Place;
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
/*     */ public class GuiListBoxPlaces
/*     */   extends GuiListBoxFilebound<Place>
/*     */ {
/*     */   public GuiListBoxPlaces(Macros macros, bib minecraft, int controlId) {
/*  35 */     super(macros, minecraft, controlId, true, macros.getFile(".places.txt"), Place.PATTERN);
/*     */     
/*  37 */     this.saveTrimTailSize = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  43 */     return MacroParam.Type.PLACE.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  49 */     IListEntry<Place> newEntry = getItemById(-1);
/*  50 */     removeItemAt(this.items.size() - 1);
/*  51 */     super.sort();
/*  52 */     addItem(newEntry);
/*  53 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigChanged(IConfigs configs) {
/*  63 */     if ((this.macros.getSettings()).configsForPlaces) {
/*     */       
/*  65 */       super.onConfigChanged(configs);
/*     */     }
/*     */     else {
/*     */       
/*  69 */       this.items = (List)this.configs.get("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(IConfigs configs) {
/*  79 */     super.load(configs);
/*     */     
/*  81 */     ListEntry<Place> newPlace = new ListEntry(-1, I18n.get("list.new.place"), null);
/*     */     
/*  83 */     for (Map.Entry<String, List<IListEntry<Place>>> config : (Iterable<Map.Entry<String, List<IListEntry<Place>>>>)this.configs.entrySet())
/*     */     {
/*  85 */       ((List<ListEntry<Place>>)config.getValue()).add(newPlace);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadItem(String line, Matcher linePatternMatcher, List<IListEntry<Place>> items, String currentConfigName) {
/*  92 */     if (linePatternMatcher.matches()) {
/*     */       
/*  94 */       Place newPlace = Place.parsePlace(this.macros.getListProvider(), line);
/*  95 */       if (newPlace != null) {
/*     */         
/*  97 */         items.add(new PlaceListEntry(this.newItemIndex++, newPlace));
/*     */       }
/*     */       else {
/*     */         
/* 101 */         Log.info("Parsing place failed: {0}", new Object[] { line });
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
/*     */   protected void notifyNewConfig(List<IListEntry<Place>> newConfig) {
/* 113 */     newConfig.add(new ListEntry(-1, I18n.get("list.new.place"), null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListEntry<Place> newItem, int itemIndex) {
/* 123 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 124 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListEntry<Place> createObject(String text, int iconId, Object data) {
/* 134 */     return (IListEntry<Place>)new PlaceListEntry(this.newItemIndex++, (Place)data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPlace(String placeName) {
/* 144 */     for (int i = 0; i < this.items.size(); i++) {
/*     */       
/* 146 */       if (((IListEntry)this.items.get(i)).getText().equals(placeName))
/*     */       {
/* 148 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Place getPlace(String placeName) {
/* 162 */     for (int i = 0; i < this.items.size(); i++) {
/*     */       
/* 164 */       if (((IListEntry)this.items.get(i)).getText().equals(placeName))
/*     */       {
/* 166 */         return (Place)((IListEntry)this.items.get(i)).getData();
/*     */       }
/*     */     } 
/*     */     
/* 170 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxPlaces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */