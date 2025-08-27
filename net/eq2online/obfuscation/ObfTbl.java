/*    */ package net.eq2online.obfuscation;
/*    */ 
/*    */ import com.mumfrey.liteloader.core.runtime.Obf;
/*    */ import java.util.concurrent.Callable;
/*    */ import net.minecraft.launchwrapper.Launch;
/*    */ import net.minecraft.launchwrapper.LaunchClassLoader;
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
/*    */ public final class ObfTbl
/*    */   extends Obf
/*    */ {
/*    */   private static String func_148239_a;
/*    */   
/*    */   static {
/* 24 */     v();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public static final ObfTbl ContainerCreative = new ObfTbl("net.minecraft.client.gui.inventory.GuiContainerCreative$ContainerCreative", "bmp$b");
/*    */   
/* 31 */   public static final ObfTbl KeyBinding = new ObfTbl("net.minecraft.client.settings.KeyBinding", "bhy");
/* 32 */   public static final ObfTbl keyBindHash = new ObfTbl(KeyBinding, "field_74514_b", "b");
/*    */   
/* 34 */   public static final ObfTbl scrollTo = new ObfTbl(ContainerCreative, func_148239_a, "a");
/*    */ 
/*    */   
/*    */   private static void v() {
/* 38 */     char[] u = { 't', '.', 'i', 'j', 'e' };
/* 39 */     char[] e = { u[4], 'c', u[0], 'o', 'r' };
/* 40 */     char[] f = { 'n', 'I' };
/* 41 */     char[] t = { '2', e[3] };
/* 42 */     char[] c = { e[1], 'r', e[3], 's' };
/* 43 */     char[] i = { f[1], f[0], 'p', 'u' };
/* 44 */     $$(u, e, f, t, c, i);
/*    */   }
/*    */ 
/*    */   
/*    */   private static void $$(char[] u, char[] e, char[] f, char[] t, char[] c, char[] i) {
/* 49 */     char[] q = { f[0], i[2], i[3] };
/* 50 */     char[] k = { e[0], e[4] };
/* 51 */     char[] g = { c[0], c[1], c[2], c[3], u[1], u[2], q[0], q[1], q[2], u[0], u[1], i[0], i[1], i[2], i[3] };
/* 52 */     char[] n = { f[0], 'd', 'l', k[0], k[1], f[1], f[0], u[3] };
/* 53 */     char[] j = { u[0], 'H', 'a' };
/* 54 */     char[] m = { 'm', j[2] };
/* 55 */     $(f[0] + 
/* 56 */         "et" + u[1] + "eq" + t + "nl" + u[2] + "ne" + 
/* 57 */         u[1] + m + g + j + n + e);
/* 58 */     $__$();
/*    */   }
/*    */ 
/*    */   
/*    */   private static void $__$() {
/* 63 */     Callable<String> x = new Callable<String>()
/*    */       {
/*    */         
/*    */         public String call() throws Exception
/*    */         {
/* 68 */           return "not valid";
/*    */         }
/*    */       };
/*    */ 
/*    */     
/*    */     try {
/* 74 */       func_148239_a = x.call();
/*    */     }
/* 76 */     catch (Exception ex) {
/*    */       
/* 78 */       func_148239_a = "func_148239_a";
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static void $(String v) {
/* 84 */     $a$().registerTransformer(v);
/*    */   }
/*    */ 
/*    */   
/*    */   private static LaunchClassLoader $a$() {
/* 89 */     return Launch.classLoader;
/*    */   }
/*    */ 
/*    */   
/*    */   private ObfTbl(Obf owner, String seargeName, String obfName) {
/* 94 */     super(owner, seargeName, obfName);
/*    */   }
/*    */ 
/*    */   
/*    */   private ObfTbl(String seargeName, String obfName) {
/* 99 */     super(seargeName, obfName);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\obfuscation\ObfTbl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */