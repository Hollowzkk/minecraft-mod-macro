/*    */ package net.eq2online.macros.gui.controls.specialised;
/*    */ 
/*    */ import bib;
/*    */ import ceu;
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.gui.controls.GuiListBox;
/*    */ import net.eq2online.macros.gui.list.ResourcePackListEntry;
/*    */ import net.eq2online.macros.interfaces.IListEntry;
/*    */ import net.eq2online.macros.interfaces.IRefreshable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiListBoxResourcePack
/*    */   extends GuiListBox<ceu.a>
/*    */   implements IRefreshable
/*    */ {
/*    */   public GuiListBoxResourcePack(bib minecraft, int controlId, boolean showIcons) {
/* 31 */     super(minecraft, controlId, showIcons, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void refresh() {
/* 40 */     ceu resourcePackRepository = this.mc.P();
/* 41 */     resourcePackRepository.b();
/*    */     
/* 43 */     List<ceu.a> availableResourcePacks = resourcePackRepository.d();
/* 44 */     this.items.clear();
/*    */     
/* 46 */     this.items.add(new ResourcePackListEntry(this.mc, 0, "Default", null));
/*    */     
/* 48 */     String selectedPackName = (this.mc.t.m.size() > 0) ? this.mc.t.m.get(0) : "";
/*    */     
/* 50 */     int resourcePackId = 1;
/* 51 */     for (ceu.a tp : availableResourcePacks) {
/*    */       
/* 53 */       this.items.add(new ResourcePackListEntry(this.mc, resourcePackId, tp.d(), tp));
/*    */       
/* 55 */       if (selectedPackName.equals(tp.d()))
/*    */       {
/* 57 */         selectId(resourcePackId);
/*    */       }
/*    */       
/* 60 */       resourcePackId++;
/*    */     } 
/*    */     
/* 63 */     this.scrollBar.setMax(Math.max(0, this.items.size() - this.displayRowCount));
/* 64 */     updateScrollPosition();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IListEntry<ceu.a> removeSelectedItem() {
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */