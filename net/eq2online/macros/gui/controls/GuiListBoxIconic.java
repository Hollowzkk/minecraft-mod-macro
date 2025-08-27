/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.interfaces.IDecoratedListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IRenderedListEntry;
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
/*     */ public class GuiListBoxIconic<T>
/*     */   extends GuiListBox<T>
/*     */ {
/*  22 */   private static int fadeTicks = 40;
/*     */ 
/*     */   
/*     */   private int lastSelectedItem;
/*     */   
/*     */   private int lastSelectedItemCounter;
/*     */ 
/*     */   
/*     */   public GuiListBoxIconic(bib minecraft, int controlId) {
/*  31 */     super(minecraft, controlId, true, false, false);
/*     */     
/*  33 */     this.itemsPerRow = (this.f - 20) / this.rowSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListBoxIconic(bib minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  39 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, rowHeight, showIcons, dragSource, dragTarget);
/*     */     
/*  41 */     this.itemsPerRow = (this.f - 20) / this.rowSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListBox<T> setSize(int controlWidth, int controlHeight, int rowHeight, boolean showIcons) {
/*  51 */     super.setSize(controlWidth, controlHeight, rowHeight, showIcons);
/*     */     
/*  53 */     this.itemsPerRow = (this.f - 20) / this.rowSize;
/*  54 */     updateScrollBar();
/*     */     
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectId(int id) {
/*  65 */     super.selectId(id);
/*  66 */     this.lastSelectedItemCounter = this.updateCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawItems(int mouseX, int mouseY, int itemTextColour) {
/*  76 */     super.drawItems(mouseX, mouseY, itemTextColour);
/*     */     
/*  78 */     GL.glClear(256);
/*     */     
/*  80 */     int mouseOverItemIndex = getItemIndexAt(mouseX, mouseY);
/*     */     
/*  82 */     if (this.items.size() > 0 && this.selectedItem < this.items.size()) {
/*     */       
/*  84 */       if (this.selectedItem != this.lastSelectedItem) {
/*     */         
/*  86 */         this.lastSelectedItem = this.selectedItem;
/*  87 */         this.lastSelectedItemCounter = this.updateCounter;
/*     */       } 
/*     */       
/*  90 */       float fadeCounter = (fadeTicks - this.updateCounter - this.lastSelectedItemCounter);
/*     */       
/*  92 */       if (fadeCounter > 0.0F && this.selectedItem != mouseOverItemIndex) {
/*     */         
/*  94 */         int opacity = ((int)(fadeCounter / fadeTicks * 215.0F) & 0xFF) << 24;
/*     */         
/*  96 */         Rectangle itm = getItemBoundingBox(this.selectedItem);
/*  97 */         this.renderer.drawTooltip(((IListEntry)this.items.get(this.selectedItem)).getText(), itm.x + 8 + itm.width, itm.y + 18 + this.iconOffset, 999, 999, opacity | 0xFFFFFF, opacity | 0x0);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (mouseOverItemIndex > -1 && mouseOverItemIndex < this.items.size())
/*     */     {
/* 104 */       drawTooltip(((IListEntry)this.items.get(mouseOverItemIndex)).getText(), getItemBoundingBox(mouseOverItemIndex), 999, 999, itemTextColour, -805306368);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawTooltip(String tooltipText, Rectangle itemBoundingBox, int screenWidth, int screenHeight, int colour, int backgroundColour) {
/* 122 */     int textSize = this.mc.k.a(tooltipText) + this.iconOffset + this.iconOffset + 6;
/* 123 */     a(itemBoundingBox.x, itemBoundingBox.y, itemBoundingBox.x + itemBoundingBox.width + textSize, itemBoundingBox.y + itemBoundingBox.height, 1082163328);
/*     */     
/* 125 */     this.renderer.drawTooltip(tooltipText, itemBoundingBox.x + 8 + itemBoundingBox.width, itemBoundingBox.y + 18 + this.iconOffset, screenWidth, screenHeight, colour, backgroundColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderItem(IListEntry<T> item, bib minecraft, int mouseX, int mouseY, int itemX, int itemY, int itemWidth, int itemHeight, int itemTextColour) {
/* 134 */     if (item instanceof IRenderedListEntry) {
/*     */       
/* 136 */       ((IRenderedListEntry)item).render(this.iconsEnabled, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*     */     }
/*     */     else {
/*     */       
/* 140 */       if (!item.renderIcon(minecraft, itemX + this.iconOffset, itemY + this.iconOffset) && item.hasIcon() && item.getIcon() != null) {
/*     */         
/* 142 */         a(itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16, 1090519039);
/* 143 */         drawIcon(item.getIconTexture(), item.getIcon(), itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 148 */       if (item instanceof IDecoratedListEntry)
/*     */       {
/* 150 */         ((IDecoratedListEntry)item).decorate(this.iconsEnabled, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiListBoxIconic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */