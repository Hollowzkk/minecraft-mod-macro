/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ public class GuiLayoutListEntry
/*    */   extends EditableListEntry<DesignableGuiLayout> {
/*    */   private DesignableGuiLayout layout;
/*    */   
/*    */   public GuiLayoutListEntry(int id, DesignableGuiLayout layout) {
/* 13 */     super(id, (layout.getName().equals(layout.getDisplayName()) ? "" : "§e") + layout.getDisplayName(), layout, true, 
/* 14 */         !layout.getManager().isBuiltinLayout(layout.getName()));
/* 15 */     this.layout = layout;
/* 16 */     this.iconTexture = ResourceLocations.EXT;
/* 17 */     this.icon = (Icon)new IconTiled(this.iconTexture, layout.getManager().isBuiltinLayout(layout.getName()) ? 32 : 33, 32);
/* 18 */     this.hasIcon = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public DesignableGuiLayout getLayout() {
/* 23 */     return this.layout;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLayoutName() {
/* 28 */     return this.layout.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLayoutDisplayName() {
/* 33 */     return this.layout.getDisplayName();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\list\GuiLayoutListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */