/*    */ package net.eq2online.macros.gui.controls.specialised;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.gui.controls.GuiListBoxIconic;
/*    */ import net.eq2online.macros.gui.helpers.ItemList;
/*    */ import net.eq2online.macros.interfaces.IListEntry;
/*    */ import net.eq2online.macros.interfaces.IRefreshable;
/*    */ import net.eq2online.macros.struct.ItemInfo;
/*    */ 
/*    */ public class GuiListBoxItems
/*    */   extends GuiListBoxIconic<Integer> implements IRefreshable {
/*    */   private final ItemList itemList;
/*    */   
/*    */   public GuiListBoxItems(bib minecraft, int controlId, ItemList itemList) {
/* 15 */     super(minecraft, controlId);
/* 16 */     this.itemList = itemList;
/* 17 */     refresh();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void refresh() {
/* 23 */     this.itemList.refresh();
/*    */ 
/*    */     
/* 26 */     for (ItemInfo item : this.itemList)
/*    */     {
/* 28 */       addItem((IListEntry)item);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */