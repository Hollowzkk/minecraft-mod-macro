/*     */ package net.eq2online.macros.gui.list;
/*     */ 
/*     */ import aip;
/*     */ import bib;
/*     */ import bir;
/*     */ import bzw;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.gui.GuiRenderer;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListEntry<T>
/*     */   extends bir
/*     */   implements IListEntry<T>
/*     */ {
/*  23 */   protected static GuiRenderer renderer = new GuiRenderer(bib.z());
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
/*     */   protected boolean hasIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected nf iconTexture;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Icon icon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String displayName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T data;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected aip displayItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListEntry(int id, String text, T data, boolean hasIcon, nf iconTexture, int iconID) {
/*  76 */     this.id = id;
/*  77 */     this.text = text;
/*  78 */     this.data = data;
/*  79 */     this.hasIcon = hasIcon;
/*  80 */     initIcon(iconTexture, iconID);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListEntry(int id, String text, T data, nf iconTexture, Icon icon) {
/*  85 */     this.id = id;
/*  86 */     this.text = text;
/*  87 */     this.data = data;
/*  88 */     this.hasIcon = (icon != null);
/*  89 */     this.icon = icon;
/*  90 */     this.iconTexture = iconTexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListEntry(int id, String text, T data, Icon icon) {
/*  95 */     this(id, text, data, (icon instanceof IconTiled) ? ((IconTiled)icon).getTextureResource() : ResourceLocations.ITEMS, icon);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListEntry(int id, String text, Icon icon) {
/* 100 */     this(id, text, null, (icon instanceof IconTiled) ? ((IconTiled)icon).getTextureResource() : ResourceLocations.ITEMS, icon);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListEntry(int id, String text, T data, aip displayItem) {
/* 105 */     this.id = id;
/* 106 */     this.text = text;
/* 107 */     this.data = data;
/* 108 */     this.hasIcon = false;
/* 109 */     this.icon = null;
/* 110 */     this.iconTexture = null;
/* 111 */     this.displayItem = displayItem;
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
/*     */   public ListEntry(int id, String text, T data) {
/* 123 */     this.id = id;
/* 124 */     this.text = text;
/* 125 */     this.hasIcon = false;
/* 126 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListEntry(int id, String text) {
/* 137 */     this.id = id;
/* 138 */     this.text = text;
/* 139 */     this.hasIcon = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initIcon(nf iconTexture, int iconID) {
/* 148 */     iconID %= 256;
/* 149 */     this.icon = (iconTexture != null) ? (Icon)new IconTiled(iconTexture, iconID) : null;
/* 150 */     this.iconTexture = iconTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIcon() {
/* 159 */     return this.hasIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public nf getIconTexture() {
/* 168 */     return this.iconTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getIcon() {
/* 178 */     return this.icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 187 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 193 */     return getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 202 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIdentifier() {
/* 208 */     return String.valueOf(this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getData() {
/* 217 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListEntry.CustomAction getCustomAction(boolean bClear) {
/* 227 */     return IListEntry.CustomAction.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIconId(int newIconId) {
/* 233 */     if (this.icon != null && this.icon instanceof IconTiled) {
/*     */       
/* 235 */       newIconId %= 256;
/* 236 */       ((IconTiled)this.icon).setIconId(newIconId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String newText) {
/* 243 */     this.text = newText;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayName(String newDisplayName) {
/* 249 */     if (newDisplayName.equals(getText()))
/*     */     {
/* 251 */       newDisplayName = "";
/*     */     }
/*     */     
/* 254 */     this.displayName = newDisplayName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int newId) {
/* 260 */     this.id = newId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(T newData) {
/* 266 */     this.data = newData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderIcon(bib minecraft, int xPosition, int yPosition) {
/* 272 */     if (this.displayItem != null) {
/*     */       
/* 274 */       bzw render = minecraft.ad();
/* 275 */       render.a(this.displayItem, xPosition, yPosition);
/* 276 */       return true;
/*     */     } 
/*     */     
/* 279 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\list\ListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */