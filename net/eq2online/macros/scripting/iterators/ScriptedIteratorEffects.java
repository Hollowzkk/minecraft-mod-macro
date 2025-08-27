/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import bud;
/*    */ import cey;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import uz;
/*    */ import va;
/*    */ 
/*    */ public class ScriptedIteratorEffects
/*    */   extends ScriptedIterator {
/* 13 */   private static final String[] AMPLIFIER_SUFFIXES = new String[] { "", " II", " III", " IV", " VI" };
/*    */ 
/*    */ 
/*    */   
/*    */   public ScriptedIteratorEffects(IScriptActionProvider provider, IMacro macro) {
/* 18 */     super(provider, macro);
/*    */     
/* 20 */     bud thePlayer = this.mc.h;
/* 21 */     if (thePlayer == null)
/*    */       return; 
/* 23 */     for (va effect : thePlayer.ca()) {
/*    */       
/* 25 */       String potionName = cey.a(effect.f(), new Object[0]);
/*    */       
/* 27 */       begin();
/* 28 */       add("EFFECTID", Integer.valueOf(uz.a(effect.a())));
/* 29 */       add("EFFECT", potionName.toUpperCase().replace(" ", ""));
/*    */       
/* 31 */       int amplifier = effect.c();
/* 32 */       if (amplifier < AMPLIFIER_SUFFIXES.length)
/*    */       {
/* 34 */         potionName = potionName + AMPLIFIER_SUFFIXES[amplifier];
/*    */       }
/*    */       
/* 37 */       add("EFFECTNAME", potionName);
/* 38 */       add("EFFECTPOWER", Integer.valueOf(amplifier + 1));
/* 39 */       add("EFFECTTIME", Integer.valueOf(effect.b() / 20));
/* 40 */       end();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorEffects.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */