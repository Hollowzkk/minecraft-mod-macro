/*     */ package net.eq2online.macros.compatibility;
/*     */ 
/*     */ import bhy;
/*     */ import com.mumfrey.liteloader.core.runtime.Obf;
/*     */ import com.mumfrey.liteloader.util.ObfuscationUtilities;
/*     */ import net.eq2online.obfuscation.ObfTbl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrivateFields<P, T>
/*     */ {
/*     */   public final Class<P> parentClass;
/*     */   private final String fieldName;
/*     */   
/*     */   private PrivateFields(Class<P> owner, Obf mapping) {
/*  39 */     this.parentClass = owner;
/*  40 */     this.fieldName = ObfuscationUtilities.getObfuscatedFieldName(mapping);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(P instance) {
/*     */     try {
/*  54 */       return Reflection.getPrivateValue(this.parentClass, instance, this.fieldName);
/*     */     }
/*  56 */     catch (Exception ex) {
/*     */       
/*  58 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T set(P instance, T value) {
/*     */     try {
/*  73 */       Reflection.setPrivateValue(this.parentClass, instance, this.fieldName, value);
/*     */     }
/*  75 */     catch (Exception exception) {}
/*     */     
/*  77 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class StaticFields<P, T>
/*     */     extends PrivateFields<P, T>
/*     */   {
/*     */     public StaticFields(Class<P> owner, ObfTbl mapping) {
/*  91 */       super(owner, (Obf)mapping);
/*     */     }
/*     */ 
/*     */     
/*     */     public T get() {
/*  96 */       return get(null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(T value) {
/* 101 */       set(null, value);
/*     */     }
/*     */     
/* 104 */     public static final StaticFields<bhy, Object> KEYBIND_HASH = new StaticFields((Class)bhy.class, ObfTbl.keyBindHash);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\compatibility\PrivateFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */