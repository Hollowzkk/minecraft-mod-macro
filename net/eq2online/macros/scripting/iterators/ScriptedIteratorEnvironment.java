/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ 
/*    */ public class ScriptedIteratorEnvironment
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorEnvironment(IScriptActionProvider provider, IMacro macro) {
/* 13 */     super(provider, macro);
/*    */     
/* 15 */     Set<String> environmentVariables = provider.getEnvironmentVariables();
/* 16 */     environmentVariables.addAll(macro.getVariables());
/*    */     
/* 18 */     for (String var : environmentVariables) {
/*    */       
/* 20 */       if (var.matches("^~?[A-Z_]+[0-9]*$")) {
/*    */         
/* 22 */         begin();
/* 23 */         add("VARNAME", var);
/* 24 */         end();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */