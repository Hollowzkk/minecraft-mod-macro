/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.executive.MacroAction;
/*    */ import net.eq2online.macros.scripting.ActionParser;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ 
/*    */ public class ActionParserEval
/*    */   extends ActionParserAbstract
/*    */ {
/* 14 */   private static final Pattern PATTERN_PRINT = Pattern.compile("^(\\?|print)(% | |)(.*)$", 2);
/*    */   
/*    */   private final IScriptAction eval;
/*    */ 
/*    */   
/*    */   public ActionParserEval(ScriptContext context, IScriptAction eval) {
/* 20 */     super(context);
/* 21 */     this.eval = eval;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroAction parse(IMacroActionProcessor actionProcessor, String scriptEntry) {
/* 27 */     Matcher matcher = PATTERN_PRINT.matcher(scriptEntry);
/*    */     
/* 29 */     if (matcher.matches()) {
/*    */       
/* 31 */       String expression = matcher.group(3);
/* 32 */       Matcher actionMatcher = ActionParser.PATTERN_SCRIPTACTION.matcher(expression);
/*    */       
/* 34 */       if (actionMatcher.matches())
/*    */       {
/* 36 */         return (IMacroAction)parse(actionProcessor, actionMatcher.group(1), actionMatcher.group(2), "EVAL");
/*    */       }
/*    */       
/* 39 */       if (expression.trim().startsWith("\"") && expression.trim().endsWith("\"")) {
/*    */         
/* 41 */         StringBuilder rawParams = new StringBuilder();
/* 42 */         ScriptCore.tokenize(expression, ' ', '"', '"', '\\', rawParams);
/* 43 */         expression = (rawParams.length() > 0) ? rawParams.substring(1) : "";
/*    */       } 
/*    */       
/* 46 */       return (IMacroAction)new MacroAction(actionProcessor, this.eval, expression, expression, new String[] { expression, matcher.group(2) }, null);
/*    */     } 
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\ActionParserEval.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */