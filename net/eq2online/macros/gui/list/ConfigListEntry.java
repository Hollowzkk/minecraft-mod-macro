/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import bib;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.settings.Settings;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ 
/*    */ public class ConfigListEntry
/*    */   extends EditableListEntry<String>
/*    */ {
/* 16 */   private final Icon defaultIcon = (Icon)new IconTiled(null, 0, 16, 96, 64, 128);
/*    */   
/*    */   private final Settings settings;
/*    */ 
/*    */   
/*    */   public ConfigListEntry(Macros macros, int id, String config) {
/* 22 */     super(id, (id < 0) ? ("§e" + I18n.get("options.defaultconfig")) : config, config, ResourceLocations.EXT, (Icon)null);
/*    */ 
/*    */     
/* 25 */     this.settings = macros.getSettings();
/* 26 */     this.allowEdit = false;
/* 27 */     this.allowDelete = true;
/*    */     
/* 29 */     int iconU = (id < 0) ? 80 : 64;
/* 30 */     this.icon = (Icon)new IconTiled(null, id, 16, iconU, 64, 128);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean renderIcon(bib minecraft, int xPosition, int yPosition) {
/* 36 */     xPosition += 2;
/*    */     
/* 38 */     minecraft.N().a(this.iconTexture);
/*    */     
/* 40 */     GL.glDisableBlend();
/* 41 */     renderIcon(xPosition, yPosition, this.icon);
/*    */     
/* 43 */     if (this.data != null && this.data.equals(this.settings.initialConfiguration)) {
/*    */       
/* 45 */       GL.glEnableBlend();
/* 46 */       renderIcon(xPosition, yPosition, this.defaultIcon);
/*    */     } 
/*    */     
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderIcon(int xPosition, int yPosition, Icon icon) {
/* 54 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 55 */     ListEntry.renderer.drawTexturedModalRectF(xPosition, yPosition, xPosition + 16, yPosition + 16, icon.getMinU(), icon.getMinV(), icon
/* 56 */         .getMaxU(), icon.getMaxV());
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\list\ConfigListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */