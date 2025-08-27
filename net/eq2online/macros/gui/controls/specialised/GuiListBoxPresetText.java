/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bib;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.list.EditInPlaceListEntry;
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
/*     */ 
/*     */ public class GuiListBoxPresetText
/*     */   extends GuiListBoxFilebound<String>
/*     */ {
/*     */   private final int presetIndex;
/*     */   
/*     */   public GuiListBoxPresetText(Macros macros, bib minecraft, int controlId, int presetIndex) {
/*  33 */     super(macros, minecraft, controlId, false, macros.getFile(String.format(".presettext%d.txt", new Object[] { Integer.valueOf(presetIndex) })));
/*     */     
/*  35 */     this.saveTrimTailSize = 1;
/*  36 */     this.presetIndex = presetIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  42 */     return MacroParam.Type.PRESET.getKey(String.valueOf(this.presetIndex));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  48 */     IListEntry<String> newEntry = getItemById(-1);
/*  49 */     removeItemAt(this.items.size() - 1);
/*  50 */     super.sort();
/*  51 */     addItem(newEntry);
/*  52 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigChanged(IConfigs configs) {
/*  58 */     if ((this.macros.getSettings()).configsForPresets) {
/*     */       
/*  60 */       super.onConfigChanged(configs);
/*     */     }
/*     */     else {
/*     */       
/*  64 */       this.items = (List)this.configs.get("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(IConfigs configs) {
/*  71 */     super.load(configs);
/*     */     
/*  73 */     ListEntry<String> newPreset = new ListEntry(-1, I18n.get("list.new.preset"), "");
/*     */     
/*  75 */     for (Map.Entry<String, List<IListEntry<String>>> config : (Iterable<Map.Entry<String, List<IListEntry<String>>>>)this.configs.entrySet())
/*     */     {
/*  77 */       ((List<ListEntry<String>>)config.getValue()).add(newPreset);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadItem(String line, Matcher linePatternMatcher, List<IListEntry<String>> items, String currentConfigName) {
/*  84 */     if (linePatternMatcher.matches())
/*     */     {
/*  86 */       items.add(new EditInPlaceListEntry(this.newItemIndex++, 0, linePatternMatcher.group(1), ResourceLocations.ITEMS, ""));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(List<IListEntry<String>> newConfig) {
/*  93 */     newConfig.add(new ListEntry(-1, I18n.get("list.new.preset"), ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean left() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean right() {
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiControlEx.HandledState keyTyped(char keyChar, int keyCode) {
/* 117 */     if (keyCode == 205) {
/*     */       
/* 119 */       beginEditInPlace();
/* 120 */       return GuiControlEx.HandledState.HANDLED;
/*     */     } 
/*     */     
/* 123 */     return super.keyTyped(keyChar, keyCode);
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
/* 136 */     return createObject(text, 0, (nf)null);
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
/* 150 */     return (IListEntry<String>)new EditInPlaceListEntry(this.newItemIndex++, iconId, text, iconTexture, I18n.get("list.prompt.preset"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListEntry<String> newItem, int itemIndex) {
/* 156 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 157 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxPresetText.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */