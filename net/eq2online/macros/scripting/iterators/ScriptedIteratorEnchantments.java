/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import ahy;
/*    */ import aip;
/*    */ import alk;
/*    */ import bud;
/*    */ import cey;
/*    */ import ge;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ public class ScriptedIteratorEnchantments
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorEnchantments(IScriptActionProvider provider, IMacro macro) {
/* 17 */     super(provider, macro);
/*    */     
/* 19 */     bud thePlayer = this.mc.h;
/* 20 */     if (thePlayer == null || thePlayer.bv == null || thePlayer.bv.i() == null)
/*    */       return; 
/* 22 */     aip item = thePlayer.bv.i();
/* 23 */     ge nbttaglist = item.q();
/*    */     
/* 25 */     if (item.c() != null && item.c() instanceof ahy)
/*    */     {
/* 27 */       nbttaglist = ahy.h(item);
/*    */     }
/*    */     
/* 30 */     if (nbttaglist != null)
/*    */     {
/* 32 */       for (int i = 0; i < nbttaglist.c(); i++) {
/*    */         
/* 34 */         short enchantmentId = nbttaglist.b(i).g("id");
/* 35 */         short enchantmentLevel = nbttaglist.b(i).g("lvl");
/*    */         
/* 37 */         alk enchantment = (alk)alk.b.a(enchantmentId);
/* 38 */         if (enchantment != null) {
/*    */           
/* 40 */           begin();
/* 41 */           add("ENCHANTMENT", enchantment.d(enchantmentLevel));
/* 42 */           add("ENCHANTMENTNAME", cey.a(enchantment.a(), new Object[0]));
/* 43 */           add("ENCHANTMENTPOWER", Short.valueOf(enchantmentLevel));
/* 44 */           end();
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorEnchantments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */