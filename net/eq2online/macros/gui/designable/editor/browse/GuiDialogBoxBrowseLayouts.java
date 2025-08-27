/*    */ package net.eq2online.macros.gui.designable.editor.browse;
/*    */ 
/*    */ import aip;
/*    */ import air;
/*    */ import bib;
/*    */ import blk;
/*    */ import net.eq2online.macros.gui.GuiDialogBoxBrowse;
/*    */ import net.eq2online.macros.gui.designable.LayoutManager;
/*    */ import net.eq2online.macros.gui.list.ListEntry;
/*    */ import net.eq2online.macros.interfaces.IListEntry;
/*    */ 
/*    */ public class GuiDialogBoxBrowseLayouts
/*    */   extends GuiDialogBoxBrowse<String> {
/*    */   public GuiDialogBoxBrowseLayouts(bib minecraft, blk parentScreen, String windowTitle, LayoutManager layouts, String selected) {
/* 15 */     super(minecraft, parentScreen, 210, 174, windowTitle);
/*    */     
/* 17 */     int id = 0;
/* 18 */     aip icon = new aip(air.aS);
/* 19 */     for (String layout : layouts.getLayoutNames())
/*    */     {
/* 21 */       this.listBox.addItem((IListEntry)new ListEntry(id++, layout, layout, icon));
/*    */     }
/*    */     
/* 24 */     this.listBox.selectData(selected);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\browse\GuiDialogBoxBrowseLayouts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */