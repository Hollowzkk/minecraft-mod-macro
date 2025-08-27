/*    */ package net.eq2online.macros.gui.overlay;
/*    */ 
/*    */ import agr;
/*    */ import bib;
/*    */ import bmg;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.helpers.SlotHelper;
/*    */ 
/*    */ 
/*    */ public class SlotIdOverlay
/*    */   extends Overlay
/*    */ {
/*    */   private final SlotHelper slotHelper;
/*    */   
/*    */   public SlotIdOverlay(Macros macros, bib minecraft, OverlayHandler handler) {
/* 17 */     super(macros, minecraft, handler);
/* 18 */     this.slotHelper = new SlotHelper(macros, minecraft);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAlwaysRendered() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(int mouseX, int mouseY, long tick, float partialTick, boolean clock) {
/* 30 */     this.mc.B.c("slotid");
/*    */ 
/*    */     
/* 33 */     if (this.settings.showSlotInfo && this.mc.m != null && this.slotHelper.currentScreenIsContainer())
/*    */     {
/* 35 */       drawSlotInfo(mouseX, mouseY, partialTick);
/*    */     }
/*    */   }
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
/*    */   protected void drawSlotInfo(int mouseX, int mouseY, float partialTick) {
/* 49 */     bmg currentScreen = this.slotHelper.getGuiContainer();
/*    */     
/* 51 */     if (currentScreen != null) {
/*    */       
/* 53 */       GL.glDisableDepthTest();
/* 54 */       GL.glDisableBlend();
/* 55 */       GL.glDisableLighting();
/* 56 */       GL.glEnableTexture2D();
/*    */ 
/*    */       
/*    */       try {
/* 60 */         agr mouseOverSlot = this.slotHelper.getMouseOverSlot(currentScreen, mouseX, mouseY);
/* 61 */         int slotIndex = SlotHelper.getSlotIndex(currentScreen, mouseOverSlot);
/*    */         
/* 63 */         if (slotIndex > -1)
/*    */         {
/* 65 */           int yOffset = (this.mc.h.bv.q().b() && mouseOverSlot.e()) ? 18 : 0;
/* 66 */           drawFunkyTooltip("Slot " + slotIndex, mouseX, mouseY - yOffset, -256, -266338304, 1358888960);
/*    */         }
/*    */       
/* 69 */       } catch (Exception exception) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\SlotIdOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */