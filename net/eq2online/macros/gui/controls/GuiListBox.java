/*      */ package net.eq2online.macros.gui.controls;
/*      */ 
/*      */ import aip;
/*      */ import bib;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.security.InvalidParameterException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ import net.eq2online.macros.gui.GuiControl;
/*      */ import net.eq2online.macros.gui.GuiControlEx;
/*      */ import net.eq2online.macros.gui.list.ListEntry;
/*      */ import net.eq2online.macros.interfaces.IDecoratedListEntry;
/*      */ import net.eq2online.macros.interfaces.IDragDrop;
/*      */ import net.eq2online.macros.interfaces.IDraggable;
/*      */ import net.eq2online.macros.interfaces.IEditInPlace;
/*      */ import net.eq2online.macros.interfaces.IInteractiveListEntry;
/*      */ import net.eq2online.macros.interfaces.IListEntry;
/*      */ import net.eq2online.macros.interfaces.IRenderedListEntry;
/*      */ import nf;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GuiListBox<T>
/*      */   extends GuiControlEx
/*      */   implements IDragDrop<T>
/*      */ {
/*      */   public static final int DEFAULT_ROW_HEIGHT = 20;
/*      */   protected boolean iconsEnabled;
/*      */   protected boolean isDragSource;
/*      */   protected boolean isDragTarget;
/*   58 */   protected List<IDragDrop<T>> dragTargets = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   63 */   protected IDraggable<T> dragItem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   protected Point dragOffset = new Point();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   protected Point mouseDownLocation = new Point();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   protected Boolean mouseDelta = Boolean.valueOf(false);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int rowSize;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   88 */   protected int itemsPerRow = 1;
/*      */ 
/*      */   
/*      */   protected int textOffset;
/*      */ 
/*      */   
/*      */   protected int iconOffset;
/*      */ 
/*      */   
/*      */   protected int iconSpacing;
/*      */ 
/*      */   
/*      */   protected int displayRowCount;
/*      */ 
/*      */   
/*  103 */   protected int selectedItem = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   protected int editInPlaceItem = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   protected int timer = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected GuiScrollBar scrollBar;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   protected List<IListEntry<T>> items = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IInteractiveListEntry<T> mouseDownObject;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  133 */   protected int newItemIndex = 0;
/*      */   
/*  135 */   public int backColour = -2146562560;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortable = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox(bib minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  157 */     super(minecraft, controlId, xPos, yPos, Math.max(controlWidth, 60), Math.max(controlHeight, 8), "");
/*      */     
/*  159 */     this.rowSize = Math.max(rowHeight, 8);
/*  160 */     this.textOffset = (this.rowSize - 8) / 2;
/*  161 */     this.iconOffset = (this.rowSize - 16) / 2;
/*  162 */     this.iconSpacing = showIcons ? this.rowSize : 0;
/*  163 */     this.displayRowCount = this.g / this.rowSize;
/*      */     
/*  165 */     this.iconsEnabled = (showIcons && rowHeight >= 16);
/*      */     
/*  167 */     this.isDragSource = dragSource;
/*  168 */     this.isDragTarget = dragTarget;
/*      */     
/*  170 */     if (dragSource && dragTarget)
/*      */     {
/*  172 */       this.dragTargets.add(this);
/*      */     }
/*      */     
/*  175 */     this.scrollBar = new GuiScrollBar(minecraft, controlId, xPos + controlWidth - 20, yPos, 20, this.g, 0, 0, GuiScrollBar.ScrollBarOrientation.VERTICAL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox(bib minecraft, int controlId, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  189 */     this(minecraft, controlId, 0, 0, 100, 20, 20, showIcons, dragSource, dragTarget);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSortable() {
/*  194 */     return this.sortable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox<T> setSortable(boolean sortable) {
/*  202 */     this.sortable = sortable;
/*  203 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox<T> setPosition(int newXPosition, int newYPosition) {
/*  209 */     super.setPosition(newXPosition, newYPosition);
/*  210 */     updateScrollBarPosition();
/*  211 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox<T> setSize(int controlWidth, int controlHeight) {
/*  223 */     setSize(controlWidth, controlHeight, this.rowSize, this.iconsEnabled);
/*  224 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox<T> setSize(int controlWidth, int controlHeight, int rowHeight, boolean showIcons) {
/*  238 */     this.f = Math.max(controlWidth, 60);
/*  239 */     this.g = Math.max(controlHeight, 40);
/*      */     
/*  241 */     this.rowSize = Math.max(rowHeight, 8);
/*  242 */     this.textOffset = (this.rowSize - 8) / 2;
/*  243 */     this.iconOffset = (this.rowSize - 16) / 2;
/*  244 */     this.iconSpacing = showIcons ? this.rowSize : 0;
/*      */     
/*  246 */     this.displayRowCount = this.g / this.rowSize;
/*  247 */     this.iconsEnabled = (showIcons && rowHeight >= 16);
/*      */     
/*  249 */     updateScrollBarPosition();
/*  250 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/*  263 */     setPosition(left, top);
/*  264 */     setSize(controlWidth, controlHeight);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight, int rowHeight, boolean showIcons) {
/*  279 */     setPosition(left, top);
/*  280 */     setSize(controlWidth, controlHeight, rowHeight, showIcons);
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateScrollBarPosition() {
/*  285 */     this.scrollBar.setSizeAndPosition(this.h + this.f - 20, this.i, 20, this.g);
/*  286 */     updateScrollBar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  294 */     endEditInPlace();
/*  295 */     this.scrollBar.setValue(0);
/*  296 */     this.scrollBar.setMax(0);
/*  297 */     this.items.clear();
/*  298 */     this.mouseDownObject = null;
/*  299 */     this.selectedItem = 0;
/*  300 */     this.timer = this.updateCounter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItem(IListEntry<T> newItem) {
/*  310 */     this.items.add(newItem);
/*  311 */     updateScrollBar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItemAt(IListEntry<T> newItem, int itemIndex) {
/*  322 */     if (itemIndex < 0) itemIndex = 0;
/*      */     
/*  324 */     if (itemIndex >= this.items.size()) {
/*      */       
/*  326 */       this.items.add(newItem);
/*      */     }
/*      */     else {
/*      */       
/*  330 */       this.items.add(itemIndex, newItem);
/*      */     } 
/*      */     
/*  333 */     updateScrollBar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> removeItemAt(int itemIndex) {
/*  343 */     IListEntry<T> removedObject = null;
/*      */     
/*  345 */     if (itemIndex >= 0 && itemIndex < this.items.size()) {
/*      */       
/*  347 */       removedObject = this.items.remove(itemIndex);
/*  348 */       updateScrollBar();
/*  349 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  352 */     return removedObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItem(IListEntry<T> item) {
/*  362 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  364 */       if (this.items.get(i) == item) {
/*      */         
/*  366 */         removeItemAt(i);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean up() {
/*  377 */     if (this.l) {
/*      */       
/*  379 */       this.selectedItem -= this.itemsPerRow;
/*  380 */       endEditInPlace();
/*  381 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  384 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean down() {
/*  392 */     if (this.l) {
/*      */       
/*  394 */       this.selectedItem += this.itemsPerRow;
/*  395 */       endEditInPlace();
/*  396 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  399 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean left() {
/*  407 */     if (this.l) {
/*      */       
/*  409 */       this.selectedItem--;
/*  410 */       endEditInPlace();
/*  411 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  414 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean right() {
/*  422 */     if (this.l) {
/*      */       
/*  424 */       this.selectedItem++;
/*  425 */       endEditInPlace();
/*  426 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  429 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean pageUp() {
/*  437 */     this.selectedItem -= this.displayRowCount * this.itemsPerRow;
/*  438 */     endEditInPlace();
/*  439 */     updateScrollPosition();
/*      */     
/*  441 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean pageDown() {
/*  449 */     this.selectedItem += this.displayRowCount * this.itemsPerRow;
/*  450 */     endEditInPlace();
/*  451 */     updateScrollPosition();
/*      */     
/*  453 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectedItemIndex(int itemIndex) {
/*  463 */     this.selectedItem = itemIndex;
/*  464 */     if (itemIndex != this.editInPlaceItem) endEditInPlace(); 
/*  465 */     updateScrollPosition();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scrollToCentre() {
/*  473 */     int startRow = Math.max(0, this.selectedItem / this.itemsPerRow - this.displayRowCount / 2);
/*  474 */     this.scrollBar.setValue(startRow);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectId(int id) {
/*  484 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  486 */       if (((IListEntry)this.items.get(i)).getId() == id) {
/*      */         
/*  488 */         setSelectedItemIndex(i);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectData(Object data) {
/*  501 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  503 */       if (((IListEntry)this.items.get(i)).getData() != null && ((IListEntry)this.items.get(i)).getData().equals(data)) {
/*      */         
/*  505 */         setSelectedItemIndex(i);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean selectIdWithData(int id, Object data) {
/*  519 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  521 */       if (((IListEntry)this.items.get(i)).getId() == id && ((((IListEntry)this.items
/*  522 */         .get(i)).getData() == null && data == null) || data == null || ((IListEntry)this.items.get(i)).getData().equals(data))) {
/*      */         
/*  524 */         setSelectedItemIndex(i);
/*  525 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  529 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean selectIdentifier(String identifier) {
/*  534 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  536 */       if (((IListEntry)this.items.get(i)).getIdentifier().equals(identifier)) {
/*      */         
/*  538 */         setSelectedItemIndex(i);
/*  539 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  543 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean selectIdentifierWithData(String identifier, Object data) {
/*  548 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  550 */       if (((IListEntry)this.items.get(i)).getIdentifier().equals(identifier) && ((((IListEntry)this.items
/*  551 */         .get(i)).getData() == null && data == null) || data == null || ((IListEntry)this.items.get(i)).getData().equals(data))) {
/*      */         
/*  553 */         setSelectedItemIndex(i);
/*  554 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  558 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectItem(IListEntry<T> item) {
/*  568 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  570 */       if (this.items.get(i) == item) {
/*      */         
/*  572 */         setSelectedItemIndex(i);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount() {
/*  583 */     return this.items.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectedItemIndex() {
/*  593 */     return this.selectedItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> getSelectedItem() {
/*  603 */     return (this.items.size() > this.selectedItem && this.selectedItem > -1) ? this.items.get(this.selectedItem) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> removeSelectedItem() {
/*  613 */     if (this.items.size() > this.selectedItem && this.selectedItem > -1) {
/*      */       
/*  615 */       IListEntry<T> removed = this.items.remove(this.selectedItem);
/*  616 */       updateScrollPosition();
/*  617 */       return removed;
/*      */     } 
/*      */     
/*  620 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> getItemById(int id) {
/*  631 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  633 */       if (((IListEntry)this.items.get(i)).getId() == id)
/*      */       {
/*  635 */         return this.items.get(i);
/*      */       }
/*      */     } 
/*      */     
/*  639 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsItem(Object itemData) {
/*  649 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  651 */       if (((IListEntry)this.items.get(i)).getData().equals(itemData))
/*      */       {
/*  653 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  657 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sort() {
/*  665 */     if (!isSortable())
/*      */       return; 
/*  667 */     IListEntry<T> selectedItem = getSelectedItem();
/*      */     
/*  669 */     TreeMap<String, IListEntry<T>> sortList = new TreeMap<>();
/*      */     
/*  671 */     while (this.items.size() > 0)
/*      */     {
/*  673 */       sortList.put(((IListEntry)this.items.get(0)).getText().toLowerCase(), this.items.remove(0));
/*      */     }
/*      */     
/*  676 */     for (Map.Entry<String, IListEntry<T>> item : sortList.entrySet())
/*      */     {
/*  678 */       this.items.add(item.getValue());
/*      */     }
/*      */     
/*  681 */     if (selectedItem != null)
/*      */     {
/*  683 */       selectItem(selectedItem);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getItemBoundingBox(int itemId) {
/*  696 */     int itemWidth = (this.itemsPerRow > 1) ? this.rowSize : (this.f - 20);
/*      */     
/*  698 */     return new Rectangle(this.h + itemId % this.itemsPerRow * itemWidth, this.i + this.rowSize * (itemId / this.itemsPerRow - this.scrollBar
/*      */         
/*  700 */         .getValue()), itemWidth, this.rowSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getTotalRowCount() {
/*  714 */     int rowCount = this.items.size() / this.itemsPerRow;
/*  715 */     if (this.items.size() % this.itemsPerRow > 0) rowCount++; 
/*  716 */     return rowCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateScrollBar() {
/*  724 */     this.scrollBar.setMax(Math.max(0, getTotalRowCount() - this.displayRowCount));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateScrollPosition() {
/*  734 */     updateScrollPosition(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateScrollPosition(boolean noEndEdit) {
/*  744 */     if (this.selectedItem < 0) this.selectedItem = 0; 
/*  745 */     if (this.selectedItem >= this.items.size()) this.selectedItem = this.items.size() - 1;
/*      */     
/*  747 */     int selectedItemRow = this.selectedItem / this.itemsPerRow;
/*      */     
/*  749 */     if (selectedItemRow < this.scrollBar.getValue())
/*      */     {
/*  751 */       this.scrollBar.setValue(selectedItemRow);
/*      */     }
/*      */     
/*  754 */     if (selectedItemRow > this.scrollBar.getValue() + this.displayRowCount - 1)
/*      */     {
/*  756 */       this.scrollBar.setValue(selectedItemRow - this.displayRowCount + 1);
/*      */     }
/*      */     
/*  759 */     if (!noEndEdit && this.editInPlaceItem > -1 && this.editInPlaceItem != this.selectedItem)
/*      */     {
/*  761 */       endEditInPlace();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginEditInPlace() {
/*  770 */     beginEditInPlace(this.selectedItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginEditInPlace(int editInPlaceIndex) {
/*  780 */     if (editInPlaceIndex == this.editInPlaceItem)
/*      */       return; 
/*  782 */     endEditInPlace();
/*      */     
/*  784 */     if (editInPlaceIndex > -1 && editInPlaceIndex < this.items.size() && this.items.get(editInPlaceIndex) instanceof IEditInPlace) {
/*      */       
/*  786 */       this.editInPlaceItem = editInPlaceIndex;
/*  787 */       ((IEditInPlace)this.items.get(this.editInPlaceItem)).setEditInPlace(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endEditInPlace() {
/*  796 */     if (getEditingInPlaceObject() != null) {
/*      */       
/*  798 */       ((IEditInPlace)this.items.get(this.editInPlaceItem)).setEditInPlace(false);
/*      */       
/*  800 */       if ("".equals(((IListEntry)this.items.get(this.editInPlaceItem)).getText()))
/*      */       {
/*  802 */         removeItemAt(this.editInPlaceItem);
/*      */       }
/*      */       
/*  805 */       updateScrollPosition(true);
/*  806 */       save();
/*      */     } 
/*      */     
/*  809 */     this.editInPlaceItem = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEditInPlace<T> getEditingInPlaceObject() {
/*  817 */     if (this.editInPlaceItem > -1 && this.editInPlaceItem < this.items.size() && this.items.get(this.editInPlaceItem) instanceof IEditInPlace)
/*      */     {
/*  819 */       return (IEditInPlace<T>)this.items.get(this.editInPlaceItem);
/*      */     }
/*      */     
/*  822 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GuiControlEx.HandledState listBoxKeyTyped(char keyChar, int keyCode) {
/*  833 */     IListEntry<T> currentItem = getSelectedItem();
/*      */     
/*  835 */     this.doubleClicked = false;
/*  836 */     this.actionPerformed = false;
/*      */     
/*  838 */     if (currentItem != null) {
/*      */       
/*  840 */       if (currentItem instanceof IEditInPlace && ((IEditInPlace)currentItem).isEditingInPlace()) {
/*      */         
/*  842 */         if (this.editInPlaceItem < this.scrollBar.getValue() || this.editInPlaceItem >= this.scrollBar.getValue() + this.displayRowCount)
/*      */         {
/*  844 */           scrollToCentre();
/*      */         }
/*      */         
/*  847 */         if (!((IEditInPlace)currentItem).editInPlaceKeyTyped(keyChar, keyCode)) {
/*      */           
/*  849 */           endEditInPlace();
/*      */           
/*  851 */           if (keyCode == 1)
/*      */           {
/*  853 */             return GuiControlEx.HandledState.HANDLED;
/*      */           }
/*      */           
/*  856 */           if (keyCode == 200) up(); 
/*  857 */           if (keyCode == 208) down(); 
/*  858 */           if (keyCode == 203) left(); 
/*  859 */           if (keyCode == 205) right(); 
/*  860 */           if (keyCode == 201) pageUp(); 
/*  861 */           if (keyCode == 209) pageDown();
/*      */           
/*  863 */           return GuiControlEx.HandledState.ACTION_PERFORMED;
/*      */         } 
/*  865 */         return GuiControlEx.HandledState.HANDLED;
/*      */       } 
/*      */       
/*  868 */       if (keyCode == 200 && up()) return GuiControlEx.HandledState.ACTION_PERFORMED; 
/*  869 */       if (keyCode == 208 && down()) return GuiControlEx.HandledState.ACTION_PERFORMED; 
/*  870 */       if (keyCode == 203 && left()) return GuiControlEx.HandledState.ACTION_PERFORMED; 
/*  871 */       if (keyCode == 205 && right()) return GuiControlEx.HandledState.ACTION_PERFORMED; 
/*  872 */       if (keyCode == 201 && pageUp()) return GuiControlEx.HandledState.ACTION_PERFORMED; 
/*  873 */       if (keyCode == 209 && pageDown()) return GuiControlEx.HandledState.ACTION_PERFORMED;
/*      */       
/*  875 */       return keyTyped(keyChar, keyCode);
/*      */     } 
/*      */     
/*  878 */     return GuiControlEx.HandledState.NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected GuiControlEx.HandledState keyTyped(char keyChar, int keyCode) {
/*  890 */     return GuiControlEx.HandledState.NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidDragTarget() {
/*  902 */     return this.isDragTarget;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidDragSource() {
/*  913 */     return this.isDragSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDragTarget(IDragDrop<T> target) {
/*  924 */     addDragTarget(target, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDragTarget(IDragDrop<T> target, boolean mutual) {
/*  937 */     if (!this.isDragSource)
/*      */     {
/*  939 */       throw new InvalidParameterException("This listbox does not support drag/drop source functions");
/*      */     }
/*      */     
/*  942 */     if (target != null && target.isValidDragTarget()) {
/*      */       
/*  944 */       if (!this.dragTargets.contains(target))
/*      */       {
/*  946 */         this.dragTargets.add(target);
/*      */       }
/*      */       
/*  949 */       if (mutual && this.isDragTarget && target.isValidDragSource())
/*      */       {
/*  951 */         target.addDragTarget(this, false);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  956 */       throw new InvalidParameterException("Target listbox is not a valid drag target");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeDragTarget(IDragDrop<T> target) {
/*  968 */     removeDragTarget(target, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeDragTarget(IDragDrop<T> target, boolean mutual) {
/*  981 */     if (!this.isDragSource)
/*      */     {
/*  983 */       throw new InvalidParameterException("This listbox does not support drag/drop source functions");
/*      */     }
/*      */     
/*  986 */     if (this.dragTargets.contains(target))
/*      */     {
/*  988 */       this.dragTargets.remove(target);
/*      */     }
/*      */     
/*  991 */     if (mutual && this.isDragTarget)
/*      */     {
/*  993 */       target.removeDragTarget(this, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean dragDrop(IDragDrop<T> source, IListEntry<T> object, int mouseX, int mouseY) {
/* 1009 */     if (this.isDragTarget && this.l && mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g) {
/*      */ 
/*      */       
/* 1012 */       int targetIndex = this.scrollBar.getValue() + (mouseY - this.i + this.rowSize / 2) / this.rowSize;
/*      */       
/* 1014 */       if (source == this) {
/*      */         
/* 1016 */         int sourceIndex = this.items.indexOf(object);
/*      */         
/* 1018 */         if (targetIndex == sourceIndex)
/*      */         {
/* 1020 */           return true;
/*      */         }
/*      */         
/* 1023 */         removeItemAt(sourceIndex);
/*      */       } 
/*      */       
/* 1026 */       addItemAt(object, targetIndex);
/* 1027 */       save();
/* 1028 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1032 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 1045 */     if (!this.m) {
/*      */       return;
/*      */     }
/* 1048 */     a(this.h, this.i, this.h + this.f, this.i + this.g, this.backColour);
/*      */ 
/*      */     
/* 1051 */     this.scrollBar.setEnabled(this.l);
/*      */ 
/*      */     
/* 1054 */     this.scrollBar.updateCounter = this.updateCounter;
/* 1055 */     this.scrollBar.a(minecraft, mouseX, mouseY, partialTicks);
/*      */     
/* 1057 */     int itemTextColour = this.l ? -1 : 1143087650;
/*      */     
/* 1059 */     drawItems(mouseX, mouseY, itemTextColour);
/*      */ 
/*      */     
/* 1062 */     if (!this.mouseDelta.booleanValue() && (mouseX != this.mouseDownLocation.x || mouseY != this.mouseDownLocation.y))
/*      */     {
/* 1064 */       this.mouseDelta = Boolean.valueOf(true);
/*      */     }
/*      */ 
/*      */     
/* 1068 */     if (this.dragItem != null && this.mouseDelta.booleanValue()) {
/*      */       
/* 1070 */       a(mouseX + this.dragOffset.x + 1, mouseY + this.dragOffset.y, mouseX + this.dragOffset.x + this.f - 21, mouseY + this.dragOffset.y + this.rowSize, 1610612736);
/*      */       
/* 1072 */       renderItem((IListEntry<T>)this.dragItem, minecraft, mouseX, mouseY, mouseX + this.dragOffset.x, mouseY + this.dragOffset.y, this.f - 20, this.rowSize, itemTextColour);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawItems(int mouseX, int mouseY, int itemTextColour) {
/* 1086 */     int startRow = this.scrollBar.getValue();
/* 1087 */     int itemWidth = (this.itemsPerRow > 1) ? this.rowSize : (this.f - 20);
/*      */ 
/*      */     
/* 1090 */     int itemNumber = startRow * this.itemsPerRow;
/* 1091 */     for (; itemNumber < startRow * this.itemsPerRow + this.displayRowCount * this.itemsPerRow && itemNumber < this.items.size(); 
/* 1092 */       itemNumber++) {
/*      */       
/* 1094 */       int rowNumber = itemNumber / this.itemsPerRow;
/* 1095 */       int colNumber = itemNumber % this.itemsPerRow;
/*      */       
/* 1097 */       IListEntry<T> item = this.items.get(itemNumber);
/*      */       
/* 1099 */       int itemY = this.i + this.rowSize * (rowNumber - startRow);
/* 1100 */       int itemX = this.h + colNumber * itemWidth;
/*      */ 
/*      */       
/* 1103 */       if (itemNumber == this.selectedItem)
/*      */       {
/* 1105 */         a(itemX, itemY, itemX + itemWidth, itemY + this.rowSize, 1895825407);
/*      */       }
/*      */ 
/*      */       
/* 1109 */       if (itemNumber == this.editInPlaceItem) {
/*      */         
/* 1111 */         if (item instanceof IEditInPlace) {
/*      */           
/* 1113 */           IEditInPlace<T> editableItem = (IEditInPlace<T>)item;
/*      */           
/* 1115 */           if (editableItem.isEditingInPlace())
/*      */           {
/* 1117 */             editableItem.editInPlaceDraw(this.iconsEnabled, this.mc, mouseX, mouseY, itemX, itemY, itemWidth, this.rowSize, this.updateCounter);
/*      */           
/*      */           }
/*      */           else
/*      */           {
/* 1122 */             endEditInPlace();
/* 1123 */             renderItem(item, this.mc, mouseX, mouseY, itemX, itemY, itemWidth, this.rowSize, itemTextColour);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1128 */           renderItem(item, this.mc, mouseX, mouseY, itemX, itemY, itemWidth, this.rowSize, itemTextColour);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1133 */         renderItem(item, this.mc, mouseX, mouseY, itemX, itemY, itemWidth, this.rowSize, itemTextColour);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderItem(IListEntry<T> item, bib minecraft, int mouseX, int mouseY, int itemX, int itemY, int itemWidth, int itemHeight, int itemTextColour) {
/* 1154 */     if (item instanceof IRenderedListEntry) {
/*      */       
/* 1156 */       ((IRenderedListEntry)item).render(this.iconsEnabled, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*      */       
/*      */       return;
/*      */     } 
/* 1160 */     if (this.iconsEnabled)
/*      */     {
/* 1162 */       if (!item.renderIcon(minecraft, itemX + this.iconOffset, itemY + this.iconOffset) && item.hasIcon() && item.getIcon() != null) {
/*      */         
/* 1164 */         a(itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16, 1090519039);
/* 1165 */         GuiControl.sharedRenderer.drawIcon(item.getIconTexture(), item.getIcon(), itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1171 */     this.renderer.drawStringWithEllipsis("" + item.getDisplayName(), itemX + this.iconSpacing + 4, itemY + this.textOffset, this.f - 32 - this.iconSpacing, itemTextColour);
/*      */ 
/*      */     
/* 1174 */     if (item instanceof IDecoratedListEntry)
/*      */     {
/*      */       
/* 1177 */       ((IDecoratedListEntry)item).decorate(this.iconsEnabled, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void a(bib minecraft, int mouseX, int mouseY) {
/* 1192 */     this.scrollBar.a(minecraft, mouseX, mouseY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(int mouseX, int mouseY) {
/* 1204 */     if (this.dragItem != null) {
/*      */       
/* 1206 */       if (this.mouseDelta.booleanValue())
/*      */       {
/* 1208 */         for (int targetId = 0; targetId < this.dragTargets.size(); targetId++) {
/*      */           
/* 1210 */           IDragDrop<T> target = this.dragTargets.get(targetId);
/*      */           
/* 1212 */           if (target.dragDrop(this, (IListEntry)this.dragItem, mouseX, mouseY)) {
/*      */             
/* 1214 */             if (target != this) {
/*      */               
/* 1216 */               removeItem((IListEntry<T>)this.dragItem);
/* 1217 */               save();
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1225 */       this.dragItem = null;
/*      */     } 
/*      */     
/* 1228 */     if (this.mouseDownObject != null) {
/*      */       
/* 1230 */       this.mouseDownObject.mouseReleased(mouseX, mouseY);
/* 1231 */       this.mouseDownObject = null;
/*      */     } 
/*      */     
/* 1234 */     this.scrollBar.a(mouseX, mouseY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getItemIndexAt(int mouseX, int mouseY) {
/* 1246 */     int row = this.scrollBar.getValue() + (mouseY - this.i) / this.rowSize;
/*      */     
/* 1248 */     if (this.itemsPerRow > 1) {
/*      */       
/* 1250 */       if (!isMouseOver(null, mouseX, mouseY)) return -1;
/*      */       
/* 1252 */       int itemWidth = (this.itemsPerRow > 1) ? this.rowSize : (this.f - 20);
/* 1253 */       int col = (mouseX - this.h) / itemWidth;
/*      */       
/* 1255 */       if (col < this.itemsPerRow)
/*      */       {
/* 1257 */         return col + row * this.itemsPerRow;
/*      */       }
/*      */       
/* 1260 */       return -1;
/*      */     } 
/*      */     
/* 1263 */     return row;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> getItemAtPosition(int mouseX, int mouseY) {
/* 1274 */     int index = getItemIndexAt(mouseX, mouseY);
/*      */     
/* 1276 */     if (index > -1 && index < this.items.size())
/*      */     {
/* 1278 */       return this.items.get(index);
/*      */     }
/*      */     
/* 1281 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 1295 */     this.actionPerformed = false;
/* 1296 */     this.doubleClicked = false;
/*      */     
/* 1298 */     IEditInPlace<T> editInPlaceObject = getEditingInPlaceObject();
/*      */     
/* 1300 */     if (editInPlaceObject != null) {
/*      */       
/* 1302 */       Rectangle rect = getItemBoundingBox(this.editInPlaceItem);
/* 1303 */       if (editInPlaceObject.editInPlaceMousePressed(this.iconsEnabled, minecraft, mouseX, mouseY, rect.x, rect.y, rect.width, rect.height)) {
/*      */         
/* 1305 */         this.mouseDownObject = (IInteractiveListEntry<T>)editInPlaceObject;
/* 1306 */         this.actionPerformed = false;
/* 1307 */         this.doubleClicked = false;
/* 1308 */         return true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1313 */     if (this.scrollBar.b(minecraft, mouseX, mouseY)) return true;
/*      */     
/* 1315 */     if (super.b(minecraft, mouseX, mouseY)) {
/*      */ 
/*      */       
/* 1318 */       if (mouseX < this.h + this.f - 20) {
/*      */ 
/*      */         
/* 1321 */         int newSelectedItem = getItemIndexAt(mouseX, mouseY);
/*      */ 
/*      */         
/* 1324 */         if (newSelectedItem > -1 && newSelectedItem < this.items.size()) {
/*      */ 
/*      */           
/* 1327 */           IListEntry<T> item = this.items.get(newSelectedItem);
/* 1328 */           Rectangle rect = getItemBoundingBox(newSelectedItem);
/*      */ 
/*      */           
/* 1331 */           if (item instanceof IInteractiveListEntry) {
/*      */             
/* 1333 */             this.mouseDownObject = (IInteractiveListEntry<T>)item;
/* 1334 */             this.actionPerformed = this.mouseDownObject.mousePressed(this.iconsEnabled, mouseX, mouseY, rect);
/*      */           } 
/*      */           
/* 1337 */           if (newSelectedItem != this.selectedItem) {
/*      */             
/* 1339 */             this.actionPerformed = true;
/* 1340 */             this.selectedItem = newSelectedItem;
/* 1341 */             updateScrollPosition();
/* 1342 */             this.timer = this.updateCounter;
/*      */           }
/*      */           else {
/*      */             
/* 1346 */             if (this.updateCounter - this.timer < 9) {
/*      */               
/* 1348 */               this.actionPerformed = true;
/* 1349 */               this.doubleClicked = true;
/*      */             } 
/*      */             
/* 1352 */             this.timer = this.updateCounter;
/*      */           } 
/*      */ 
/*      */           
/* 1356 */           if (!this.doubleClicked && this.isDragSource && this.dragTargets.size() > 0 && item instanceof IDraggable) {
/*      */             
/* 1358 */             this.dragItem = (IDraggable<T>)item;
/* 1359 */             this.dragOffset = new Point(rect.x - mouseX, rect.y - mouseY);
/* 1360 */             this.mouseDownLocation.x = mouseX;
/* 1361 */             this.mouseDownLocation.y = mouseY;
/* 1362 */             this.mouseDelta = Boolean.valueOf(false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1367 */       return true;
/*      */     } 
/*      */     
/* 1370 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> createObject(String text) {
/* 1389 */     return createObject(text, -1, (nf)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> createObject(String text, int iconId, nf iconTexture) {
/* 1402 */     return (IListEntry<T>)new ListEntry(this.newItemIndex++, text, null, (this.iconsEnabled && iconId > -1), iconTexture, iconId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> createObject(String text, aip displayItem) {
/* 1411 */     return (IListEntry<T>)new ListEntry(this.newItemIndex++, text, null, displayItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> createObject(String text, aip displayItem, T data) {
/* 1419 */     return (IListEntry<T>)new ListEntry(this.newItemIndex++, text, data, displayItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> createObject(String text, int iconId, nf iconTexture, T data) {
/* 1432 */     return (IListEntry<T>)new ListEntry(this.newItemIndex++, text, data, (this.iconsEnabled && iconId > -1), iconTexture, iconId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> createObject(String text, int iconId) {
/* 1444 */     return createObject(text, iconId, "/gui/items.png");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListEntry<T> createObject(String text, int iconId, Object data) {
/* 1456 */     return createObject(text, iconId);
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiListBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */