/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptedIteratorArray
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorArray(IScriptActionProvider provider, IMacro macro, String arrayName, String valueVarName, String offsetVarName) {
/* 16 */     super(provider, macro);
/*    */     
/* 18 */     if (provider.getArrayExists(macro, arrayName)) {
/*    */       
/* 20 */       int ubound = provider.getArraySize(macro, arrayName);
/*    */       
/* 22 */       for (int offset = 0; offset < ubound; offset++) {
/*    */         
/* 24 */         begin();
/* 25 */         add(valueVarName, provider.getArrayElement(macro, arrayName, offset));
/* 26 */         add(offsetVarName, Integer.valueOf(offset));
/* 27 */         end();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */