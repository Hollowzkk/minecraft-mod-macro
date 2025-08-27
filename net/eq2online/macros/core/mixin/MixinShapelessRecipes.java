/*    */ package net.eq2online.macros.core.mixin;
/*    */ 
/*    */ import akq;
/*    */ import akx;
/*    */ import fi;
/*    */ import net.eq2online.macros.core.overlays.IVanillaRecipe;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ @Mixin({akx.class})
/*    */ public abstract class MixinShapelessRecipes
/*    */   implements IVanillaRecipe
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   fi<akq> b;
/*    */   
/*    */   public int getWidth() {
/* 20 */     return this.b.size();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 26 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public fi<akq> getItems() {
/* 32 */     return this.b;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean requiresCraftingTable() {
/* 38 */     return (this.b.size() > 4);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\MixinShapelessRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */