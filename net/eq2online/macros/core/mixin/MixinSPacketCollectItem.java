/*    */ package net.eq2online.macros.core.mixin;
/*    */ 
/*    */ import ks;
/*    */ import net.eq2online.macros.core.overlays.IPacketCollectItem;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ 
/*    */ @Mixin({ks.class})
/*    */ public abstract class MixinSPacketCollectItem
/*    */   implements IPacketCollectItem
/*    */ {
/*    */   private boolean hasQuantity = false;
/* 12 */   private int quantity = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasQuantity() {
/* 17 */     return this.hasQuantity;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setQuantity(int quantity) {
/* 23 */     this.hasQuantity = true;
/* 24 */     this.quantity = quantity;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQuantity() {
/* 30 */     return this.quantity;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\MixinSPacketCollectItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */