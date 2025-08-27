/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.res.ResourceLocations;
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
/*     */ public class GuiDropDownList
/*     */   extends GuiDropDownMenu
/*     */ {
/*     */   private final IDropDownContainer container;
/*     */   private int lastX;
/*     */   private int lastY;
/*     */   
/*     */   public static class GuiDropDownListControl
/*     */     extends GuiControlEx
/*     */     implements IDropDownContainer
/*     */   {
/*     */     private final GuiDropDownList.IDropDownContainer container;
/*     */     private final GuiDropDownList list;
/*     */     
/*     */     public GuiDropDownListControl(GuiDropDownList.IDropDownContainer container, bib minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int itemHeight, String emptyText) {
/*  47 */       super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, emptyText);
/*  48 */       this.container = container;
/*  49 */       this.list = new GuiDropDownList(this, minecraft, controlWidth, controlHeight, itemHeight);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/*  59 */       this.list.drawControlAt(this.h, this.i, mouseX, mouseY);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawControlAt(bib minecraft, int mouseX, int mouseY, int yPosition) {
/*  70 */       this.list.drawControlAt(this.h, yPosition, mouseX, mouseY);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean b(bib minecraft, int mouseX, int mouseY) {
/*  80 */       return (this.list.mousePressed(mouseX, mouseY) != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GuiDropDownMenu addItem(String itemKey, String itemName) {
/*  92 */       return this.list.addItem(itemKey, itemName);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void selectItem(String item) {
/* 102 */       this.list.selectItem(item);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void selectItemByTag(String itemTag) {
/* 112 */       this.list.selectItemByTag(itemTag);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void selectItem(int itemIndex) {
/* 122 */       this.list.selectItem(itemIndex);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GuiDropDownMenu.Item getSelectedItem() {
/* 130 */       return this.list.getSelectedItem();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSelectedItemTag() {
/* 135 */       GuiDropDownMenu.Item item = this.list.getSelectedItem();
/* 136 */       return (item != null) ? item.getTag() : null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final int getContainerWidth() {
/* 142 */       return this.container.getContainerWidth();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final int getContainerHeight() {
/* 148 */       return this.container.getContainerHeight();
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
/* 162 */   private GuiDropDownMenu.Item selectedItem = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   private int fieldHeight = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String emptyText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownList(IDropDownContainer container, bib minecraft) {
/* 179 */     this(container, minecraft, 200, 16, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownList(IDropDownContainer container, bib minecraft, int width, int height, int itemHeight) {
/* 189 */     super(minecraft, width, itemHeight, true);
/* 190 */     this.autoSize = false;
/* 191 */     this.fieldHeight = height;
/* 192 */     this.container = container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu addItem(String itemKey, String itemName) {
/* 202 */     super.addItem(itemKey, itemName);
/*     */     
/* 204 */     if (this.selectedItem == null)
/*     */     {
/* 206 */       this.selectedItem = this.items.get(0);
/*     */     }
/*     */     
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu addSeparator() {
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectItem(String text) {
/* 226 */     if (text == null)
/*     */       return; 
/* 228 */     for (GuiDropDownMenu.Item item : this.items) {
/*     */       
/* 230 */       if (text.equals(item.text)) {
/*     */         
/* 232 */         this.selectedItem = item;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectItemByTag(String tag) {
/* 243 */     if (tag == null)
/*     */       return; 
/* 245 */     for (GuiDropDownMenu.Item item : this.items) {
/*     */       
/* 247 */       if (tag.equals(item.tag)) {
/*     */         
/* 249 */         this.selectedItem = item;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectItem(int itemIndex) {
/* 260 */     if (itemIndex == -1) {
/*     */       
/* 262 */       this.selectedItem = null;
/*     */       
/*     */       return;
/*     */     } 
/* 266 */     if (itemIndex > -1 && itemIndex < this.items.size())
/*     */     {
/* 268 */       this.selectedItem = this.items.get(itemIndex);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu.Item getSelectedItem() {
/* 277 */     return this.selectedItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawControlAt(int x, int y, int mouseX, int mouseY) {
/* 287 */     this.lastX = x;
/* 288 */     this.lastY = y;
/*     */     
/* 290 */     a(x - 1, y - 1, x + this.width + 1, y + this.fieldHeight + 1, -6250336);
/* 291 */     a(x, y, x + this.width, y + this.fieldHeight, -16777216);
/*     */     
/* 293 */     int top = y + (this.fieldHeight - 8) / 2;
/*     */     
/* 295 */     if (this.selectedItem != null) {
/*     */       
/* 297 */       this.fontRenderer.a(this.selectedItem.toString(), x + 4, top, -1118482);
/*     */     }
/*     */     else {
/*     */       
/* 301 */       this.fontRenderer.a(this.emptyText, x + 4, top, -8355712);
/*     */     } 
/*     */     
/* 304 */     this.mc.N().a(ResourceLocations.FIXEDWIDTHFONT);
/*     */     
/* 306 */     a(x + this.width - 16, y, x + this.width, y + this.fieldHeight, -16777216);
/*     */     
/* 308 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 309 */     drawTexturedModalRect(x + this.width - 12, top, x + this.width - 4, top + 8, 240, 16, 256, 32);
/*     */     
/* 311 */     super.drawControlAt(x, Math.min(y + this.fieldHeight, this.container.getContainerHeight() - this.height), mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu.Item mousePressed(int mouseX, int mouseY) {
/* 321 */     if (this.dropDownVisible) {
/*     */       
/* 323 */       GuiDropDownMenu.Item item = super.mousePressed(mouseX, mouseY);
/* 324 */       if (item != null)
/*     */       {
/* 326 */         this.selectedItem = item;
/*     */       }
/* 328 */       return this.selectedItem;
/*     */     } 
/*     */     
/* 331 */     if (mouseX > this.lastX && mouseX < this.lastX + this.width && mouseY > this.lastY && mouseY < this.lastY + this.fieldHeight)
/*     */     {
/* 333 */       this.dropDownVisible = !this.dropDownVisible;
/*     */     }
/*     */     
/* 336 */     return null;
/*     */   }
/*     */   
/*     */   public static interface IDropDownContainer {
/*     */     int getContainerWidth();
/*     */     
/*     */     int getContainerHeight();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiDropDownList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */