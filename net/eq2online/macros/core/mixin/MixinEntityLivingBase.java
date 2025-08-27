/*    */ package net.eq2online.macros.core.mixin;
/*    */ 
/*    */ import ht;
/*    */ import net.eq2online.macros.core.overlays.IPacketCollectItem;
/*    */ import ol;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import vg;
/*    */ import vp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({vp.class})
/*    */ public abstract class MixinEntityLivingBase
/*    */ {
/*    */   @Redirect(method = {"onItemPickup"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityTracker;sendToTracking(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/Packet;)V"))
/*    */   private void sendToTracking(ol entityTracker, vg entity, ht<?> packet, vg entityIn, int quantity) {
/* 22 */     if (packet instanceof IPacketCollectItem)
/*    */     {
/* 24 */       ((IPacketCollectItem)packet).setQuantity(quantity);
/*    */     }
/* 26 */     entityTracker.a(entity, packet);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\MixinEntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */