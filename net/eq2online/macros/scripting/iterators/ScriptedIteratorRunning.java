/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ 
/*    */ public class ScriptedIteratorRunning
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorRunning(IScriptActionProvider provider, IMacro macro) {
/* 12 */     super(provider, macro);
/*    */     
/* 14 */     for (IMacro.IMacroStatus status : provider.getMacroEngine().getExecutingMacroStatus()) {
/*    */       
/* 16 */       begin();
/* 17 */       add("MACROID", Integer.valueOf(status.getMacro().getID()));
/* 18 */       add("MACRONAME", status.getMacro().getDisplayName());
/* 19 */       add("MACROTIME", Integer.valueOf((int)status.getRunTime()));
/* 20 */       end();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorRunning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */