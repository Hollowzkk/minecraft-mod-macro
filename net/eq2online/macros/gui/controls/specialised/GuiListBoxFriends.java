/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bib;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.list.FriendListEntry;
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
/*     */ public class GuiListBoxFriends
/*     */   extends GuiListBoxFilebound<String>
/*     */ {
/*  28 */   private static final Pattern PATTERN_LINE_FRIENDS_LEGACY = Pattern.compile("^([a-zA-Z0-9_]{2,16}),(\\d{1,3})", 2);
/*  29 */   private static final Pattern PATTERN_LINE_FRIENDS = Pattern.compile("^([0-9]{1,3}):([a-zA-Z0-9_]{2,16})", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListBoxFriends(Macros macros, bib minecraft, int controlId) {
/*  39 */     super(macros, minecraft, controlId, true, macros.getFile(".friends.txt"), PATTERN_LINE_FRIENDS);
/*     */     
/*  41 */     this.saveTrimTailSize = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  47 */     return MacroParam.Type.FRIEND.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  53 */     IListEntry<String> newEntry = getItemById(-1);
/*  54 */     removeItemAt(this.items.size() - 1);
/*  55 */     super.sort();
/*  56 */     addItem(newEntry);
/*  57 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigChanged(IConfigs configs) {
/*  63 */     if ((this.macros.getSettings()).configsForFriends) {
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
/*     */   public void load(IConfigs configs) {
/*  76 */     super.load(configs);
/*     */     
/*  78 */     ListEntry<String> newFriend = new ListEntry(-1, I18n.get("list.new.friend"), "");
/*     */     
/*  80 */     for (Map.Entry<String, List<IListEntry<String>>> config : (Iterable<Map.Entry<String, List<IListEntry<String>>>>)this.configs.entrySet())
/*     */     {
/*  82 */       ((List<ListEntry<String>>)config.getValue()).add(newFriend);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadItem(String line, Matcher linePatternMatcher, List<IListEntry<String>> items, String currentConfigName) {
/*  89 */     if (linePatternMatcher.matches()) {
/*     */       
/*  91 */       items.add(new FriendListEntry(this.newItemIndex++, Integer.parseInt(linePatternMatcher.group(1)), linePatternMatcher.group(2)));
/*     */     }
/*     */     else {
/*     */       
/*  95 */       Matcher friendPatternMatcher = PATTERN_LINE_FRIENDS_LEGACY.matcher(line);
/*     */       
/*  97 */       if (friendPatternMatcher.matches())
/*     */       {
/*  99 */         items.add(new FriendListEntry(this.newItemIndex++, Integer.parseInt(friendPatternMatcher.group(2)), friendPatternMatcher.group(1)));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(List<IListEntry<String>> newConfig) {
/* 107 */     newConfig.add(new ListEntry(-1, I18n.get("list.new.friend"), ""));
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
/* 120 */     return createObject(text, iconId, ResourceLocations.FRIENDS);
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
/* 134 */     return (IListEntry<String>)new FriendListEntry(this.newItemIndex++, iconId, text);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListEntry<String> newItem, int itemIndex) {
/* 140 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 141 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxFriends.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */