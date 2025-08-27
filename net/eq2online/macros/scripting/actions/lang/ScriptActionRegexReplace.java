/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.regex.PatternSyntaxException;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionRegexReplace
/*    */   extends ScriptActionReplace
/*    */ {
/*    */   public ScriptActionRegexReplace(ScriptContext context) {
/* 14 */     super(context, "regexreplace");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String doReplacement(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String sourceString, String searchFor, String replaceWith) {
/*    */     try {
/* 23 */       return sourceString.replaceAll(searchFor, replaceWith);
/*    */     }
/* 25 */     catch (PatternSyntaxException ex) {
/*    */       
/* 27 */       displayErrorMessage(provider, macro, instance, ex, "script.error.badregex");
/*    */     }
/* 29 */     catch (IndexOutOfBoundsException ex) {
/*    */       
/* 31 */       displayErrorMessage(provider, macro, instance, ex, "script.error.badreplacement");
/*    */     } 
/*    */     
/* 34 */     return sourceString;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionRegexReplace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */