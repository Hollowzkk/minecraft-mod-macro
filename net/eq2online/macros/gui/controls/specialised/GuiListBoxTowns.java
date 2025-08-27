/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bib;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.list.EditableListEntry;
/*     */ import net.eq2online.macros.gui.list.ListEntry;
/*     */ import net.eq2online.macros.interfaces.IConfigs;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiListBoxTowns
/*     */   extends GuiListBoxFilebound<String>
/*     */ {
/*     */   public GuiListBoxTowns(Macros macros, bib minecraft, int controlId) {
/*  28 */     super(macros, minecraft, controlId, true, macros.getFile(".towns.txt"), true);
/*     */     
/*  30 */     this.iconTexture = ResourceLocations.DYNAMIC_TOWNS;
/*  31 */     this.saveTrimTailSize = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  37 */     return MacroParam.Type.TOWN.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  43 */     IListEntry<String> newEntry = getItemById(-1);
/*  44 */     removeItemAt(this.items.size() - 1);
/*  45 */     super.sort();
/*  46 */     addItem(newEntry);
/*  47 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigChanged(IConfigs configs) {
/*  53 */     if ((this.macros.getSettings()).configsForTowns) {
/*     */       
/*  55 */       super.onConfigChanged(configs);
/*     */     }
/*     */     else {
/*     */       
/*  59 */       this.items = (List)this.configs.get("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(IConfigs configs) {
/*  66 */     super.load(configs);
/*     */     
/*  68 */     ListEntry<String> newTown = new ListEntry(-1, I18n.get("list.new.town"), "");
/*     */     
/*  70 */     for (Map.Entry<String, List<IListEntry<String>>> config : (Iterable<Map.Entry<String, List<IListEntry<String>>>>)this.configs.entrySet())
/*     */     {
/*  72 */       ((List<ListEntry<String>>)config.getValue()).add(newTown);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(List<IListEntry<String>> newConfig) {
/*  79 */     newConfig.add(new ListEntry(-1, I18n.get("list.new.town"), ""));
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
/*     */   
/*     */   public IListEntry<String> createObject(String text, int iconId) {
/*  92 */     return createObject(text, iconId, ResourceLocations.DYNAMIC_TOWNS);
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
/*     */ 
/*     */   
/*     */   public IListEntry<String> createObject(String text, int iconId, nf iconTexture) {
/* 106 */     return (IListEntry<String>)new EditableListEntry(this.newItemIndex++, iconId, text, iconTexture);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListEntry<String> newItem, int itemIndex) {
/* 112 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 113 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxTowns.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */