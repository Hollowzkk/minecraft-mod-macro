/*    */ package net.eq2online.macros.core.mixin;
/*    */ 
/*    */ import afw;
/*    */ import agr;
/*    */ import ahp;
/*    */ import bmp;
/*    */ import org.apache.commons.lang3.NotImplementedException;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ import org.spongepowered.asm.mixin.gen.Invoker;
/*    */ import uk;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({bmp.class})
/*    */ public interface IGuiContainerCreative
/*    */ {
/*    */   @Accessor("destroyItemSlot")
/*    */   agr getBinSlot();
/*    */   
/*    */   @Invoker("setCurrentCreativeTab")
/*    */   void setCreativeTab(ahp paramahp);
/*    */   
/*    */   @Invoker("handleMouseClick")
/*    */   void mouseClick(agr paramagr, int paramInt1, int paramInt2, afw paramafw);
/*    */   
/*    */   @Accessor("currentScroll")
/*    */   void setScrollPosition(float paramFloat);
/*    */   
/*    */   @Accessor("basicInventory")
/*    */   static uk getCreativeInventory() {
/* 32 */     throw new NotImplementedException("IGuiContainerCreative mixin not applied");
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IGuiContainerCreative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */