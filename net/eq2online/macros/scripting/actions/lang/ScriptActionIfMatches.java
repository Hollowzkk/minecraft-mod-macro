/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import java.util.regex.PatternSyntaxException;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ public class ScriptActionIfMatches
/*    */   extends ScriptActionIf
/*    */ {
/*    */   public ScriptActionIfMatches(ScriptContext context) {
/* 17 */     super(context, "ifmatches");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     if (params.length > 1) {
/*    */       
/* 25 */       String subject = provider.expand(macro, params[0], false);
/* 26 */       String pattern = provider.expand(macro, params[1], false);
/*    */ 
/*    */       
/*    */       try {
/* 30 */         Pattern regex = Pattern.compile(pattern, 2);
/* 31 */         Matcher matcher = regex.matcher(subject);
/*    */         
/* 33 */         if (matcher.find())
/*    */         {
/* 35 */           if (params.length > 2) {
/*    */             
/* 37 */             int grpVar = (params.length > 3) ? ScriptCore.tryParseInt(provider.expand(macro, params[3], false), 0) : 0;
/* 38 */             int groupNumber = Math.min(Math.max(grpVar, 0), matcher.groupCount());
/* 39 */             provider.setVariable(macro, params[2], matcher.group(groupNumber));
/*    */           } 
/*    */           
/* 42 */           return true;
/*    */         }
/*    */       
/* 45 */       } catch (PatternSyntaxException ex) {
/*    */         
/* 47 */         displayErrorMessage(provider, macro, instance, ex, "script.error.badregex");
/*    */       }
/* 49 */       catch (IllegalArgumentException illegalArgumentException) {}
/*    */     } 
/*    */     
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIfMatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */