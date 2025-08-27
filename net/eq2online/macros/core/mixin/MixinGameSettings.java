/*    */ package net.eq2online.macros.core.mixin;
/*    */ 
/*    */ import bid;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({bid.class})
/*    */ public abstract class MixinGameSettings
/*    */ {
/*    */   @Inject(method = {"getKeyDisplayString(I)Ljava/lang/String;"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onGetDisplayString(int key, CallbackInfoReturnable<String> ci) {
/* 17 */     if (key == -37) {
/*    */       
/* 19 */       ci.setReturnValue("Scroll Up");
/*    */     }
/* 21 */     else if (key == -36) {
/*    */       
/* 23 */       ci.setReturnValue("Scroll Down");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\MixinGameSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */