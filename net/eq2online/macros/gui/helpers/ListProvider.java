/*     */ package net.eq2online.macros.gui.helpers;
/*     */ import bib;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxIconic;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxFile;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxItems;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxPlaces;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxPresetText;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxResourcePack;
/*     */ import net.eq2online.macros.gui.list.ListEntry;
/*     */ import net.eq2online.macros.interfaces.IConfigs;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.ILoadableConfigObserver;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ import net.eq2online.macros.struct.ItemInfo;
/*     */ 
/*     */ public class ListProvider {
/*     */   protected final bib mc;
/*     */   protected final Macros macros;
/*     */   protected final ItemList items;
/*     */   
/*     */   public static interface IListBoxProvider {
/*     */     <T> GuiListBox<T> getListBox(String param1String);
/*     */     
/*     */     <T> GuiListBox<T> getFilteredListBox(String[] param1ArrayOfString, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3);
/*     */   }
/*     */   
/*     */   public abstract class AbstractListBoxProvider implements IListBoxProvider {
/*     */     public <T> GuiListBox<T> getListBox(String context) {
/*  35 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> GuiListBox<T> getFilteredListBox(String[] values, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  42 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class UserListBoxProvider
/*     */     extends AbstractListBoxProvider
/*     */   {
/*     */     public <T> GuiListBox<T> getListBox(String context) {
/*  52 */       GuiListBox<String> userListBox = new GuiListBox(ListProvider.this.mc, 2, 0, 0, 200, 200, 20, true, false, false);
/*     */       
/*  54 */       ListProvider.this.macros.getAutoDiscoveryHandler().populateUserListBox(userListBox, false);
/*  55 */       return (GuiListBox)userListBox;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> GuiListBox<T> getFilteredListBox(String[] values, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  63 */       GuiListBox<String> listBox = new GuiListBox(ListProvider.this.mc, controlId, xPos, yPos, controlWidth, controlHeight, rowHeight, showIcons, dragSource, dragTarget);
/*     */       
/*  65 */       int playerId = 0;
/*     */       
/*  67 */       for (String value : values)
/*     */       {
/*  69 */         listBox.addItem((IListEntry)new OnlineUserListEntry(playerId++, value, ListProvider.this.macros.getUserSkinHandler()));
/*     */       }
/*     */       
/*  72 */       return (GuiListBox)listBox;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class StringListBoxProvider
/*     */     extends AbstractListBoxProvider
/*     */   {
/*     */     public <T> GuiListBox<T> getFilteredListBox(String[] values, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  82 */       GuiListBox<T> listBox = new GuiListBox(ListProvider.this.mc, controlId, xPos, yPos, controlWidth, controlHeight, rowHeight, showIcons, dragSource, dragTarget);
/*     */       
/*  84 */       int id = 0;
/*     */       
/*  86 */       for (String value : values)
/*     */       {
/*  88 */         listBox.addItem((IListEntry)new ListEntry(id++, value));
/*     */       }
/*     */       
/*  91 */       return listBox;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class FilteredItemListBoxProvider
/*     */     extends AbstractListBoxProvider
/*     */   {
/*     */     public <T> GuiListBox<T> getFilteredListBox(String[] values, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/* 102 */       GuiListBoxIconic<T> listBox = new GuiListBoxIconic(ListProvider.this.mc, 2);
/*     */       
/* 104 */       for (String value : values) {
/*     */         
/* 106 */         ItemID itemId = new ItemID(value);
/* 107 */         for (ItemInfo item : ListProvider.this.items.getInfoForItem(itemId)) {
/*     */           
/* 109 */           if (!itemId.hasDamage || item.getDamage() == itemId.damage)
/*     */           {
/* 111 */             listBox.addItem((IListEntry)item);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 116 */       return (GuiListBox<T>)listBox;
/*     */     }
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
/* 129 */   private Map<String, GuiListBox<?>> listBoxes = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   private Map<String, IListBoxProvider> listBoxProviders = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ListProvider(Macros macros, bib mc) {
/* 138 */     this.mc = mc;
/* 139 */     this.macros = macros;
/* 140 */     this.items = new ItemList(macros);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(Macros macros) {
/* 145 */     this.items.refresh();
/* 146 */     createListboxes(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createListboxes(int controlId) {
/* 156 */     registerListBoxProvider(MacroParam.Type.USER, new UserListBoxProvider());
/* 157 */     registerListBoxProvider(MacroParam.Type.ITEM, new FilteredItemListBoxProvider());
/* 158 */     registerListBoxProvider(MacroParam.Type.NORMAL, new StringListBoxProvider());
/*     */     
/* 160 */     registerListBox(MacroParam.Type.ITEM, (GuiListBox<?>)new GuiListBoxItems(this.mc, controlId, this.items));
/* 161 */     registerListBox(MacroParam.Type.FILE, (GuiListBox<?>)new GuiListBoxFile(this.mc, controlId, true, this.macros.getMacrosDirectory(), "txt"));
/* 162 */     registerListBox(MacroParam.Type.RESOURCE_PACK, (GuiListBox<?>)new GuiListBoxResourcePack(this.mc, controlId, true));
/* 163 */     registerListBox(MacroParam.Type.SHADER_GROUP, (GuiListBox<?>)new GuiListBoxShaders(this.mc, controlId, true));
/* 164 */     registerListBox((GuiListBoxFilebound<?>)new GuiListBoxFriends(this.macros, this.mc, controlId));
/* 165 */     registerListBox((GuiListBoxFilebound<?>)new GuiListBoxTowns(this.macros, this.mc, controlId));
/* 166 */     registerListBox((GuiListBoxFilebound<?>)new GuiListBoxWarps(this.macros, this.mc, controlId));
/* 167 */     registerListBox((GuiListBoxFilebound<?>)new GuiListBoxHomes(this.macros, this.mc, controlId));
/* 168 */     registerListBox((GuiListBoxFilebound<?>)new GuiListBoxPlaces(this.macros, this.mc, controlId));
/*     */     
/* 170 */     for (int l = 0; l < 10; l++)
/*     */     {
/* 172 */       registerListBox((GuiListBoxFilebound<?>)new GuiListBoxPresetText(this.macros, this.mc, controlId, l));
/*     */     }
/*     */     
/* 175 */     for (GuiListBox<?> listBox : this.listBoxes.values()) {
/*     */       
/* 177 */       if (listBox instanceof ILoadableConfigObserver)
/*     */       {
/* 179 */         ((ILoadableConfigObserver)listBox).load((IConfigs)this.macros);
/*     */       }
/*     */     } 
/*     */     
/* 183 */     ((GuiListBox)this.listBoxes.get(MacroParam.Type.FRIEND.getKey())).save();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerListBox(GuiListBoxFilebound<?> listBox) {
/* 188 */     this.listBoxes.put(listBox.getKey(), listBox);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerListBox(MacroParam.Type type, GuiListBox<?> listBox) {
/* 193 */     this.listBoxes.put(type.getKey(), listBox);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerListBoxProvider(MacroParam.Type type, IListBoxProvider provider) {
/* 198 */     this.listBoxProviders.put(type.getKey(), provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> GuiListBox<T> getListBox(MacroParam.Type type) {
/* 206 */     return getListBox(type, "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> GuiListBox<T> getListBox(MacroParam.Type type, String context) {
/* 212 */     IListBoxProvider provider = this.listBoxProviders.get(type.getKey());
/* 213 */     if (provider != null) {
/*     */       
/* 215 */       GuiListBox<T> listBox = provider.getListBox(context);
/* 216 */       if (listBox != null)
/*     */       {
/* 218 */         return listBox;
/*     */       }
/*     */     } 
/*     */     
/* 222 */     return (GuiListBox<T>)this.listBoxes.get(type.getKey(context));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> GuiListBox<T> getListBox(MacroParam.Type type, String[] values, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/* 228 */     IListBoxProvider provider = this.listBoxProviders.get(type.getKey());
/* 229 */     if (provider != null) {
/*     */       
/* 231 */       GuiListBox<T> listBox = provider.getFilteredListBox(values, controlId, xPos, yPos, controlWidth, controlHeight, rowHeight, showIcons, dragSource, dragTarget);
/*     */       
/* 233 */       if (listBox != null)
/*     */       {
/* 235 */         return listBox;
/*     */       }
/*     */     } 
/*     */     
/* 239 */     return new GuiListBox(this.mc, controlId, xPos, yPos, controlWidth, controlHeight, rowHeight, showIcons, dragSource, dragTarget);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\helpers\ListProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */