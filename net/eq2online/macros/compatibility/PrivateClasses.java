/*    */ package net.eq2online.macros.compatibility;
/*    */ 
/*    */ import agr;
/*    */ import com.mumfrey.liteloader.core.runtime.Obf;
/*    */ import com.mumfrey.liteloader.util.ObfuscationUtilities;
/*    */ import net.eq2online.obfuscation.ObfTbl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PrivateClasses<C>
/*    */ {
/*    */   public final Class<? extends C> Class;
/*    */   private final String className;
/*    */   
/*    */   private PrivateClasses(Obf mapping) {
/* 32 */     this.className = ObfuscationUtilities.getObfuscatedFieldName(mapping);
/*    */     
/* 34 */     Class<? extends C> reflectedClass = null;
/*    */ 
/*    */     
/*    */     try {
/* 38 */       reflectedClass = (Class)Class.forName(this.className);
/*    */     } catch (Exception ex) {
/* 40 */       ex.printStackTrace();
/*    */     } 
/* 42 */     this.Class = reflectedClass;
/*    */   }
/*    */   
/* 45 */   public static final PrivateClasses<agr> SlotCreativeInventory = new PrivateClasses((Obf)ObfTbl.SlotCreativeInventory);
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\compatibility\PrivateClasses.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */