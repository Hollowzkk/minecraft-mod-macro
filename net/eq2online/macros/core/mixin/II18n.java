/*    */ package net.eq2online.macros.core.mixin;
/*    */ 
/*    */ import cey;
/*    */ import cfb;
/*    */ import org.apache.commons.lang3.NotImplementedException;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({cey.class})
/*    */ public interface II18n
/*    */ {
/*    */   @Accessor("i18nLocale")
/*    */   static cfb getCurrentLocale() {
/* 16 */     throw new NotImplementedException("II18n mixin failed");
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\II18n.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */