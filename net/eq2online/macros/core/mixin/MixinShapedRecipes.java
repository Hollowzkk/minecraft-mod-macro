/*    */ package net.eq2online.macros.core.mixin;
/*    */ 
/*    */ import akq;
/*    */ import akw;
/*    */ import fi;
/*    */ import net.eq2online.macros.core.overlays.IVanillaRecipe;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({akw.class})
/*    */ public abstract class MixinShapedRecipes
/*    */   implements IVanillaRecipe
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private int a;
/*    */   
/*    */   public int getWidth() {
/* 22 */     return this.a;
/*    */   } @Shadow
/*    */   @Final
/*    */   private int b; @Shadow
/*    */   @Final
/*    */   private fi<akq> c; public int getHeight() {
/* 28 */     return this.b;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public fi<akq> getItems() {
/* 34 */     return this.c;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean requiresCraftingTable() {
/* 40 */     return (this.a > 2 || this.b > 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\MixinShapedRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */