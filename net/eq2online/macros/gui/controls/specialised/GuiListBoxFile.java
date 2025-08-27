/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import ain;
/*     */ import aip;
/*     */ import air;
/*     */ import bib;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.list.EditableListEntry;
/*     */ import net.eq2online.macros.gui.list.ListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IRefreshable;
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
/*     */ public class GuiListBoxFile
/*     */   extends GuiListBox<String>
/*     */   implements FilenameFilter, IRefreshable
/*     */ {
/*     */   protected File directory;
/*     */   protected String filterExtension;
/*     */   
/*     */   public GuiListBoxFile(bib minecraft, int controlId, boolean showIcons, File directory, String filterExtension) {
/*  47 */     super(minecraft, controlId, showIcons, false, false);
/*     */     
/*  49 */     this.directory = directory;
/*  50 */     this.filterExtension = filterExtension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(File dir, String name) {
/*  57 */     if (name.startsWith("."))
/*     */     {
/*  59 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  63 */     if (name.matches("^[A-Za-z0-9\\x20_\\-\\.]+\\." + this.filterExtension + "$"))
/*     */     {
/*  65 */       return true;
/*     */     }
/*     */     
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {
/*  77 */     File[] files = this.directory.listFiles(this);
/*  78 */     this.items.clear();
/*     */     
/*  80 */     if (files != null) {
/*     */       
/*  82 */       int id = 0;
/*  83 */       for (File file : files) {
/*     */         
/*  85 */         aip icon = !Macros.isValidFileName(file.getName()) ? new aip(air.aS) : new aip((ain)air.cg);
/*  86 */         this.items.add(new EditableListEntry(id++, icon, file.getName(), file.getName()));
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     this.items.add(new ListEntry(-1, I18n.get("list.new.file"), ""));
/*     */     
/*  92 */     this.scrollBar.setMax(Math.max(0, this.items.size() - this.displayRowCount));
/*  93 */     updateScrollPosition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderItem(IListEntry<String> item, bib minecraft, int mouseX, int mouseY, int itemX, int itemY, int itemWidth, int itemHeight, int itemTextColour) {
/* 103 */     if (this.l && !Macros.isValidFileName(item.getText()))
/*     */     {
/* 105 */       itemTextColour = -6710887;
/*     */     }
/*     */     
/* 108 */     super.renderItem(item, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, itemTextColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListEntry<String> removeSelectedItem() {
/* 119 */     if (this.items.size() > this.selectedItem) {
/*     */       
/* 121 */       IListEntry<String> selected = getSelectedItem();
/*     */ 
/*     */       
/* 124 */       if (selected != null && !Macros.isValidFileName((String)selected.getData()))
/*     */       {
/* 126 */         return null;
/*     */       }
/*     */       
/* 129 */       IListEntry<String> removed = this.items.remove(this.selectedItem);
/* 130 */       File deleteFile = new File(this.directory, (String)removed.getData());
/*     */ 
/*     */       
/*     */       try {
/* 134 */         if (deleteFile.exists()) deleteFile.delete();
/*     */       
/* 136 */       } catch (Exception ex) {
/*     */         
/* 138 */         Log.printStackTrace(ex);
/*     */       } 
/*     */       
/* 141 */       refresh();
/* 142 */       updateScrollPosition();
/* 143 */       return removed;
/*     */     } 
/*     */     
/* 146 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */