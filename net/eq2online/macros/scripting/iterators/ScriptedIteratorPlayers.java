/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import bsc;
/*    */ import bud;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ public class ScriptedIteratorPlayers
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorPlayers(IScriptActionProvider provider, IMacro macro) {
/* 13 */     super(provider, macro);
/*    */     
/* 15 */     bud thePlayer = this.mc.h;
/* 16 */     for (bsc playerEntry : thePlayer.d.d()) {
/*    */       
/* 18 */       begin();
/* 19 */       add("PLAYERNAME", playerEntry.a().getName());
/* 20 */       end();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */