/*    */ package net.eq2online.macros.gui.designable.editor.browse;
/*    */ 
/*    */ import aip;
/*    */ import air;
/*    */ import bib;
/*    */ import blk;
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ import net.eq2online.macros.gui.GuiDialogBoxBrowse;
/*    */ import net.eq2online.macros.gui.list.ListEntry;
/*    */ import net.eq2online.macros.interfaces.IListEntry;
/*    */ 
/*    */ public class GuiDialogBoxBrowseImportable
/*    */   extends GuiDialogBoxBrowse<File>
/*    */   implements FilenameFilter {
/*    */   public GuiDialogBoxBrowseImportable(bib minecraft, blk parentScreen, String windowTitle, File rootDir) {
/* 17 */     super(minecraft, parentScreen, 210, 174, windowTitle);
/*    */     
/* 19 */     int id = 0;
/* 20 */     aip icon = new aip(air.aS);
/* 21 */     for (File file : rootDir.listFiles(this))
/*    */     {
/* 23 */       this.listBox.addItem((IListEntry)new ListEntry(id++, file.getName(), file, icon));
/*    */     }
/*    */     
/* 26 */     this.listBox.selectId(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean accept(File dir, String name) {
/* 33 */     if (name.startsWith("."))
/*    */     {
/* 35 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 39 */     if (name.matches("^[A-Za-z0-9\\x20_\\-\\.]+\\.xml$"))
/*    */     {
/* 41 */       return true;
/*    */     }
/*    */     
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\browse\GuiDialogBoxBrowseImportable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */