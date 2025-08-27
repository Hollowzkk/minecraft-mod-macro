/*    */ package net.eq2online.macros.gui.controls.specialised;
/*    */ 
/*    */ import aip;
/*    */ import air;
/*    */ import bib;
/*    */ import net.eq2online.macros.core.CustomResourcePack;
/*    */ import net.eq2online.macros.gui.controls.GuiListBox;
/*    */ import net.eq2online.macros.gui.list.ListEntry;
/*    */ import nf;
/*    */ 
/*    */ 
/*    */ public class GuiListBoxResource
/*    */   extends GuiListBox<String>
/*    */ {
/*    */   private final CustomResourcePack resources;
/*    */   
/*    */   public GuiListBoxResource(bib minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, CustomResourcePack resources) {
/* 18 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, 16, true, false, false);
/* 19 */     this.resources = resources;
/*    */   }
/*    */ 
/*    */   
/*    */   public void refresh() {
/* 24 */     this.items.clear();
/*    */     
/* 26 */     int id = 0;
/* 27 */     aip icon = new aip(air.aS);
/* 28 */     for (nf resource : this.resources.getResourceList()) {
/*    */       
/* 30 */       String path = this.resources.getResourcePath(resource);
/* 31 */       this.items.add(new ListEntry(id++, path, path, icon));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */