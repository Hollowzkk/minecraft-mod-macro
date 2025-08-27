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
/*     */ public class GuiListBoxWarps
/*     */   extends GuiListBoxFilebound<String>
/*     */ {
/*     */   public GuiListBoxWarps(Macros macros, bib minecraft, int controlId) {
/*  27 */     super(macros, minecraft, controlId, true, macros.getFile(".warps.txt"), true);
/*     */     
/*  29 */     this.iconTexture = ResourceLocations.DYNAMIC_TOWNS;
/*  30 */     this.saveTrimTailSize = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  36 */     return MacroParam.Type.WARP.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  42 */     IListEntry<String> newEntry = getItemById(-1);
/*  43 */     removeItemAt(this.items.size() - 1);
/*  44 */     super.sort();
/*  45 */     addItem(newEntry);
/*  46 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigChanged(IConfigs configs) {
/*  52 */     if ((this.macros.getSettings()).configsForWarps) {
/*     */       
/*  54 */       super.onConfigChanged(configs);
/*     */     }
/*     */     else {
/*     */       
/*  58 */       this.items = (List)this.configs.get("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(IConfigs configs) {
/*  65 */     super.load(configs);
/*     */     
/*  67 */     ListEntry<String> newTown = new ListEntry(-1, I18n.get("list.new.warp"), "");
/*     */     
/*  69 */     for (Map.Entry<String, List<IListEntry<String>>> config : (Iterable<Map.Entry<String, List<IListEntry<String>>>>)this.configs.entrySet())
/*     */     {
/*  71 */       ((List<ListEntry<String>>)config.getValue()).add(newTown);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(List<IListEntry<String>> newConfig) {
/*  78 */     newConfig.add(new ListEntry(-1, I18n.get("list.new.warp"), ""));
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
/*  91 */     return createObject(text, iconId, ResourceLocations.DYNAMIC_TOWNS);
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
/* 105 */     return (IListEntry<String>)new EditableListEntry(this.newItemIndex++, iconId, text, iconTexture);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListEntry<String> newItem, int itemIndex) {
/* 111 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 112 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxWarps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */