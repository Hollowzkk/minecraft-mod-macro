/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import amy;
/*    */ import awt;
/*    */ import bhc;
/*    */ import et;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.variable.BlockPropertyTracker;
/*    */ import net.eq2online.macros.scripting.variable.IVariableStore;
/*    */ 
/*    */ public class ScriptedIteratorProperties
/*    */   extends ScriptedIterator
/*    */   implements IVariableStore
/*    */ {
/*    */   public ScriptedIteratorProperties(IScriptActionProvider provider, IMacro macro) {
/* 18 */     super(provider, macro);
/*    */     
/* 20 */     bhc objectHit = this.mc.s;
/*    */     
/* 22 */     if (objectHit != null && objectHit.a == bhc.a.b && this.mc.f != null) {
/*    */       
/* 24 */       et blockPos = objectHit.a();
/* 25 */       awt blockState = this.mc.f.o(blockPos);
/* 26 */       awt actualState = blockState.c((amy)this.mc.f, blockPos);
/* 27 */       new BlockPropertyTracker("", this, false, actualState);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeVariable(String name, boolean value) {
/* 34 */     begin();
/* 35 */     add("PROPNAME", name);
/* 36 */     add("PROPVALUE", Boolean.valueOf(value));
/* 37 */     end();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeVariable(String name, int value) {
/* 43 */     begin();
/* 44 */     add("PROPNAME", name);
/* 45 */     add("PROPVALUE", Integer.valueOf(value));
/* 46 */     end();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeVariable(String name, String value) {
/* 52 */     begin();
/* 53 */     add("PROPNAME", name);
/* 54 */     add("PROPVALUE", value);
/* 55 */     end();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */