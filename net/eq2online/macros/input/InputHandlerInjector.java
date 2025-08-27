/*     */ package net.eq2online.macros.input;
/*     */ 
/*     */ import com.mumfrey.liteloader.transformers.ClassTransformer;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.FieldInsnNode;
/*     */ import org.objectweb.asm.tree.InsnNode;
/*     */ import org.objectweb.asm.tree.LdcInsnNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.objectweb.asm.tree.VarInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputHandlerInjector
/*     */   extends ClassTransformer
/*     */ {
/*  24 */   private final byte[] a = new byte[] { 110, 101, 116, 46, 101, 113, 50, 111, 110, 108, 105, 110, 101, 46, 109, 97, 99, 114, 111, 115, 46, 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 46, 77, 97, 99, 114, 111, 77, 111, 100, 80, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115 };
/*     */ 
/*     */   
/*  27 */   private final byte[] z = new byte[] { 110, 101, 116, 46, 101, 113, 50, 111, 110, 108, 105, 110, 101, 46, 111, 98, 102, 117, 115, 99, 97, 116, 105, 111, 110, 46, 79, 98, 102, 84, 98, 108, 36, 49 };
/*     */   
/*  29 */   private final byte[] v = new byte[] { 102, 117, 110, 99, 95, 49, 52, 56, 51, 50, 57, 95, 97 };
/*  30 */   private final byte[] p = new byte[] { 110, 101, 116, 47, 101, 113, 50, 111, 110, 108, 105, 110, 101, 47, 109, 97, 99, 114, 111, 115, 47, 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 47, 77, 97, 99, 114, 111, 77, 111, 100, 80, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115 };
/*     */ 
/*     */   
/*  33 */   private final byte[] m = new byte[] { 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 77, 97, 110, 97, 103, 101, 114 };
/*  34 */   private final byte[] c = new byte[] { 99, 111, 109, 47, 109, 117, 109, 102, 114, 101, 121, 47, 108, 105, 116, 101, 108, 111, 97, 100, 101, 114, 47, 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 47, 80, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 77, 97, 110, 97, 103, 101, 114, 67, 108, 105, 101, 110, 116 };
/*     */ 
/*     */   
/*  37 */   private final byte[] g = new byte[] { 103, 101, 116, 77, 111, 100, 80, 101, 114, 109, 105, 115, 115, 105, 111, 110 };
/*  38 */   private final byte[] t = new byte[] { 116, 97, 109, 112, 101, 114, 67, 104, 101, 99, 107 };
/*  39 */   private final byte[] l = new byte[] { 99, 111, 109, 47, 109, 117, 109, 102, 114, 101, 121, 47, 108, 105, 116, 101, 108, 111, 97, 100, 101, 114, 47, 80, 101, 114, 109, 105, 115, 115, 105, 98, 108, 101 };
/*     */   
/*  41 */   private final byte[] s = new byte[] { 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103 };
/*     */   
/*     */   private final String b;
/*     */   
/*     */   private final String bb;
/*     */   private static boolean j;
/*     */   
/*     */   public InputHandlerInjector() {
/*  49 */     this.b = new String(this.a);
/*  50 */     this.bb = new String(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] transform(String name, String transformedName, byte[] basicClass) {
/*  56 */     if (this.b.equals(transformedName))
/*     */     {
/*  58 */       return writeClass(b(readClass(basicClass, true)));
/*     */     }
/*  60 */     if (this.bb.equals(transformedName))
/*     */     {
/*  62 */       return writeClass(bb(readClass(basicClass, true)));
/*     */     }
/*     */     
/*  65 */     return basicClass;
/*     */   }
/*     */ 
/*     */   
/*     */   private ClassNode b(ClassNode classNode) {
/*  70 */     MethodNode method = classNode.methods.get(6);
/*  71 */     String z = "o";
/*  72 */     method.instructions.clear();
/*  73 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(178, new String(this.p), new String(this.m), "L" + new String(this.c) + ";"));
/*  74 */     int k = 5;
/*  75 */     method.instructions.add((AbstractInsnNode)new MethodInsnNode(182, new String(this.c), new String(this.t), "()V", false));
/*  76 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(178, new String(this.p), new String(this.m), "L" + new String(this.c) + ";"));
/*  77 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(178, new String(this.p), "m" + z + "d", "L" + new String(this.l) + ";"));
/*  78 */     boolean d = (1 + k > 1);
/*  79 */     j = d;
/*  80 */     method.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*  81 */     method.instructions.add((AbstractInsnNode)new MethodInsnNode(182, new String(this.c), new String(this.g), "(L" + new String(this.l) + ";L" + new String(this.s) + ";)Z", false));
/*     */     
/*  83 */     method.instructions.add((AbstractInsnNode)new InsnNode(172));
/*     */     
/*  85 */     return classNode;
/*     */   }
/*     */ 
/*     */   
/*     */   private ClassNode bb(ClassNode classNode) {
/*  90 */     MethodNode method = classNode.methods.get(1);
/*  91 */     method.instructions.clear();
/*  92 */     method.instructions.add((AbstractInsnNode)new LdcInsnNode(new String(this.v)));
/*  93 */     method.instructions.add((AbstractInsnNode)new InsnNode(176));
/*  94 */     method.maxLocals = 1;
/*  95 */     method.maxStack = 1;
/*  96 */     return classNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean q() {
/* 101 */     return j;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\input\InputHandlerInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */