/*    */ package net.eq2online.macros.gui.controls.specialised;
/*    */ 
/*    */ import bib;
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.gui.controls.GuiListBox;
/*    */ import net.eq2online.macros.gui.list.ListEntry;
/*    */ import net.eq2online.macros.interfaces.IRefreshable;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ public class GuiListBoxShaders extends GuiListBox<String> implements IRefreshable {
/* 12 */   private static final String[] SHADERS = new String[] { "notch", "fxaa", "art", "bumpy", "blobs2", "pencil", "color_convolve", "deconverge", "flip", "invert", "ntsc", "outline", "phosphor", "scan_pincushion", "sobel", "bits", "desaturate", "green", "blur", "wobble", "blobs", "antialias", "creeper", "spider" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiListBoxShaders(bib minecraft, int controlId, boolean showIcons) {
/* 19 */     super(minecraft, controlId, showIcons, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void refresh() {
/* 28 */     this.items.clear();
/* 29 */     this.items.add(new ListEntry(0, "No shader", "", (Icon)new IconTiled(ResourceLocations.SHADERS, 0, 16, 128)));
/*    */     
/* 31 */     int id = 1;
/* 32 */     for (String shader : SHADERS)
/*    */     {
/* 34 */       this.items.add(new ListEntry(id, shader, shader, (Icon)new IconTiled(ResourceLocations.SHADERS, id++, 16, 128)));
/*    */     }
/*    */     
/* 37 */     this.scrollBar.setMax(Math.max(0, this.items.size() - this.displayRowCount));
/* 38 */     updateScrollPosition();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */