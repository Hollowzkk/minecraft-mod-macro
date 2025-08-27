/*    */ package net.eq2online.macros.gui.layout;
/*    */ 
/*    */ import bib;
/*    */ import bip;
/*    */ import bir;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import java.awt.Rectangle;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.event.MacroEventManager;
/*    */ import net.eq2online.macros.interfaces.IEditablePanel;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LayoutButtonEvent
/*    */   extends LayoutButton
/*    */ {
/*    */   protected MacroEventManager eventManager;
/*    */   
/*    */   public LayoutButtonEvent(Macros macros, bib minecraft, bip fontRenderer, int id, String name, int width, int height, boolean centre) {
/* 25 */     super(macros, minecraft, fontRenderer, id, name, width, height, 20, centre, false);
/* 26 */     this.eventManager = this.macros.getEventManager();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(LayoutPanelStandard parent, Rectangle boundingBox, int mouseX, int mouseY, IEditablePanel.EditMode mode, boolean selected, boolean denied) {
/* 36 */     denied = isDenied();
/*    */     
/* 38 */     super.draw(parent, boundingBox, mouseX, mouseY, mode, selected, denied);
/*    */     
/* 40 */     GL.glEnableTexture2D();
/* 41 */     GL.glDisableLighting();
/* 42 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 44 */     int xPos = parent.getXPosition() + this.drawX;
/* 45 */     int yPos = parent.getYPosition() + this.yPosition;
/*    */     
/* 47 */     IMacroEvent event = this.eventManager.getEvent(this.id);
/* 48 */     if (event != null) {
/*    */       
/* 50 */       Icon icon = event.getIcon();
/* 51 */       if (icon instanceof IconTiled) ((IconTiled)icon).bind(this.mc); 
/* 52 */       drawIcon(icon, xPos + 4, yPos + 2, 12, 12);
/*    */     } 
/*    */     
/* 55 */     if (denied) {
/*    */       
/* 57 */       this.mc.N().a(ResourceLocations.MAIN);
/* 58 */       drawTexturedModalRect(xPos + 4, yPos + 2, xPos + 16, yPos + 14, 184, 0, 208, 24);
/*    */       
/* 60 */       drawTriangle(0, xPos + 1, yPos + 1, xPos + this.width - 1, yPos + this.height - 1, 1627324416);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDenied() {
/* 70 */     return !this.eventManager.checkPermission(this.id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDeniedText() {
/* 79 */     return "Permission denied by server";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\LayoutButtonEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */